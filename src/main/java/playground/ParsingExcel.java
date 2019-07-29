package playground;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingExcel {
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        String file = "C:\\Users\\Артем\\Downloads\\Book_1.xlsx";
//        writeIntoExcel(file);
        String a = "USD 500";

        String currency = null;
        String salary = null;
//        for(String ss : s){
//            currency = ss;
//            salary = ss;
//        }

        if (a.startsWith("USD") && a.contains(" ")) {
            String[] curr = a.split("(\\d+)|(^\\s)");
            String[] sal = a.split("(^\\s)|(\\D+)");
            currency = Arrays.toString(curr);
            salary = Arrays.toString(sal);
        }
        System.out.println(currency);
        System.out.println(salary);

    }


    public static void readeFromExcel(String file) throws FileNotFoundException, IOException {
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
//        XSSFSheet myExcelSheet =
    }


    /*
    Запишем в xlsx файл следующие данные: в первую ячейку — строку с именем, а во вторую — дату рождения
     */
    public static void writeIntoExcel(String file) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        //Создаём лист, используя на объекте, созданном в предыдущем шаге, createSheet();
        Sheet sheet = workbook.createSheet("Birthdays");

        //Создаём на листе строку, используя createRow()
        Row row = sheet.createRow(0);

        //Создаём в строке ячейку — createCell();
        //Мы запишем имя и дату в два столбца
        //имя будет String, а дата рождения --- Date,
        //формата dd.mm.yyyy
        Cell name = row.createCell(0);
        name.setCellValue("Hustle");

        //Чтобы записать дату, нам понадобится сделать ещё кое-что:
        Cell birthday = row.createCell(1);

        //Создать DateFormat;
        DataFormat format = workbook.createDataFormat();

        //Создать CellStyle;
        CellStyle dateStyle = workbook.createCellStyle();

        //Записать DateFormat в CellStyle;
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));

        //Записать CellStyle в ячейку;
        birthday.setCellStyle(dateStyle);

        //Теперь в эту ячейку можно записать объект Date через всё тот же setCellValue;
        // Нумерация лет начинается с 1900-го
        birthday.setCellValue(new Date(113, 10, 3));

        //Чтобы дата поместилась в ячейку, нам нужно добавить столбцу свойство автоматически менять размер: sheet.autoSizeColumn(1).
        sheet.autoSizeColumn(1);

        workbook.write(new FileOutputStream(file));
        workbook.close();
    }


    public void candidatesDataParsing() throws Exception{
        System.out.println("testCandidateFromCSVFile");

        File fileIn = new File("C:\\Users\\Артем\\Downloads\\CandidateParser\\data_001\\Data\\Notes_001.csv");
        File fileOut = new File("C:\\Users\\Артем\\Downloads\\CandidateParser\\Notes_001_fixed.csv");

        try {
            FileReader fr = new FileReader(fileIn);
            BufferedReader reader = new BufferedReader(fr);

            FileWriter fw = new FileWriter(fileOut);
            BufferedWriter writer = new BufferedWriter(fw);

            String line = "";

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Zrecruit")) {
                    writer.write("\n" + line);
                } else {
                    writer.write(line);
                }
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
