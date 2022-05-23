package SortTask;

import java.util.concurrent.ForkJoinPool;

public class MergeSortParallel extends SortArray {

    public MergeSortParallel(double[] arr) {
        super(arr);
    }

    @Override
    void SortArr() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ArrSortAction(arr, 0, arr.length-1));
    }
}