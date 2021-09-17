package scm.kaifwong8.postnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditNoteActivity extends AppCompatActivity {
    private static final String TAG = "EditNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Toolbar
        initializeToolbar();

        TextView noteTitle = findViewById(R.id.txt_noteTitle);
        TextView noteCreatedDate = findViewById(R.id.txt_lastModified);
        EditText noteContent = findViewById(R.id.editText_noteContent);

        Intent i = getIntent();
        noteTitle.setText(i.getStringExtra(MainActivity.KEY_TITLE));
        noteCreatedDate.setText("17 Sept, 2021");
        noteContent.setText(i.getStringExtra(MainActivity.KEY_CONTENT));


    }

    private void initializeToolbar() {
        ImageButton btnConnect = findViewById(R.id.img_connect);
        ImageButton btnExit = findViewById(R.id.img_exit);
        ImageButton btnDelete = findViewById(R.id.img_delete);

        btnConnect.setAlpha(0);
        btnConnect.setEnabled(false);

        btnExit.setOnClickListener((v) -> {
            Log.d(TAG, "initializeToolbar: btnExit clicked");
            finish();
        });

        btnDelete.setOnClickListener((v) -> {
            Log.d(TAG, "initializeToolbar: btnDelete clicked");
        });
    }
}