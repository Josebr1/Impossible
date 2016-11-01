package casadocodigo.com.br.impossible;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by jose on 31/10/2016.
 *
 * - O objeto do tipo SurfaceView permite que desenhos sejam
 * executados sobre a superfície em vez de trabalhar com um XML
 * de apresentação.
 *
 */

public class Impossible extends SurfaceView implements Runnable {

    private boolean running = false;
    private Thread renderThread = null;
    private static final String TAG = "Game";


    public Impossible(Context context) {
        super(context);
    }

    @Override
    public void run() {
        while(running){
            Log.i(TAG, "Impossible Running...!");
        }

    }

    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }
}
