package ProducerConsumer;
import SnapShot.CareTaker;
import SnapShot.Originator;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sample.Shape;
import sample.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Producer implements Subject{
    String name;
    public BlockingQueue< Product > ProductsQueue =
            new LinkedBlockingQueue< Product >();
    private ExecutorService executorService =
            Executors.newCachedThreadPool();
    private List< Machine > Machines =
            new LinkedList< Machine >();
    private volatile boolean shutdownCalled = false;

    public long producerNum;

    public Producer(){

    }

    public void addMachine(Machine machine){
        Machines.add(machine);
    }


    public synchronized boolean sendToMachine(Product product, TreeNode root, Canvas canvas, int shapeCount, Shape[] shapes, ArrayList<Shape> joinXY)
    {
        if(!shutdownCalled)
        {
            try
            {
                ProductsQueue.put(product);

                int index =-1;
                for(int i = 0 ; i < Machines.size();i++){
                    if(Machines.get(i).Available){
                        Machines.get(i).Available = false;
                        index=i;

                        break;
                    }
                }
                if(index!=-1){
                    //Machines.get(index).Available = false;
                    Machines.get(index).setProducer(this);
                    Machines.get(index).setProductsQueue(this.ProductsQueue);
                    Machines.get(index).number=index;
                    Machines.get(index).setRoot(root);
                    Machines.get(index).setGraph(canvas,shapeCount,shapes,joinXY);
                    executorService.execute(Machines.get(index));}
            }
            catch(InterruptedException ie)
            {
                Thread.currentThread().interrupt();
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    public void finishConsumption()
    {
        for(Machine machine : Machines)
        {
            machine.cancelExecution();
        }

        executorService.shutdown();
    }

    public void start(Originator originator, CareTaker careTaker, TreeNode root ,int products,Canvas canvas,
                      int shapeCount, Shape[] shapes, ArrayList<Shape> joinXY,ArrayList<Button> buttons,
                      TextField Field){
        ArrayList<Color> color = new ArrayList<>();
        color.add(Color.RED);
        color.add(Color.YELLOW);
        color.add(Color.BLUE);
        color.add(Color.GREEN);
        color.add(Color.GREY);
        color.add(Color.CYAN);
        color.add(Color.PINK);
        color.add(Color.AQUA);
        color.add(Color.BROWN);
        color.add(Color.SKYBLUE);
        ArrayList<Color> chosenColors = new ArrayList<>();
        for (int i=0; i<products;i++){
            chosenColors.add(color.get((int)(Math.random() * (8 - 0 + 1) + 0 )));
        }
        int min = 6000;// 2 seconds
        int max = 15000;// 10 seconds
        ArrayList<Long> time = new ArrayList<Long>();
        for (int i=0; i<root.numberOfMachines;i++){
            time.add( (long)(Math.random() * (max - min + 1) + min ));
        }
        Thread t1 = new Thread(new myRun(originator,careTaker,time,root,products,canvas
                ,shapeCount,shapes,joinXY,buttons,chosenColors,Field));
        t1.start();


    }

    public void replay(Originator originator, CareTaker careTaker, TreeNode root, int products, Canvas canvas,
                       int shapeCount, Shape[] shapes, ArrayList<Shape> joinXY, ArrayList<Button> buttons,
                       TextField Field){
        ArrayList<Long> time2 = new ArrayList<Long>();
        for (int i=0; i<root.numberOfMachines;i++){
            originator.getStateFromMemento(careTaker.get(i));
            time2.add(originator.getState());
        }
        ArrayList<Color> colors = new ArrayList<>();
        for (int i=0; i<products;i++){
            originator.getColorsFromMemento(careTaker.getColor(i));
            colors.add(originator.getColors());
        }
        originator = new Originator();
        careTaker = new CareTaker();
        Thread t2 = new Thread(new myRun(originator,careTaker,time2,root,products,canvas,shapeCount,
                shapes,joinXY,buttons,colors,Field));
        t2.start();

    }


    public static void main(String [] args){
        /*
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();
        TreeNode root = new TreeNode();
        //new Producer().start(originator,careTaker,root);
        System.out.println("25iraaaaaan");
        //new Producer().replay(originator,careTaker,root);
        System.out.println("elhamdullah");

         */
    }


}

