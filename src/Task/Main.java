package Task;

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

        MergeSort ms = new MergeSort(arr.clone());
        ms.SortArr();
        System.out.println(ms.checkArr());
        ShowArr.showInConsole(ms.getArr());

        ShowArr.showInConsole(arr);
        MergeSortParallel msp = new MergeSortParallel(arr.clone());
        msp.SortArr();
        System.out.println(msp.checkArr());
        ShowArr.showInConsole(msp.getArr());
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
        /*readArr();
        long sTime2 = System.currentTimeMillis();
        SortInParallel();
        //showArr(arr);
        long resTime2 = System.currentTimeMillis() - sTime2;
        System.out.println(resTime2);
        checkArr();
        //writeInFile(arr);*/
    }
}


class ArrSortAction extends RecursiveAction {
    private final int[] arr;
    private final int from;
    private final int to;

    public ArrSortAction(int[] arr, int from, int to) {
        this.arr = arr;
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
        ArrSortAction act1 = new ArrSortAction(arr, from, to1);
        ArrSortAction act2 = new ArrSortAction(arr,to1+1, to);
        invokeAll(act1, act2);
        act1.join();
        act2.join();
        MergeArr(from, to1, to1+1, to);
    }

    void computeDirectly() {
       SortArrFunc(from, to);

    }

    public void SortArrFunc(int from, int to) {
        if(arr == null)
            return;
        if(to-from < 1)
            return;

        int to1 = (from+to)/2;
        SortArrFunc(from, to1);
        SortArrFunc(to1+1, to);

        MergeArr(from, to1, to1+1, to);
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

abstract class SortArray {
    protected int[] arr;

    public SortArray(int[] arr) {
        this.arr = arr;
    }

    abstract void SortArr();


    public int[] getArr() {
        return arr;
    }

    public boolean checkArr() {
        boolean k = true;
        for(int i=0; i<arr.length-1; i++){
            if(arr[i] > arr[i+1]){
                k=false;
                break;
            }
        }
        return k;
    }

}

class MergeSort extends SortArray {

    public MergeSort(int[] arr) {
        super(arr);
    }

    @Override
    void SortArr() {
        SortArrFunc(0, arr.length-1);
    }

    public void SortArrFunc(int from, int to) {
        if(arr == null)
            return;
        if(to-from < 1)
            return;

        int to1 = (from+to)/2;
        SortArrFunc(from, to1);
        SortArrFunc(to1+1, to);

        MergeArr(from, to1, to1+1, to);
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

class MergeSortParallel extends SortArray {

    public MergeSortParallel(int[] arr) {
        super(arr);
    }

    @Override
    void SortArr() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ArrSortAction(arr, 0, arr.length-1));
    }
}



class ShowArr {
    public static void showInConsole(int[] arr) {
        if(arr == null)
            return;
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static void writeInFile(int[] arr, String fName)  {
        try(FileWriter writer = new FileWriter(fName, false)){
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