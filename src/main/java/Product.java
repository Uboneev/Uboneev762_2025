//Номенклатура
public class Product {
    private int id;
    private String name;
    private String unit;
    private double price;

    public Product(int id, String name, String unit, double price) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getUnit() { return unit; }
    public double getPrice() { return price; }
}