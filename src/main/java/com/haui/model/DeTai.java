package com.haui.model;

import java.io.Serializable;

public class DeTai implements Comparable<DeTai>, Serializable {

    private String maDT;
    private String tenDT;
    private String giangVien;
    private String mssv;
    private String hoTenSV;
    private String moTa;
    private String trangThai; // "Mới", "Đang thực hiện", "Hoàn thành", "Tạm dừng"
    private String ngayBatDau;
    private String ngayKetThuc;

    private boolean deCuong;
    private boolean thuThapDuLieu;
    private boolean codeDemo;
    private boolean vietBaoCao;

    public DeTai() {
        this.trangThai = "Mới";
    }

    public DeTai(String maDT, String tenDT, String giangVien, String mssv) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.giangVien = giangVien;
        this.mssv = mssv;
        this.trangThai = "Đang thực hiện";
    }

    public int getTienDo() {
        int done = 0;
        if (deCuong) done++;
        if (thuThapDuLieu) done++;
        if (codeDemo) done++;
        if (vietBaoCao) done++;
        return done * 25;
    }

    @Override
    public int compareTo(DeTai o) {
        return maDT.compareToIgnoreCase(o.maDT);
    }

    public String getMaDT() { return maDT; }
    public void setMaDT(String maDT) { this.maDT = maDT; }
    public String getTenDT() { return tenDT; }
    public void setTenDT(String tenDT) { this.tenDT = tenDT; }
    public String getGiangVien() { return giangVien; }
    public void setGiangVien(String giangVien) { this.giangVien = giangVien; }
    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }
    public String getHoTenSV() { return hoTenSV != null ? hoTenSV : ""; }
    public void setHoTenSV(String hoTenSV) { this.hoTenSV = hoTenSV; }
    public String getMoTa() { return moTa != null ? moTa : ""; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getTrangThai() { return trangThai != null ? trangThai : "Mới"; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getNgayBatDau() { return ngayBatDau != null ? ngayBatDau : ""; }
    public void setNgayBatDau(String ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public String getNgayKetThuc() { return ngayKetThuc != null ? ngayKetThuc : ""; }
    public void setNgayKetThuc(String ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public boolean isDeCuong() { return deCuong; }
    public void setDeCuong(boolean deCuong) { this.deCuong = deCuong; }
    public boolean isThuThapDuLieu() { return thuThapDuLieu; }
    public void setThuThapDuLieu(boolean thuThapDuLieu) { this.thuThapDuLieu = thuThapDuLieu; }
    public boolean isCodeDemo() { return codeDemo; }
    public void setCodeDemo(boolean codeDemo) { this.codeDemo = codeDemo; }
    public boolean isVietBaoCao() { return vietBaoCao; }
    public void setVietBaoCao(boolean vietBaoCao) { this.vietBaoCao = vietBaoCao; }
}
