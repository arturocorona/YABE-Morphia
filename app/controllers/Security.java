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
     * Como ya tenemos los objetos User como parte del modelo del blog, es fácil
     * implementar una versión de este método que valide el usuario y password
     * correctamente.
     *
     * @param username
     * @param password
     * @return
     */
    static boolean authenticate(String username, String password) {
        return User.connect(username, password) != null;
    }

    /**
     * La aplicación lo enviará de nuevo al formulario de inicio de sesión:
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

    /**
     * El módulo secure no sólo provee manejo de autenticación sino tambien
     * manejo de autorización, a través de los llamados perfiles (profiles).
     *
     * @param profile
     * @return
     */
    static boolean check(String profile) {
        if ("admin".equals(profile)) {
            return User.find("byEmail", connected()).<User>first().isAdmin;
        }
        return false;
    }

}
