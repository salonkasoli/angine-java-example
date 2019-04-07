package bindings;


import angine.util.IIdentifiable;
import generated.AST;

public class MyUrlEntity extends AST.UrlEntity implements IIdentifiable {

    public MyUrlEntity(String path){
        this.path = path;
    }

    public String id() {
        return this.path;
    }
}
