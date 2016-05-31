package cz.tul.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Marek on 11.05.2016.
 */
@Entity
@Document(collection = "Comment")
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "ID_Coment")

    private String id;
    @Column(name = "Description")
    private String description;

    private Date createDate;
    @DBRef
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_images")
    private Images images;
    @DBRef
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Autor autor;
    @Column(name = "like", columnDefinition = "int default 0")
    private int like;
    @Column(name = "dislike", columnDefinition = "int default 0")
    private int dislike;

    public Comment(String id, String description, Date createDate, Images images, Autor autor) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.images = images;
        this.autor = autor;
    }

    public Comment() {

    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Images getImages() {
        return images;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void incrementLike() {
        like++;
    }

    public void incrementDisLike() {
        dislike++;
    }
}
