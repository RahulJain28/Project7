package assignment7;
/**
 * Project 7 Chat Room
 * EE 422C submission by
 * Aditya Kharosekar amk3587
 * Rahul Jain rj8656
 * Fall 2016
 * Slip days used - 1 (on this project) (overall - 2)
 * This is the second slip day that we have used this semester
 */
import java.util.HashMap;
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
