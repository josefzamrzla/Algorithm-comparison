package algorithmcomparison;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Josef Zamrzla
 */
class AlgorithmComparisonFrame extends Frame implements Runnable
{
    public static int delay = 500;
    public static int numOfItems = 36;
    Thread mainThread;
    Image offscreenImage;
    Graphics offscreenGraph;
    Dimension frameDim;
    BubbleSortThread bubble;
    SelectSortThread select;
    QuickSortThread quick;
    InsertSortThread insert;
    
    boolean isRunning = false;
    
    int[] itemsArray = new int[AlgorithmComparisonFrame.numOfItems];
    
    JSlider speedSlider;
    JButton btnRun, btnSetRandom, btnSetSorted;

    AlgorithmComparisonFrame(String title)
    {
        super(title);
        
        for(int i = 0; i < AlgorithmComparisonFrame.numOfItems; i++)
            itemsArray[i] = (int)Math.round(Math.random() * 16) + 1;

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent evt)
            {
                if(mainThread != null)
                    mainThread = null;

                if(bubble.isAlive())
                    bubble.stop();
                
                if(select.isAlive())
                    select.stop();
                
                if(quick.isAlive())
                    quick.stop();
                
                if(insert.isAlive())
                    insert.stop();
                
                if(offscreenGraph != null)
                    offscreenGraph.dispose();

                dispose();
                System.exit(0);
            }
        });
        
        setSize(830, 600);
        setResizable(true);
        
        bubble = new BubbleSortThread();
        bubble.setArray(itemsArray);
        
        select = new SelectSortThread();
        select.setArray(itemsArray);
        
        quick = new QuickSortThread();
        quick.setArray(itemsArray);
        
        insert = new InsertSortThread();
        insert.setArray(itemsArray);
    
        Panel panel; 
        btnRun = new JButton("Run animation");
        btnSetRandom = new JButton("Random sequence");
        btnSetSorted = new JButton("Sorted sequence");
        
        class SpeedSliderAction implements ChangeListener
        {
            public void stateChanged(ChangeEvent ce)
            {
                int value = speedSlider.getValue();
                value = 1000 - (value * 10);
                if(value < 0)
                    value = 0;
                
                AlgorithmComparisonFrame.delay = value;
            }
        }
        
        class BtnSetRandomListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                for(int i = 0; i < AlgorithmComparisonFrame.numOfItems; i++)
                    itemsArray[i] = (int)Math.round(Math.random() * 16) + 1;
                
                bubble.setArray(itemsArray);
                insert.setArray(itemsArray);
                select.setArray(itemsArray);
                quick.setArray(itemsArray);
                
                repaint();
            }
        }
        
        class BtnSetSortedListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                for(int i = 0; i < AlgorithmComparisonFrame.numOfItems; i++)
                    itemsArray[i] = (int)Math.round(i / 2.3) + 1;
                
                bubble.setArray(itemsArray);
                insert.setArray(itemsArray);
                select.setArray(itemsArray);
                quick.setArray(itemsArray);
                
                repaint();
            }
        }
        
        class BtnRunListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                btnSetSorted.setEnabled(isRunning);
                btnSetRandom.setEnabled(isRunning);
                
                if(isRunning)
                {
                    int[] tempArray;
                    mainThread.stop();
                    mainThread = new Thread(AlgorithmComparisonFrame.this);
                    
                    tempArray = bubble.getArray();
                    bubble.stop();
                    bubble = new BubbleSortThread();
                    bubble.setArray(tempArray);
                    
                    tempArray = select.getArray();
                    select.stop();
                    select = new SelectSortThread();
                    select.setArray(tempArray);
                    
                    tempArray = quick.getArray();
                    quick.stop();
                    quick = new QuickSortThread();
                    quick.setArray(tempArray);
                    
                    tempArray = insert.getArray();
                    insert.stop();
                    insert = new InsertSortThread();
                    insert.setArray(tempArray);
                    
                    isRunning = false;
                    
                    btnRun.setText("Run animation");
                }
                else
                {
                    mainThread.start();
                    isRunning = true;
                    
                    btnRun.setText("Stop animation");
                }
            }
        }
        
        speedSlider = new JSlider();
        speedSlider.setValue(50);
        speedSlider.addChangeListener(new SpeedSliderAction());
        
        BtnSetRandomListener setRandomListener = new BtnSetRandomListener();
        BtnRunListener runListener = new BtnRunListener();
        BtnSetSortedListener sortedListener = new BtnSetSortedListener();
        
        btnRun.addActionListener(runListener);
        btnSetRandom.addActionListener(setRandomListener);
        btnSetSorted.addActionListener(sortedListener);
        
        panel = new Panel();
        panel.setBackground(Color.LIGHT_GRAY);
        
        add("South", panel); 
        
        panel.add(btnSetRandom);
        panel.add(btnSetSorted);
        panel.add(new Label("< slow down"));
        panel.add(speedSlider);
        panel.add(new Label("speed up >"));
        panel.add(btnRun);
        
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        frameDim = getSize();
        setLocation((screenDim.width - frameDim.width) / 2, (screenDim.height - frameDim.height) / 2);
        setVisible(true);
        
        mainThread = new Thread(this);
    }

    public void run()
    {
        select.start();
        bubble.start();
        quick.start();
        insert.start();
        
        while(!(bubble.isSorted() && select.isSorted() && quick.isSorted() && insert.isSorted()))
        {
            repaint();
        }
        
        btnRun.doClick();
    }

    @Override
    public void update(Graphics g)
    {
        offscreenGraph.setFont(new Font("Arial", Font.PLAIN, 12));
        offscreenGraph.setColor(getBackground());
        offscreenGraph.fillRect(0,0,frameDim.width,frameDim.height);
        
        offscreenGraph.setColor(Color.BLACK);
        offscreenGraph.drawRect(20, 50, 370, 200);
        offscreenGraph.drawRect(410, 50, 370, 200);
        offscreenGraph.drawRect(20, 300, 370, 200);
        offscreenGraph.drawRect(410, 300, 370, 200);
        
        // BubbleSort
        offscreenGraph.setColor(Color.BLACK);
        offscreenGraph.drawString("bubble sort", 20, 40);
        offscreenGraph.setColor(Color.RED);
        offscreenGraph.drawString("C = change needed,", 20, 265);
        offscreenGraph.setColor(Color.decode("0x00CC00"));
        offscreenGraph.drawString("N = no change,", 135, 265);
        offscreenGraph.setColor(Color.BLUE);
        offscreenGraph.drawString("currently compared items", 220, 265);
        
        // SelectSort
        offscreenGraph.setColor(Color.BLACK);
        offscreenGraph.drawString("select sort", 410, 40);
        offscreenGraph.setColor(Color.decode("0x00CC00"));
        offscreenGraph.drawString("(M) = minimum in current loop,", 410, 265);
        offscreenGraph.setColor(Color.RED);
        offscreenGraph.drawString("(R) = item to replace with minimum", 580, 265);
        
        // QuickSort
        offscreenGraph.setColor(Color.BLACK);
        offscreenGraph.drawString("quick sort", 20, 290);
        offscreenGraph.setColor(Color.BLUE);
        offscreenGraph.drawString("BLUE is current pivot,", 20, 515);
        offscreenGraph.setColor(Color.BLACK);
        offscreenGraph.drawString("BLACK lines delimites current sub-array", 140, 515);
        offscreenGraph.setColor(Color.decode("0x00CC00"));
        offscreenGraph.drawString("GREEN: currently swapped items", 20, 530);
        
        // InsertSort
        offscreenGraph.setColor(Color.BLACK);
        offscreenGraph.drawString("insert sort", 410, 290);
        offscreenGraph.setColor(Color.BLUE);
        offscreenGraph.drawString("BLUE is current item,", 410, 515);
        offscreenGraph.setColor(Color.RED);
        offscreenGraph.drawString("GRAY/RED shows currently inserted (moved) item", 528, 515);
        
        bubble.paint(offscreenGraph);
        select.paint(offscreenGraph);
        quick.paint(offscreenGraph);
        insert.paint(offscreenGraph);

        g.drawImage(offscreenImage, 0, 0, this);
        
        synchronized(this)
        {
            notifyAll();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    @Override
    public void addNotify()
    {
        super.addNotify();

        offscreenImage = createImage(frameDim.width, frameDim.height);
        offscreenGraph = offscreenImage.getGraphics();
    }
} 
