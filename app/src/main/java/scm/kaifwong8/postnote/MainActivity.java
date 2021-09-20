package scm.kaifwong8.postnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConnectionDialog.ConnectionDialogListener {
    private static final String TAG = "MainActivity";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";

    public static ArrayList<Note> localNoteDataSet;
    private RecyclerView recyclerView;
    private NoteAdapter.RecyclerViewClickListener recyclerViewClickListener;
    private NoteAdapter.RecyclerViewLongClickListener recyclerViewLongClickListener;

    private String hostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization
        initializeToolbar();
        seedNote(3);

        // RecycleView
        recyclerView = findViewById(R.id.rv_notes);
        setRecyclerViewClickListener();
        setNoteAdapter();

        // Floating button
        FloatingActionButton btnNewNote = findViewById(R.id.btn_newNote);
        btnNewNote.setOnClickListener((v) -> {
            Log.d(TAG, "onCreate: btn_newNote clicked");

            Intent requestTest = new Intent(MainActivity.this, RequestTestingActivity.class);
            startActivity(requestTest);
        });
    }

    private void initializeToolbar() {
        View toolbarContextForNoteActivity = findViewById(R.id.toolbar_context_note);
        toolbarContextForNoteActivity.setAlpha(0);
        toolbarContextForNoteActivity.setEnabled(false);
        toolbarContextForNoteActivity.setTranslationX(400);   // prevent the imgBtn with higher z-index covers other imgBtn on the bottom

        /** Connection */
        ImageButton btnConnect = findViewById(R.id.img_connect);
        btnConnect.setOnClickListener((v) -> {
            Log.d(TAG, "initializeToolbar: btnConnect clicked");
            ConnectionDialog connectionDialog = new ConnectionDialog();
            connectionDialog.show(getSupportFragmentManager(), "connection_dialog");
        });
    }

    @Override   /** Connection */
    public void applyTexts(String connectionId) {
        this.hostId = connectionId;
    }

    private void setRecyclerViewClickListener() {
        recyclerViewClickListener = (v, position) -> {
            Log.d(TAG, "setRecyclerViewClickListener: clicked");
            Intent i = new Intent(MainActivity.this, EditNoteActivity.class);
            // SQL?
            i.putExtra(KEY_TITLE, localNoteDataSet.get(position).getTitle());
            i.putExtra(KEY_CONTENT, localNoteDataSet.get(position).getContent());
            startActivity(i);
        };

        recyclerViewLongClickListener = (v, position) -> {
            Log.d(TAG, "setRecyclerViewClickListener: " + localNoteDataSet.get(position).getTitle() + " - long clicked");
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Note")
                    .setMessage("Do you want to delete this note?\n" + localNoteDataSet.get(position).getTitle() + ", id: " + localNoteDataSet.get(position).getId())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: Yes");
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: No");
                    }).create();
            alertDialog.show();
        };
    }

    private void setNoteAdapter() {
        NoteAdapter adapter = new NoteAdapter(localNoteDataSet, recyclerViewClickListener, recyclerViewLongClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }












    /** Save and restore bundle */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void seedNote(int times) {
        localNoteDataSet = new ArrayList<>();
        for (int i=0; i<times; i++) {
            localNoteDataSet.add(new Note("Breakfast"));
            localNoteDataSet.add(new Note("To-Do"));
            localNoteDataSet.add(new Note("Assignments"));
        }

        for (Note note:localNoteDataSet) {
            note.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur varius orci nec risus dapibus, id semper neque condimentum. Vivamus tempus lacus eu leo malesuada, vel pulvinar velit sodales. Nulla egestas lectus id tellus porta, id mattis mauris faucibus. Fusce luctus quam in lorem auctor ornare. Fusce in elementum libero, sit amet porta ligula. Suspendisse dictum lacus non elit dignissim, vel sodales dui pharetra. Donec viverra ac est a pretium. Proin id tortor id velit lobortis mollis ut quis velit. Praesent hendrerit finibus interdum. Aliquam luctus tempus sem eu tristique. Fusce imperdiet ex eget accumsan mattis. Vivamus aliquet dui id velit rhoncus cursus. Donec id hendrerit enim. Praesent eget vulputate neque. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.");
        }
    }
}