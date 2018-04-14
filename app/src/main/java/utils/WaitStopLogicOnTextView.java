package utils;

import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lucas-vieira on 12/04/18.
 */

public abstract class WaitStopLogicOnTextView {
    private Activity activity;
    private int tempoEmMinutos;
    private TextView cronometro;
    private Timer timer;

    public WaitStopLogicOnTextView(Activity activity, TextView cronometro, int tempoEmMinutos) {
        this.cronometro = cronometro;
        this.tempoEmMinutos = tempoEmMinutos;
        this.activity = activity;
    }

    public WaitStopLogicOnTextView start(){
        final Integer[] tempoEmSegundos = {tempoEmMinutos * 60};

         timer = new Timer();
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tempoEmSegundos[0] == 0) {
                                aoEsgotarTempo();
                                cancel();
                                stop();


                            }

                            cronometro.setText(String.valueOf(stringTempoFormatado(tempoEmSegundos[0])));
                            tempoEmSegundos[0]--;

                        }
                    });

                }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return this;
    }

    protected abstract void aoEsgotarTempo();


    private String stringTempoFormatado(Integer tempoEmSegundos) {
        String minuto = String.valueOf(tempoEmSegundos / 60);
        String segundos = String.format("%02d", tempoEmSegundos % 60);


        return minuto + ":" + segundos;
    }

    public void stop(){
        timer.cancel();
        timer.purge();
    }
}
