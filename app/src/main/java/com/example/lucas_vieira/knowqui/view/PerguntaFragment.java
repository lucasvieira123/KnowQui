package com.example.lucas_vieira.knowqui.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas_vieira.knowqui.R;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
}
