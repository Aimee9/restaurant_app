import org.sql2o.*;
import java.util.List;

public class Restaurant{
  private int rest_id;
  private int cuisine_id;
  private String rest_name;

  public Restaurant(String rest_name){
    this.rest_name = rest_name;
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

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO restaurants (rest_name, cuisine_id) VALUES (:rest_name, :cuisine_id)";
      this.rest_id = (int)con.createQuery(sql, true)
        .addParameter("rest_name", rest_name)
        .addParameter("cuisine_id", cuisine_id)
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
