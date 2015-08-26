import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.junit.Assert.*;
import spark.template.velocity.VelocityTemplateEngine;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;


public class AppTest extends FluentTest {

  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
}

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("List Of Cuisines:");
  }

  @Test
  public void newCuisineIsCreated() {
    goTo("http://localhost:4567/");
    fill("#newCuisine").with("Italian");
    submit(".btn");
    assertThat(pageSource()).contains("Italian");
  }

  @Test
  public void allCuisineIsCreated() {
    goTo("http://localhost:4567/");
    fill("#newCuisine").with("Italian");
    submit(".btn");
    fill("#newCuisine").with("Diner");
    submit(".btn");
    assertThat(pageSource()).contains("Italian");
    assertThat(pageSource()).contains("Diner");
  }

  @Test
  public void cuisineLinkWorks() {
    Cuisine myCuisine = new Cuisine("Italian");
    myCuisine.save();
    String cuisinePath = String.format("http://localhost:4567/cuisines/%d", myCuisine.getCuisineId());
    goTo(cuisinePath);
    assertThat(pageSource()).contains("Italian");
    }

  @Test
  public void displayListOfRestaurants() {
    Cuisine myCuisine = new Cuisine("Italian");
    myCuisine.save();
    Restaurant myRest = new Restaurant("Pop Shop");
    myRest.addCuisineId(myCuisine.getCuisineId());
    myRest.save();
    String cuisinePath = String.format("http://localhost:4567/cuisines/%d", myCuisine.getCuisineId());
    goTo(cuisinePath);
    assertThat(pageSource()).contains("Pop Shop");
    }

  @Test
  public void restaurantLinkWorks() {
    Cuisine myCuisine = new Cuisine("Italian");
    myCuisine.save();
    Restaurant myRest = new Restaurant("Pop Shop");
    myRest.addCuisineId(myCuisine.getCuisineId());
    myRest.save();
    String cuisinePath = String.format("http://localhost:4567/cuisines/%d/%d", myCuisine.getCuisineId(), myRest.getRestId());
    goTo(cuisinePath);
    assertThat(pageSource()).contains("You have selected Pop Shop");
  }

  @Test
  public void returnFromCuisineLinkWorks() {
    Cuisine myCuisine = new Cuisine("Italian");
    myCuisine.save();
    String cuisinePath = String.format("http://localhost:4567/cuisines/%d", myCuisine.getCuisineId());
    goTo(cuisinePath);
    click("a", withText("Return to list of cuisines"));
    assertThat(pageSource()).contains("List Of Cuisines:");
  }

  @Test
  public void returnToCuisineListFromRestaurantListLinkWorks(){
    Cuisine myCuisine = new Cuisine("Italian");
    myCuisine.save();
    Restaurant myRest = new Restaurant("Pop Shop");
    myRest.addCuisineId(myCuisine.getCuisineId());
    myRest.save();
    String cuisinePath = String.format("http://localhost:4567/cuisines/%d/%d", myCuisine.getCuisineId(), myRest.getRestId());
    goTo(cuisinePath);
    click("a", withText("Return to list of cuisines"));
    assertThat(pageSource()).contains("List Of Cuisines:");
  }

  @Test
  public void returnToRestListFromRestaurantListLinkWorks(){
    Cuisine myCuisine = new Cuisine("Italian");
    myCuisine.save();
    Restaurant myRest = new Restaurant("Pop Shop");
    myRest.addCuisineId(myCuisine.getCuisineId());
    myRest.save();
    String cuisinePath = String.format("http://localhost:4567/cuisines/%d/%d", myCuisine.getCuisineId(), myRest.getRestId());
    goTo(cuisinePath);
    click("a", withText("Return to List of Italian restaurants"));
    assertThat(pageSource()).contains("Restaurants with Italian food");
  }

}
