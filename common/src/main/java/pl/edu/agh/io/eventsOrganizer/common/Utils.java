package pl.edu.agh.io.eventsOrganizer.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Utils {

    private Utils() {
    }

    public static String getLastLine(String filename) throws FileNotFoundException {
        List<String> fileContent = readFile(filename);
        return fileContent.size() == 0 ? null : fileContent.get(fileContent.size() - 1);
    }

    public static List<String> readFile(String filename) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String newLine = scanner.nextLine().strip();
            if (!newLine.isEmpty()) {
                result.add(newLine);
            }
        }
        return result;
    }
}
