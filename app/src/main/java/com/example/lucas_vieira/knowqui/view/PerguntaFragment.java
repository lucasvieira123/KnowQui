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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import custom.LetraDoItem;
import custom.RequestAndResponseUrlConst;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.DAO.UsuarioDAO;
import model.Pergunta;
import model.Resposta;
import model.Usuario;
import utils.AsynctaskWithProgress;
import utils.RequestAndResponseHelper;
import utils.WaitStopLogicOnTextView;

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
    private TextView cronometro;
    private Fragment thisFragment = this;
    private Pergunta perguntaAtual;

    private TextView ultimoItemSelecionado;
    private LetraDoItem letraSelecionada;
    private LetraDoItem letraDoItemCorreto;
    private WaitStopLogicOnTextView waitStopLogicOnTextView;


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

        ImageView imagemPular = layout.findViewById(R.id.pular_imageView);
        imagemPular.setOnClickListener(onClickListenerPularOuValidar());

        ImageView imagemValidar = layout.findViewById(R.id.validar_imageView);
        imagemValidar.setOnClickListener(onClickListenerPularOuValidar());

        return layout;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        requisitarPerguntaComSuasRespostasLimparBdESalvarBdEPreencherAView();



    }

    @SuppressLint("StaticFieldLeak")
    private void requisitarPerguntaComSuasRespostasLimparBdESalvarBdEPreencherAView() {
        final RequestAndResponseHelper perguntaComSuasRespostasReqAndRespHelper = new RequestAndResponseHelper();
        final String jsonPergunta = construirJsonPergunta();
        new AsynctaskWithProgress<String, Void, String>(getActivity()) {
            @Override
            public String doInBackgroundCustom(String[] uri) throws Exception {

                String jsonComPerguntaESuasRespostas = perguntaComSuasRespostasReqAndRespHelper
                        .setUri(uri[0])
                        .setJsonRequestString(jsonPergunta)
                        .getJson();

                return jsonComPerguntaESuasRespostas;
            }

            @Override
            public void onPostExecuteCustom(String jsonStringPerguntaComSuasRespostas) {
                if(jsonStringPerguntaComSuasRespostas == null){
                    return;
                }

                if(jsonStringPerguntaComSuasRespostas.contains("error")){
                    String message = getMessageJson(jsonStringPerguntaComSuasRespostas);

                    Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
                    chamarTelaAgradecimento();
                    return;
                }

                criarPerguntaComSuasRespostasLimparBDESalvarBD(jsonStringPerguntaComSuasRespostas);

                perguntaAtual = (PerguntaDAO.getInstance(getActivity())).getFirst();
                letraDoItemCorreto = getLetraDoItemCorreto();

                preencherViewComBaseNaPerguntaAtual();
            }

            public void onExceptionInBackGround(Exception e) {
                Toast.makeText(getActivity(), "Ocorreu um erro: " +e.getMessage(), Toast.LENGTH_LONG).show();
                chamarTelaMenu();
            }

        }.execute(RequestAndResponseUrlConst.LISTA_PERGUNTA);
    }



    private String getMessageJson(String jsonStringPerguntaComSuasRespostas) {
        String message = "";
        try {
            JSONArray jsonArray = new JSONArray(jsonStringPerguntaComSuasRespostas);
            JSONObject object = new JSONObject(jsonArray.getString(0));
            message = object.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    private LetraDoItem getLetraDoItemCorreto() {
        Resposta resposta = (RespostaDAO.getInstance(getActivity())).get("id_pergunta = %s and ehCorreta = %s", String.valueOf(perguntaAtual.getId()), "1");
        switch (resposta.getLetra()) {
            case "A":
                return LetraDoItem.levraA;
            case "B":
                return LetraDoItem.levraB;
            case "C":
                return LetraDoItem.levraC;
            case "D":
                return LetraDoItem.levraD;
        }
        return null;
    }

    private void preencherViewComBaseNaPerguntaAtual() {

        RespostaDAO respostaDAO = RespostaDAO.getInstance(getActivity());
        List<Resposta> respostasDaQuestaoAtual = respostaDAO.list("id_pergunta = %s", String.valueOf(perguntaAtual.getId()));


        textoTipo.setText(perguntaAtual.getTipo());
        textoPrincial.setText(perguntaAtual.getDescricao());


        if (perguntaAtual.getComplemento() != null) {
            textoSecundario.setVisibility(View.VISIBLE);
            textoSecundario.setText(perguntaAtual.getComplemento());
        } else {
            textoSecundario.setVisibility(View.GONE);
        }

        if (perguntaAtual.getDiretorioImagem() != null && perguntaAtual.getImagem() != null) {

            String imagemUrlString = perguntaAtual.getDiretorioImagem().concat(perguntaAtual.getImagem());

            try {
                URL imagemUrl = new URL(imagemUrlString);
                Picasso.get().load(imagemUrl.toString()).fit().into(imagemDaPergunta);
            } catch (MalformedURLException e) {
                Toast.makeText(getActivity(), "Problema ao carregar imagem", Toast.LENGTH_SHORT).show();
            }


            imagemDaPergunta.setVisibility(View.VISIBLE);

        } else {
            imagemDaPergunta.setVisibility(View.GONE);
        }

        itemA.setText(respostasDaQuestaoAtual.get(0).getDescricao());
        itemB.setText(respostasDaQuestaoAtual.get(1).getDescricao());
        itemC.setText(respostasDaQuestaoAtual.get(2).getDescricao());
        itemD.setText(respostasDaQuestaoAtual.get(3).getDescricao());

        int tempoEmMinutos = perguntaAtual.getTempo();

       waitStopLogicOnTextView =  new WaitStopLogicOnTextView(getActivity(),cronometro,tempoEmMinutos) {
            @Override
            protected void aoEsgotarTempo() {
                enviarRespostaRequisitarUmaNovaPerguntaLimparESalvarBDEPreencherTela(false);

            }
        }.start();
    }



    private void criarPerguntaComSuasRespostasLimparBDESalvarBD(String jsonStringPerguntaComSuasRespostas) {
        RespostaDAO respostaDAO = RespostaDAO.getInstance(getActivity());
        respostaDAO.removeAll();
        PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(getActivity());
        perguntaDAO.removeAll();

        JSONArray jsonArrayPerguntas = null;
        try {
            jsonArrayPerguntas = new JSONArray(jsonStringPerguntaComSuasRespostas);


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

    private View.OnClickListener onClickListenerPularOuValidar() {
        final boolean[] selecioandoPularPergunta = {true};
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.validar_imageView:
                        selecioandoPularPergunta[0] = false;
                        if(existeItemSelecionado()){
                            enviarRespostaRequisitarUmaNovaPerguntaLimparESalvarBDEPreencherTela(selecioandoPularPergunta[0]);
                        }else {
                            Toast.makeText(getActivity(),"Nenhum item selecionado",Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.pular_imageView:
                        enviarRespostaRequisitarUmaNovaPerguntaLimparESalvarBDEPreencherTela(selecioandoPularPergunta[0]);
                        break;


                }
            }
        };
    }

    private boolean existeItemSelecionado() {
        return letraSelecionada != null;
    }

    @SuppressLint("StaticFieldLeak")
    private void enviarRespostaRequisitarUmaNovaPerguntaLimparESalvarBDEPreencherTela(boolean foiPuladaAPergunta) {
        final Integer itemEstaCorretoInt;
        final Boolean itemEstaCorretoBoolean;
        final RequestAndResponseHelper respostaReqAndRespHelper = new RequestAndResponseHelper();

        if(foiPuladaAPergunta){
            itemEstaCorretoInt = 0;
        }else {

            itemEstaCorretoBoolean = estaCorreto(letraSelecionada);
            itemEstaCorretoInt = itemEstaCorretoBoolean ? 1:0;

        }



        new AsynctaskWithProgress<String, Void, String>(getActivity()) {
            @Override
            public String doInBackgroundCustom(String[] uri) throws Exception {
                String jsonRequestResposta = construirJsonResposta(itemEstaCorretoInt);
                String jsonResponseResposta = respostaReqAndRespHelper
                        .setUri(uri[0])
                        .setJsonRequestString(jsonRequestResposta)
                        .getJson();
                return jsonResponseResposta;

            }

            @Override
            public void onPostExecuteCustom(String jsonResponseResposta) {
                if(jsonResponseResposta.contains("sucess")){
                    Toast.makeText(getActivity(),"Enviado com sucesso",Toast.LENGTH_SHORT).show();
                    carregarNovaPerguntaEAtualizarTela();
                }else {
                    Toast.makeText(getActivity(),"Problema ao enviar resposta",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onExceptionInBackGround(Exception e) {

                Toast.makeText(getActivity(), "Ocorreu um erro: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }.execute(RequestAndResponseUrlConst.RESPONDER);

    }


    public String construirJsonResposta(int itemEstaCorretoInt) {
        Usuario usuarioLogado = (UsuarioDAO.getInstance(getActivity())).getFirst();

        JSONObject jsonRespostaRequest = new JSONObject();

        try {

            jsonRespostaRequest.put("id_pergunta", perguntaAtual.getId());
            jsonRespostaRequest.put("id_usuario", usuarioLogado.getId());
            jsonRespostaRequest.put("acertou", itemEstaCorretoInt);
            jsonRespostaRequest.put("data", getDataAtual());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonRespostaRequest.toString();
    }

    private String getDataAtual() {
        String data = "dd/MM/yyyy";

        String dataAtual;
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        dataAtual = formata.format(agora);
        return dataAtual;
    }

    public String construirJsonPergunta(){

        Usuario usuarioLogado = (UsuarioDAO.getInstance(getActivity())).getFirst();
        JSONObject jsonObjectRequestPergunta = new JSONObject();

        try {
            jsonObjectRequestPergunta.put("id_usuario", usuarioLogado.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObjectRequestPergunta.toString();

    }


    private boolean estaCorreto(LetraDoItem letraDoItemSelecionado) {

        if(letraDoItemSelecionado == null){
            return false;
        }else {
            return letraDoItemSelecionado.equals(letraDoItemCorreto);
        }

    }


    private void chamarTelaAgradecimento() {


        if(waitStopLogicOnTextView != null){
            waitStopLogicOnTextView.stop();
        }


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AgradecimentoFragment agradecimentoFragment = new AgradecimentoFragment();

        fragmentTransaction.replace(R.id.pergunta_layout_id,
                agradecimentoFragment,
                agradecimentoFragment.getClass().getSimpleName());

        fragmentTransaction.commitAllowingStateLoss();

    }

    private void chamarTelaMenu() {

        if(waitStopLogicOnTextView != null){
            waitStopLogicOnTextView.stop();
        }


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();

        fragmentTransaction.replace(R.id.layout_main,
                menuFragment,
                menuFragment.getClass().getSimpleName());

        fragmentTransaction.commitAllowingStateLoss();
    }

    private void carregarNovaPerguntaEAtualizarTela() {

        waitStopLogicOnTextView.stop();
        letraDoItemCorreto = null;
        letraSelecionada = null;

        getActivity().getFragmentManager()
                .beginTransaction()
                .detach(thisFragment)
                .attach(thisFragment)
                .commitAllowingStateLoss();
    }

    private View.OnClickListener onClickListenerItens() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.item_a:
                        if (!itemA.isSelected()) {
                            letraSelecionada = LetraDoItem.levraA;
                        }else {
                            letraSelecionada = null;
                        }
                        break;

                    case R.id.item_b:
                        if (!itemB.isSelected()) {
                            letraSelecionada = LetraDoItem.levraB;
                        }else {
                            letraSelecionada = null;
                        }
                        break;

                    case R.id.item_c:
                        if (!itemC.isSelected()) {
                            letraSelecionada = LetraDoItem.levraC;
                        }else {
                            letraSelecionada = null;
                        }
                        break;

                    case R.id.item_d:
                        if (!itemD.isSelected()) {
                            letraSelecionada = LetraDoItem.levraD;
                        }else {
                            letraSelecionada = null;
                        }
                        break;
                }

              coloreItemSelecionado(v);

            }
        };
    }

    private void coloreItemSelecionado(View v) {
        if (!v.isSelected()) {
            v.setSelected(true);
            if (ultimoItemSelecionado != null) {
                ultimoItemSelecionado.setSelected(false);
            }
            ultimoItemSelecionado = (TextView) v;

        } else {
            v.setSelected(false);
        }
    }


}
