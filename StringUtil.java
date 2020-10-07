
import java.util.*;
import java.util.Stack;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
/**
 * 
 * String util
 * 
 * @author Martin
 * @version 12-12-19
 */
public class StringUtil
{
    // instance variables - replace the example below with your own
    private ArrayList<String> permList;
    /**
     * Constructor for objects of class a
     */
    public StringUtil()
    {
        permList = new ArrayList<String>();
    }

   
    
    public ArrayList<String> permutation(String prefix, String str) {
       
        int n = str.length();
        if (n == 0) 
            permList.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
        return permList;
    }
    
    public static void main(String[] args)
    {
        StringUtil u = new StringUtil();
        ArrayList<String> a = u.permutation("", "012345");
        System.out.println(a);
    }
}
