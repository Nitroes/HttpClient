package com.alicetool.project.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;


public class HttpClient {

    public interface CallBack {
        void Request(String data) throws Exception;
    }


    public void Get(String strUrl, CallBack callBack) {
        new Thread(() -> {

            HttpURLConnection conn = null;
            try {
                URL url = new URL(strUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(5 * 1000);
                if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    System.out.printf("get请求成功");
                    InputStream in = conn.getInputStream();
                    String backcontent = readString(in);
                    backcontent = URLDecoder.decode(backcontent, "UTF-8");
                    System.out.printf(backcontent);
                    callBack.Request(backcontent);
                    in.close();
                } else {
                    System.out.printf("请求失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }).start();
    }


    public void Post(String strUrl, String postData, CallBack callBack) {
        new Thread(() -> {

            HttpURLConnection conn = null;
            try {
                URL url = new URL(strUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                OutputStreamWriter outputStream = new OutputStreamWriter(conn.getOutputStream());
                outputStream.write(postData);
                outputStream.flush();
                outputStream.close();
                if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    System.out.printf("post请求成功");
                    InputStream in = conn.getInputStream();
                    String backcontent = readString(in);
                    backcontent = URLDecoder.decode(backcontent, "UTF-8");
                    System.out.printf(backcontent);
                    callBack.Request(backcontent);
                    in.close();
                } else {
                    System.out.printf("请求失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }).start();
    }

    String readString(InputStream is) throws IOException {

        int len = -1;
        byte buf[] = new byte[128];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        String result = new String(baos.toByteArray());
        return result;
    }


}
