package com.haui.ui;

import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AboutPanel extends JPanel {

    public AboutPanel() {
        setBackground(StyleManager.BG_LIGHT);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleManager.BG_LIGHT);
        header.setBorder(new EmptyBorder(28, 30, 14, 30));
        header.add(StyleManager.createSectionLabel("Giới thiệu"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(StyleManager.BG_LIGHT);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(10, 30, 30, 30));

        JPanel card = new JPanel(new BorderLayout(0, 20));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(36, 40, 36, 40)
        ));

        // Logo section
        JLabel logo = new JLabel("🎓", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel appName = new JLabel("Hệ thống Hỗ trợ Nghiên cứu Khoa học", SwingConstants.CENTER);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appName.setForeground(StyleManager.TEXT_PRIMARY);

        JLabel university = new JLabel("Trường Đại học Công nghiệp Hà Nội (HaUI)", SwingConstants.CENTER);
        university.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        university.setForeground(StyleManager.HAUI_BLUE);

        JLabel version = new JLabel("Phiên bản 2.0  –  2025", SwingConstants.CENTER);
        version.setFont(StyleManager.FONT_SMALL);
        version.setForeground(StyleManager.TEXT_SECONDARY);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 235, 245));

        JPanel topSection = new JPanel();
        topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.add(logo);
        topSection.add(Box.createRigidArea(new Dimension(0, 10)));
        topSection.add(appName);
        topSection.add(Box.createRigidArea(new Dimension(0, 6)));
        topSection.add(university);
        topSection.add(Box.createRigidArea(new Dimension(0, 4)));
        topSection.add(version);
        topSection.add(Box.createRigidArea(new Dimension(0, 20)));
        topSection.add(sep);

        card.add(topSection, BorderLayout.NORTH);

        // Features list
        JPanel featurePanel = new JPanel(new GridLayout(2, 3, 16, 16));
        featurePanel.setOpaque(false);

        String[][] features = {
            {"📚", "Quản lý đề tài", "Thêm, sửa, xóa, tìm kiếm đề tài NCKH"},
            {"📊", "Theo dõi tiến độ", "Cập nhật tiến độ theo từng giai đoạn"},
            {"📁", "Kho tài liệu", "Lưu trữ và quản lý tài liệu nghiên cứu"},
            {"📋", "Xuất báo cáo", "Xuất báo cáo chi tiết dạng văn bản"},
            {"🔍", "Tìm kiếm nhanh", "Tìm kiếm theo mã, tên, giảng viên"},
            {"💾", "Lưu tự động", "Dữ liệu lưu tự động dạng JSON"},
        };

        for (String[] f : features) {
            JPanel fCard = new JPanel(new BorderLayout(10, 6));
            fCard.setBackground(new Color(249, 250, 252));
            fCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(232, 236, 245), 1, true),
                    new EmptyBorder(16, 16, 16, 16)
            ));
            JLabel icon = new JLabel(f[0]);
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            JLabel name = new JLabel(f[1]);
            name.setFont(new Font("Segoe UI", Font.BOLD, 13));
            name.setForeground(StyleManager.TEXT_PRIMARY);
            JLabel desc = new JLabel("<html>" + f[2] + "</html>");
            desc.setFont(StyleManager.FONT_SMALL);
            desc.setForeground(StyleManager.TEXT_SECONDARY);
            JPanel textCol = new JPanel(new GridLayout(2, 1, 0, 3));
            textCol.setOpaque(false);
            textCol.add(name);
            textCol.add(desc);
            fCard.add(icon, BorderLayout.WEST);
            fCard.add(textCol, BorderLayout.CENTER);
            featurePanel.add(fCard);
        }

        card.add(featurePanel, BorderLayout.CENTER);

        // Tech stack
        JLabel techLabel = new JLabel("Công nghệ: Java 17  •  Swing  •  FlatLaf  •  Gson", SwingConstants.CENTER);
        techLabel.setFont(StyleManager.FONT_SMALL);
        techLabel.setForeground(StyleManager.TEXT_SECONDARY);
        card.add(techLabel, BorderLayout.SOUTH);

        content.add(card);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }
}
