/* File: BookShoppingCart.java
 * Author: Patrick Jadwin
 * Date: 04/22/18
 * An online book store.  This online book store is connected to an external
 * Oracle database.  This book store allows users to search through a selection
 * of available books, and add them to a cart. The user can then checkout and 
 * send their shipping information to the database. Users are also able to 
 * track their orders, as well as administrators can update order status.
 */
package BookShoppingCart;

import java.util.*;
import java.text.*;
import java.io.*;
import javafx.event.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookShoppingCart extends Application {
    // Labels for aboutMenu
    private static Label aboutMenuItems1;
    private static Label aboutMenuItems2;
    // checkout apt num
    private static TextField aptNumFloor; // can be null
    // BorderPane
    private static BorderPane bPane;
    // Blank formatting label
    private static final Label blank = new Label("");
    // Store author data from database
    private static ArrayList<String> bookAuthor;
    // Observable object list for tableview
    private static ObservableList<BookCart> bookCart;
    // Store book names from database
    private static ArrayList<String> bookNames;
    // Observable list for ListView
    private static ObservableList<String> bookNamesList;
    // Store prices from database
    private static ArrayList<Double> bookPrices;
    // Cart window gridpane
    private static GridPane bottom;
    // Add to cart button
    private static Button cartButton;
    // Cart window borderpane
    private static BorderPane cartPane;
    // Cart window scene
    private static Scene cartScene;
    // Cart window stage
    private static Stage cartWindow;
    // Check out button
    private static Button checkOut;
    // Gridpane for checkout
    private static GridPane checkOutPane;
    // Scene for checkout
    private static Scene checkOutScene;
    // Stage for checkout
    private static Stage checkOutStage;
    // Get user city
    private static TextField city;
    // HBox for checkout
    private static HBox cityStateZip;
    // Clear cart
    private static Button clearAll;
    // Hbox for checkout
    private static HBox completeCheckOut;
    // database connection
    private static Connection connection;
    // Exit cart
    private static Button continueShopping;
    // currency format
    private static final NumberFormat curForm = NumberFormat
        .getCurrencyInstance();
    // Delete from cart button
    private static Button deleteButton;
    // Displays retreieved nam
    private static Label dispName;
    // Displays retrieved order number
    private static Label dispOrderNum;
    // Displays shipping status
    private static Label dispShipStat;
    // Get user fname
    private static TextField fName;
    // Final cost
    private static double finalCost;
    // Finish checkout
    private static Button finishCheckOut;
    // prints the selected title and price
    private static Label getTitlePrice;
    // GridPane
    private static GridPane grid;
    // HBox
    private static HBox hbox;
    // View images
    private static ImageView image;
    // Array to store Images
    private static Image[] imageArray;
    // More info button
    private static Button infoButton;
    // Get user last name
    private static TextField lName;
    // Store image links from database
    private static ArrayList<String> links;
    // ListView variable
    private static ListView<String> listView;
    // Name HBox for CheckOutFunction
    private static HBox nameBox;
    // Order number
    private static int newOrderNum;
    // Search order number
    private static int orderNumSearch;
    // Get order number for searching
    private static TextField orderSearch;
    // Order status VBox
    private static VBox orderStatBox;
    // Order status borderpane
    private static BorderPane orderStatPane;
    // Order status scene
    private static Scene orderStatScene;
    // Orders status stage
    private static Stage orderStatStage;
    // Orders status apt num
    private static String orderAptNumFloor;
    // Orders city
    private static String orderCity;
    // Orders FName
    private static String orderFName;
    // Order gridpane
    private static GridPane orderGrid;
    // Orders LName
    private static String orderLName;
    // Get order numbers
    private static ArrayList<Integer> orderNum;
    // Orders state
    private static String orderState;
    // Orders address
    private static String orderStrAddr;
    // Orders zip code
    private static int orderZip;
    // Retrieve order VBox
    private static VBox retrieveBox;
    // Retrieve order GridPane
    private static GridPane retrieveGrid;
    // Retrieve borderpane
    private static BorderPane retrievePane;
    // Retrieve scene
    private static Scene retrieveScene;
    // Retrieve stage
    private static Stage retrieveStage;
    // Result sets for queries
    private static ResultSet rst;
    private static ResultSet rst2;
    private static ResultSet rst3;
    // Search button
    private static Button search;
    // Name searched for
    private static String searchName;
    // Order number searched for
    private static int searchOrderNum;
    // Shipping status searched for
    private static String searchShipStat;
    // Tell user to select a book
    private static Label selectLabel;
    // Store selected book names for shopping cart
    private static ArrayList<String> selectedBookNames;
    // Store selected book prices for shopping cart
    private static ArrayList<Double> selectedBookPrices;
    // Store ISBN
    private static ArrayList<String> selectedISBN;
    // Users selection number
    private static int selectionNum;
    // Users shipped status
    private static String shipped;
    // Shipping cost
    private static final double shippingCost = 15.00;
    // Store ISBN
    private static ArrayList<String> sqlISBN;
    // List of states
    private static ComboBox stateBox;
    // Statements
    private static Statement stmt;
    private static Statement stmt2;
    // Get user str addr
    private static TextField strAddr;
    // TableView references BookCart object
    private static TableView<BookCart> table;
    // Tax 9%
    private static final double tax = 0.09;
    // Prints title: price:
    private static Label titlePrice;
    // Total cost of order
    private static double totalCost;
    // Totals
    private static Label totals;
    private static Label totals2;
    // List of states
    private static final ObservableList<String> usStates =
        FXCollections.observableArrayList(
            "AL",//1
            "AK",//2
            "AZ",//3
            "AR",//4
            "CA",//5
            "CO",//6
            "CT",//7
            "DE",//8
            "FL",//9
            "GA",//10
            "HI",//11
            "ID",//12
            "IL",//13
            "IN",//14
            "IA",//15
            "KS",//16
            "KY",//17
            "LA",//18
            "ME",//19
            "MD",//20
            "MA",//21
            "MI",//22
            "MN",//23
            "MS",//24
            "MO",//25
            "MT",//26
            "NE",//27
            "NV",//28
            "NH",//29
            "NJ",//30
            "NM",//31
            "NY",//32
            "NC",//33
            "ND",//34
            "OH",//35
            "OK",//36
            "OR",//37
            "PA",//38
            "RI",//39
            "SC",//40
            "SD",//41
            "TN",//42
            "TX",//43
            "UT",//44
            "VT",//45
            "VA",//46
            "WA",//47
            "WV",//48
            "WI",//49
            "WY" //50
        );
    // Get zip
    private static TextField zip;
    // About menu
    private void AboutMenuFunction(MenuItem aboutMenuItem) {
        // Set on menu item clicked
        aboutMenuItem.setOnAction((ActionEvent event) -> {
            // About menu pic
            String url = "https://content.mycutegraphics.com/graphics/book/"
                + "stack-of-books-clipart-book-clip-art.png";
            // Set pic
            ImageView img = new ImageView(new Image(url));
            // Set size
            img.setFitHeight(100);
            img.setFitWidth(100);
            // Create description
            Label label = new Label("Book Store\nv0.0.1\n"
                + "_________________________________________________\n\n"
                + "An application that allows you to purchase books\n"
                + "from the book store", img);
            // Stackpane
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(label);
            Scene secondScene = new Scene(secondaryLayout,500,125);
            // New Window
            Stage newWindow = new Stage();
            newWindow.setTitle("About");
            newWindow.setScene(secondScene);
            // Display
            newWindow.show();
        });
    }
    // Adds items to cart button
    private void CartButtonFuction() {
        // Set array lists
        selectedBookNames = new ArrayList<>();
        selectedBookPrices = new ArrayList<>();
        selectedISBN = new ArrayList<>();
        // Start listening for mouse click
        cartButton.setOnMouseClicked((MouseEvent arg0) -> {
            if (selectionNum == 0) {
            }
            // Adds items to cart
            else {
                selectedBookNames.add(bookNames.get(listView
                    .getSelectionModel().getSelectedIndex()));
                selectedBookPrices.add(bookPrices.get(listView
                    .getSelectionModel().getSelectedIndex()));
                selectedISBN.add(sqlISBN.get(listView
                    .getSelectionModel().getSelectedIndex()));
            }
        });
    }
    // Cart menu
    private void CartMenuFunction(MenuItem cartItem) {
        // Enter cart
        cartItem.setOnAction((ActionEvent event) -> {
            // Check out button
            checkOut = new Button("Check Out");
            // Delete 1 item button
            deleteButton = new Button("Delete Item");
            // open cart button
            continueShopping = new Button("Keep Shopping");
            // Clear all button
            clearAll = new Button("Clear All");
            // Set cart array list
            bookCart = FXCollections.observableArrayList();
            // Add selected items to cart
            for (int i = 0; i < selectedBookNames.size(); i++) {
                bookCart.add(new BookCart(selectedBookNames.get(i), 
                selectedBookPrices.get(i)));
            }
            // Get total cost
            GetTotal();
            // Print totals
            totals = new Label("\nTotal: " + curForm.format(totalCost)
                + "\n" + "Shipping: " + curForm.format(shippingCost) + "\n" +
                "Tax: " + curForm.format(totalCost * tax) + "\n" +
                "\nGrand Total: " + curForm.format(finalCost));
            // Set new tableview item
            table = new TableView();
            // Book name column
            TableColumn bookNameCol = new TableColumn("Book Title        " +
                "(Select row and press delete to remove item)");
            bookNameCol.setMinWidth(545);
            bookNameCol.setCellValueFactory(
                new PropertyValueFactory<>("bookTitle"));
            // Price column
            TableColumn lastNameCol = new TableColumn("Price");
            lastNameCol.setMinWidth(110);
            lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("bookPrice"));
            // Set items into table
            table.setItems(bookCart);
            // Add columns to tableview
            table.getColumns().addAll(bookNameCol,lastNameCol);
            // Borderpane for cart
            cartPane = new BorderPane();
            // VBox for cart
            VBox cartBox = new VBox();
            // GridPane for cart
            bottom = new GridPane();
            // Rows and columns
            // 0
            bottom.getColumnConstraints().add(new ColumnConstraints(175));
            // 0
            bottom.getRowConstraints().add(new RowConstraints(100));
            // 1
            bottom.getColumnConstraints().add(new ColumnConstraints(150));
            // 1
            bottom.getRowConstraints().add(new RowConstraints(150));
            // 2
            bottom.getColumnConstraints().add(new ColumnConstraints(120));
            // 2
            bottom.getRowConstraints().add(new RowConstraints(30));
            // 3
            bottom.getColumnConstraints().add(new ColumnConstraints(120));
            // 3
            bottom.getRowConstraints().add(new RowConstraints(20));
            // Spacing
            bottom.setHgap(10);
            bottom.setVgap(5);
            bottom.setPadding(new Insets(0,10,0,10));
            // Set totals in gridpane
            bottom.add(totals, 0,0);
            // VBox
            VBox deleteClearBox = new VBox();
            // Spacing
            deleteClearBox.setSpacing(10);
            // Add items to VBox
            deleteClearBox.getChildren().addAll(blank,deleteButton,clearAll);
            // Delete single item function
            DeleteFunction();
            // Clear all items function
            ClearAll();
            // Add to gridpane
            bottom.add(deleteClearBox,1,0);
            bottom.add(continueShopping,2,0);
            // Continue shopping button function
            KeepShoppingFunction();
            // Add checkout button to gridpane
            bottom.add(checkOut, 3,0);
            // Checkout button functionality
            CheckOutFunction();
            // Add table and grid to VBox
            cartBox.getChildren().addAll(table,bottom);
            // Set location
            cartPane.setTop(cartBox);
            // Create scene
            cartScene = new Scene(cartPane,650,535,Color.WHITE);
            // Create stage
            cartWindow = new Stage();
            // Title
            cartWindow.setTitle("Cart");
            cartWindow.setScene(cartScene);
            // Display
            cartWindow.show();
        });
    }
    private void CheckOutFunction() {
        // Set combobox
        stateBox = new ComboBox(usStates);
        // Action
        checkOut.setOnAction((ActionEvent event) -> {
            completeCheckOut = new HBox();
            nameBox = new HBox();
            cityStateZip = new HBox();
            totals2 = new Label();
            // Text fields
            fName = new TextField();
            fName.setPromptText("First Name");
            lName = new TextField();
            lName.setPromptText("Last Name");
            strAddr = new TextField();
            strAddr.setPromptText("Street Address");
            aptNumFloor = new TextField();
            aptNumFloor.setPromptText("Apartment #/Floor (Can leave empty)");
            city = new TextField();
            city.setPromptText("City");
            zip = new TextField();
            zip.setPromptText("Zip");
            finishCheckOut = new Button("Complete Check-Out");
            // Recalculate totals
            GetTotal();
            // Print totals
            totals2.setText("\nTotal: " + curForm.format(totalCost)
                + "\n" + "Shipping: " + curForm.format(shippingCost) + "\n" +
                "Tax: " + curForm.format(totalCost * tax) + "\n" +
                "\nGrand Total: " + curForm.format(finalCost));
            // Gridpane
            checkOutPane = new GridPane();
            // 0
            checkOutPane.getColumnConstraints().add(new ColumnConstraints(20));
            // 0
            checkOutPane.getRowConstraints().add(new RowConstraints(20));
            // 1
            checkOutPane.getColumnConstraints().add(
                new ColumnConstraints(450));
            // 1
            checkOutPane.getRowConstraints().add(new RowConstraints(40));
            // 2
            checkOutPane.getRowConstraints().add(new RowConstraints(40));
            // 3
            checkOutPane.getRowConstraints().add(new RowConstraints(40));
            // 4
            checkOutPane.getRowConstraints().add(new RowConstraints(40));
            // 5
            checkOutPane.getRowConstraints().add(new RowConstraints(20));
            // 6
            checkOutPane.getRowConstraints().add(new RowConstraints(300));
            // Format text boxes
            nameBox.getChildren().addAll(fName,lName);
            nameBox.setSpacing(10);
            cityStateZip.getChildren().addAll(city,stateBox,zip);
            stateBox.setValue("AL"); // Initial statebox value
            completeCheckOut.getChildren().addAll(finishCheckOut, totals2);
            cityStateZip.setPadding(new Insets(15, 0, 0, 0));
            cityStateZip.setSpacing(10);
            completeCheckOut.setPadding(new Insets(15,0,0,0));
            completeCheckOut.setSpacing(30);
            checkOutPane.add(nameBox, 1, 1);
            checkOutPane.add(strAddr, 1, 2);
            checkOutPane.add(aptNumFloor, 1, 3);
            checkOutPane.add(cityStateZip, 1, 4);
            checkOutPane.add(completeCheckOut, 1, 6);
            // Send checkout to database
            try {
                FinishCheckOutFunction();
            } catch (SQLException ex) {
                Logger.getLogger(BookShoppingCart.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            // Display
            checkOutScene = new Scene(checkOutPane,500,350,Color.WHITE);
            checkOutStage = new Stage();
            checkOutStage.setTitle("Check Out");
            totals2.requestFocus();
            checkOutStage.setScene(checkOutScene);
            checkOutStage.show();
        });
    }
    // Clear all function
    private void ClearAll() {
        clearAll.setOnAction((ActionEvent event) -> {
            // removes everything from bookCart, selectedBookNames,
            // selectedBookPrices, selected ISBN
            bookCart.removeAll(bookCart);
            selectedBookNames.removeAll(selectedBookNames);
            selectedBookPrices.removeAll(selectedBookPrices);
            selectedISBN.removeAll(selectedISBN);
            GetTotal();
            totals.setText("\nTotal: " + curForm.format(totalCost) + "\n" +
                "Shipping: " + curForm.format(shippingCost) + "\n" +
                "Tax: " + curForm.format(totalCost * tax) + "\n" +
                "\nGrand Total: " + curForm.format(finalCost));
        });
    }
    private void DBConnect() throws ClassNotFoundException, SQLException, 
            IOException {
        // Set lists for saving data
        bookNames = new ArrayList<>();
        bookPrices = new ArrayList<>();
        links = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        sqlISBN = new ArrayList<>();
        orderNum = new ArrayList<>();
        // Start connection
        try {
            // Register the Oracle JDBC drivers
            DriverManager.registerDriver(
                new oracle.jdbc.OracleDriver()
            );
            System.out.println("Driver loaded");
            // Establish a connection to the remote database
            connection = DriverManager.getConnection(
            // IP address of the Oracle1 server, port 1521, SID name
                "jdbc:oracle:thin:@198.146.192.57:1521:orcl",
                "prjadwin",    // A generic Oracle account 
                "vM7J3vgr"
            );
            System.out.println("Database connected");
            // Create a statement
            stmt = connection.createStatement();
            // Query
            rst = stmt.executeQuery
                ("SELECT BOOK,BOOK_PRICE,IMAGE_LINK,BOOK_AUTHOR,ISBN\n"
                + "FROM JAVA_BOOK_STORE");
            // Store query
            while (rst.next()) {
                bookNames.add(rst.getString(1));
                bookPrices.add(rst.getDouble(2));
                links.add(rst.getString(3));
                bookAuthor.add(rst.getString(4));
                sqlISBN.add(rst.getString(5));
            }
            // Another query to generate new order numbers
            bookNamesList = FXCollections.observableArrayList(bookNames);
            stmt2 = connection.createStatement();
            rst2 = stmt2.executeQuery
                ("SELECT UNIQUE ORDER_NUM " +
                "FROM JAVA_BOOK_ORDERS " +
                "ORDER BY ORDER_NUM ASC");
            while (rst2.next()) {
                orderNum.add(rst2.getInt(1));
            }
        } catch (SQLException ex) {}
    }
    // Delete individual items
    private void DeleteFunction() {
        totals2 = new Label();
        deleteButton.setOnAction((ActionEvent event) -> {
            // Remove from selected index
            selectedBookNames.remove(table.getSelectionModel()
                .getSelectedIndex());
            selectedBookPrices.remove(table.getSelectionModel()
                .getSelectedIndex());
            selectedISBN.remove(table.getSelectionModel()
                .getSelectedIndex());
            // Reset list
            bookCart.removeAll(bookCart);
            // Refill list with updated cart
            for (int i = 0; i < selectedBookNames.size(); i++) {
                bookCart.add(new BookCart(selectedBookNames.get(i), 
                selectedBookPrices.get(i)));
            }
            // Recalculate totals
            GetTotal();
            totals.setText("\nTotal: " + curForm.format(totalCost) + "\n" +
                "Shipping: " + curForm.format(shippingCost) + "\n" +
                "Tax: " + curForm.format(totalCost * tax) + "\n" +
                "\nGrand Total: " + curForm.format(finalCost));
            totals2.setText("\nTotal: " + curForm.format(totalCost) + "\n" +
                "Shipping: " + curForm.format(shippingCost) + "\n" +
                "Tax: " + curForm.format(totalCost * tax) + "\n" +
                "\nGrand Total: " + curForm.format(finalCost));
        });
    }
    // Exit menu
    private void ExitMenuFunction(MenuItem exitMenuItem) {
        // Exit if clicked
        exitMenuItem.setOnAction(ActionEvent -> Platform.exit());
    }
    // Fills image list
    private void FillImageList() {
        // Make array expandable
        imageArray = new Image[links.size()];
        // Fill
        for(int i = 0; i < links.size(); i++) {
            imageArray[i] = new Image(links.get(i));
        }
    }
    // Final checkout
    private void FinishCheckOutFunction() throws SQLException {
        // labels for unnacceptable inputs
        Label error = new Label("Error! 1 or more field(s) are Null!");
        Label error2 = new Label("Error! Zip code entry invalid");
        // BorderPane for errors
        BorderPane errorPane = new BorderPane();
        // Scene/Stage for errors
        Scene errorScene = new Scene(errorPane,300,75);
        Stage errorWindow = new Stage();
        // Listen for complete check out
        finishCheckOut.setOnAction((ActionEvent event) -> {
            // If any fields are empty error
            if (fName.getText().trim().isEmpty() ||
                    lName.getText().trim().isEmpty() ||
                    strAddr.getText().trim().isEmpty() ||
                    city.getText().trim().isEmpty() ||
                    zip.getText().trim().isEmpty()) {
                errorPane.setCenter(error);
                errorWindow.setScene(errorScene);
                errorWindow.show();
            }
            // If zip code is not valid error
            else if (isValidZip(zip.getText()) == false) {
                errorPane.setCenter(error2);
                errorWindow.setScene(errorScene);
                errorWindow.show();
            }
            // Send information to the database
            else {
                // Getting information from the textfields
                // Converts text field to integer
                orderZip = Integer.parseInt(zip.getText());
                orderFName = fName.getText(); 
                orderLName = lName.getText();
                orderStrAddr = strAddr.getText();
                orderAptNumFloor = aptNumFloor.getText();
                orderCity = city.getText();
                orderState = String.valueOf(stateBox.getValue());
                newOrderNum = orderNum.size() + 1;
                shipped = "NO";
                // Uploads book orders to the database
                for (int x = 0; x < selectedISBN.size(); x++) {
                    try {
                        String stringQry = "INSERT INTO JAVA_BOOK_ORDERS " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement checkOutState = 
                            connection.prepareStatement(stringQry);
                        checkOutState.setString(1, orderFName.toUpperCase());
                        checkOutState.setString(2, orderLName.toUpperCase());
                        checkOutState.setString(3, orderStrAddr.toUpperCase());
                        checkOutState.setString(4, orderAptNumFloor
                            .toUpperCase());
                        checkOutState.setString(5, orderCity.toUpperCase());
                        checkOutState.setString(6, orderState.toUpperCase());
                        checkOutState.setInt(7, orderZip);
                        checkOutState.setInt(8, newOrderNum);
                        checkOutState.setString(9, selectedISBN.get(x));
                        checkOutState.setString(10, shipped);
                        checkOutState.setDouble(11, finalCost);
                        checkOutState.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(BookShoppingCart.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
                try {
                    // Prints customers order number
                    GiveOrderNum();
                } catch (SQLException ex) {
                    Logger.getLogger(BookShoppingCart.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                cartWindow.close();
                checkOutStage.close();
            }
        });
    }
    // More info button
    private void FullInfoMenu(Stage stage) {
        // Button to enter more info menu
        infoButton = new Button("More Info");
        aboutMenuItems2.setWrapText(true);
        infoButton.setOnAction((ActionEvent event) -> {
            GridPane pane = new GridPane();
            pane.getColumnConstraints().add(new ColumnConstraints(25));
            pane.getRowConstraints().add(new RowConstraints(5));
            pane.getColumnConstraints().add(new ColumnConstraints(50));
            pane.getRowConstraints().add(new RowConstraints(70));
            pane.getColumnConstraints().add(new ColumnConstraints(200));
            pane.getRowConstraints().add(new RowConstraints(50));
            pane.setHgap(20);
            pane.setVgap(20);
            pane.add(aboutMenuItems1, 1,1);
            pane.add(aboutMenuItems2, 2,1);
            Scene thirdScene = new Scene(pane, 500, 125);
            Stage anotherWindow = new Stage();
            anotherWindow.setTitle("More Info");
            anotherWindow.setScene(thirdScene);
            anotherWindow.setX(stage.getX() + 200);
            anotherWindow.setY(stage.getY() + 100);
            anotherWindow.show();
        });
    }
    // Customer get order
    private void GetOrder() {
        retrieveBox = new VBox();
        retrieveGrid = new GridPane();
        retrievePane = new BorderPane();
        // Start search query
        search.setOnAction((ActionEvent event) -> {
            orderNumSearch = Integer.parseInt(orderSearch.getText());
            try {
                // Prepared statement, allows use of variables
                String orderQry = "SELECT UNIQUE ORDER_NUM, F_NAME || ' ' || "
                    + "L_NAME, SHIPPED "
                    + "FROM JAVA_BOOK_ORDERS "
                    + "WHERE ORDER_NUM = ?";
                PreparedStatement searchStatement
                    = connection.prepareStatement(orderQry);
                searchStatement.setInt(1, orderNumSearch);
                rst3 = searchStatement.executeQuery();
                // Get values for use in program
                while (rst3.next()) {
                    searchOrderNum = rst3.getInt(1);
                    searchName = rst3.getString(2);
                    searchShipStat = rst3.getString(3);
                }
            } catch (SQLException SQLException) {}
            // Displays order number
            dispOrderNum = new Label("ORDER # " + searchOrderNum);
            dispOrderNum.setFont(new Font("Arial", 13));
            // Displays name on order
            dispName = new Label("Name: " + searchName);
            dispName.setFont(new Font("Arial", 13));
            // Displays shipping status
            dispShipStat = new Label("Shipped Status: " + searchShipStat);
            dispShipStat.setFont(new Font("Arial", 13));
            // Gridpane columns and rows
            // 0
            retrieveGrid.getColumnConstraints().add(new ColumnConstraints(20));
            // 0
            retrieveGrid.getRowConstraints().add(new RowConstraints(20));
            // 1
            retrieveGrid.getColumnConstraints().add(new 
                ColumnConstraints(250));
            // 1
            retrieveGrid.getRowConstraints().add(new RowConstraints(40));
            // 2
            retrieveGrid.getRowConstraints().add(new RowConstraints(40));
            // 3
            retrieveGrid.getRowConstraints().add(new RowConstraints(40));
            // Add items to gridpane
            retrieveGrid.add(dispOrderNum,1,1);
            retrieveGrid.add(dispName,1,2);
            retrieveGrid.add(dispShipStat,1,3);
            retrieveBox.getChildren().addAll(retrieveGrid);
            retrievePane.setTop(retrieveBox);
            retrieveScene = new Scene(retrievePane,300,200,Color.WHITE);
            retrieveStage = new Stage();
            retrieveStage.setTitle("Order Status");
            retrieveStage.setScene(retrieveScene);
            retrieveStage.show();
        });
    }
    // Admin get order (Allows user to update shipping status)
    private void GetOrderAndUpdate() {
        // Button for updating shipping status
        Button update = new Button("Update Shipped");
        // Update button functionality
        UpdateShippedButton(update);
        retrieveBox = new VBox();
        retrieveGrid = new GridPane();
        retrievePane = new BorderPane();
        search.setOnAction((ActionEvent event) -> {
            // Convert to integer from textbox
            orderNumSearch = Integer.parseInt(orderSearch.getText());
            // Query
            try {
                String orderQry = "SELECT UNIQUE ORDER_NUM, F_NAME || ' ' || "
                    + "L_NAME, SHIPPED "
                    + "FROM JAVA_BOOK_ORDERS "
                    + "WHERE ORDER_NUM = ?";
                PreparedStatement searchStatement
                    = connection.prepareStatement(orderQry);
                searchStatement.setInt(1, orderNumSearch);
                rst3 = searchStatement.executeQuery();
                while (rst3.next()) {
                    searchOrderNum = rst3.getInt(1);
                    searchName = rst3.getString(2);
                    searchShipStat = rst3.getString(3);
                }
            } catch (SQLException SQLException) {}
            // Display queried info
            dispOrderNum = new Label("ORDER # " + searchOrderNum);
            dispOrderNum.setFont(new Font("Arial", 13));
            dispName = new Label("Name: " + searchName);
            dispName.setFont(new Font("Arial", 13));
            dispShipStat = new Label("Shipped Status: " + searchShipStat);
            dispShipStat.setFont(new Font("Arial", 13));
            // 0
            retrieveGrid.getColumnConstraints().add(new ColumnConstraints(20));
            // 0
            retrieveGrid.getRowConstraints().add(new RowConstraints(20));
            // 1
            retrieveGrid.getColumnConstraints().add(new 
                ColumnConstraints(250));
            // 1
            retrieveGrid.getRowConstraints().add(new RowConstraints(40));
            // 2
            retrieveGrid.getRowConstraints().add(new RowConstraints(40));
            // 3
            retrieveGrid.getRowConstraints().add(new RowConstraints(40));
            // 2
            retrieveGrid.getColumnConstraints().add(new ColumnConstraints(150));
            retrieveGrid.add(dispOrderNum,1,1);
            retrieveGrid.add(dispName,1,2);
            retrieveGrid.add(dispShipStat,1,3);
            retrieveGrid.add(update,2,3);
            retrieveBox.getChildren().addAll(retrieveGrid);
            retrievePane.setTop(retrieveBox);
            retrieveScene = new Scene(retrievePane,425,200,Color.WHITE);
            retrieveStage = new Stage();
            retrieveStage.setTitle("Order Status");
            retrieveStage.setScene(retrieveScene);
            retrieveStage.show();
        });
    }
    // Calculate totals
    private void GetTotal() {
        totalCost = 0;
        finalCost = 0;
        for (int i = 0; i < selectedBookPrices.size(); i++) {
            totalCost = selectedBookPrices.get(i) + totalCost;
        }
        finalCost = totalCost * tax + totalCost + shippingCost;
    }
    // Give user their order number after checkout
    private void GiveOrderNum() throws SQLException {
        Stage orderStage = new Stage();
        orderStage.setTitle("Order #");
        GridPane orderGrid2 = new GridPane();
        Label yourOrderNum = new Label("Your order number is " + 
            newOrderNum + "\n\n!!SAVE THIS NUMBER!!");
        yourOrderNum.setFont(new Font("Arial",14));
        BorderPane orderPane = new BorderPane();
        orderGrid2.getColumnConstraints().add(new ColumnConstraints(30));
        orderGrid2.getRowConstraints().add(new RowConstraints(30));
        orderGrid2.add(yourOrderNum, 1, 1);
        orderPane.setCenter(orderGrid2);
        Scene orderScene = new Scene(orderPane,250,150,Color.WHITE);
        orderStage.setScene(orderScene);
        orderStage.show();
    }
    // Main Menu gridpane
    private void GridPaneAdd() {
        selectLabel = new Label("Select a Book From The List\n\n<--");
        selectLabel.setWrapText(true);
        selectLabel.setFont(Font.font("Source Code Pro", 18));
        // Set selected picture size
        image.setFitWidth(155);
        image.setFitHeight(240);
        // Cache for performance
        image.setCache(true);
        grid = new GridPane();
        // Column constraints for grid formatting
        grid.getColumnConstraints().add(new ColumnConstraints(120));
        grid.getRowConstraints().add(new RowConstraints(110));
        grid.getColumnConstraints().add(new ColumnConstraints(0));
        grid.getRowConstraints().add(new RowConstraints(60));
        grid.getColumnConstraints().add(new ColumnConstraints(0));
        grid.getRowConstraints().add(new RowConstraints(80));
        grid.getColumnConstraints().add(new ColumnConstraints(5));
        grid.setHgap(0);
        grid.setVgap(20);
        grid.setPadding(new Insets(0,30,0,30));
        // Add image and button to grid
        grid.add(image, 2, 1);
        grid.add(titlePrice, 0, 3);
        grid.add(getTitlePrice, 4, 3);
        // Set cart button
        cartButton = new Button("Add to Cart");
        grid.add(cartButton, 4, 4);
        grid.add(infoButton, 0, 4);
        grid.add(selectLabel, 0, 0);
    }
    // open cart
    private void KeepShoppingFunction() {
        continueShopping.setOnAction((ActionEvent event) -> {
            cartWindow.close();
        });
    }
    // Layout main menu
    private void Layout() {
        bPane = new BorderPane();
        hbox = new HBox();
        hbox.getChildren().addAll(listView, grid);
        bPane.setLeft(hbox);
    }
    // Fill listview
    private void ListView() {
        listView = new ListView<>(bookNamesList);
        // Make only one item selectable
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    // Listview functionality
    private void ListViewFunction() {
        aboutMenuItems1 = new Label();
        aboutMenuItems2 = new Label();
        titlePrice = new Label();
        getTitlePrice = new Label();
        getTitlePrice.setWrapText(true);
        image = new ImageView();
        // Set on mouse click action
        listView.setOnMouseClicked((MouseEvent arg0) -> {
            selectLabel.setText("");
            image.setImage(imageArray[listView.getSelectionModel()
                .getSelectedIndex()]);
            titlePrice.setText("Title:\n\n" + "Price: ");
            getTitlePrice.setText(bookNames.get(listView.getSelectionModel()
                .getSelectedIndex()) + "\n\n" + curForm.format(bookPrices.get(
                listView.getSelectionModel().getSelectedIndex()
                )));
            selectionNum = listView.getSelectionModel().getSelectedIndex() + 1;
            aboutMenuItems1.setText("Title:\n\n" + "Price: ");
            aboutMenuItems2.setText(bookNames.get(listView.getSelectionModel()
                .getSelectedIndex()) + "\n\n" + curForm.format(bookPrices.get(
                listView.getSelectionModel().getSelectedIndex()
                )));
        });
    }
    private void MenuBar(Stage stage, BorderPane root) {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        // set menubar on top
        root.setTop(menuBar);
        // File menu item
        Menu fileMenu = new Menu("File");
        // Add exit item
        MenuItem exitMenuItem = new MenuItem("Exit");
        ExitMenuFunction(exitMenuItem);
        fileMenu.getItems().addAll(exitMenuItem);
        // Help menu item
        Menu helpMenu = new Menu("Help");
        // add about item
        MenuItem aboutMenuItem = new MenuItem("About");
        AboutMenuFunction(aboutMenuItem);
        helpMenu.getItems().addAll(aboutMenuItem);
        // Cart menu
        Menu cartMenu = new Menu("Cart");
        MenuItem cartItem = new MenuItem("Shopping Cart");
        MenuItem orderItem = new MenuItem("Order Status");
        CartMenuFunction(cartItem);
        OrderStatus(orderItem);
        cartMenu.getItems().addAll(cartItem, orderItem);
        // Admin menu
        Menu adminMenu = new Menu("Admin");
        MenuItem updateShip = new MenuItem("Update Ship Status");
        updateShipping(updateShip);
        adminMenu.getItems().addAll(updateShip);
        // Menu bar
        menuBar.getMenus().addAll(fileMenu, helpMenu, cartMenu, adminMenu);
    }
    // Search for order status
    private void OrderStatus(MenuItem orderItem) {
        orderItem.setOnAction((ActionEvent event) -> {
            searchOrderNum = 0;
            searchName = null;
            searchShipStat = null;
            search = new Button("Search");
            orderGrid = new GridPane();
            orderStatBox = new VBox();
            orderSearch = new TextField();
            // Ask user to enter their order number
            orderSearch.setPromptText("Enter Order #");
            // 0
            orderGrid.getColumnConstraints().add(new ColumnConstraints(20));
            // 0
            orderGrid.getRowConstraints().add(new RowConstraints(20));
            // 1
            orderGrid.getColumnConstraints().add(new ColumnConstraints(160));
            // 1
            orderGrid.getRowConstraints().add(new RowConstraints(40));
            // 2
            orderGrid.getRowConstraints().add(new RowConstraints(40));
            orderGrid.add(orderSearch,1,1);
            orderGrid.add(search,1,2);
            // Gets order status
            GetOrder();
            orderStatBox.getChildren().addAll(orderGrid);
            orderStatPane = new BorderPane();
            orderStatPane.setTop(orderStatBox);
            orderStatScene = new Scene(orderStatPane,300,150,Color.WHITE);
            orderStatStage = new Stage();
            search.requestFocus();
            orderStatStage.setTitle("Order Status");
            orderStatStage.setScene(orderStatScene);
            orderStatStage.show();
        });
    }
    // Allows admin to update shipping status
    private void UpdateShippedButton(Button update) {
        update.setOnAction((ActionEvent event) -> {
            // Update query
            try {
                String sqlUpdate = 
                        "UPDATE JAVA_BOOK_ORDERS " +
                        "SET SHIPPED = 'YES' " +
                        "WHERE ORDER_NUM = ?";
                PreparedStatement searchStatement = 
                        connection.prepareStatement(sqlUpdate);
                searchStatement.setInt(1, orderNumSearch);
                searchStatement.executeQuery();
                dispShipStat.setText("Shipped Status: YES");
            } catch (SQLException SQLException) {}
        });
    }
    // Check if valid zipcode
    private static boolean isValidZip(String validZip) {
        if (validZip.length() != 5) {
            return false;
        }
        return validZip.chars().allMatch(Character::isDigit);
    }
    // Main
    public static void main(String[] args) throws ClassNotFoundException, 
            SQLException {
        launch(args);
    }
    // Main start
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, 
            SQLException {
        // Title main menu
        stage.setTitle("Book Store");
        stage.setWidth(650);
        stage.setHeight(535);
        // Connect to database
        DBConnect();
        // Fill image list
        FillImageList();
        // Generate listview
        ListView();
        // Provide listview function
        ListViewFunction();
        FullInfoMenu(stage);
        // Setup gridpane
        GridPaneAdd();
        // Generate layout
        Layout();
        CartButtonFuction();
        // Generate and add menubar
        MenuBar(stage,bPane);
        // Display
        Scene scene = new Scene(bPane,300,250,Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }
    // Copy of GetOrder() but for accessing admin menu
    private void updateShipping(MenuItem updateShip) {
        updateShip.setOnAction((ActionEvent event) -> {
            searchOrderNum = 0;
            searchName = null;
            searchShipStat = null;
            search = new Button("Search");
            orderGrid = new GridPane();
            orderStatBox = new VBox();
            orderSearch = new TextField();
            orderSearch.setPromptText("Enter Order #");
            //0
            orderGrid.getColumnConstraints().add(new ColumnConstraints(20));
            //0
            orderGrid.getRowConstraints().add(new RowConstraints(20));
            //1
            orderGrid.getColumnConstraints().add(new ColumnConstraints(160));
            //1
            orderGrid.getRowConstraints().add(new RowConstraints(40));
            //2
            orderGrid.getRowConstraints().add(new RowConstraints(40));
            orderGrid.add(orderSearch,1,1);
            orderGrid.add(search,1,2);
            GetOrderAndUpdate();
            orderStatBox.getChildren().addAll(orderGrid);
            orderStatPane = new BorderPane();
            orderStatPane.setTop(orderStatBox);
            orderStatScene = new Scene(orderStatPane,300,150,Color.WHITE);
            orderStatStage = new Stage();
            search.requestFocus();
            orderStatStage.setTitle("Order Status");
            orderStatStage.setScene(orderStatScene);
            orderStatStage.show();
        });
    }
    // Tableview object class
    public static class BookCart {
        public final SimpleStringProperty bookTitle;
        public final SimpleDoubleProperty bookPrice;
        public BookCart(String bookTitle, Double bookPrice) {
            this.bookTitle = new SimpleStringProperty(bookTitle);
            this.bookPrice = new SimpleDoubleProperty(bookPrice);
        }
        public String getBookTitle() {
            return bookTitle.get();
        }
        public Double getBookPrice() {
            return bookPrice.get();
        }
        public void setBookTitle(String bookTitle) {
            this.bookTitle.set(bookTitle);
        }
        public void setBookPrice(Double bookPrice) {
            this.bookPrice.set(bookPrice);
        }
    }
}