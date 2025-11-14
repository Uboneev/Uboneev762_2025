//Контрагенты
public class Contractor {
    private int id;
    private String name;
    private String address;
    private String phone;

    public Contractor(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}