package cz.tul.pojo;

/**
 * Created by Marek on 29.05.2016.
 */
public class CommentDTO {

    private int likes;
    private int dislikes;
    private String text;
    private String id;

    public CommentDTO(int likes, int dislikes, String text, String id) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.text = text;
        this.id = id;
    }

    public CommentDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

