package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TreeNode  {
     public  List<TreeNode> children;
     public List<TreeNode> parent;
     public long Code;
     public Shape shape;
     private TreeNode selectedNode;

     public int numberOfMachines = 0;
    public int numberOfProducers = 1;

   
    public TreeNode() {
    	
    }
    
    public TreeNode(long code,Shape shape) {
      
        this.children = new ArrayList<>();
        this.parent = null;
        this.Code = code;
        this.shape=shape;
        
    }

    public TreeNode(long data, TreeNode  parent) {
    
        this.children = new ArrayList<>();
        this.Code = data;
        this.parent.add(parent);
        
        parent.addChild(this);   	
    }


    public void incrementNumberOfMachines(){
        this.numberOfMachines++;
    }
    public void incrementNumberOfProducers(){
        this.numberOfProducers++;
    }
    public void inorder(TreeNode root,TreeNode node,long data)
    {
        if (node == null)
            return ;
 
        // 
        int total = node.getChildren().size();
        // 
        for (int i = 0; i < total - 1; i++)
            inorder(root,(TreeNode) node.getChildren().get(i),data);
 
        // Print the current node's data
        if(node.getCode()==data) root.selectedNode=node;
 
         inorder(root,(TreeNode) node.getChildren().get(total - 1),data);
    }
    
    
    public TreeNode GetNode (TreeNode root,long Code){
    	inorder(root,root,Code);
    	return root.selectedNode;
    }
    
    public List<TreeNode> getChildren() {
        return children;
    }

    public void addParent(TreeNode  parent) {
        
        parent.addChild(this);   
        this.parent.add(parent) ;
    }
    
    public List<TreeNode> getParent() {
        return this.parent;
    }

    public void addChild(Shape shape) {
        TreeNode  child = new TreeNode(shape.hashCode(),shape);
        this.children.add(child);
    }



    public void addChild(TreeNode  child) {
        this.children.add(child);
    }

    public long getCode() {
        return this.Code;
    }

    public void setCode(long data) {
        this.Code = data;
    }

    public boolean isRootNode() {
        return (this.parent == null);
    }

    public boolean isLeafNode() {
        return (this.children.size() == 0);
    }

    public void removeParent() {
        this.parent = null;
    }
    public void Traverse(TreeNode root ) {

        // Stack to store the nodes
        Stack<TreeNode> nodes = new Stack<>();

        // push the current node onto the stack
        nodes.push(root);

        // Loop while the stack is not empty
        while (!nodes.isEmpty()) {

            // Store the current node and pop
            // it from the stack
            TreeNode curr = nodes.pop();

            // Current node has been travarsed
            if (curr != null) {
                if (curr.shape instanceof CircleShape) {
                    System.out.println(" (machine - " + curr.Code + ")");
                } else {
                    System.out.println(" (queue - " + curr.Code + ")");
                }

                // Store all the childrent of
                // current node from right to left.
                for (int i = curr.children.size() - 1; i >= 0; i--) {
                    nodes.add(curr.children.get(i));
                }
            }
        }
    }
    public boolean ErrMachineCheck(TreeNode root )
    {

        Stack<TreeNode> nodes = new Stack<>();

        nodes.push(root);

        while (!nodes.isEmpty())
        {

            TreeNode curr = nodes.pop();
            if (curr != null)
            {
                if(curr.shape instanceof CircleShape) {
                    if (curr.isLeafNode())return false;
                }

                for(int i = curr.children.size() - 1; i >= 0; i--)
                {
                    nodes.add(curr.children.get(i));
                }
            }
        }

        return true;
    }
    public TreeNode FindNode(TreeNode root ,Long Code) 
    {
         
        // Stack to store the nodes
        Stack<TreeNode> nodes = new Stack<>();
     
        // push the current node onto the stack
        nodes.push(root);
     
        // Loop while the stack is not empty
        while (!nodes.isEmpty()) 
        {
             
            // Store the current node and pop
            // it from the stack
            TreeNode curr = nodes.pop();
     
            // Current node has been travarsed
            if (curr != null)
            {
                if(curr.Code==Code) {
                	return curr;
                }
     
                // Store all the childrent of 
                // current node from right to left.
                for(int i = curr.children.size() - 1; i >= 0; i--) 
                {
                    nodes.add(curr.children.get(i));
                } 
            }
        }
		return null;
    }

    
    
    
    


}