package scm.kaifwong8.postnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class GestureDisplay extends View {
    private static final String TAG = "GestureDisplay";

    private PointF uiPos = new PointF();

    public GestureDisplay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.argb(100, 100, 100, 100));
        canvas.drawCircle(uiPos.x, uiPos.y, 500, p);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

}
