package com.example.tetoretratil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroForm extends AppCompatActivity {

    private EditText nome_cadastro,email_cadastro,senha_cadastro;

    private Button cadastro_button;

    private TextView txt_voltar;

    private ImageView voltar;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    String[] mensagens = {"Preencha todos os campos.", "Cadastro realizado com sucesso."};

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_form);

        getSupportActionBar().hide();
        iniciarComponent();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormLogin.class);
                startActivity(intent);
            }
        });

        txt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormLogin.class);
                startActivity(intent);
            }
        });

        cadastro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = nome_cadastro.getText().toString();
                String email = email_cadastro.getText().toString();
                String senha = senha_cadastro.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view, mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    cadastrarUsuario(view);
                }

            }
        });
    }

    private void cadastrarUsuario(View view) {
        String nome = nome_cadastro.getText().toString();
        String email = email_cadastro.getText().toString();
        String senha = senha_cadastro.getText().toString();

        auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Usuario u = new Usuario();
                    DatabaseReference usuario = reference.child("usuario");
                    u.setId(auth.getUid());
                    u.setNome(nome);
                    u.setStatus(0);
                    u.setTempo(" ");
                    u.salvar();

                    Intent intent = new Intent(getApplicationContext(), FormLogin.class);
                    startActivity(intent);

                    Snackbar snackbar = Snackbar.make(view, mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    String erro;
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Essa conta ja foi cadastrada.";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar o usuario.";
                    }

                    Snackbar snackbar = Snackbar.make(view, erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void iniciarComponent() {
        nome_cadastro = findViewById(R.id.nome_cadastro);
        email_cadastro = findViewById(R.id.email_cadastro);
        senha_cadastro = findViewById(R.id.senha_cadastro);
        cadastro_button = findViewById(R.id.cadastro_button);
        voltar = findViewById(R.id.volta);

        txt_voltar = findViewById(R.id.txt_voltar);

    }
}