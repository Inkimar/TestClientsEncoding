package se.nrm.bio.mediaserver.testing.base64.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ingimar
 */
public class StringToFile {
    
    public static boolean saveFile(String path, String content){
       
        boolean isSuccessful=false;
//        try {
//           Files.writeString(Paths.get(path), content);
//         
//        } catch (IOException ex) {
//            Logger.getLogger(StringToFile.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
        return isSuccessful;
        
    }
    
}
