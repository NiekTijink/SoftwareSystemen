package ss.week4;

import java.util.*;

public class MergeSort {
	
    public static <E extends Comparable<E>> void mergesort(List<E> list) {
    	int size = list.size();
    	
    	//If the list does not consists of more than 1 item,
    	//sorting is not necessary
    	if (size <= 1) {
    		return;
    	}

    	//Divide list in two, where the first node is indexed 0
    	//and the last node is indexed size -1.
    	partiallist(list, 0, size - 1);
    }
    
    public static <E extends Comparable<E>> void partiallist(List<E> list, int start, int end) {
    	//make sure partiallist does not continue if the partial list is smaller than 1
    	if ((end - start) + 1 <= 1) {
    		return;
    	}
    	
    	//determine the halfway point
    	int half = ((end - start) / 2) + start;
    	//divide first half
    	partiallist(list, start, half);
    	//divide second half
    	partiallist(list, half + 1, end);
    	
    	//merge(sort) both halves
    	merge(list, start, half, half + 1, end);    	
    }
    
    public static <E extends Comparable<E>> void merge(List<E> list, int s1, int e1, int s2, int e2) {
    	
    	//System.out.println("Merging lists: (" + s1 + ":" + e1 + ") & (" + s2 + ":" + e2 + ")");
    	
    	//create temporary list
    	List<E> temp = new ArrayList<E>(list);
    	
    	int p1 = s1; //point in old list
    	int p2 = s2; //point in old list
    	int np = s1; //point in new list
    	while (p1 <= e1 && p2 <= e2) {
    		//System.out.println("Comparing items (" + p1 + ":" + p2 + ").");
    		//compare to points of the old lists, then put the lowest at point np
    		if (temp.get(p1).compareTo(temp.get(p2)) < 0) {
    			list.set(np, temp.get(p1));
    			p1++;
    			//go to the next point
    			np++;
    		} else {
    			list.set(np, temp.get(p2));
    			p2++;
    			//go to the next point
    			np++;
    		}
    	}
    	
    	while (p1 <= e1) {
    		list.set(np, temp.get(p1));
    		p1++;
			//go to the next point
    		np++;
    	}
    	
    	while (p2 <= e1) {
    		list.set(np, temp.get(p1));
    		p1++;
			//go to the next point
    		np++;
    	}
    	
    	return;
    	
    }
}