package com.fiudeus.glyphsignal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GlyphMatrixView matrixView = findViewById(R.id.glyphMatrixView);

        boolean[][] frame = new boolean[25][25];

        for (int x = 6; x <= 18; x++) {
            frame[12][x] = true;
        }

        matrixView.setFrame(frame);

        boolean[][][] frames = {
                GlyphFrames.dot(),
                GlyphFrames.dash(),
                GlyphFrames.full(),
                GlyphFrames.off()
        };

        final int[] index = {0};

        matrixView.setFrame(frames[index[0]]);

        matrixView.setOnClickListener(v -> {
            index[0] = (index[0] + 1) % frames.length;
            matrixView.setFrame(frames[index[0]]);
        });

    }
}