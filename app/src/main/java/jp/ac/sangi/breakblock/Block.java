package jp.ac.sangi.breakblock;

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
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
}
