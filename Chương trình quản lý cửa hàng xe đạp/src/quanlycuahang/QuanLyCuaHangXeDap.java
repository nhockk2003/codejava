package quanlycuahangxedap;

import java.util.Scanner;

public class QuanLyCuaHangXeDap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuanLyNhanVien qlnv = new QuanLyNhanVien();
        QuanLySanPham qlsp = new QuanLySanPham();
        QuanLyDoanhThu qldt = new QuanLyDoanhThu();
        QuanLyNhaCungCap qlncc = new QuanLyNhaCungCap();
        ThongKe tk = new ThongKe();

        int luaChon;
        do {
            System.out.println("\nMENU CHÍNH:");
            System.out.println("1. Quản lý nhân viên");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Quản lý doanh thu");
            System.out.println("4. Quản lý nhà cung cấp");
            System.out.println("5. Thống kê");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            luaChon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới còn lại

            switch (luaChon) {
                case 1:
                    qlnv.menuQuanLyNhanVien();
                    break;
                case 2:
                    qlsp.menuQuanLySanPham();
                    break;
                case 3:
                    qldt.menuQuanLyDoanhThu();
                    break;
                case 4:
                    qlncc.menuQuanLyNhaCungCap();
                    break;
                case 5:
                    tk.menuThongKe();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (luaChon != 0);
    }
}

