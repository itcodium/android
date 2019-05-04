package com.itcodium.dogeturljob;

public class New{
    String tableCount;
    String copyCount;
    String copyDate;
    String processDate;

    public New(String tableCount, String copyCount, String copyDate, String processDate) {
        this.tableCount = tableCount;
        this.copyCount = copyCount;
        this.copyDate = copyDate;
        this.processDate = processDate;
    }
    public String getTableCount() {
        return tableCount;
    }
    public String getCopyCount() {
        return copyCount;
    }
    public String getCopyDate() {
        return copyDate;
    }
    public String getProcessDate() {
        return processDate;
    }
}