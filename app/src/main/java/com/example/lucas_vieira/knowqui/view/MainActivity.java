package com.example.lucas_vieira.knowqui.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lucas_vieira.knowqui.R;
import com.facebook.stetho.Stetho;

import model.DAO.HistoricoDAO;
import model.DAO.LoginDAO;
import model.DAO.NivelDAO;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.DAO.TipoDAO;
import model.DAO.UsuarioDAO;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        startUpMethod(getBaseContext());
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CadastroFragment cadastroFragment = new CadastroFragment();

        fragmentTransaction.replace(R.id.layout_main,cadastroFragment,cadastroFragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }



    private static void startUpMethod(Context context) {
        //depois devo criar uma classe so para gerenciar isso
        // acredito que seja uma classe do tipo MyApplication extends Application

        HistoricoDAO historicoDAO = HistoricoDAO.getInstance(context);
//
//        for(int i = 0 ;i <5 ; i++){
//            Historico historicoTeste = new Historico();
//            historicoTeste.setAcertou(true);
//            historicoTeste.setData("04/03/2018");
//            historicoTeste.setDescricao("descricaoHistorico");
//            historicoTeste.setUsuario_id(1);
//            historicoDAO.add(historicoTeste);
//
//        }






        LoginDAO loginDAO = LoginDAO.getInstance(context);

        NivelDAO nivelDAO = NivelDAO.getInstance(context);

        PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(context);
        perguntaDAO.removeAll();

        RespostaDAO respostaDAO = RespostaDAO.getInstance(context);
        respostaDAO.removeAll();

        TipoDAO tipoDAO = TipoDAO.getInstance(context);

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(context);




    }
}
