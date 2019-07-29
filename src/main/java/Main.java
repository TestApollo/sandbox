import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*
Если есть слово Starter, создать новую строку и вставить это слово и каждое следующее после него в строку через запятую

 */

public class Main {
    public static void main(String[] args) throws Exception {
        String fileIn = "C:\\Users\\Артем\\Downloads\\new1.csv";
        String fileOut = "C:\\Users\\Артем\\Downloads\\new1s.csv";

        BufferedReader reader = new BufferedReader(new FileReader(fileIn));
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));

        String line = "";

        while ((line = reader.readLine()) != null) {

            if (line.startsWith("Starter") || line.startsWith("\\s"+"Starter")){
                writer.write("\n" + line);
            } else {
                writer.write(line);
            }
        }

        reader.close();
        writer.close();
    }
}