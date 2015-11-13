package models;
 
import java.util.*;
import javax.persistence.*;
import play.data.validation.Email;
import play.data.validation.Required;
 
import play.db.jpa.*;
 
/**
 * Clase-entidad para modelar la información de los usuarios.
 * @author arturo
 */
@Entity
public class User extends Model {

    @Email
    @Required
    public String email;
    
    @Required
    public String password;
    
    public String fullname;
    public boolean isAdmin;
    
    /**
     * Método constructor de un nuevo User
     * @param email
     * @param password
     * @param fullname 
     */
    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
    
    /**
     * Encuentra a un usuario por email y password
     * @param email
     * @param password
     * @return 
     */
    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public String toString() {
        return email;
    }
    
}