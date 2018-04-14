package utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;

import com.example.lucas_vieira.knowqui.R;

/**
 * Created by lucas-vieira on 04/04/18.
 */

public abstract class AsynctaskWithProgress<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    Activity activity;
    CarregamentoDialog carregamentoDialog;

    public AsynctaskWithProgress(Activity activity) {
        carregamentoDialog = new CarregamentoDialog(activity);
        this.activity = activity;
    }

    @Override
    public Result doInBackground(Params[] params) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                carregamentoDialog.show();
            }
        });

        try {
            return doInBackgroundCustom(params);
        }catch (final Exception e){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    carregamentoDialog.dismiss();
                    onExceptionInBackGround(e);
                    cancel(true);
                }
            });

        }

        return null;

    }

    @Override
    public void onPostExecute(Result result) {
        carregamentoDialog.dismiss();
        onPostExecuteCustom(result);
    }

    public abstract Result doInBackgroundCustom(Params[] params) throws Exception;
    public abstract void onPostExecuteCustom(Result result);
    public abstract void onExceptionInBackGround (Exception e);
}
