package jp.ac.sangi.breakblock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Pad implements DrawableItem {
    private final float top;    // 上
    private final float bottom; // 下
    private float left;          // 左
    private float right;         // 右

    /*
    // コンストラクタ
    // 2018.10.03 R.Suzuki 新規作成
    */
    public Pad(float top, float bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    /*
    // 左右移動
    // 2018.10.03 R.Suzuki 新規作成
    */
    public void setLeftRight(float left, float right) {
        this.left = left;
        this.right = right;
    }

    /*
    // 描画
    // 2018.10.03 R.Suzuki 新規作成
    */
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
