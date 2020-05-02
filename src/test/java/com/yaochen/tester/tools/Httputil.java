package com.yaochen.tester.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class Httputil {
    public static Logger logger=Logger.getLogger(Httputil.class);
    public static Map<String,String> tokenorcookie=new HashMap<String, String>();
    public static Map<String,Object> token=new HashMap<>();

//    public static String doPost(String url, Map<String, Object> data) {
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String result = "";
//        logger.info("接口请求地址"+url);
//        //创造一个模拟浏览器
//        client = HttpClients.createDefault();
//        //发送一个post请求
//        HttpPost post = new HttpPost(url);
//        //创建一个List集合来存数据
//        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//        post.addHeader("X-Lemonban-Media-Type","lemonban.v2");
//        post.addHeader("Content-Type","application/json");
//        addCookieInRequestHeaderBeforeRequest(post);
////        //获取data数据的name
////        Set<String> keys = data.keySet();
////        //循环取出name
////        for (String name : keys) {
////            //data.get（name）方法根据name取出value
////            Object value = data.get(name);
////            //把数据添加到list集合里面
////            list.add(new BasicNameValuePair(name, (String) value));
////        }
//        if (data != null) {
//            for (Map.Entry<String, Object> entry : data.entrySet()) {
//                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue() + ""));
//            }
//        }
//
//        JSONObject jsonObject = new JSONObject(data);
//        //将参数编码格式设置为UTF-8
//        StringEntity stringEntity = new StringEntity(jsonObject.toString(),"UTF-8");
//        //创造一个全局变量，能够返回
//        try {
//            post.setEntity(stringEntity);
//            //提交post请求信息
////            post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
////            addTokenOrCookie(post);
//            addCookieInRequestHeaderBeforeRequest(post);
//            //获取post的返回信息
//            response = client.execute(post);
//            getTokenOrCookie(response);
//            //拿到接口的code值
//            int code = response.getStatusLine().getStatusCode();
//
//            //拿到接口的返回报文，转成String
//            result = EntityUtils.toString(response.getEntity());
////            result=jsonObject.toJSONString();
//            gettoken(result);
//            logger.info("接口响应报文：code:["+code+"],结果:["+result+"]");
//            if (!("200".equals(String.valueOf(code)))){
//                logger.warn("请求失败，当前状态码：" + code);
//                logger.warn("请求参数：" + jsonObject);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//        //资源关闭
//        try {
//            if (client != null) {
//                logger.info("关闭当前client连接");
//                client.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            if ( response!= null) {
//                logger.info("关闭当前response连接");
//                response.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//        return result;
//    }

    /**
     * post请求
     * @param url     接口请求地址
     * @param params 接口请求参数
     * @return
     */

    public static String doPost(String url, Map<String, Object> params) {
        logger.info("开始调用doPost()，此方法用于发起Post请求");
        //指定请求方式
        HttpPost httpPost = new HttpPost(url);
        //准备测试数据
        List<BasicNameValuePair> paramenters = new ArrayList<>();
        //指定请求头
        httpPost.addHeader("X-Lemonban-Media-Type","lemonban.v2");
        httpPost.addHeader("Content-Type","application/json");
        addCookieInRequestHeaderBeforeRequest(httpPost);
        //根据获取得数据，添加请求头（方法将在下面以代码块形式贴出）
        //1.为什么是使用Object而不是String？
        //因为我们要处理的参数肯定不是一种类型。
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramenters.add(new BasicNameValuePair(entry.getKey(),entry.getValue() + ""));
            }
        }
        //将参数转为json格式
        JSONObject jsonObject = new JSONObject(params);
        //将参数编码格式设置为UTF-8
        StringEntity stringEntity = new StringEntity(jsonObject.toString(),"UTF-8");
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String result = null;
        try {
            httpPost.setEntity(stringEntity);
//            httpPost.setEntity(new UrlEncodedFormEntity(paramenters, "UTF-8"));
            client = HttpClients.createDefault();
            //发起请求，获取接口响应信息(状态码，响应报文，或某些特殊的响应头数据）
            response = client.execute(httpPost);
            logger.info("请求url：" + url);
//            logger.info("请求参数：" + jsonObject);
            //判断请求头中是否带有Set-cookie
//            getTokenOrCookie(response);
            //获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //获取响应体
            result = EntityUtils.toString(response.getEntity());
            gettokenss(result);
            logger.info("状态码[" + statusCode + "]，响应报文[" + result + "]");
            //判断result中的返回结果是否带有token，如果有添加到map保存（方法将在下面以代码块形式贴出）

            if (!("200".equals(String.valueOf(statusCode)))){
                logger.warn("请求失败，当前状态码：" + statusCode);
//                logger.warn("请求参数：" + jsonObject);
            }
        } catch (Exception e) {
            logger.error("运行时异常，报错内容：" + e);
        } finally {
            //资源关闭
            try {
                if (client != null) {
                    logger.info("关闭当前client连接");
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response != null) {
                    logger.info("关闭当前response连接");
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回响应结果
        return result;
    }

    /**
     * 判断map中是否存有值，如果不为空设置为请求头
     * @param request http请求返回得结果
     */
    private static void addCookieInRequestHeaderBeforeRequest(HttpRequest request) {
        logger.info("开始调用addCookieInRequestHeaderBeforeRequest()，此方法用于添加请求头");
        Object getToken=token.get("token");
        if (getToken != null) {
            logger.info("添加token到请求头");
            request.addHeader("token", (String) getToken);
            logger.info("token:"+getToken);
        }
    }


    private static void gettoken(String result) {
        logger.info("开始调用getResultValueLoad()，此方法用于获取result中的token");
        //将result转为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(result);
        try {
            //取出key为data的数据
            Object data = jsonObject.get("data");
            //data不为空则转型为map
            if (data != null && data.toString().trim().length() > 0) {
                //getStringToMap():用于String转map，以代码块形式在下面贴出
                Map<String, String> stringToMap = getStringToMap(String.valueOf(data));
                for (String name : stringToMap.keySet()) {
                    String value = stringToMap.get(name);
                    if ("\"token\"".equalsIgnoreCase(name)) {
                        logger.info("将当前token：" + name + "并添加到map储存");
                        logger.info("将当前token：" + value + "并添加到map储存");
                        token.put(name, value);
                        Iterator it=token.keySet().iterator();
                        while (it.hasNext()) {
                            Object key =it.next();
                            System.out.println( "key is "+key);
                            System.out.println( "value is "+token.get(key));
                        }

                    }
                }
            }
            //如果result中没有包含data实体，调用data.toString().trim().length()，可能会引发空指针，所以使用了try/catch
        }catch (NullPointerException e) {
            logger.error("空指针异常，请检查result中是否包含data",e);
        }

    }

    private static void gettokenss(String result) {
        logger.info("开始调用getResultValueLoad()，此方法用于获取result中的token");
        //将result转为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(result);
        try {
            //取出key为data的数据
            JSONObject jsonObject1=JSONObject.parseObject(jsonObject.get("data").toString());
            Object jsonObject2=jsonObject1.get("token_info");

            if (jsonObject2 != null && jsonObject2.toString().trim().length() > 0) {
                String token1="token";
                logger.info("将当前token：" + token1 + "并添加到map储存");
                logger.info("将当前token：" + jsonObject2 + "并添加到map储存");
                token.put(token1,jsonObject2);

            }
            //如果result中没有包含data实体，调用data.toString().trim().length()，可能会引发空指针，所以使用了try/catch
        }catch (NullPointerException e) {
            logger.error("空指针异常，请检查result中是否包含data",e);
        }

    }
//    public static void gettokens(String result){
//        JSONObject jsonObject=JSONObject.parseObject(result);
//        JSONObject jsonObject1=JSONObject.parseObject(jsonObject.get("data").toString());
//        jsonObject1.get("token");
//
//    }



    public static void main(String[] args) {

    }

    /**
     * String转map
     * @param str
     * @return
     */
    public static Map<String,String> getStringToMap(String str){
        logger.info("开始调用getStringToMap()，此方法用于String转map");
        Map<String,String> map = null;
        if (str != null && str.trim().length() > 0) {
            //根据逗号截取字符串数组
            String[] str1 = str.split(",");
            //创建Map对象
            map = new HashMap<>();
            //循环加入map集合
            for (int i = 0; i < str1.length; i++) {
                //根据":"截取字符串数组
                try {
                    String[] str2 = str1[i].split(":");
                    String s=str2[1];
                    s=s.substring(0,s.length()-1);
                    //str2[0]为KEY,str2[1]为值
                    map.put(str2[0], s);
                } catch (ArrayIndexOutOfBoundsException e) {
                    logger.error("请检查传入参数："+str+"，出现索引越界！");
                    return null;
                }
            }
        }
        return map;
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
    public static String doGet(String url, Map<String, Object> data) {
        logger.info("接口请求地址"+url);
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
            logger.info("接口响应报文：code:["+code+"],结果:["+text+"]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


    public static String doService(String url,String type,Map<String,Object>params) {
        String result="";
        if ("post".equals(type)) {
            result=Httputil.doPost(url, params);
        } else if ("get".equals(type)) {
            result=Httputil.doGet(url, params);
        }
        logger.info("拿到的请求结果是"+type);
        return result;
}






}
