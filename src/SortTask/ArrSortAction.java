package SortTask;

import java.util.concurrent.RecursiveAction;

public class ArrSortAction extends RecursiveAction {
    private final double[] arr;
    private final int from;
    private final int to;

    public ArrSortAction(double[] arr, int from, int to) {
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
        double[] resArr = new double[length1 + length2];
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