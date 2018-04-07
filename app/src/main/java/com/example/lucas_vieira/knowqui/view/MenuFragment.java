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

import com.example.lucas_vieira.knowqui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import custom.RequestAndResponseUrlConst;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.Pergunta;
import model.Resposta;
import utils.CarregamentoDialog;


public class MenuFragment extends Fragment {

    private TextView iniciarJogo;


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

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String jsonString = configurandoERequisitando();
                salvaNoBd(jsonString);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                carregamentoDialog.dismiss();
                chamarTelaDePergunta();
            }
        }.execute();
    }

    private String configurandoERequisitando() {

        URL url = null;
        try {
            url = new URL(RequestAndResponseUrlConst.LISTA_PERGUNTA);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        connection.setConnectTimeout(5000);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder resposta = new StringBuilder();

        while (scanner.hasNextLine()) {
            resposta.append(scanner.nextLine());
        }

        return resposta.toString();


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
