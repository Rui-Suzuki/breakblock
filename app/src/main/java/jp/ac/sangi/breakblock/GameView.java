package jp.ac.sangi.breakblock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import java.util.ArrayList;

public class GameView extends TextureView
        implements TextureView.SurfaceTextureListener,
        View.OnTouchListener {

    private Thread thread;  // ワーカースレッド
    volatile private boolean isRunnable;
    volatile private float touchedX;
    volatile private float touchedY;

    private ArrayList<Block> blockList; // ブロック管理用

    /**
     * コンストラクタ
     * @param context
     * 2018.09.14 R.Suzuki 新規作成
     */
    public GameView(Context context) {
        super(context);
        setSurfaceTextureListener(this);
        setOnTouchListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        readyObjects(width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        readyObjects(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            return true;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    /*
    // スレッドのスタート
    // 2018.09.19 R.Suzuki 新規作成
    */
    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.FILL);
                while(true) {
                    synchronized (GameView.this) {
                        if(!isRunnable) {
                            break;
                        }
                    }
                    Canvas canvas = lockCanvas();
                    if(canvas == null) {
                        continue;
                    }
                    canvas.drawColor(Color.BLACK);
                    for(Block item : blockList) {
                        item.draw(canvas, paint);
                    }
                    unlockCanvasAndPost(canvas);
                }

            }
        });
        isRunnable = true;
        thread.start();
    }

    /*
    // スレッドのストップ
    // 2018.09.19 R.Suzuki 新規作成
    */
    public void stop() {
        isRunnable = false;
    }

    @Override
    public  boolean onTouch(View v, MotionEvent event) {
        touchedX = event.getX();
        touchedY = event.getY();
        return true;
    }

    /*
    // オブジェクトの準備
    // 2018.09.26 R.Suzuki 新規作成
    */
    public void readyObjects(int width, int height) {
        float blockWidth = width / 10;
        float blockHeight = height / 20;

        blockList = new ArrayList<Block>();
        for(int i = 0; i < 100; i++) {
            float blockTop = i / 10 * blockHeight;
            float blockLeft = i % 10 * blockWidth;
            float blockBottom = blockTop + blockHeight;
            float blockRight = blockLeft + blockWidth;
            blockList.add(new Block(blockTop, blockLeft, blockBottom, blockRight));
        }
    }
}
