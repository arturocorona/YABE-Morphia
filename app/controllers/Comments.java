package controllers;

import play.*;
import play.mvc.*;

/**
 * Controlador de Comentarios
 * @author arturo
 */
@Check("admin")
@With(Secure.class)
public class Comments extends CRUD {    
}