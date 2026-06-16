package com.haui.ui;

import com.haui.model.DeTai;
import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProgressPanel extends JPanel {

    private JComboBox<String> cboDeTai;
    private JCheckBox chkDeCuong, chkThuThap, chkCodeDemo, chkBaoCao;
    private JProgressBar progressBar;
    private JLabel lblPct, lblTenDT, lblGV, lblTrangThai;
    private JPanel stepsPanel;
    private DeTai current;

    public ProgressPanel() {
        setBackground(StyleManager.BG_LIGHT);
        setLayout(new BorderLayout());
        initUI();
        loadCombo();
    }

    private void initUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleManager.BG_LIGHT);
        header.setBorder(new EmptyBorder(28, 30, 14, 30));
        header.add(StyleManager.createSectionLabel("Theo dõi Tiến độ"), BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Main
        JPanel main = new JPanel(new BorderLayout(20, 0));
        main.setBackground(StyleManager.BG_LIGHT);
        main.setBorder(new EmptyBorder(0, 30, 30, 30));

        main.add(buildLeftPanel(), BorderLayout.WEST);
        main.add(buildRightPanel(), BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    private JPanel buildLeftPanel() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setPreferredSize(new Dimension(320, 0));

        JPanel card = new JPanel(new BorderLayout(0, 16));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(22, 22, 22, 22)
        ));

        // Combo
        JPanel topRow = new JPanel(new BorderLayout(8, 0));
        topRow.setOpaque(false);
        JLabel lbl = new JLabel("Chọn đề tài:");
        lbl.setFont(StyleManager.FONT_SMALL);
        lbl.setForeground(StyleManager.TEXT_SECONDARY);
        cboDeTai = new JComboBox<>();
        cboDeTai.setFont(StyleManager.FONT_BODY);
        cboDeTai.setPreferredSize(new Dimension(1, 34));
        topRow.add(lbl, BorderLayout.NORTH);
        topRow.add(cboDeTai, BorderLayout.SOUTH);
        card.add(topRow, BorderLayout.NORTH);

        // Info card
        JPanel infoCard = new JPanel(new GridLayout(4, 1, 0, 6));
        infoCard.setOpaque(false);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(243, 244, 246), 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        lblTenDT     = buildInfoRow(infoCard, "Tên đề tài:", "—");
        lblGV        = buildInfoRow(infoCard, "Giảng viên:", "—");
        lblTrangThai = buildInfoRow(infoCard, "Trạng thái:", "—");

        JPanel pctRow = new JPanel(new BorderLayout());
        pctRow.setOpaque(false);
        JLabel pctTitle = new JLabel("Tiến độ tổng:");
        pctTitle.setFont(StyleManager.FONT_SMALL);
        pctTitle.setForeground(StyleManager.TEXT_SECONDARY);
        lblPct = new JLabel("0%");
        lblPct.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblPct.setForeground(StyleManager.HAUI_ORANGE);
        pctRow.add(pctTitle, BorderLayout.NORTH);
        pctRow.add(lblPct, BorderLayout.CENTER);
        infoCard.add(pctRow);

        card.add(infoCard, BorderLayout.CENTER);

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(1, 14));
        progressBar.setForeground(StyleManager.HAUI_ORANGE);
        progressBar.setBackground(new Color(243, 244, 246));
        card.add(progressBar, BorderLayout.SOUTH);

        wrap.add(card, BorderLayout.CENTER);
        cboDeTai.addActionListener(e -> hienThiTienDo());
        return wrap;
    }

    private JLabel buildInfoRow(JPanel parent, String labelText, String value) {
        JPanel row = new JPanel(new GridLayout(2, 1));
        row.setOpaque(false);
        JLabel title = new JLabel(labelText);
        title.setFont(StyleManager.FONT_SMALL);
        title.setForeground(StyleManager.TEXT_SECONDARY);
        JLabel val = new JLabel(value);
        val.setFont(StyleManager.FONT_BODY);
        val.setForeground(StyleManager.TEXT_PRIMARY);
        row.add(title);
        row.add(val);
        parent.add(row);
        return val;
    }

    private JPanel buildRightPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 18));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(22, 22, 22, 22)
        ));

        JLabel title = new JLabel("Các giai đoạn thực hiện");
        title.setFont(StyleManager.FONT_SUBTITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);
        card.add(title, BorderLayout.NORTH);

        stepsPanel = new JPanel(new GridLayout(4, 1, 0, 14));
        stepsPanel.setOpaque(false);

        chkDeCuong  = buildStepItem("1. Viết đề cương",     "Lập kế hoạch nghiên cứu, xây dựng đề cương chi tiết");
        chkThuThap  = buildStepItem("2. Thu thập dữ liệu",  "Tìm kiếm, tổng hợp tài liệu và dữ liệu nghiên cứu");
        chkCodeDemo = buildStepItem("3. Code demo",          "Lập trình, xây dựng sản phẩm/demo minh chứng");
        chkBaoCao   = buildStepItem("4. Viết báo cáo",      "Hoàn thiện báo cáo tổng kết kết quả nghiên cứu");

        card.add(stepsPanel, BorderLayout.CENTER);

        // Save button
        JButton btnLuu = StyleManager.createStyledButton("💾  Lưu tiến độ", StyleManager.HAUI_BLUE, Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(160, 38));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setOpaque(false);
        btnRow.add(btnLuu);
        card.add(btnRow, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> capNhat());

        return card;
    }

    private JCheckBox buildStepItem(String title, String desc) {
        JPanel item = new JPanel(new BorderLayout(12, 0));
        item.setBackground(new Color(249, 250, 252));
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(232, 236, 245), 1, true),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JCheckBox chk = new JCheckBox();
        chk.setOpaque(false);

        JPanel textBlock = new JPanel(new GridLayout(2, 1, 0, 3));
        textBlock.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLbl.setForeground(StyleManager.TEXT_PRIMARY);

        JLabel descLbl = new JLabel(desc);
        descLbl.setFont(StyleManager.FONT_SMALL);
        descLbl.setForeground(StyleManager.TEXT_SECONDARY);

        textBlock.add(titleLbl);
        textBlock.add(descLbl);

        item.add(chk, BorderLayout.WEST);
        item.add(textBlock, BorderLayout.CENTER);

        // Done indicator
        JLabel doneLbl = new JLabel("✓ Xong");
        doneLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        doneLbl.setForeground(StyleManager.SUCCESS_GREEN);
        doneLbl.setVisible(false);
        item.add(doneLbl, BorderLayout.EAST);

        chk.addActionListener(e -> {
            doneLbl.setVisible(chk.isSelected());
            item.setBackground(chk.isSelected()
                    ? new Color(240, 253, 244)
                    : new Color(249, 250, 252));
            updateProgressBar();
        });

        stepsPanel.add(item);
        return chk;
    }

    private void updateProgressBar() {
        int done = 0;
        if (chkDeCuong.isSelected())  done++;
        if (chkThuThap.isSelected())  done++;
        if (chkCodeDemo.isSelected()) done++;
        if (chkBaoCao.isSelected())   done++;
        int pct = done * 25;
        progressBar.setValue(pct);
        lblPct.setText(pct + "%");
        Color c = pct == 100 ? StyleManager.SUCCESS_GREEN
                : pct >= 50  ? StyleManager.HAUI_ORANGE
                : StyleManager.DANGER_RED;
        progressBar.setForeground(c);
        lblPct.setForeground(c);
    }

    public void loadCombo() {
        cboDeTai.removeAllItems();
        for (DeTai dt : AppContext.DATA_SERVICE.layTatCa())
            cboDeTai.addItem(dt.getMaDT() + " – " + dt.getTenDT());
        hienThiTienDo();
    }

    private void hienThiTienDo() {
        String sel = (String) cboDeTai.getSelectedItem();
        if (sel == null) { current = null; return; }
        String ma = sel.contains("–") ? sel.split("–")[0].trim() : sel.trim();
        current = AppContext.DATA_SERVICE.timTheoMa(ma);
        if (current == null) return;

        lblTenDT.setText(current.getTenDT());
        lblGV.setText(current.getGiangVien());
        lblTrangThai.setText(current.getTrangThai());

        chkDeCuong.setSelected(current.isDeCuong());
        chkThuThap.setSelected(current.isThuThapDuLieu());
        chkCodeDemo.setSelected(current.isCodeDemo());
        chkBaoCao.setSelected(current.isVietBaoCao());

        // Refresh step item backgrounds
        refreshStepColors();
        updateProgressBar();
    }

    private void refreshStepColors() {
        JCheckBox[] chks = {chkDeCuong, chkThuThap, chkCodeDemo, chkBaoCao};
        for (JCheckBox chk : chks) {
            JPanel item = (JPanel) chk.getParent();
            item.setBackground(chk.isSelected() ? new Color(240, 253, 244) : new Color(249, 250, 252));
            JLabel doneLbl = (JLabel) item.getComponent(2);
            doneLbl.setVisible(chk.isSelected());
        }
    }

    private void capNhat() {
        if (current == null) { JOptionPane.showMessageDialog(this, "Chưa chọn đề tài!"); return; }
        current.setDeCuong(chkDeCuong.isSelected());
        current.setThuThapDuLieu(chkThuThap.isSelected());
        current.setCodeDemo(chkCodeDemo.isSelected());
        current.setVietBaoCao(chkBaoCao.isSelected());
        AppContext.JSON_SERVICE.save(AppContext.DATA_SERVICE.layTatCa());
        AppContext.MAIN_FRAME.refreshAll();
        JOptionPane.showMessageDialog(this, "Đã lưu tiến độ!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
