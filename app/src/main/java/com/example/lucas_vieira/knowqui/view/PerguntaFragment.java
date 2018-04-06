package com.example.lucas_vieira.knowqui.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import custom.RequestAndResponseUrlConst;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.Pergunta;
import model.Resposta;
import utils.CarregamentoDialog;

/**
 * Created by lucas-vieira on 09/03/18.
 */

public class PerguntaFragment extends Fragment {
    private TextView textoTipo;
    private TextView textoPrincial;
    private ImageView imagemDaPergunta;
    private TextView textoSecundario;
    private TextView itemA;
    private TextView itemB;
    private TextView itemC;
    private TextView itemD;
    private ImageView imagemPular;
    private ImageView imagemValidar;
    private final static String TEXTO_TESTE = "Quem pretende acompanhar alguns jogos da Copa do Mundo na Rússia, entre 14 de junho e 15 de julho de 2018, já precisa começar a se planejar financeiramente. Apesar de a experiência ser inesquecível, os valores a serem desembolsados são muito altos e podem fazer muito torcedor fanático desistir - ou escolher outros destino.";

    private TextView ultimoItemSelecionado;

    /*todo verificar se não tem que ser fragment v4*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pergunta_layout, container, false);

        textoTipo = layout.findViewById(R.id.texto_tipo);

        textoPrincial = layout.findViewById(R.id.texto_princial);

        imagemDaPergunta = layout.findViewById(R.id.imagem);

        textoSecundario = layout.findViewById(R.id.texto_secundario);

        itemA = layout.findViewById(R.id.item_a);
        itemA.setOnClickListener(onClickListenerItens());


        itemB = layout.findViewById(R.id.item_b);
        itemB.setOnClickListener(onClickListenerItens());

        itemC = layout.findViewById(R.id.item_c);
        itemC.setOnClickListener(onClickListenerItens());

        itemD = layout.findViewById(R.id.item_d);
        itemD.setOnClickListener(onClickListenerItens());

        imagemPular = layout.findViewById(R.id.pular_imageView);
        imagemPular.setOnClickListener(onClickListenerPularOuValidar());

        imagemValidar = layout.findViewById(R.id.validar_imageView);
        imagemValidar.setOnClickListener(onClickListenerPularOuValidar());

        return layout;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//        String jgon = jsonTeste();

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
            }
        }.execute();

//        Call<Pergunta[]> call = new RetrofitConfig().getPerguntaService().listaDePerguntas();
//        call.enqueue(new Callback<Pergunta[]>() {
//            @Override
//            public void onResponse(Call<Pergunta[]> call, Response<Pergunta[]> response) {
//                carregamentoDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<Pergunta[]> call, Throwable t) {
//                carregamentoDialog.dismiss();
//            }
//        });





        /*TESTES*/
        //teste texto
        textoPrincial.setText(TEXTO_TESTE);
        textoSecundario.setText(TEXTO_TESTE);
        itemA.setText(TEXTO_TESTE);
        itemB.setText(TEXTO_TESTE);
        itemC.setText(TEXTO_TESTE);
        itemD.setText(TEXTO_TESTE);
        //teste visibilidade
        //imagemDaPergunta.setVisibility(View.GONE);
        //textoSecundario.setVisibility(View.GONE);

    }

    private void salvaNoBd(String jsonString) {

        JSONArray jsonArrayPerguntas = null;
        try {
            jsonArrayPerguntas = new JSONArray(jsonString);


        for( int i =0; i< jsonArrayPerguntas.length(); i++) {
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

            PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(getActivity());
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

            for( int j =0; j<jsonArrayResposta.length();j++){
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
                resposta.setLetraResposta(_indexResposta);
                resposta.setId_pergunta(Integer.valueOf(id_pergunta));

                RespostaDAO respostaDAO = RespostaDAO.getInstance(getActivity());
                respostaDAO.add(resposta);
            }



        }



        } catch (JSONException e) {
                e.printStackTrace();
            }


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

        while (scanner.hasNext()) {
            resposta.append(scanner.next());
        }

        return resposta.toString();


    }

    private String jsonTeste() {
        List<Pergunta> perguntas = new ArrayList<>();

        Pergunta pergunta1 = new Pergunta();
        pergunta1.setComplemento("complemento");
        pergunta1.setDescricao("descricao");
        pergunta1.setDiretorioImagem("diretorioImagem");
        pergunta1.setId(1);
        pergunta1.setNivel("nivel");
        pergunta1.setImagem("imagem");
        pergunta1.setTempo(1);
        pergunta1.setTipo("tipo");
        perguntas.add(pergunta1);

        Pergunta pergunta2 = new Pergunta();
        pergunta2.setComplemento("complemento");
        pergunta2.setDescricao("descricao");
        pergunta2.setDiretorioImagem("diretorioImagem");
        pergunta2.setId(1);
        pergunta2.setNivel("nivel");
        pergunta2.setImagem("imagem");
        pergunta2.setTempo(1);
        pergunta2.setTipo("tipo");
        perguntas.add(pergunta2);

        Resposta resposta1 = new Resposta();
        resposta1.setDescricao("descricao1");
        resposta1.setEhCorreta(1);
        resposta1.setId(1);
        resposta1.setLetraResposta("a");
        resposta1.setId_pergunta(1);

        Resposta resposta2 = new Resposta();
        resposta2.setDescricao("descricao2");
        resposta2.setEhCorreta(2);
        resposta2.setId(2);
        resposta2.setLetraResposta("b");
        resposta2.setId_pergunta(2);

        List<Resposta> respostas = new ArrayList<>();
        respostas.add(resposta1);
        respostas.add(resposta2);

      //  pergunta1.set_respostas(respostas);
      //  pergunta2.set_respostas(respostas);


        Gson gson  = new Gson();

        return gson.toJson(perguntas.toArray(),Pergunta[].class);
    }

    private View.OnClickListener onClickListenerPularOuValidar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pular_imageView:
                        Toast.makeText(getActivity().getBaseContext(), "Pular", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.validar_imageView:
                        Toast.makeText(getActivity().getBaseContext(), "Validar", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private View.OnClickListener onClickListenerItens() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.item_a:
                        Toast.makeText(getActivity().getBaseContext(), "A", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item_b:
                        Toast.makeText(getActivity().getBaseContext(), "B", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item_c:
                        Toast.makeText(getActivity().getBaseContext(), "C", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item_d:
                        Toast.makeText(getActivity().getBaseContext(), "D", Toast.LENGTH_SHORT).show();
                        break;


                }

                if(!v.isSelected()){
                    v.setSelected(true);
                    if(ultimoItemSelecionado != null){
                        ultimoItemSelecionado.setSelected(false);
                    }
                    ultimoItemSelecionado = (TextView) v;

                }else {
                    v.setSelected(false);
                }

            }
        };
    }

    public class Wrapper{
        List<Contato> contatos;



    }

    public class Contato{
        String number;
        String title;
    }
}
