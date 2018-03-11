package com.example.lucas_vieira.knowqui.view;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import com.example.lucas_vieira.knowqui.R;

public class DadosCadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextIdade;

    private CheckBox checkBoxPublico;
    private CheckBox checkBoxPrivado;

    private ImageButton imageButtonSexoFeminino;
    private ImageButton imageButtonSexoMasculino;

    private Button botaoSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dados_cadastro);

        Spinner spinnerEscolaridade = findViewById(R.id.spinnerEscolaridadeId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tiposDeEscolaridades,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEscolaridade.setAdapter(adapter);

        botaoSalvar = findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(onClickListenerSalvar());

    }

    private View.OnClickListener onClickListenerSalvar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo JP FAZER
                boolean camposValidados = validarCampos();

                if(camposValidados){
                    chamarTelaInicial();
                }


            }
        };
    }


    private boolean validarCampos() {
        return true;
    }

    private void chamarTelaInicial() {
        Intent intent = new Intent(this, TelaInicialActivity.class);
        startActivity(intent);
        finish();
    }


}
