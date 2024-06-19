package quanlycuahangxedap;

import java.sql.*;

public class DatabaseConnection {
    
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=baitapjava;integratedSecurity=true;trustServerCertificate=true"; // Thay thế "baitapjava" bằng tên CSDL của bạn

   
    private static DatabaseConnection instance;

    private Connection connection;


    private DatabaseConnection() {
        openConnection(); 
    }

  
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
             
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

              
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Kết nối đến cơ sở dữ liệu thành công!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Lỗi khi mở kết nối: " + e.getMessage());
            e.printStackTrace();
            
            System.exit(1);
        }
    }

 
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            openConnection(); // Tự động mở lại kết nối nếu bị đóng
        }
        return connection;
    }


    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối đến cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
