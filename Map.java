package assignment7;

import java.util.HashMap;

/**
 * This Map is just like a global variable.
 * It will store the username-password combinations of all users
 */
public class Map {
    public static HashMap<String, String> UsernamePasswordMap = new HashMap<>();

    public static void change(String username, String password) {
        UsernamePasswordMap.put(username, password);
    }
}
