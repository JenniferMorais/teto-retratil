package com.example.tetoretratil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class FormInicio extends AppCompatActivity {
    private ImageView abrir_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pincipal);

        getSupportActionBar().hide();
        iniciarComponent();

        abrir_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FormLogin.class);
                startActivity(intent);

            }
        });
    }

    private void iniciarComponent() {
        abrir_button = findViewById(R.id.abrir_button);
    }
}