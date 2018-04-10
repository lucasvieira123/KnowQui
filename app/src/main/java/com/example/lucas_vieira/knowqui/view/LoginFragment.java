package com.example.lucas_vieira.knowqui.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

import model.DAO.UsuarioDAO;
import model.Login;
import model.Usuario;
import utils.CarregamentoDialog;

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

    @SuppressLint("StaticFieldLeak")
    private void logar() {
        final CarregamentoDialog carregamentoDialog = new CarregamentoDialog(getActivity());
        carregamentoDialog.show();

        new AsyncTask<Void, Void, HttpResponse>(){

            HttpClient cliente = new DefaultHttpClient();
            HttpResponse response;
            JSONObject login = new JSONObject();


            @Override
            protected HttpResponse doInBackground(Void... voids) {

                HttpPost post = new HttpPost("http://knowqui.com.br/logar");
                try {
                    login.put("login", loginEdtTxt.getText().toString());
                    login.put("senha", senhaEdtTxt.getText().toString());
                    StringEntity entity = new StringEntity(login.toString());
                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(entity);
                    response = cliente.execute(post);
                    System.out.println("reponse: " + response.toString());

                    return response;

                    /*if (response != null){
                        InputStream inputStream = response.getEntity().getContent();

                        String json = getStringFromInputStream(inputStream);
                        inputStream.close();
                        InserirUsuarioBDViaJson(json);

                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @SuppressLint("ShowToast")
            @Override
            protected void onPostExecute(HttpResponse httpResponse) {
                carregamentoDialog.dismiss();
                if (response != null){
                    try{
                        InputStream inputStream = response.getEntity().getContent();

                        String json = getStringFromInputStream(inputStream);
                        inputStream.close();
                        InserirUsuarioBDViaJson(json);
                    }catch (IOException e) {
                        Toast.makeText(getActivity(), "Ocorreu um erro: " + e.getMessage(), Toast.LENGTH_SHORT);
                    }

                    Toast.makeText(getActivity(), "Bem vindo!", Toast.LENGTH_SHORT);
                }
            }

        }.execute();


    }

    private void InserirUsuarioBDViaJson(String json) {
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(getActivity());
        try{
            JSONArray usuarioJson = new JSONArray(json);
            JSONObject usuarioJsonObj;

            for (int i = 0; i < usuarioJson.length(); i++){
                usuarioJsonObj = new JSONObject(usuarioJson.getString(i));

                Log.i("USUARIO", "InserirUsuarioBDViaJson: Usuario Encontrado" + usuarioJsonObj.toString());

                Usuario usuarioObj = new Usuario();
                usuarioObj.setNome(usuarioJsonObj.getString("nome"));
                usuarioObj.setId(usuarioJsonObj.getInt("id"));
                usuarioObj.setRede_ensino(usuarioJsonObj.getInt("rede_ensino"));
                usuarioObj.setLogin(usuarioJsonObj.getString("login"));
                usuarioObj.setSenha(usuarioJsonObj.getString("senha"));
                usuarioObj.setSexo(usuarioJsonObj.getString("sexo"));

                usuarioDAO.add(usuarioObj);

                Log.i("USUARIO", "InserirUsuarioBDViaJson: Usuario Adicionado");

            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void chamarTelaCadastro() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CadastroFragment cadastroFragment = new CadastroFragment();

        fragmentTransaction.replace(R.id.layout_main,cadastroFragment,cadastroFragment.getClass().getSimpleName());

        fragmentTransaction.commit();
    }

    //Converte objeto InputStream para String
    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}

