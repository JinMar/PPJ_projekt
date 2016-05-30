package cz.tul.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Marek on 11.05.2016.
 */
@Entity
@Document(collection = "Images")
@Table(name = "Images")
public class Images {
    @Id
    @Column(name = "ID_images")

    private String id;
    private String url;

    private String name;
    private Date createDate;
    private Date updateDate;
    @Column(name = "position")
    private int position;
    @Column(name = "like",columnDefinition = "int default 0")
    private int like;
    @Column(name = "dislike",columnDefinition = "int default 0")
    private int dislike;
    @Column(name = "tages")

    private String tagses ="";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "images")
    private Set<Comment> comments = new HashSet<>(0);
    @DBRef
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID")
    private Autor autor;

    public Images(String id, String url, String name, Date createDate, Date updateDate, Autor autor, int position) {
        this.id =id;
        this.url = url;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.autor = autor;
        this.position=position;
    }
    public Images() {

    }


    public String getTagses() {
        return tagses;
    }

    public void setTagses(String tagses) {
        this.tagses = tagses;
    }

    public int getPosition() {
        return position;
    }
    public void addTag(String tag){
        tagses += " "+tag;
    }


    public void setPosition(int position) {
        this.position = position;
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

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Autor getAutor() { return autor; }


    public Set<Comment> getComments() { return comments;}

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public void incrementLike(){
        like++;
    }
    public void incrementDisLike(){
        dislike++;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
