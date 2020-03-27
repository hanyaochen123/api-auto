package com.yaochen.tester.tools;

import com.alibaba.fastjson.JSONObject;
import com.yaochen.tester.pojo.DBChecker;
import com.yaochen.tester.pojo.DBQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBCheckUtil {
    /*
    根据脚本执行查询并返回查询结果
     */

    public static String doQuery(String validateSql) {
        //将脚本字符串封装成对象
        List<DBChecker>dbCheckers= JSONObject.parseArray(validateSql,DBChecker.class);
        List<DBQueryResult> dbQueryResults=new ArrayList<DBQueryResult>();
        for (DBChecker dbChecker:dbCheckers){
            //拿到编号
            String no=dbChecker.getNo();
            //拿到语句
            String sql=dbChecker.getSql();
            //执行查询 获取结果
            Map<String,Object>columenLabelAndValues=JDBCutil.query(sql);
            DBQueryResult dbQueryResult=new DBQueryResult();
            dbQueryResult.setNo(no);
            dbQueryResult.setColumenLabelAndValues(columenLabelAndValues);
            dbQueryResults.add(dbQueryResult);
        }
        return JSONObject.toJSONString(dbQueryResults);
    }
}
