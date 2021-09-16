package scm.kaifwong8.postnote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private static final String TAG = "NoteAdapter";
    private RecyclerViewClickListener recyclerViewClickListener;
    private RecyclerViewLongClickListener recyclerViewLongClickListener;

    // local data here
    private ArrayList<Note> noteDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, content;

        public ViewHolder(@NonNull View itemView) {   // or implement View.OnClickListener
            super(itemView);
            // link view here
            title = itemView.findViewById(R.id.txt_noteTitle);
            content = itemView.findViewById(R.id.txt_noteContent);

            // click listener here
            //itemView.setOnClickListener(this);
            itemView.setOnClickListener((v) -> {
                recyclerViewClickListener.onClick(v, getAdapterPosition());
            });

            itemView.setOnLongClickListener((v) -> {
                recyclerViewLongClickListener.onLongClick(v, getAdapterPosition());
                return true;
            });
        }

    }

    public NoteAdapter(ArrayList<Note> noteDataSet, RecyclerViewClickListener listener) {
        this.noteDataSet = noteDataSet;
        this.recyclerViewClickListener = listener;
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

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public interface RecyclerViewLongClickListener {
        void onLongClick(View v, int position);
    }
}
