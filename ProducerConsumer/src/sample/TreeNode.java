package sample;

import java.util.ArrayList;
import java.util.List;


public class TreeNode<T> {
    private final List<TreeNode<T>> children;
    private List<TreeNode<T>> parent;
    private T data;
    private TreeNode selectedNode;
   
    public TreeNode(T data) {
      
        this.children = new ArrayList<>();
        this.parent = null;
        this.data = data;
        
    }

    public TreeNode(T data, List<TreeNode<T>> parent) {
    
        this.children = new ArrayList<>();
        this.data = data;
        this.parent = parent;
        
        for(int i=0;i<parent.size();i++) parent.get(i).addChild(this);   	
    }

    public void inorder(TreeNode node,T data)
    {
        if (node == null)
            return ;
 
        // 
        int total = node.getChildren().size();
        // 
        for (int i = 0; i < total - 1; i++)
            inorder((TreeNode) node.getChildren().get(i),data);
 
        // Print the current node's data
        if(node.getData().equals(data)) this.selectedNode=node;
 
         inorder((TreeNode) node.getChildren().get(total - 1),data);
    }
    
    
    public TreeNode GetNode (TreeNode root,T data){
    	inorder(root,data);
    	return this.selectedNode;
    }
    
    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void addParent(TreeNode<T> parent) {
        
        parent.addChild(this);   
        this.parent.add(parent) ;
    }
    
    public List<TreeNode<T>> getParent() {
        return this.parent;
    }

    public void addChild(T data) {
        TreeNode<T> child = new TreeNode<>(data);
        this.children.add(child);
    }

    public void addChild(TreeNode<T> child) {
        this.children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
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


}