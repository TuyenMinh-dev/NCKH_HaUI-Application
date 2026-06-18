package com.haui.ui;

import com.haui.model.DeTai;
import com.haui.model.TaiLieu;
import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Màn hình Thống kê — hiển thị biểu đồ cột và biểu đồ tròn
 * từ dữ liệu đề tài và tài liệu hiện có trong DataService.
 *
 * Không cần thêm model hay service mới.
 * Chỉ cần gọi AppContext.DATA_SERVICE.layTatCa() là đủ dữ liệu.
 */
public class ThongKePanel extends JPanel {

    public ThongKePanel() {
        setBackground(StyleManager.BG_LIGHT);
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        // ── Header ──────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleManager.BG_LIGHT);
        header.setBorder(new EmptyBorder(28, 30, 14, 30));

        JLabel title = StyleManager.createSectionLabel("Thống kê");
        header.add(title, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // ── Content (scroll) ────────────────────────────────────
        JPanel content = new JPanel();
        content.setBackground(StyleManager.BG_LIGHT);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(0, 30, 30, 30));

        // Hàng 1: biểu đồ cột (trạng thái) + biểu đồ tròn (tiến độ)
        JPanel row1 = new JPanel(new GridLayout(1, 2, 18, 0));
        row1.setOpaque(false);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        row1.add(buildBieuDoCot());
        row1.add(buildBieuDoTron());
        content.add(row1);

        content.add(Box.createRigidArea(new Dimension(0, 18)));

        // Hàng 2: biểu đồ cột loại tài liệu + bảng xếp hạng tiến độ
        JPanel row2 = new JPanel(new GridLayout(1, 2, 18, 0));
        row2.setOpaque(false);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        row2.add(buildBieuDoTaiLieu());
        row2.add(buildBangXepHang());
        content.add(row2);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setBackground(StyleManager.BG_LIGHT);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    // ────────────────────────────────────────────────────────────
    // 1. Biểu đồ cột — số đề tài theo trạng thái
    // ────────────────────────────────────────────────────────────
    private JPanel buildBieuDoCot() {
        JPanel card = buildCard("📊  Đề tài theo trạng thái");

        // Đếm số lượng từng trạng thái
        String[] trangThais = {"Mới", "Đang thực hiện", "Tạm dừng", "Hoàn thành"};
        Color[]  colors     = {
                StyleManager.HAUI_BLUE,
                StyleManager.HAUI_ORANGE,
                StyleManager.WARNING_AMBER,
                StyleManager.SUCCESS_GREEN
        };

        int[] counts = new int[4];
        for (DeTai dt : AppContext.DATA_SERVICE.layTatCa()) {
            for (int i = 0; i < trangThais.length; i++) {
                if (trangThais[i].equals(dt.getTrangThai())) {
                    counts[i]++;
                    break;
                }
            }
        }

        // Panel vẽ biểu đồ bằng Graphics2D
        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                // Bật anti-aliasing cho chữ mượt hơn
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int paddingLeft  = 40;
                int paddingRight = 20;
                int paddingTop   = 20;
                int paddingBot   = 50; // chỗ cho nhãn dưới cột

                int chartW = w - paddingLeft - paddingRight;
                int chartH = h - paddingTop - paddingBot;

                // Tìm max để tính tỉ lệ
                int maxVal = 1;
                for (int c : counts) if (c > maxVal) maxVal = c;

                int barCount   = trangThais.length;
                int barWidth   = chartW / (barCount * 2);  // cột chiếm 1 nửa slot
                int slotWidth  = chartW / barCount;

                // Vẽ đường kẻ ngang mờ
                g2.setColor(new Color(220, 225, 235));
                g2.setStroke(new BasicStroke(0.8f));
                for (int i = 1; i <= 4; i++) {
                    int y = paddingTop + chartH - (chartH * i / 4);
                    g2.drawLine(paddingLeft, y, paddingLeft + chartW, y);
                    // Nhãn trục Y
                    g2.setFont(StyleManager.FONT_SMALL);
                    g2.setColor(StyleManager.TEXT_SECONDARY);
                    g2.drawString(String.valueOf(maxVal * i / 4), 4, y + 4);
                    g2.setColor(new Color(220, 225, 235));
                }

                // Vẽ từng cột
                for (int i = 0; i < barCount; i++) {
                    int barH = counts[i] == 0 ? 0 : (int) ((double) counts[i] / maxVal * chartH);
                    int x    = paddingLeft + i * slotWidth + (slotWidth - barWidth) / 2;
                    int y    = paddingTop + chartH - barH;

                    // Cột
                    g2.setColor(colors[i]);
                    g2.fillRoundRect(x, y, barWidth, barH, 6, 6);

                    // Số trên đầu cột
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    g2.setColor(StyleManager.TEXT_PRIMARY);
                    String valStr = String.valueOf(counts[i]);
                    FontMetrics fm = g2.getFontMetrics();
                    int valX = x + (barWidth - fm.stringWidth(valStr)) / 2;
                    g2.drawString(valStr, valX, y - 4);

                    // Nhãn dưới cột (xuống dòng nếu dài)
                    g2.setFont(StyleManager.FONT_SMALL);
                    g2.setColor(StyleManager.TEXT_SECONDARY);
                    String label = trangThais[i];
                    fm = g2.getFontMetrics();
                    int labelX = x + (barWidth - fm.stringWidth(label)) / 2;
                    g2.drawString(label, labelX, paddingTop + chartH + 18);
                }

                // Đường trục X
                g2.setColor(new Color(200, 210, 225));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(paddingLeft, paddingTop + chartH,
                        paddingLeft + chartW, paddingTop + chartH);
            }
        };
        chart.setOpaque(false);

        card.add(chart, BorderLayout.CENTER);
        return card;
    }

    // ────────────────────────────────────────────────────────────
    // 2. Biểu đồ tròn — tỉ lệ hoàn thành
    // ────────────────────────────────────────────────────────────
    private JPanel buildBieuDoTron() {
        JPanel card = buildCard("🥧  Tỉ lệ hoàn thành");

        int tong      = AppContext.DATA_SERVICE.tongDeTai();
        int hoanThanh = AppContext.DATA_SERVICE.tongHoanThanh();
        int chuaXong  = tong - hoanThanh;

        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Kích thước hình tròn
                int diameter = Math.min(w, h) - 60;
                int cx = w / 2 - diameter / 2;
                int cy = 20;

                if (tong == 0) {
                    // Không có dữ liệu — vẽ vòng tròn xám và thông báo
                    g2.setColor(new Color(220, 225, 235));
                    g2.fillOval(cx, cy, diameter, diameter);
                    g2.setFont(StyleManager.FONT_BODY);
                    g2.setColor(StyleManager.TEXT_SECONDARY);
                    String msg = "Chưa có dữ liệu";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(msg, (w - fm.stringWidth(msg)) / 2,
                            cy + diameter / 2 + 5);
                    return;
                }

                // Tính góc
                int angleHoanThanh = (int) Math.round(360.0 * hoanThanh / tong);
                int angleChuaXong  = 360 - angleHoanThanh;

                // Vẽ phần chưa xong (xám nhạt)
                g2.setColor(new Color(220, 225, 235));
                g2.fillArc(cx, cy, diameter, diameter, 90, 360);

                // Vẽ phần hoàn thành (xanh lá)
                g2.setColor(StyleManager.SUCCESS_GREEN);
                g2.fillArc(cx, cy, diameter, diameter, 90, -angleHoanThanh);

                // Vẽ lỗ giữa (donut effect)
                int hole = diameter / 3;
                int hx = cx + (diameter - hole) / 2;
                int hy = cy + (diameter - hole) / 2;
                g2.setColor(getBackground() == null ? Color.WHITE : getParent().getBackground());
                g2.fillOval(hx, hy, hole, hole);

                // % ở giữa
                int pct = (int) Math.round(100.0 * hoanThanh / tong);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                g2.setColor(StyleManager.TEXT_PRIMARY);
                String pctStr = pct + "%";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(pctStr, (w - fm.stringWidth(pctStr)) / 2,
                        cy + diameter / 2 + 7);

                // Chú thích bên dưới
                int legendY = cy + diameter + 20;

                // Hoàn thành
                g2.setColor(StyleManager.SUCCESS_GREEN);
                g2.fillRoundRect(w / 2 - 80, legendY, 12, 12, 4, 4);
                g2.setFont(StyleManager.FONT_SMALL);
                g2.setColor(StyleManager.TEXT_PRIMARY);
                g2.drawString("Hoàn thành: " + hoanThanh, w / 2 - 64, legendY + 11);

                // Chưa xong
                g2.setColor(new Color(220, 225, 235));
                g2.fillRoundRect(w / 2 - 80, legendY + 20, 12, 12, 4, 4);
                g2.setColor(StyleManager.TEXT_PRIMARY);
                g2.drawString("Chưa xong: " + chuaXong, w / 2 - 64, legendY + 31);
            }
        };
        chart.setOpaque(false);

        card.add(chart, BorderLayout.CENTER);
        return card;
    }

    // ────────────────────────────────────────────────────────────
    // 3. Biểu đồ cột — số tài liệu theo loại
    // ────────────────────────────────────────────────────────────
    private JPanel buildBieuDoTaiLieu() {
        JPanel card = buildCard("📁  Tài liệu theo loại");

        String[] loais  = {"PDF", "Word", "Excel", "PowerPoint", "Khác"};
        Color[]  colors = {
                new Color(239, 68, 68),   // đỏ — PDF
                new Color(29, 78, 216),   // xanh dương — Word
                new Color(21, 128, 61),   // xanh lá — Excel
                new Color(154, 52, 18),   // cam — PowerPoint
                new Color(100, 116, 139)  // xám — Khác
        };

        int[] counts = new int[loais.length];
        for (TaiLieu tl : AppContext.DATA_SERVICE.layTatCaTaiLieu()) {
            for (int i = 0; i < loais.length; i++) {
                if (loais[i].equals(tl.getLoai())) {
                    counts[i]++;
                    break;
                }
            }
        }

        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                int pL = 40, pR = 20, pT = 20, pB = 50;
                int chartW = w - pL - pR;
                int chartH = h - pT - pB;

                int maxVal = 1;
                for (int c : counts) if (c > maxVal) maxVal = c;

                int barCount  = loais.length;
                int slotWidth = chartW / barCount;
                int barWidth  = slotWidth / 2;

                // Đường kẻ ngang
                g2.setColor(new Color(220, 225, 235));
                g2.setStroke(new BasicStroke(0.8f));
                for (int i = 1; i <= 4; i++) {
                    int y = pT + chartH - (chartH * i / 4);
                    g2.drawLine(pL, y, pL + chartW, y);
                    g2.setFont(StyleManager.FONT_SMALL);
                    g2.setColor(StyleManager.TEXT_SECONDARY);
                    g2.drawString(String.valueOf(maxVal * i / 4), 4, y + 4);
                    g2.setColor(new Color(220, 225, 235));
                }

                for (int i = 0; i < barCount; i++) {
                    int barH = counts[i] == 0 ? 0
                            : (int) ((double) counts[i] / maxVal * chartH);
                    int x = pL + i * slotWidth + (slotWidth - barWidth) / 2;
                    int y = pT + chartH - barH;

                    g2.setColor(colors[i]);
                    g2.fillRoundRect(x, y, barWidth, barH, 6, 6);

                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    g2.setColor(StyleManager.TEXT_PRIMARY);
                    String valStr = String.valueOf(counts[i]);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(valStr, x + (barWidth - fm.stringWidth(valStr)) / 2, y - 4);

                    g2.setFont(StyleManager.FONT_SMALL);
                    g2.setColor(StyleManager.TEXT_SECONDARY);
                    fm = g2.getFontMetrics();
                    g2.drawString(loais[i],
                            x + (barWidth - fm.stringWidth(loais[i])) / 2,
                            pT + chartH + 18);
                }

                g2.setColor(new Color(200, 210, 225));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(pL, pT + chartH, pL + chartW, pT + chartH);
            }
        };
        chart.setOpaque(false);

        card.add(chart, BorderLayout.CENTER);
        return card;
    }

    // ────────────────────────────────────────────────────────────
    // 4. Bảng xếp hạng — top đề tài theo tiến độ
    // ────────────────────────────────────────────────────────────
    private JPanel buildBangXepHang() {
        JPanel card = buildCard("🏆  Xếp hạng tiến độ");

        // Lấy danh sách và sort từ cao xuống thấp
        List<DeTai> dsTatCa = new ArrayList<>(AppContext.DATA_SERVICE.layTatCa());
        dsTatCa.sort((a, b) -> b.getTienDo() - a.getTienDo());

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        if (dsTatCa.isEmpty()) {
            JLabel empty = new JLabel("Chưa có đề tài nào");
            empty.setFont(StyleManager.FONT_BODY);
            empty.setForeground(StyleManager.TEXT_SECONDARY);
            list.add(empty);
        } else {
            // Lấy tối đa 5 đề tài
            int limit = Math.min(5, dsTatCa.size());
            for (int i = 0; i < limit; i++) {
                DeTai dt = dsTatCa.get(i);
                list.add(buildRankRow(i + 1, dt));
                if (i < limit - 1) {
                    JSeparator sep = new JSeparator();
                    sep.setForeground(new Color(240, 243, 248));
                    sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
                    list.add(sep);
                }
            }
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    /** Một hàng trong bảng xếp hạng */
    private JPanel buildRankRow(int rank, DeTai dt) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(8, 0, 8, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Số thứ hạng
        Color rankColor;
        if      (rank == 1) rankColor = new Color(251, 191, 36); // vàng
        else if (rank == 2) rankColor = new Color(180, 188, 200); // bạc
        else if (rank == 3) rankColor = new Color(205, 127, 50);  // đồng
        else                rankColor = StyleManager.TEXT_SECONDARY;

        JLabel rankLbl = new JLabel(String.valueOf(rank));
        rankLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rankLbl.setForeground(rankColor);
        rankLbl.setPreferredSize(new Dimension(24, 24));
        rankLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // Tên đề tài + MSSV
        JPanel textCol = new JPanel(new GridLayout(2, 1, 0, 2));
        textCol.setOpaque(false);

        JLabel nameLbl = new JLabel(dt.getTenDT());
        nameLbl.setFont(StyleManager.FONT_BODY);
        nameLbl.setForeground(StyleManager.TEXT_PRIMARY);

        JLabel subLbl = new JLabel(dt.getMssv() + "  •  " + dt.getGiangVien());
        subLbl.setFont(StyleManager.FONT_SMALL);
        subLbl.setForeground(StyleManager.TEXT_SECONDARY);

        textCol.add(nameLbl);
        textCol.add(subLbl);

        // Badge tiến độ
        int pct = dt.getTienDo();
        Color pColor = pct == 100 ? StyleManager.SUCCESS_GREEN
                : pct >= 50  ? StyleManager.HAUI_ORANGE
                : StyleManager.DANGER_RED;

        JLabel pctLbl = new JLabel(pct + "%");
        pctLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        pctLbl.setForeground(Color.WHITE);
        pctLbl.setOpaque(true);
        pctLbl.setBackground(pColor);
        pctLbl.setHorizontalAlignment(SwingConstants.CENTER);
        pctLbl.setPreferredSize(new Dimension(44, 22));
        pctLbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

        row.add(rankLbl,  BorderLayout.WEST);
        row.add(textCol,  BorderLayout.CENTER);
        row.add(pctLbl,   BorderLayout.EAST);
        return row;
    }

    // ────────────────────────────────────────────────────────────
    // Helper — tạo card trắng có tiêu đề, dùng chung cho mọi chart
    // ────────────────────────────────────────────────────────────
    private JPanel buildCard(String titleText) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel hdr = new JLabel(titleText);
        hdr.setFont(StyleManager.FONT_SUBTITLE);
        hdr.setForeground(StyleManager.TEXT_PRIMARY);
        card.add(hdr, BorderLayout.NORTH);

        return card;
    }

    /** Gọi khi bấm nút "Làm mới" — rebuild toàn bộ giao diện */
    public void lamMoi() {
        removeAll();
        initUI();
        revalidate();
        repaint();
    }
}