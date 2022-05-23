package Var1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Main {

    public static int[] arr;

    public static void main(String[] args) throws IOException {
        arr = new int[] {45, 2, 5, 1, 7, 8, 9, 0, 99, 33, 2, 17, 8, 4, 21, -4, 10};
        //arr = SortArr(arr);
        //arr = SortInParallel(arr);
        //showArr(arr);
        //arr = SortArr(arr);
        //showArr(arr);

        /*readArr();
        long sTime1 = System.currentTimeMillis();
        System.out.println(sTime1);
        SortArr(0, arr.length-1);
        long sTime2 = System.currentTimeMillis();
        System.out.println(sTime2);
        long resTime1 = sTime2 - sTime1;
        System.out.println(resTime1);
        checkArr();
        //showArr(arr);
*/
        readArr();
        long sTime2 = System.currentTimeMillis();
        SortInParallel();
        //showArr(arr);
        long resTime2 = System.currentTimeMillis() - sTime2;
        System.out.println(resTime2);
        checkArr();
        //writeInFile(arr);
    }

    public static void SortArr(int from, int to) {
        if(arr == null)
            return;
        if(to-from < 1)
            return;

        int to1 = (from+to)/2;
        SortArr(from, to1);
        SortArr(to1+1, to);

        MergeArr(from, to1, to1+1, to);
    }

    public static void MergeArr(int from1, int to1, int from2, int to2) {
        int length1 = to1-from1+1;
        int length2 = to2-from2+1;
        int[] resArr = new int[length1 + length2];
        int pos1 = 0, pos2 = 0;
        for(int i=0; i<resArr.length; i++) {
            if(pos1 == length1 && pos2 < length2) {
                resArr[i] = arr[from2+pos2];
                pos2++;
            }
            else if(pos2 == length2) {
                resArr[i] = arr[from1+pos1];
                pos1++;
            }
            else if(arr[from1+pos1] < arr[from2+pos2]) {
                resArr[i] = arr[from1+pos1];
                pos1++;
            }
            else {
                resArr[i] = arr[from2+pos2];
                pos2++;
            }
        }
        for(int i=0; i<length1+length2; i++) {
            arr[from1+i] = resArr[i];
        }
    }

    public static void showArr(int[] arr) {
        if(arr == null)
            return;
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void SortInParallel() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ArrSortAction(0, arr.length-1));
    }

    public static void readArr() throws IOException {
        Path path = Paths.get("D:\\genArr\\arr100_000_000.txt");
        Scanner scanner = new Scanner(path);
        int length = scanner.nextInt();
        arr = new int[length];

        for(int i=0; i<length; i++) {
            arr[i] = scanner.nextInt();
        }
        scanner.close();
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

    public static void checkArr() {
        boolean k = true;
        for(int i=0; i<arr.length-1; i++){
            if(arr[i] > arr[i+1]){
                k=false;
                break;
            }
        }
        System.out.println(k);
    }
}

class ArrSortAction extends RecursiveAction {
    private int[] arr;
    private int from, to;

    public ArrSortAction(int from, int to) {
        this.arr = Main.arr;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void compute() {
        if(to-from < 1000) {
            computeDirectly();
            return;
        }

        int to1 = (from+to)/2;
        ArrSortAction act1 = new ArrSortAction(from, to1);
        ArrSortAction act2 = new ArrSortAction(to1+1, to);
        invokeAll(act1, act2);
        act1.join();
        act2.join();
        MergeArr(from, to1, to1+1, to);
    }

    void computeDirectly() {
        Main.SortArr(from, to);
    }

    public void MergeArr(int from1, int to1, int from2, int to2) {
        int length1 = to1-from1+1;
        int length2 = to2-from2+1;
        int[] resArr = new int[length1 + length2];
        int pos1 = 0, pos2 = 0;
        for(int i=0; i<resArr.length; i++) {
            if(pos1 == length1 && pos2 < length2) {
                resArr[i] = arr[from2+pos2];
                pos2++;
            }
            else if(pos2 == length2) {
                resArr[i] = arr[from1+pos1];
                pos1++;
            }
            else if(arr[from1+pos1] < arr[from2+pos2]) {
                resArr[i] = arr[from1+pos1];
                pos1++;
            }
            else {
                resArr[i] = arr[from2+pos2];
                pos2++;
            }
        }
        for(int i=0; i<length1+length2; i++) {
            arr[from1+i] = resArr[i];
        }
    }
}
