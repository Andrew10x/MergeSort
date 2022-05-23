package genArr;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        double[] arr = genArr(100_000_000);
        writeInFile(arr);
        //readArr();
    }

    public static double[] genArr(int size) {
        double[] arr = new double[size];

        for(int i=0; i<size; i++) {
            arr[i] = (Math.random()*100_000_000);
        }
        return arr;
    }

    public static void writeInFile(double[] arr)  {
        try(FileWriter writer = new FileWriter("D:\\genArr\\arr100_000_000.txt", false)){
            writer.append(Integer.toString(arr.length)).append("\n");
            for(int i=0; i<arr.length; i++) {
                if (i != arr.length - 1)
                    writer.append(Double.toString(arr[i])).append(" ");
                else
                    writer.append(Double.toString(arr[i]));
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    static void readArr() throws IOException {
        Path path = Paths.get("D:\\genArr\\arr10.txt");
        Scanner scanner = new Scanner(path);
        int length = scanner.nextInt();
        int[] arr = new int[length];

        for(int i=0; i<length; i++) {
            arr[i] = scanner.nextInt();
        }
        scanner.close();


        for(int i=0; i<length; i++) {
            System.out.print(arr[i] + " ");
        }
            System.out.print("\n");
    }


}
