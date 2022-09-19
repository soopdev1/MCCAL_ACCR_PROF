/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.action;

import static com.seta.action.Constant.bando;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author raffaele
 */
public class Excel {

    public static void excel(File excelin, File excelout) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelin));
            HSSFCellStyle hsfstyleL = wb.createCellStyle();
            hsfstyleL.setBorderBottom((short) 2);
            hsfstyleL.setBorderLeft((short) 2);
            hsfstyleL.setBorderRight((short) 1);
            hsfstyleL.setFillBackgroundColor((short) 245);
            
            HSSFCellStyle hsfstyleS = wb.createCellStyle();
            hsfstyleS.setBorderBottom((short) 2);
            hsfstyleS.setBorderLeft((short) 1);
            hsfstyleS.setBorderRight((short) 2);
            hsfstyleS.setFillBackgroundColor((short) 245);
            
            
            
            Sheet sh1 = wb.getSheetAt(0);
            int ri = 3;
            Row row = sh1.createRow(3);
            Cell c = row.createCell(1);
            c.setCellValue("RAF");
            c.setCellStyle(hsfstyleL);
            
            Cell c1 = row.createCell(21);
            String strFormula = "SUM(P" + (ri+1) + ":U" + (ri+1) + ")";
            c1.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            c1.setCellFormula(strFormula);
            c1.setCellStyle(hsfstyleS);
            
            
           FileOutputStream fos = new FileOutputStream(excelout);
            wb.write(fos);
        } catch (IOException ex) {
            Constant.log.severe(bando+": "+ex.getMessage());
        }
    }

    public static void main(String[] args) {
        excel(new File("TemplateGradParz.xls"), new File("TemplateGradParz__1.xls"));
    }

}
