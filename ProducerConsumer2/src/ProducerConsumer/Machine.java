package ProducerConsumer;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Main;
import sample.Shape;
import sample.TreeNode;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Machine implements Runnable, Observer {
    boolean Available =true;
    public long intervalSimulation;
    Producer producer;
    Producer output ;
    Stack<Product> stack;
    boolean finalOutput = false;
    public long machineNum ;
    public TreeNode root;

    int number ;

    //set canvas
    public Canvas canvas;
    public ArrayList<Shape> joinXY;
    public int shapeCount;
    public Shape[] shapes;

    private BlockingQueue<Product > productsQueue;
    private volatile boolean keepProcessing;


    Machine(BlockingQueue<Product> products){
        this.productsQueue=products;
    }
    Machine(long time){
        this.intervalSimulation=time;
        System.out.println(intervalSimulation);
        //this.ending = ending;
    }
    public void setOutput(Producer producer){
        this.output=producer;
    }
    public void setProductsQueue(BlockingQueue<Product> productsQueue) {
        this.productsQueue = productsQueue;
    }
    public void setFinalOutput(Stack<Product> stack){
        this.stack=stack;
        //System.out.println("done");
        finalOutput = true;
    }
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    public synchronized void gett(ArrayList<Shape> joinXY,Canvas canvas){
            GraphicsContext g = canvas.getGraphicsContext2D();
            for(int i=0;i<joinXY.size()-1;i++) {
                g.beginPath();
                int k = joinXY.get(i).left + joinXY.get(i).width / 2;
                int m = joinXY.get(i).top + joinXY.get(i).height / 2;
                int k2 = joinXY.get(i+1).left + joinXY.get(i+1).width / 2;
                int m2 = joinXY.get(i+1).top + joinXY.get(i+1).height / 2;
                g.moveTo(k, m);
                g.lineTo(k2, m2);
                g.stroke();
                i++;
            }
    }
    public synchronized void paintCanvas(Canvas canvas,int shapeCount,Shape[] shapes,ArrayList<Shape> joinXY) {
        // Redraw the shapes.  The entire list of shapes
        // is redrawn whenever the user adds a new shape
        // or moves an existing shape.
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE); // Fill with white background.
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        for (int i = 0; i < shapeCount; i++) {
            Shape s = shapes[i];
            s.draw(g);
            gett(joinXY,canvas);
        }
    }

    public void setGraph(Canvas canvas,int shapeCount,Shape[] shapes,ArrayList<Shape> joinXY){
        this.canvas = canvas;
        this.shapeCount = shapeCount;
        this.shapes = shapes;
        this.joinXY = joinXY;
    }


    @Override
     public synchronized void run() {

        while (!productsQueue.isEmpty()) {
            try {

                Product temp = productsQueue.poll(1, TimeUnit.SECONDS);

                if (temp != null) {

                    root.FindNode(root,machineNum).shape.setColor(temp.color);
                    root.FindNode(root, producer.producerNum).shape.num--;


                    paintCanvas(canvas,shapeCount,shapes,joinXY);

                    Thread.sleep(intervalSimulation);

                    root.FindNode(root,machineNum).shape.setColor(Color.WHITE);
                    root.FindNode(root, output.producerNum).shape.num++;
                    paintCanvas(canvas,shapeCount,shapes,joinXY);

                    System.out.println(temp.name + "    "+producer.name+" the machine" + number);

                    if(output!=null){
                        this.output.sendToMachine(temp,this.root,canvas,shapeCount,shapes,joinXY);
                    }
                    if (finalOutput){
                        this.stack.push(temp);
                    }
                }
            } catch (Exception e) {
                System.out.println("error in run in Machine");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
        notifyProducers();
        //this.cancelExecution();

    }

    // observer notify producer
    @Override
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

