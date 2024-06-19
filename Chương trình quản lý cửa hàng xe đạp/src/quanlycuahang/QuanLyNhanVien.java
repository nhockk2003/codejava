/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;
/**
 *
 * @author nguye
 */

public class QuanLyNhanVien {
    private Scanner scanner = new Scanner(System.in);
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance(); 


    public void themNhanVien() {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO NhanVien (TenNV, DiaChi, SDT, ChucVu, LuongCoBan, NgayVaoLam) VALUES (?, ?, ?, ?, ?, ?)")) {

            System.out.print("Nhập tên nhân viên: ");
            String tenNV = scanner.nextLine();
            System.out.print("Nhập địa chỉ: ");
            String diaChi = scanner.nextLine();
            System.out.print("Nhập số điện thoại: ");
            String sdt = scanner.nextLine();
            System.out.print("Nhập chức vụ: ");
            String chucVu = scanner.nextLine();
            System.out.print("Nhập lương cơ bản: ");
            double luongCoBan = scanner.nextDouble();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại
            System.out.print("Nhập ngày vào làm (dd/MM/yyyy): ");
            String ngayVaoLamStr = scanner.nextLine();
            Date ngayVaoLam = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(ngayVaoLamStr);

            // Chuyển đổi java.util.Date thành java.sql.Date
            java.sql.Date sqlNgayVaoLam = new java.sql.Date(ngayVaoLam.getTime());

        preparedStatement.setString(1, tenNV);
        preparedStatement.setString(2, diaChi);
        preparedStatement.setString(3, sdt);
        preparedStatement.setString(4, chucVu);
        preparedStatement.setDouble(5, luongCoBan);
        preparedStatement.setDate(6, sqlNgayVaoLam); // Sử dụng sqlNgayVaoLam
        preparedStatement.executeUpdate();
        System.out.println("Thêm nhân viên thành công!");

    } catch (SQLException | ParseException e) {
        System.out.println("Lỗi: " + e.getMessage());
    }
}
public void suaNhanVien() {
    try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE NhanVien SET TenNV=?, DiaChi=?, SDT=?, ChucVu=?, LuongCoBan=?, NgayVaoLam=? WHERE MaNV=?")) {


        System.out.print("Nhập mã nhân viên cần sửa: ");
        int maNV;
        try {
            maNV = scanner.nextInt();
            scanner.nextLine(); 
        } catch (NumberFormatException e) {
            System.out.println("Mã nhân viên phải là số nguyên.");
            return; 
        }

        if (!kiemTraNhanVienTonTai(maNV, connection)) {
            System.out.println("Không tìm thấy nhân viên có mã " + maNV);
            return;
        }

        System.out.print("Nhập tên nhân viên mới: ");
        String tenNV = scanner.nextLine();

        System.out.print("Nhập địa chỉ mới: ");
        String diaChi = scanner.nextLine();

        System.out.print("Nhập số điện thoại mới: ");
        String sdt = scanner.nextLine();

        System.out.print("Nhập chức vụ mới: ");
        String chucVu = scanner.nextLine();

        System.out.print("Nhập lương cơ bản mới: ");
        double luongCoBan;
        try {
            luongCoBan = scanner.nextDouble();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại
        } catch (NumberFormatException e) {
            System.out.println("Lương cơ bản phải là số.");
            return;
        }

        System.out.print("Nhập ngày vào làm mới (dd/MM/yyyy): ");
        String ngayVaoLamStr = scanner.nextLine();
        Date ngayVaoLam;
        try {
            ngayVaoLam = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(ngayVaoLamStr);
        } catch (ParseException e) {
            System.out.println("Ngày vào làm không hợp lệ. Định dạng phải là dd/MM/yyyy");
            return;
        }
       java.sql.Date sqlNgayVaoLam = new java.sql.Date(ngayVaoLam.getTime());

 
        preparedStatement.setString(1, tenNV);
        preparedStatement.setString(2, diaChi);
        preparedStatement.setString(3, sdt);
        preparedStatement.setString(4, chucVu);
        preparedStatement.setDouble(5, luongCoBan);
         preparedStatement.setDate(6, sqlNgayVaoLam);
        preparedStatement.setInt(7, maNV);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Sửa thông tin nhân viên thành công!");
        } else {
            System.out.println("Không thể sửa thông tin nhân viên.");
        }

    } catch (SQLException e) {
        System.out.println("Lỗi khi sửa thông tin nhân viên: " + e.getMessage());
        e.printStackTrace(); // In ra stack trace để dễ dàng debug
    }
}

// Phương thức kiểm tra nhân viên tồn tại
private boolean kiemTraNhanVienTonTai(int maNV, Connection connection) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT COUNT(*) FROM NhanVien WHERE MaNV=?")) {
        preparedStatement.setInt(1, maNV);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
    }
    return false;
}
public void xoaNhanVien() {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM NhanVien WHERE MaNV=?")) {

            System.out.print("Nhập mã nhân viên cần xóa: ");
            int maNV = scanner.nextInt();
            preparedStatement.setInt(1, maNV);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa nhân viên thành công!");
            } else {
                System.out.println("Không tìm thấy nhân viên có mã " + maNV);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void hienThiDanhSachNhanVien() {
        try (Connection connection = dbConnection.getConnection(); 
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM NhanVien")) {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        while (resultSet.next()) {
            int maNV = resultSet.getInt("MaNV");
            String tenNV = resultSet.getString("TenNV");
            String diaChi = resultSet.getString("DiaChi");
            String sdt = resultSet.getString("SDT");
            String chucVu = resultSet.getString("ChucVu");
            double luongCoBan = resultSet.getDouble("LuongCoBan");
            Date ngayVaoLam = resultSet.getDate("NgayVaoLam");

            NhanVien nhanVien = new NhanVien(maNV, tenNV, diaChi, sdt, chucVu, luongCoBan, ngayVaoLam);
            dsNhanVien.add(nhanVien);
        }

        if (dsNhanVien.isEmpty()) {
            System.out.println("Không có nhân viên nào.");
        } else {
            System.out.println("DANH SÁCH NHÂN VIÊN:");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-30s | %-15s | %-20s | %-15s | %-15s |\n",
                    "Mã NV", "Tên nhân viên", "Địa chỉ", "SĐT", "Chức vụ", "Lương cơ bản", "Ngày vào làm");
            System.out.println("---------------------------------------------------------------------------------");
            for (NhanVien nv : dsNhanVien) {
                System.out.printf("| %-5d | %-20s | %-30s | %-15s | %-20s | %-15.0f | %-15s |\n",
                        nv.getMaNV(), nv.getTenNV(), nv.getDiaChi(), nv.getSdt(), nv.getChucVu(),
                        nv.getLuongCoBan(), new SimpleDateFormat("dd/MM/yyyy").format(nv.getNgayVaoLam()));
            }
            System.out.println("---------------------------------------------------------------------------------");
        }

    } catch (SQLException e) {
        System.out.println("Lỗi khi hiển thị danh sách nhân viên: " + e.getMessage());
    }
}

public void menuQuanLyNhanVien() {
        int luaChon;
        do {
            System.out.println("\nQUẢN LÝ NHÂN VIÊN:");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Sửa nhân viên");
            System.out.println("3. Xóa nhân viên");
            System.out.println("4. Hiển thị danh sách nhân viên");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            switch (luaChon) {
                case 1:
                    themNhanVien();
                    break;
                case 2:
                    suaNhanVien();
                    break;
                case 3:
                    xoaNhanVien();
                    break;
                case 4:
                    hienThiDanhSachNhanVien();
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

