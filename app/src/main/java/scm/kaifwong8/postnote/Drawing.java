package scm.kaifwong8.postnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Drawing extends View {
    private static final String TAG = "Drawing";

    private boolean first;
    private boolean inSetting;
    private Paint p;
    private ArrayList<StrokePath> paths;
    private int currColor;

    public Drawing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.first = true;
        this.paths = new ArrayList<>();
        this.currColor = 6;
        this.p = new Paint();
        this.p.setStrokeWidth(3);
        this.p.setPathEffect(null);
        this.p.setColor(Color.BLACK);
        this.p.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (StrokePath path : paths) {
            canvas.drawPath(path.getPath(), path.getP());
        }

        // draw menu
        drawMenu(canvas);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "onTouchEvent: Up");
            this.first = true;

            if (event.getY() < (getHeight()-getHeight()/9)+getWidth()/22 && event.getY() > (getHeight()-getHeight()/9)-getWidth()/22) {
                Log.d(TAG, "onTouchEvent: menu clicked");

                // undo
                if (event.getX() < (getWidth()/8)+getWidth()/17 && event.getX() > (getWidth()/8)-getWidth()/17) {
                    paths.clear();
                }

                // color
                if (event.getX() < (getWidth()-((getWidth()/8) * 6f))+getWidth()/23 && event.getX() > (getWidth()-((getWidth()/8) * 6f))-getWidth()/23) {
                    currColor = 1;
                    p.setColor(Color.rgb(255, 153, 153));
                } else if (event.getX() < (getWidth()-((getWidth()/8) * 5f))+getWidth()/23 && event.getX() > (getWidth()-((getWidth()/8) * 5f))-getWidth()/23) {
                    currColor = 2;
                    p.setColor(Color.rgb(255, 153, 51));
                } else if (event.getX() < (getWidth()-((getWidth()/8) * 4f))+getWidth()/23 && event.getX() > (getWidth()-((getWidth()/8) * 5f))-getWidth()/23) {
                    currColor = 3;
                    p.setColor(Color.rgb(153, 255, 51));
                } else if (event.getX() < (getWidth()-((getWidth()/8) * 3f))+getWidth()/23 && event.getX() > (getWidth()-((getWidth()/8) * 5f))-getWidth()/23) {
                    currColor = 4;
                    p.setColor(Color.rgb(102, 204, 255));
                } else if (event.getX() < (getWidth()-((getWidth()/8) * 2f))+getWidth()/23 && event.getX() > (getWidth()-((getWidth()/8) * 5f))-getWidth()/23) {
                    currColor = 5;
                    p.setColor(Color.GRAY);
                } else if (event.getX() < (getWidth()-((getWidth()/8) * 1f))+getWidth()/23 && event.getX() > (getWidth()-((getWidth()/8) * 5f))-getWidth()/23) {
                    currColor = 6;
                    p.setColor(Color.BLACK);
                }
            }

        } else {
            Log.d(TAG, "onTouchEvent: Down");
            if (this.first) {
                Log.d(TAG, "onTouchEvent: added new path");
                paths.add(new StrokePath(new Path(), p));
                paths.get(paths.size() - 1).getPath().moveTo(event.getX(), event.getY());
                this.first = false;

            } else if (!this.first) {
                if (paths.get(paths.size() - 1) != null) {
                    paths.get(paths.size() - 1).getPath().lineTo(event.getX(), event.getY());
                }
            }
        }

        return true;
    }

    private void drawMenu(Canvas canvas) {
        Paint mP = new Paint();
        Paint mcP = new Paint();

        mP.setColor(Color.rgb(220, 220, 220));
        canvas.drawCircle(getWidth()/8, getHeight()-getHeight()/9, getWidth()/12, mP);
        canvas.drawCircle(getWidth()-(getWidth()/8), getHeight()-getHeight()/9, getWidth()/12, mP);
        canvas.drawRect(new RectF(getWidth()/8, getHeight()-getHeight()/9-getWidth()/12, getWidth()-(getWidth()/8), getHeight()-getHeight()/9+getWidth()/12), mP);
        mP.setColor(Color.rgb(245, 245, 245));
        canvas.drawCircle(getWidth()/8, getHeight()-getHeight()/9, getWidth()/17, mP);

        mcP.setColor(Color.BLACK);
        canvas.drawCircle(getWidth()-(getWidth()/8), getHeight()-getHeight()/9, getWidth()/22, mP);
        if (currColor == 6) {
            canvas.drawCircle(getWidth()-(getWidth()/8), getHeight()-getHeight()/9, getWidth()/23, mcP);
        } else {
            canvas.drawCircle(getWidth()-(getWidth()/8), getHeight()-getHeight()/9, getWidth()/26, mcP);
        }
        mcP.setColor(Color.GRAY);
        canvas.drawCircle(getWidth()-((getWidth()/8)*2f), getHeight()-getHeight()/9, getWidth()/22, mP);
        if (currColor == 5) {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 2f), getHeight() - getHeight() / 9, getWidth() / 23, mcP);
        } else {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 2f), getHeight() - getHeight() / 9, getWidth() / 26, mcP);
        }
        mcP.setColor(Color.rgb(102, 204, 255));
        canvas.drawCircle(getWidth()-((getWidth()/8)*3f), getHeight()-getHeight()/9, getWidth()/22, mP);
        if (currColor == 4) {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 3f), getHeight() - getHeight() / 9, getWidth() / 23, mcP);
        } else {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 3f), getHeight() - getHeight() / 9, getWidth() / 26, mcP);
        }
        mcP.setColor(Color.rgb(153, 255, 51));
        canvas.drawCircle(getWidth()-((getWidth()/8)*4f), getHeight()-getHeight()/9, getWidth()/22, mP);
        if (currColor == 3) {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 4f), getHeight() - getHeight() / 9, getWidth() / 23, mcP);
        } else {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 4f), getHeight() - getHeight() / 9, getWidth() / 26, mcP);
        }
        mcP.setColor(Color.rgb(255, 153, 51));
        canvas.drawCircle(getWidth()-((getWidth()/8)*5f), getHeight()-getHeight()/9, getWidth()/22, mP);
        if (currColor == 2) {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 5f), getHeight() - getHeight() / 9, getWidth() / 23, mcP);
        } else {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 5f), getHeight() - getHeight() / 9, getWidth() / 26, mcP);
        }
        mcP.setColor(Color.rgb(255, 153, 153));
        canvas.drawCircle(getWidth()-((getWidth()/8)*6f), getHeight()-getHeight()/9, getWidth()/22, mP);
        if (currColor == 1) {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 6f), getHeight() - getHeight() / 9, getWidth() / 23, mcP);
        } else {
            canvas.drawCircle(getWidth() - ((getWidth() / 8) * 6f), getHeight() - getHeight() / 9, getWidth() / 26, mcP);
        }
    }
}
