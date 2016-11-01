package casadocodigo.com.br.impossible;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Game extends AppCompatActivity implements View.OnTouchListener{

    private Impossible view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lógica do jogo
        view = new Impossible(this);

        // Configura view
        setContentView(view);

        // Configura o toque na tela
        view.setOnTouchListener(this);

       /* /* Usar a tela cheia do aparelho
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getX() < 100 && event.getY() > 290 && event.getY() < 310){
            view.init();
        }

        // Exit
        if(event.getX() < 100 && event.getY() > 490 && event.getY() < 510){
            finish();
        }

        // Incrementa em 10 pixels a posição
        // Vertical do player e o placar
        view.moveDown(10);
        view.addScore(100);
        return true;
    }
}
