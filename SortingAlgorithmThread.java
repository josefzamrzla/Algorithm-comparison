package algorithmcomparison;

import java.awt.*;

/**
 * @author Josef Zamrzla
 */
abstract class SortingAlgorithmThread extends Thread
{
    protected int[] array = new int[AlgorithmComparisonFrame.numOfItems];
    protected boolean isSorted = false;
    
    SortingAlgorithmThread(String name)
    {
        super(name);
    }
    
    abstract public void paint(Graphics g);
    
    public void setArray(int[] array)
    {
        for(int i = 0; i < AlgorithmComparisonFrame.numOfItems; i++)
            this.array[i] = array[i];
    }
    
    public int[] getArray()
    {
        return this.array;
    }
    
    public boolean isSorted()
    {
        return this.isSorted;
    }
} 