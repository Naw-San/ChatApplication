package server;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NotFoundTest {

  @Test
  public void do404(){
    // fallback, if random endpoint is called, return an http 404
    String rand = String.valueOf(Math.random());
    String test1 = "GET /" + rand + " HTTP/1.1\n"
        + "\n";
    String response = Server.processRequest(test1);
    Assert.assertEquals(response, "HTTP/1.1 404 Not Found\n");
  }
}
