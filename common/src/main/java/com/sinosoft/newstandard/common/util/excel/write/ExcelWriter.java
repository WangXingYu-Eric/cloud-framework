package com.sinosoft.newstandard.common.util.excel.write;

import com.sinosoft.newstandard.common.util.DateUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Excel文件写入封装,使用说明:
 * <pre>
 *      ExcelWriter excelWriter = new ExcelWriter();
 *      excelWriter.setDateFormat("日期格式");
 *      excelWriter.fillExcel("sheet1", 表头数据, excelWriter.headStyle(), 内容数据, excelWriter.bodyStyle());
 *      excelWriter.saveFile(excelWriter.getWorkbook(), "文件绝对路径"); 或者 excelWriter.saveToWeb(excelWriter.getWorkbook(),response,"文件名称");
 * </pre>
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class ExcelWriter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String dateFormat = "yyyy-MM-dd";

    private Workbook workbook = new SXSSFWorkbook();

    /**
     * 填充表格数据
     *
     * @param sheetName sheet名称
     * @param headData  表头数据
     * @param headStyle 表头样式
     * @param bodyData  内容数据
     * @param bodyStyle 内容样式
     */
    public void fillExcel(String sheetName, String[] headData, CellStyle headStyle, List<List<Object>> bodyData, CellStyle bodyStyle) {
        Sheet sheet = getSheet(workbook, sheetName);
        fillExcel(headData, headStyle, bodyData, bodyStyle, sheet);
    }

    /**
     * 填充表格数据
     *
     * @param sheetIndex sheet索引位置(起始值为0)
     * @param headData   表头数据
     * @param headStyle  表头样式
     * @param bodyData   内容数据
     * @param bodyStyle  内容样式
     */
    public void fillExcel(int sheetIndex, String[] headData, CellStyle headStyle, List<List<Object>> bodyData, CellStyle bodyStyle) {
        Sheet sheet = getSheet(workbook, sheetIndex);
        fillExcel(headData, headStyle, bodyData, bodyStyle, sheet);
    }

    private void fillExcel(String[] headData, CellStyle headStyle, List<List<Object>> bodyData, CellStyle bodyStyle, Sheet sheet) {
        setHead(sheet, headData, headStyle);
        for (int i = 1; i <= bodyData.size(); i++) {
            Row row = getRow(sheet, i);
            List<Object> rowData = bodyData.get(i - 1);
            setRowValue(row, rowData, bodyStyle);
        }
    }

    /**
     * 填充表格数据
     *
     * @param sheetName sheet名称
     * @param bodyData  内容数据
     * @param bodyStyle 内容样式
     * @param startNum  填充起始行数:表格将从(startNum+1)行开始填充
     */
    public void fillExcel(String sheetName, List<List<Object>> bodyData, CellStyle bodyStyle, int startNum) {
        Sheet sheet = getSheet(workbook, sheetName);
        fillExcel(bodyData, bodyStyle, startNum, sheet);
    }

    /**
     * 填充表格数据
     *
     * @param sheetIndex sheet索引位置(起始值为0)
     * @param bodyData   内容数据
     * @param bodyStyle  内容样式
     * @param startNum   填充起始行数:表格将从(startNum+1)行开始填充
     */
    public void fillExcel(int sheetIndex, List<List<Object>> bodyData, CellStyle bodyStyle, int startNum) {
        Sheet sheet = getSheet(workbook, sheetIndex);
        fillExcel(bodyData, bodyStyle, startNum, sheet);
    }

    private void fillExcel(List<List<Object>> bodyData, CellStyle bodyStyle, int startNum, Sheet sheet) {
        for (int i = 0; i < bodyData.size(); i++) {
            Row row = getRow(sheet, i + startNum);
            List<Object> rowData = bodyData.get(i);
            setRowValue(row, rowData, bodyStyle);
        }
    }

    /**
     * 保存excel文件
     *
     * @param workbook  Workbook对象
     * @param excelPath 文件绝对路径
     */
    public void saveFile(Workbook workbook, String excelPath) {
        try (FileOutputStream outputStream = new FileOutputStream(new File(excelPath))) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("保存文件失败,失败原因:{}", e.getMessage());
        }
    }

    /**
     * web形式保存excel文件.
     *
     * @param workbook Workbook对象
     * @param response HttpServletResponse对象
     * @param fileName 文件名称
     */
    public void saveToWeb(Workbook workbook, HttpServletResponse response, String fileName) {
        try {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            logger.error("保存文件失败,失败原因:{}", e.getMessage());
        }
    }

    synchronized private Sheet getSheet(Workbook workbook, int index) {
        Sheet sheet = workbook.getSheetAt(index);
        if (sheet == null) {
            try {
                sheet = workbook.createSheet();
            } catch (Exception e) {
                logger.error("创建Sheet对象失败,失败原因:{}", e.getMessage());
            }
        } else {
            logger.info("提示:已存在第{}个Sheet.", index + 1);
        }
        return sheet;
    }

    synchronized private Sheet getSheet(Workbook workbook, String name) {
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            try {
                sheet = workbook.createSheet(name);
            } catch (Exception e) {
                logger.info("创建Sheet对象失败,失败原因:{}", e.getMessage());
            }
        } else {
            logger.error("提示:已存在名称为{}的Sheet.", name);
        }
        return sheet;
    }

    synchronized private Row getRow(Sheet sheet, int rownum) {
        Row row = sheet.getRow(rownum);
        if (row == null) {
            try {
                row = sheet.createRow(rownum);
            } catch (Exception e) {
                logger.info("创建Row对象失败,失败原因:{}", e.getMessage());
            }
        } else {
            logger.info("提示:已存在第{}行.", rownum + 1);
        }
        return row;
    }

    synchronized private Cell getCell(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null) {
            try {
                cell = row.createCell(cellnum);
            } catch (Exception e) {
                logger.info("创建Cell对象失败,失败原因:{}", e.getMessage());
            }
        } else {
            logger.info("提示:已存在第{}行第{}列.", row.getRowNum() + 1, cellnum + 1);
        }
        return cell;
    }

    /**
     * 设置表头数据.
     */
    private void setHead(Sheet sheet, String[] headData, CellStyle cellStyle) {
        Row headRow = getRow(sheet, 0);
        setRowValue(headRow, new ArrayList<>(Arrays.asList(headData)), cellStyle);
    }

    /**
     * 设置行数据(有样式).
     *
     * @param row       row
     * @param rowData   rowData
     * @param cellStyle cellStyle,无样式传null
     */
    private void setRowValue(Row row, List<Object> rowData, CellStyle cellStyle) {
        for (int i = 0; i < rowData.size(); i++) {
            Cell cell = getCell(row, i);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            setCellValue(cell, rowData.get(i));
        }
    }

    /**
     * 设置单元格的值.
     */
    private void setCellValue(Cell cell, Object value) {
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue(DateUtils.dateToString((Date) value, dateFormat));
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else {
            cell.setCellValue(String.valueOf(value));
            cell.setCellType(CellType.STRING);
            logger.info("提示:不是常用类型.");
        }
    }

    /**
     * 表头样式:宋体、12号、粗体、水平居中对齐、垂直居中对齐、蓝色背景填充、有边框.
     */
    public CellStyle headStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getColor().getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        setBorder(cellStyle);
        return cellStyle;
    }

    /**
     * 表格样式:宋体、11号、水平左对齐、垂直居中对齐、有边框.
     */
    public CellStyle bodyStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(cellStyle);
        return cellStyle;
    }

    /**
     * 设置边框.
     */
    private void setBorder(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
    }

    /**
     * 设置表格时间样式,默认样式'yyyy-MM-dd'.
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

}
