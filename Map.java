package assignment7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This Map is just like a global variable.
 * It will store the username-password combinations of all users
 */
public class Map {
    public static HashMap<String, String> UsernamePasswordMap = new HashMap<>();
    public static HashMap<String, Integer> nameID = new HashMap<>();
    static int id = 0; 
    public static void addOrChange(String username, String password) {
        UsernamePasswordMap.put(username, password);
        nameID.put(username, id);
        id++; 
    }
    
    public static int size(){
    	return UsernamePasswordMap.size();
    }
    
    public static Set getUsernames(){
    	return nameID.keySet(); 
    	
    }
    
    public static HashMap<String, Integer> getId(){
    	return nameID;
    }

}
