package jp.ac.sangi.breakblock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

public class GameView extends TextureView
        implements TextureView.SurfaceTextureListener,
        View.OnTouchListener {

    private Thread thread;  // ワーカースレッド
    volatile private boolean isRunnable;
    volatile private float touchedX;
    volatile private float touchedY;

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
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return true;
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
                while(isRunnable) {
                    Canvas canvas = lockCanvas();
                    if(canvas == null) {
                        continue;
                    }
                    canvas.drawColor(Color.BLACK);
                    canvas.drawCircle(touchedX,touchedY,50, paint);
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
}
