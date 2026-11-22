
package com.fiudeus.glyphsignal;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private enum TransmissionMode {
        FULL_MATRIX,
        SPLIT_SYMBOLS
    }

    private enum DemoSignal {
        DOT,
        DASH,
        PAUSE
    }

    private static final int MENU_FULL = 1;
    private static final int MENU_SPLIT = 2;

    private TransmissionMode mode = TransmissionMode.FULL_MATRIX;
    private DemoSignal signal = DemoSignal.DOT;

    private GlyphMatrixView matrixView;
    private TextView matrixStatus;
    private MaterialButton modeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        matrixView = findViewById(R.id.glyphMatrixView);
        matrixStatus = findViewById(R.id.matrixStatus);
        modeButton = findViewById(R.id.modeButton);

        modeButton.setOnClickListener(v -> showModeMenu());

        matrixView.setOnClickListener(v -> {
            switch (signal) {
                case DOT:
                    signal = DemoSignal.DASH;
                    break;
                case DASH:
                    signal = DemoSignal.PAUSE;
                    break;
                case PAUSE:
                    signal = DemoSignal.DOT;
                    break;
            }

            updatePreview();
        });

        updatePreview();
    }

    private void showModeMenu() {

        PopupMenu popup = new PopupMenu(
                this,
                modeButton,
                android.view.Gravity.NO_GRAVITY,
                0,
                R.style.GlyphSignalPopupMenu
        );

        popup.getMenu().add(
                Menu.NONE, MENU_FULL, 0, R.string.mode_full_menu
        );

        popup.getMenu().add(
                Menu.NONE, MENU_SPLIT, 1, R.string.mode_split_menu
        );

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_FULL) {
                mode = TransmissionMode.FULL_MATRIX;
                modeButton.setText(R.string.mode_full_matrix);
            } else if (item.getItemId() == MENU_SPLIT) {
                mode = TransmissionMode.SPLIT_SYMBOLS;
                modeButton.setText(R.string.mode_split_symbols);
            } else {
                return false;
            }

            signal = DemoSignal.DOT;
            updatePreview();
            return true;
        });

        popup.show();
    }

    private void updatePreview() {
        if (signal == DemoSignal.PAUSE) {
            matrixView.setFrame(GlyphFrames.off());
            matrixStatus.setText(R.string.preview_pause);
            return;
        }

        if (mode == TransmissionMode.FULL_MATRIX) {
            matrixView.setFrame(GlyphFrames.full());
        } else {
            boolean[][] frame = signal == DemoSignal.DOT
                    ? GlyphFrames.dot()
                    : GlyphFrames.dash();

            matrixView.setFrame(frame);
        }

        matrixStatus.setText(
                signal == DemoSignal.DOT
                        ? R.string.preview_dot
                        : R.string.preview_dash
        );
    }
}
