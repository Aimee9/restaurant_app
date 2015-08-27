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
    //Get index page. List of cuisines

    post("/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine newCuisine = new Cuisine(request.queryParams("newCuisine"));
      newCuisine.save();
      List<Cuisine> cuisines = Cuisine.all();
      model.put("cuisines", cuisines);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //post to index page, from index page

    post("/cuisines/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine deadCuisine = Cuisine.find(Integer.parseInt(request.queryParams("deleteCuisine")));
      deadCuisine.delete();

      List<Cuisine> cuisines = Cuisine.all();
      model.put("cuisines", cuisines);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //post to index page, from index page

    get("/cuisines/:cuisine_id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      List<Restaurant> restaurants = cuisine.getRestaurants();

      model.put("restaurants", restaurants);
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //get list of restaurants for a specific cuisine ID

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
      //post to list of restaurants for a specific cuisine ID

    get("/cuisines/:cuisine_id/restaurant/:rest_id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String,Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));
      String price = restaurant.getPrice();
      String hours = restaurant.getHours();

      model.put("hours", hours);
      model.put("price", price);
      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //look at a specific restaurant within a cuisine

    post("/cuisines/:cuisine_id/restaurant/:rest_id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));
      restaurant.update(request.queryParams("updatedName"));

      // String hours = restaurant.getHours();
      // String price = restaurant.getPrice();
      //
      // model.put("hours", hours);
      // model.put("price", price);
      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //post to restaurant page

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
    //delete rest


    get("/cuisines/:cuisine_id/restaurant/:rest_id/UpdateName", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));

      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/UpdateRestaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines/:cuisine_id/restaurant/:rest_id/AddHours", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/AddHours.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines/:cuisine_id/restaurant/:rest_id/hours", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));
      restaurant.addHours(request.queryParams("hours"));
      restaurant.updateHours();
      //
      String hours = restaurant.getHours();
      String price = restaurant.getPrice();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      
      model.put("price", price);
      model.put("hours", hours);
      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines/:cuisine_id/restaurant/:rest_id/AddPrice", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      String hours = restaurant.getHours();
      model.put("cuisine", cuisine);
      model.put("hours", hours);
      model.put("restaurant", restaurant);
      model.put("template", "templates/AddPrice.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines/:cuisine_id/restaurant/:rest_id/price", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":rest_id")));
      restaurant.addPrice(request.queryParams("price"));
      restaurant.updatePrice();

      String hours = restaurant.getHours();
      String price = restaurant.getPrice();

      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":cuisine_id")));
      model.put("hours", hours);
      model.put("price", price);
      model.put("cuisine", cuisine);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
}

}
