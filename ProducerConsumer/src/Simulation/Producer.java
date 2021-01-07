package Simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Producer  {
    //Will be used as queue to get first out .
   private ArrayList<Product> inputs= new ArrayList<>() ;
    //the machines each producer is connected to .
    private ArrayList<Machine> outputs = new ArrayList<>() ;
    //store the state of each machine
    private ArrayList<Boolean> stateOfMachines = new ArrayList<>() ;
    public Producer(){}
    // used for first Q only
    public Producer(ArrayList<Product> inputs ,ArrayList<Machine> outputs){
        this.inputs=inputs ;
        this.outputs=outputs ;
        for (int i = 0 ; i < outputs.size() ; i++){
            stateOfMachines.add(true);
        }
    }
    //3rd constructor can be used for rest Q's
    public Producer (ArrayList<Machine> outputs){
        this.outputs=outputs;
        for (int i = 0 ; i < outputs.size() ; i++){
            stateOfMachines.add(true);
        }
    }

    public void sendProducts(){
        //if size==0 then nothing will be sent

                    for (int i = 0;i < outputs.size()&&i<inputs.size();i++){
                        if(!stateOfMachines.get(i)){
                            continue;
                        }
                        Product toBeSent=getAProduct() ;
                        outputs.get(i).startProcess(i,toBeSent,this) ;
                        stateOfMachines.set(i,false) ;
                        if (inputs.isEmpty()){
                            break ;
                        }
                    }

        }
        //called by machine to request an object when it finishes.
        public void sendProductToMachine(int index){
            if(!inputs.isEmpty()){
                Product temp = getAProduct();
                outputs.get(index).startProcess(index,temp,this);
            }
        }
        //when a product is sent to producer from machine
        //it is added to inputs and state of machines isincreased by 1
        public void receiveProductFromMachine(Product product){
            if(!outputs.isEmpty()){
                inputs.add(product);
                sendProducts();
            }
        }
    public Product getAProduct(){
        Product requiredOne = inputs.get(0);
        inputs.remove(0);
        return requiredOne;
    }

    // too add a product when the machine finishes
    public void addProduct(Product product){
        inputs.add(product);
    }
    //setters and getters to achieve encapsulation with private fields .
    public void updateStateOfMachine(int index){
            stateOfMachines.set(index, true);
    }

    public ArrayList<Boolean> getStateOfMachines() {
        return stateOfMachines;
    }

    public ArrayList<Machine> getOutputs() {
        return outputs;
    }

    public ArrayList<Product> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Product> inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(ArrayList<Machine> outputs) {
        this.outputs = outputs;
    }

    public void setStateOfMachines(ArrayList<Boolean> stateOfMachines) {
        this.stateOfMachines = stateOfMachines;
    }
    public static void main(String [] args){
        Machine machine = new Machine();
        Machine machine1 = new Machine();
        Product product= new Product();
        Product product1 = new Product();
        Product product2 = new Product();
        ArrayList<Product> products=new ArrayList<>();
        products.add(product);
        products.add(product1);
        products.add(product2);
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());
        Machine machine2 = new Machine();
        ArrayList<Machine> arr =  new ArrayList<>();
        arr.add(machine);
        arr.add(machine1);
        arr.add(new Machine());
        Producer  producer = new Producer(products,arr);
        producer.sendProducts();
        System.out.println("test");

    }
}
