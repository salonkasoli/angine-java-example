import angine.Decision;
import angine.PDP;
import angine.PIP;
import angine.context.RequestContext;
import angine.context.ResponseContext;
import angine.util.IIdentifiable;
import bindings.MyFactory;
import bindings.MySubject;
import bindings.MyUrlEntity;
import spark.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AccessController {

    private String pathToLua = "policy.lua";

    private String pathToEntities = "Entities.json";

    private String pathToSchema = "scheme.json";

    private PDP pdp;
    private PIP pip;

    public AccessController() throws Exception {
        String luaPolicy = readFile(pathToLua);
        pdp = new PDP(luaPolicy);
        String json  = readFile(pathToEntities);
        String schema = readFile(pathToSchema);
        pip = PIP.fromJson(schema, json, new MyFactory());
    }


    public boolean isAllowed(Request request){
        String role = "guest";
        if(request.cookie("role") != null){
            role = request.cookie("role");
        }
        RequestContext requestContext = new RequestContext(
                new MySubject(role),
                new ArrayList<IIdentifiable>(Collections.singletonList(
                        new MyUrlEntity(request.pathInfo())
                )),
                request.requestMethod()
        );
        ResponseContext responseContext = pdp.evaluate(pip.createContext(requestContext));
        if(responseContext.results.size() == 1 && responseContext.results.get(0).decision.equals(Decision.PERMIT)){
            return true;
        } else {
            return false;
        }
    }



    private String readFile(String path) {
        StringBuilder result = new StringBuilder("");
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream(path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            result.append(line).append("\n");
        }
        scanner.close();

        return result.toString();
    }
}
