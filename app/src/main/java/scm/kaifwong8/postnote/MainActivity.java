package scm.kaifwong8.postnote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConnectionDialog.ConnectionDialogListener {

    private static final String TAG = "MainActivity";
    public static final String KEY_IS_NEW = "is_new_note";
    public static final String KEY_NOTE_POSITION = "note_position";
    public static final String KEY_NOTE_LIST = "note_list";

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

        // load dataSet
        localNoteDataSet = new ArrayList<>();
        loadData();
        // seedNote(3);

        // set recycleView
        recyclerView = findViewById(R.id.rv_notes);
        setRecyclerViewClickListener();
        setNoteAdapter();

        // set floating button
        FloatingActionButton btnNewNote = findViewById(R.id.btn_newNote);
        btnNewNote.setOnClickListener((v) -> {
            Log.d(TAG, "onCreate: btn_newNote clicked");

            Intent i = new Intent(MainActivity.this, EditNoteActivity.class);
            i.putExtra(KEY_IS_NEW, true);
            localNoteDataSet.add(new Note());
            saveData();
            startActivity(i);
        });
    }

    private void initializeToolbar() {
        View toolbarContextForNoteActivity = findViewById(R.id.toolbar_context_note);
        View toolbarContextForDrawActivity = findViewById(R.id.toolbar_context_draw);
        toolbarContextForNoteActivity.setAlpha(0);
        toolbarContextForNoteActivity.setEnabled(false);
        toolbarContextForNoteActivity.setTranslationX(400);   // prevent the imgBtn with higher z-index covers other imgBtn on the bottom
        toolbarContextForDrawActivity.setAlpha(0);
        toolbarContextForDrawActivity.setEnabled(false);
        toolbarContextForDrawActivity.setTranslationX(400);

        /** Multi User Connection Dialog */
        ImageButton btnDraw = findViewById(R.id.img_draw);
        btnDraw.setOnClickListener((v) -> {
            Log.d(TAG, "initializeToolbar: btnDraw clicked");

            Intent i = new Intent(MainActivity.this, DrawActivity.class);
            startActivity(i);
            // disabled function: connection
//            ConnectionDialog connectionDialog = new ConnectionDialog();
//            connectionDialog.show(getSupportFragmentManager(), "connection_dialog");
        });
    }

    @Override
    public void applyTexts(String connectionId) {
        this.hostId = connectionId;
    }

    private void setRecyclerViewClickListener() {
        recyclerViewClickListener = (v, position) -> {
            Log.d(TAG, "setRecyclerViewClickListener: clicked");
            Intent i = new Intent(MainActivity.this, EditNoteActivity.class);
            // create new note or edit note
            i.putExtra(KEY_IS_NEW, false);
            i.putExtra(KEY_NOTE_POSITION, position);
            startActivity(i);
        };

        recyclerViewLongClickListener = (v, position) -> {
            Log.d(TAG, "setRecyclerViewClickListener: " + localNoteDataSet.get(position).getTitle() + " - long clicked");
            AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Note")
                    .setMessage("Do you want to delete this note?\n" + localNoteDataSet.get(position).getTitle() + ", id: " + localNoteDataSet.get(position).getId())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Log.d(TAG, "setRecyclerViewClickListener: Yes");
                        localNoteDataSet.remove(position);
                        saveData();
                        setNoteAdapter();
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

    public void saveData() {
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
        String json = sharedPreferences.getString(KEY_NOTE_LIST, null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        localNoteDataSet = gson.fromJson(json, type);
    }

    /** refresh recyclerView after actions */
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        loadData();
        setNoteAdapter();
    }

    // debug function
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
        saveData();
    }

}