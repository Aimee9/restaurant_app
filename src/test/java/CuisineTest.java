import org.junit.*;
import static org.junit.Assert.*;


public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void empty_emptyAtFirst() {
    assertEquals(0, Cuisine.all().size());
  }

  @Test
  public void equals_returnTruewithSameNames() {
    Cuisine firstCuisine = new Cuisine("Italian");
    Cuisine secondCuisine = new Cuisine("Italian");
    assertEquals(true, firstCuisine.equals(secondCuisine));
  }

  @Test
  public void getCuisineId_returnCorrectId() {
    Cuisine newCuisine = new Cuisine("Italian");
    newCuisine.save();
    assertEquals(Cuisine.all().get(0).getCuisineId(), newCuisine.getCuisineId());
  }

  @Test
  public void getCuisineType_returnCorrectType() {
    Cuisine newCuisine = new Cuisine("Italian");
    assertEquals("Italian", newCuisine.getCuisineType());
  }

  @Test
  public void find_findsCorrectId() {
    Cuisine newCuisine = new Cuisine("French");
    newCuisine.save();
    Cuisine savedCuisine = Cuisine.find(newCuisine.getCuisineId());
    assertEquals(savedCuisine, newCuisine);
  }

  @Test
  public void delete_deletesCuisineFromDatabase() {
    Cuisine newCuisine = new Cuisine("French");
    newCuisine.save();
    newCuisine.delete();
    assertEquals(0, Cuisine.all().size());
  }

  @Test
  public void update_updatesCuisineCorrectly() {
    Cuisine newCuisine = new Cuisine("French");
    newCuisine.save();
    newCuisine.update("Italian");
    assertEquals("Italian", Cuisine.all().get(0).getCuisineType());
  }

  @Test
  public void getId_multipleIDsAdded() {
    Cuisine newCuisine1 = new Cuisine("french");
    newCuisine1.save();
    Cuisine newCuisine2 = new Cuisine("mexican");
    newCuisine2.save();
    assertEquals(Cuisine.all().get(1).getCuisineId(), newCuisine2.getCuisineId());
  }

  @Test
  public void getRestaurants_getsCorrectRestaurants() {
    Cuisine newCuisine = new Cuisine("french");
    newCuisine.save();
    Restaurant newRestaurant = new Restaurant("food place");
    newRestaurant.save();
    newRestaurant.addCuisineId(newCuisine.getCuisineId());
    Restaurant savedRestaurant = newCuisine.getRestaurants().get(0);
    assertEquals(savedRestaurant, newRestaurant);
  }
}
