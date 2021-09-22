package scm.kaifwong8.postnote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        initializeToolbar();

        // canvas
        View toolbar = findViewById(R.id.include);
        Drawing canvas = findViewById(R.id.canvas);
        toolbar.setZ(2);
        canvas.setZ(1);
    }

    private void initializeToolbar() {
        View toolbarContextForNoteActivity = findViewById(R.id.toolbar_context_note);
        toolbarContextForNoteActivity.setAlpha(0);
        toolbarContextForNoteActivity.setEnabled(false);
        toolbarContextForNoteActivity.setTranslationX(400);
        ImageButton btnDraw = findViewById(R.id.img_draw);
        ImageButton btnExit = findViewById(R.id.img_draw_exit);
        btnDraw.setAlpha(0);
        btnDraw.setEnabled(false);

        btnExit.setOnClickListener((v) -> {
            finish();
        });
    }
}