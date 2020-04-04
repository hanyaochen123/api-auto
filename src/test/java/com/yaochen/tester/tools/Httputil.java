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

import java.text.DateFormat;
import java.util.*;

public class Httputil {
    public static Logger logger=Logger.getLogger(Httputil.class);
    public static Map<String,String> tokenorcookie=new HashMap<String, String>();
    public static String doPost(String url, Map<String, Object> data) {
        String result = "";
        logger.info("接口请求地址"+url);
        //创造一个模拟浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //发送一个post请求
        HttpPost post = new HttpPost(url);
        //创建一个List集合来存数据
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        post.setHeader("X-Lemonban-Media-Type","lemonban.v2");
        post.setHeader("Content-Type","application/json");

//        //获取data数据的name
//        Set<String> keys = data.keySet();
//        //循环取出name
//        for (String name : keys) {
//            //data.get（name）方法根据name取出value
//            Object value = data.get(name);
//            //把数据添加到list集合里面
//            list.add(new BasicNameValuePair(name, (String) value));
//        }
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue() + ""));
            }
        }

        JSONObject jsonObject = new JSONObject(data);
        //将参数编码格式设置为UTF-8
        StringEntity stringEntity = new StringEntity(jsonObject.toString(),"UTF-8");
        //创造一个全局变量，能够返回
        try {
            post.setEntity(stringEntity);
            //提交post请求信息
//            post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            addTokenOrCookie(post);
            //获取post的返回信息
            HttpResponse httpResponse = httpClient.execute(post);
            getTokenOrCookie(httpResponse);
            //拿到接口的code值
            int code = httpResponse.getStatusLine().getStatusCode();

            //拿到接口的返回报文，转成String
            result = EntityUtils.toString(httpResponse.getEntity());
//            result=jsonObject.toJSONString();
            logger.info("数据:"+jsonObject);
            logger.info("接口响应报文：code:["+code+"],结果:["+result+"]");
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

//    /**
//     * 拆分获取response的返回结果，将token添加到map
//     * @param result  Http请求返回result结果
//     */
//    public static void getResultToken(String result) {
//        logger.info("开始调用getResultValueLoad()，此方法用于获取result中的token");
//        //将result转为JSONObject
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        try {
//            //取出key为data的数据
//            Object data = jsonObject.get("data");
//            //data不为空则转型为map
//            if (data != null && data.toString().trim().length() > 0) {
//                //getStringToMap():用于String转map，以代码块形式在下面贴出
//                Map<String, String> stringToMap = getStringToMap(String.valueOf(data));
//                for (String name : stringToMap.keySet()) {
//                    String value = stringToMap.get(name);
//                    if ("\"token\"".equalsIgnoreCase(name)) {
//                        logger.info("将当前token：" + value + "并添加到map储存");
//                        token.put(name, value);
//                    }
//                }
//            }
//            //如果result中没有包含data实体，调用data.toString().trim().length()，可能会引发空指针，所以使用了try/catch
//        }catch (NullPointerException e) {
//            logger.error("空指针异常，请检查result中是否包含data",e);
//        }
//    }
//
//    /**
//     * String转map
//     * @param str
//     * @return
//     */
//    public static Map<String,String> getStringToMap(String str){
//        logger.info("开始调用getStringToMap()，此方法用于String转map");
//        Map<String,String> map = null;
//        if (str != null && str.trim().length() > 0) {
//            //根据逗号截取字符串数组
//            String[] str1 = str.split(",");
//            //创建Map对象
//            map = new HashMap<>();
//            //循环加入map集合
//            for (int i = 0; i < str1.length; i++) {
//                //根据":"截取字符串数组
//                try {
//                    String[] str2 = str1[i].split(":");
//                    //str2[0]为KEY,str2[1]为值
//                    map.put(str2[0], str2[1]);
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    logger.error("请检查传入参数："+str+"，出现索引越界！");
//                    return null;
//                }
//            }
//        }
//        return map;
//    }
//    /**
//     * 判断map中是否存有值，如果不为空设置为请求头
//     * @param request http请求返回得结果
//     */
//    private static void addCookieInRequestHeaderBeforeRequest(HttpRequest request) {
//        logger.info("开始调用addCookieInRequestHeaderBeforeRequest()，此方法用于添加请求头");
//        String getToken = Httputil.token.get("token");
//        if (getToken != null) {
//            logger.info("添加token到请求头");
//            request.addHeader("token",getToken);
//        }
//    }




}
