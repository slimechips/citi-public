import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Q5 {
  static final int DIGIT_SIZE = 10;
  static final String[] hashTable = new String[]
    {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"}; 
  static Node dictionary;
  final static String preInitDictPathShort = "./dict.obj";
  final static String preInitDictPath = Paths.get(
    System.getProperty("user.dir").toString(), preInitDictPathShort).toString();

  public static void main(String args[]) throws Exception {
    getDictionary();
    System.out.println("Dictionary Initialised");

    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    while (true) {
      
      String digits = in.next();
      Node curNode = dictionary;
      String out = "";
      
      for (char digitChar : digits.toCharArray()) {
        try {
          curNode = curNode.children[Character.getNumericValue(digitChar)];
          out = curNode.possWords.toString();
        } catch (Exception e) {
          // No possible words
          out = "No possible words";
          break;
        }
      }
      System.out.println(out);
    }
  }

  public static class Node {
    public Node[] children;
    public Set<String> possWords;

    public Node() {
      this.children = new Node[DIGIT_SIZE];
      this.possWords = new HashSet<>();
    }

    public void insert(String word) {
      this.possWords.add(word);
    }
  }

  public static void getDictionary() throws Exception {
    
    String path = "./words";
    File file = new File(path);
    dictionary = new Node();
    
    // Read dictionary file
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st;

    while((st = br.readLine()) != null) {
      Node curNode = dictionary;
      for (int i = 0; i < st.length(); ++i) {
        char curChar = st.charAt(i);
        int digit = -1;
        for (int j = 0; j < hashTable.length; ++j) {
          String curTable = hashTable[j];
          if (curTable.contains(Character.toString(curChar).toLowerCase())) {
            digit = j;
            break;
          }
        }
        // If char is invalid skip this word
        if (digit == -1) break;

        // Create new child node if it doesnt exist
        if (curNode.children[digit] == null) {
          curNode.children[digit] = new Node();
        }
        curNode = curNode.children[digit];

        if (i >= (st.length() - 1)) {
          // At EOW, time to add word
          curNode.insert(st);
        }
      }
    }
    br.close();
  }
}

