package ProducerConsumer;

import javafx.scene.paint.Color;

public class Product {
    public Color color;
     public Product(Color c){
         this.color = c;
     }

    private int number;
    String name ;
    public void process() throws InterruptedException {
        System.out.println("got here 1");
        System.out.println("got here 2");
        Thread.sleep(6000);
    }

}
