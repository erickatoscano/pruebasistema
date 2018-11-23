package com.example.administrador.prueba_sistema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class estudiante_inicio extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CustomAuthActivity";

    Spinner universidades;
    Button signup, login;
    EditText nameTextView, emailTextView, passTextView, emailI, claveI;
    Persons persona;
    String idEstudiante;
    Boolean flag=true;
    private DatabaseReference databaseReference;
    //Declaramos un objeto firebaseAuth
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String mCustomToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_inicio);

        universidades=(Spinner)findViewById(R.id.spinner);

        mAuth = FirebaseAuth.getInstance();

        nameTextView=(EditText)findViewById(R.id.registro_name);
        emailTextView=(EditText)findViewById(R.id.registro_correo);
        passTextView=(EditText)findViewById(R.id.registro_pass);

        emailI=(EditText)findViewById(R.id.login_correo);
        claveI=(EditText)findViewById(R.id.login_clave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.universidades, android.R.layout.simple_spinner_item);
        universidades.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        ////////////////////////////// I N I C I O
        Button login = (Button) findViewById(R.id.btn_inicio);
        login.setOnClickListener(this);

        ////////////////////////////// R E G I S T R O
        Button signup = (Button) findViewById(R.id.btn_registroE);
        signup.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
  //      updateUI(currentUser);
    }



    private void registrarUsuario() {
        String nombre = nameTextView.getText().toString().trim();
        final String email = emailTextView.getText().toString().trim();
        String clave = passTextView.getText().toString().trim();
        Integer rol_id = 1;

        //se comprueba que no este vacia
        if(TextUtils.isEmpty(email)){
            Toast.makeText(estudiante_inicio.this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(clave)){
            Toast.makeText(estudiante_inicio.this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Creando cuenta");
        progressDialog.show();

        idEstudiante=databaseReference.push().getKey();


        //CREO EL OBJETO Y PREGUNTO SI SE REPITE POR EMAIL
        persona = new Persons (nombre,email,clave,rol_id);
        //Query personaQ = databaseReference.child("persons").orderByChild("mail").equalTo(email);


        mAuth.createUserWithEmailAndPassword(email, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                           // FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);
                            Toast.makeText(estudiante_inicio.this,"Se ha registrado el usuario con el email: "+
                                    emailTextView.getText(),Toast.LENGTH_LONG).show();



                            //AGREGO EN LA BD DE FIREBASE MI OBJETO PERSONA
                            databaseReference.child("persons").child(idEstudiante).setValue(persona);
                            // databaseReference.child("students").child(idEstudiante).setValue(persona);



                            //IR A PANTALLA PRINCIPAL
/*
                            Intent intent = new Intent(getApplicationContext(), estudiante_principal.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);*/

                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);

                            Toast.makeText(estudiante_inicio.this, "Bienvenido: " + emailTextView.getText(),
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), estudiante_principal.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(estudiante_principal.user, user);//enviar el parametro
                            startActivity(intent);





                        } else {


                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(estudiante_inicio.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(estudiante_inicio.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                                // updateUI(null);
                            }




                        }
                        progressDialog.dismiss();
                        // ...
                    }
                });

    }


    private void loguearUsuario() {

        //AUTENTICA

        //Obtenemos el email y la contraseña desde las cajas de texto

        final String emailA = emailI.getText().toString().trim();
        String clave = claveI.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(emailA)) {//(precio.equals(""))
            Toast.makeText(estudiante_inicio.this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(clave)) {
            Toast.makeText(estudiante_inicio.this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        mAuth.signInWithEmailAndPassword(emailA, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            int pos = emailA.indexOf("@");
                            String user = emailA.substring(0, pos);

                            Toast.makeText(estudiante_inicio.this, "Bienvenido: " + emailI.getText(),
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), estudiante_principal.class);
                            intent.putExtra(estudiante_principal.user, user);//enviar el parametro
                            startActivity(intent);


                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(estudiante_inicio.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(estudiante_inicio.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }



                        }
                        progressDialog.dismiss();
                    }
                });








    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_registroE:
                //Invocamos al método:
                registrarUsuario();
                break;
            case R.id.btn_inicio:
                loguearUsuario();
                break;
        }


    }

}
