/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author nguye
 */
public class QuanLySanPham {

    private Scanner scanner = new Scanner(System.in);
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance(); 
    private SanPham sanPham;

    public void themSanPham() {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO SanPham (TenSP, LoaiSP, HangSX, GiaNhap, GiaBan, SoLuongTon) VALUES (?, ?, ?, ?, ?, ?)")) {

            System.out.print("Nhập tên sản phẩm: ");
            String tenSP = scanner.nextLine();
            System.out.print("Nhập loại sản phẩm: ");
            String loaiSP = scanner.nextLine();
            System.out.print("Nhập hãng sản xuất: ");
            String hangSX = scanner.nextLine();
            System.out.print("Nhập giá nhập: ");
            double giaNhap = scanner.nextDouble();
            System.out.print("Nhập giá bán: ");
            double giaBan = scanner.nextDouble();
            System.out.print("Nhập số lượng tồn: ");
            int soLuongTon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            preparedStatement.setString(1, tenSP);
            preparedStatement.setString(2, loaiSP);
            preparedStatement.setString(3, hangSX);
            preparedStatement.setDouble(4, giaNhap);
            preparedStatement.setDouble(5, giaBan);
            preparedStatement.setInt(6, soLuongTon);

            preparedStatement.executeUpdate();
            System.out.println("Thêm sản phẩm thành công!");

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
public void hienThiDanhSachSanPham() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SanPham")) {

            List<SanPham> dsSanPham = new ArrayList<>();
            while (resultSet.next()) {
                int maSP = resultSet.getInt("MaSP");
                String tenSP = resultSet.getString("TenSP");
                String loaiSP = resultSet.getString("LoaiSP");
                String hangSX = resultSet.getString("HangSX");
                double giaNhap = resultSet.getDouble("GiaNhap");
                double giaBan = resultSet.getDouble("GiaBan");
                int soLuongTon = resultSet.getInt("SoLuongTon");

                SanPham sanPham = new SanPham(maSP, tenSP, loaiSP, hangSX, giaNhap, giaBan, soLuongTon);
                dsSanPham.add(sanPham);
            }

            if (dsSanPham.isEmpty()) {
                System.out.println("Không có sản phẩm nào.");
            } else {
                System.out.println("DANH SÁCH SẢN PHẨM:");
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.printf("| %-5s | %-30s | %-15s | %-15s | %-12s | %-12s | %-10s |\n",
                        "Mã SP", "Tên sản phẩm", "Loại", "Hãng SX", "Giá nhập", "Giá bán", "SL Tồn");
                System.out.println("---------------------------------------------------------------------------------------");
                for (SanPham sp : dsSanPham) {
                    System.out.printf("| %-5d | %-30s | %-15s | %-15s | %-12.0f | %-12.0f | %-10d |\n",
                            sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getHangSX(),
                            sp.getGiaNhap(), sp.getGiaBan(), sp.getSoLuongTon());
                }
                System.out.println("---------------------------------------------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi hiển thị danh sách sản phẩm: " + e.getMessage());
        }
    }
public void xoaSanPham() {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM SanPham WHERE MaSP=?")) {

            System.out.print("Nhập mã sản phẩm cần xóa: ");
            int maSP = scanner.nextInt();
            preparedStatement.setInt(1, maSP);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa sản phẩm thành công!");
            } else {
                System.out.println("Không tìm thấy sản phẩm có mã " + maSP);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
public void suaSanPham() {
    try (Connection connection = dbConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
                 "UPDATE SanPham SET TenSP=?, LoaiSP=?, HangSX=?, GiaNhap=?, GiaBan=?, SoLuongTon=? WHERE MaSP=?")) {

        System.out.print("Nhập mã sản phẩm cần sửa: ");
        int maSP;
        try {
            maSP = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại
        } catch (NumberFormatException e) {
            System.out.println("Mã sản phẩm phải là số nguyên.");
            return;
        }

        // Kiểm tra xem sản phẩm có tồn tại không
        if (!kiemTraSanPhamTonTai(maSP, connection)) {
            System.out.println("Không tìm thấy sản phẩm có mã " + maSP);
            return;
        }

        System.out.print("Nhập tên sản phẩm mới: ");
        String tenSP = scanner.nextLine();

        System.out.print("Nhập loại sản phẩm mới: ");
        String loaiSP = scanner.nextLine();

        System.out.print("Nhập hãng sản xuất mới: ");
        String hangSX = scanner.nextLine();

        System.out.print("Nhập giá nhập mới: ");
        double giaNhap;
        try {
            giaNhap = scanner.nextDouble();
        } catch (NumberFormatException e) {
            System.out.println("Giá nhập phải là số.");
            return;
        }

        System.out.print("Nhập giá bán mới: ");
        double giaBan;
        try {
            giaBan = scanner.nextDouble();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại
        } catch (NumberFormatException e) {
            System.out.println("Giá bán phải là số.");
            return;
        }

        // Lấy số lượng tồn kho hiện tại của sản phẩm
        int soLuongTonHienTai = laySoLuongTonHienTai(maSP, connection);

        System.out.print("Nhập số lượng tồn mới (hoặc nhập 0 để giữ nguyên): ");
        int soLuongTonMoi;
        try {
            soLuongTonMoi = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại
        } catch (NumberFormatException e) {
            System.out.println("Số lượng tồn phải là số nguyên.");
            return;
        }

        // Nếu người dùng nhập 0, giữ nguyên số lượng tồn hiện tại
        int soLuongTon = (soLuongTonMoi == 0) ? soLuongTonHienTai : soLuongTonMoi;

        // Cập nhật thông tin sản phẩm
        preparedStatement.setString(1, tenSP);
        preparedStatement.setString(2, loaiSP);
        preparedStatement.setString(3, hangSX);
        preparedStatement.setDouble(4, giaNhap);
        preparedStatement.setDouble(5, giaBan);
        preparedStatement.setInt(6, soLuongTon);
        preparedStatement.setInt(7, maSP);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Sửa thông tin sản phẩm thành công!");
        } else {
            System.out.println("Không thể sửa thông tin sản phẩm.");
        }

    } catch (SQLException e) {
        System.out.println("Lỗi khi sửa thông tin sản phẩm: " + e.getMessage());
    }
}

private boolean kiemTraSanPhamTonTai(int maSP, Connection connection) throws SQLException {
    String sql = "SELECT COUNT(*) FROM SanPham WHERE MaSP = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, maSP);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Trả về true nếu tìm thấy sản phẩm (count > 0)
            }
        }
    }
    return false; // Trả về false nếu không tìm thấy sản phẩm
}


// Phương thức lấy số lượng tồn kho hiện tại
private int laySoLuongTonHienTai(int maSP, Connection connection) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT SoLuongTon FROM SanPham WHERE MaSP=?")) {
        preparedStatement.setInt(1, maSP);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("SoLuongTon");
            }
        }
    }
    return 0; // Nếu không tìm thấy sản phẩm, trả về 0
}
public void timKiemSanPham() {
    System.out.println("\nTÌM KIẾM SẢN PHẨM:");
    System.out.println("1. Theo tên");
    System.out.println("2. Theo loại");
    System.out.println("3. Theo hãng");
    System.out.println("4. Kết hợp các tiêu chí");
    System.out.print("Chọn phương thức tìm kiếm: ");
    int luaChonTimKiem = scanner.nextInt();
    scanner.nextLine(); // Đọc bỏ dòng mới còn lại

    String tenSP = "", loaiSP = "", hangSX = "";
    if (luaChonTimKiem >= 1 && luaChonTimKiem <= 4) {
        if (luaChonTimKiem == 1 || luaChonTimKiem == 4) {
            System.out.print("Nhập tên sản phẩm: ");
            tenSP = scanner.nextLine();
        }
        if (luaChonTimKiem == 2 || luaChonTimKiem == 4) {
            System.out.print("Nhập loại sản phẩm: ");
            loaiSP = scanner.nextLine();
        }
        if (luaChonTimKiem == 3 || luaChonTimKiem == 4) {
            System.out.print("Nhập hãng sản xuất: ");
            hangSX = scanner.nextLine();
        }
    } else {
        System.out.println("Lựa chọn không hợp lệ.");
        return;
    }

    String sql = "SELECT * FROM SanPham WHERE 1=1"; // Luôn đúng để dễ dàng thêm điều kiện
    if (!tenSP.isEmpty()) {
        sql += " AND TenSP LIKE ?";
    }
    if (!loaiSP.isEmpty()) {
        sql += " AND LoaiSP LIKE ?";
    }
    if (!hangSX.isEmpty()) {
        sql += " AND HangSX LIKE ?";
    }

    try (Connection connection = dbConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        int parameterIndex = 1;
        if (!tenSP.isEmpty()) {
            preparedStatement.setString(parameterIndex++, "%" + tenSP + "%");
        }
        if (!loaiSP.isEmpty()) {
            preparedStatement.setString(parameterIndex++, "%" + loaiSP + "%");
        }
        if (!hangSX.isEmpty()) {
            preparedStatement.setString(parameterIndex, "%" + hangSX + "%");
        }

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<SanPham> dsSanPhamTimKiem = new ArrayList<>();
            while (resultSet.next()) {
            
                dsSanPhamTimKiem.add(sanPham);
            }

            if (dsSanPhamTimKiem.isEmpty()) {
                System.out.println("Không tìm thấy sản phẩm nào.");
            } else {
                System.out.println("KẾT QUẢ TÌM KIẾM:");
                hienThiDanhSachSanPham(dsSanPhamTimKiem); // Gọi phương thức hiển thị danh sách
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
    }
}


private void hienThiDanhSachSanPham(List<SanPham> SanPham) {
    try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SanPham")) {

            List<SanPham> dsSanPham = new ArrayList<>();
            while (resultSet.next()) {
                int maSP = resultSet.getInt("MaSP");
                String tenSP = resultSet.getString("TenSP");
                String loaiSP = resultSet.getString("LoaiSP");
                String hangSX = resultSet.getString("HangSX");
                double giaNhap = resultSet.getDouble("GiaNhap");
                double giaBan = resultSet.getDouble("GiaBan");
                int soLuongTon = resultSet.getInt("SoLuongTon");

                SanPham sanPham = new SanPham(maSP, tenSP, loaiSP, hangSX, giaNhap, giaBan, soLuongTon);
                dsSanPham.add(sanPham);
            }

            if (dsSanPham.isEmpty()) {
                System.out.println("Không có sản phẩm nào.");
            } else {
                System.out.println("DANH SÁCH SẢN PHẨM:");
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.printf("| %-5s | %-30s | %-15s | %-15s | %-12s | %-12s | %-10s |\n",
                        "Mã SP", "Tên sản phẩm", "Loại", "Hãng SX", "Giá nhập", "Giá bán", "SL Tồn");
                System.out.println("---------------------------------------------------------------------------------------");
                for (SanPham sp : dsSanPham) {
                    System.out.printf("| %-5d | %-30s | %-15s | %-15s | %-12.0f | %-12.0f | %-10d |\n",
                            sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP(), sp.getHangSX(),
                            sp.getGiaNhap(), sp.getGiaBan(), sp.getSoLuongTon());
                }
                System.out.println("---------------------------------------------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi hiển thị danh sách sản phẩm: " + e.getMessage());
        }
    }


public void menuQuanLySanPham() {
        int luaChon;
        do {
            System.out.println("\nQUẢN LÝ SẢN PHẨM:");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Sửa sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Hiển thị danh sách sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            switch (luaChon) {
                case 1:
                    themSanPham();
                    break;
                case 2:
                    suaSanPham();
                    break;
                case 3:
                    xoaSanPham();
                    break;
                case 4:
                    hienThiDanhSachSanPham();
                    break;
                case 5:
                    timKiemSanPham();
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