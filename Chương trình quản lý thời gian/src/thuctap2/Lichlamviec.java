/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thuctap2;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author nguye
 */
public class Lichlamviec {
               
    private String tenCongViec;  
    private LocalTime thoiGianBatDau; 
    private LocalTime thoiGianKetThuc; 
    private LocalDate ngay; 

    public Lichlamviec(String tenCongViec, LocalTime thoiGianBatDau, LocalTime thoiGianKetThuc, LocalDate ngay) {
       
        this.tenCongViec = tenCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ngay = ngay;
    }

   
    

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public void setThoiGianBatDau(LocalTime thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public void setThoiGianKetThuc(LocalTime thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

  

    public String getTenCongViec() {
        return tenCongViec;
    }

    public LocalTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public LocalTime getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public LocalDate getNgay() {
        return ngay;
    }
    
    
}
