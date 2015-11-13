package models;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Lob;
import play.data.binding.As;
import play.data.validation.MaxSize;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import play.data.validation.Required;
import play.modules.morphia.Model;

/**
 * Clase-entidad encargada de representar cada entrada en el blog.
 *
 * @author arturo
 */
@Entity
public class Post extends Model {

    @Required
    public String title;

    @Required @As("yyyy-MM-dd")
    public Date postedAt;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Required
    public String authorEmail;

    @Reference
    public List<Comment> comments;

    public Set<String> tags = new HashSet<String>();

    /**
     * Método constructor para la clase {@link Post}
     * 
     * @param author
     * @param title
     * @param content 
     */
    public Post(User author, String title, String content) {
        this.comments = new ArrayList<Comment>();
        this.tags = new TreeSet();
        this.authorEmail = author.email;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }

    /**
     * Obtiene un autor
     * @return 
     */
    public User getAuthor() {
        return User.q("email", authorEmail).get();
    }

    /**
     * Agrega un comentario al post
     * @param author
     * @param content
     * @return 
     */
    public Post addComment(String author, String content) {
        /*
        Comment newComment = new Comment(this, author, content).save();
        this.comments.add(newComment);
        this.save();
         */
        new Comment(this, author, content).save();
        return this;
    }

    /**
     * Regresa el post anterior
     * @return 
     */
    public Post previous() {
        return Post.q().filter("postedAt <", postedAt).order("-postedAt").first();
    }

    /**
     * Regresa el siguiente post
     * @return 
     */
    public Post next() {
        return Post.q().filter("postedAt >", postedAt).order("postedAt").first();
    }

    /**
     * Agrega un tag
     * @param name
     * @return 
     */
    public Post tagItWith(String name) {
        tags.add(name);
        return this;
    }

    /**
     * Encuentra posts con un determinado tag
     * @param tag
     * @return 
     */
    public static List<Post> findTaggedWith(String tag) {
        return Post.q().filter("tags", tag).asList();
    }

    /**
     * Encuentra posts con unos determinados tags
     * @param tags
     * @return 
     */
    public static List<Post> findTaggedWith(String... tags) {
        return Post.q().filter("tags all", tags).asList();
    }

    /**
     * Regresa el titulo del post
     * @return 
     */
    @Override
    public String toString() {
        return title;
    }

    /**
     * Elimina comentarios
     */
    @OnDelete void cascadeDelete() {
        comments.stream().forEach((c) -> {
            c.delete();
        });
    }

}
