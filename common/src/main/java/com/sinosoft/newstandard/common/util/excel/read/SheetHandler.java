package com.sinosoft.newstandard.common.util.excel.read;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private SheetListener sheetListener;
    private List<String> rowData = new ArrayList<>();

    public SheetHandler(SheetListener sheetListener) {
        this.sheetListener = sheetListener;
    }

    @Override
    public void startRow(int rowNum) {
        rowData.clear();
    }

    @Override
    public void endRow(int rowNum) {
        sheetListener.addRow(rowData, rowNum);
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        this.rowData.add(formattedValue);
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {

    }

}
