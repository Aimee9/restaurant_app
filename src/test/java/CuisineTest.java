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

}
