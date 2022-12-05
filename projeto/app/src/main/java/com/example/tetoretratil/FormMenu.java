package com.example.tetoretratil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class FormMenu extends AppCompatActivity {

    private ImageView abrir_button, fechar_button, temporizador_button;

    private TextView abrir, fechar, temporizador;

//    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String usuarioId;

//    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_menu);

        getSupportActionBar().hide();
        iniciarComponent();

        temporizador_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormTemporizador.class);
                startActivity(intent);
            }
        });
        temporizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormTemporizador.class);
                startActivity(intent);
            }
        });

        abrir_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTeto();
            }
        });
        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTeto();
            }
        });

        fechar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecharTeto();
            }
        });
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecharTeto();
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        DatabaseReference teste = FirebaseDatabase.getInstance().getReference("nome");
//
//        DatabaseReference reference = teste.child(usuarioId);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String data = snapshot.child("nome").getValue().toString();
//                    nome.setText(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("db_error", "Erro ao exibir nome");
//            }
//        });
//    }

    private void abrirTeto(){
        HashMap<String,Object> usuario = new HashMap();
        usuario.put("status",1);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference teste = FirebaseDatabase.getInstance().getReference("usuarios");

        DatabaseReference reference = teste.child(usuarioId);
        reference.updateChildren(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("db_error", "Abrir teto, sucesso!");
                }else{
                    Log.d("db_error", "Erro ao abrir teto");
                }
            }
        });
    }

    private void fecharTeto(){
        HashMap<String,Object> usuario = new HashMap();
        usuario.put("status",0);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference teste = FirebaseDatabase.getInstance().getReference("usuarios");

        DatabaseReference reference = teste.child(usuarioId);
        reference.updateChildren(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("db_error", "Fechar teto, sucesso!");
                }else{
                    Log.d("db_error", "Erro ao fechar teto");
                }
            }
        });
    }

    private void iniciarComponent() {
        abrir_button = findViewById(R.id.abrir_button);
        abrir = findViewById(R.id.abrir);

        fechar_button = findViewById(R.id.fechar_button);
        fechar = findViewById(R.id.fechar);

        temporizador_button  = findViewById(R.id.temporizador_button);
        temporizador = findViewById(R.id.temporizador);
    }
}