package se.nrm.bio.mediaserver.testing.base64;

import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * 2019-11-05: Använder commons codec 1.16
 *  -> java -Xms512m -Xmx20480m -jar target/mediaClient.jar 
 *
 * following Idas file ;
 * <p>
 * @see dina-vegacenter-logic, se.nrm.dina.vegacenter.Main.java
 * @see -> https://issues.apache.org/jira/browse/CODEC-61 -> base64 is in the
 * commons-codec
 *
 * @author ingimar
 */
public class NewClientbyteArrayPost {

    public static void main(String[] args) throws IOException, JSONException {

        NewClientbyteArrayPost main = new NewClientbyteArrayPost();
        main.posting();

    }

    private static void posting() throws IOException, JSONException {
        System.out.println("Starting ".concat("NewClientEncodePost"));
        String URL = Util.getLocalURL();

//        String filePath = "/tmp/1gb.zip";
        String filePath = "/tmp/marley.jpeg";

        System.out.println("\t Post to  : ".concat(URL));
        System.out.println("\t file : ".concat(filePath).concat("\n"));

        Path path = Paths.get(filePath);
        byte[] bArray = Files.readAllBytes(path);
        String objectToJson = JsonWriter.objectToJson(bArray);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;

        JSONObject metadata = new JSONObject();
        metadata.put("owner", "ida li");
        metadata.put("access", "public");
        metadata.put("licenseType", "CC BY");
        metadata.put("fileName", "fileName");
        metadata.put("fileDataBase64", objectToJson);
        
        

//        String metadataFormatted = StringEscapeUtils.unescapeJavaScript(metadata.toString());
//
//        StringEntity entity = new StringEntity(metadataFormatted, ContentType.APPLICATION_JSON);
//
//        HttpPost post = new HttpPost(URL);
//        post.setEntity(entity);
//        response = client.execute(post);
//        HttpEntity responseEntity = response.getEntity();
//
//        String responseFromMediaserver = EntityUtils.toString(responseEntity, "UTF-8");
//        System.out.println("\n****");
//        System.out.println("Response from the mediaserver is : " + responseFromMediaserver);
//
//        JSONObject json = new JSONObject(responseFromMediaserver);
//        String uuid = json.getString("uuid");
//        System.out.println("UUID is " + uuid);

    }
}
