package scm.kaifwong8.postnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";

    public static ArrayList<Note> localNoteDataSet;
    private RecyclerView recyclerView;
    private NoteAdapter.RecyclerViewClickListener recyclerViewClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // generate dummy note content
        seedNote();

        // setting up the recycleView
        setOnClickListener();
        recyclerView = findViewById(R.id.rv_notes);
        NoteAdapter adapter = new NoteAdapter(localNoteDataSet, recyclerViewClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void setOnClickListener() {
        recyclerViewClickListener = (v, position) -> {
            Intent i = new Intent(MainActivity.this, EditNoteActivity.class);
            i.putExtra(KEY_TITLE, localNoteDataSet.get(position).getTitle());
            i.putExtra(KEY_CONTENT, localNoteDataSet.get(position).getContent());
            startActivity(i);
        };
    }

    private void seedNote() {
        localNoteDataSet = new ArrayList<>();
        localNoteDataSet.add(new Note("Breakfast"));
        localNoteDataSet.add(new Note("To-Do"));
        localNoteDataSet.add(new Note("Assignments"));
        for (Note note:localNoteDataSet) {
            note.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur varius orci nec risus dapibus, id semper neque condimentum. Vivamus tempus lacus eu leo malesuada, vel pulvinar velit sodales. Nulla egestas lectus id tellus porta, id mattis mauris faucibus. Fusce luctus quam in lorem auctor ornare. Fusce in elementum libero, sit amet porta ligula. Suspendisse dictum lacus non elit dignissim, vel sodales dui pharetra. Donec viverra ac est a pretium. Proin id tortor id velit lobortis mollis ut quis velit. Praesent hendrerit finibus interdum. Aliquam luctus tempus sem eu tristique. Fusce imperdiet ex eget accumsan mattis. Vivamus aliquet dui id velit rhoncus cursus. Donec id hendrerit enim. Praesent eget vulputate neque. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.");
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}