package controllers;

import play.*;
import play.mvc.*;

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
        if (Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }

    /**
     * Aquí usamos la instrucción render("@form") que viene a ser una versión
     * simplificada de la instrucción render("Admin/form.html"). Esta
     * instrucción simplemente le dice a Play que utilice la plantilla por
     * defecto de la acción ‘form’.
     *
     * @param title
     * @param content
     * @param tags
     */
    public static void save(String title, String content, String tags) {
        // Create post
        User author = User.find("byEmail", Security.connected()).first();
        Post post = new Post(author, title, content);
        // Set tags list
        for (String tag : tags.split("\\s+")) {
            if (tag.trim().length() > 0) {
                post.tags.add(Tag.findOrCreateByName(tag));
            }
        }
        // Validate
        validation.valid(post);
        if (validation.hasErrors()) {
            render("@form", post);
        }
        // Save
        post.save();
        index();
    }

    public static void index() {
        String user = Security.connected();
        List<Post> posts = Post.find("author.email", user).fetch();
        render(posts);
    }

    /**
     * Primero necesitamos que Admin.form pueda además obtener los datos de un Post existente:
     * @param id 
     */
    public static void form(Long id) {
        if(id != null) {
            Post post = Post.findById(id);
            render(post);
        }
        render();
    }

    public static void save(Long id, String title, String content, String tags) {
        Post post;
        if(id == null) {
            // Create post
            User author = User.find("byEmail", Security.connected()).first();
            post = new Post(author, title, content);
        } else {
            // Retrieve post
            post = Post.findById(id);
            // Edit
            post.title = title;
            post.content = content;
            post.tags.clear();
        }
        // Set tags list
        for(String tag : tags.split("\\s+")) {
            if(tag.trim().length() > 0) {
                post.tags.add(Tag.findOrCreateByName(tag));
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
