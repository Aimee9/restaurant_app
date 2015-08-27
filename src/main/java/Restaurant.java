import org.sql2o.*;
import java.util.List;

public class Restaurant{
  private int rest_id;
  private int cuisine_id;
  private String rest_name;
  private String price;
  private String hours;

  public Restaurant(String rest_name){
    this.rest_name = rest_name;
    price = null;
    hours = null;
  }

  public int getRestId(){
    return rest_id;
  }

  public int getCuisineId(){
    return cuisine_id;
  }

  public String getRestName(){
    return rest_name;
  }

  public String getPrice(){
    return price;
  }

  public String getHours(){
    return hours;
  }


  @Override
  public boolean equals(Object otherRestaurantInstance) {
    if (!(otherRestaurantInstance instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurantInstance = (Restaurant) otherRestaurantInstance;
      return this.getRestName().equals(newRestaurantInstance.getRestName()) &&
             this.getRestId() == newRestaurantInstance.getRestId() &&
             this.getCuisineId() == newRestaurantInstance.getCuisineId();
    }
  }



  public void addCuisineId(int cuisine_id){
    this.cuisine_id = cuisine_id;
    String sql = "UPDATE restaurants SET cuisine_id = :cuisine_id WHERE rest_id = :rest_id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
        .addParameter("cuisine_id", cuisine_id)
        .addParameter("rest_id", rest_id)
        .executeUpdate();
    }
  }

  public void addPrice(String price){
    this.price = price;
    String sql = "UPDATE restaurants SET price = :price WHERE rest_id = :rest_id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
        .addParameter("price", price)
        .addParameter("rest_id", rest_id)
        .executeUpdate();
    }
  }

  public void addHours(String hours){
    this.hours = hours;
    String sql = "UPDATE restaurants SET hours = :hours WHERE rest_id = :rest_id";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
        .addParameter("hours", hours)
        .addParameter("rest_id", rest_id)
        .executeUpdate();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO restaurants (rest_name, cuisine_id, price, hours) VALUES (:rest_name, :cuisine_id, :price, :hours)";
      this.rest_id = (int)con.createQuery(sql, true)
        .addParameter("rest_name", rest_name)
        .addParameter("cuisine_id", cuisine_id)
        .addParameter("price", price)
        .addParameter("hours", hours)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Restaurant> all() {
    String sql = "SELECT * FROM restaurants";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }


  // public static List<Restaurant> findMulti(int cuisine_id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM restaurants where cuisine_id = :cuisine_id";
  //     List<Restaurant> rest = con.createQuery(sql)
  //       .addParameter("cuisine_id", cuisine_id)
  //       .executeAndFetch(Restaurant.class);
  //     return rest;
  //   }
  // }

  // public static boolean hoursAdded(Restaurant restaurant){
  //   if( restaurant.getHours() == null){
  //     return true;
  //   } else {
  //   return false;
  //   }
  // }

  public static Restaurant find(int rest_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants where rest_id = :rest_id";
      Restaurant rest = con.createQuery(sql)
        .addParameter("rest_id", rest_id)
        .executeAndFetchFirst(Restaurant.class);
      return rest;
    }
  }

  public void update(String newRestName) {
    rest_name = newRestName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET rest_name = :rest_name WHERE rest_id=:rest_id";
      con.createQuery(sql)
        .addParameter("rest_name", rest_name)
        .addParameter("rest_id", rest_id)
        .executeUpdate();
    }
  }



  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM restaurants WHERE rest_id = :rest_id";
      con.createQuery(sql)
        .addParameter("rest_id", rest_id)
        .executeUpdate();
    }
  }

}
