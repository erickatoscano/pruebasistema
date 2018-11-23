package com.example.administrador.prueba_sistema;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Person;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePaciente extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner enfermedadesSpinner;
    ArrayList<String> listaEnfermedades=new ArrayList<>();

    ArrayAdapter<String> comboAdapter;
    String idEnfermedad, nombreEnfermedad;



private String userId;

    TextView txtuser;

    EditText mail, nombre, apellido, clave, rol_id;
    Button guardar, sacarCita;

    private DatabaseReference databaseReference;
     TextView nameTextView, nameEView, mailEView, contactEtView,infoView, messageView;
    private Persons persona;
    Cita cita;
    Paciente paciente;
    String idU, enfermedadGuardada;
    Boolean flag=true;
    Integer cont;
    private ProgressDialog progressDialog;
    FirebaseUser user; //USUARIO LOGUEADO
    String idPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_paciente);

        enfermedadesSpinner=(Spinner)findViewById(R.id.spinnerEnfermedades);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.enfermedades,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        enfermedadesSpinner.setOnItemSelectedListener(this);

        txtuser= (TextView)findViewById(R.id.bienvenidoE);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        //DATOS AUTENTICACION//
        nameTextView = (TextView) findViewById(R.id.nameTextView);



        user = FirebaseAuth.getInstance().getCurrentUser();
        idPaciente = user.getUid();

        if (user != null) {
            String name = user.getDisplayName();
            String pass = "123";
            String mail = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();
            Integer rol_id = 2;

            nameTextView.setText("Bienvenido paciente "+name);

            idU=databaseReference.push().getKey();

            persona = new Persons (name,mail,pass,rol_id);


            //for que pase por los ids (child)
            //for que pase por el mail y pregunte

            Query q = databaseReference.child("persons").orderByChild("mail").equalTo(mail);

            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cont=0;
                    for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                        cont++;
                        flag=false;

                        //Toast.makeText(MainActivity.this, "He encontrado "+cont, Toast.LENGTH_LONG).show();

                        Toast.makeText(getApplicationContext(),
                                "Usuario ya registrado",
                                Toast.LENGTH_SHORT).show();
                    }


                    Toast.makeText(getApplicationContext(), flag.toString(), Toast.LENGTH_SHORT).show();


                    //AGREGAR A LA BASE DE DATOS EN FIREBASE
                    if(flag==true){

                        paciente = new Paciente(user.getUid(), 2, "norte");
                        //AGREGO EN LA BD DE FIREBASE MI OBJETO PERSONA
                        databaseReference.child("patient").child(idU).setValue(paciente);



                        databaseReference.child("persons").child(user.getUid()).setValue(persona);
                        Toast.makeText(getApplicationContext(),
                                "Usuario registrado",
                                Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } else {
          //  goLoginScreen();
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Almaceno el nombre de la fruta seleccionada

        enfermedadGuardada=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sacarCita(View view) {
        //recorrer personas
        //guardar en bd la cita
        //cambiar el estado del estudiante

        Query q = databaseReference.child("persons").orderByChild("enfermedad_a_tratar").equalTo(enfermedadGuardada);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(final DataSnapshot datasnapshot: dataSnapshot.getChildren())
                    if (datasnapshot.child("disponible").getValue().equals("si")) {
                        Toast.makeText(getApplicationContext(),
                                "Se ha encontrado un resultado con el estudiante: "
                                        + datasnapshot.child("name").getValue() + " , para el día: "
                                        + datasnapshot.child("disponibilidad").getValue(),
                                Toast.LENGTH_SHORT).show();



                        //LO IDEAL SERIA UN BOOKING



                        // Use the Builder class for convenient dialog construction
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePaciente.this);
                        alertDialog.setTitle("¿Desea confirmar la cita?");
                        alertDialog.setMessage("Se ha encontrado un resultado con el estudiante: "
                                + datasnapshot.child("name").getValue() + ", para el día: "
                                + datasnapshot.child("disponibilidad").getValue() +
                        "\n Tenga en cuenta que si cancela deberá consultar otro día")
                                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // FIRE ZE MISSILES!
                                        //process dialog
                                        //agregar a bd cita


                                        //Toast.makeText(getApplicationContext(), "ID PACIENTE: "+idPaciente+"\nID ESTUDIANTE: " + datasnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();


                                        String id_patient = idPaciente;
                                        String id_student = datasnapshot.getKey().toString();
                                        String fecha = datasnapshot.child("disponibilidad").getValue().toString();


                                        cita = new Cita (fecha, id_patient, id_student);
                                        databaseReference.child("appointment").child(idU).setValue(cita);

                                        nameEView = (TextView) findViewById(R.id.nombreEstudiante);
                                        mailEView = (TextView) findViewById(R.id.correoEstudiante);
                                        contactEtView = (TextView) findViewById(R.id.contactoEstudiante);
                                        infoView =  (TextView) findViewById(R.id.infoCita);
                                        messageView =  (TextView) findViewById(R.id.messageCita);

                                        String nameE = datasnapshot.child("name").getValue().toString();
                                        String emailE = datasnapshot.child("mail").getValue().toString();

                                        infoView.setText("Información de su cita");
                                        messageView.setText("Su cita esta programada para el " +fecha);
                                        nameEView.setText("Nombre del estudiante: "+nameE);
                                        mailEView.setText("Email: "+emailE);

                                        //actualizar bd
                                        databaseReference.child("persons").child(datasnapshot.getKey()).child("disponible").setValue("no");



                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                });
                        // Create the AlertDialog object and return it
                        AlertDialog alert = alertDialog.create();
                        alert.show();

                        break;




                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Lo sentimos no hay fechas disponibles, intente otro día",
                                Toast.LENGTH_SHORT).show();

                    }
           }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
