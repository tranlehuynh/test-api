package helper;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Hashtable;

public class sendRequest {

    public static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String sendGetRequest(String URL, Hashtable<String, String> header) {
        HttpGet httpGet = new HttpGet(URL);
        header.forEach((k,v) -> httpGet.addHeader(k,v));

        String apiResponse = null;
        try {
            CloseableHttpResponse response;
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity(); //get API response as a String

//            Header[] headers = response.getAllHeaders();

            if (entity != null) {
                apiResponse = EntityUtils.toString(entity);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } return apiResponse;
    }
}
