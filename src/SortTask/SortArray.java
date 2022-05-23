package SortTask;

abstract class SortArray {
    protected double[] arr;

    public SortArray(double[] arr) {
        this.arr = arr;
    }

    abstract void SortArr();


    public double[] getArr() {
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