package ss.week4;

public class Exercises {
    public static int countNegativeNumbers(int[] arr) {
    	int sum = 0;
    	int i = 0;
    	while (i < arr.length) {
    		if (arr[i] < 0) {
    			sum++;
    		}
    		i++;
    	}
    	return sum;
    }

    public static void reverseArray(int[] ints) {
    	int i = ints.length / 2;
    	for (int j = 0; j < i; j++) {
    		int temp;
    		temp = ints[ints.length - 1 - j];
    		ints[ints.length - 1 - j] = ints[j];
    		ints[j] = temp;
    		
    	}
    }

    class SimpleList {
        public class Element {}

        public class Node {
            public Node next;
            public Element element;
        }

        private Node first;

        private Node find(Element element) {
            Node p = first;
            if (p == null) {
                return null;
            }
            while (p.next != null && !p.next.element.equals(element)) {
                p = p.next;
            }
            if (p.next == null) {
                return null;
            } else {
                return p;
            }
        }

        public void remove(Element element) {
            Node p = find(element);
            if (p != null) {
                p.next = p.next.next;
            }
        }
    }
}
