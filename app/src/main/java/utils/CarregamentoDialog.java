package utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.lucas_vieira.knowqui.R;

/**
 * Created by lucas-vieira on 05/04/18.
 */

public class CarregamentoDialog {

    private Context context;
    private int iconeDeCarregamentoRes;
    private Dialog dialog ;
    private ImageView iconeDeCarregamento;

    public CarregamentoDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.carregamento_dialog_layout);
        iconeDeCarregamento = dialog.findViewById(R.id.carregamento_imgVw);
        Animation animationRotacaoInfinita = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationRotacaoInfinita.setRepeatCount(Animation.INFINITE);
        animationRotacaoInfinita.setDuration(2500);
        iconeDeCarregamento.startAnimation(animationRotacaoInfinita);

    }

    public CarregamentoDialog(Context context, @DrawableRes int iconeDeCarregamentoRes) {
        //todo metodo nao implementado
        this.context = context;
        this.iconeDeCarregamentoRes = iconeDeCarregamentoRes;
    }

   public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }


}
