package com.haui.ui;

import com.haui.model.DeTai;
import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {

    private JLabel lblTong, lblDangLam, lblHoanThanh, lblTiLe;
    private JPanel recentPanel;

    public DashboardPanel() {
        setBackground(StyleManager.BG_LIGHT);
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleManager.BG_LIGHT);
        header.setBorder(new EmptyBorder(28, 30, 10, 30));
        JLabel title = new JLabel("Dashboard");
        title.setFont(StyleManager.FONT_TITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel dateLbl = new JLabel("Hôm nay: " + today);
        dateLbl.setFont(StyleManager.FONT_SMALL);
        dateLbl.setForeground(StyleManager.TEXT_SECONDARY);
        header.add(title, BorderLayout.WEST);
        header.add(dateLbl, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Main scroll
        JPanel main = new JPanel();
        main.setBackground(StyleManager.BG_LIGHT);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(0, 30, 30, 30));

        // Stat cards
        JPanel cardRow = new JPanel(new GridLayout(1, 4, 18, 0));
        cardRow.setOpaque(false);
        cardRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        lblTong       = buildStatCard(cardRow, "Tổng đề tài",   "0", StyleManager.HAUI_BLUE,   "📚");
        lblDangLam    = buildStatCard(cardRow, "Đang thực hiện","0", StyleManager.HAUI_ORANGE,  "⚡");
        lblHoanThanh  = buildStatCard(cardRow, "Hoàn thành",    "0", StyleManager.SUCCESS_GREEN,"✅");
        lblTiLe       = buildStatCard(cardRow, "Tỉ lệ HT",      "0%",StyleManager.ACCENT_PURPLE,"📊");

        main.add(cardRow);
        main.add(Box.createRigidArea(new Dimension(0, 24)));

        // Bottom: Deadlines + Recent topics
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 18, 0));
        bottomRow.setOpaque(false);
        bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        bottomRow.add(buildDeadlinePanel());
        bottomRow.add(buildRecentPanel());

        main.add(bottomRow);

        JScrollPane scroll = new JScrollPane(main);
        scroll.setBorder(null);
        scroll.setBackground(StyleManager.BG_LIGHT);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JLabel buildStatCard(JPanel parent, String title, String value, Color accent, String icon) {
        JPanel card = new JPanel(new BorderLayout(10, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(18, 20, 18, 20)
        ));

        // Left accent bar
        JPanel bar = new JPanel();
        bar.setBackground(accent);
        bar.setPreferredSize(new Dimension(5, 0));

        // Icon
        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        // Text stack
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(StyleManager.FONT_SMALL);
        titleLbl.setForeground(StyleManager.TEXT_SECONDARY);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(StyleManager.FONT_CARD_NUM);
        valueLbl.setForeground(accent);

        JPanel text = new JPanel(new GridLayout(2, 1, 0, 2));
        text.setOpaque(false);
        text.add(titleLbl);
        text.add(valueLbl);

        card.add(bar, BorderLayout.WEST);
        card.add(iconLbl, BorderLayout.EAST);
        card.add(text, BorderLayout.CENTER);

        parent.add(card);
        return valueLbl;
    }

    private JPanel buildDeadlinePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JLabel hdr = new JLabel("📅  Lịch quan trọng");
        hdr.setFont(StyleManager.FONT_SUBTITLE);
        hdr.setForeground(StyleManager.TEXT_PRIMARY);
        panel.add(hdr, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        String[][] deadlines = {
                {"30/10", "Hạn đăng ký đề tài",       "🔴"},
                {"15/12", "Nghiệm thu cấp khoa",        "🟡"},
                {"20/12", "Nộp báo cáo cuối kỳ",        "🔴"},
                {"05/01", "Bảo vệ đề tài",              "🟠"},
                {"15/01", "Nộp điểm về Phòng NCKH",     "🟢"},
        };

        for (String[] dl : deadlines) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(6, 0, 6, 0));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

            JLabel dot = new JLabel(dl[2]);
            dot.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

            JLabel date = new JLabel(dl[0]);
            date.setFont(new Font("Segoe UI", Font.BOLD, 12));
            date.setForeground(StyleManager.HAUI_ORANGE);
            date.setPreferredSize(new Dimension(48, 20));

            JLabel content = new JLabel(dl[1]);
            content.setFont(StyleManager.FONT_BODY);
            content.setForeground(StyleManager.TEXT_PRIMARY);

            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
            left.setOpaque(false);
            left.add(dot);
            left.add(date);

            row.add(left, BorderLayout.WEST);
            row.add(content, BorderLayout.CENTER);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(240, 243, 248));

            list.add(row);
            list.add(sep);
        }

        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildRecentPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JLabel hdr = new JLabel("📋  Đề tài gần đây");
        hdr.setFont(StyleManager.FONT_SUBTITLE);
        hdr.setForeground(StyleManager.TEXT_PRIMARY);
        panel.add(hdr, BorderLayout.NORTH);

        recentPanel = new JPanel();
        recentPanel.setOpaque(false);
        recentPanel.setLayout(new BoxLayout(recentPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(recentPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    public void refreshData() {
        int tong = AppContext.DATA_SERVICE.tongDeTai();
        int dangLam = AppContext.DATA_SERVICE.tongDangLam();
        int hoanThanh = AppContext.DATA_SERVICE.tongHoanThanh();
        int tile = tong == 0 ? 0 : (int) Math.round(hoanThanh * 100.0 / tong);

        lblTong.setText(String.valueOf(tong));
        lblDangLam.setText(String.valueOf(dangLam));
        lblHoanThanh.setText(String.valueOf(hoanThanh));
        lblTiLe.setText(tile + "%");

        // Refresh recent list
        recentPanel.removeAll();
        int count = 0;
        for (DeTai dt : AppContext.DATA_SERVICE.layTatCa()) {
            if (count++ >= 5) break;
            recentPanel.add(buildRecentRow(dt));
            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(240, 243, 248));
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
            recentPanel.add(sep);
        }
        if (count == 0) {
            JLabel empty = new JLabel("Chưa có đề tài nào");
            empty.setFont(StyleManager.FONT_BODY);
            empty.setForeground(StyleManager.TEXT_SECONDARY);
            recentPanel.add(empty);
        }
        recentPanel.revalidate();
        recentPanel.repaint();
    }

    private JPanel buildRecentRow(DeTai dt) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(7, 0, 7, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        // Progress circle label
        int pct = dt.getTienDo();
        Color pColor = pct == 100 ? StyleManager.SUCCESS_GREEN
                     : pct >= 50  ? StyleManager.HAUI_ORANGE
                     : StyleManager.DANGER_RED;

        JLabel pLbl = new JLabel(pct + "%");
        pLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        pLbl.setForeground(Color.WHITE);
        pLbl.setOpaque(true);
        pLbl.setBackground(pColor);
        pLbl.setHorizontalAlignment(SwingConstants.CENTER);
        pLbl.setPreferredSize(new Dimension(44, 22));
        pLbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

        JLabel nameLbl = new JLabel(dt.getTenDT());
        nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameLbl.setForeground(StyleManager.TEXT_PRIMARY);

        JLabel gvLbl = new JLabel("GV: " + dt.getGiangVien());
        gvLbl.setFont(StyleManager.FONT_SMALL);
        gvLbl.setForeground(StyleManager.TEXT_SECONDARY);

        JPanel textCol = new JPanel(new GridLayout(2, 1));
        textCol.setOpaque(false);
        textCol.add(nameLbl);
        textCol.add(gvLbl);

        row.add(pLbl, BorderLayout.EAST);
        row.add(textCol, BorderLayout.CENTER);
        return row;
    }
}
