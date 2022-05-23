package SortTask;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class ReadArrFromFile {
    public double[] readArr(String filename) throws IOException {
        Path path = Paths.get(filename);
        Scanner scanner = new Scanner(path).useLocale(Locale.US);;
        int length = scanner.nextInt();
        double[] arr = new double[length];

        for(int i=0; i<length; i++) {
            arr[i] = scanner.nextDouble();
        }
        scanner.close();
        return arr;
    }
}
