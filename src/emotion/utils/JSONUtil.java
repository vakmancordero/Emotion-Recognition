package emotion.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author VakSF
 */
public class JSONUtil {
    
    public JSONArray getJSONArray(HttpUtil http, String url) {
        
        String text = http.get(url);
        
        if (!text.isEmpty())
            return new JSONArray(text);
        
        return null;
    }
    
    public JSONObject getJSONObject(HttpUtil http, String url) {
        
        try {
            
            String text =  http.get(url);
            
            if (text.isEmpty()) 
                return new JSONObject("{}");
            
            return new JSONObject(text);
            
        } catch (JSONException ex) {
            System.out.println("Error: " + ex.toString());
        }
        
        return null;
    }
    
    public String getJSONAttribute(JSONObject jsonObject, String attribute) {
        
        try {
            
            if (!jsonObject.isNull(attribute)) 
                return jsonObject.get(attribute).toString();
            
        } catch (JSONException ex) {
            
            System.out.println("Error: " + ex.toString());
            
            return null;
        }
        
        return null;
    }
    
    public String getSimpleResult(HttpUtil http, String url) {
        return http.get(url);
    }
    
}
