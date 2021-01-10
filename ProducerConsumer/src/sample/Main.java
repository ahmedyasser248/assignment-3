package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    Button maButton = new Button("change");
    Button machineButton = new Button("Add a Machine");
    TextField Field = new TextField();
    Button queueButton = new Button("Add a Queue");
    Button joinButton = new Button("Join");
    Button rootButton = new Button("Pick the beginning Q");
    Button beginButton = new Button("Start Simulation");
    Button endButton = new Button("Stop Simulation");
    private Shape[] shapes = new Shape[500];  // Contains shapes the user has drawn.
    private int shapeCount = 0; // Number of shapes that the user has drawn.
    private Canvas canvas; // The drawing area where the user draws.
    private Color currentColor = Color.WHITE;  // Color to be used for new shapes.
    private boolean joinn = false;
    private boolean selectroot = false;
    private int joinfig = 0;
    private int products = 0;
    public  ArrayList<Shape> joinXY = new ArrayList<Shape>();
    public TreeNode root ;


    @Override
    /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));*/
    public void start(Stage stage) {

        canvas = makeCanvas();
        paintCanvas();
        StackPane canvasHolder = new StackPane(canvas);
        canvasHolder.setStyle("-fx-border-width: 1px; -fx-border-color: #444");
        BorderPane root = new BorderPane(canvasHolder);
        //root.setStyle("-fx-border-width: 1px; -fx-border-color: #000000");
        root.setTop(makeToolPanel());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Producer/Consumer");
        stage.setResizable(false);
        stage.show();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("When joining figures >Join the right order please");
        a.show();
    }

    private Canvas makeCanvas() {
        // Creates a canvas, and add mouse listeners to implement dragging.
        // The listeners are given by methods that are defined below.
        Canvas canvas = new Canvas(800,600);
        canvas.setOnMousePressed( this::mousePressed );
        canvas.setOnMouseReleased( this::mouseReleased );
        canvas.setOnMouseDragged( this::mouseDragged );
        return canvas;
    }
    public void end(){
        System.out.println("hello");
    }
    public void begin() {
        if (this.joinXY.size() == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Put some machines and Queues and join them");
            a.show();
        } else {

            ArrayList<TreeNode> Helper = new ArrayList<TreeNode>();

            //this.root= new TreeNode(this.joinXY.get(0).hashCode(),this.joinXY.get(0)) ; //Assumption


            for (int i = 0; i < this.joinXY.size(); i += 2) {

                if (this.joinXY.get(i).hashCode() == this.root.shape.hashCode()) {
                    TreeNode child = new TreeNode(this.joinXY.get(i + 1).hashCode(), this.joinXY.get(i + 1));
                    this.root.addChild(child);

                } else {
                    TreeNode parent = new TreeNode(this.joinXY.get(i).hashCode(), this.joinXY.get(i));
                    TreeNode child = new TreeNode(this.joinXY.get(i + 1).hashCode(), this.joinXY.get(i + 1));
                    parent.addChild(child);
                    Helper.add(parent);
                }
            }

            while (!Helper.isEmpty()) {
                for (TreeNode node : Helper) {
                    TreeNode temp = root.FindNode(root, node.Code);
                    if (temp != null) {
                        temp.addChild(node.getChildren().get(0));
                        Helper.remove(node);
                        if (Helper.isEmpty()) break;
                    }

                }
            }


            //System.out.println(this.root.shape.hashCode());
            //System.out.println(this.joinXY.get(2).hashCode());
            System.out.println(this.root.shape);

        }
    }
    public void join(){
        this.joinn=true;
        machineButton.setDisable(true);
        queueButton.setDisable(true);
        beginButton.setDisable(true);
        rootButton.setDisable(true);
    }
    public void first(){
        this.selectroot = true;
    }
    public void textf(){
        String pr=Field.getText();
        if(pr.matches("\\d+")) {
            products = Integer.parseInt(pr);
            Field.clear();
            System.out.println("number is " + products);
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Only Numbers are allowed");
            a.show();
        }
    }
    public void change() throws InterruptedException {
        shapes[0].setColor(Color.RED);
        shapes[1].setColor(Color.BLUE);
        paintCanvas();
    }
    public void gett(){
        if (this.joinfig%2==0){
            this.joinn=false;
            this.shapeBeingjoined = null;
            GraphicsContext g = canvas.getGraphicsContext2D();
            for(int i=0;i<this.joinXY.size()-1;i++) {
                g.beginPath();
                int k = this.joinXY.get(i).left + this.joinXY.get(i).width / 2;
                int m = this.joinXY.get(i).top + this.joinXY.get(i).height / 2;
                int k2 = this.joinXY.get(i+1).left + this.joinXY.get(i+1).width / 2;
                int m2 = this.joinXY.get(i+1).top + this.joinXY.get(i+1).height / 2;
                g.moveTo(k, m);
                g.lineTo(k2, m2);
                g.stroke();
                i++;
            }
        }
    }

    private HBox makeToolPanel() {
        maButton.setOnAction( (e) -> {
            try {
                change();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        machineButton.setOnAction( (e) -> addShape( new CircleShape() ) );
        queueButton.setOnAction( (e) -> addShape( new RectShape() ) );
        joinButton.setOnAction( (e) -> join());
        rootButton.setOnAction( (e) -> first() );
        beginButton.setOnAction( (e) -> begin()  );
        endButton.setOnAction( (e) ->   end()  );
        ComboBox<String> combobox = new ComboBox<>();
        endButton.setStyle("-fx-background-color: Red");
        beginButton.setStyle("-fx-background-color: Green");
        combobox.setEditable(false);
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.CYAN,
                Color.MAGENTA, Color.YELLOW, Color.BLACK, Color.WHITE };
        String[] colorNames = { "Red", "Green", "Blue", "Cyan",
                "Magenta", "Yellow", "Black", "White" };
        combobox.getItems().addAll(colorNames);
        combobox.setValue("White");
        combobox.setOnAction(
                e -> currentColor = colors[combobox.getSelectionModel().getSelectedIndex()] );
        Field.setPromptText("number of products");
        Field.setOnAction( (e) -> textf() );
        HBox tools = new HBox(10);
        tools.getChildren().add(machineButton);
        //tools.getChildren().add(maButton);
        tools.getChildren().add(queueButton);
        tools.getChildren().add(joinButton);
        tools.getChildren().add(rootButton);
        tools.getChildren().add(beginButton);
        tools.getChildren().add(endButton);
        tools.getChildren().add(Field);
        //tools.getChildren().add(combobox);
        tools.setStyle("-fx-border-width: 5px; -fx-border-color: transparent; -fx-background-color: lightgray");
        return tools;
    }

    private void paintCanvas() {
        // Redraw the shapes.  The entire list of shapes
        // is redrawn whenever the user adds a new shape
        // or moves an existing shape.
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE); // Fill with white background.
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        for (int i = 0; i < shapeCount; i++) {
            Shape s = shapes[i];
            s.draw(g);
            gett();
        }
    }

    private void addShape(Shape shape) {
        // Add the shape to the canvas, and set its size/position and color.
        // The shape is added at the top-left corner, with size 80-by-50.
        // Then redraw the canvas to show the newly added shape.  This method
        // is used in the event listeners for the buttons in makeToolsPanel().
        shape.setColor(currentColor);
        shape.reshape(10,10,35,35);
        shapes[shapeCount] = shape;
        shapeCount++;
        paintCanvas();
    }


    // ------------ This part of the class defines methods to implement dragging -----------
    // -------------- These methods are added to the canvas as event listeners -------------

    private Shape shapeBeingDragged = null;  // This is null unless a shape is being dragged.
    private Shape shapeBeingjoined = null;
    // A non-null value is used as a signal that dragging
    // is in progress, as well as indicating which shape
    // is being dragged.

    private int prevDragX;  // During dragging, these record the x and y coordinates of the
    private int prevDragY;  //    previous position of the mouse.

    private void mousePressed(MouseEvent evt) {
        // User has pressed the mouse.  Find the shape that the user has clicked on, if
        // any.  If there is a shape at the position when the mouse was clicked, then
        // start dragging it.  If the user was holding down the shift key, then bring
        // the dragged shape to the front, in front of all the other shapes.
        int x = (int)evt.getX();  // x-coordinate of point where mouse was clicked
        int y = (int)evt.getY();  // y-coordinate of point
        for ( int i = shapeCount - 1; i >= 0; i-- ) {  // check shapes from front to back
            Shape s = shapes[i];
            if (s.containsPoint(x,y)) {
                if(this.selectroot==true){
                    if(s.isInstance("CircleShape")){
                        this.selectroot=false;
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Only Queue can be selected>>Press the Button again to select");
                        a.show();
                    }else {
                        this.root = new TreeNode(s.hashCode(), s);
                        System.out.println("bu ic tmam");
                        this.selectroot = false;
                    }
                }else{
                if(this.joinn){
                    this.joinfig++;
                    if(this.joinXY.size()==0) {
                        this.joinXY.add(s);
                    }else{
                        Shape temp = this.joinXY.get(this.joinXY.size()-1);
                        System.out.println(this.joinXY.size());
                        if(this.joinXY.size()%2!=0){
                            if(temp.hashCode() == s.hashCode()){
                                this.joinXY.remove(this.joinXY.size()-1);
                            }else{
                                this.joinXY.add(s);
                            }
                        }else{
                            this.joinXY.add(s);
                        }
                        System.out.println(this.joinXY.size());
                    }

                    shapeBeingjoined=s;
                }else {
                    shapeBeingDragged = s;
                }}
                prevDragX = x;
                prevDragY = y;
                if (evt.isShiftDown()) { // s should be moved on top of all the other shapes
                    for (int j = i; j < shapeCount-1; j++) {
                        // move the shapes following s down in the list
                        shapes[j] = shapes[j+1];
                    }
                    shapes[shapeCount-1] = s;  // put s at the end of the list
                    paintCanvas();  // repaint canvas to show s in front of other shapes
                }
                gett();
                return;
            }
        }


    }

    private void mouseDragged(MouseEvent evt) {
        // User has moved the mouse.  Move the dragged shape by the same amount.
        int x = (int)evt.getX();
        int y = (int)evt.getY();
        if (shapeBeingDragged != null) {
            shapeBeingDragged.moveBy(x - prevDragX, y - prevDragY);
            prevDragX = x;
            prevDragY = y;
            paintCanvas();      // redraw canvas to show shape in new position
        }
    }

    private void mouseReleased(MouseEvent evt) {
        // User has released the mouse.  Move the dragged shape, then set
        // shapeBeingDragged to null to indicate that dragging is over.
        shapeBeingDragged = null;
        if(!this.joinn){
            machineButton.setDisable(false);
            queueButton.setDisable(false);
            beginButton.setDisable(false);
            rootButton.setDisable(false);
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
