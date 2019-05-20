package com.sinosoft.newstandard.common.util.excel.read;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 事件模式读取Excel,使用说明:
 * <pre>
 *      ExcelParser excelParser = new ExcelParser("Excel路径", new SheetHandler((row, rowNum) -> {
 *          对row的处理逻辑
 *      }));
 *      excelParser.parse();
 * </pre>
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class ExcelParser {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String fileName;
    private SheetHandler customSheetContentsHandler;

    public ExcelParser(String fileName, SheetHandler customSheetContentsHandler) throws Exception {
        if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            this.fileName = fileName;
        } else {
            throw new Exception(fileName + "的文件名格式不正确.");
        }
        this.customSheetContentsHandler = customSheetContentsHandler;
    }

    public void parse() {
        try (OPCPackage opcPackage = OPCPackage.open(fileName, PackageAccess.READ)) {
            ReadOnlySharedStringsTable readOnlySharedStringsTable = new ReadOnlySharedStringsTable(opcPackage);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            StylesTable stylesTable = xssfReader.getStylesTable();
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setContentHandler(new XSSFSheetXMLHandler(stylesTable, readOnlySharedStringsTable, customSheetContentsHandler, false));
            try (InputStream sheetStream = xssfReader.getSheetsData().next()) {
                InputSource sheetSource = new InputSource(sheetStream);
                xmlReader.parse(sheetSource);
            }
        } catch (IOException | OpenXML4JException | SAXException e) {
            logger.error("{}解析失败:失败原因:{}", fileName, e.getMessage());
        }
    }

}
