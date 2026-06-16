package com.haui.model;

public class TaiLieu {
    private String id;
    private String tenFile;
    private String duongDan;
    private String loai;      // "PDF", "Word", "Excel", "Khác"
    private String maDeTai;
    private String ngayThem;
    private String ghiChu;

    public TaiLieu() {}

    public TaiLieu(String id, String tenFile, String duongDan, String loai, String maDeTai, String ngayThem) {
        this.id = id;
        this.tenFile = tenFile;
        this.duongDan = duongDan;
        this.loai = loai;
        this.maDeTai = maDeTai;
        this.ngayThem = ngayThem;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTenFile() { return tenFile; }
    public void setTenFile(String tenFile) { this.tenFile = tenFile; }
    public String getDuongDan() { return duongDan; }
    public void setDuongDan(String duongDan) { this.duongDan = duongDan; }
    public String getLoai() { return loai != null ? loai : "Khác"; }
    public void setLoai(String loai) { this.loai = loai; }
    public String getMaDeTai() { return maDeTai != null ? maDeTai : ""; }
    public void setMaDeTai(String maDeTai) { this.maDeTai = maDeTai; }
    public String getNgayThem() { return ngayThem != null ? ngayThem : ""; }
    public void setNgayThem(String ngayThem) { this.ngayThem = ngayThem; }
    public String getGhiChu() { return ghiChu != null ? ghiChu : ""; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
