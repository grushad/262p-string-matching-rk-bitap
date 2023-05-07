package project3;
import java.util.Map;
import java.util.HashMap;
public class Bitap {
  
  public int match(String T, String P) {
    /** Your code goes here */
    int n = T.length(), m = P.length(); 
    if(m > n) 
      return -1; //pattern length greater than text.
    int val = 0, mask = (int)Math.pow(2, m) - 1;    
    
    Map<Character, Integer> map = new HashMap<>();
    for(int i = 0; i < n; i++){
      map.put(T.charAt(i), ~0);
    }
    for(int i = 0; i < m; i++){
      if(!map.containsKey(P.charAt(i))){
        map.put(P.charAt(i), 1);
      }
      map.put(P.charAt(i), map.get(P.charAt(i)) & ~(1 << i));
    }
  
    for(int i = 0; i < n; i++){      
      val = (val << 1) & mask | map.get(T.charAt(i));
      if(((val & (1 << m - 1)) == 0) && i >= m - 1 ){
        return i - m + 1;
      }
    }
    return -1;
  }

}
