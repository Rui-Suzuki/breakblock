package jp.ac.sangi.breakblock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);

        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.start();
    }

    @Override
    protected  void onPause() {
        super.onPause();
        gameView.stop();
    }
}
