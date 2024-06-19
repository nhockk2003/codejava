/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thuctap2;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class DbConnection {
    
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyLichLamViec;integratedSecurity=true;trustServerCertificate=true";

    private Connection connection;

    public DbConnection() throws SQLException {
        try {
            // Register the JDBC driver (if needed)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("SQL Server JDBC driver not found.");
        }

        // Establish the connection (Windows Authentication)
        this.connection = DriverManager.getConnection(DB_URL);
    }

    // Method to load LichLamViec data from the database
    public List<Lichlamviec> loadLich() throws SQLException {
        List<Lichlamviec> list = new ArrayList<>();
        String sql = "SELECT * FROM LichLamViec";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Lichlamviec lich = new Lichlamviec(
                    rs.getString("TenCongViec"),
                    rs.getTime("ThoiGianBatDau").toLocalTime(),
                    rs.getTime("ThoiGianKetThuc").toLocalTime(),
                    rs.getDate("Ngay").toLocalDate()
                );
                list.add(lich);
            }
        }
        return list;
    }

 
    public void insertLich(Lichlamviec lich) throws SQLException {
        String sql = "INSERT INTO LichLamViec (TenCongViec, ThoiGianBatDau, ThoiGianKetThuc, Ngay) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lich.getTenCongViec());
            stmt.setTime(2, Time.valueOf(lich.getThoiGianBatDau()));
            stmt.setTime(3, Time.valueOf(lich.getThoiGianKetThuc()));
            stmt.setDate(4, Date.valueOf(lich.getNgay()));
            stmt.executeUpdate();
        }
    }

    // Method to update an existing LichLamViec in the database
    public void updateLich(Lichlamviec lich) throws SQLException {
        // Assuming you have an ID field in your LichLamViec class and table
        String sql = "UPDATE LichLamViec SET TenCongViec = ?, ThoiGianBatDau = ?, ThoiGianKetThuc = ?, Ngay = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, lich.getTenCongViec());
            stmt.setTime(2, Time.valueOf(lich.getThoiGianBatDau()));
            stmt.setTime(3, Time.valueOf(lich.getThoiGianKetThuc()));
            stmt.setDate(4, Date.valueOf(lich.getNgay()));
        
            stmt.executeUpdate();
        }
    }
    public void deleteLichByValues(String tenCongViec, LocalTime thoiGianBatDau, LocalTime thoiGianKetThuc, LocalDate ngay) throws SQLException {
    String sql = "DELETE FROM LichLamViec WHERE TenCongViec = ? AND ThoiGianBatDau = ? AND ThoiGianKetThuc = ? AND Ngay = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, tenCongViec);
        stmt.setTime(2, Time.valueOf(thoiGianBatDau));
        stmt.setTime(3, Time.valueOf(thoiGianKetThuc));
        stmt.setDate(4, Date.valueOf(ngay));
        stmt.executeUpdate();
    }
} 
}

