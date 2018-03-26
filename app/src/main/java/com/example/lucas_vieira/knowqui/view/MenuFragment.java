package com.example.lucas_vieira.knowqui.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lucas_vieira.knowqui.R;

public class TelaInicialActivity extends AppCompatActivity {

private TextView iniciarJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_inicial);

        iniciarJogo = findViewById(R.id.textoIniciarId);
        iniciarJogo.setOnClickListener(onClickListener());
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.textoIniciarId:
                        onClickIniciarJogo();
                        break;
                }
            }
        };
    }

    private void onClickIniciarJogo() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PerguntaFragment perguntaFragment = new PerguntaFragment();
        fragmentTransaction.replace(R.id.tela_inicial_layout,perguntaFragment, perguntaFragment.getClass().getSimpleName());
        fragmentTransaction.commit();

        /* todo devo da o finish?*/


    }
}
