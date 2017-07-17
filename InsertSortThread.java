package algorithmcomparison;

import java.awt.*;

/**
 * @author Josef Zamrzla
 */
class InsertSortThread extends SortingAlgorithmThread
{
    private boolean inserted = true;
    int currentId = -1, currentId2 = -1, insertId = 0;
    
    InsertSortThread()
    {
        super("InsertSortThread");
    }
    
    @Override
    public void run()
    {
        for(int n=1; n<this.array.length ; n++)
        {
            currentId = n;
            insert(this.array, n, this.array[n]);
            
            try
            {
                sleep(AlgorithmComparisonFrame.delay);
            }
            catch(InterruptedException ie) {}
            
            yield();
        }
        
        this.isSorted = true;
    }
    
    private void insert(int[] a, int n, int x)
    {
        int i;
        inserted = false;
        insertId = x;
        
        for(i = n - 1; i >= 0 && a[i] > x; i--)
        {
            currentId2 = i;
            a[i+1] = a[i];
            
            try
            {
                sleep(AlgorithmComparisonFrame.delay);
            }
            catch(InterruptedException ie) {}
            
            yield();
        }
        
        a[i+1] = x;
        inserted = true;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.RED);
        
        for(int i = 0; i<array.length; i++)
        {
            if(isSorted())
                g.setColor(Color.GREEN);
            else
            {
                if(i == currentId)
                    g.setColor(Color.BLUE);
                else if(i == currentId2 && !inserted)
                    g.setColor(Color.LIGHT_GRAY);
                else
                    g.setColor(Color.RED);
            }   
            
            g.fillRect((i*10)+420, 500-(array[i]*10), 5, (array[i]*10));
            
            if(!isSorted())
            {
                if(currentId != currentId2 && currentId > -1 && currentId2 > -1)
                {
                    g.setColor(Color.BLACK);
                    g.drawLine((currentId*10)+420, 320, (currentId*10)+420, 495-(array[currentId]*10));
                    g.drawLine((currentId2*10)+420, 320, (currentId2*10)+420, 495-(array[currentId2]*10));
                    g.drawLine((currentId*10)+420, 320, (currentId2*10)+420, 320);
                }
                
                if(i == currentId2 && !inserted)
                {
                    g.setColor(Color.RED);
                    g.fillRect((i*10)+420, 500-(insertId*10), 5, (insertId*10));
                }
            }
        }
    }
} 