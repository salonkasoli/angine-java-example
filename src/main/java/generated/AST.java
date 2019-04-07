package generated;

import java.util.List;

public class AST{

    public static abstract class Entity  {
    
        public  abstract String id();
        
    }
    
    public static abstract class UrlEntity extends Entity {
    
        public String path;
        
    }
    
    public static abstract class Subject extends Entity {
    
        public String role;
        
    }
    
}