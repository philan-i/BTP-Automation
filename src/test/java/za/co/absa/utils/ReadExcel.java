package za.co.absa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;


public class ReadExcel {

public static void main(String[]args){
    String postBody = "{ \"applicationNumber\":" + "\"applicationNumber\" }";
    System.out.println(postBody);

}
    @DataProvider(name = "scoringNumberTestData")
    public Iterator<Object[]> getData(String scoringNumber) throws Exception {
        File file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\testData.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet(scoringNumber);

        int totalRows = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int totalColumns = row.getLastCellNum();

        DataFormatter format = new DataFormatter();

        String testData[][] = new String[totalRows][totalColumns];
        for (int i = 1; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                testData[i - 1][j] = format.formatCellValue(sheet.getRow(i).getCell(j));
            }
        }
        //
        return getData(scoringNumber);
    }


}
