import org.junit.rules.ExternalResource;
import spark.Spark;

public class ServerRule extends ExternalResource {
  //make the server:
  protected void before() {
    String[] args = {};
    ChangeMakerApp.main(args);
  }

  //stop the server:
  protected void after() {
    Spark.stop();
  }
}
