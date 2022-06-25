package com.example.diamond_shop;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shopConnect.ConnectDB;
import shopConnect.Products;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.SimpleAttributeSet.EMPTY;


public class HelloApplication extends Application {

    TextField tfName = new TextField();
    TextField tfImg = new TextField();
    TextField tfPrice = new TextField();
    VBox productRoot = new VBox();
    ConnectDB connection = new ConnectDB();
    private Scene scene;
    private Scene navigationButton, admin, homePage;

    private Stage window;
//----------------------------------------------------------------------------------------------------------
    Scene navigationButton() {
        GridPane goAdmin = new GridPane();
        GridPane goHome = new GridPane();
        GridPane goHomeAdmin = new GridPane();
        goHomeAdmin.setAlignment(Pos.CENTER);
        Button btnGoAdmin = new Button("GO ADMIN");
        btnGoAdmin.setPadding(new Insets(15, 25, 15, 25));
        btnGoAdmin.setOnAction(actionEvent -> {
            window.setScene(admin);
            window.centerOnScreen();
        });
        Button btnGoHome = new Button("GO Home");
        btnGoHome.setPadding(new Insets(15, 25, 15, 25));
        btnGoHome.setOnAction(actionEvent -> {
            window.setScene(homePage);
            window.centerOnScreen();
        });
        GridPane gridAdmin = new GridPane();
        gridAdmin.getChildren().addAll(btnGoAdmin);

        GridPane btnHome = new GridPane();
        btnHome.getChildren().add(btnGoHome);

        goAdmin.add(gridAdmin, 0, 0);

        goHome.add(btnHome, 1, 0);

        goHomeAdmin.add( goHome,0,0);
        goHomeAdmin.add( goAdmin,1,0);

        return new Scene(goHomeAdmin, 300, 300);
    }
    Scene home() {
        Button btnGoBack = new Button("Back");
        btnGoBack.setPadding(new Insets(5, 15, 5, 15));
        btnGoBack.setOnAction(actionEvent -> {
            window.setScene(navigationButton);
            window.centerOnScreen();
        });
        //
        GridPane home = new GridPane();
        GridPane grid = new GridPane();
        GridPane goBack = new GridPane();
        //
        GridPane btnBack = new GridPane();
        btnBack.getChildren().addAll(btnGoBack);

        goBack.add(btnBack, 1, 0);

        home.add( goBack,2,0);
        //
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        ConnectDB connection = new ConnectDB();
        List<Products> products = connection.getProducts();

        //show
        for(int i = 0; i < products.size(); i++){

            var btnBuy = new Button("Buy Now");
            Image image = new Image(products.get(i).img);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

            Label lbId = new Label("" + products.get(i).id);
            Label lbName = new Label(products.get(i).name);
            Label lbPrice = new Label("" + products.get(i).price);

            grid.add((lbId), 0, i+0);
            grid.add((lbName), 1, i+0);
            grid.add((imageView), 2, i+0);
            grid.add(lbPrice, 3, i+0);
           grid.add((btnBuy), 4, i+0);
        }
        home.getChildren().add(grid);
        return new Scene(home, 810, 800);
    }
    Scene admin() {
        GridPane goBack = new GridPane();
        Button btnGoBack = new Button("Back");
        btnGoBack.setPadding(new Insets(5, 15, 5, 15));
        btnGoBack.setOnAction(actionEvent -> {
            window.setScene(navigationButton);
            window.centerOnScreen();
        });
        //
        GridPane adminn = new GridPane();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        List<Products> products = connection.getProducts();
        //
        //
        GridPane btnAdmin = new GridPane();
        btnAdmin.getChildren().addAll(btnGoBack);

        goBack.add(btnAdmin, 0, 0);

        adminn.add( goBack,1,0);
        //

        grid.add(new Label("Name:"), 0, 0);
        var tfName = new TextField();
        grid.add(tfName, 0, 1);
        //
        grid.add(new Label("Image:"), 1, 0);
        var tfImage = new TextField();
        grid.add(tfImage, 1, 1);
        //
        grid.add(new Label("Price:"), 2, 0);
        var tfPrice = new TextField();
        grid.add(tfPrice, 2, 1);
        //
        grid.add(new Label("Description:"), 3, 0);
        var tfDescription = new TextField();
        grid.add(tfDescription, 3, 1);
        //

        // add
        var btnAdd = new Button("Add");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnAdd.setOnAction(e -> {
            String name = tfName.getText();
            String image = tfImage.getText();
            Integer price = Integer.valueOf(tfPrice.getText());

            if (!name.equals(EMPTY) && !image.equals(EMPTY) && !price.equals(EMPTY)) {
                connection.insertProduct(new Products(tfName.getText(), tfImg.getText(), Float.parseFloat(tfPrice.getText())));
                try {
                    admin = admin();
                    window.setScene(admin);
                    window.centerOnScreen();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                return;
            }
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank!");
            alert.showAndWait();
        });
        grid.add(btnAdd, 4, 1);

        //show
        for (int i = 0; i < products.size(); i++) {

            Image image = new Image(products.get(i).img);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(110);
            imageView.setFitHeight(110);


            grid.add(new Label(String.valueOf(products.get(i).id)), 0, i + 2);
            grid.add(imageView, 1, i + 2);
            grid.add(new Label(products.get(i).name), 2, i + 2);
            grid.add(new Label("$" + String.valueOf(products.get(i).price)), 3, i + 2);
//            grid.add(new Label(products.get(i).getDescription()), 3, i + 2);

            // Update
//            var btnUpdate = new Button("Update");
//            btnUpdate.setId(String.valueOf(i));
//            btnUpdate.setOnAction(e -> {
//                btnAdd.setVisible(false);
//                int id1 = Integer.parseInt(btnUpdate.getId());
//                System.out.println(id1);
//                TextField tfname = (TextField) grid.getChildren().get(1);
//                tfname.setText("" + products.get(id1).name());
//                TextField tfimage = (TextField) grid.getChildren().get(3);
//                tfimage.setText("" + paintingList.get(id1).getImage());
////                name.setText(stdList.get(Integer.parseInt(btnUpdate.getId())).getName());
//                TextField tfprice = (TextField) grid.getChildren().get(5);
//                tfprice.setText("" + paintingList.get(id1).getPrice());
//                TextField tfdescription = (TextField) grid.getChildren().get(7);
//                tfdescription.setText("" + paintingList.get(id1).getDescription());
//                var newbtnAdd = new Button("Update");
//                newbtnAdd.setPadding(new Insets(5, 15, 5, 15));
//                newbtnAdd.setOnAction(newe -> {
//                    Integer Newid = paintingList.get(id1).id;
//                    System.out.println(Newid);
//                    String Newname = tfname.getText();
//                    String Newimage = tfimage.getText();
//                    Integer Newprice = 0;
//                    try {
//                        Newprice = Integer.parseInt(tfprice.getText());
//                    } catch (Exception ex) {
//                    }
////                    Integer Newprice = Integer.valueOf(tfprice.getText());
//                    String Newdescription = tfdescription.getText();
//                    if (!Newname.equals(EMPTY) && !Newimage.equals(EMPTY) && !(Newprice == 0)) {
//                        DB.updatePainting(new Painting(Newid, Newname, Newimage, Newprice, Newdescription));
//                        try {
//                            admin = admin();
//                            window.setScene(admin);
//                            window.centerOnScreen();
//                        } catch (Exception ex) {
//                            throw new RuntimeException(ex);
//                        }
//                        return;
//                    }
//                    var alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setHeaderText(null);
//                    alert.setContentText("Please fill all blank!");
//                    alert.showAndWait();
//                });
//                grid.add(newbtnAdd, 4, 1);
//            });
//            grid.add(btnUpdate, 4, i + 2);

            // Delete
            var btnDelete = new Button("Delete");
            btnDelete.setId(String.valueOf(products.get(i).id));
            btnDelete.setOnAction(e -> {
                int id3 = Integer.parseInt(btnDelete.getId());
                connection.deleteProduct(id3);
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Deleted!");
                alert.showAndWait();
                try {
                    admin = admin();
                    window.setScene(admin);
                    window.centerOnScreen();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            grid.add(btnDelete, 5, i + 2);
        }
        adminn.getChildren().add(grid);
        return new Scene(adminn, 810, 800);
    }

    public void start(Stage stage) {
        ScrollPane scrollPane = new ScrollPane();

        admin = this.admin();
        homePage = this.home();
        navigationButton = this.navigationButton();
        window = stage;
        window.setScene(navigationButton);
        window.show();
    }


//----------------------------------------------------------------------------------------------------------





//
//    @Override
//    public void start(Stage stage) throws IOException {
//
//        ConnectDB connection = new ConnectDB();
//        VBox root = new VBox();
//        HBox hInsertProduct = new HBox();
//
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(root);
//
//
//        tfName.setPromptText("Nhập tên sản phẩm");
//        tfPrice.setPromptText("Nhập giá sản phẩm");
//        tfImg.setPromptText("Nhập link ảnh sản phẩm");
//
//        Button btnAdd = new Button("Add");
//        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                connection.insertProduct(new Products(tfName.getText(), tfImg.getText(), Float.parseFloat(tfPrice.getText())));
//                tfName.clear();
//                tfImg.clear();
//                tfPrice.clear();
//                getThenDisplayProducts(productRoot, connection);
//            }
//        });
//
//
//        Button btnEdit = new Button("Update");
//        btnEdit.setOnAction(event -> {
//            connection.updateProduct(new Products(tfName.getText(), tfImg.getText(), Float.parseFloat(tfPrice.getText())),Integer.parseInt(tfName.getId()));
//            tfName.clear();
//            tfImg.clear();
//            tfPrice.clear();
//            getThenDisplayProducts(productRoot, connection);
//        });
//
//
//
//        hInsertProduct.getChildren().addAll(tfName, tfImg, tfPrice, btnAdd, btnEdit);
//        root.getChildren().addAll(hInsertProduct, productRoot);
//        getThenDisplayProducts(productRoot, connection);
//        Scene scene = new Scene(scrollPane, 900, 700);
//
//        stage.setTitle("TRANG SỨC");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    void displayProducts(ConnectDB connection, VBox root, List <Products> products)  {
//        root.getChildren().clear();
//        System.out.println(root.getChildren().getClass());
//        for (int i = 0; i < products.size(); i++) {
//            final int finialI = i;
//            HBox productsBox = new HBox();
//            Label lbId = new Label("" + products.get(i).id);
//            Label lbName = new Label(products.get(i).name);
//            lbName.setMaxWidth(150);
//
//
//
//            Image image = new Image(products.get(i).img);
//            ImageView imageView = new ImageView(image);
//            //Setting the position of the image
//            imageView.setX(50);
//            imageView.setY(25);
//
//            //setting the fit height and width of the image view
//            imageView.setFitHeight(200);
//            imageView.setFitWidth(200);
//            //Setting the preserve ratio of the image view
//            imageView.setPreserveRatio(true);
//
//
//            Label lbImg = new Label(products.get(i).img);
//            Label lbPrice = new Label("" + products.get(i).price);
//
//
//
//            Button btnDelete = new Button("Delete");
//            btnDelete.setOnAction(actionEvent -> {
//                System.out.println("Click delete " + products.get(finialI).id);
//                connection.deleteProduct(products.get(finialI).id);
//                getThenDisplayProducts(root, connection);
//            });
//
//            Button btnUpdate = new Button("Update");
//            btnUpdate.setOnAction(actionEvent->{
//                tfName.setText(String.valueOf((products.get(finialI).name)));
//                tfName.setId(""+products.get(finialI).id);
//                tfImg.setText(String.valueOf((products.get(finialI).img)));
//                tfPrice.setText(String.valueOf((products.get(finialI).price)));
//
//            });
//
//            Button btnBuy = new Button("Buy now");
//
//            productsBox.setSpacing(50);
//            productsBox.getChildren().addAll(lbId, lbName, imageView, lbPrice, btnDelete, btnUpdate,btnBuy);
//            root.getChildren().add(productsBox);
//        }
//    }
//
//    private void getThenDisplayProducts(VBox root, ConnectDB connection) {
//        List<Products> products = connection.getProducts();
//        displayProducts(connection, root, products);
//    }

    public static void main(String[] args) {
        launch();
    }
}