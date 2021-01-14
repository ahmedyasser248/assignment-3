package ProducerConsumer;

import SnapShot.CareTaker;
import SnapShot.Originator;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.Shape;
import sample.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class myRun implements Runnable{

    Originator originator;
    CareTaker careTaker;
    ArrayList<Long> time;
    Stack<Product> stack = new Stack<Product>();
    TreeNode root;
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<Producer> producers = new ArrayList<>();
    public  int numberOfMachines;
    int count = 0;
    int countProduct = 1;
    int products;
    int k =0;
    int j = 0;
    int o =0;
    ArrayList<Product> productArrayList = new ArrayList<>();
    public  ArrayList<Color> color;
    //set canvas
    public Canvas canvas;
    public ArrayList<Shape> joinXY;
    public int shapeCount;
    public Shape[] shapes;
    public ArrayList<Button> buttons;
    public TextField Field;

    myRun(Originator o, CareTaker c, ArrayList<Long>time, TreeNode root, int products,
          Canvas canvas, int shapeCount, Shape[] shapes, ArrayList<Shape> joinXY, ArrayList<Button> buttons,
          ArrayList<Color> chosenColors, TextField Field){
        this.originator = o;
        this.careTaker = c;
        this.time = time;
        this.root = root;
        this.numberOfMachines = this.root.numberOfMachines;
        this.products = products;
        this.color = chosenColors;
        this.canvas = canvas;
        this.shapeCount = shapeCount;
        this.shapes = shapes;
        this.joinXY = joinXY;
        this.buttons = buttons;
        this.Field = Field;
        //System.err.println(products);
    }

    public void preOrder(TreeNode root ){
        List<TreeNode> rootMachine = root.getChildren();
        boolean flag;
        boolean exist = false;
        int place = j;
        for (int i = 0; i < rootMachine.size(); i++) {
            for(int d = 0;d<machines.size();d++){
                if (machines.get(d).machineNum == rootMachine.get(i).Code){
                    producers.get(place).addMachine(machines.get(d));
                    exist = true;
                }
            }
            if (exist){
                continue;
            }
            machines.add(new Machine(this.time.get(k++)));
            machines.get(machines.size()-1).machineNum = rootMachine.get(i).Code;
            producers.get(place).addMachine(machines.get(machines.size()-1));
           // System.out.println(producers.get(place).name+" add : machine "+ machines.get(machines.size()-1).intervalSimulation+"    "
             //       +rootMachine.get(i).Code);
            flag = false;
            if(!rootMachine.get(i).getChildren().get(0).isLeafNode()){
                for (int c = 0; c < producers.size(); c++) {
                    if (producers.get(c).producerNum == rootMachine.get(i).getChildren().get(0).Code){
                        machines.get(machines.size()-1).setOutput(producers.get(c));
                        flag = true;

                        //System.out.println("machine : "+machines.get(machines.size()-1).intervalSimulation+"    "+rootMachine.get(i).Code+
                          //      "  setOutput : "+producers.get(c).name+"("+producers.get(c).producerNum+")");
                        break;
                    }
                }
                if(!flag){
                    producers.add(new Producer());
                    producers.get(++j).name = "producer" + count++;
                    producers.get(j).producerNum = rootMachine.get(i).getChildren().get(0).Code;
                    //System.out.println("code : "+ producers.get(j).producerNum);
                    machines.get(machines.size()-1).setOutput(producers.get(j));

                   // System.out.println("machine : "+machines.get(machines.size()-1).intervalSimulation+"    "+rootMachine.get(i).Code+"  setOutput : "
                     //       +producers.get(j).name+"("+producers.get(j).producerNum+")");
                    preOrder(rootMachine.get(i).getChildren().get(0));
                }
            }
            else {
                producers.add(new Producer());
                producers.get(++j).name = "producer" + count++;
                producers.get(j).producerNum = rootMachine.get(i).getChildren().get(0).Code;
                System.out.println("code : "+ producers.get(j).producerNum);
                machines.get(machines.size()-1).setFinalOutput(stack);
                machines.get(machines.size()-1).setOutput(producers.get(j));
                //System.out.println("-----------put in stack--------");
                //System.out.println("machine : "+machines.get(machines.size()-1).intervalSimulation+"    "+rootMachine.get(i).Code+"  setFinalOutput : Stack");
            }

        }
        return;
    }

    @Override
    public void run() {
        //save time to replay
        for (int i = 0; i < this.numberOfMachines; i++) {
            originator.setState(this.time.get(i));
            careTaker.add(originator.saveStateToMemento());
        }
        producers.add(new Producer());
        producers.get(0).name = "producer" + count++;
        producers.get(0).producerNum = this.root.Code;
        root.FindNode(root, producers.get(0).producerNum).shape.num = products;
        System.out.println("code : "+ this.root.Code);
        preOrder(this.root);

        for (int i = 0; i < products; i++) {
            productArrayList.add(new Product(color.get(i)));
            originator.setColors(this.color.get(i));
            careTaker.addColor(originator.saveColorsToMemento());
            productArrayList.get(i).name = "Product" + countProduct++;
        }
        for (int i = 0; i < products; i++) {
            producers.get(0).sendToMachine(productArrayList.get(i),this.root,canvas,shapeCount,shapes,joinXY);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Terminate
        try {
            while (stack.size()!=products) {
                Thread.sleep(1000);
            }

            Thread.sleep(1000);
            if(stack.size()==products){
              for (int i = 0;i<producers.size();i++) {
                  producers.get(i).finishConsumption();
                  root.FindNode(root, producers.get(i).producerNum).shape.num = 0;
              }
              machines.get(0).paintCanvas(machines.get(0).canvas,machines.get(0).shapeCount,machines.get(0).shapes,
                      machines.get(0).joinXY);

              for (int i = 0;i<buttons.size();i++){
                  buttons.get(i).setDisable(false);
              }
              canvas.setDisable(false);
              Field.setDisable(false);

              System.out.println("Terminated");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
