package shopConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {

        private static Connection connection;
        public  ConnectDB(){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/diamond_shop","root",""
                );
                System.out.println("Connect successfully!");
            } catch(SQLException e){
                connection=null;
                System.out.println(e);
            }
        }
        public List<Products> getProducts(){
            ArrayList<Products> products = new ArrayList<>();
            try{
                ResultSet result = connection.prepareStatement("SELECT * from diamond_pro").executeQuery();
                while (result.next()){
                    int id =result.getInt("id");
                    String name = result.getNString("name");
                    String img = result.getNString("img");
                    float price = result.getFloat("price");
                    System.out.println(id);
                    System.out.println(name);
                    System.out.println(img);
                    System.out.println(price);

                    products.add(new Products(id, name, img, price));

                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            return products;
        }
        public void insertProduct(Products products){
            String sql = "INSERT INTO diamond_pro(name,img,price) VALUES ('"+products.name+"','"+ products.img+"',"+ products.price+")";
            System.out.println(sql);

            try{
                connection.prepareStatement(sql).executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }

        public void updateProduct(Products products, int id){
            String sql = "UPDATE diamond_pro SET name ='"+products.name+"',img='"+ products.img+"',price=" + products.price + "where id="+ id;
            System.out.println(sql);
            try{
                connection.prepareStatement(sql).executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        public void deleteProduct(int id){
            String sql ="DELETE FROM diamond_pro where id= "+ id;
            System.out.println(sql);
            try{
                connection.prepareStatement(sql).executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }



}
