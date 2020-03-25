package com.yaochen.tester.tools;


import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
kaishiRow传入开始行号
jieshuRow传入结束行号
kaishiCall传入开始列号
jieshuCall传入结束列号
 */
public class ExcelUtil {
    public static Map<String,Integer>caseIdRownumMap=new HashMap<String, Integer>();
    public static Map<String,Integer>caseIdCellnumMap=new HashMap<String, Integer>();
    public static List<WriteBackData> writeBackDatas=new ArrayList<WriteBackData>();
    static {
        loadRowAndCellMap("src/main/resources/casesV1.xlsx","login");
    }
    /*
    获取caseID以及它对应的行索引
    获取cellname以及它相应的列索引
     */
    private static void loadRowAndCellMap(String excelpath,String sheetName){
        InputStream inputStream=null;
        try {
            //提供一个输入流
            inputStream=new FileInputStream(new File(excelpath));
            //创建一个workbook对象
            Workbook workbook=WorkbookFactory.create(inputStream);
            //用workbook对象打开指定表单
            Sheet sheet=workbook.getSheet(sheetName);
            //获取第一行
            Row titleRow=sheet.getRow(0);
            //判断第一行不等于空
            if (titleRow!=null&&!isEmptyRow(titleRow)){
                //获取最大列
                int lastCellnum=titleRow.getLastCellNum();
                //循环处理标题行每一列
                for (int i = 0; i <lastCellnum ; i++) {
                    Cell cell=titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //讲列单元格转成string类型
                    cell.setCellType(CellType.STRING);
                    //获取转化字符串的值
                    String title=cell.getStringCellValue();
                    //把列值进行字符串切割
                    title = title.substring(0,title.indexOf("("));
                    //获取列索引
                    int cellnum=cell.getAddress().getColumn();
                    caseIdCellnumMap.put(title,cellnum);
                }
            }
            //获取所有的数据行
            int lastRownum=sheet.getLastRowNum();
            //循环拿到每一个数据行
            for (int i = 1; i <=lastRownum ; i++) {
                Row datarow=sheet.getRow(i);
                Cell firstCellOfRow=datarow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCellOfRow.setCellType(CellType.STRING);
                String caseId=firstCellOfRow.getStringCellValue();
                int rownum=datarow.getRowNum();
                caseIdRownumMap.put(caseId,rownum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        }

    }
    //返回的是一个二维数组 需要传入的值有文件路径，开始行号，结束行号，开始列号，结束列号
    public static Object[][] datas(String excelpath,String sheetname,int [] rows,int [] calls) {
        Object[][] datas = null;
        try {
            //打开一个workbook工作区间
            Workbook workbook = WorkbookFactory.create(new File(excelpath));
            //拿到你要执行的表单
            Sheet sheet = workbook.getSheet(sheetname);
            //获取到二位数组有几行 几列
            datas = new Object[rows.length][calls.length];
            //从开始行号开始循环，结束行号结束循环
            for (int i = 0; i <=rows.length-1; i++) {
                //循环获取每一行
                Row row = sheet.getRow(rows[i]-1);
                //从开始列数开始循环，到结束列号结束
                for (int j = 0; j <= calls.length-1; j++) {
                    //循环获取每一列
                    Cell call = row.getCell(calls[j]-1);
                    //把每一列的数据改成字符串类型
                    call.setCellType(CellType.STRING);
                    //拿到列的值
                    String value = call.getStringCellValue();
                    //从第0个数组开始添加
                    datas[i][j] = value;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return datas;
    }

    public static <T>void load(String excelPath, String sheetName, Class<T> clazz) {
        try {

            Workbook workbook= WorkbookFactory.create(new File(excelPath));
            Sheet sheet=workbook.getSheet(sheetName);
            //获取第零行
            Row titleRow=sheet.getRow(0);
            //获取最后一列的列号
            int lastcallnum=titleRow.getLastCellNum();
            //循环处理每一列
            String [] fields=new String[lastcallnum];
            for (int i=0;i<lastcallnum;i++){
                //根据索引获取对应的列
                Cell cell=titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //将全部的列设置为字符串
                cell.setCellType(CellType.STRING);
                //获取列的值
                String title=cell.getStringCellValue();
                title=title.substring(0,title.indexOf("("));
                fields[i]=title;
            }
            int lastRowIndex=sheet.getLastRowNum();
            for (int i = 1; i <=lastRowIndex; i++) {
                Object obj= clazz.newInstance();
                Row dataRow=sheet.getRow(i);
                for (int j=0;j<lastcallnum;j++){
                    if (dataRow==null||isEmptyRow(dataRow)){
                        continue;
                    }
                    Cell cell=dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value=cell.getStringCellValue();
                    String methodName="set"+fields[j];
                    Method method=clazz.getMethod(methodName,String.class);
                    method.invoke(obj,value);
                }
                if (obj instanceof Case){
                    Case cs= (Case) obj;
                    CaseUtil.cases.add(cs);
                }else if (obj instanceof Rest){
                    Rest rest=(Rest) obj;
                    RestUtil.rests.add(rest);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static boolean isEmptyRow(Row dataRow){
        int lastCellNum=dataRow.getLastCellNum();
        for (int i=0;i<lastCellNum;i++){
            Cell cell=dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            if (value!=null&&value.trim().length()>0){
                return false;
            }
        }
            return true;

    }
    /*
    回写测试结果
    回写接口响应报文
    excelpath：传入文件路径
    sheetname：传入表单名称
    caseId：传入caseID
    cellName：传入回写的字段名
    result：填入结果
     */

    public static void writeBackData(String excelpath,String sheetname,String caseId,String cellName,String result) {
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            inputStream=new FileInputStream(new File(excelpath));
            Workbook workbook=WorkbookFactory.create(inputStream);
            Sheet sheet=workbook.getSheet(sheetname);
            int rownum=caseIdRownumMap.get(caseId);
            Row row=sheet.getRow(rownum);
            int cellnum=caseIdCellnumMap.get(cellName);
            Cell cell=row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            outputStream=new FileOutputStream(new File(excelpath));
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (outputStream!=null){
                    outputStream.close();
                }if (inputStream!=null){
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void batchWriteBackDatas(String excelpath) {
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try {
            inputStream=new FileInputStream(new File(excelpath));
            Workbook workbook=WorkbookFactory.create(inputStream);
            //循环集合来取出来数据
            for (WriteBackData writeBackData:writeBackDatas){
                //取出sheetname
                String sheetname=writeBackData.getSheetName();
                //打开表单对象
                Sheet sheet=workbook.getSheet(sheetname);
                //取出caseID，也就是行索引
                String caseid=writeBackData.getCaseId();
                int rownum=caseIdRownumMap.get(caseid);
                Row row=sheet.getRow(rownum);
                String cellName=writeBackData.getCellName();
                int cellnum=caseIdCellnumMap.get(cellName);
                Cell cell=row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String result=writeBackData.getResult();
                cell.setCellValue(result);
            }
            outputStream =new FileOutputStream(new File(excelpath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



}
