package com.yaochen.tester.api;

//public class DDD {
//    /**
//     * post请求
//     * @param url     接口请求地址
//     * @param params 接口请求参数
//     * @return
//     */
//    @RequestWrapper()
//    public static String doPost(String url, Map<String, Object> params) {
//        log.info("开始调用doPost()，此方法用于发起Post请求");
//        //指定请求方式
//        HttpPost httpPost = new HttpPost(url);
//        //准备测试数据
//        List<BasicNameValuePair> paramenters = new ArrayList<>();
//        //指定请求头
//        httpPost.addHeader("User-agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
//        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
//        //根据获取得数据，添加请求头（方法将在下面以代码块形式贴出）
//        addCookieInRequestHeaderBeforeRequest(httpPost);
//        //1.为什么是使用Object而不是String？
//        //因为我们要处理的参数肯定不是一种类型。
//        if (params != null) {
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                paramenters.add(new BasicNameValuePair(entry.getKey(),entry.getValue() + ""));
//            }
//        }
//        //将参数转为json格式
//        //JSONObject jsonObject = new JSONObject(params);
//        //将参数编码格式设置为UTF-8
//        //StringEntity stringEntity = new StringEntity(jsonObject.toString(),"UTF-8");
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String result = null;
//        try {
//            //httpPost.setEntity(stringEntity);
//            httpPost.setEntity(new UrlEncodedFormEntity(paramenters, "UTF-8"));
//            client = HttpClients.createDefault();
//            //发起请求，获取接口响应信息(状态码，响应报文，或某些特殊的响应头数据）
//            response = client.execute(httpPost);
//            log.info("请求url：" + url);
//            log.info("请求参数：" + jsonObject);
//            //判断请求头中是否带有Set-cookie
//            getAndStoreCookiesFromResponseHeader(response);
//            //获取状态码
//            int statusCode = response.getStatusLine().getStatusCode();
//            //获取响应体
//            result = EntityUtils.toString(response.getEntity());
//            log.info("状态码[" + statusCode + "]，响应报文[" + result + "]");
//            //判断result中的返回结果是否带有token，如果有添加到map保存（方法将在下面以代码块形式贴出）
//            getResultToken(result);
//            if (!("200".equals(String.valueOf(statusCode)))){
//                log.warn("请求失败，当前状态码：" + statusCode);
//                log.warn("请求参数：" + jsonObject);
//            }
//        } catch (Exception e) {
//            log.error("运行时异常，报错内容：" + e);
//        } finally {
//            //资源关闭
//            try {
//                if (client != null) {
//                    log.info("关闭当前client连接");
//                    client.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (response != null) {
//                    log.info("关闭当前response连接");
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //返回响应结果
//        return result;
//    }
//}
