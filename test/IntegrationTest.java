import akka.stream.javadsl.Source;
import models.Pet;
import org.junit.*;

import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.*;
import play.test.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static play.test.Helpers.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertThat(browser.pageSource(), containsString("Hello. Welcome to my pet shop!"));
        });
    }

    @Test
    public void testHomeServer() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Test
    public void testListPets() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/list_pets").get();
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(OK, response.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Test
    public void testAddAndDeletePet() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/add")
                        .setQueryParameter("name","test_pet")
                        .setQueryParameter("age","2")
                        .setQueryParameter("sex","male")
                        .put("");
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(CREATED, response.getStatus());

                WSClient ws2 = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage2 = ws2.url("/delete/test_pet").delete();
                WSResponse response2 = completionStage2.toCompletableFuture().get();
                ws2.close();
                assertEquals(OK, response2.getStatus());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Test
    public void testAddUpdateAndDeletePet() throws Exception {
        TestServer server = testServer(3333);
        running(server, () -> {
            try {
                Map<String, String[]> data = new HashMap<>();
                WSClient ws = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage = ws.url("/add")
                        .setQueryParameter("name","test_pet")
                        .setQueryParameter("age","2")
                        .setQueryParameter("sex","male")
                        .put("");
                WSResponse response = completionStage.toCompletableFuture().get();
                ws.close();
                assertEquals(CREATED, response.getStatus());

                WSClient ws2 = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage2 = ws2.url("/update")
                        .setContentType("application/x-www-form-urlencoded")
                        .post("name=test_pet&age=4&sex=male");
                WSResponse response2 = completionStage2.toCompletableFuture().get();
                ws2.close();
                assertEquals(OK, response2.getStatus());

                WSClient ws3 = play.libs.ws.WS.newClient(3333);
                CompletionStage<WSResponse> completionStage3 = ws3.url("/delete/test_pet").delete();
                WSResponse response3 = completionStage3.toCompletableFuture().get();
                ws3.close();
                assertEquals(OK, response3.getStatus());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
