package com.example.lucas_vieira.knowqui.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.squareup.picasso.Picasso;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import custom.RequestAndResponseUrlConst;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.DAO.UsuarioDAO;
import model.Pergunta;
import model.Resposta;
import model.Usuario;
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
    private TextView cronometro;
    Fragment thisFragment = this;
    private Pergunta perguntaAtual;
    private int contadorDeRequisicoesPosFalha = 0;
    private TextView ultimoItemSelecionado;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pergunta_layout, container, false);

        textoTipo = layout.findViewById(R.id.texto_tipo);

        textoPrincial = layout.findViewById(R.id.texto_princial);

        imagemDaPergunta = layout.findViewById(R.id.imagem);
        cronometro = layout.findViewById(R.id.cronometro);
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
        requisitarPerguntaSalvarPerguntaEPreencherAView();


    }

    private void preencherViewComBaseNaPergunta(Pergunta perguntaAtual) {
       RespostaDAO respostaDAO = RespostaDAO.getInstance(getActivity());
        List<Resposta> respostasDaQuestaoAtual = respostaDAO.list("id_pergunta = %s", String.valueOf(perguntaAtual.getId()));



       textoTipo.setText(perguntaAtual.getTipo());
       textoPrincial.setText(perguntaAtual.getDescricao());


       if(perguntaAtual.getComplemento() != null){
           textoSecundario.setVisibility(View.VISIBLE);
           textoSecundario.setText(perguntaAtual.getComplemento());
       }else {
           textoSecundario.setVisibility(View.GONE);
       }

       if(perguntaAtual.getDiretorioImagem() != null && perguntaAtual.getImagem()!= null){

           String imagemUrlString = perguntaAtual.getDiretorioImagem().concat(perguntaAtual.getImagem());

           try {
               URL imagemUrl = new URL(imagemUrlString);
               Picasso.get().load(imagemUrl.toString()).fit().into(imagemDaPergunta);
           } catch (MalformedURLException e) {
               Toast.makeText(getActivity(),"Problema ao carregar imagem", Toast.LENGTH_SHORT).show();
           }


           imagemDaPergunta.setVisibility(View.VISIBLE);

       }else {
           imagemDaPergunta.setVisibility(View.GONE);
       }

        itemA.setText(respostasDaQuestaoAtual.get(0).getDescricao());
        itemB.setText(respostasDaQuestaoAtual.get(1).getDescricao());
        itemC.setText(respostasDaQuestaoAtual.get(2).getDescricao());
        itemD.setText(respostasDaQuestaoAtual.get(3).getDescricao());
        Integer tempoEmMinutos = perguntaAtual.getTempo();

        final Integer[] tempoEmSegundos = {tempoEmMinutos * 60};

        Timer timer = new Timer();
        try{
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if(tempoEmSegundos[0] == 0){
                            aoEsgotarTempo();
                            cancel();
                        }

                       cronometro.setText(String.valueOf(stringTempoFormatado(tempoEmSegundos[0])));
                        tempoEmSegundos[0]--;

                    }
                });

            }
        },0,1000);}catch (Exception e){
            e.printStackTrace();
        }


    }

    private void aoEsgotarTempo() {
        if(respondeuTodasAsQuestoes()){
            chamarTelaAgradecimento();
        }else {
            carregarNovaPergunta();
        }
    }

    private boolean respondeuTodasAsQuestoes() {
        return perguntaAtual == null;
    }

    private String stringTempoFormatado(Integer tempoEmSegundos) {
        String minuto = String.valueOf(tempoEmSegundos/60);
        String segundos = String.format("%02d",tempoEmSegundos%60);


        return minuto+":"+segundos;
    }


    private View.OnClickListener onClickListenerPularOuValidar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pular_imageView:
                        //Toast.makeText(getActivity().getBaseContext(), "Pulado", Toast.LENGTH_SHORT).show();
                        if(perguntaAtual==null){
                            chamarTelaAgradecimento();
                        }else {
                            carregarNovaPergunta();
                        }
                        break;

                    case R.id.validar_imageView:
                        if(perguntaAtual==null){
                            chamarTelaAgradecimento();
                        }else {
                            carregarNovaPergunta();
                        }

                        break;
                }
            }
        };
    }

    private void chamarTelaAgradecimento() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AgradecimentoFragment agradecimentoFragment = new AgradecimentoFragment();

        fragmentTransaction.replace(R.id.pergunta_layout_id,
                agradecimentoFragment,
                agradecimentoFragment.getClass().getSimpleName());

        fragmentTransaction.commit();
    }

    private void carregarNovaPergunta() {
        getActivity().getFragmentManager()
                .beginTransaction()
                .detach(thisFragment)
                .attach(thisFragment)
                .commit();
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

    @SuppressLint("StaticFieldLeak")
    private void requisitarPerguntaSalvarPerguntaEPreencherAView() {
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
                        requisitarPerguntaSalvarPerguntaEPreencherAView();
                        contadorDeRequisicoesPosFalha++;
                    }else {
                        // foi requisitado mais de 3 vezes
                        Toast.makeText(getActivity().getBaseContext(),
                                "Estamos com problemas ao requisitar Perguntas. Por favor, saida do aplicativo e entre novamente!", Toast.LENGTH_LONG).show();
                    }


                }else {
                    salvaNoBd(jsonResponsePergunta);
                    contadorDeRequisicoesPosFalha = 0;
                }

                PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(getActivity());
                perguntaAtual = perguntaDAO.getFirst();

                preencherViewComBaseNaPergunta(perguntaAtual);


            }
        }.execute();
    }

    private String configurandoERequisitando() {

        Usuario usuarioLogado = (UsuarioDAO.getInstance(getActivity())).getFirst();

        HttpClient requestPergunta = new DefaultHttpClient();
        HttpResponse responsePergunta;
        JSONObject jsonObjectRequestPergunta = new JSONObject();

        HttpPost post = new HttpPost(RequestAndResponseUrlConst.LISTA_PERGUNTA);
        try {
            jsonObjectRequestPergunta.put("id_usuario", usuarioLogado.getId());

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


}
