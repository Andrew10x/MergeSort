package com.company;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {

    public static void main(String[] args) throws IOException {
        //int[] arr = new int[] {2, 5, 1, 7, 8, 9, 0, 33, 2, 17, 8, 4, 21, -4, 10};
        //arr = SortArr(arr);
        //arr = SortInParallel(arr);
        //showArr(arr);
        //arr = SortArr(arr);
        //showArr(arr);

        int[] arr = readArr();
        long sTime1 = System.currentTimeMillis();
        arr = SortArr(arr);
        long resTime1 = System.currentTimeMillis() - sTime1;
        System.out.println(resTime1);


        long sTime2 = System.currentTimeMillis();
        arr = SortInParallel(arr);
        long resTime2 = System.currentTimeMillis() - sTime2;
        System.out.println(resTime2);
        writeInFile(arr);

    }

    public static int[] SortArr(int[] arr) {
        if(arr == null)
            return null;
        else if(arr.length < 2)
            return arr;

        int[] arr1 = new int[arr.length/2];
        int[] arr2 = new int[arr.length - arr.length/2];
        System.arraycopy(arr, 0, arr1, 0, arr1.length);
        System.arraycopy(arr, arr.length/2, arr2, 0, arr2.length);

        arr1 = SortArr(arr1);
        arr2 = SortArr(arr2);
        return  MergeArr(arr1, arr2);
    }

    public static int[] MergeArr(int[] arr1, int[] arr2) {
        int[] resArr = new int[arr1.length + arr2.length];
        int pos1 = 0, pos2 = 0;
        for(int i=0; i<resArr.length; i++) {
            if(pos1 == arr1.length && pos2 < arr2.length) {
                resArr[i] = arr2[pos2];
                pos2++;
            }
            else if(pos2 == arr2.length) {
                resArr[i] = arr1[pos1];
                pos1++;
            }
            else if(arr1[pos1] < arr2[pos2]) {
                resArr[i] = arr1[pos1];
                pos1++;
            }
            else {
                resArr[i] = arr2[pos2];
                pos2++;
            }
        }
        return resArr;
    }

    public static void showArr(int[] arr) {
        if(arr == null)
            return;
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static int[] SortInParallel(int[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ArrSortTask(arr));
    }

    public static int[] readArr() throws IOException {
            Path path = Paths.get("D:\\genArr\\arr100_000_000.txt");
            Scanner scanner = new Scanner(path);
            int length = scanner.nextInt();
            int[] arr = new int[length];

            for(int i=0; i<length; i++) {
                arr[i] = scanner.nextInt();
            }
            scanner.close();
            return arr;
    }

    public static void writeInFile(int[] arr)  {
        try(FileWriter writer = new FileWriter("D:\\genArr\\sArr100_000_000.txt", false)){
            writer.append(Integer.toString(arr.length)).append("\n");
            for(int i=0; i<arr.length; i++) {
                if (i != arr.length - 1)
                    writer.append(Integer.toString(arr[i])).append(" ");
                else
                    writer.append(Integer.toString(arr[i]));
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}

class ArrSortTask extends RecursiveTask<int[]> {
    private int[] arr;

    public ArrSortTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected int[] compute() {
        if(arr.length < 1000)
            return arr;
        //List<ArrSortTask> tl = new ArrayList<>();
        int[] arr1 = new int[arr.length/2];
        int[] arr2 = new int[arr.length - arr.length/2];
        System.arraycopy(arr, 0, arr1, 0, arr1.length);
        System.arraycopy(arr, arr.length/2, arr2, 0, arr2.length);
        ArrSortTask ast1 = new ArrSortTask(arr1);
        ArrSortTask ast2 = new ArrSortTask(arr2);
        //ast1.fork();
        //ast2.fork();
        //tl.add(ast1);
        //tl.add(ast2);
        //invokeAll(ast1, ast2);
        ast1.fork();
        ast2.fork();

        arr1 = ast1.join();
        arr2 = ast2.join();

        return Main.MergeArr(arr1, arr2);
    }

    int[] computeDirectly() {
        return new int[]{0};
    }
}
