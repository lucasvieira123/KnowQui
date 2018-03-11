package com.example.lucas_vieira.knowqui.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lucas_vieira.knowqui.R;

public class LayoutSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_splash);
        Handler handler = new Handler();
        mostrarTelaInicial();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mostrarTelaInicial();
            }
        },3000);
        }

        private void mostrarTelaInicial(){

            Intent intent = new Intent(LayoutSplash.this,TelaDadosCadastro.class);
            startActivity(intent);
            finish();

        }

    }

