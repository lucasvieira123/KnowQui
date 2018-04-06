package com.example.lucas_vieira.knowqui.view;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;

public class CadastroFragment extends Fragment {

    private EditText editTextNome;
    private EditText editTextIdade;

    private CheckBox checkBoxPublico;
    private CheckBox checkBoxPrivado;

    private ImageView imageButtonSexoFeminino;
    private ImageView imageButtonSexoMasculino;
    private Spinner spinnerEscolaridade;

    private Button botaoSalvar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View layout = inflater.inflate(R.layout.cadastro_fragment,container,false);

        spinnerEscolaridade = layout.findViewById(R.id.spinnerEscolaridadeId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext()
                , R.array.tiposDeEscolaridades,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEscolaridade.setAdapter(adapter);

        botaoSalvar = layout.findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(onClickListenerSalvar());

        editTextNome = layout.findViewById(R.id.editTextNomeId);
        editTextNome.setText("bla");
        editTextIdade = layout.findViewById(R.id.editTextIdadeId);
        editTextIdade.setText("bla");

        checkBoxPrivado = layout.findViewById(R.id.checkBoxPrivadoId);
        checkBoxPublico = layout.findViewById(R.id.checkBoxPublicoId);

        checkBoxPrivado.setOnClickListener(onCheckedChangeListnerPrivado());
        checkBoxPublico.setOnClickListener(onCheckedChangeListnerPublico());

        return layout;
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


    private void chamarMenuFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MenuFragment menuFragment = new MenuFragment();

        fragmentTransaction.replace(R.id.layout_main,
                menuFragment,
                menuFragment.getClass().getSimpleName());

        fragmentTransaction.commit();

    }

    private boolean validarCampoIdade() {

        if(editTextIdade.getText().toString().equals("")){

            Toast.makeText(getActivity().getBaseContext(),"Campo Idade Vazio", Toast.LENGTH_SHORT).show();
            return false;
        }else {

            chamarMenuFragment();
            return true;

        }

    }

    private boolean validarCampoNome() {
        if(editTextNome.getText().toString().equals("")){
            Toast.makeText(getActivity().getBaseContext(),"Campo Nome Vazio", Toast.LENGTH_SHORT).show();
            return false;
        }else {

            chamarMenuFragment();
            return true;

        }
    }

    private boolean validarCheckBoxPrivado() {

        if(checkBoxPrivado.isChecked()){

            chamarMenuFragment();
            return true;

        }else {

            Toast.makeText(getActivity().getBaseContext(),"Caixa Rede de Ensino Vazia", Toast.LENGTH_SHORT).show();
            return false;

        }

    }

    private boolean validarCheckBoxPublico() {
        if(checkBoxPublico.isChecked()){

            chamarMenuFragment();
            return true;

        }else {

            Toast.makeText(getActivity().getBaseContext(),"Caixa Rede de Ensino Vazia", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
