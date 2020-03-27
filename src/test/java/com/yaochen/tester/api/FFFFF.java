//package com.yaochen.tester.api;
//
//public class FFFFF {
//
//    /**
//     *get请求
//     * @param url 接口请求地址
//     * @param params 请求参数
//     * @return
//     */
//    public static String doGet(String url, Map <String, Object> params){
//        log.info("开始调用doGet()，此方法用于发起get请求");
//        int number = 1;
//        for (Map.Entry<String ,Object> entry : params.entrySet()) {
//            //如果number=1，那就代表再url与参数的拼接处，以"？"拼接
//            url += (number == 1) ? ("?" + entry.getKey() + "=" + entry.getValue()) : ("&" + entry.getKey() + "=" + entry.getValue());
//            log.info("当前请求url和params：" + url);
//            number++;
//        }
//        //指定接口请求方式
//        HttpGet httpGet = new HttpGet(url);
//        //设置请求头
//        httpGet.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
//        addCookieInRequestHeaderBeforeRequest(httpGet);
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String result = "";
//        try {
//            //发送请求获取响应数据实体
//            client = HttpClients.createDefault();
//            //将获取到得数据，添加到请求头
//            addCookieInRequestHeaderBeforeRequest(httpGet);
//            response = client.execute(httpGet);
//            //获取http状态码
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (!("200".equals(String.valueOf(statusCode)))) {
//                log.warn("请求失败,请检查请求参数及请求头！");
//            }
//            //获取http响应实体
//            result = EntityUtils.toString(response.getEntity());
//            log.info("状态码[" + statusCode + "]，响应报文[" + result + "]");
//            //如果result中有有token，就添加到map
//            getResultToken(result);
//        } catch (Exception e) {
//            log.error("出现传输异常，请检查当前流！");
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
//        return result;
//    }
//}
//#根logger设置
//        log4j.rootLogger = INFO,console,file
//
//        ###输出信息到控制台###
//        log4j.appender.console = org.apache.log4j.ConsoleAppender
//        log4j.appender.console.Target = System.out
//        log4j.appender.console.layout = org.apache.log4j.PatternLayout
//        log4j.appender.console.layout.ConversionPattern = [%p] %d{yyyy-MM-dd HH:mm:ss} method: %l -> %m%n
//
//        ###输出 INFO 级别以上日志文件设置###
//        log4j.appender.file = org.apache.log4j.DailyRollingFileAppender
//        log4j.appender.file.File = target/apitest.log
//        log4j.appender.file.Append = true
//        log4j.appender.file.layout = org.apache.log4j.PatternLayout
//        log4j.appender.file.layout.ConversionPattern = %d{yyyy-MM-dd HH:mm:ss} method: %l - [ %p ] -> %m%n




