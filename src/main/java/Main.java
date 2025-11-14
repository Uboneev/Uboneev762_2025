import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите действие:");
        System.out.println("1. Инициализация базы данных (один раз или если была отчищена)");
        System.out.println("2. Очистить таблицы");
        System.out.println("3. Добавить заказ");
        System.out.println("4. Вывести все");
        System.out.println("5. Выйти из выбора");

        System.out.print("Введите номер выбора: ");
        int choice = scanner.nextInt(); // считываем число

        switch (choice) {
            case 1:
                System.out.println("Инициализация базы данных...");
                DBInitializer.initialize();
                break;
            case 2:
                System.out.println("Очистка базы...");
                DatabaseCleaner.main();
                break;
            case 3:
                System.out.println("Добавим заказ (проверка работы)");

                //Создаем заказ
                Date today = new Date(System.currentTimeMillis());

                //Выбор Контрагента
                printAllContractors();
                System.out.print("Введите ID контрагента для выбора: ");
                int selectedIdContractors = scanner.nextInt();

                Order order = new Order(1, selectedIdContractors, today);

                //Выбор продукта
                printAllProducts();
                System.out.print("Введите ID продукта для добавления его в заказ: ");
                int selectedIdProducts = scanner.nextInt();

                System.out.print("Введите кол-во продукта для добавления его в заказ: ");
                int quantity = scanner.nextInt();

                OrderItem item1 = new OrderItem(selectedIdProducts, quantity);
                order.addItem(item1);
                order.printOrder();

                //Добавим 5 к кол-ву в заказе
                order.receiveGoods(selectedIdProducts, 5);
                order.printOrder();
                //Установим новое кол-во 10
                order.updateItemQuantity(selectedIdProducts,10);
                order.printOrder();

                break;
            case 4:
                System.out.println("Вывод данных...");
                printAllContractors();
                printAllProducts();
                break;
            case 5:
                System.out.println("Выход из программы");
                break;
            default:
                System.out.println("Некорректный выбор");
        }
        scanner.close();
    }

    public static void printAllContractors() {
        String sql = "SELECT * FROM CONTRACTORS";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Контрагенты:");
            while (rs.next()) {
                System.out.printf("ID: %d, Название: %s, Адрес: %s, Телефон: %s%n",
                        rs.getInt("ID"), rs.getString("NAME"),
                        rs.getString("ADDRESS"), rs.getString("PHONE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllProducts() {
        String sql = "SELECT * FROM PRODUCTS";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Товары:");
            while (rs.next()) {
                System.out.printf("ID: %d, Название: %s, Ед.изм: %s, Цена: %.2f%n",
                        rs.getInt("ID"), rs.getString("NAME"),
                        rs.getString("UNIT"), rs.getDouble("PRICE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}