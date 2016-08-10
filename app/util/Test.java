package util;

import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import javax.xml.ws.Response;
import java.util.concurrent.CompletionStage;

/**
 * Created by cherish.sham on 8/10/16.
 */
public class Test {

    @Inject
    WSClient ws;

    public static void main(String args[]){

//        WSRequest request = ws.url("http://localhost:9000/list_pets");
//        CompletionStage<String> response = request.get().thenApply(WSResponse::getBody);
//        System.out.print("Hi");
//        System.out.print(response.toString());

//        @BeforeClass
//        public static void beforeClass() throws Exception {
//            server = InMemoryRestServer.create(sut);
//        }
//        @AfterClass
//        public static void afterClass() throws Exception {
//            server.close();
//        }
//
//        @Test
//        Response response = server.newRequest("/myresource").request().invoke();
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }
}
