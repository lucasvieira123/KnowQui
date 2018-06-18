package com.example.lucas_vieira.knowqui.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lucas_vieira.knowqui.R;
import com.facebook.stetho.Stetho;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import model.DAO.HistoricoDAO;
import model.DAO.LoginDAO;
import model.DAO.NivelDAO;
import model.DAO.PerguntaDAO;
import model.DAO.RespostaDAO;
import model.DAO.TipoDAO;
import model.DAO.UsuarioDAO;
import model.Usuario;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        startUpMethod(getBaseContext());
        setContentView(R.layout.activity_main);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set the transparent color of the status bar, 20% darker
        tintManager.setTintColor(Color.parseColor("#20000000"));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.layout_main, loginFragment, loginFragment.getClass().getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();

    }


    private static void startUpMethod(Context context) {
        //depois devo criar uma classe so para gerenciar isso
        // acredito que seja uma classe do tipo MyApplication extends Application
        //Joao: Exatamente

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(context);

        usuarioDAO.removeAll();

        PerguntaDAO perguntaDAO = PerguntaDAO.getInstance(context);
        perguntaDAO.removeAll();

        RespostaDAO respostaDAO = RespostaDAO.getInstance(context);
        respostaDAO.removeAll();


    }

    @Override
    public void onBackPressed() {

        Fragment agradecimentoFragment = getFragmentManager().findFragmentByTag("RelatorioFragment");
        if(agradecimentoFragment != null && agradecimentoFragment.isVisible()){
            super.onBackPressed();
        }

        Fragment cadastroFragment = getFragmentManager().findFragmentByTag("CadastroFragment");

        if (cadastroFragment != null && cadastroFragment.isVisible()) {
            chamarTelaLogin();
            return;
        }

        Fragment menuFragment = getFragmentManager().findFragmentByTag("MenuFragment");

        if (menuFragment != null && menuFragment.isVisible()) {
            (UsuarioDAO.getInstance(this)).removeAll();
            chamarTelaLogin();
            return;
        }

        Fragment perguntaFragment = getFragmentManager().findFragmentByTag("PerguntaFragment");
        if (perguntaFragment != null && perguntaFragment.isVisible()) {
            return;
        }



        super.onBackPressed();


    }

    private void chamarTelaLogin() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();

        fragmentTransaction.replace(R.id.layout_main,
                loginFragment,
                loginFragment.getClass().getSimpleName());

        fragmentTransaction.commitAllowingStateLoss();
    }
}
