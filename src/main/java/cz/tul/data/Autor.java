package cz.tul.data;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Marek on 11.05.2016.
 */
@Entity
@Document(collection = "Autor")
@Table(name = "Autor")
public class Autor {
    @Id
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;


    @Column(name = "createDate")
    private Date createdate;
    @Column(name = "Name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "autor")
    private Set<Images> images = new HashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "images")
    private Set<Comment> comments = new HashSet<>(0);

    public Autor() {


    }

    public Autor(UUID id, String name, Date createDate) {
        this.name = name;
        this.createdate = createDate;
        this.id = id;

    }


    public UUID getId() {
        return id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public String getName() {
        return name;
    }

    public Set<Images> getImages() {
        return images;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreatedate(Date createDate) {
        this.createdate = createDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
