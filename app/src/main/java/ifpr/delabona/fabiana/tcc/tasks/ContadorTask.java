package ifpr.delabona.fabiana.tcc.tasks;

import android.os.CountDownTimer;

import java.util.TimerTask;

/**
 * Created by wheezy on 20/09/17.
 */

public class ContadorTask extends CountDownTimer {

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */

    CountTimerListener listener;

    public ContadorTask() {
        //Valores em milisegundos
        super(60000, 1000);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (listener != null)
            listener.updateCounter(millisUntilFinished/1000);
    }

    public void setListener(CountTimerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFinish() {
        listener.updateCounter(0);
    }

    public interface CountTimerListener{
        void updateCounter(long remainingTime);
    }
}
