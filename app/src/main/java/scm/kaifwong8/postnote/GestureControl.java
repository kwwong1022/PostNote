package scm.kaifwong8.postnote;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class GestureControl extends View {
    public GestureControl(Context context) {
        super(context);
    }

    public GestureControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        EditText editContent = ((EditNoteActivity)getContext()).findViewById(R.id.editText_noteContent);

        editContent.setZ(1);


        return super.onTouchEvent(event);
    }
}
