/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;

/**
 *
 * @author nguye
 */
public class ChiTietPhieuNhap {
    private PhieuNhap phieuNhap;
    private SanPham sanPham;
    private int soLuong;
    private double donGia;

    // Constructor
    public ChiTietPhieuNhap(PhieuNhap phieuNhap, SanPham sanPham, int soLuong, double donGia) {
        this.phieuNhap = phieuNhap;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getters
    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    // Setters
    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    // Phương thức tính thành tiền
    public double tinhThanhTien() {
        return soLuong * donGia;
    }
}


