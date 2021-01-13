package ProducerConsumer;

import SnapShot.CareTaker;
import SnapShot.Originator;
import sample.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class myRun implements Runnable{

    Originator originator;
    CareTaker careTaker;
    ArrayList<Long> time;
    Stack<Product> stack = new Stack<Product>();
    TreeNode root;
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<Producer> producers = new ArrayList<>();

    myRun(Originator o, CareTaker c, ArrayList<Long>time, TreeNode root){
        this.originator = o;
        this.careTaker = c;
        this.time = time;
        this.root = root;
    }

    @Override
    public void run() {
        long time1 = time.get(0);
        originator.setState(time1);
        careTaker.add(originator.saveStateToMemento());
        long time2 = time.get(1);
        originator.setState(time2);
        careTaker.add(originator.saveStateToMemento());
        long time3 = time.get(2);
        originator.setState(time3);
        careTaker.add(originator.saveStateToMemento());
        long time4 = time.get(3);
        originator.setState(time4);
        careTaker.add(originator.saveStateToMemento());

        Product product1 = new Product();product1.name="product1";
        Product product2= new Product();product2.name="product2";
        Product product3 = new Product();product3.name="product3";
        Product product4 =new Product();product4.name="product4";
        Product product5=new Product();product5.name="product5";

        // products
        Producer producer0= new Producer();producer0.name="producer0";



        Machine machine1 =new Machine(time1);
        Machine machine2 = new Machine(time2);
        Machine machine3 = new Machine(time3);
        Machine machine4 = new Machine(time4);

        producer0.addMachine(machine1);
        producer0.addMachine(machine2);
        producer0.addMachine(machine3);
        producer0.addMachine(machine4);


        machine1.setFinalOutput(stack);
        machine2.setFinalOutput(stack);
        machine3.setFinalOutput(stack);
        machine4.setFinalOutput(stack);


        //adding products
        producer0.sendToMachine(product1);
        producer0.sendToMachine(product2);
        producer0.sendToMachine(product3);
        producer0.sendToMachine(product4);
        producer0.sendToMachine(product5);


        /*
        long time1 = time.get(0);
        originator.setState(time1);
        careTaker.add(originator.saveStateToMemento());
        long time2 = time.get(1);
        originator.setState(time2);
        careTaker.add(originator.saveStateToMemento());
        long time3 = time.get(2);
        originator.setState(time3);
        careTaker.add(originator.saveStateToMemento());
        long time4 = time.get(3);
        originator.setState(time4);
        careTaker.add(originator.saveStateToMemento());
        long time5 = time.get(4);
        originator.setState(time5);
        careTaker.add(originator.saveStateToMemento());
        long time6 = time.get(5);
        originator.setState(time6);
        careTaker.add(originator.saveStateToMemento());
        long time7 = time.get(6);
        originator.setState(time7);
        careTaker.add(originator.saveStateToMemento());
        long time8 = time.get(7);
        originator.setState(time8);
        careTaker.add(originator.saveStateToMemento());
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
        Machine machineA =new Machine(time1);
        Machine machineB = new Machine(time2);
        Machine machine1 = new Machine(time3);
        Machine machine2 = new Machine(time4);
        Machine machine3 = new Machine(time5);
        Machine machine4= new Machine(time6);
        Machine machine5 = new Machine(time7);
        Machine machine6 = new Machine(time8);
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
        machine5.setFinalOutput(stack);
        machine6.setFinalOutput(stack);
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

*/

        try {
            while (stack.size()!=5) {
                Thread.sleep(1000);
            }
            System.err.println("errooooor");
            if(stack.size()==5){

                producer0.finishConsumption();
                /*
                producer1.finishConsumption();

                producer2.finishConsumption();
                producer3.finishConsumption();
                producer4.finishConsumption();
                producer5.finishConsumption();
*/

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
