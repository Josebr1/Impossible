package casadocodigo.com.br.impossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by jose on 31/10/2016.
 *
 * - O objeto do tipo SurfaceView permite que desenhos sejam
 * executados sobre a superfície em vez de trabalhar com um XML
 * de apresentação.
 *
 * - Certificar de que a superfície ou tela já está preparada para receber
 * os desenhos. Para a verificação podemos utilizar o SurfaceHolder.
 *
 * - Para desenhar usaremos o Paint.
 */

public class Impossible extends SurfaceView implements Runnable{

    private boolean running = false;
    private Thread renderThread = null;
    private SurfaceHolder holder;
    private Paint paint;
    private int playerY = 300;
    private static final String TAG = "Game";


    public Impossible(Context context) {
        super(context);
        paint = new Paint();
        holder = getHolder();
    }

    @Override
    public void run() {
        while(running){
            Log.i(TAG, "Impossible Running...!");

            // Verifica se a tela já está pronta
            if(!holder.getSurface().isValid())
                continue;

            // Bloqueia o canvas
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            // Desnha o player
            drawPlayer(canvas);

            // Atualiza e libera o canvas
            holder.unlockCanvasAndPost(canvas);
        }

    }

    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    // Método para desenhar o player
    private void drawPlayer(Canvas canvas){
        paint.setColor(Color.GREEN);
        canvas.drawCircle(300, playerY, 50, paint);
    }

    // Método para mover o player para baixo
    public void moveDown(int pixels){
        playerY += pixels;
    }
}
