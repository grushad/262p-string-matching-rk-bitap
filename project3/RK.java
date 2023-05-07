package project3;

public class RK {
  int b = 256; // alphabet size -> ascii size
  int prime = 101;
  public int match(String T, String P) {
    /** Your code goes here */    
    int n = T.length(), m = P.length(); //7, 3    
    if(m > n) 
      return -1; //pattern length greater than text.
    
    int textHash = hashFunc(T, m);
    int patternHash = hashFunc(P, m);  
    
    //calculate power of b ^ m - 1
    int pow = 1;
    for(int i = 0; i < m - 1; i++){
      pow = pow * b % prime;
    }

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
            textHash = rollHash(textHash, T, i, m, pow);
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
  int rollHash(int hash, String T, int index, int m, int pow){
    hash = (hash * b - T.charAt(index) * pow * b + T.charAt(index + m)) % prime;
    if(hash < 0)
      hash += prime;
    return hash;
  }
}