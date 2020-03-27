//package com.yaochen.tester.api;
//
//public class QQQ {
//    /**
//     * String转map
//     * @param str
//     * @return
//     */
//    public static Map<String,String> getStringToMap(String str){
//        log.info("开始调用getStringToMap()，此方法用于String转map");
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
//                    log.error("请检查传入参数："+str+"，出现索引越界！");
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
//        log.info("开始调用addCookieInRequestHeaderBeforeRequest()，此方法用于添加请求头");
//                String getToken = HttpUtil.token.get("token");
//                if (getToken != null) {
//                log.info("添加token到请求头");
//                request.addHeader("token",getToken);
//                }
//                }
//@Test
//public void test_GetList() {
//        String url = "http://hd215.api.yesapi.cn/?s=App.User.GetList";
//        Map<String, Object> params = new HashMap<>();
//        //这里的key不方便展示，有需要的同学可以自行去官网获取
//        params.put("app_key","********");
//        params.put("page",1);
//        params.put("perpage",20);
//        doPost(url,params);
//        }
//
//
//        }
