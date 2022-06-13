package shopConnect;

public class Products {
    public int id;
    public String name;
    public String img;
    public float price;


    public Products(int id, String name,  String img, float price) {
        this.id = id;
        this.name = name;

        this.img = img;
        this.price = price;
    }

    public Products(String name, String img, float price) {
        this.name = name;

        this.img = img;
        this.price = price;
    }
}
