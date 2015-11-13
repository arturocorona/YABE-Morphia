package controllers;
 
import play.*;
import play.mvc.*;

/**
 *
 * @author arturo
 */
@Check("admin")
@With(Secure.class)
public class Tags extends CRUD {
    
}
