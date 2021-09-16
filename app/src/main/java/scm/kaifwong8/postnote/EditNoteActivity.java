package scm.kaifwong8.postnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        EditText noteContent = findViewById(R.id.editText_noteContent);

        Intent i = getIntent();
        noteContent.setText(i.getStringExtra(MainActivity.KEY_CONTENT));
    }
}