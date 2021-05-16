package pl.edu.agh.io.eventsOrganizer.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.edu.agh.io.eventsOrganizer.errors.NotFoundException;
import pl.edu.agh.io.eventsOrganizer.model.Classes;
import pl.edu.agh.io.eventsOrganizer.model.Instructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class ExcelExport {
    public static File exportToExcel(List<Classes> classesList, Optional<Instructor> instructor) throws IOException {
        if (instructor.isEmpty())
            throw new NotFoundException("", "", List.of());
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Classes");
        for (int i = 0; i < 10; i++)
            sheet.setColumnWidth(i, 6000);

        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 8500);
        sheet.setColumnWidth(5, 7000);

        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("First Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Last Name");
        headerCell.setCellStyle(headerStyle);

        Row a = sheet.createRow(1);
        a.createCell(0).setCellValue(instructor.get().getFirstName());
        a.createCell(1).setCellValue(instructor.get().getLastName());

        Row b = sheet.createRow(3);
        headerCell = b.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(1);
        headerCell.setCellValue("Start Time");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(2);
        headerCell.setCellValue("End Time");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(3);
        headerCell.setCellValue("Number of Hours");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(4);
        headerCell.setCellValue("Appointment Number");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(5);
        headerCell.setCellValue("Students Group");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(6);
        headerCell.setCellValue("Classes Type");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(7);
        headerCell.setCellValue("Classes Form");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(8);
        headerCell.setCellValue("Classroom");
        headerCell.setCellStyle(headerStyle);

        headerCell = b.createCell(9);
        headerCell.setCellValue("Event");
        headerCell.setCellStyle(headerStyle);


        for (int i = 0; i < classesList.size(); i++) {
            Classes classes = classesList.get(i);
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy HH:mm"));

            Calendar startTime = Calendar.getInstance();
            startTime.set(
                    classes.getStartTime().getYear(),
                    classes.getStartTime().getMonthValue() - 1,
                    classes.getStartTime().getDayOfMonth(),
                    classes.getStartTime().getHour(),
                    classes.getStartTime().getMinute(),
                    classes.getStartTime().getSecond()
            );
            Calendar endTime = Calendar.getInstance();
            endTime.set(
                    classes.getEndTime().getYear(),
                    classes.getEndTime().getMonthValue() - 1,
                    classes.getEndTime().getDayOfMonth(),
                    classes.getEndTime().getHour(),
                    classes.getEndTime().getMinute(),
                    classes.getEndTime().getSecond()
            );

            Row newRow = sheet.createRow(4 + i);
            newRow.createCell(0).setCellValue(classes.getName());
            Cell cellStartDate = newRow.createCell(1);
            cellStartDate.setCellValue(startTime);
            cellStartDate.setCellStyle(dateStyle);
            Cell cellEndDate = newRow.createCell(2);
            cellEndDate.setCellValue(endTime);
            cellEndDate.setCellStyle(dateStyle);

            newRow.createCell(3).setCellValue(classes.getNumberOfHours());
            newRow.createCell(4).setCellValue(classes.getAppointmentNumber());
            newRow.createCell(5).setCellValue(classes.getStudentsGroup());
            newRow.createCell(6).setCellValue(classes.getClassesType().toString());
            newRow.createCell(7).setCellValue(classes.getClassesForm().toString());
            newRow.createCell(8).setCellValue(classes.getClassroom());
            newRow.createCell(9).setCellValue(classes.getEvent());
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + instructor.get().getFirstName() + "_" + instructor.get().getLastName() + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
        return new File(fileLocation);
    }
}
