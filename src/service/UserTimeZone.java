package service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by lbene on 20.10.2014.
 */
@ManagedBean(name = "userTimeZone", eager = true)
@SessionScoped
public class UserTimeZone implements Serializable{

    private static final long serialVersionUID = 1L;
    private static final String serviceUrl = "http://localhost:8080/TimeZone_war_exploded/TimeZone";

    public String getTimeZone(int coordinateX, int coordinateY) {
        String result = "Could not retrieve timezone.";
        try {
            URL timeZoneServlet = new URL(serviceUrl+"?long="+ coordinateX + "&lat=" + coordinateY);
            HttpURLConnection servletConnection = (HttpURLConnection) timeZoneServlet.openConnection();
            servletConnection.setRequestMethod("GET");
            servletConnection.setDoOutput(true);
            InputStream reader = servletConnection.getInputStream();

            int nr = reader.available();
            String str = "";
            for (int i = 0; i < nr; i++) {
                str += (char) reader.read();
            }

            result = str.split("<time_zone_id>")[1].split("</time_zone_id>")[0];
        } catch (MalformedURLException ee) {
            ee.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }

        return result;
    }
}
