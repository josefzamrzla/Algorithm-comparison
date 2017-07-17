package algorithmcomparison;

import java.awt.*;

/**
 * @author Josef Zamrzla
 */
class SelectSortThread extends SortingAlgorithmThread
{
    private int minId = -1, currentId = -1, changeWith = -1;

    SelectSortThread()
    {
        super("SelectSortThread");
    }
    
    @Override
    public void run()
    {
        int  i, j, imin, pom;
        
        for(i = 0; i < this.array.length - 1; i++)
        {
            imin = i;
            minId = i;
            changeWith = i;
            
            for(j = i + 1; j < this.array.length; j++)
            {
                currentId = j;
                if(this.array[j] < this.array[imin])
                {
                    imin = j;
                    minId = j;
                }

                try
                {
                    sleep(AlgorithmComparisonFrame.delay);
                }
                catch(InterruptedException ie) {}
                
                yield();
            }
            
            if(imin!=i)
            {
                pom = this.array[imin];
                this.array[imin] = this.array[i];
                this.array[i] = pom;
                
                try
                {
                    sleep(AlgorithmComparisonFrame.delay);
                }
                catch(InterruptedException ie) {}
                
                yield();
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
                
                if(i == currentId)
                    g.setColor(Color.BLUE);
                else if(i == minId)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(Color.RED);
            }
            
            g.fillRect((i*10)+420, 250-(array[i]*10), 5, (array[i]*10));
            
            if(!isSorted())
            {
                if(i == currentId)
                {
                    if(minId != changeWith)
                    {
                        g.setColor(Color.RED);
                        g.drawString("R", (changeWith*10)+417, 65);
                        g.setColor(Color.BLACK);
                        g.drawLine((changeWith*10)+420, 70, (minId*10)+420, 70);
                        g.drawLine((changeWith*10)+420, 70, (changeWith*10)+420, 245-(array[changeWith]*10));
                    }
                    
                    g.setColor(Color.GREEN);
                    g.drawString("M", (minId*10)+417, 65);

                    g.setColor(Color.BLACK);
                    g.drawLine((minId*10)+420, 70, (minId*10)+420, 245-(array[minId]*10));
                }
            }
        }
    }
} 