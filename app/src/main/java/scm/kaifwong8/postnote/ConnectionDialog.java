package scm.kaifwong8.postnote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ConnectionDialog extends AppCompatDialogFragment {
    private static final String TAG = "ConnectionDialog";

    EditText connectionId;
    ConnectionDialogListener dialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_connection_dialog, null);

        builder.setView(view)
                .setPositiveButton("Connect", (dialog, which) -> {
                    Log.d(TAG, "onCreateDialog: Connect, hostId is " + connectionId.getText().toString());
                    String id = connectionId.getText().toString();
                    dialogListener.applyTexts(id);

                    if (id.contains(" ")) {
                        Toast.makeText(getContext(), "id should not contain empty space", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getActivity(), MultiEditNoteActivity.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Log.d(TAG, "onCreateDialog: Cancel");
                });

        connectionId = view.findViewById(R.id.editText_connection_id);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogListener = (ConnectionDialogListener) context;
        } catch (Exception e) { e.printStackTrace(); }

    }

    public interface ConnectionDialogListener {
        void applyTexts(String connectionId);
    }
}
