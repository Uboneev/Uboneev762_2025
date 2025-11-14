import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;


public class DBInitializer {
    public static void initialize() {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // Создаем таблицы
            String sqlCreate = """
                CREATE TABLE IF NOT EXISTS CONTRACTORS (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    NAME VARCHAR(255) NOT NULL,
                    ADDRESS VARCHAR(255),
                    PHONE VARCHAR(50)
                );

                CREATE TABLE IF NOT EXISTS PRODUCTS (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    NAME VARCHAR(255) NOT NULL,
                    UNIT VARCHAR(50),
                    PRICE DECIMAL(10,2)
                );

                CREATE TABLE IF NOT EXISTS ORDERS (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    CONTRACTOR_ID INT,
                    ORDER_DATE DATE,
                    FOREIGN KEY (CONTRACTOR_ID) REFERENCES CONTRACTORS(ID)
                );

                CREATE TABLE IF NOT EXISTS ORDER_ITEMS (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    ORDER_ID INT,
                    PRODUCT_ID INT,
                    QUANTITY INT,
                    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID),
                    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS(ID)
                );

                CREATE TABLE IF NOT EXISTS RECEIPTS (
                    ID INT AUTO_INCREMENT PRIMARY KEY,
                    ORDER_ID INT,
                    RECEIPT_DATE DATE,
                    QUANTITY_DELIVERED INT,
                    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID)
                );
                """;

            // Выполняем скрипт
            stmt.execute(sqlCreate);

            // Вставим тестовые данные
            String sqlInsert = """
                INSERT INTO CONTRACTORS (NAME, ADDRESS, PHONE) VALUES
                ('ООО Ромашка', 'г. Москва, ул. Пушкина, д.1', '89990001122'),
                ('ЗАО Весна', 'г. Санкт-Петербург, ул. Ленина, д.5', '89231234567');

                INSERT INTO PRODUCTS (NAME, UNIT, PRICE) VALUES
                ('Товар1', 'шт', 50.00),
                ('Товар2', 'кг', 120.50);

                -- Можно добавить начальные заказы и др.
                """;

            stmt.execute(sqlInsert);

            System.out.println("База данных успешно создана и инициализирована");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}