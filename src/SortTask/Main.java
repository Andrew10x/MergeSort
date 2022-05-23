package SortTask;

import java.io.IOException;

public class Main {

    public static double[] arr;

    public static void main(String[] args) throws IOException {
        task();
    }

    public static void task() throws IOException {
        //arr = new int[]{45, 2, 5, 1, 7, 8, 9, 0, 99, 33, 2, 17, 8, 4, 21, -4, 10};
        ReadArrFromFile rf = new ReadArrFromFile();
        arr = rf.readArr("D:\\genArr\\arr100_000_000.txt");
       long sTime1 = System.currentTimeMillis();
        MergeSort ms = new MergeSort(arr.clone());
        ms.SortArr();
        long resTime1 = System.currentTimeMillis();
        long Time1 = resTime1 - sTime1;
        System.out.println("Time: " + Time1);
        System.out.println(ms.checkArr());
        System.out.println();

        long sTime2 = System.currentTimeMillis();
        MergeSortParallel msp = new MergeSortParallel(arr.clone());
        msp.SortArr();
        long resTime2 = System.currentTimeMillis();
        long Time2 = resTime2 - sTime2;
        System.out.println("Time: " + Time2);
        System.out.println(msp.checkArr());

        System.out.println("SpeedUp: " + ((double) Time1/Time2));

        //ShowArr.showInConsole(msp.arr);
    }
}