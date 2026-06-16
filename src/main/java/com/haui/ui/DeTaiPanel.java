package com.haui.ui;

import com.haui.model.DeTai;
import com.haui.service.DataService;
import com.haui.service.ReportService;
import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DeTaiPanel extends JPanel {

    private JTextField txtMa, txtTen, txtGV, txtMSSV, txtHoTen, txtNgayBD, txtNgayKT, txtSearch;
    private JTextArea  txtMoTa;
    private JComboBox<String> cboTrangThai;
    private JTable table;
    private DefaultTableModel model;
    private final DataService service;

    private static final String[] TRANG_THAI = {"Mới", "Đang thực hiện", "Tạm dừng", "Hoàn thành"};

    public DeTaiPanel() {
        service = AppContext.DATA_SERVICE;
        setBackground(StyleManager.BG_LIGHT);
        setLayout(new BorderLayout());
        initUI();
        loadTable(null);
    }

    private void initUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleManager.BG_LIGHT);
        header.setBorder(new EmptyBorder(28, 30, 14, 30));
        JLabel title = StyleManager.createSectionLabel("Quản lý Đề tài");
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Split: form left, table right
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(370);
        split.setDividerSize(2);
        split.setBorder(null);
        split.setBackground(StyleManager.BG_LIGHT);

        split.setLeftComponent(buildForm());
        split.setRightComponent(buildTablePanel());
        add(split, BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(StyleManager.BG_LIGHT);
        wrap.setBorder(new EmptyBorder(0, 30, 20, 12));

        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel formTitle = new JLabel("Thông tin đề tài");
        formTitle.setFont(StyleManager.FONT_SUBTITLE);
        formTitle.setForeground(StyleManager.TEXT_PRIMARY);
        card.add(formTitle, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.weightx = 1;

        txtMa      = new JTextField();
        txtTen     = new JTextField();
        txtGV      = new JTextField();
        txtMSSV    = new JTextField();
        txtHoTen   = new JTextField();
        txtNgayBD  = new JTextField();
        txtNgayKT  = new JTextField();
        txtMoTa    = new JTextArea(3, 1);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        cboTrangThai = new JComboBox<>(TRANG_THAI);

        Object[][] rows = {
            {"Mã đề tài *",   txtMa},
            {"Tên đề tài *",  txtTen},
            {"Giảng viên HD *",txtGV},
            {"MSSV *",         txtMSSV},
            {"Họ tên SV",      txtHoTen},
            {"Ngày bắt đầu",   txtNgayBD},
            {"Ngày kết thúc",  txtNgayKT},
            {"Trạng thái",     cboTrangThai},
        };

        for (int i = 0; i < rows.length; i++) {
            gbc.gridy = i * 2;     gbc.gridx = 0;
            JLabel lbl = new JLabel((String) rows[i][0]);
            lbl.setFont(StyleManager.FONT_SMALL);
            lbl.setForeground(StyleManager.TEXT_SECONDARY);
            fields.add(lbl, gbc);

            gbc.gridy = i * 2 + 1; gbc.gridx = 0;
            Component comp = (Component) rows[i][1];
            comp.setPreferredSize(new Dimension(1, 32));
            fields.add(comp, gbc);
        }

        // Mo ta
        gbc.gridy = rows.length * 2; gbc.gridx = 0;
        JLabel moTaLbl = new JLabel("Mô tả");
        moTaLbl.setFont(StyleManager.FONT_SMALL);
        moTaLbl.setForeground(StyleManager.TEXT_SECONDARY);
        fields.add(moTaLbl, gbc);

        gbc.gridy = rows.length * 2 + 1; gbc.weighty = 0;
        JScrollPane taScroll = new JScrollPane(txtMoTa);
        taScroll.setPreferredSize(new Dimension(1, 72));
        fields.add(taScroll, gbc);

        card.add(fields, BorderLayout.CENTER);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnRow.setOpaque(false);

        JButton btnThem    = StyleManager.createStyledButton("＋ Thêm",    StyleManager.HAUI_BLUE,     Color.WHITE);
        JButton btnSua     = StyleManager.createStyledButton("✎ Sửa",     StyleManager.HAUI_ORANGE,   Color.WHITE);
        JButton btnXoa     = StyleManager.createStyledButton("✕ Xóa",     StyleManager.DANGER_RED,    Color.WHITE);
        JButton btnMoiLai  = StyleManager.createStyledButton("↺ Làm mới", new Color(100,116,139),     Color.WHITE);
        JButton btnExport  = StyleManager.createStyledButton("↓ Xuất BC", StyleManager.SUCCESS_GREEN, Color.WHITE);

        btnThem.setPreferredSize(new Dimension(110, 34));
        btnSua.setPreferredSize(new Dimension(100, 34));
        btnXoa.setPreferredSize(new Dimension(100, 34));
        btnMoiLai.setPreferredSize(new Dimension(110, 34));
        btnExport.setPreferredSize(new Dimension(110, 34));

        btnRow.add(btnThem);
        btnRow.add(btnSua);
        btnRow.add(btnXoa);
        btnRow.add(btnMoiLai);
        btnRow.add(btnExport);

        card.add(btnRow, BorderLayout.SOUTH);
        wrap.add(card, BorderLayout.CENTER);

        // Events
        btnThem.addActionListener(e -> themDeTai());
        btnSua.addActionListener(e -> suaDeTai());
        btnXoa.addActionListener(e -> xoaDeTai());
        btnMoiLai.addActionListener(e -> clearForm());
        btnExport.addActionListener(e -> xuatBaoCao());

        return wrap;
    }

    private JPanel buildTablePanel() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(StyleManager.BG_LIGHT);
        wrap.setBorder(new EmptyBorder(0, 6, 20, 30));

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));

        // Search bar
        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setOpaque(false);
        JLabel searchLbl = new JLabel("🔍");
        searchLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(1, 34));
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm theo mã, tên, giảng viên...");
        searchRow.add(searchLbl, BorderLayout.WEST);
        searchRow.add(txtSearch, BorderLayout.CENTER);
        card.add(searchRow, BorderLayout.NORTH);

        // Table
        String[] cols = {"Mã DT", "Tên đề tài", "Giảng viên", "MSSV", "SV", "Trạng thái", "Tiến độ"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(StyleManager.FONT_BODY);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(243, 245, 250));
        table.setSelectionBackground(new Color(230, 87, 37, 40));
        table.setSelectionForeground(StyleManager.TEXT_PRIMARY);

        // Header style
        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));
        th.setBackground(new Color(248, 250, 254));
        th.setForeground(StyleManager.TEXT_SECONDARY);
        th.setPreferredSize(new Dimension(0, 38));

        // Column widths
        int[] widths = {70, 220, 130, 80, 110, 110, 80};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Trang thai renderer
        table.getColumnModel().getColumn(5).setCellRenderer(new TrangThaiRenderer());
        // Progress renderer
        table.getColumnModel().getColumn(6).setCellRenderer(new TienDoRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        card.add(scroll, BorderLayout.CENTER);

        wrap.add(card, BorderLayout.CENTER);

        // Events
        table.getSelectionModel().addListSelectionListener(e -> hienThiLenForm());
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { loadTable(txtSearch.getText()); }
            public void removeUpdate(DocumentEvent e) { loadTable(txtSearch.getText()); }
            public void changedUpdate(DocumentEvent e) { loadTable(txtSearch.getText()); }
        });

        return wrap;
    }

    private void themDeTai() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String gv = txtGV.getText().trim();
        String mssv = txtMSSV.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || gv.isEmpty() || mssv.isEmpty()) {
            showWarn("Vui lòng nhập đầy đủ các trường có dấu (*)");
            return;
        }

        DeTai dt = new DeTai(ma, ten, gv, mssv);
        dt.setHoTenSV(txtHoTen.getText().trim());
        dt.setNgayBatDau(txtNgayBD.getText().trim());
        dt.setNgayKetThuc(txtNgayKT.getText().trim());
        dt.setMoTa(txtMoTa.getText().trim());
        dt.setTrangThai((String) cboTrangThai.getSelectedItem());

        if (service.themDeTai(dt)) {
            loadTable(null); clearForm();
            showInfo("Thêm đề tài thành công!");
            AppContext.MAIN_FRAME.refreshAll();
        } else {
            showWarn("Mã đề tài \"" + ma + "\" đã tồn tại!");
        }
    }

    private void suaDeTai() {
        String ma = txtMa.getText().trim();
        if (ma.isEmpty()) { showWarn("Chọn đề tài cần sửa từ bảng."); return; }

        DeTai dt = new DeTai(ma, txtTen.getText().trim(), txtGV.getText().trim(), txtMSSV.getText().trim());
        dt.setHoTenSV(txtHoTen.getText().trim());
        dt.setNgayBatDau(txtNgayBD.getText().trim());
        dt.setNgayKetThuc(txtNgayKT.getText().trim());
        dt.setMoTa(txtMoTa.getText().trim());
        dt.setTrangThai((String) cboTrangThai.getSelectedItem());

        if (service.suaDeTai(dt)) {
            loadTable(null);
            showInfo("Cập nhật thành công!");
            AppContext.MAIN_FRAME.refreshAll();
        } else {
            showWarn("Không tìm thấy đề tài!");
        }
    }

    private void xoaDeTai() {
        String ma = txtMa.getText().trim();
        if (ma.isEmpty()) { showWarn("Chọn đề tài cần xóa từ bảng."); return; }
        int r = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa đề tài \"" + ma + "\" không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r != JOptionPane.YES_OPTION) return;
        service.xoaDeTai(ma);
        loadTable(null); clearForm();
        AppContext.MAIN_FRAME.refreshAll();
    }

    private void xuatBaoCao() {
        String ma = txtMa.getText().trim();
        DeTai dt = service.timTheoMa(ma);
        if (dt == null) { showWarn("Chọn đề tài từ bảng trước khi xuất báo cáo."); return; }
        String file = new ReportService().export(dt);
        if (file != null)
            showInfo("Đã xuất báo cáo ra file:\n" + file);
    }

    private void loadTable(String keyword) {
        model.setRowCount(0);
        java.util.Collection<DeTai> data = (keyword == null || keyword.isBlank())
                ? service.layTatCa()
                : service.timKiem(keyword);
        for (DeTai dt : data) {
            model.addRow(new Object[]{
                    dt.getMaDT(), dt.getTenDT(), dt.getGiangVien(),
                    dt.getMssv(), dt.getHoTenSV(), dt.getTrangThai(), dt.getTienDo()
            });
        }
    }

    private void hienThiLenForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String ma = model.getValueAt(row, 0).toString();
        DeTai dt = service.timTheoMa(ma);
        if (dt == null) return;
        txtMa.setText(dt.getMaDT());
        txtTen.setText(dt.getTenDT());
        txtGV.setText(dt.getGiangVien());
        txtMSSV.setText(dt.getMssv());
        txtHoTen.setText(dt.getHoTenSV());
        txtNgayBD.setText(dt.getNgayBatDau());
        txtNgayKT.setText(dt.getNgayKetThuc());
        txtMoTa.setText(dt.getMoTa());
        cboTrangThai.setSelectedItem(dt.getTrangThai());
    }

    private void clearForm() {
        txtMa.setText(""); txtTen.setText(""); txtGV.setText("");
        txtMSSV.setText(""); txtHoTen.setText(""); txtNgayBD.setText("");
        txtNgayKT.setText(""); txtMoTa.setText("");
        cboTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    // ========== Custom Renderers ==========

    static class TrangThaiRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(
                JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            String s = lbl.getText();
            if (s.contains("Hoàn thành")) { lbl.setBackground(new Color(220,252,231)); lbl.setForeground(new Color(21,128,61)); }
            else if (s.contains("Đang"))  { lbl.setBackground(new Color(255,237,213)); lbl.setForeground(new Color(154,52,18)); }
            else if (s.contains("Tạm"))   { lbl.setBackground(new Color(254,249,195)); lbl.setForeground(new Color(113,63,18)); }
            else                           { lbl.setBackground(new Color(219,234,254)); lbl.setForeground(new Color(30,64,175)); }
            if (sel) { lbl.setBackground(lbl.getBackground().darker()); }
            return lbl;
        }
    }

    static class TienDoRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(
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
            bar.setBackground(new Color(243,244,246));
            bar.setBorder(new EmptyBorder(4,6,4,6));
            return bar;
        }
    }
}
