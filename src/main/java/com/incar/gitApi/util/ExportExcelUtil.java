package com.incar.gitApi.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class ExportExcelUtil {
    public static HSSFWorkbook exprotExcel(String[] tableHeader,String methods[],Object obj){
         HSSFWorkbook workbook =new HSSFWorkbook();
         HSSFSheet sheet= workbook.createSheet();
        //生成表格表头
        HSSFRow rowTitle= sheet.createRow(0);
        for (int i = 0; i < tableHeader.length; i++) {
            rowTitle.createCell(i).setCellValue(tableHeader[i]);
        }

        for (int i = 0; i < ((List)obj).size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            Object r = ((List)obj).get(i);
            for (int j = 0; j < methods.length; j++) {
                try {
                    Method m = r.getClass().getDeclaredMethod(methods[j]);
                    row.createCell(j).setCellValue(m.invoke(r).toString());
                } catch (Exception e) {

                }
            }
        }
        return workbook;
    }
}
