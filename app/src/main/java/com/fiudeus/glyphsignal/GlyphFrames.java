package com.fiudeus.glyphsignal;

import java.util.Arrays;

public final class GlyphFrames {

    public static final int SIZE = 25;
    private static final int CENTER = SIZE / 2;

    private GlyphFrames() {
    }

    public static boolean[][] off() {
        return new boolean[SIZE][SIZE];
    }

    public static boolean[][] dot() {
        boolean[][] frame = off();
        fillCircle(frame, CENTER, CENTER, 4);
        return frame;
    }

    public static boolean[][] dash() {
        boolean[][] frame = off();

        for (int y = CENTER - 2; y <= CENTER + 2; y++) {
            for (int x = CENTER - 9; x <= CENTER + 9; x++) {
                frame[y][x] = true;
            }
        }

        return frame;
    }

    public static boolean[][] full() {
        boolean[][] frame = off();

        for (boolean[] row : frame) {
            Arrays.fill(row, true);
        }

        return frame;
    }

    private static void fillCircle(boolean[][] frame, int centerX, int centerY, int radius) {
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                if (!isInsideFrame(x, y)) {
                    continue;
                }

                int dx = x - centerX;
                int dy = y - centerY;

                if (dx * dx + dy * dy <= radius * radius) {
                    frame[y][x] = true;
                }
            }
        }
    }

    private static boolean isInsideFrame(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }
}