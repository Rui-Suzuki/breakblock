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

    private Thread thread;                   // ワーカースレッド
    volatile private boolean isRunnable;   // 動作状態
    volatile private float touchedX;       // タッチ座標X
    volatile private float touchedY;       // タッチ座標Y

    private ArrayList<DrawableItem> blockList; // ブロック管理用

    private Pad pad;                        // パッド
    private float padHalfWidth;            // 横幅半分

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
    // 描画用スレッドのスタート
    // 2018.09.19 R.Suzuki 新規作成
    */
    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {   // スレッド生成
                Paint paint = new Paint();
                paint.setColor(Color.RED);          // 色は赤
                paint.setStyle(Paint.Style.FILL);   // 塗りつぶし
                while(true) {                       // メインループ(ワーカースレッド)
                    synchronized (GameView.this) { //
                        if(!isRunnable) {           // 動作中であれば
                            break;
                        }
                    }
                    Canvas canvas = lockCanvas();     // 画面を取得
                    if(canvas == null) {
                        continue;
                    }
                    canvas.drawColor(Color.BLACK);
                    float padLeft = touchedX - padHalfWidth;
                    float padRight = touchedX + padHalfWidth;
                    pad.setLeftRight(padLeft, padRight);
                    for(DrawableItem item : blockList) {   // ブロックを数分配置
                        item.draw(canvas, paint);
                    }
                    unlockCanvasAndPost(canvas);      // 画面に反映
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

        blockList = new ArrayList<DrawableItem>();
        for(int i = 0; i < 100; i++) {
            float blockTop = i / 10 * blockHeight;
            float blockLeft = i % 10 * blockWidth;
            float blockBottom = blockTop + blockHeight;
            float blockRight = blockLeft + blockWidth;
            blockList.add(new Block(blockTop, blockLeft, blockBottom, blockRight));
        }
        pad = new Pad(height * 0.8f, height * 0.85f);
        blockList.add(pad);
        padHalfWidth = width / 10;
    }
}
