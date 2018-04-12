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
      chamarTelaDePergunta();

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

    @Override
    public void onResume() {
        super.onResume();
        iniciarJogo.performClick();
    }
}
