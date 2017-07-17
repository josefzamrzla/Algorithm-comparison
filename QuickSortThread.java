package algorithmcomparison;

import java.awt.*;

/**
 * @author Josef Zamrzla
 */
class QuickSortThread extends SortingAlgorithmThread
{
    int pivotId = -1, endId = -1, startId = -1, currentId = -1, currentId2 = -1;
    
    QuickSortThread()
    {
        super("QuickSortThread");        
    }
    
    @Override
    public void run()
    {
        this.quickSort(this.array, 0, this.array.length - 1);
        this.isSorted = true;
    }
    
    public void swap(int array[], int index1, int index2)
    {
        currentId = index1;
        currentId2 = index2;
        
        try
        {
            sleep(AlgorithmComparisonFrame.delay);
        }
        catch(InterruptedException ie) {}
        
        yield();
        
        int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
        
        try
        {
            sleep(AlgorithmComparisonFrame.delay);
        }
        catch(InterruptedException ie) {}
        
        yield();
   }

    public void quickSort(int array[], int start, int end)
    {
        int i = start;
        int k = end;
        
        try
        {
            sleep(AlgorithmComparisonFrame.delay);
        }
        catch(InterruptedException ie) {}
        
        yield();
        
        if(end - start >= 1)
        {
            int pivot = array[start];
            pivotId = start;
            startId = start;
            endId = end;
            
            while(k > i)
            {
                currentId = -1;
                currentId2 = -1;

                while(array[i] <= pivot && i <= end && k > i)
                    i++;
                
                while(array[k] > pivot && k >= start && k >= i)
                    k--;
                
                if(k > i)
                {
                    swap(array, i, k);
                }
            }
            
            swap(array, start, k);
            
            this.quickSort(array, start, k - 1);
            this.quickSort(array, k + 1, end);
        }
        else
        {
            return;
        }
    }
   
    public void paint(Graphics g)
    {
        g.setColor(Color.RED);
        if(isSorted())
            g.setColor(Color.GREEN);
        
        for(int i = 0; i<array.length; i++)
        {
            if(isSorted())
                g.setColor(Color.GREEN);
            else
            {
                if(i == currentId || i == currentId2)
                    g.setColor(Color.GREEN);
                else if(i == pivotId)
                    g.setColor(Color.BLUE);
                else
                    g.setColor(Color.RED);
            }
            
            g.fillRect((i*10)+30, 500-(array[i]*10), 5, (array[i]*10));
        }
        
        if(!isSorted())
        {
            g.setColor(Color.BLACK);

            if(pivotId > -1)
            {
                for(int i = 29; i < 385; i++)
                {
                    if(i % 2 == 0)
                        g.drawLine(i, 498-(array[pivotId]*10), i, 498-(array[pivotId]*10));
                }
            }
            
            if(startId >= 0 && endId >= 0)
            {
                g.drawLine((startId*10)+30, 320, (startId*10)+30, 495-(array[startId]*10));
                g.drawLine((endId*10)+30, 320, (endId*10)+30, 495-(array[endId]*10));
                g.drawLine((startId*10)+30, 320, (endId*10)+30, 320);
            }
        }
    }
} 