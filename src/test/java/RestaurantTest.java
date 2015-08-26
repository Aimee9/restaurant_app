import org.junit.*;
import static org.junit.Assert.*;


public class RestaurantTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void getRestId_returnsCorrectId(){
    Restaurant newRest = new Restaurant("Food Place");
    newRest.save();
    Restaurant savedRestaurant = Restaurant.all().get(0);
    assertEquals(newRest.getRestId(), savedRestaurant.getRestId());
  }

  @Test
  public void getRestName_returnsCorrectName(){
    Restaurant newRest = new Restaurant("food place");
    newRest.save();
    assertEquals("food place", newRest.getRestName());
  }

  @Test
  public void equals_returnTrueIfNamesAreSame(){
    Restaurant newRest = new Restaurant("food place");
    Restaurant newRest2 = new Restaurant("food place");
    assertEquals(true, newRest.equals(newRest2));
  }

  @Test
  public void save_returnTrueIfSaved() {
    Restaurant newRest = new Restaurant("Food Place");
    newRest.save();
    assertTrue(Restaurant.all().get(0).equals(newRest));
  }

  @Test
  public void find_findsCorrectId() {
    Restaurant newRest = new Restaurant("Food Place");
    newRest.save();
    Restaurant savedRest = Restaurant.find(newRest.getRestId());
    assertEquals(savedRest, newRest);
  }

  @Test
  public void update_updatesRestaurantNameInDatabase() {
    Restaurant newRestaurant = new Restaurant("Food Place");
    newRestaurant.save();
    newRestaurant.update("Pop Shop");
    assertEquals("Pop Shop", Restaurant.all().get(0).getRestName());
  }

  @Test
  public void delete_deletesRestaurantFromDatabase() {
    Restaurant newRestaurant = new Restaurant("Pop Shop");
    newRestaurant.save();
    newRestaurant.delete();
    assertEquals(0, Restaurant.all().size());
  }
}
