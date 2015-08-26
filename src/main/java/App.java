// import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args){
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      List<Cuisine> cuisines = Cuisine.all();

      model.put("cuisines", cuisines);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine newCuisine = new Cuisine(request.queryParams("newCuisine"));
      newCuisine.save();
      List<Cuisine> cuisines = Cuisine.all();
      model.put("cuisines", cuisines);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine deadCuisine = Cuisine.find(Integer.parseInt(request.queryParams("deleteCuisine")));
      deadCuisine.delete();

      List<Cuisine> cuisines = Cuisine.all();
      model.put("cuisines", cuisines);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/cuisines/:cuisine_id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      List<Restaurant> restaurants = cuisine.getRestaurants();

      model.put("restaurants", restaurants);
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines/:cuisine_id", (request, response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      Restaurant newRestaurant = new Restaurant(request.queryParams("newRestaurant"));
      newRestaurant.addCuisineId(cuisine.getCuisineId());
      newRestaurant.save();
      List<Restaurant> restaurants = cuisine.getRestaurants();

      model.put("cuisine", cuisine);
      model.put("restaurants", restaurants);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    get("/cuisines/:cuisine_id/:rest_id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String,Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));

      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines/:cuisine_id/delete", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Restaurant deadRest = Restaurant.find(Integer.parseInt(request.queryParams("deleteRest")));
      deadRest.delete();

      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      List<Restaurant> restaurants = cuisine.getRestaurants();

      model.put("cuisine", cuisine);
      model.put("restaurants", restaurants);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);

    }, new VelocityTemplateEngine());




  }
}
