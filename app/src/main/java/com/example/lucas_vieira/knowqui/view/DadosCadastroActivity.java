package com.example.lucas_vieira.knowqui.view;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;

public class DadosCadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextIdade;

    private CheckBox checkBoxPublico;
    private CheckBox checkBoxPrivado;

    private ImageView imageButtonSexoFeminino;
    private ImageView imageButtonSexoMasculino;

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

        editTextNome = findViewById(R.id.editTextNomeId);
        editTextIdade = findViewById(R.id.editTextIdadeId);

        checkBoxPrivado = findViewById(R.id.checkBoxPrivadoId);
        checkBoxPublico = findViewById(R.id.checkBoxPublicoId);

    }


    private View.OnClickListener onClickListenerSalvar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo JP FAZER
                boolean campoNomeValidado = validarCampoNome();
                boolean campoIdadeValidade = validarCampoIdade();

                if(campoNomeValidado == true){
                    chamarTelaInicial();
                }else if(campoIdadeValidade == true){
                    chamarTelaInicial();
                }


            }
        };
    }

    private boolean validarCampoIdade() {

        if(editTextIdade.getText().toString().equals("")){

            Toast.makeText(getApplication(),"Campo Idade Vazio", Toast.LENGTH_SHORT).show();
            editTextIdade.requestFocus();
            return false;
        }return  true;

    }

    private boolean validarCampoNome() {
        if(editTextNome.getText().toString().equals("")){
            Toast.makeText(getApplication(),"Campo Nome Vazio", Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return false;
        }return true;
    }

    private void chamarTelaInicial() {
        Intent intent = new Intent(this, TelaInicialActivity.class);
        startActivity(intent);
        finish();
    }



}
