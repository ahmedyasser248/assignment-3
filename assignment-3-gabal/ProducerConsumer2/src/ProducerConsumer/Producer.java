package ProducerConsumer;
import SnapShot.CareTaker;
import SnapShot.Originator;
import sample.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Producer {
    String name;
    private BlockingQueue< Product > ProductsQueue =
            new LinkedBlockingQueue< Product >();
    private ExecutorService executorService =
            Executors.newCachedThreadPool();
    private List< Machine > Machines =
            new LinkedList< Machine >();
    private volatile boolean shutdownCalled = false;

    public Producer(){

    }

    public void addMachine(Machine machine){
        Machines.add(machine);
    }


     public synchronized boolean sendToMachine(Product product)
    {try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
        if(!shutdownCalled)
        {
            try
            {
                ProductsQueue.put(product);

                int index =-1;
                for(int i = 0 ; i < Machines.size();i++){
                    if(Machines.get(i).Available){
                        Machines.get(i).Available=false;
                        index=i;

                        break;
                    }
                }
                if(index!=-1){
                    //Machines.get(index).Available = false;
                    Machines.get(index).setProducer(this);
                    Machines.get(index).setProductsQueue(this.ProductsQueue);
                    Machines.get(index).number=index;
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

    public void start(Originator originator, CareTaker careTaker, TreeNode root){
        int min = 2000;// 2 seconds
        int max = 15000;// 10 seconds
        ArrayList<Long> time = new ArrayList<Long>();
        for (int i=0; i<8;i++){
            time.add( (long)(Math.random() * (max - min + 1) + min ));
        }
        Thread t1 = new Thread(new myRun(originator,careTaker,time,root));
        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void replay(Originator originator, CareTaker careTaker,TreeNode root){
        ArrayList<Long> time2 = new ArrayList<Long>();
        for (int i=0; i<8;i++){
            originator.getStateFromMemento(careTaker.get(i));
            time2.add(originator.getState());
        }
        originator = new Originator();
        careTaker = new CareTaker();
        Thread t2 = new Thread(new myRun(originator,careTaker,time2,root));
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String [] args){
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();
        TreeNode root = new TreeNode();
        new Producer().start(originator,careTaker,root);
        System.out.println("25iraaaaaan");
        //new Producer().replay(originator,careTaker,root);
        //System.out.println("elhamdullah");
    }


}

