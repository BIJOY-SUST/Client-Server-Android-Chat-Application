package com.example.aymen.androidchat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class WebService {
    public static String readUrl(String server_url) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(server_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json+"\n");

            }
            return sb.toString();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
