package casadocodigo.com.br.impossible;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    private int playerX = 300, playerY = 300, playerRadius = 50;
    private int enemyX, enemyY, enemyRadius = 50;
    private double distance;
    private boolean gameOver;
    private int score;
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
            //canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sky), 0, 0, null);

            // Desnha o player e o inimigo
            drawPlayer(canvas);
            drawEnemy(canvas);

            // Detecta colisão
            checkCollision(canvas);

            if(gameOver){
                stopGame(canvas);
                break;
            }

            // Atualiza o placar
            drawScore(canvas);

            // Restart e Exit
            drawButtons(canvas);

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
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.nave), playerX-50, playerY-50, null);
        //canvas.drawCircle(playerX, playerY, 50, paint);
    }

    // Método que desenha o inimigo
    private void drawEnemy(Canvas canvas){
        paint.setColor(Color.RED);
        enemyRadius++;
        canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
    }

    // Método para mover o player para baixo
    public void moveDown(int pixels){
        playerY += pixels;
    }

    // Método que verifica a colisão
    private void checkCollision(Canvas canvas){
        // Calcula a hipotenusa
        distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerX - enemyX, 2);
        distance = Math.sqrt(distance);

        // Verifica distancia entre os raios
        if(distance <= playerRadius + enemyRadius){
            gameOver = true;
        }
    }

    private void stopGame(Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(100);
        canvas.drawText("GAME OVER!", 50, 150, paint);
    }

    // Método de Pontos
    public void addScore(int points){
        score += points;
    }

    // Atualizamos o campo na tela com o valor do placar
    private void drawScore(Canvas canvas){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText(String.valueOf(score), 50, 200, paint);
    }

    // Botões de interface do usuário
    private void drawButtons(Canvas canvas){
        // Restart
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Restart", 50, 300, paint);

        // Exit
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Exit", 50, 500, paint);
    }

    public void init(){
        enemyX = enemyY = enemyRadius = 0;
        playerX = playerY = 300;
        playerRadius = 50;
        gameOver = false;
    }
}
