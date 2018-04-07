package com.example.lucas_vieira.knowqui.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.Pergunta;
import model.Resposta;

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
    private final static String TEXTO_TESTE = "Quem pretende acompanhar alguns jogos da Copa do Mundo na Rússia, entre 14 de junho e 15 de julho de 2018, já precisa começar a se planejar financeiramente. Apesar de a experiência ser inesquecível, os valores a serem desembolsados são muito altos e podem fazer muito torcedor fanático desistir - ou escolher outros destino.";
    List<Pergunta>perguntas;
    Integer indexQuestaoAtual = 0;
    Fragment thisFragment = this;

    private TextView ultimoItemSelecionado;

    /*todo verificar se não tem que ser fragment v4*/

    public PerguntaFragment() {
        PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(getActivity());
        perguntas =  perguntaDAO.list();

    }

    @Nullable
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

        Pergunta perguntaAtual = perguntas.get(indexQuestaoAtual);

        preencherViewComBaseNaPergunta(perguntaAtual);

//        String jgon = jsonTeste();

//        final CarregamentoDialog carregamentoDialog = new CarregamentoDialog(getActivity());
//        carregamentoDialog.show();
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//               String jsonString = configurandoERequisitando();
//                salvaNoBd(jsonString);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                carregamentoDialog.dismiss();
//            }
//        }.execute();

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




//
//        /*TESTES*/
//        //teste texto
//        textoPrincial.setText(TEXTO_TESTE);
//        textoSecundario.setText(TEXTO_TESTE);
//        itemA.setText(TEXTO_TESTE);
//        itemB.setText(TEXTO_TESTE);
//        itemC.setText(TEXTO_TESTE);
//        itemD.setText(TEXTO_TESTE);
//        //teste visibilidade
//        //imagemDaPergunta.setVisibility(View.GONE);
//        //textoSecundario.setVisibility(View.GONE);

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
                            aoEsgotaTempo();
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



//        /*TESTES*/
//        //teste texto
//        textoPrincial.setText(TEXTO_TESTE);
//        textoSecundario.setText(TEXTO_TESTE);
//        itemA.setText(TEXTO_TESTE);
//        itemB.setText(TEXTO_TESTE);
//        itemC.setText(TEXTO_TESTE);
//        itemD.setText(TEXTO_TESTE);
        //teste visibilidade
        //imagemDaPergunta.setVisibility(View.GONE);
        //textoSecundario.setVisibility(View.GONE);
    }

    private void aoEsgotaTempo() {
        if(indexQuestaoAtual==perguntas.size()-1){
            chamarTelaAgradecimento();
        }else {
            carregarNovaPergunta();
        }
    }

    private String stringTempoFormatado(Integer tempoEmSegundos) {
        String minuto = String.valueOf(tempoEmSegundos/60);
        String segundos = String.format("%02d",tempoEmSegundos%60);



        return minuto+":"+segundos;
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
        resposta1.setLetra("a");
        resposta1.setId_pergunta(1);

        Resposta resposta2 = new Resposta();
        resposta2.setDescricao("descricao2");
        resposta2.setEhCorreta(2);
        resposta2.setId(2);
        resposta2.setLetra("b");
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
                        //Toast.makeText(getActivity().getBaseContext(), "Pulado", Toast.LENGTH_SHORT).show();
                        if(indexQuestaoAtual==perguntas.size()-1){
                            chamarTelaAgradecimento();
                        }else {
                            carregarNovaPergunta();
                        }
                        break;

                    case R.id.validar_imageView:
                        if(indexQuestaoAtual==perguntas.size()-1){
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
        indexQuestaoAtual++;
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

    public class Wrapper{
        List<Contato> contatos;



    }

    public class Contato{
        String number;
        String title;
    }
}
