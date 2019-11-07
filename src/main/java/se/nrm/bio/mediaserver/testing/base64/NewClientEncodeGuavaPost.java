package se.nrm.bio.mediaserver.testing.base64;

import com.google.common.io.BaseEncoding;
import java.io.IOException;
import org.apache.commons.lang.StringEscapeUtils;
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
 * 2019-11-05: Använder commons codec 1.16 -> java -Xms512m -Xmx20480m -jar
 * target/mediaClient.jar
 *
 * following Idas file ;
 * <p>
 * @see dina-vegacenter-logic, se.nrm.dina.vegacenter.Main.java
 * @see -> https://issues.apache.org/jira/browse/CODEC-61 -> base64 is in the
 * @author ingimar
 */
public class NewClientEncodeGuavaPost {

    public static void main(String[] args) throws IOException, JSONException {

        NewClientEncodeGuavaPost main = new NewClientEncodeGuavaPost();
        boolean isMediaserverRunning = false;

        main.posting(isMediaserverRunning);

    }

    private static void posting(boolean isMediaserverRunning) throws IOException, JSONException {
        System.out.println("Starting ".concat("NewClientEncodeGuavaPost"));
        String URL = Util.getLocalURL();

//        String filePath = "/tmp/Testbild.jpg";
//        String filePath = "/tmp/500mb.zip";
        String filePath = "/tmp/1000mb.zip"; 

        System.out.println("\t Post to  : ".concat(URL));
        System.out.println("\t file : ".concat(filePath).concat("\n"));

        Path path = Paths.get(filePath);
        byte[] bArray = Files.readAllBytes(path);

        String contentToBeSaved = BaseEncoding.base64().encode(bArray);
        System.out.println("contentToBeSaved " + contentToBeSaved.length());

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;

        JSONObject metadata = new JSONObject();
        metadata.put("owner", "ingo");
        metadata.put("access", "public");
        metadata.put("licenseType", "CC BY");
        metadata.put("fileName", "fileName");
        metadata.put("fileDataBase64", contentToBeSaved);

        String metadataFormatted = StringEscapeUtils.unescapeJavaScript(metadata.toString());

        StringEntity entity = new StringEntity(metadataFormatted, ContentType.APPLICATION_JSON);

        if (isMediaserverRunning) {

            HttpPost post = new HttpPost(URL);
            post.setEntity(entity);
            response = client.execute(post);
            HttpEntity responseEntity = response.getEntity();

            String responseFromMediaserver = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println("\n****");
            System.out.println("Response from the mediaserver is : " + responseFromMediaserver);

            JSONObject json = new JSONObject(responseFromMediaserver);
            String uuid = json.getString("uuid");
            System.out.println("UUID is " + uuid);
        }

    }
}
