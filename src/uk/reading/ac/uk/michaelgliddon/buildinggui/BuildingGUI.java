package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

/**
 * @author Michael-Lee Gliddon GUI for the building
 *
 */
public class BuildingGUI extends Application implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	private Stage stagePrimary;

	private Building theBuilding = new Building(buildingString()); // set up the building
	private VBox rtPane; // vertical box for putting info
	private GraphicsContext gc; // graphics context for drawing it
	private AnimationTimer timer; // timer used for animation
	private int whichBuild;

	/**
	 * 
	 * @return
	 */
	public Building getBuilding() {
		return this.theBuilding; // Function to be able to call thebuilding when dealing with file handling
	}

	/**
	 * Function to show a message,
	 * 
	 * @param TStr title of message block
	 * @param CStr content of message
	 */
	private void showMessage(String TStr, String CStr) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(TStr);
		alert.setHeaderText(null);
		alert.setContentText(CStr);

		alert.showAndWait();
	}

	private void showWelcome() {
		showMessage("Welcome", "Welcome to Michael Gliddon's Intelligent Building!");
	}

	private void showAbout() {
		showMessage("About", "Currently running version 1.0 of Michael Gliddon's Intelligent Building");
	}

	/**
	 * set up the menu of commands for the GUI
	 * 
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		// initially set up the file chooser to look for cfg files in current directory
		MenuBar menuBar = new MenuBar(); // create main menu

		Menu mFile = new Menu("File"); // add File main menu
		MenuItem mExit = new MenuItem("Exit"); // whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) { // action on exit
				timer.stop(); // stop timer
				System.out.println("\n\nGoodbye!");
				System.exit(0); // exit program

			}
		});

		/**
		 * 
		 */
		Menu mBuilding = new Menu("Building Selection");
		MenuItem mBuilding1 = new MenuItem("Building 1"); // whose sub menu has Exit
		MenuItem mBuilding2 = new MenuItem("Building 2"); 
		mBuilding.getItems().add(mBuilding1);
		mBuilding.getItems().add(mBuilding2);
		
		// whose sub menu has Exit
		mBuilding1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				theBuilding = new Building(buildingString());
				drawBuilding(); // then redraw arena
			}
		});
		
		mBuilding2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				theBuilding = new Building(buildingString2());
				drawBuilding(); // then redraw arena
			}
		});

		mFile.getItems().addAll(mExit); // add load, save and exit to File menu

		Menu mHelp = new Menu("Help"); // create Help menu
		MenuItem mWelcome = new MenuItem("About"); // add Welcome sub menu item
		mWelcome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				showAbout(); // whose action is to give welcome message
			}
		});
		mHelp.getItems().addAll(mWelcome); // add Welcome and About to Run main item

		menuBar.getMenus().addAll(mFile, mHelp, mBuilding); // set main menu with File, Config, Run, Help

		return menuBar; // return the menu
	}

	/**
	 * set up the horizontal box for the bottom with buttons
	 * 
	 * @return buttons
	 */
	private HBox setButtons() {

		Button btnStart = new Button("Start");
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timer.start(); // whose action is to start the timer

			}
		});

		Button btnStop = new Button("Pause");
		btnStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timer.stop(); // and its action to stop the timer
			}
		});

		Button btnAdd = new Button("Add Person");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				theBuilding.addPerson();
				drawBuilding();
			}
		});

		Button btnRemove = new Button("Remove Person");
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				theBuilding.RemovePerson();
				drawBuilding();
			}
		});

		Button btnOn = new Button("Turn Light On");
		btnOn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				theBuilding.On();
				// drawBuilding();
			}
		});

		Button btnOff = new Button("Turn Light Off");
		btnOff.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				theBuilding.Off();
				// drawBuilding();
			}
		});

		Button btnReset1 = new Button("Reset Building 1");
		btnReset1.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			theBuilding = new Building(buildingString());
			timer.stop();
			drawBuilding(); // then redraw arena
		}
	});
		
		Button btnReset2 = new Button("Reset Building 2");
		btnReset2.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			theBuilding = new Building(buildingString2());
			timer.stop();
			drawBuilding(); // then redraw arena
		}
	});
		// now add these buttons + labels to a HBox
		HBox hbox = new HBox(new Label(" Run: "), btnStart, btnStop, new Label(" Person: "), btnAdd, btnRemove,
				new Label(" Light: "), btnOn, btnOff, 
				new Label(" Restart: "), btnReset1, btnReset2);
		return hbox;
	}

	/**
	 * function to convert char c to actual colour used
	 * 
	 * @param c
	 * @return Color
	 */
	Color colFromChar(char c) {
		Color ans = Color.BLACK;
		switch (c) {
		case 'y':
			ans = Color.YELLOW;
			break;
		case 'r':
			ans = Color.RED;
			break;
		case 'g':
			ans = Color.GREEN;
			break;
		case 'b':
			ans = Color.BLUE;
			break;
		case 'k':
			ans = Color.BLACK;
			break;
		case 'o':
			ans = Color.ORANGE;
			break;
		case 'p':
			ans = Color.PINK;
			break;
		case 'c':
			ans = Color.CYAN;
			break;
		}
		return ans;
	}

	/**
	 * show a Line from first xy point to second xy point, with given width and
	 * colour
	 * 
	 * @param xy1   is xy1[0] is x, xy1[1] is y
	 * @param xy2
	 * @param width
	 * @param col
	 */
	void showLine(int[] xy1, int[] xy2, int width, char col) {
		gc.setStroke(colFromChar(col)); // set the stroke colour
		gc.setLineWidth(width);
		gc.strokeLine(xy1[0], xy1[1], xy2[0], xy2[1]); // draw line
	}

	void showWall(int x1, int y1, int x2, int y2) {
		gc.setStroke(colFromChar('k')); // set the stroke colour
		gc.setLineWidth(3);
		gc.strokeLine(x1, y1, x2, y2); // draw line
	}

	/**
	 * Show the entity of given size in the interface at position x,y Do so by
	 * drawing a circle in the colour specified by character c
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param col
	 */
	void showItem(int x, int y, int size, char col) {
		gc.setFill(colFromChar(col)); // set the fill colour
		gc.fillArc(x - size, y - size, size * 2, size * 2, 0, 360, ArcType.ROUND);
	} // fill 360 degree arc

	/**
	 * draw the arena and its contents
	 */
	void drawBuilding() {
		gc.setFill(Color.BEIGE);
		gc.fillRect(0, 0, theBuilding.getXSize(), theBuilding.getYSize()); // clear the canvas
		theBuilding.showBuilding(this); // draw all items

		String s = theBuilding.toString();
		rtPane.getChildren().clear(); // clear rtpane
		Label l = new Label(s); // turn string to label
		rtPane.getChildren().add(l); // add label

	}

	/**
	 * return as String definition of bOpt'th building
	 * 
	 * @param bOpt
	 */
	public String buildingString() {
		whichBuild = 1 - whichBuild;
		if (whichBuild == 1) {
	}
			return "420 400;10 10 140 60 60 60 10;140 10 240 60 180 60 10;240 10 400 60 320 60 20 ;10 90 120 180 40 90 15;120 90 280 180 160 90 10;280 90 400 180 340 90 10";		
	}
	
	public String buildingString2() {
		whichBuild = 1 - whichBuild;
				if(whichBuild == 1) {
	}
	return "400 400;10 10 90 140 70 140 20;90 10 320 70 220 70 10;10 180 100 380 60 180 15;100 180 320 380 200 180 20;320 10 400 380 320 110 25";
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		stagePrimary = primaryStage;
		stagePrimary.setTitle("Michael Gliddon's Intellegent Building");
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 20, 10, 20));

		bp.setTop(setMenu()); // put menu at the top

		Group root = new Group(); // create group with canvas
		Canvas canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);
		bp.setCenter(root); // load canvas to left area

		gc = canvas.getGraphicsContext2D(); // context for drawing

		timer = new AnimationTimer() { // set up timer
			public void handle(long currentNanoTime) {
				theBuilding.update();
				drawBuilding();
			}
		};

		rtPane = new VBox(); // set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);
		rtPane.setPadding(new Insets(5, 75, 75, 5));
		bp.setRight(rtPane);

		bp.setBottom(setButtons()); // set bottom pane with buttons

		Scene scene = new Scene(bp, 800, 600); // set overall scene
		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());

		primaryStage.setScene(scene);
		primaryStage.show();
		whichBuild = 0;
		theBuilding = new Building(buildingString());
		showWelcome(); // set welcome message
		drawBuilding();
	}

	/**
	 * @param args
	 */
	public static void writeObjectToDisk(Object obj, String name) throws IOException {
		// Create file output stream
		FileOutputStream fileOutStr = new FileOutputStream(name);
		// Create object output stream and write object
		ObjectOutputStream objOutStr = new ObjectOutputStream(fileOutStr);
		objOutStr.writeObject(obj);
		// Close all streams
		objOutStr.close();
		fileOutStr.close();
		System.out.printf("Serialized data is saved in a file  - " + name); // Printing message and file name which is
																			// saved
	}

	/**
	 * Method to load objects which have been written to disk
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object objectLoader(String filename) throws IOException, ClassNotFoundException {
		// Creat file input stream
		FileInputStream fileInStr = new FileInputStream(filename);
		// Create object input steam
		ObjectInputStream objInStr = new ObjectInputStream(fileInStr);
		Object obj = (Object) objInStr.readObject();
		// Closing all streams
		objInStr.close();
		fileInStr.close();
		System.out.printf("Deserialized data  - " + filename); // Printing message plus file name that was saved
		return obj;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) {
		// initialize BuildingGUI object and get the Building object
		BuildingGUI building2 = new BuildingGUI();
		Building myBuilding = building2.getBuilding();

		// we serialize the Building and BuildingGUI, by casting it to (Object) and
		// feeding it to the method
		try {
			BuildingGUI.writeObjectToDisk((Object) myBuilding, "Whole building.ser");
			System.out.println();
			BuildingGUI.writeObjectToDisk((Object) building2, "buildingGUI.ser");
			System.out.println();
		} catch (IOException ioe) {
			System.out.println("Error IOExecption"); // Catching error exception
			ioe.printStackTrace();
		}

		Building myBuilding2 = null;
		BuildingGUI buildingGUI = null;
		// now for deserializing
		try {
			// We cast to appropriate type, because method returns 'Object'
			myBuilding2 = (Building) objectLoader("Whole building.ser");
			System.out.println();
			buildingGUI = (BuildingGUI) objectLoader("buildingGUI.ser");
		} catch (IOException ioe) {
			System.out.println("Error IOExecption"); // Catching IOException error
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error Class not found caption"); // Catching class not found exception
			cnfe.printStackTrace();
		}
		Application.launch(args);
	}
}
