package com.haui.ui;

import com.haui.model.TaiLieu;
import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TaiLieuPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JLabel lblTong;

    public TaiLieuPanel() {
        setBackground(StyleManager.BG_LIGHT);
        setLayout(new BorderLayout());
        initUI();
        loadTable();
    }

    private void initUI() {
        // North: header + toolbar combined
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(StyleManager.BG_LIGHT);
        north.setBorder(new EmptyBorder(28, 30, 12, 30));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        JLabel title = StyleManager.createSectionLabel("Kho Tài liệu");
        lblTong = new JLabel("0 tài liệu");
        lblTong.setFont(StyleManager.FONT_SMALL);
        lblTong.setForeground(StyleManager.TEXT_SECONDARY);
        headerRow.add(title, BorderLayout.WEST);
        headerRow.add(lblTong, BorderLayout.EAST);

        JPanel toolRow = new JPanel(new BorderLayout(8, 0));
        toolRow.setOpaque(false);
        toolRow.setBorder(new EmptyBorder(12, 0, 0, 0));

        JPanel searchBox = new JPanel(new BorderLayout(6, 0));
        searchBox.setOpaque(false);
        JLabel sIcon = new JLabel("🔍");
        sIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(280, 34));
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm theo tên file, mã đề tài...");
        searchBox.add(sIcon, BorderLayout.WEST);
        searchBox.add(txtSearch, BorderLayout.CENTER);

        JPanel btnArea = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnArea.setOpaque(false);
        JButton btnThem = StyleManager.createStyledButton("＋ Thêm file",  StyleManager.HAUI_BLUE,     Color.WHITE);
        JButton btnMo   = StyleManager.createStyledButton("↗ Mở file",    StyleManager.HAUI_ORANGE,   Color.WHITE);
        JButton btnXoa  = StyleManager.createStyledButton("✕ Xóa",        StyleManager.DANGER_RED,    Color.WHITE);
        btnThem.setPreferredSize(new Dimension(120, 34));
        btnMo.setPreferredSize(new Dimension(110, 34));
        btnXoa.setPreferredSize(new Dimension(90, 34));
        btnArea.add(btnThem);
        btnArea.add(btnMo);
        btnArea.add(btnXoa);

        toolRow.add(searchBox, BorderLayout.CENTER);
        toolRow.add(btnArea, BorderLayout.EAST);

        north.add(headerRow, BorderLayout.NORTH);
        north.add(toolRow, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        // Center: table card
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(230, 235, 245), 1, true));

        String[] cols = {"#", "Tên file", "Loại", "Đề tài liên kết", "Ngày thêm", "Ghi chú"};
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

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));
        th.setBackground(new Color(248, 250, 254));
        th.setForeground(StyleManager.TEXT_SECONDARY);
        th.setPreferredSize(new Dimension(0, 38));

        int[] widths = {36, 280, 90, 150, 110, 200};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        table.getColumnModel().getColumn(2).setCellRenderer(new LoaiRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        tableCard.add(scroll, BorderLayout.CENTER);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(StyleManager.BG_LIGHT);
        tableWrap.setBorder(new EmptyBorder(0, 30, 30, 30));
        tableWrap.add(tableCard, BorderLayout.CENTER);
        add(tableWrap, BorderLayout.CENTER);

        // Events
        btnThem.addActionListener(e -> themTaiLieu());
        btnMo.addActionListener(e -> moFile());
        btnXoa.addActionListener(e -> xoaTaiLieu());
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { loadTable(); }
            public void removeUpdate(DocumentEvent e) { loadTable(); }
            public void changedUpdate(DocumentEvent e) { loadTable(); }
        });
    }

    private void themTaiLieu() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setDialogTitle("Chọn tài liệu cần thêm vào kho");
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        // Select linked project
        String selectedMa = "";
        String[] ds = AppContext.DATA_SERVICE.layTatCa().stream()
                .map(d -> d.getMaDT() + " – " + d.getTenDT())
                .toArray(String[]::new);
        if (ds.length > 0) {
            Object choice = JOptionPane.showInputDialog(this,
                    "Liên kết với đề tài (bỏ qua nếu không cần):",
                    "Chọn đề tài", JOptionPane.QUESTION_MESSAGE,
                    null, ds, ds[0]);
            if (choice != null) {
                String s = choice.toString();
                selectedMa = s.contains("–") ? s.split("–")[0].trim() : s;
            }
        }

        String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        File[] files = chooser.getSelectedFiles();
        for (File f : files) {
            String ext = getExtension(f.getName()).toUpperCase();
            String loai = switch (ext) {
                case "PDF"  -> "PDF";
                case "DOC","DOCX" -> "Word";
                case "XLS","XLSX" -> "Excel";
                case "PPT","PPTX" -> "PowerPoint";
                default -> "Khác";
            };
            TaiLieu tl = new TaiLieu(UUID.randomUUID().toString(),
                    f.getName(), f.getAbsolutePath(), loai, selectedMa, today);
            AppContext.DATA_SERVICE.themTaiLieu(tl);
        }
        loadTable();
        JOptionPane.showMessageDialog(this,
                "Đã thêm " + files.length + " tài liệu vào kho!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void moFile() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn tài liệu cần mở."); return; }
        String ten = model.getValueAt(row, 1).toString();
        for (TaiLieu tl : AppContext.DATA_SERVICE.layTatCaTaiLieu()) {
            if (tl.getTenFile().equals(ten)) {
                try {
                    File f = new File(tl.getDuongDan());
                    if (!f.exists()) {
                        JOptionPane.showMessageDialog(this, "File không tồn tại nữa:\n" + tl.getDuongDan());
                        return;
                    }
                    Desktop.getDesktop().open(f);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Không thể mở file:\n" + ex.getMessage());
                }
                return;
            }
        }
    }

    private void xoaTaiLieu() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn tài liệu cần xóa."); return; }
        int r = JOptionPane.showConfirmDialog(this,
                "Xóa tài liệu này khỏi kho? (File gốc không bị xóa)",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r != JOptionPane.YES_OPTION) return;
        String ten = model.getValueAt(row, 1).toString();
        AppContext.DATA_SERVICE.layTatCaTaiLieu().stream()
                .filter(t -> t.getTenFile().equals(ten))
                .findFirst()
                .ifPresent(tl -> AppContext.DATA_SERVICE.xoaTaiLieu(tl.getId()));
        loadTable();
    }

    public void loadTable() {
        String key = txtSearch != null ? txtSearch.getText().toLowerCase() : "";
        model.setRowCount(0);
        int idx = 1;
        for (TaiLieu tl : AppContext.DATA_SERVICE.layTatCaTaiLieu()) {
            if (!key.isBlank()
                    && !tl.getTenFile().toLowerCase().contains(key)
                    && !tl.getMaDeTai().toLowerCase().contains(key)) continue;
            model.addRow(new Object[]{
                    idx++, tl.getTenFile(), tl.getLoai(),
                    tl.getMaDeTai(), tl.getNgayThem(), tl.getGhiChu()
            });
        }
        if (lblTong != null)
            lblTong.setText((idx - 1) + " tài liệu");
    }

    private String getExtension(String name) {
        int i = name.lastIndexOf('.');
        return i >= 0 ? name.substring(i + 1) : "";
    }

    static class LoaiRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(
                JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            String s = lbl.getText();
            switch (s) {
                case "PDF"        -> { lbl.setBackground(new Color(254,226,226)); lbl.setForeground(new Color(153,27,27)); }
                case "Word"       -> { lbl.setBackground(new Color(219,234,254)); lbl.setForeground(new Color(29,78,216)); }
                case "Excel"      -> { lbl.setBackground(new Color(220,252,231)); lbl.setForeground(new Color(21,128,61)); }
                case "PowerPoint" -> { lbl.setBackground(new Color(255,237,213)); lbl.setForeground(new Color(154,52,18)); }
                default           -> { lbl.setBackground(new Color(243,244,246)); lbl.setForeground(new Color(75,85,99)); }
            }
            if (sel) lbl.setBackground(lbl.getBackground().darker());
            return lbl;
        }
    }
}
