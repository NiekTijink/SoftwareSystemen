package ss.week7;


public class QuickSort extends Thread {
	int[] a;
	int first;
	int last;
	
	public QuickSort(int[] a, int first, int last) {
		this.a = a;
		this.first = first;
		this.last = last;
	}
	
	public void run() {
		qsort(a, first, last);
	}
	
    public static void qsort(int[] a) {
        qsort(a, 0, a.length - 1);
    }
    public static void qsort(int[] a, int first, int last) {
        if (first < last) {
            int position = partition(a, first, last);
            Thread z = new QuickSort(a,first, position -1);
            z.start();
            //qsort(a, first, position - 1);
            Thread y = new QuickSort(a, position + 1, last);
        	y.start();
            //qsort(a, position + 1, last);
        }
    }
    public static synchronized int partition(int[] a, int first, int last) {

        int mid = (first + last) / 2;
        int pivot = a[mid];
        swap(a, mid, last); // put pivot at the end of the array
        int pi = first;
        int i = first;
        while (i != last) {
            if (a[i] < pivot) {
                swap(a, pi, i);
                pi++;
            }
            i++;
        }
        swap(a, pi, last); // put pivot in its place "in the middle"
        return pi;
    }
    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
