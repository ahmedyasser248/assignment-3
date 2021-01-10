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
    static int min = 1000;// 2 seconds
    static int max = 18000;// 10 seconds
    private BlockingQueue< Product > ProductsQueue =
            new LinkedBlockingQueue< Product >();
    ArrayList<Boolean>stateOfMachine =new ArrayList<>();
    private ExecutorService executorService =
            Executors.newCachedThreadPool();
    private List< Machine > Machines =
            new LinkedList< Machine >();
    private volatile boolean shutdownCalled = false;

    public Producer(ArrayList<Machine>machines){
            this.Machines=machines;
            for (int i = 0 ; i< stateOfMachine.size();i++){
                stateOfMachine.add(true);
            }
    }
    Producer(){

    }

    public void addMachine(Machine machine){
        machine.setProducer(this);
        stateOfMachine.add(true);
        machine.setProductsQueue(ProductsQueue);
       machine.number =Machines.size();
        Machines.add(machine);

    }

    public void setProductsQueue(BlockingQueue<Product> productsQueue) {
        ProductsQueue = productsQueue;
    }

    synchronized public void notifyProducer(int index){
        stateOfMachine.set(index,true);
    }
    synchronized public boolean sendToMachine(Product product)
    {
        if(!shutdownCalled)
        {
            try
            {
                ProductsQueue.put(product);

                int index =stateOfMachine.indexOf(true);
                if(index!=-1){
                stateOfMachine.set(index,false);
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
        Producer producer1= new Producer();producer1.name="producer2";
        Producer producer2= new Producer();producer2.name="producer3";
        Machine mach7 = new Machine(9041);
        Machine mach8 = new Machine(6027);
        producer2.addMachine(mach7);
        producer2.addMachine(mach8);
        Machine mach4 = new Machine(9519);mach4.setOutput(producer2);
        Machine mach5 = new Machine(9490);mach5.setOutput(producer2);
        Machine mach6 = new Machine(5537);mach6.setOutput(producer2);
        producer1.addMachine(mach4);
        producer1.addMachine(mach5);
        producer1.addMachine(mach6);
        Machine mach = new Machine(13117);mach.setOutput(producer1);
        Machine mach1 = new Machine(1000);mach1.setOutput(producer1);
        Machine mach3 = new Machine(6435);mach3.setOutput(producer1);
        Producer producer = new Producer();producer.name="producer1";
        producer.addMachine(mach);
        producer.addMachine(mach1);
        producer.addMachine(mach3);
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();
        product1.name="name1";
        producer.sendToMachine(product1);
        product2.name="name2";
        producer.sendToMachine(product2);
        product3.name="name3";
        producer.sendToMachine(product3);
        product4.name="name4";
        producer.sendToMachine(product4);
        product5.name="name5";
        producer.sendToMachine(product5);
        System.out.println("the producer has finished");
    }
}

