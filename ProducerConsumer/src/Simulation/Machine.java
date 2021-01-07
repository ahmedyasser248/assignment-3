package Simulation;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

public class Machine implements Runnable  {
    int min = 3000;// 2 seconds
    int max = 150000;// 10 seconds
    long intervalSimulation;//being created when the machine is created for first time.
    boolean processing=false;
    Producer input ;
    Product currentProduct = new Product() ;
    Producer output;
    int index ;
    public Machine(){
        this.intervalSimulation=(long)Math.random() * (max - min + 1) + min ;
    }

    public void setOutput(Producer output) {
        this.output = output;
    }

    public void setInput(Producer input) {
        this.input = input;
    }

    public void setProduct(Product product){
        this.currentProduct=product;
    }
    public Product getProduct(){
        return this.currentProduct;
    }

    public void startProcess(int IndexOfMachine,Product product,Producer producer){
        processing=true;
        setProduct(product);
        setInput(producer);
        this.index=IndexOfMachine;
        Thread test = new Thread(this);
        test.start();

    }
    @Override
    public void run() {

        synchronized (currentProduct){
            try {
                while (processing){//to avoid superior wake up
                    System.out.println("the index of machine of "+index);
                    System.out.println("here1");
                    currentProduct.wait(intervalSimulation);
                    System.out.println("the index of machine of "+index);
                    System.out.println("here2");
                processing=false;
                }
                notifyProducer();
                transferProduct();
            } catch (InterruptedException e) {
                System.out.println("thread is interrepted");
            }

        }
    }
    //notify producer for the updates as it ended processing the product
    public void notifyProducer(){
        this.input.updateStateOfMachine(index);
        this.input.sendProductToMachine(index);
    }
    public void transferProduct(){
        //this.output.receiveProductFromMachine(currentProduct);
    }
}

