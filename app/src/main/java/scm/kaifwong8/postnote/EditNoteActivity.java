package scm.kaifwong8.postnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditNoteActivity extends AppCompatActivity implements RequestDialog.RequestDialogListener {
    private static final String TAG = "EditNoteActivity";

    public static ArrayList<Note> localNoteDataSet;
    private EditText noteContent;
    private boolean isNew = false;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        localNoteDataSet = new ArrayList<>();
        loadData();

        EditText noteTitle = findViewById(R.id.txt_noteTitle);
        TextView noteCreatedDate = findViewById(R.id.txt_lastModified);
        noteContent = findViewById(R.id.editText_noteContent);

        // Toolbar
        initializeToolbar();

        // get and set data
        Intent i = getIntent();
        // check if super activity intent to create new or edit note
        if (i.getBooleanExtra(MainActivity.KEY_IS_NEW, false)) {
            isNew = true;
            noteTitle.setText("");
            noteCreatedDate.setText(localNoteDataSet.get(localNoteDataSet.size()-1).getLastModified());
            noteContent.setText("");
        } else {
            pos = i.getIntExtra(MainActivity.KEY_NOTE_POSITION, 0);
            noteTitle.setText(localNoteDataSet.get(pos).getTitle());
            noteCreatedDate.setText(localNoteDataSet.get(pos).getLastModified());
            noteContent.setText(localNoteDataSet.get(pos).getContent());
        }
        // on title or content text change
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: triggered");
                localNoteDataSet.get(pos).setTitle(noteTitle.getText().toString());
                localNoteDataSet.get(pos).setContent(noteContent.getText().toString());
                saveData();
            }
        };
        noteTitle.addTextChangedListener(textWatcher);
        noteContent.addTextChangedListener(textWatcher);

        // trigger http request dialog
        FloatingActionButton requestButton = findViewById(R.id.btn_request);
        requestButton.setOnClickListener((v) -> {
            RequestDialog requestDialog = new RequestDialog();
            requestDialog.show(getSupportFragmentManager(), "request_dialog");
        });
    }

    private void initializeToolbar() {
        View toolbarContextForDrawActivity = findViewById(R.id.toolbar_context_draw);
        toolbarContextForDrawActivity.setAlpha(0);
        toolbarContextForDrawActivity.setEnabled(false);
        toolbarContextForDrawActivity.setTranslationX(400);
        ImageButton btnDraw = findViewById(R.id.img_draw);
        ImageButton btnExit = findViewById(R.id.img_exit);
        ImageButton btnDelete = findViewById(R.id.img_delete);
        btnDraw.setAlpha(0);
        btnDraw.setEnabled(false);

        btnExit.setOnClickListener((v) -> {
            Log.d(TAG, "initializeToolbar: btnExit clicked");
            finish();
        });

        btnDelete.setOnClickListener((v) -> {
            Log.d(TAG, "initializeToolbar: btnDelete clicked");
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: Yes");
                        if (isNew) {
                            localNoteDataSet.remove(localNoteDataSet.size()-1);
                        } else {
                            localNoteDataSet.remove(pos);
                        }
                        saveData();

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: No");
                    }).create();
            alertDialog.show();
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localNoteDataSet);
        editor.putString("note_list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MainActivity.KEY_NOTE_LIST, null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        localNoteDataSet = gson.fromJson(json, type);
    }

    /** get request here */
    @Override
    public void applyTexts(String url, String method) {
        Log.d(TAG, "applyTexts: url is" + url + ", method is " + method );

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { e.printStackTrace(); }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    EditNoteActivity.this.runOnUiThread(() -> noteContent.append(responseData));
                }
            }
        });
    }

    /** gesture function here */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}