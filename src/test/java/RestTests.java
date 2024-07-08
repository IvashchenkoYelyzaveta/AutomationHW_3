import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class RestTests {

    @Test
    public void getEmployees() throws IOException {

        String employeeId = "5";

        String url = "https://dummy.restapiexample.com/api/v1/employee/5" + employeeId;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getAllEmployees = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(getAllEmployees);

        System.out.println("Status code: " + response.getStatusLine().getStatusCode());
        System.out.println("Status phrase: " + response.getStatusLine().getReasonPhrase());

        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);

        System.out.println("Response body: " + json);
    }

}
