package emotion.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Arturh
 */
public class HttpUtil {
    
    private Map<String, String> header;
    private String contentType;

    public HttpUtil() {
        this.header = new HashMap<>();
        this.contentType = "application/json";
    }
    
    public Map<String, String> getHeader() {
        return header;
    }
    
    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String get(String url) {
        
        String toSend = "";
        
        try {
            
            URL $url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) $url.openConnection();
            
            conn.setRequestMethod("GET");
            
            conn.setRequestProperty("Content-Type", this.contentType);
            
            this.header.forEach((key, value) -> {
                conn.setRequestProperty(key, value);
            });
            
            System.out.println("Sending 'GET' request to URL : " + $url);
            System.out.println("Response Code : " + conn.getResponseCode());
            
            Object[] toArray = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            ).lines().toArray();
            
            for (Object object : toArray) toSend += object.toString() + "\n";
            
        } catch (MalformedURLException | ProtocolException ex) {
            
        } catch (IOException ex) {
            
        }
        
        return toSend;
        
    }
    
}
