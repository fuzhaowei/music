package fzw.yuandi.music.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/18.
 */

public class mHttp {

    /**
     * @param uri
     * @return
     */
    public String mHttpUrlConnection(String uri) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);    //输入流
            connection.setDoOutput(true);   //输出流
            connection.setUseCaches(true); //缓存
            connection.setRequestMethod("GET");
            inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String intputline = "";
            while ((intputline = bufferedReader.readLine()) != null) {
                result += intputline + "/n";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
        return result;
    }

}
