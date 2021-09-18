package scm.kaifwong8.postnote;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Note {
    private static final String TAG = "Note";

    private final int id;
    private String title;
    private String content;
    private String lastModified;

    Random rand = new Random();

    public Note() {
        this("undefined");
    }

    public Note(String title) {
        this.id = rand.nextInt(Integer.MAX_VALUE);
        this.title = title;

        /** found on: https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android/15698784 */
        Date currDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(currDate);
        // Log.d(TAG, "Note: formattedDate - " + formattedDate);
        this.lastModified = formattedDate;
        this.title = this.title=="undefined"? "Note "+formattedDate:this.title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
