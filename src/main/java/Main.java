import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    private static AccessController accessController;

    public static void main(String[] args) {

        port(31337);

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/index", new TemplateViewRoute() {
            public ModelAndView handle(Request request, Response response) throws Exception {
                Map<String, Object> model = new HashMap<String, Object>();
                String role = request.cookie("role");
                if(role != null){
                    model.put("role", role);
                } else {
                    model.put("role", "stranger");
                }
                return new ModelAndView(model, "templates/index.vm");
            }
        }, velocityTemplateEngine);

        get("/login", new TemplateViewRoute() {
            public ModelAndView handle(Request request, Response response) throws Exception {
                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("message","");
                    if(request.cookie("isWrongLogin") != null){
                        model.put("message","Неправильный логин или пароль");
                        response.removeCookie("isWrongLogin");
                    }
                    return new ModelAndView(model, "templates/login.vm"); // located in the resources directory
            }
        }, velocityTemplateEngine);


        post("auth", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                if(AuthController.isValid(request.queryParams("username"), request.queryParams("password"))){
                    response.cookie("role",AuthController.getRole(request.queryParams("username")),60*30);
                    response.redirect("/index");
                } else {
                    response.cookie("isWrongLogin","true");
                    response.redirect("/login");
                }
                return "Перенаправляем Вас на главную";
            }
        });

        get("/admin", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                return "Welcome to admin panel ;]";
            }
        });

        before("/", new Filter() {
            public void handle(Request request, Response response) throws Exception {
                response.redirect("/index");
            }
        });

        before(new Filter() {
            public void handle(Request request, Response response) throws Exception {
                if(!accessController.isAllowed(request)){
                    halt(403, "you are not welcome here, go away!");
                }
            }
        });

        get("/stop", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                stop();
                return "stopped";
            }
        });


        try {
            accessController = new AccessController();
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }
}
