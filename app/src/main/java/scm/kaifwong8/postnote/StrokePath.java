package scm.kaifwong8.postnote;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class StrokePath {
    private Path path;
    private Paint p;

    public StrokePath(Path path, Paint p) {
        this.path = path;
        this.p = new Paint();
        this.p.setStrokeWidth(3);
        this.p.setPathEffect(null);
        this.p.setColor(p.getColor());
        this.p.setStyle(Paint.Style.STROKE);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Paint getP() {
        return p;
    }

    public void setPaintColor(Paint p) {
        this.p.setColor(p.getColor());
    }
}
