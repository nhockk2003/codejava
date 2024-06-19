/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlycuahangxedap;

/**
 *
 * @author nguye
 */
public class SanPham {
    
    private int maSP;
    private String tenSP;
    private String loaiSP;
    private String hangSX;
    private double giaNhap;
    private double giaBan;
    private int soLuongTon;

    // Constructor
    public SanPham(int maSP, String tenSP, String loaiSP, String hangSX, double giaNhap, double giaBan, int soLuongTon) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.hangSX = hangSX;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
    }

    // Getters
    public int getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public String getHangSX() {
        return hangSX;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    // Setters
    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
}


