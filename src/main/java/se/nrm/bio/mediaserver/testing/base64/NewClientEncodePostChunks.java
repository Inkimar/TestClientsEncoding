package se.nrm.bio.mediaserver.testing.base64;

import java.io.FileInputStream;
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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 * 2019-11-05: Använder commons codec 1.16 -> java -Xms512m -Xmx20480m -jar
 * target/mediaClient.jar
 *
 * following Idas file ;
 * <p>
 * @see dina-vegacenter-logic, se.nrm.dina.vegacenter.Main.java
 * @see -> https://issues.apache.org/jira/browse/CODEC-61 -> base64 is in the
 * commons-codec
 *
 * @author ingimar
 */
public class NewClientEncodePostChunks {

    public static void main(String[] args) throws IOException, JSONException {

        NewClientEncodePostChunks main = new NewClientEncodePostChunks();
        main.posting();

    }

    private static void posting() throws IOException, JSONException {
        System.out.println("Starting ".concat("NewClientEncodePostChunks"));
        String URL = Util.getLocalURL();
        String filePath = "/tmp/500mb.zip";
//        String filePath = "/tmp/Testbild.jpg";

        System.out.println("\t Post to  : ".concat(URL));
        System.out.println("\t file : ".concat(filePath).concat("\n"));

        StringBuilder sb = new StringBuilder();
        FileInputStream fin = new FileInputStream(filePath);

        int bSize = 3 * 512;
        // Buffer
        byte[] buf = new byte[bSize];
        // Actual size of buffer
        int len = 0;

        while ((len = fin.read(buf)) != -1) {
            byte[] encoded = Base64.encodeBase64(buf);

            // Although you might want to write the encoded bytes to another 
            // stream, otherwise you'll run into the same problem again.
            sb.append(new String(buf, 0, len));
        }
        String base64EncodedFile = sb.toString();

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;

        JSONObject metadata = new JSONObject();
        metadata.put("owner", "ida li");
        metadata.put("access", "public");
        metadata.put("licenseType", "CC BY");
        metadata.put("fileName", "fileName");
        metadata.put("fileDataBase64", base64EncodedFile);

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
