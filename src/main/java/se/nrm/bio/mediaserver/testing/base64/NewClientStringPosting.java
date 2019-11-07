package se.nrm.bio.mediaserver.testing.base64;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import se.nrm.bio.mediaserver.testing.base64.util.StringToFile;

/**
 * 2019-11-06: Idas suggestion 
 * 1. FileInputStream is = new FileInputStream(filePath);
 * 2. String result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
 * 
 *
 * @author ingimar
 */
public class NewClientStringPosting {

    public static void main(String[] args) throws IOException, JSONException {

        NewClientStringPosting main = new NewClientStringPosting();
        main.posting();

    }

    private static void posting() throws IOException, JSONException {
        System.out.println("Starting ".concat("NewClientEncodePost"));
        String URL = Util.getLocalURL();

//        String filePath = "/tmp/1gb.zip";
        String filePath = "/tmp/marley.jpeg";

        System.out.println("\t Post to  : ".concat(URL));
        System.out.println("\t file : ".concat(filePath).concat("\n"));

        FileInputStream is = new FileInputStream(filePath);
        String result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
        StringToFile.saveFile("/home/ingimar/ingimar-json-0.log",result);
        

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;

        JSONObject metadata = new JSONObject();
        metadata.put("owner", "ida li");
        metadata.put("access", "public");
        metadata.put("licenseType", "CC BY");
        metadata.put("fileName", "fileName");
        metadata.put("fileDataBase64", result);

        String metadataFormatted = StringEscapeUtils.unescapeJavaScript(metadata.toString());
        StringToFile.saveFile("/home/ingimar/ingimar-json-1.log",metadataFormatted);

        StringEntity entity = new StringEntity(metadataFormatted, ContentType.APPLICATION_JSON);
        

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

    static String convertStreamToString(java.io.InputStream inputStream) {
        String theString = "";
        try {
            theString = IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(NewClientStringPosting.class.getName()).log(Level.SEVERE, null, ex);
        }

        return theString;

    }
}
