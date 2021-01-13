package ProducerConsumer;

public class Product {

    private int number ;
    String name ;
    public void process() throws InterruptedException {
        System.out.println("got here 1");
        System.out.println("got here 2");
        Thread.sleep(6000);
    }

}
