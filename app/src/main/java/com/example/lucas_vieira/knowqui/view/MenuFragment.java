package com.example.lucas_vieira.knowqui.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.lucas_vieira.knowqui.R;



public class MenuFragment extends Fragment {

private TextView iniciarJogo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment,container,false);
        iniciarJogo = view.findViewById(R.id.textoIniciarId);
        iniciarJogo.setOnClickListener(onClickListener());

        return view;
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.textoIniciarId:
                        onClickIniciarJogo();
                        break;
                }
            }
        };
    }

    private void onClickIniciarJogo() {
    //  requisitarPerguntasEPreencherNoBanco();
      chamarTelaDePergunta();



    }

//    @SuppressLint("StaticFieldLeak")
//    private void requisitarPerguntasEPreencherNoBanco() {
//        new AsyncTask<String, Long, Void>() {
//
//            @Override
//            protected void onProgressUpdate(Long... values) {
//                super.onProgressUpdate(values);
//            }
//
//            @Override
//            protected Void doInBackground(String... url) {
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//            }
//
//        }.execute(RequestAndResponseUrlConst.LISTA_PERGUNTA);
//    }

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
