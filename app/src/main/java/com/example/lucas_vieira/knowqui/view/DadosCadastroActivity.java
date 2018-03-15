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

        checkBoxPrivado.setOnClickListener(onCheckedChangeListnerPrivado());
        checkBoxPublico.setOnClickListener(onCheckedChangeListnerPublico());
    }

    private View.OnClickListener onCheckedChangeListnerPublico() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        };
    }

    private View.OnClickListener onCheckedChangeListnerPrivado() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        };


    }


    private View.OnClickListener onClickListenerSalvar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo JP FAZER
                boolean campoNomeValidado = validarCampoNome();
                boolean campoIdadeValidada = validarCampoIdade();
                boolean checkBoxPrivadoValidado = validarCheckBoxPrivado();
                boolean checkBoxPublicoValidado = validarCheckBoxPublico();

                validarCampoIdade();
                validarCampoNome();

                validarCheckBoxPublico();
                validarCheckBoxPrivado();

            }
        };
    }


    private void chamarTelaInicial() {
        Intent intent = new Intent(this, TelaInicialActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampoIdade() {

        if(editTextIdade.getText().toString().equals("")){

            Toast.makeText(getApplication(),"Campo Idade Vazio", Toast.LENGTH_SHORT).show();
            return false;
        }else {

            chamarTelaInicial();
            return true;

        }

    }

    private boolean validarCampoNome() {
        if(editTextNome.getText().toString().equals("")){
            Toast.makeText(getApplication(),"Campo Nome Vazio", Toast.LENGTH_SHORT).show();
            return false;
        }else {

            chamarTelaInicial();
            return true;

        }
    }

    private boolean validarCheckBoxPrivado() {

        if(checkBoxPrivado.isChecked()){

            chamarTelaInicial();
            return true;

        }else {

            Toast.makeText(getApplication(),"Caixa Rede de Ensino Vazia", Toast.LENGTH_SHORT).show();
            return false;

        }

    }

    private boolean validarCheckBoxPublico() {
        if(checkBoxPublico.isChecked()){

            chamarTelaInicial();
            return true;

        }else {

            Toast.makeText(getApplication(),"Caixa Rede de Ensino Vazia", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
