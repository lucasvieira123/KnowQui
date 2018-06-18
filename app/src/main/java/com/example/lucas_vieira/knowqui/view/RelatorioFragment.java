package com.example.lucas_vieira.knowqui.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.lucas_vieira.knowqui.R;

import java.util.ArrayList;
import java.util.List;

import model.DAO.RelatorioDAO;
import model.Relatorio;
import model.Resposta;

/**
 * Created by lucas-vieira on 07/04/18.
 */

public class RelatorioFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relatorio_layout_fragment, container, false);
        RelatorioDAO relatorioDAO =RelatorioDAO.getInstance(getActivity());

        try {
            ArrayList<Relatorio> acertosFQ = (ArrayList<Relatorio>) relatorioDAO.list("tipo = %s and acertou = %s","'Quimica Geral'", "1");
            ArrayList<Relatorio> errosFQ = (ArrayList<Relatorio>) relatorioDAO.list("tipo = %s and acertou = %s","'Quimica Geral'", "0");
            ArrayList<Relatorio> acertosQG = (ArrayList<Relatorio>) relatorioDAO.list("tipo = %s and acertou = %s","'Físico-Química'", "1");
            ArrayList<Relatorio> errosQG = (ArrayList<Relatorio>) relatorioDAO.list("tipo = %s and acertou = %s","'Físico-Química'", "0");

            TextView view1 = view.findViewById(R.id.quantidade_acertos_fq);
            view1.setText(acertosFQ.size()+"");
            TextView view2 =  view.findViewById(R.id.quantidade_erros_fq);
            view2.setText(errosFQ.size()+"");

            TextView view3 = view.findViewById(R.id.quantidade_acertos_qg);
            view3.setText(acertosQG.size()+"");
            TextView view4 = view.findViewById(R.id.quantidade_erros_qg);
            view4.setText(errosQG.size()+"");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  view;
    }


    public void setFullscreen(Activity activity) {
        if (Build.VERSION.SDK_INT > 10) {
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;

            if (isImmersiveAvailable()) {
                flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }

            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        } else {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static boolean isImmersiveAvailable() {
        return android.os.Build.VERSION.SDK_INT >= 19;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFullscreen(getActivity());
    }
}
