package ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Machine implements Runnable{
    boolean Available =true;
    int min = 1000;// 2 seconds
    int max = 18000;// 10 seconds
    long intervalSimulation;
    Producer producer;
    Producer output ;

    int number ;
    private BlockingQueue<Product > productsQueue;
    private volatile boolean keepProcessing;
    Machine(BlockingQueue<Product> products){
        this.productsQueue=products;
    }
    Machine(long time){
        this.intervalSimulation=time;
        System.out.println(intervalSimulation);
    }
    public void setOutput(Producer producer){
        this.output=producer;
    }
    public void setProductsQueue(BlockingQueue<Product> productsQueue) {
        this.productsQueue = productsQueue;
    }

    @Override
    synchronized public void run() {
                    while (!productsQueue.isEmpty()) {
                        try {
                            Available =false;
                            Product temp = productsQueue.poll(5, TimeUnit.SECONDS);

                            if (temp != null) {
                                Thread.sleep(intervalSimulation);
                                System.out.println(temp.name);
                                System.out.println(producer.name+" the machine" + number);
                                System.out.println("- -- - -- - - - - -- -");
                                if(output!=null){
                                    this.output.sendToMachine(temp);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("error in run in Machine");
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }

                    }

                    notifyProducers();
                    this.cancelExecution();


    }
    public void notifyProducers(){
        this.Available =true;
    }

    public void setProducer(Producer producer){
        this.producer=producer;
    }
    public void cancelExecution()
    {
        this.keepProcessing = false;
    }
}

