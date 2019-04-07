package generated;

import com.google.gson.*;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

/**
* To override this class, you must override every createClass(JsonElement je) method.
* The easiest way to do it is to call create(JsonElement je, Type class) method
* example:
* @Override
* public Object createClass(JsonElement je){
*   return create(je, MyClass.class);
* }
* With MyClass you must override Class.
*
*/
public abstract class Decoder{

    private Gson gson;

    public Decoder(){
        gson = new Gson();
    }

    public Map fromJson(String jsonString) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> result = new HashMap<String, Object>();
        JsonObject je = new JsonParser().parse(jsonString).getAsJsonObject();
        Set<String> keys = je.getAsJsonObject().keySet();
        for(String key : keys){
            JsonElement jsonElement = je.get(key);
            Object obj;
            if(jsonElement.isJsonArray()){
                obj = readArray(jsonElement.getAsJsonArray());
            } else {
                obj = readObject(jsonElement);
            }
            result.put(key,obj);
        }
        return result;
    }

    private Object readArray(JsonArray array) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       List<Object> res = new ArrayList<Object>();
        for(JsonElement element : array){
            res.add(readObject(element));
        }
        return res;
    }


    @Nullable
    private Object readObject(JsonElement element){
        String str = element.getAsJsonObject().get("type").getAsString();
        Object currentObject = null;

        if(str.equals("entity")){
            currentObject = createEntity(element);    
        }
        
        if(str.equals("urlentity")){
            currentObject = createUrlEntity(element);    
        }
        
        if(str.equals("subject")){
            currentObject = createSubject(element);    
        }
        return currentObject;

    }


    public abstract Object createEntity(JsonElement je);
    
    public abstract Object createUrlEntity(JsonElement je);
    
    public abstract Object createSubject(JsonElement je);
    
    public Object create(JsonElement je, Type type){
        return gson.fromJson(je, type);
    }
}