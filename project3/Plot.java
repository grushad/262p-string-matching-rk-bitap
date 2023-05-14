package project3;
import java.util.*;
import java.io.*;

public class Plot {
    public static void main(String[] args) {
        
        RK rk = new RK();
        Bitap bitap = new Bitap();

        inp1(rk, bitap);
        jfk(rk, bitap);
        
    }

    static String generateRandomString(int len, Random rand){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < len; i++){
            int ascii = rand.nextInt(94) + 32; //32 -> 126 => 94 ascii characters
            char c = (char)(ascii + '0');
            str.append(c);
        }
        return str.toString();
    }   
    
    static void saveToFile(Map<Integer, Double> map, String fileName){
        File file = new File(fileName);
        BufferedWriter bf = null;  
        try {            
            bf = new BufferedWriter(new FileWriter(file));  
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {                
                bf.write(entry.getKey() + ":"+ entry.getValue());                  
                bf.newLine();
            }  
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {               
                bf.close();
            }
            catch (Exception e) {
            }
        }
    }

    static String readFile(String fileName){
        StringBuilder sb = new StringBuilder();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              sb.append(myReader.nextLine());              
            }
            myReader.close();
          } catch (FileNotFoundException e) {            
          }
          return sb.toString();
    }

    static void readPatterns(String fileName, Map<Integer, List<String>> patterns){
        int startLen = 2, endLen = 10;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int len = data.length();
                if(len <= endLen && len >= startLen){
                    if(!patterns.containsKey(len)){
                        patterns.put(len, new ArrayList<>());
                    }
                    patterns.get(len).add(data);
                }                    
            }
            myReader.close();
          } catch (FileNotFoundException e) {            
          }
    }

    static void jfk(RK rk, Bitap bitap){
        //run algo on jfk speech as text
        //read file as text
        String speechText = readFile("/Users/grushadharod/Desktop/spring 2023/262p/jfk-speech.txt");
        //read english words as pattern -> confine length to 10 
        Map<Integer, List<String>> patterns = new HashMap<>();
        readPatterns("/Users/grushadharod/Desktop/spring 2023/262p/proj1/words2.txt", patterns);
        // for(Map.Entry<Integer, List<String>> entry: patterns.entrySet()){
        //     System.out.println(entry.getKey() + " " + entry.getValue().size());
        // }

        Map<Integer, Double> rkRuntimes = new HashMap<>();
        Map<Integer, Double> bitapRuntimes = new HashMap<>();

        //for each pattern of length 2, 3, 4, 5, 6, ... 10 -> calculate avg runtime for each pattern length
        long durationRk = 0, durationBitap = 0, startTime = 0;
        for(Map.Entry<Integer, List<String>> entry : patterns.entrySet()){
            int inpSize = entry.getKey();
            int size = 500;
            for(String pat: entry.getValue()){
                if(size == 0)
                    break;
                startTime = System.nanoTime();                
                rk.match(speechText, pat);
                durationRk += System.nanoTime() - startTime;
                
                startTime = System.nanoTime();                
                bitap.match(speechText, pat);
                durationBitap += System.nanoTime() - startTime;
                size--;                              
            }            
            rkRuntimes.put(inpSize, (double)durationRk / 500); 
            bitapRuntimes.put(inpSize, (double)durationBitap / 500);             
        }

        // System.out.println("KMP: " + kmpRuntimes + "\n");
        // System.out.println("BMH: " + bmhRuntimes + "\n");
        // System.out.println("Brute: " + bruteRuntimes + "\n");

        saveToFile(rkRuntimes, "./rk-runtimes-jfk.txt");
        saveToFile(bitapRuntimes, "./bitap-runtimes-jfk.txt");        
    }

    static void inp1(RK rk, Bitap bitap){
        Random rand = new Random();
        int textLen = 10000;

        String text = generateRandomString(textLen, rand);
        
        Map<Integer, Double> rkRuntimes = new HashMap<>();
        Map<Integer, Double> bitapRuntimes = new HashMap<>();
        
        long durationRk = 0, durationBitap = 0, startTime = 0;
        for(int i = 2; i <= 10; i++){
            for(int j = 0; j < 100; j++){
                String pattern = generateRandomString(i, rand);
                
                startTime = System.nanoTime();
                rk.match(text, pattern);
                durationRk += System.nanoTime() - startTime;            
                
                startTime = System.nanoTime();            
                bitap.match(text, pattern);
                durationBitap += System.nanoTime() - startTime;                            
            }
            rkRuntimes.put(i, (double)durationRk / 500);  
            bitapRuntimes.put(i, (double)durationBitap / 500);        
        }

        // System.out.println("RK: " + rkRuntimes + "\n");
        // System.out.println("Bitap: " + bitapRuntimes + "\n");        

        saveToFile(rkRuntimes, "./rk-runtimes-ip1.txt");
        saveToFile(bitapRuntimes, "./bitap-runtimes-ip1.txt");        

    }
}
