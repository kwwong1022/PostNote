package scm.kaifwong8.postnote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RequestDialog extends AppCompatDialogFragment {
    private static final String TAG = "RequestDialog";
    private RequestDialogListener requestDialogListener;

    private EditText requestUrl;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_request_dialog, null);

        builder.setView(view)
                .setPositiveButton("Get", (dialog, which) -> {
                    Log.d(TAG, "onCreateDialog: Get");
                    String url = requestUrl.getText().toString();
                    requestDialogListener.applyTexts(url, "Get");
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Log.d(TAG, "onCreateDialog: Cancel");
                });

        requestUrl = view.findViewById(R.id.editText_request_url);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            requestDialogListener = (RequestDialogListener) context;
        } catch (Exception e) { e.printStackTrace(); }
    }

    public interface RequestDialogListener {
        void applyTexts(String url, String method);
    }
}
