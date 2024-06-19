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
public class ThongKe {
    private Scanner scanner = new Scanner(System.in);
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance(); 

     public void thongKeDoanhThuTheoNgay() {
    System.out.print("Nhập ngày cần thống kê doanh thu (dd/MM/yyyy): ");
    String ngayStr = scanner.nextLine();

    try {
        Date ngay = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(ngayStr);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT SUM(TongTien) AS TongDoanhThu " +
                             "FROM HoaDon " +
                             "WHERE NgayBan = ?")) {

            preparedStatement.setDate(1, new java.sql.Date(ngay.getTime()));

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
    } catch (ParseException e) {
        System.out.println("Ngày không hợp lệ. Định dạng phải là dd/MM/yyyy");
    } catch (SQLException e) {
        System.out.println("Lỗi khi thống kê doanh thu: " + e.getMessage());
    }
}


   public void thongKeDoanhThuTheoThang() {
    System.out.print("Nhập năm cần thống kê doanh thu (yyyy): ");
    int nam;
    try {
        nam = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới còn lại
    } catch (NumberFormatException e) {
        System.out.println("Năm phải là số nguyên.");
        return;
    }

    try (Connection connection = dbConnection.getConnection()) {
        for (int thang = 1; thang <= 12; thang++) {
            String sql = "SELECT SUM(TongTien) AS TongDoanhThu " +
                         "FROM HoaDon " +
                         "WHERE YEAR(NgayBan) = ? AND MONTH(NgayBan) = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, nam);
                preparedStatement.setInt(2, thang);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double tongDoanhThu = resultSet.getDouble("TongDoanhThu");
                        if (tongDoanhThu > 0) {
                            System.out.println("Tháng " + thang + "/" + nam + ": " + tongDoanhThu);
                        } else {
                            System.out.println("Tháng " + thang + "/" + nam + ": Không có doanh thu");
                        }
                    } else {
                        System.out.println("Không có dữ liệu doanh thu cho tháng " + thang + "/" + nam);
                    }
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi thống kê doanh thu: " + e.getMessage());
    }
}


    public void thongKeDoanhThuTheoNam() {
    System.out.print("Nhập năm cần thống kê doanh thu (yyyy): ");
    int nam;
    try {
        nam = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới còn lại
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
            System.out.println("---------------------------------");
            System.out.printf("| %-10s | %-15s |\n", "Tháng", "Tổng doanh thu");
            System.out.println("---------------------------------");

            boolean coDuLieu = false;
            while (resultSet.next()) {
                int thang = resultSet.getInt("Thang");
                double tongDoanhThu = resultSet.getDouble("TongDoanhThu");
                System.out.printf("| %-10d | %-15.0f |\n", thang, tongDoanhThu);
                coDuLieu = true;
            }
            if (!coDuLieu) {
                System.out.println("| Không có dữ liệu doanh thu cho năm " + nam + "        |");
            }
            System.out.println("---------------------------------");

        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi thống kê doanh thu: " + e.getMessage());
    }
}

    public void thongKeSanPhamBanChayNhat() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT TOP 5 sp.TenSP, SUM(cthd.SoLuong) AS TongSoLuongBan " +
                     "FROM ChiTietHoaDon cthd " +
                     "JOIN SanPham sp ON cthd.MaSP = sp.MaSP " +
                     "GROUP BY sp.TenSP " +
                     "ORDER BY TongSoLuongBan DESC")) {

            System.out.println("TOP 5 SẢN PHẨM BÁN CHẠY NHẤT:");
            System.out.println("---------------------------------");
            System.out.printf("| %-30s | %-15s |\n", "Tên sản phẩm", "Số lượng bán");
            System.out.println("---------------------------------");
            while (resultSet.next()) {
                String tenSP = resultSet.getString("TenSP");
                int tongSoLuongBan = resultSet.getInt("TongSoLuongBan");
                System.out.printf("| %-30s | %-15d |\n", tenSP, tongSoLuongBan);
            }
            System.out.println("---------------------------------");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thống kê sản phẩm bán chạy nhất: " + e.getMessage());
        }
    }

    public void thongKeNhanVienBanHangXuatSacNhat() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT TOP 3 nv.TenNV, COUNT(hd.MaHD) AS SoLuongHoaDon " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN HoaDon hd ON nv.MaNV = hd.MaNV " +
                     "GROUP BY nv.TenNV " +
                     "ORDER BY SoLuongHoaDon DESC")) {

            System.out.println("TOP 3 NHÂN VIÊN BÁN HÀNG XUẤT SẮC NHẤT:");
            System.out.println("---------------------------------");
            System.out.printf("| %-20s | %-15s |\n", "Tên nhân viên", "Số lượng hóa đơn");
            System.out.println("---------------------------------");
            while (resultSet.next()) {
                String tenNV = resultSet.getString("TenNV");
                int soLuongHoaDon = resultSet.getInt("SoLuongHoaDon");
                System.out.printf("| %-20s | %-15d |\n", tenNV, soLuongHoaDon);
            }
            System.out.println("---------------------------------");

        } catch (SQLException e) {
            System.out.println("Lỗi khi thống kê nhân viên bán hàng xuất sắc nhất: " + e.getMessage());
        }
    }

    public void menuThongKe() {
        int luaChon;
        do {
            System.out.println("\nTHỐNG KÊ:");
            System.out.println("1. Doanh thu theo ngày");
            System.out.println("2. Doanh thu theo tháng");
            System.out.println("3. Doanh thu theo năm");
            System.out.println("4. Sản phẩm bán chạy nhất");
            System.out.println("5. Nhân viên bán hàng xuất sắc nhất");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            switch (luaChon) {
                case 1:
                    thongKeDoanhThuTheoNgay();
                    break;
                case 2:
                    thongKeDoanhThuTheoThang();
                    break;
                case 3:
                    thongKeDoanhThuTheoNam();
                    break;
                case 4:
                    thongKeSanPhamBanChayNhat();
                    break;
                case 5:
                    thongKeNhanVienBanHangXuatSacNhat();
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
