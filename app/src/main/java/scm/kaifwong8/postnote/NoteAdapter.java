package scm.kaifwong8.postnote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    // local data here
    private ArrayList<Note> noteDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // link view here
            title = itemView.findViewById(R.id.txt_noteTitle);
            content = itemView.findViewById(R.id.txt_noteContent);

            // define click listener for the viewHolder's view

        }
    }

    public NoteAdapter(ArrayList<Note> noteDataSet) {
        this.noteDataSet = noteDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(noteDataSet.get(position).getTitle());
        holder.content.setText(noteDataSet.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return noteDataSet.size();
    }
}
