package com.haui.model;

public class Deadline {
    private String ngay;
    private String noiDung;
    private String loai; // "Quan trọng", "Thường", "Cảnh báo"

    public Deadline() {}

    public Deadline(String ngay, String noiDung, String loai) {
        this.ngay = ngay;
        this.noiDung = noiDung;
        this.loai = loai;
    }

    public String getNgay() { return ngay; }
    public void setNgay(String ngay) { this.ngay = ngay; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    public String getLoai() { return loai != null ? loai : "Thường"; }
    public void setLoai(String loai) { this.loai = loai; }
}
