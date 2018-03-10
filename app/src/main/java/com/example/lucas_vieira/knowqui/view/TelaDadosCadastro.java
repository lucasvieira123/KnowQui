package com.example.lucas_vieira.knowqui.view;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lucas_vieira.knowqui.R;

public class TelaDadosCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dados_cadastro);

        Spinner spinnerEscolaridade = findViewById(R.id.spinnerEscolaridadeId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tiposDeEscolaridades,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEscolaridade.setAdapter(adapter);

    }


}
