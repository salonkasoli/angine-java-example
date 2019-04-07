package bindings;


import angine.util.IIdentifiable;
import generated.AST;

public class MySubject extends AST.Subject implements IIdentifiable {

    public MySubject(String role){
        this.role = role;
    }

    public String id() {
        return this.role;
    }
}
