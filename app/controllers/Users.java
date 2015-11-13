package controllers;

import play.*;
import play.mvc.*;

/**
 * Controlador de usuarios
 * @author arturo
 */
@Check("admin")
@With(Secure.class)
public class Users extends CRUD {    
    
}