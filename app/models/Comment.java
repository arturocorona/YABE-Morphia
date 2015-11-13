package models;
 
import java.util.*;
import javax.persistence.*;
import play.data.validation.MaxSize;
import play.data.validation.Required;
 
import play.db.jpa.*;
 
@Entity
/**
 *  Modelo para agregar comentarios a los mensajes
 */
public class Comment extends Model {
 
    @Required
    public String author;
    
    @Required
    public Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @ManyToOne
    @Required
    public Post post; 
    
    /**
     * Método constructor de Comment
     * @param post
     * @param author
     * @param content 
     */
    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
 
}