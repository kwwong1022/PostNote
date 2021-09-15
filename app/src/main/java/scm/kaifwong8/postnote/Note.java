package scm.kaifwong8.postnote;

import java.util.Random;

public class Note {
    private final int id;
    private String title;
    private String content;

    Random rand = new Random();

    public Note() {
        this("undefined");
    }

    public Note(String title) {
        this.id = rand.nextInt(Integer.MAX_VALUE);
        this.title = title;
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
}
