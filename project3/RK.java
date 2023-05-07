package project3;

public class RK {
  int b = 52; // alphabet size -> ascii size
  int prime = 101;
  public int match(String T, String P) {
    /** Your code goes here */    
    int n = T.length(), m = P.length(); //7, 3    
    if(m > n) 
      return -1; //pattern length greater than text.
    
    int textHash = hashFunc(T, m);
    int patternHash = hashFunc(P, m);    
    for(int i = 0; i <= n - m; i++){
      if(textHash == patternHash){
        int j = 0;
        while(j < m && T.charAt(i + j) == P.charAt(j)){
          j++;
          if(j == m)
            return i;
        }
      }
      if(i < n - m){
            textHash = (textHash * b - T.charAt(i) * (int)Math.pow(b, m) + T.charAt(i + m)) % prime; 
            if(textHash < 0)
              textHash += prime;
      }      
    }
    return -1;
  }
  int hashFunc(String str, int len){
    int hash = 0;    
    for(int i = 0; i < len; i++){
      hash = (b * hash + str.charAt(i)) % prime;
    }
    return hash;
  }
  int rollHash(int hash, String T, int index, int m){
    return (hash * b - T.charAt(index) * (int)Math.pow(b, m) + T.charAt(index + m));
  }
}