import java.util.HashMap;
import java.util.Map;

public class AuthController {

    private static Map<String, String> loginToPass = new HashMap<String, String>(){{
        put("user","user");
        put("admin","admin");
    }};


    public static boolean isValid(String name, String pass){
        String realPassword = loginToPass.get(name);
        if(realPassword != null && realPassword.equals(pass)){
            return true;
        } else {
            return false;
        }
    }

    public static String getRole(String username){
        return username;
    }
}
