package ss.week5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {
    public static <K, V> boolean isOneOnOne(Map<K, V> map) {
    	for (int j = 1; j < map.size(); j++) {
    		for (int i = j + 1; i < map.size() + 1; i++) {
    			if (map.get(j) == map.get(i)) {
    				return false;
    			} 
    		}
    	}
        return true;
    }
    
    public static <K, V> 
           boolean isSurjectiveOnRange(Map<K, V> map, Set<V> range) {
    	for (Iterator<V> it = range.iterator(); it.hasNext();) {
    		
    		V f = it.next();

    		eachV: for (int i = 1; i < map.size() + 1; i++) {
    			if (f.equals(map.get(i))) {
    				break eachV;
    			}
    			if (i == map.size()) {
    				return false;
    			}
    		}
    	
    	}
        return true;
    }
    public static <K, V> Map<V, Set<Integer>> inverse(Map<K, V> map) {
    	Map<V, Set<Integer>> inv = new HashMap<V, Set<Integer>>();
    	for (int i = 1; i < map.size() + 1; i++) {
    		if (inv.containsKey(map.get(i))) {
    			inv.get(map.get(i)).add(i);
    		} else {
    			inv.put(map.get(i), new HashSet<Integer>(Arrays.asList(i)));
    		}
    	}
        return inv;
	}
	public static <K, V> Map<V, K> inverseBijection(Map<K, V> map) {
		if (isOneOnOne(map) && isSurjectiveOnRange(map, new HashSet<V>())) {
			Map<V, K> inv = new HashMap<V, K>();
			Iterator<K> it = (Iterator<K>) map.entrySet().iterator();
			while (it.hasNext()) {				
				K f = it.next();
				inv.put(map.get(f), f);
			}
			return inv;
		} else {
			return null;
		}
	}
	public static <K, V, W> boolean compatible(Map<K, V> f, Map<V, W> g) {
        // TODO: implement, see exercise P-5.4
        return false;
	}
	public static <K, V, W> Map<K, W> compose(Map<K, V> f, Map<V, W> g) {
        // TODO: implement, see exercise P-5.5
        return null;
	}
}
