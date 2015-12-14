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
    
    //@ ensures (/exists K k; map.containsValue(v); map.get(k) == v);
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
    public static <K, V> Map<V, Set<K>> inverse(Map<K, V> map) {
    	Map<V, Set<K>> inv = new HashMap<V, Set<K>>();
    	
    	Set <K> keyset = map.keySet(); // set met alle K's uit map
    	
    	for (Iterator<K> it = keyset.iterator(); it.hasNext();) {
			K k = it.next();
			if (inv.containsKey(map.get(k))) {
    			// in Set K in inv plaatsen
    			inv.get(map.get(k)).add(k);
    		} else {
    			// nieuwe set aanmaken
    			inv.put(map.get(k), new HashSet<K>(Arrays.asList(k)));
    		}
    	}
    	return inv;
	}
    
	public static <K, V> Map<V, K> inverseBijection(Map<K, V> map) {
		if (isOneOnOne(map) && isSurjectiveOnRange(map, new HashSet<V>())) {
			Map<V, K> inv = new HashMap<V, K>();
			Set<K> keyset = map.keySet();
			for (Iterator<K> it = keyset.iterator(); it.hasNext();) {				
				K k = it.next();
				inv.put(map.get(k), k);
			}
			return inv;
		} else {
			return null;
		}
	}
	public static <K, V, W> boolean compatible(Map<K, V> f, Map<V, W> g) {
		Set<K> keyset = f.keySet();
		for (Iterator<K> it = keyset.iterator(); it.hasNext();) {
			K k = it.next();
			if (!(g.containsKey(f.get(k)))) {
				return false;
			}
		}
        return true;
	}
	
	public static <K, V, W> Map<K, W> compose(Map<K, V> f, Map<V, W> g) {
        if (compatible(f,g)) {
        	Map<K, W> map = new HashMap <K, W>();
        	Set<K> keyset = f.keySet();
        	for (Iterator<K> it = keyset.iterator(); it.hasNext();) {
        		K k = it.next();
        		map.put(k, g.get(f.get(k)));
        	}
        return map;
        } else {
        	return null;
        }
	} 
}

