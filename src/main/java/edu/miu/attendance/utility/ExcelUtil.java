package edu.miu.attendance.utility;

import edu.miu.attendance.dto.AttendanceRecordExcelDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ExcelUtil {
    public static void generateExcel(List<AttendanceRecordExcelDTO> data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Joined Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Serial NO.");

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Student ID");
            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("First Name");
            Cell headerCell4 = headerRow.createCell(3);
            headerCell4.setCellValue("Last Name");
            Cell headerCell5 = headerRow.createCell(4);
            headerCell5.setCellValue("Faculty First Name");
            Cell headerCell6 = headerRow.createCell(5);
            headerCell6.setCellValue("Faculty Last Name");
            Cell headerCell7 = headerRow.createCell(6);
            headerCell7.setCellValue("Course Code");
            Cell headerCell8 = headerRow.createCell(7);
            headerCell8.setCellValue("Course Name");
            Cell headerCell9 = headerRow.createCell(8);
            headerCell9.setCellValue("Department");
            Cell headerCell10 = headerRow.createCell(9);
            headerCell10.setCellValue("Credits");
            Cell headerCell11 = headerRow.createCell(10);
            headerCell11.setCellValue("Scan Date Time");
            Cell headerCell12 = headerRow.createCell(11);
            headerCell12.setCellValue("Location Name");
            Cell headerCell13 = headerRow.createCell(12);
            headerCell13.setCellValue("Location Type");

            // Create data rows
            int rowNum = 1;
            for (AttendanceRecordExcelDTO dto : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum-1);
               row.createCell(1).setCellValue((dto.getStudentid()));
                 row.createCell(2).setCellValue(dto.getFirstName());
                row.createCell(3).setCellValue(dto.getLastName());
                row.createCell(4).setCellValue(dto.getFacultyFirstName());
                row.createCell(5).setCellValue(dto.getFacultyLastName());
                row.createCell(6).setCellValue(dto.getCourseCode());
                row.createCell(7).setCellValue(dto.getCourseName());
                row.createCell(8).setCellValue(dto.getDepartment());
                row.createCell(9).setCellValue(dto.getCredits());
                row.createCell(10).setCellValue(dto.getScanDateTime().toString());
                row.createCell(11).setCellValue(dto.getName());
                row.createCell(12).setCellValue(dto.getType());


            }

            // Save the output to the desktop
            String desktopPath = System.getProperty("user.home") + "/Desktop/joined_data_"+ LocalDateTime.now() +".xlsx";
            try (FileOutputStream fos = new FileOutputStream(desktopPath)) {
                workbook.write(fos);
            }

            // Write the output to a byte array
         /*   try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);
                return bos.toByteArray();
            }
            */

        }
    }
}
