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
    @Column(name = "ID_images", columnDefinition = "BINARY(16)")
    private UUID id;
    private String url;
    @Column(name = "name")
    private String name;
    @Column(name = "createdate")

    private Date createdate;
    @Column(name = "updateDate")

    private Date updateDate;

    @Column(name = "like", columnDefinition = "int default 0")
    private int like;
    @Column(name = "dislike", columnDefinition = "int default 0")
    private int dislike;
    @Column(name = "tags")

    private String tags = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "images")
    private Set<Comment> comments = new HashSet<>(0);
    @DBRef
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID")
    private Autor autor;

    public Images(UUID id, String url, String name, Date createDate, Date updateDate, Autor autor) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.createdate = createDate;
        this.updateDate = updateDate;
        this.autor = autor;

    }

    public Images() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public void addTag(String tag) {
        tags += "|" + tag.substring(0, 16);
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


    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Autor getAutor() {
        return autor;
    }


    public Set<Comment> getComments() {
        return comments;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateDate(Date createDate) {
        this.createdate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void incrementLike() {
        like++;
    }

    public void incrementDisLike() {
        dislike++;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
