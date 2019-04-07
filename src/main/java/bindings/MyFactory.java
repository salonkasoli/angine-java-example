package bindings;


import angine.util.INodesDecoder;
import com.google.gson.JsonElement;
import generated.AST;
import generated.Decoder;

public class MyFactory extends Decoder implements INodesDecoder{
    public Object createEntity(JsonElement je) {
        return create(je, AST.Entity.class);
    }

    public Object createUrlEntity(JsonElement je) {
        return create(je,MyUrlEntity.class);
    }

    public Object createSubject(JsonElement je) {
        return create(je,MySubject.class);
    }
}
