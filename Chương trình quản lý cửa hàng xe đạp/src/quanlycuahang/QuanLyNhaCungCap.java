/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;
/**
 *
 * @author nguye
 */
public class QuanLyNhaCungCap {
    
    private Scanner scanner = new Scanner(System.in);
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance(); 


    public void themNhaCungCap() {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO NhaCungCap (TenNCC, DiaChi, SDT) VALUES (?, ?, ?)")) {

            System.out.print("Nhập tên nhà cung cấp: ");
            String tenNCC = scanner.nextLine();
            System.out.print("Nhập địa chỉ: ");
            String diaChi = scanner.nextLine();
            System.out.print("Nhập số điện thoại: ");
            String sdt = scanner.nextLine();

            preparedStatement.setString(1, tenNCC);
            preparedStatement.setString(2, diaChi);
            preparedStatement.setString(3, sdt);

            preparedStatement.executeUpdate();
            System.out.println("Thêm nhà cung cấp thành công!");

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

public void suaNhaCungCap() {
    System.out.print("Nhập mã nhà cung cấp cần sửa: ");
    int maNCC;
    try {
        maNCC = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới còn lại
    } catch (NumberFormatException e) {
        System.out.println("Mã nhà cung cấp phải là số nguyên.");
        return;
    }

    try (Connection checkConnection = dbConnection.getConnection()) { // Kết nối để kiểm tra
        if (!kiemTraNhaCungCapTonTai(maNCC, checkConnection)) {
            System.out.println("Không tìm thấy nhà cung cấp có mã " + maNCC);
            return;
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi kiểm tra nhà cung cấp: " + e.getMessage());
        return;
    }

    try (Connection updateConnection = dbConnection.getConnection(); // Kết nối để cập nhật
         PreparedStatement preparedStatement = updateConnection.prepareStatement(
                 "UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, SDT=? WHERE MaNCC=?")) {

        System.out.print("Nhập tên nhà cung cấp mới: ");
        String tenNCC = scanner.nextLine();
        System.out.print("Nhập địa chỉ mới: ");
        String diaChi = scanner.nextLine();
        System.out.print("Nhập số điện thoại mới: ");
        String sdt = scanner.nextLine();

        preparedStatement.setString(1, tenNCC);
        preparedStatement.setString(2, diaChi);
        preparedStatement.setString(3, sdt);
        preparedStatement.setInt(4, maNCC);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Sửa thông tin nhà cung cấp thành công!");
        } else {
            System.out.println("Không thể sửa thông tin nhà cung cấp.");
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi sửa thông tin nhà cung cấp: " + e.getMessage());
        e.printStackTrace();
    }
}

public void xoaNhaCungCap() {
    System.out.print("Nhập mã nhà cung cấp cần xóa: ");
    int maNCC;
    try {
        maNCC = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới còn lại
    } catch (NumberFormatException e) {
        System.out.println("Mã nhà cung cấp phải là số nguyên.");
        return;
    }

    try (Connection connection = dbConnection.getConnection()) { 
        if (!kiemTraNhaCungCapTonTai(maNCC, connection)) { 
            System.out.println("Không tìm thấy nhà cung cấp có mã " + maNCC);
            return;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM NhaCungCap WHERE MaNCC=?")) {

            preparedStatement.setInt(1, maNCC);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa nhà cung cấp thành công!");
            } else {
                System.out.println("Không thể xóa nhà cung cấp.");
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi: " + e.getMessage());
    }
}

private boolean kiemTraNhaCungCapTonTai(int maNCC, Connection connection) throws SQLException {
    String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE MaNCC = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, maNCC);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
    return false;
}

public void hienThiDanhSachNhaCungCap() {
    try (Connection connection = dbConnection.getConnection(); // Lấy kết nối mới ở đây
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT * FROM NhaCungCap")) {

        List<NhaCungCap> dsNhaCungCap = new ArrayList<>();

        while (resultSet.next()) {
            int maNCC = resultSet.getInt("MaNCC");
            String tenNCC = resultSet.getString("TenNCC");
            String diaChi = resultSet.getString("DiaChi");
            String sdt = resultSet.getString("SDT");

            NhaCungCap nhaCungCap = new NhaCungCap(maNCC, tenNCC, diaChi, sdt);
            dsNhaCungCap.add(nhaCungCap);
        }

        if (dsNhaCungCap.isEmpty()) {
            System.out.println("Không có nhà cung cấp nào.");
        } else {
            System.out.println("DANH SÁCH NHÀ CUNG CẤP:");
            System.out.println("---------------------------------------------------");
            System.out.printf("| %-5s | %-30s | %-30s | %-15s |\n",
                    "Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "SĐT");
            System.out.println("---------------------------------------------------");

            for (NhaCungCap ncc : dsNhaCungCap) {
                System.out.printf("| %-5d | %-30s | %-30s | %-15s |\n",
                        ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSdt());
            }
            System.out.println("---------------------------------------------------");
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi hiển thị danh sách nhà cung cấp: " + e.getMessage());
        e.printStackTrace(); // In ra stack trace để dễ debug
    } 
}


   


    public void menuQuanLyNhaCungCap() {
        int luaChon;
        do {
            System.out.println("\nQUẢN LÝ NHÀ CUNG CẤP:");
            System.out.println("1. Thêm nhà cung cấp");
            System.out.println("2. Sửa nhà cung cấp");
            System.out.println("3. Xóa nhà cung cấp");
            System.out.println("4. Hiển thị danh sách nhà cung cấp");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            switch (luaChon) {
                case 1:
                    themNhaCungCap();
                    break;
                case 2:
                    suaNhaCungCap();
                    break;
                case 3:
                    xoaNhaCungCap();
                    break;
                case 4:
                    hienThiDanhSachNhaCungCap();
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

