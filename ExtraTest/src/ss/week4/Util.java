package ss.week4;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static <ELEMENT> List<ELEMENT> zip(
    				List<ELEMENT> l1, List<ELEMENT> l2) {
        ArrayList<ELEMENT> rESULT = new ArrayList<ELEMENT>();
        for (int i = 0; i < l1.size(); i++) {
            rESULT.add(l1.get(i));
            rESULT.add(l2.get(i));
        }
        return rESULT;
    }    

    /** signum function.
     * @param i the function argument
     * @return -1, 0 or 1 if the argument is negative, 0 or positive
     */
    public static int signum(int i) {
        if (i < 0) 	{ 
        	return -1; 
        } else if (i > 0) {
        	return 1;
        } else {
        	return 0;
        }
    }
}
