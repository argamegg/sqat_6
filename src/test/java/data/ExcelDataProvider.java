package data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataProvider {

    private static final Path EXCEL_PATH = Path.of("testdata", "LoginData.xlsx");

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        List<Object[]> rows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH.toFile());
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();

            // row 0 = headers
            for (int i = 1; i <= lastRow; i++) {
                Row r = sheet.getRow(i);
                if (r == null) continue;

                String testCaseId = cellString(r.getCell(0));
                String username   = cellString(r.getCell(1));
                String password   = cellString(r.getCell(2));
                String expected   = cellString(r.getCell(3));

                if (testCaseId.isBlank()) continue;

                rows.add(new Object[]{testCaseId, username, password, expected});
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel: " + EXCEL_PATH, e);
        }

        return rows.toArray(new Object[0][]);
    }

    private static String cellString(Cell c) {
        if (c == null) return "";
        return switch (c.getCellType()) {
            case STRING -> c.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) c.getNumericCellValue());
            case BOOLEAN -> String.valueOf(c.getBooleanCellValue());
            case FORMULA -> c.getCellFormula().trim();
            default -> "";
        };
    }
}
