package scm.kaifwong8.postnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MultiEditNoteActivity extends AppCompatActivity {
    private static final String TAG = "MultiEditNoteActivity";

    private EditText noteContent;
    private String id;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_edit_note);

        EditText noteTitle = findViewById(R.id.txt_noteTitle);
        TextView noteSubtitle = findViewById(R.id.txt_lastModified);
        noteContent = findViewById(R.id.editText_noteContent);

        initializeToolbar();

        Intent i = getIntent();
        id = i.getStringExtra("id");

        noteTitle.setText(id);
        noteSubtitle.setText(new Note().getLastModified());

        // sync data
        getData();

        noteContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    sendData();
                } catch (Exception e) {
                    Log.e(TAG, "afterTextChanged: " + e);
                }
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    getData();
                } catch (Exception e) {
                    Log.e(TAG, "afterTextChanged: " + e);
                }
            }
        },10000, 10000);
    }

    private void initializeToolbar() {
        View toolbarContextForDrawActivity = findViewById(R.id.toolbar_context_draw);
        toolbarContextForDrawActivity.setAlpha(0);
        toolbarContextForDrawActivity.setEnabled(false);
        toolbarContextForDrawActivity.setTranslationX(400);
        ImageButton btnDraw = findViewById(R.id.img_draw);
        ImageButton btnExit = findViewById(R.id.img_exit);
        ImageButton btnDelete = findViewById(R.id.img_delete);
        ImageButton btnConnect = findViewById(R.id.img_connect);
        btnDraw.setAlpha(0);
        btnDraw.setEnabled(false);
        btnExit.setOnClickListener((v)->{finish();});
        btnDelete.setAlpha(0);
        btnDelete.setEnabled(false);
        btnConnect.setAlpha(0);
        btnConnect.setEnabled(false);
    }

    private void getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://enigma-324812.df.r.appspot.com/androidTest/r/" + id)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { e.printStackTrace(); }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    MultiEditNoteActivity.this.runOnUiThread(() -> {
                        noteContent.setText(responseData);
                    });
                }
            }
        });
    }

    private void sendData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://enigma-324812.df.r.appspot.com/androidTest/r/?id=" + id + "&updatedContent=" + noteContent.getText())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { e.printStackTrace(); }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: data sent");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}