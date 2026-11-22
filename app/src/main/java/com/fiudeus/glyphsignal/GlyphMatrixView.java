
package com.fiudeus.glyphsignal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GlyphMatrixView extends View {

    public static final int SIZE = 25;

    // Временная круглая маска матрицы.
    private static final int MASK_RADIUS_SQUARED = 153;

    private final boolean[][] pixels = new boolean[SIZE][SIZE];
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GlyphMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFrame(boolean[][] frame) {
        if (frame == null || frame.length != SIZE) {
            throw new IllegalArgumentException("Expected a 25x25 frame");
        }

        for (int y = 0; y < SIZE; y++) {
            if (frame[y] == null || frame[y].length != SIZE) {
                throw new IllegalArgumentException("Expected a 25x25 frame");
            }
        }

        for (int y = 0; y < SIZE; y++) {
            System.arraycopy(frame[y], 0, pixels[y], 0, SIZE);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cellSize = Math.min(getWidth(), getHeight()) / (float) SIZE;
        float offsetX = (getWidth() - cellSize * SIZE) / 2f;
        float offsetY = (getHeight() - cellSize * SIZE) / 2f;

        int activeColor = getContext().getColor(R.color.gs_text);
        int inactiveColor = getContext().getColor(R.color.gs_border);

        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {

                int dx = x - 12;
                int dy = y - 12;

                if (dx * dx + dy * dy > MASK_RADIUS_SQUARED) {
                    continue;
                }

                paint.setColor(pixels[y][x] ? activeColor : inactiveColor);

                float cx = offsetX + (x + 0.5f) * cellSize;
                float cy = offsetY + (y + 0.5f) * cellSize;

                canvas.drawCircle(cx, cy, cellSize * 0.32f, paint);
            }
        }
    }
}
