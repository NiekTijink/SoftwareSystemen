package ss.week4;

import java.util.*;

public class MergeSort {
    public static <Elem extends Comparable<Elem>>
           void mergesort(List<Elem> list) {
    	int halfsize = list.size() / 2;
    	int otherhalf = list.size() - halfsize;
    	
    	int[] arr1;
    	for (int i = 0; i < halfsize; i++) {
    		arr1[i] = ((int) list.get(i));
    	}
    	// TODO: implement, see exercise P-4.16
    	//Test
    }
}
