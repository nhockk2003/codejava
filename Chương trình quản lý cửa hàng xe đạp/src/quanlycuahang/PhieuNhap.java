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

public class PhieuNhap {
    private int maPN;
    private Date ngayNhap;
    private NhaCungCap nhaCungCap;
    private double tongTien;
    private List<ChiTietPhieuNhap> dsChiTietPhieuNhap; // Danh sách chi tiết phiếu nhập

    // Constructor
    public PhieuNhap(int maPN, Date ngayNhap, NhaCungCap nhaCungCap) {
        this.maPN = maPN;
        this.ngayNhap = ngayNhap;
        this.nhaCungCap = nhaCungCap;
        this.tongTien = 0; // Khởi tạo tổng tiền là 0
        this.dsChiTietPhieuNhap = new ArrayList<>(); // Khởi tạo danh sách chi tiết phiếu nhập
    }

    // Getters
    public int getMaPN() {
        return maPN;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public List<ChiTietPhieuNhap> getDsChiTietPhieuNhap() {
        return dsChiTietPhieuNhap;
    }

    // Setters
    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    
    public void tinhTongTien() {
        tongTien = 0;
        for (ChiTietPhieuNhap ctpn : dsChiTietPhieuNhap) {
            tongTien += ctpn.getSoLuong() * ctpn.getDonGia();
        }
    }

    
    public void themChiTietPhieuNhap(ChiTietPhieuNhap ctpn) {
        dsChiTietPhieuNhap.add(ctpn);
        tinhTongTien(); 
    }
}


