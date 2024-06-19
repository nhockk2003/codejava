/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author nguye
 */
;

public class HoaDon {
    private int maHD;
    private Date ngayBan;
    private NhanVien nhanVien;
    private double tongTien;
    private List<ChiTietHoaDon> dsChiTietHoaDon; 

  
    public HoaDon(int maHD, Date ngayBan, NhanVien nhanVien) {
        this.maHD = maHD;
        this.ngayBan = ngayBan;
        this.nhanVien = nhanVien;
        this.tongTien = 0; 
        this.dsChiTietHoaDon = new ArrayList<>(); 
    }

    public int getMaHD() {
        return maHD;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public List<ChiTietHoaDon> getDsChiTietHoaDon() {
        return dsChiTietHoaDon;
    }


    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }


    public void tinhTongTien() {
        tongTien = 0;
        for (ChiTietHoaDon cthd : dsChiTietHoaDon) {
            tongTien += cthd.getSoLuong() * cthd.getDonGia();
        }
    }

   
    public void themChiTietHoaDon(ChiTietHoaDon cthd) {
        dsChiTietHoaDon.add(cthd);
        tinhTongTien(); 
    }
}
