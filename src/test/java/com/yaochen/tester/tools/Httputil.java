package com.yaochen.tester.tools;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class Httputil {
    public static Map<String,String> tokenorcookie=new HashMap<String, String>();
    public static String doPost(String url, Map<String, String> data) {
        //创造一个模拟浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //发送一个post请求
        HttpPost post = new HttpPost(url);
        //创建一个List集合来存数据
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        //获取data数据的name
        Set<String> keys = data.keySet();
        //循环取出name
        for (String name : keys) {
            //data.get（name）方法根据name取出value
            String value = data.get(name);
            //把数据添加到list集合里面
            list.add(new BasicNameValuePair(name, value));
        }
        //创造一个全局变量，能够返回
        String result = "";
        try {
            //提交post请求信息
            post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            addTokenOrCookie(post);
            //获取post的返回信息
            HttpResponse httpResponse = httpClient.execute(post);
            getTokenOrCookie(httpResponse);
            //拿到接口的code值
            int code = httpResponse.getStatusLine().getStatusCode();
            System.out.println(code);
            //拿到接口的返回报文，转成String
            result = EntityUtils.toString(httpResponse.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void addTokenOrCookie(HttpRequest request) {
        String tokenorcookievalue=tokenorcookie.get("JSESSIONID");
        if (tokenorcookievalue!=null){
            request.setHeader("data",tokenorcookievalue);
        }


    }

    private static void getTokenOrCookie(HttpResponse httpResponse) {
        //从响应头里取出token或者cookie
        Header tokenheader=httpResponse.getFirstHeader("Set-cookie");
        //判断响应头有没有数据
        if (tokenheader!=null){
            //获取token或者cookie的value值
            String token=tokenheader.getValue();
            //判断取出来的值是否为空并大于零
            if (token!=null&&token.trim().length()>0){
                //如果是cookie需要分割value值，token可以不用
                String [] tokenPair=token.split(";");
                //判断根据符号取出来的数组是否为空
                if (tokenPair!=null){
                    //增强循环把数组的值取出来
                    for (String tokenPairs:tokenPair){
                        //判断这组里面是否包含token或者JSESSIONID
                        if (tokenPairs.contains("JSESSIONID")){
                            //把token或者cookie值放到提前准备好的Map里
                            tokenorcookie.put("JSESSIONID",tokenPairs);
                        }
                    }
                }
            }
        }
    }
    public static String doGet(String url, Map<String, String> data) {
        Set<String> keys = data.keySet();
        int mack = 1;
        for (String name : keys) {
            if (mack == 1) {
                url += ("?" + name + "=" + data.get(name));
            } else {
                url += ("&" + name + "=" + data.get(name));
            }
            ;
            mack++;
        }
        //指定接口提交方式
        HttpGet get = new HttpGet(url);
        //准备测试数据
        System.out.println(url);
        //发送请求
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = null;
        String text = "";
        try {
            addTokenOrCookie(get);
            httpResponse = httpClient.execute(get);
            getTokenOrCookie(httpResponse);
            int code = httpResponse.getStatusLine().getStatusCode();
            text = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    public static String doService(String url,String type,Map<String,String>params) {
        String result="";
        if ("post".equals(type)) {
            result=Httputil.doPost(url, params);
        } else if ("get".equals(type)) {
            result=Httputil.doGet(url, params);
        }
        return result;
}




}
