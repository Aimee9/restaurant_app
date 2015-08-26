import org.sql2o.*;
import java.util.List;

public class Cuisine {
  private int cuisine_id;
  private String cuisine_type;

public Cuisine (String cuisine_type) {
  this.cuisine_type = cuisine_type;
  }

  public String getCuisineType() {
    return cuisine_type;
  }

  public int getCuisineId() {
    return cuisine_id;
  }



  public static List<Cuisine> all() {
    String sql = "SELECT * FROM cuisine";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Cuisine.class);
    }
  }

  @Override
  public boolean equals(Object otherCuisineInstance) {
    if (!(otherCuisineInstance instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisineInstance = (Cuisine) otherCuisineInstance;
      return this.getCuisineType().equals(newCuisineInstance.getCuisineType()) &&
              this.getCuisineId() == newCuisineInstance.getCuisineId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisine (cuisine_type, cuisine_id) VALUES (:cuisine_type, :cuisine_id)";
      this.cuisine_id = (int)con.createQuery(sql, true)
        .addParameter("cuisine_type", cuisine_type)
        .addParameter("cuisine_id", cuisine_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Cuisine find(int cuisine_id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM cuisine where cuisine_id = :cuisine_id";
      Cuisine cuisine = con.createQuery(sql)
        .addParameter("cuisine_id", cuisine_id)
        .executeAndFetchFirst(Cuisine.class);
      return cuisine;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM cuisine WHERE cuisine_id = :cuisine_id";
      con.createQuery(sql)
        .addParameter("cuisine_id", cuisine_id)
        .executeUpdate();
    }
  }

  public void update(String newType) {
    cuisine_type = newType;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE cuisine SET cuisine_type = :cuisine_type WHERE cuisine_id = :cuisine_id";
      con.createQuery(sql)
        .addParameter("cuisine_type", cuisine_type)
        .addParameter("cuisine_id", cuisine_id)
        .executeUpdate();
    }
  }



}
