package com.example.tetoretratil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FormTemporizador extends AppCompatActivity {

    private EditText tempo;
    private ImageView voltar;
    private Button limpar, iniciar;
    private TextView txt_voltar, tempo_txt;

    private CountDownTimer mCountDownTimer;

    String usuarioId;

    Integer status;

    private long mTimeLeftInMillis;
    private boolean mTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_temporizador);
        getSupportActionBar().hide();

        iniciarComponent();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormMenu.class);
                startActivity(intent);
            }
        });
        txt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormMenu.class);
                startActivity(intent);
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = tempo.getText().toString();
                if (input.length() == 0){
                    Snackbar snackbar = Snackbar.make(view, "Erro. Adicione um tempo ou limpe",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    return;
                }
                long teste = Long.parseLong(input) * 60000;
                setTime(teste);
                startTimer();
            }
        });

        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStop();
            }
        });

    }

    private void setTime(long milliseconds) {
        mTimeLeftInMillis = milliseconds;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tempo_txt.setText(timeLeftFormatted);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                abrirTeto();
            }
        }.start();

        mTimerRunning = true;
    }

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

    @Override
    protected void onStop() {
        super.onStop();
            mCountDownTimer.cancel();
            mTimeLeftInMillis = 0;
            updateCountDownText();
            tempo.setText("");
    }


    private void iniciarComponent() {
        voltar = findViewById(R.id.volta);
        txt_voltar = findViewById(R.id.txt_voltar);
        limpar = findViewById(R.id.limpar);
        iniciar = findViewById(R.id.iniciar);
        tempo = findViewById(R.id.tempo);
        tempo_txt = findViewById(R.id.tempo_txt);
    }
}