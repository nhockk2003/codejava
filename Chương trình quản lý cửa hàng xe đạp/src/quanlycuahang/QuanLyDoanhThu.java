/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
/**
 *
 * @author nguye
 */
public class QuanLyDoanhThu {
    


    private Scanner scanner = new Scanner(System.in);
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance(); 

public void xemDoanhThuTheoNgay() {
    System.out.print("Nhập ngày cần xem (dd/MM/yyyy): ");
    String ngayStr = scanner.nextLine();

    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = sdf.parse(ngayStr);

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String sql = "SELECT SUM(TongTien) AS TongDoanhThu " +
                     "FROM HoaDon " +
                     "WHERE NgayBan = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, sqlDate);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double tongDoanhThu = resultSet.getDouble("TongDoanhThu");
                    if (tongDoanhThu > 0) {
                        System.out.println("Tổng doanh thu ngày " + ngayStr + ": " + tongDoanhThu);
                    } else {
                        System.out.println("Không có doanh thu trong ngày " + ngayStr);
                    }
                } else {
                    System.out.println("Không có dữ liệu doanh thu cho ngày " + ngayStr);
                }
            }
        } 
    } catch (ParseException | SQLException e) {
        System.out.println("Lỗi khi xem doanh thu: " + e.getMessage());
    }
}


 public void xemDoanhThuTheoThang() {
    System.out.print("Nhập tháng và năm cần xem (MM/yyyy): ");
    String thangNamStr = scanner.nextLine();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

    try {
        java.util.Date utilDate = sdf.parse(thangNamStr);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(utilDate);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1); 
        java.sql.Date startDate = new java.sql.Date(cal.getTimeInMillis());

        cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        java.sql.Date endDate = new java.sql.Date(cal.getTimeInMillis());

        String sql = "SELECT SUM(TongTien) AS TongDoanhThu " +
                     "FROM HoaDon " +
                     "WHERE NgayBan >= ? AND NgayBan <= ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double tongDoanhThu = resultSet.getDouble("TongDoanhThu");
                    if (tongDoanhThu > 0) {
                        System.out.println("Tổng doanh thu tháng " + thangNamStr + ": " + tongDoanhThu);
                    } else {
                        System.out.println("Không có doanh thu trong tháng " + thangNamStr);
                    }
                } else {
                    System.out.println("Không có dữ liệu doanh thu cho tháng " + thangNamStr);
                }
            }
        } 
    } catch (ParseException | SQLException e) {
        System.out.println("Lỗi khi xem doanh thu: " + e.getMessage());
    }
}


 public void xemDoanhThuTheoNam() {
    System.out.print("Nhập năm cần xem (yyyy): ");
    int nam;
    try {
        nam = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới
    } catch (NumberFormatException e) {
        System.out.println("Năm phải là số nguyên.");
        return;
    }

    try (Connection connection = dbConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "SELECT MONTH(NgayBan) AS Thang, SUM(TongTien) AS TongDoanhThu " +
                 "FROM HoaDon " +
                 "WHERE YEAR(NgayBan) = ? " +
                 "GROUP BY MONTH(NgayBan)")) {

        preparedStatement.setInt(1, nam);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Thống kê doanh thu theo tháng trong năm " + nam + ":");
            System.out.println("-------------------------");
            System.out.printf("| %-10s | %-15s |\n", "Tháng", "Tổng doanh thu");
            System.out.println("-------------------------");
            boolean coDuLieu = false;
            while (resultSet.next()) {
                int thang = resultSet.getInt("Thang");
                double tongDoanhThu = resultSet.getDouble("TongDoanhThu");
                System.out.printf("| %-10d | %-15.0f |\n", thang, tongDoanhThu);
                coDuLieu = true; // Đánh dấu đã có dữ liệu
            }
            if (!coDuLieu) {
                System.out.println("| Không có dữ liệu            |");
            }
            System.out.println("-------------------------");
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi xem doanh thu: " + e.getMessage());
    }
}



    public void menuQuanLyDoanhThu() {
        int luaChon;
        do {
            System.out.println("\nQUẢN LÝ DOANH THU:");
            System.out.println("1. Xem doanh thu theo ngày");
            System.out.println("2. Xem doanh thu theo tháng");
            System.out.println("3. Xem doanh thu theo năm");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            switch (luaChon) {
                case 1:
                    xemDoanhThuTheoNgay();
                    break;
                case 2:
                    xemDoanhThuTheoThang();
                    break;
                case 3:
                    xemDoanhThuTheoNam();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (luaChon != 0);
    }
}


