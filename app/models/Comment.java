package models;
 
import javax.persistence.Lob;
import play.data.validation.MaxSize;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import java.util.Date;
import play.data.validation.Required;
import play.modules.morphia.Model;
 
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
    
    @Required
    @Reference
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
    
    /**
     * Método sobreescrito que regresa una cadena con parte del comentario
     * @return 
     */
    @Override
    public String toString() {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }
    
    /**
     * Agrega comentarios a un post
     */
    @Added void cascadeAdd() {
        if (!post.comments.contains(this)) {
            post.comments.add(this);
            post.save();
        }
    }
 
}