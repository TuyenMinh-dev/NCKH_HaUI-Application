/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui.ui;

import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Tập hợp các Custom Renderer dùng chung cho các bảng (JTable)
 * trong toàn bộ ứng dụng.
 */
public class TableRenderers {

    /** Tô màu nhãn trạng thái đề tài: Mới / Đang thực hiện / Tạm dừng / Hoàn thành */
    public static class TrangThaiRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            String s = lbl.getText();
            if (s.contains("Hoàn thành")) {
                lbl.setBackground(new Color(220, 252, 231));
                lbl.setForeground(new Color(21, 128, 61));
            } else if (s.contains("Đang")) {
                lbl.setBackground(new Color(255, 237, 213));
                lbl.setForeground(new Color(154, 52, 18));
            } else if (s.contains("Tạm")) {
                lbl.setBackground(new Color(254, 249, 195));
                lbl.setForeground(new Color(113, 63, 18));
            } else {
                lbl.setBackground(new Color(219, 234, 254));
                lbl.setForeground(new Color(30, 64, 175));
            }
            if (sel) lbl.setBackground(lbl.getBackground().darker());
            return lbl;
        }
    }

    /** Hiển thị tiến độ (%) dưới dạng JProgressBar có màu theo mức độ */
    public static class TienDoRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            int pct = val instanceof Integer ? (Integer) val : 0;
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(pct);
            bar.setStringPainted(true);
            bar.setString(pct + "%");
            Color fg = pct == 100 ? StyleManager.SUCCESS_GREEN
                     : pct >= 50  ? StyleManager.HAUI_ORANGE
                     : StyleManager.DANGER_RED;
            bar.setForeground(fg);
            bar.setBackground(new Color(243, 244, 246));
            bar.setBorder(new EmptyBorder(4, 6, 4, 6));
            return bar;
        }
    }

    /** Tô màu nhãn loại file: PDF / Word / Excel / PowerPoint / Khác */
    public static class LoaiRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            String s = lbl.getText();
            switch (s) {
                case "PDF"        -> { lbl.setBackground(new Color(254, 226, 226)); lbl.setForeground(new Color(153, 27, 27)); }
                case "Word"       -> { lbl.setBackground(new Color(219, 234, 254)); lbl.setForeground(new Color(29, 78, 216)); }
                case "Excel"      -> { lbl.setBackground(new Color(220, 252, 231)); lbl.setForeground(new Color(21, 128, 61)); }
                case "PowerPoint" -> { lbl.setBackground(new Color(255, 237, 213)); lbl.setForeground(new Color(154, 52, 18)); }
                default           -> { lbl.setBackground(new Color(243, 244, 246)); lbl.setForeground(new Color(75, 85, 99)); }
            }
            if (sel) lbl.setBackground(lbl.getBackground().darker());
            return lbl;
        }
    }
}
