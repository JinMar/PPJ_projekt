package cz.tul.client;
import cz.tul.data.Autor;
import cz.tul.data.Comment;
import cz.tul.data.Images;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.UUID;

/**
 * Created by Marek on 31.05.2016.
 */
public interface ServerApi {
    public static final String AUTORS_PATH =         "/autor";
    public static final String AUTOR_PATH =          "/autor/{id}";
    public static final String AUTORUPDATE_PATH =    "/autor/update/{id}";
    public static final String AUTORDELETEPATH =     "/autor/delete/{id}";
    public static final String COMMENTS_PATH =       "/comment";
    public static final String COMMENT_PATH =        "/comment/{id}";
    public static final String COMMENTUPDATE_PATH =  "/comment/update/{id}";
    public static final String COMMENTLIKE_PATH =    "/comment/like/{id}";
    public static final String COMMENTDISLIKE_PATH = "/comment/dislike/{id}";
    public static final String COMMENTDELETE_PATH =  "/comment/delete/{id}";
    public static final String IMAGES_PATH =         "/images";
    public static final String IMAGE_PATH =          "/images/{id}";
    public static final String IMAGEUPDATE_PATH =    "/images/update/{id}";
    public static final String IMAGELIKE_PATH =      "/images/like/{id}";
    public static final String IMAGEDISLIKE_PATH =   "/images/dislike/{id}";
    public static final String IMAGETAG_PATH =       "/images/tag/{id}/{tag}";
    public static final String IMAGEDDELETE_PATH =   "/images/delete/{id}";

    //Autor

    @GET(AUTOR_PATH)
    public Autor getAutor(@Path("id") UUID id);

    @POST(AUTORS_PATH)
    public Autor saveAutor(@Body Autor autor);

    @PUT(AUTORUPDATE_PATH)
    public Autor updateAutor(@Body Autor autor, @Path("id") UUID id);

    @DELETE(AUTORDELETEPATH)
    public void deleteAutor(@Path("id") UUID id);

    //COMMENT

    @GET(COMMENT_PATH)
    public Comment getComment(@Path("id") UUID id) ;

    @POST(COMMENTS_PATH)
    public Comment saveComment(@Body Comment comment);

    @PUT(COMMENTUPDATE_PATH)
    public Comment updateComment(@Body Comment update, @Path("id") UUID id);

    @PUT(COMMENTLIKE_PATH)
    public Comment addLikeComment(@Path("id") UUID id);

    @PUT(COMMENTDISLIKE_PATH)
    public Comment addDisLikeComment(@Path("id") UUID id);

    @DELETE(COMMENTDELETE_PATH)
    public void deleteComment(@Path("id") UUID id);

    //IMAGES

    @GET(IMAGE_PATH)
    public Images getImage(@Path("id")  UUID id);

    @POST(IMAGES_PATH)
    public Images saveImage(@Body Images image);

    @PUT(IMAGEUPDATE_PATH)
    public Images updateImage(@Body  Images update, @Path("id")  UUID id);

    @PUT(IMAGELIKE_PATH)
    public Images updateImageAddLike(@Path("id")  UUID id) ;

    @PUT(IMAGEDISLIKE_PATH)
    public Images updateImageAddDisLike( @Path("id")  UUID id);

    @PUT(IMAGETAG_PATH)
    public Images updateImageAddTag(@Path("id") UUID id, @Path("tag")  String tag);

    @DELETE(IMAGEDDELETE_PATH)
    public void deleteImage(@Path("id") UUID id) ;
}
