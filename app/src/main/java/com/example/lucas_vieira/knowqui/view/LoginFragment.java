package com.example.lucas_vieira.knowqui.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lucas_vieira.knowqui.R;

public class LoginFragment extends Fragment {

    EditText loginEdtTxt;
    EditText senhaEdtTxt;
    TextView cadastrarTxtView;
    Button logarBtn;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.login_fragment,container,false);

        loginEdtTxt =layout.findViewById(R.id.edit_text_login);

        senhaEdtTxt = layout.findViewById(R.id.edit_text_senha);

        logarBtn = layout.findViewById(R.id.button_logar );
        logarBtn.setOnClickListener(onClickListener());

         cadastrarTxtView = layout.findViewById(R.id.textview_cadastrar);
         cadastrarTxtView.setOnClickListener(onClickListener());

        return layout;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String login, senha;

        login = loginEdtTxt.getText().toString();

        senha = senhaEdtTxt.getText().toString();
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.button_logar ){
                    logar();
                }else if(v.getId() == R.id.textview_cadastrar){
                    chamarTelaCadastro();
                }
            }
        };
    }

    private void logar() {

    }

    private void chamarTelaCadastro() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CadastroFragment cadastroFragment = new CadastroFragment();

        fragmentTransaction.replace(R.id.layout_main,cadastroFragment,cadastroFragment.getClass().getSimpleName());

        fragmentTransaction.commit();
    }
}


