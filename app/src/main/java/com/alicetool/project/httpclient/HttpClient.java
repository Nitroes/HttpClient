package com.alicetool.project.httpclient;

public class HttpClient {

    public int add(int i,int j){
        return  i+j;
    }

    public interface CallBack {
        void Request(String data) throws Exception;
    }


}
