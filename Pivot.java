
/**
 * Write a description of class Pivot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pivot
{
    // instance variables - replace the example below with your own
    

    /**
     * Constructor for objects of class Pivot
     */
    public Pivot()
    {
        // initialise instance variables
        //x = 0;
    }
    //Assumes last element is pivot
    public int solvePivot(int[] arr)
    {
        int pivot = arr[arr.length-1];
        int numLower = 0;
        int numHigherOrEqual = -1;
        for(int i : arr)
        {
            if(i < pivot)
                numLower++;
            else
                numHigherOrEqual++;
        }
        int temp = arr[numLower];
        arr[numLower] = pivot;
        arr[arr.length-1] = temp;
        for(int i = 0; i < numLower; i++)
        {
            if(arr[i] >= pivot)
            {
                for(int j = pivot+1; j < arr.length; j++)
                {
                    if(arr[j] < pivot)
                    {
                        temp = arr[j];
                        arr[j] = arr[i];
                        arr[i] = temp;
                    }
                }
            }
        }
        return numLower;
    }
    
    public static void main(String[] args)
    {
        
    }
}
