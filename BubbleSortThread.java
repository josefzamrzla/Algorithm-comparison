package algorithmcomparison;

import java.awt.*;

/**
 * @author Josef Zamrzla
 */
class BubbleSortThread extends SortingAlgorithmThread
{
    private int currentId = -1;
    private int nextId = -1;
    boolean changeNeeded = false;

    BubbleSortThread()
    {
        super("BubbleSortThread");
    }
    
    @Override
    public void run()
    {
        boolean continueSorting = true;
        int temp;
        
        while(continueSorting)
        {
            continueSorting = false;
            for(int i = 0; i < this.array.length - 1; i++)
            {
                currentId = i;
                nextId = i + 1;
                
                if(this.array[i] > this.array[i + 1])
                    changeNeeded = true;
                else
                    changeNeeded = false;
                
                try
                {
                    sleep(AlgorithmComparisonFrame.delay);
                }
                catch(InterruptedException ie) {}
                
                yield();
                
                if(this.array[i] > this.array[i + 1])
                {
                    temp = this.array[i];
                    this.array[i] = this.array[i + 1];
                    this.array[i + 1] = temp;
                    continueSorting = true;
                }
                
                if(changeNeeded)
                {
                    try
                    {
                        sleep(AlgorithmComparisonFrame.delay);
                    }
                    catch(InterruptedException ie) {}
                    
                    yield();
                }
            }
        }
        
        this.isSorted = true;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.RED);
        
        for(int i = 0; i < array.length; i++)
        {
            if(isSorted())
                g.setColor(Color.GREEN);
            else
            {
                if(i == currentId || i == nextId)
                    g.setColor(Color.BLUE);
                else
                    g.setColor(Color.RED);
            }
            
            g.fillRect((i * 10) + 30, 250 - (array[i] * 10), 5, (array[i] * 10));
            
            if(!isSorted())
            {
                if(i == currentId)
                {
                    if(changeNeeded)
                    {
                        g.setColor(Color.RED);
                        g.drawString("C", (i * 10) + 30, 65);
                    }
                    else
                    {
                        g.setColor(Color.GREEN);
                        g.drawString("N", (i * 10) + 30, 65);
                    }
                    
                    g.setColor(Color.BLACK);
                    g.drawLine((i * 10) + 30, 70, ((i + 1) * 10) + 35, 70);
                    g.drawLine((i * 10) + 30, 70, (i * 10) + 30, 245 - (array[i] * 10));
                    g.drawLine(((i + 1) * 10) + 35, 70, ((i + 1) * 10) + 35, 245 - (array[i + 1] * 10));
                }
            }
        }
    }
} 