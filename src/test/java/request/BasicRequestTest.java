package request;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicRequestTest {

  @Test
  public void basicTest(){
    String test1 = "GET /hello HTTP/1.1\n"
        + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n"
        + "Accept-Language: en-us\n"
        + "Accept-Encoding: gzip, deflate\n"
        + "Connection: Keep-Alive";
    ParsedRequest request = CustomParser.parse(test1);
    Assert.assertEquals(request.getPath(), "/hello");
    Assert.assertEquals(request.getMethod(), "GET");
  }

  @Test
  public void basicTest2(){
    String random1 = String.valueOf(Math.random());
    String random2 = String.valueOf(Math.random());
    String test1 = random1 + " /" + random2 + " HTTP/1.1\n"
        + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n"
        + "Accept-Language: en-us\n"
        + "Accept-Encoding: gzip, deflate\n"
        + "Connection: Keep-Alive";
    ParsedRequest request = CustomParser.parse(test1);
    Assert.assertEquals(request.getPath(), "/" + random2);
    Assert.assertEquals(request.getMethod(), random1);
  }

  @Test
  public void basicTest3(){
    String random1 = String.valueOf(Math.random());
    String random2 = String.valueOf(Math.random());


    int randomNumberOfHeaders = (int) Math.floor(Math.random() * 10) + 1;
    List<List<String>> headerTests = new ArrayList<>();
    String queryString = "?";
    for (int i = 0; i < randomNumberOfHeaders; i++) {
      String key = String.valueOf(Math.random());
      String value = String.valueOf(Math.random());
      headerTests.add(List.of(key, value));
      queryString += key + "=" + value;
      if(i < randomNumberOfHeaders - 1){
        queryString += "&";
      }
    }
    String test1 = random1 + " /" + random2 + queryString + " HTTP/1.1\n"
        + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n";
    ParsedRequest request = CustomParser.parse(test1);
    Assert.assertEquals(request.getPath(), "/" + random2);
    Assert.assertEquals(request.getMethod(), random1);

    for (int i = 0; i < randomNumberOfHeaders; i++) {
      List<String> testSet = headerTests.get(i);
      Assert.assertEquals(request.getQueryParam(testSet.get(0)), testSet.get(1));
    }
  }

}
