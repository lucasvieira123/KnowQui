package com.example.lucas_vieira.knowqui.view;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import utils.CarregamentoDialog;
import utils.GetStringJson;

public class CadastroFragment extends Fragment {

    private EditText editTextNome,
            editTextLogin,
            editTextSenha,
            editTextConfirmarSenha;

    private TextInputLayout inputConfirmarSenha, inputSenha, inputNome, inputLogin;

    private RadioButton sexoMasculino, sexoFeminino, ensinoPrivado, ensinoPublico;

    private CheckBox checkBoxPublico;
    private CheckBox checkBoxPrivado;

    private ImageView imageButtonSexoFeminino;
    private ImageView imageButtonSexoMasculino;
    private Spinner spinnerEscolaridade;

    private Button botaoSalvar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cadastro_fragment, container, false);

        editTextNome = layout.findViewById(R.id.editTextNomeId);
        editTextLogin = layout.findViewById(R.id.cadastrar_usuario_login);
        editTextSenha = layout.findViewById(R.id.cadastrar_usuario_senha);
        editTextConfirmarSenha = layout.findViewById(R.id.cadastrar_usuario_confirmar_senha);

        editTextLogin.setOnFocusChangeListener(desabilitarErroInputText);
        editTextNome.setOnFocusChangeListener(desabilitarErroInputText);
        editTextSenha.setOnFocusChangeListener(desabilitarErroInputText);
        editTextConfirmarSenha.setOnFocusChangeListener(desabilitarErroInputText);

        inputSenha = layout.findViewById(R.id.cadastrar_usuario_input_layout_senha);
        inputConfirmarSenha = layout.findViewById(R.id.cadastro_usuario_input_layout_confirmar_senha);
        inputNome = layout.findViewById(R.id.cadastrar_usuario_input_layout_nome);
        inputLogin = layout.findViewById(R.id.cadastrar_usuario_input_layout_login);

        sexoMasculino = layout.findViewById(R.id.cadastrar_usuario_sexo_masc);
        sexoFeminino = layout.findViewById(R.id.cadastrar_usuario_sexo_fem);
        ensinoPrivado = layout.findViewById(R.id.cadastrar_usuario_rede_ensino_privado);
        ensinoPublico = layout.findViewById(R.id.cadastrar_usuario_rede_ensino_publico);

        botaoSalvar = layout.findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(onClickListenerSalvar());

        return layout;
    }

    private View.OnClickListener onClickListenerSalvar() {
        return new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                if (!validarConfirmacaoSenha()){
                    return;
                }

                if (validarPreenchimentoCampos()){
                    return;
                }

                if (validarTamanhoMinimoLoginESenha()){
                    return;
                }

                if(validarAcentuacaoEmNomeLoginESenha()){
                    return;
                }


                final CarregamentoDialog dialog = new CarregamentoDialog(getActivity());
                dialog.show();

                new AsyncTask<Void, Void, HttpResponse>() {
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    JSONObject cadastroUsuario = new JSONObject();

                    @Override
                    protected HttpResponse doInBackground(Void... voids) {
                        HttpPost post = new HttpPost("http://knowqui.com.br/cadastrar-usuario");

                        try {
                            cadastroUsuario.put("nome", editTextNome.getText().toString());
                            cadastroUsuario.put("login", editTextLogin.getText().toString().toLowerCase().trim());
                            cadastroUsuario.put("senha", editTextSenha.getText().toString().toLowerCase().trim());

                            if (ensinoPublico.isChecked()) {
                                cadastroUsuario.put("rede_ensino", 0);
                            } else if (ensinoPrivado.isChecked()) {
                                cadastroUsuario.put("rede_ensino", 1);
                            }

                            if (sexoMasculino.isChecked()) {
                                cadastroUsuario.put("sexo", "M");
                            } else if (sexoFeminino.isChecked()) {
                                cadastroUsuario.put("sexo", "F");
                            }

                            StringEntity entity = new StringEntity(cadastroUsuario.toString());
                            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                            post.setEntity(entity);
                            response = client.execute(post);
                            Log.i("CADASTRO", "doInBackground: response --->" + response.toString());
                            return response;
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                        return null;

                    }

                    @Override
                    protected void onPostExecute(HttpResponse httpResponse) {
                        dialog.dismiss();

                        if (response != null){
                            InputStream inputStream = null;
                            try {
                                inputStream = response.getEntity().getContent();
                                String json = GetStringJson.getStringFromInputStream(inputStream);
                                inputStream.close();

                                if (verificaEExibeMensagemError(json)){
                                    return;
                                }
                                Toast.makeText(getActivity(),"Cadastrado com sucesso!",Toast.LENGTH_SHORT).show();

                                chamaLoginFragment();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.execute();
            }
        };
    }

    private boolean validarAcentuacaoEmNomeLoginESenha() {
        String nome = editTextNome.getText().toString();
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();

        boolean nomeContemAcento = Pattern.matches(".*[áàãâéèẽêíìĩîóòõôúùũûÁÀÃÂÉÈẼÊÍÌĨÎÒÓÔÕÙÚŨÛ].*", nome);
        boolean loginContemAcento = Pattern.matches(".*[áàãâéèẽêíìĩîóòõôúùũûÁÀÃÂÉÈẼÊÍÌĨÎÒÓÔÕÙÚŨÛ].*", login);
        boolean senhaContemAcento = Pattern.matches(".*[áàãâéèẽêíìĩîóòõôúùũûÁÀÃÂÉÈẼÊÍÌĨÎÒÓÔÕÙÚŨÛ].*", senha);

        if(loginContemAcento || senhaContemAcento || nomeContemAcento){
            Toast.makeText(getActivity(),"Os campos Nome, Login ou Senha não devem ter acentuação!",Toast.LENGTH_LONG).show();
        }

        return loginContemAcento || senhaContemAcento || nomeContemAcento;



    }

    @SuppressLint("ResourceType")
    private boolean validarConfirmacaoSenha(){
        String senha = editTextSenha.getText().toString();
        String confirmarSenha = editTextConfirmarSenha.getText().toString();

        if (senha.equals(confirmarSenha)){
            inputConfirmarSenha.setErrorEnabled(false);
            return true;
        }else{
            inputConfirmarSenha.setErrorEnabled(true);
            inputConfirmarSenha.setError("As senhas não conferem!");
            inputConfirmarSenha.setErrorTextAppearance(R.color.errorMessage);
            return false;
        }
    }

    private boolean validarTamanhoMinimoLoginESenha(){
        if(editTextLogin.getText().toString().length() < 4){
            inputLogin.setErrorEnabled(true);
            inputLogin.setError("A Login deve conter no mínino 4 caracteres!");
            return true;
        }

        if (editTextSenha.getText().toString().length() < 4 ){
            inputSenha.setErrorEnabled(true);
            inputSenha.setError("A Senha deve conter no mínino 4 caracteres!");

            return true;
        }

        return false;

    }

    private boolean validarPreenchimentoCampos(){
        String nome = editTextNome.getText().toString();
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();
        String confirmarSenha = editTextConfirmarSenha.getText().toString();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            if (nome.isEmpty()) {
                inputNome.setErrorEnabled(true);
                inputNome.setError("Campo Obrigatório!");
            }

            if (login.isEmpty()) {
                inputLogin.setErrorEnabled(true);
                inputLogin.setError("Campo Obrigatório!");
            }

            if (senha.isEmpty()) {
                inputSenha.setErrorEnabled(true);
                inputSenha.setError("Campo Obrigatório!");
            }

            if (confirmarSenha.isEmpty()) {
                inputConfirmarSenha.setErrorEnabled(true);
                inputConfirmarSenha.setError("Campo Obrigatório!");
            }

            return true;
        }else {
            return false;
        }
    }

    View.OnFocusChangeListener desabilitarErroInputText = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (view.hasFocus()){
                inputNome.setErrorEnabled(false);
                inputLogin.setErrorEnabled(false);
                inputSenha.setErrorEnabled(false);
                inputConfirmarSenha.setErrorEnabled(false);
            }
        }
    };



    private boolean verificaEExibeMensagemError(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = new JSONObject(jsonArray.getString(0));

            String messagem = jsonObject.getString("message");
            Toast.makeText(getActivity(), messagem, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.contains("error");
    }

    private void chamaLoginFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();

        fragmentTransaction.replace(R.id.layout_main,
                loginFragment,
                loginFragment.getClass().getSimpleName());

        fragmentTransaction.commit();


    }

}
