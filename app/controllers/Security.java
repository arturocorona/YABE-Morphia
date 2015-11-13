package controllers;

import models.*;

/**
 * El módulo proporciona un controlador llamado controllers.Secure que declara
 * todos los interceptores necesarios. Por supuesto, nosotros simplemente podría
 * heredar de este controlador, pero debido a que Java tan sólo soporta herencia
 * simple, este enfoque presentaría ciertas limitaicones.
 *
 * @author arturo
 */
public class Security extends Secure.Security {

    /**
     * Autentifica
     * @param username
     * @param password
     * @return 
     */
    static boolean authentify(String username, String password) {
        return User.connect(username, password) != null;
    }
    
    /**
     * Revisa un perfil
     * @param profile
     * @return 
     */
    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return User.find("byEmail", connected()).<User>first().isAdmin;
        }
        return false;
    }
    
    /**
     * Abre el index
     */
    static void onDisconnected() {
        Application.index();
    }
    
    /**
     * Redefine el metodo onAuthenticated
     */
    static void onAuthenticated() {
        Admin.index();
    }
    
}

