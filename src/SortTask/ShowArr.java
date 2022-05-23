package SortTask;

import java.io.FileWriter;
import java.io.IOException;

public class ShowArr {
    public static void showInConsole(double[] arr) {
        if(arr == null)
            return;
        for (double j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void writeInFile(double[] arr, String fName)  {
        try(FileWriter writer = new FileWriter(fName, false)){
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

}
