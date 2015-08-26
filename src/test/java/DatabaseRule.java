import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/food_places_test", null, null);
    // Make sure to customize the url to have the actual test database name.
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRestaurantsQuery = "DELETE FROM restaurants *;";
      String deleteCuisineQuery = "DELETE FROM cuisine *;";
      con.createQuery(deleteRestaurantsQuery).executeUpdate();
      con.createQuery(deleteCuisineQuery).executeUpdate();
    }
  }
}
