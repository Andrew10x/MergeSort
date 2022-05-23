package SortTask;

public class MergeSort extends SortArray {

    public MergeSort(double[] arr) {
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