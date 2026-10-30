package apiutilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class XLUtility {
    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    public XLUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowCount;
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellCount = row.getLastCellNum();
        workbook.close();
        fi.close();
        return cellCount;
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = (row == null) ? null : row.getCell(colnum);

        DataFormatter formatter = new DataFormatter();
        String data;

        try {
            if (cell == null) {
                data = "";
            } else {
                data = formatter.formatCellValue(cell);
            }
        } catch (Exception e) {
            throw new IOException("Unable to read cell data", e);
        } finally {
            workbook.close();
            fi.close();
        }

        return data;
    }

    // Set data in a particular cell
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        try {
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            row = sheet.getRow(rownum);
            if (row == null) {
                row = sheet.createRow(rownum);
            }
            cell = row.getCell(colnum);
            if (cell == null) {
                cell = row.createCell(colnum);
            }
            cell.setCellValue(data);
            fo = new FileOutputStream(path);
            workbook.write(fo);
        } finally {
            if (fo != null) {
                fo.close();
                fo = null;
            }
            workbook.close();
            fi.close();
        }
    }




}
