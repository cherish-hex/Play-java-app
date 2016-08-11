import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.xml.ws.Response;
import java.util.concurrent.CompletionStage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by cherish.sham on 8/10/16.
 */
public class WebservicesTest {

//    @Inject
//    WSClient ws;
//
//    @Test
//    public void getTest(){
//
//        WSRequest request = ws.url("http://localhost:9000/list_pets");
//        CompletionStage<String> response = request.get().thenApply(WSResponse::getBody);
//        System.out.print("Hi");
//        System.out.print(response.toString());
//        assertNotNull(response);
//    }

//    public static InMemoryRestServer server;
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        server = InMemoryRestServer.create(sut);
//    }
//    @AfterClass
//    public static void afterClass() throws Exception {
//        server.close();
//    }
//
//    @Test
//    public void postWithoutMocking() throws Exception {
//
//        Response response = server.newRequest("/myresource").request().invoke();
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }

}
