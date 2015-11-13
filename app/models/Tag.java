package models;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase Tag
 * @author arturo
 */
public class Tag {
 
    /**
     * Regresa los tags de un post
     * @return 
     */
    public static Map<String, Long> getCloud() {
        return Post._cloud("tags");
    }
    
    /**
     * Regresa todos los tags
     * @return 
     */
    public static List<String> findAll() {
        return new ArrayList(Post._distinct("tags"));
    }
    
}