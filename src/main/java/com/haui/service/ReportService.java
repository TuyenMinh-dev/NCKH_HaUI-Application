package com.haui.service;

import com.haui.model.DeTai;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportService {

    public String export(DeTai dt) {
        String fileName = "BaoCao_" + dt.getMaDT() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            String time = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            writer.write("╔══════════════════════════════════════╗\n");
            writer.write("║       BÁO CÁO NCKH - HAUI            ║\n");
            writer.write("╚══════════════════════════════════════╝\n\n");
            writer.write("Ngày xuất: " + time + "\n");
            writer.write("─────────────────────────────────────────\n\n");
            writer.write("THÔNG TIN ĐỀ TÀI\n");
            writer.write("  Mã đề tài  : " + dt.getMaDT() + "\n");
            writer.write("  Tên đề tài : " + dt.getTenDT() + "\n");
            writer.write("  Giảng viên : " + dt.getGiangVien() + "\n");
            writer.write("  MSSV       : " + dt.getMssv() + "\n");
            if (!dt.getHoTenSV().isEmpty())
                writer.write("  Sinh viên  : " + dt.getHoTenSV() + "\n");
            writer.write("  Trạng thái : " + dt.getTrangThai() + "\n");
            if (!dt.getNgayBatDau().isEmpty())
                writer.write("  Bắt đầu   : " + dt.getNgayBatDau() + "\n");
            if (!dt.getNgayKetThuc().isEmpty())
                writer.write("  Kết thúc  : " + dt.getNgayKetThuc() + "\n");
            writer.write("\nMÔ TẢ\n  " + (dt.getMoTa().isEmpty() ? "(Chưa có)" : dt.getMoTa()) + "\n");
            writer.write("\nTIẾN ĐỘ THỰC HIỆN: " + dt.getTienDo() + "%\n");
            writer.write("  [" + (dt.isDeCuong() ? "✓" : " ") + "] Viết đề cương\n");
            writer.write("  [" + (dt.isThuThapDuLieu() ? "✓" : " ") + "] Thu thập dữ liệu\n");
            writer.write("  [" + (dt.isCodeDemo() ? "✓" : " ") + "] Code demo\n");
            writer.write("  [" + (dt.isVietBaoCao() ? "✓" : " ") + "] Viết báo cáo\n");
            writer.write("\n─────────────────────────────────────────\n");
            writer.write("Xuất bởi Hệ thống NCKH HaUI \n");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fileName;
    }
}
