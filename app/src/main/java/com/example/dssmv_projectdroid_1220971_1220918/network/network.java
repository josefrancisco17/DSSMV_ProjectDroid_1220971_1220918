package com.example.dssmv_projectdroid_1220971_1220918.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.dssmv_projectdroid_1220971_1220918.helper.UtilsJson;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;

public class network  {
    public static Library getLibrary(String urlStr) {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer buffer = new StringBuffer();

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            inputStream = conection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String linha = "";
            while ((linha = reader.readLine()) != null) {
                buffer.append(linha);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return UtilsJson.fromJson(buffer.toString(), Library.class);
    }

    private static String readBody(InputStream in) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String read = br.readLine();
            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
