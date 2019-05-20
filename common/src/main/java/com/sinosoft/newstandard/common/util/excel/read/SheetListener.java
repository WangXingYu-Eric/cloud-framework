package com.sinosoft.newstandard.common.util.excel.read;

import java.util.List;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface SheetListener {
    void addRow(List<String> row, int rowNum);
}
