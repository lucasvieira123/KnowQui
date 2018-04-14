package utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lucas-vieira on 11/04/18.
 */

public class RequestAndResponseHelper {
    private String uri = null;
    private String jsonRequestString = null;


    public String getJson() throws Exception {

        HttpParams params = new BasicHttpParams();
        params.setParameter("http.connection.timeout", 10000);
        params.setParameter("http.socket.timeout", 10000);
        params.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        params.setParameter("http.useragent", "Apache-HttpClient/Android");

        HttpClient httpClient = new DefaultHttpClient(params);
        HttpResponse httpResponse = null;
        HttpPost post = new HttpPost(uri);

        StringEntity entity = new StringEntity(jsonRequestString);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post.setEntity(entity);
        httpResponse = httpClient.execute(post);

        Log.i(this.getClass().getSimpleName(),httpResponse.toString());

        InputStream inputStream = httpResponse.getEntity().getContent();

        String jsonResponseString = getStringFromInputStream(inputStream);
        inputStream.close();

        return jsonResponseString;

    }


    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public String getUri() {
        return uri;
    }

    public RequestAndResponseHelper setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getJsonRequestString() {
        return jsonRequestString;
    }

    public RequestAndResponseHelper setJsonRequestString(String jsonRequestString) {
        this.jsonRequestString = jsonRequestString;
        return this;
    }
}
