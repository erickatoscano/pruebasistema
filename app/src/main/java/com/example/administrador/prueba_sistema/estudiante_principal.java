package com.example.administrador.prueba_sistema;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class estudiante_principal extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final String user="names";
    TextView txtuser;
    Spinner enfermedadesSpinner;
   // ArrayList<String> listaEnfermedades=new ArrayList<>();

    //ArrayAdapter<String> comboAdapter;
    String idEnfermedad,
            nombreEnfermedad, enfermedadGuardada,
    idLogueado;

    Integer count;
    FirebaseUser userA;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_principal);


        userA = FirebaseAuth.getInstance().getCurrentUser();
        idLogueado=userA.getUid();

        txtuser= (TextView)findViewById(R.id.bienvenidoE);
        String user = getIntent().getStringExtra("names");
        txtuser.setText("BIENVENIDO " +user+"!!");


        enfermedadesSpinner=(Spinner)findViewById(R.id.spinnerEnfermedades);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.enfermedades,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enfermedadesSpinner.setOnItemSelectedListener(this);



        databaseReference = FirebaseDatabase.getInstance().getReference();
        userA = FirebaseAuth.getInstance().getCurrentUser();

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

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

    public void consultarCita(View view) {
        //recorrer personas
        //guardar en bd la cita
        //cambiar el estado del estudiante

        //consultar y mostrar el paciente segun el id del estudiante logueado

        Query q = databaseReference.child("appointment").orderByChild("id_student").equalTo(idLogueado);
        Toast.makeText(estudiante_principal.this,
                "ID: "+idLogueado, Toast.LENGTH_LONG).show();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    count++;


                    Toast.makeText(estudiante_principal.this,
                            "He encontrado "+count, Toast.LENGTH_LONG).show();


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
