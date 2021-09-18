package scm.kaifwong8.postnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditNoteActivity extends AppCompatActivity implements RequestDialog.RequestDialogListener {
    private static final String TAG = "EditNoteActivity";
    private RequestDialog.RequestDialogListener requestDialogListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        TextView noteTitle = findViewById(R.id.txt_noteTitle);
        TextView noteCreatedDate = findViewById(R.id.txt_lastModified);
        EditText noteContent = findViewById(R.id.editText_noteContent);

        // Toolbar
        initializeToolbar();

        // get and set data
        Intent i = getIntent();
        // SQL?
        noteTitle.setText(i.getStringExtra(MainActivity.KEY_TITLE));
        noteCreatedDate.setText("17 Sept, 2021");
        noteContent.setText(i.getStringExtra(MainActivity.KEY_CONTENT));

        // http request
        FloatingActionButton requestButton = findViewById(R.id.btn_request);
        requestButton.setOnClickListener((v) -> {
            RequestDialog requestDialog = new RequestDialog();
            requestDialog.show(getSupportFragmentManager(), "request_dialog");


        });
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
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Note")
                    .setMessage("Are you sure to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: Yes");
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: No");
                    }).create();
            alertDialog.show();
        });
    }

    @Override
    public void applyTexts(String url, String method) {
        Log.d(TAG, "applyTexts: url is" + url + ", method is " + method );

        /** get request here */

    }
}