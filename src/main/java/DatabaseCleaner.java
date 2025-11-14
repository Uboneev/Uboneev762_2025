import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCleaner {
    private static final String URL = "jdbc:h2:~/testdb"; // путь к вашей базе
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Получение списка таблиц
            List<String> tables = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            while (rs.next()) {
                tables.add(rs.getString(1));
            }

            // Отключение ограничений связей для удаления данных
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");

            // Очистка каждой таблицы
            for (String table : tables) {
                String sql = "TRUNCATE TABLE " + table;
                stmt.executeUpdate(sql);
                System.out.println("Таблица " + table + " очищена");
            }

            // Включение ограничений обратно
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");

            System.out.println("База данных успешно очищена.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}