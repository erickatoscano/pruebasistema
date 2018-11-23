package com.example.administrador.prueba_sistema;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePaciente extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner enfermedadesSpinner;
    ArrayList<String> listaEnfermedades=new ArrayList<>();

    ArrayAdapter<String> comboAdapter;
    String idEnfermedad, nombreEnfermedad;




    TextView txtuser;

    EditText mail, nombre, apellido, clave, rol_id;
    Button guardar;

    private DatabaseReference databaseReference;
     TextView nameTextView;
    private Persons persona;
    String idU;
    Boolean flag=true;
    Integer cont;

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
      //  String user = getIntent().getStringExtra("names");
      //  txtuser.setText("BIENVENIDO " +user+"!!");

        databaseReference = FirebaseDatabase.getInstance().getReference();


        //DATOS AUTENTICACION//
        nameTextView = (TextView) findViewById(R.id.nameTextView);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                        databaseReference.child("persons").child(idU).setValue(persona);
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
                //nombreEnfermedad = strEnfermedades[position];

        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
