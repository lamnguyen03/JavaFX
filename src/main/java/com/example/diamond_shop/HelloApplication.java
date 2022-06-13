package com.example.diamond_shop;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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



public class HelloApplication extends Application {

    TextField tfName = new TextField();
    TextField tfImg = new TextField();
    TextField tfPrice = new TextField();
    VBox productRoot = new VBox();

    @Override
    public void start(Stage stage) throws IOException {

        ConnectDB connection = new ConnectDB();
        VBox root = new VBox();
        HBox hInsertProduct = new HBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);


        tfName.setPromptText("Nhập tên sản phẩm");
        tfPrice.setPromptText("Nhập giá sản phẩm");
        tfImg.setPromptText("Nhập link ảnh sản phẩm");

        Button btnAdd = new Button("Add");
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                connection.insertProduct(new Products(tfName.getText(), tfImg.getText(), Float.parseFloat(tfPrice.getText())));
                tfName.clear();
                tfImg.clear();
                tfPrice.clear();
                getThenDisplayProducts(productRoot, connection);
            }
        });


        Button btnEdit = new Button("Update");
        btnEdit.setOnAction(event -> {
            connection.updateProduct(new Products(tfName.getText(), tfImg.getText(), Float.parseFloat(tfPrice.getText())),Integer.parseInt(tfName.getId()));
            tfName.clear();
            tfImg.clear();
            tfPrice.clear();
            getThenDisplayProducts(productRoot, connection);
        });



        hInsertProduct.getChildren().addAll(tfName, tfImg, tfPrice, btnAdd, btnEdit);
        root.getChildren().addAll(hInsertProduct, productRoot);
        getThenDisplayProducts(productRoot, connection);
        Scene scene = new Scene(scrollPane, 900, 700);

        stage.setTitle("TRANG SỨC");
        stage.setScene(scene);
        stage.show();
    }

    void displayProducts(ConnectDB connection, VBox root, List <Products> products)  {
        root.getChildren().clear();
        System.out.println(root.getChildren().getClass());
        for (int i = 0; i < products.size(); i++) {
            final int finialI = i;
            HBox productsBox = new HBox();
            Label lbId = new Label("" + products.get(i).id);
            Label lbName = new Label(products.get(i).name);
            lbName.setMaxWidth(150);


            Image image = new Image(products.get(i).img);
            ImageView imageView = new ImageView(image);
            //Setting the position of the image
            imageView.setX(50);
            imageView.setY(25);

            //setting the fit height and width of the image view
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            //Setting the preserve ratio of the image view
            imageView.setPreserveRatio(true);


            Label lbImg = new Label(products.get(i).img);
            Label lbPrice = new Label("" + products.get(i).price);

            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(actionEvent -> {
                System.out.println("Click delete " + products.get(finialI).id);
                connection.deleteProduct(products.get(finialI).id);
                getThenDisplayProducts(root, connection);
            });

            Button btnUpdate = new Button("Update");
            btnUpdate.setOnAction(actionEvent->{
                tfName.setText(String.valueOf((products.get(finialI).name)));
                tfName.setId(""+products.get(finialI).id);
                tfImg.setText(String.valueOf((products.get(finialI).img)));
                tfPrice.setText(String.valueOf((products.get(finialI).price)));



            });


            productsBox.setSpacing(50);
            productsBox.getChildren().addAll(lbId, lbName, imageView, lbPrice, btnDelete, btnUpdate);
            root.getChildren().add(productsBox);
        }
    }

    private void getThenDisplayProducts(VBox root, ConnectDB connection) {
        List<Products> products = connection.getProducts();
        displayProducts(connection, root, products);
    }

    public static void main(String[] args) {
        launch();
    }
}