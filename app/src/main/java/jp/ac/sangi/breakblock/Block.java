package jp.ac.sangi.breakblock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {
    private final float top;
    private final float left;
    private final float bottom;
    private final float right;
    private int hard;

    /*
    // コンストラクタ
    // 2018.09.19 R.Suzuki 新規作成
    */
    public Block(float top, float left, float bottom, float right) {
        this.top    = top;
        this.left   = left;
        this.bottom = bottom;
        this.right  = right;
        hard = 1;
    }

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
