package sample;

import javafx.application.Application;
import javafx.scene.Scene;
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

import java.util.ArrayList;

public class Main extends Application {
    private Shape[] shapes = new Shape[500];  // Contains shapes the user has drawn.
    private int shapeCount = 0; // Number of shapes that the user has drawn.
    private Canvas canvas; // The drawing area where the user draws.
    private Color currentColor = Color.WHITE;  // Color to be used for new shapes.
    private boolean joinn = false;
    private int joinfig = 0;
    public  ArrayList<Shape> joinXY = new ArrayList<Shape>();

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
        root.setBottom(makeToolPanel(canvas));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Producer/Consumer");
        stage.setResizable(false);
        stage.show();
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
    public void begin(){
        System.out.println("hello22");
    }
    public void join(){
        this.joinn=true;
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
            //this.joinXY.clear();

        }
    }

    private HBox makeToolPanel(Canvas canvas) {
        // Make a pane containing the buttons that are used to add shapes
        // and the pop-up menu for selecting the current color.
        Button machineButton = new Button("Add a Machine");
        machineButton.setOnAction( (e) -> addShape( new CircleShape() ) );
        Button queueButton = new Button("Add a Queue");
        queueButton.setOnAction( (e) -> addShape( new RectShape() ) );
        Button joinButton = new Button("Join");
        joinButton.setOnAction( (e) -> join());
        Button beginButton = new Button("Start Simulation");
        beginButton.setOnAction( (e) -> begin()  );
        Button endButton = new Button("Stop Simulation");
        endButton.setOnAction( (e) ->   end()  );
        ComboBox<String> combobox = new ComboBox<>();
        endButton.setStyle("-fx-background-color: Red");
        beginButton.setStyle("-fx-background-color: Green");
        combobox.setEditable(false);
        /*if(this.joinn){
            machineButton.setDisable(true);
            queueButton.setDisable(true);
            beginButton.setDisable(true);
            endButton.setDisable(true);
        }else{
            machineButton.setDisable(false);
            queueButton.setDisable(false);
            beginButton.setDisable(false);
            endButton.setDisable(false);
        }*/
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.CYAN,
                Color.MAGENTA, Color.YELLOW, Color.BLACK, Color.WHITE };
        String[] colorNames = { "Red", "Green", "Blue", "Cyan",
                "Magenta", "Yellow", "Black", "White" };
        combobox.getItems().addAll(colorNames);
        combobox.setValue("White");
        combobox.setOnAction(
                e -> currentColor = colors[combobox.getSelectionModel().getSelectedIndex()] );
        HBox tools = new HBox(10);
        tools.getChildren().add(machineButton);
        tools.getChildren().add(queueButton);
        tools.getChildren().add(joinButton);
        tools.getChildren().add(beginButton);
        tools.getChildren().add(endButton);
        tools.getChildren().add(combobox);
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
                if(this.joinn){
                    this.joinfig++;
                    this.joinXY.add(s);
                    shapeBeingjoined=s;
                }else {
                    shapeBeingDragged = s;
                }
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

    }
    public static void main(String[] args) {
        launch(args);
    }
}
