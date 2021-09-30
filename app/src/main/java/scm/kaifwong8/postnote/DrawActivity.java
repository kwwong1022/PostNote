package scm.kaifwong8.postnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DrawActivity extends AppCompatActivity {
    private static final String TAG = "DrawActivity";

    private Drawing canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        initializeToolbar();

        // canvas
        View toolbar = findViewById(R.id.include);
        canvas = findViewById(R.id.canvas);
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
        ImageButton btnConnect = findViewById(R.id.img_connect);
        btnDraw.setAlpha(0);
        btnDraw.setEnabled(false);
        btnConnect.setAlpha(0);
        btnConnect.setEnabled(false);

        btnExit.setOnClickListener((v) -> {
            finish();
        });
    }
}