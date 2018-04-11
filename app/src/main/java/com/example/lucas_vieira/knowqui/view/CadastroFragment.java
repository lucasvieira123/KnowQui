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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
import java.io.UnsupportedEncodingException;

import utils.CarregamentoDialog;
import utils.GetStringJson;

public class CadastroFragment extends Fragment {

    private EditText editTextNome,
            editTextLogin,
            editTextSenha,
            editTextConfirmarSenha;

    private TextInputLayout inputConfirmarSenha, inputSenha;

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

        //TODO retirar tudo o que eu comentei, caso ache necessário
        /*spinnerEscolaridade = layout.findViewById(R.id.spinnerEscolaridadeId);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext()
                , R.array.tiposDeEscolaridades,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEscolaridade.setAdapter(adapter);*/

        botaoSalvar = layout.findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(onClickListenerSalvar());

        editTextNome = layout.findViewById(R.id.editTextNomeId);
        editTextLogin = layout.findViewById(R.id.cadastrar_usuario_login);
        editTextSenha = layout.findViewById(R.id.cadastrar_usuario_senha);

        inputSenha = layout.findViewById(R.id.cadastrar_usuario_input_layout_senha);
        inputConfirmarSenha = layout.findViewById(R.id.cadastro_usuario_input_layout_confirmar_senha);

        sexoMasculino = layout.findViewById(R.id.cadastrar_usuario_sexo_masc);
        sexoFeminino = layout.findViewById(R.id.cadastrar_usuario_sexo_fem);
        ensinoPrivado = layout.findViewById(R.id.cadastrar_usuario_rede_ensino_privado);
        ensinoPublico = layout.findViewById(R.id.cadastrar_usuario_rede_ensino_publico);

        /*checkBoxPrivado = layout.findViewById(R.id.checkBoxPrivadoId);
        checkBoxPublico = layout.findViewById(R.id.checkBoxPublicoId);*/

        /*checkBoxPrivado.setOnClickListener(onCheckedChangeListnerPrivado());
        checkBoxPublico.setOnClickListener(onCheckedChangeListnerPublico());*/


        botaoSalvar = layout.findViewById(R.id.botaoSalvar);
        botaoSalvar.setOnClickListener(onClickListenerSalvar());

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
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
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
                            cadastroUsuario.put("login", editTextLogin.getText().toString());
                            cadastroUsuario.put("senha", editTextSenha.getText().toString());

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


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
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

                                if (verificaSeExisteStringDeErro(json)){
                                    //TODO criar dialog aqui
                                    return;
                                }

                                //TODO chamar tela de login aqui



                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }.execute();


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


    private boolean verificaSeExisteStringDeErro(String json){
        return json.contains("error");
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

        if (editTextLogin.getText().toString().equals("")) {

            Toast.makeText(getActivity().getBaseContext(), "Campo Idade Vazio", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            chamarMenuFragment();
            return true;

        }

    }

    private boolean validarCampoNome() {
        if (editTextNome.getText().toString().equals("")) {
            Toast.makeText(getActivity().getBaseContext(), "Campo Nome Vazio", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            chamarMenuFragment();
            return true;

        }
    }

    private boolean validarCheckBoxPrivado() {

        /*if (checkBoxPrivado.isChecked()) {

            chamarMenuFragment();
            return true;

        } else {

            Toast.makeText(getActivity().getBaseContext(), "Caixa Rede de Ensino Vazia", Toast.LENGTH_SHORT).show();
            return false;

        }*/
        return true;

    }

    private boolean validarCheckBoxPublico() {
        /*if (checkBoxPublico.isChecked()) {
            chamarMenuFragment();
            return true;

        } else {

            Toast.makeText(getActivity().getBaseContext(), "Caixa Rede de Ensino Vazia", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

}
