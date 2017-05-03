package ua.nure.tsuvarev;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Slava on 06.04.2017.
 */
public class WeatherLoader {
    
    private static final int BLOCK = 0x1000;
    private final String address;
    
    public WeatherLoader(String address) {
        this.address = address;
    }
    
    public WeatherData getForMonth(String month) {
        try {
            URL url = new URL(address  + WeatherServlet.MONTH_PARAM + "=" + month);
            InputStream input = url.openConnection().getInputStream();
    
            String res = getJsonAnswer(input);
            
            return WeatherData.fromString(res);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String getJsonAnswer(InputStream input) throws IOException {
        int readCount;
        byte[] read;
        StringBuilder builder = new StringBuilder();
        do {
            read = new byte[BLOCK];
            readCount = input.read(read);
            builder.append(new String(read, Charset.defaultCharset()));
        } while (readCount == BLOCK);
        return builder.toString();
    }
}
