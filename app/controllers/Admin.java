package controllers;
 
import play.*;
import play.mvc.*;
import play.data.validation.*;
 
import java.util.*;
 
import models.*;
 
/**
 * Hemos creado el área de administracion usando el módulo CRUD, pero todavía no
 * está bien integrada con la interfaz de usuario blog. Por lo que empezaremos a
 * trabajar en una nueva área de administración, la cual dará a cada autor
 * acceso a sus propias publicaciones (posts). Todas las funciones CRUD de
 * administración seguirán disponibles para los usuarios con perfil (profile) de
 * super administrador.
 *
 * @author arturo
 */
@With(Secure.class)
public class Admin extends Controller {
    
    /**
     * Interceptor para establecer los datos del usuario
     */
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }

    /**
     * Metodo index
     */
    public static void index() {
        List<Post> posts = Post.find("authorEmail", Security.connected()).asList();
        render(posts);
    }
    
    /**
     * 
     * @param id 
     */
    public static void form(Long id) {
        if(id != null) {
            Post post = Post.findById(id);
            render(post);
        }
        render();
    }
    
    /**
     * 
     * @param id
     * @param title
     * @param content
     * @param tags 
     */
    public static void save(Long id, String title, String content, String tags) {
        Post post;
        if(id == null) {
            // Create post
            User author = User.find("byEmail", Security.connected()).first();
            post = new Post(author, title, content);
        } else {
            // Retrieve post
            post = Post.findById(id);
            post.title = title;
            post.content = content;
            post.tags.clear();
        }
        // Set tags list
        for(String tag : tags.split("\\s+")) {
            if(tag.trim().length() > 0) {
                post.tagItWith(tag);
            }
        }
        // Validate
        validation.valid(post);
        if(validation.hasErrors()) {
            render("@form", post);
        }
        // Save
        post.save();
        index();
    }
    
}
