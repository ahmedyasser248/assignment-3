package ProducerConsumer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
public class Producer {
    String name;
    private BlockingQueue< Product > ProductsQueue =
            new LinkedBlockingQueue< Product >();
    private ExecutorService executorService =
            Executors.newCachedThreadPool();
    private List< Machine > Machines =
            new LinkedList< Machine >();
    private volatile boolean shutdownCalled = false;
    Producer(){

    }

    public void addMachine(Machine machine){
        Machines.add(machine);
    }



    synchronized public boolean sendToMachine(Product product)
    {
        if(!shutdownCalled)
        {
            try
            {
                ProductsQueue.put(product);

                int index =-1;
                for(int i = 0 ; i < Machines.size();i++){
                    if(Machines.get(i).Available){
                        index=i;

                        break;
                    }
                }
                if(index!=-1){
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
    public static void main(String [] args){
        Product product1 = new Product();product1.name="product1";
        Product product2= new Product();product2.name="product2";
        Product product3 = new Product();product3.name="product3";
        Product product4 =new Product();product4.name="product4";
        Product product5=new Product();product5.name="product5";
        Product product6 = new Product();product6.name="product6";
        Product product7= new Product();product7.name="product7";
        Product product8 = new Product();product8.name="product8";
        Product product9 = new Product();product9.name="product9";
        // products
        Producer producer0= new Producer();producer0.name="producer0";
        Producer producer1 = new Producer();producer1.name="producer1";
        Producer producer2 = new Producer();producer2.name="producer2";
        Producer producer3 = new Producer();producer3.name="producer3";
        Producer producer4 = new Producer();producer4.name="producer4";
        Producer producer5 = new Producer();producer5.name="producer5";
        Machine machineA =new Machine(2335);
        Machine machineB = new Machine(3555);
        Machine machine1 = new Machine(1000);
        Machine machine2 = new Machine(6435);
        Machine machine3 = new Machine(13117);
        Machine machine4= new Machine(9041);
        Machine machine5 = new Machine(6027);
        Machine machine6 = new Machine(5537);
        producer0.addMachine(machineA);
        producer0.addMachine(machineB);
        producer1.addMachine(machine1);
        producer2.addMachine(machine1);
        producer2.addMachine(machine2);
        producer2.addMachine(machine3);
        producer3.addMachine(machine3);
        producer3.addMachine(machine4);
        machine1.setOutput(producer4);
        machine2.setOutput(producer4);
        machine3.setOutput(producer4);
        machine4.setOutput(producer5);
        producer5.addMachine(machine5);
        producer4.addMachine(machine6);
        machineA.setOutput(producer2);
        machineB.setOutput(producer3);
        //adding products
        producer0.sendToMachine(product1);
        producer0.sendToMachine(product2);
        producer0.sendToMachine(product3);
        producer0.sendToMachine(product4);
        producer0.sendToMachine(product5);
        producer1.sendToMachine(product6);
        producer1.sendToMachine(product7);
        producer1.sendToMachine(product8);
        producer1.sendToMachine(product9);




        System.out.println("the producer has finished");
    }
}

