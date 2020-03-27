package com.yaochen.tester.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDBCutil {
    public static void main(String[] args) {
        String sql="select name,age,gender from renlei where id=?;";
        Object [] values={"2",};
        query(sql,values);
    }
    public static Map<String,Object> query(String sql, Object ... values){
        //链接sql
        /**根据添加的sql查询表数据，并与map返回，key为字段名，value为数据
         *
         *
         *
         */
        Map<String,Object> map=null;
        try {
            //创建一个Properties实例
            Properties properties=new Properties();
            //输入流读取JDBC配置文件
            InputStream inputStream=new FileInputStream(new File("src/main/resources/JDBC.properties"));
            //从输入流读取键值对
            properties.load(inputStream);
            String url=properties.getProperty("jdbc.url");
            String user=properties.getProperty("jdbc.user");
            String password=properties.getProperty("jdbc.password");
            Connection connection= DriverManager.getConnection(url,user,password);
            //提供PreparedStatement对象 此对象拥有sql里的操作方法
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            //设置问号字段的值，一个字段设置一个,如果没有占位符，则不用写下面这一行
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i+1,values[i]);
            }
            //调用查询方法，执行查询，返回resultSet结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            //查询相关的信息,得到表结构
            ResultSetMetaData metaData= resultSet.getMetaData();
            //查询列数
            int con=metaData.getColumnCount();
            map=new HashMap<String, Object>();
            while (resultSet.next()) {
                for (int i = 0; i <con ; i++) {
                    //从第一列开始获取字段名字
                    String label=metaData.getColumnLabel(i+1);
                    //获取每一个字段的值
                    String  convalue= resultSet.getObject(label).toString();
                    map.put(label,convalue);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
