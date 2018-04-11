package com.example.lucas_vieira.knowqui.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import custom.RequestAndResponseUrlConst;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.DAO.UsuarioDAO;
import model.Pergunta;
import model.Resposta;
import model.Usuario;
import utils.CarregamentoDialog;


public class MenuFragment extends Fragment {

    private TextView iniciarJogo;
    private int contadorDeRequisicoesPosFalha = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        iniciarJogo = view.findViewById(R.id.textoIniciarId);
        iniciarJogo.setOnClickListener(onClickListener());

        return view;
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textoIniciarId:
                        onClickIniciarJogo();
                        break;
                }
            }
        };
    }

    private void onClickIniciarJogo() {
        //Todo metodo esta meio acoplado, melhorar
        requisitarPerguntasSalvarPerguntasEChamarTelaPerguntas();

    }

    @SuppressLint("StaticFieldLeak")
    private void requisitarPerguntasSalvarPerguntasEChamarTelaPerguntas() {
        final CarregamentoDialog carregamentoDialog = new CarregamentoDialog(getActivity());
        carregamentoDialog.show();



        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String jsonResponsePergunta = configurandoERequisitando();

                return jsonResponsePergunta;
            }

            @Override
            protected void onPostExecute(String jsonResponsePergunta) {
                carregamentoDialog.dismiss();
                if(jsonResponsePergunta == null || jsonResponsePergunta.isEmpty() || jsonResponsePergunta.equals("")){
                    Toast.makeText(getActivity().getBaseContext(),
                            "Problema ao carregar informações de Pergunta. \n Será requisitado Novamente!", Toast.LENGTH_LONG).show();
                    if(contadorDeRequisicoesPosFalha < 3){
                        requisitarPerguntasSalvarPerguntasEChamarTelaPerguntas();
                        contadorDeRequisicoesPosFalha++;
                    }else {
                        // foi requisitado mais de 3 vezes
                        Toast.makeText(getActivity().getBaseContext(),
                                "Estamos com problemas ao requisitar Perguntas. Por favor, saida do aplicativo e entre novamente!", Toast.LENGTH_LONG).show();
                    }


                }else {
                    salvaNoBd(jsonResponsePergunta);
                    chamarTelaDePergunta();
                    contadorDeRequisicoesPosFalha = 0;
                }


            }
        }.execute();
    }

    private String configurandoERequisitando() {

        Usuario usuarioLogado = (UsuarioDAO.getInstance(getActivity())).getFirst();

        HttpClient requestPergunta = new DefaultHttpClient();
        HttpResponse responsePergunta;
        JSONObject jsonObjectRequestPergunta = new JSONObject();
        JSONArray jsonArrayRequestPergunta = new JSONArray();

        HttpPost post = new HttpPost(RequestAndResponseUrlConst.LISTA_PERGUNTA);
        try {
            jsonObjectRequestPergunta.put("id_usuario", usuarioLogado.getId());
            //jsonArrayRequestPergunta.put(jsonObjectRequestPergunta);


            //StringEntity entity = new StringEntity(jsonArrayRequestPergunta.toString());
            StringEntity entity = new StringEntity(jsonObjectRequestPergunta.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(entity);
            responsePergunta = requestPergunta.execute(post);

            System.out.println("responsePergunta: " + responsePergunta.toString());

            InputStream inputStream = responsePergunta.getEntity().getContent();

            String json = getStringFromInputStream(inputStream);
            inputStream.close();

            return json;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }


//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id_usuario",1);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(jsonObject);
//
//        String jsonArrayString = jsonArray.toString();
//
//
//        URL url = null;
//        try {
//            url = new URL(RequestAndResponseUrlConst.LISTA_PERGUNTA);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        HttpURLConnection connection = null;
//        try {
//            connection = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection.setRequestMethod("POST");
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        }
//        connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
//        connection.setRequestProperty("Accept", "application/json");
//        connection.setDoOutput(true);
//        connection.setDoInput(true);
//
//        connection.setConnectTimeout(180000);
//        try {
//            connection.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        DataOutputStream os = null;
//        try {
//            os = new DataOutputStream(connection.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//        try {
//            os.writeBytes(jsonArrayString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Scanner scanner = null;
//        try {
//            scanner = new Scanner(url.openStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        StringBuilder resposta = new StringBuilder();
//
//        while (scanner.hasNextLine()) {
//            resposta.append(scanner.nextLine());
//        }
//
//        return resposta.toString();

        return null;
    }

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

    private void salvaNoBd(String jsonString) {
        RespostaDAO respostaDAO = RespostaDAO.getInstance(getActivity());
        PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(getActivity());

        JSONArray jsonArrayPerguntas = null;
        try {
            jsonArrayPerguntas = new JSONArray(jsonString);


            for (int i = 0; i < jsonArrayPerguntas.length(); i++) {
                JSONObject jsonObjectPergunta = (JSONObject) jsonArrayPerguntas.get(i);
                JSONObject jsonObjectPerguntaPropriamenteDito = (JSONObject) jsonObjectPergunta.get("pergunta");
                String idPergunta = jsonObjectPerguntaPropriamenteDito.getString("id");
                String descricaoPergunta = jsonObjectPerguntaPropriamenteDito.getString("descricao");
                Integer tempoPergunta = jsonObjectPerguntaPropriamenteDito.getInt("tempo");
                String imgPergunta = jsonObjectPerguntaPropriamenteDito.getString("img");
                String diretorioImgPergunta = jsonObjectPerguntaPropriamenteDito.getString("diretorioImg");
                String tipoPergunta = jsonObjectPerguntaPropriamenteDito.getString("tipo");
                String nivelPergunta = jsonObjectPerguntaPropriamenteDito.getString("nivel");
                String complementoPergunta = jsonObjectPerguntaPropriamenteDito.getString("complemento");


                Pergunta pergunta = new Pergunta();

                pergunta.setId(Integer.valueOf(idPergunta));
                pergunta.setDescricao(descricaoPergunta);
                pergunta.setTempo(tempoPergunta);
                pergunta.setImagem(imgPergunta);
                pergunta.setDiretorioImagem(diretorioImgPergunta);
                pergunta.setTipo(tipoPergunta);
                pergunta.setNivel(nivelPergunta);
                pergunta.setComplemento(complementoPergunta);

                perguntaDAO.add(pergunta);

                JSONArray jsonArrayResposta = jsonObjectPerguntaPropriamenteDito.getJSONArray("resposta");

                for (int j = 0; j < jsonArrayResposta.length(); j++) {
                    JSONObject jsonObjectRespostaPropriamenteDito = (JSONObject) jsonArrayResposta.get(j);
                    String idResposta = jsonObjectRespostaPropriamenteDito.getString("id");
                    String descricaoResposta = jsonObjectRespostaPropriamenteDito.getString("descricao");
                    Integer ehCorretaResposta = jsonObjectRespostaPropriamenteDito.getInt("ehCorreta");
                    String _indexResposta = jsonObjectRespostaPropriamenteDito.getString("_index");
                    String id_pergunta = jsonObjectRespostaPropriamenteDito.getString("id_pergunta");

                    Resposta resposta = new Resposta();
                    resposta.setId(Integer.valueOf(idResposta));
                    resposta.setDescricao(descricaoResposta);
                    resposta.setEhCorreta(ehCorretaResposta);
                    resposta.setLetra(_indexResposta);
                    resposta.setId_pergunta(Integer.valueOf(id_pergunta));
                    resposta.setLetra(_indexResposta);


                    respostaDAO.add(resposta);
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    private void chamarTelaDePergunta() {


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PerguntaFragment perguntaFragment = new PerguntaFragment();

        fragmentTransaction.replace(R.id.layout_main,
                perguntaFragment,
                perguntaFragment.getClass().getSimpleName());

        fragmentTransaction.commit();
    }
}
