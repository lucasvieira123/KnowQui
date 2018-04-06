package utils;

import android.os.AsyncTask;
import android.support.annotation.DrawableRes;

import com.example.lucas_vieira.knowqui.R;

/**
 * Created by lucas-vieira on 04/04/18.
 */

public class AsynctaskWithProgress<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    //todo proximo passo eh deixar o programador colocar seu layout perssonalizado
   // private Integer custonLayout;

    @DrawableRes
    int progressIcone;

    public AsynctaskWithProgress(@DrawableRes int progressIcone) {
        this.progressIcone = progressIcone;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Progress[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Result doInBackground(Params[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
    }
}
