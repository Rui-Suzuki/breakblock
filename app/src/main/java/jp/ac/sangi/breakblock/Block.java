package jp.ac.sangi.breakblock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {
    private final float top;        // ブロック上
    private final float left;       // ブロック左
    private final float bottom;     // ブロック下
    private final float right;      // ブロック右
    private int hard;                // 硬さ

    /*
    // コンストラクタ 生成と同時に初期化する
    // 2018.09.19 R.Suzuki 新規作成
    */
    public Block(float top, float left, float bottom, float right) {
        this.top    = top;
        this.left   = left;
        this.bottom = bottom;
        this.right  = right;
        hard = 1;
    }

    /*
    // 描画
    // 2018.09.19 R.Suzuki 新規作成
    */
    public void draw(Canvas canvas, Paint paint) {
        if(0 < hard) { // 耐久が0以上
            // 塗りつぶし部分
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(left, top, right, bottom, paint);

            // 枠線部分を描画
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4f);
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }
}
