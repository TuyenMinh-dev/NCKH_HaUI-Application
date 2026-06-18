/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.haui.ui;

/**
 *
 * @author Minh Tuyen
 */
public class DashboardForm extends javax.swing.JPanel {

    /**
     * Creates new form DashboardForm
     */
    public DashboardForm() {
        initComponents();
        pnlTongDeTai.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 190, 205), 1),
                javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, new java.awt.Color(0, 102, 255))));
        pnlDangLam.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 190, 205), 1),
                javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, new java.awt.Color(230, 87, 37))));
        pnlHoanThanh.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 190, 205), 1),
                javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, new java.awt.Color(22, 163, 74))));
        pnlTiLe.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 190, 205), 1),
                javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, new java.awt.Color(147, 51, 234))));

        setupAfterInit();
        refreshData();
    }
    // ================== PHẦN NGHIỆP VỤ ==================

    private javax.swing.table.DefaultTableModel deadlineModel;
    private javax.swing.table.DefaultTableModel recentModel;

    private void setupAfterInit() {
        String today = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        lblNgayHomNay.setText("Hôm nay: " + today);

        deadlineModel = (javax.swing.table.DefaultTableModel) tblDeadline.getModel();
        recentModel = (javax.swing.table.DefaultTableModel) tblRecent.getModel();

        // Renderer màu cho cột "Tiến độ" (cột số 0) trong bảng đề tài gần đây
        tblRecent.getColumnModel().getColumn(0)
                .setCellRenderer(new TableRenderers.TienDoRenderer());
        
        // ---- tblDeadline ----
        javax.swing.table.DefaultTableCellRenderer centerDL
                = new javax.swing.table.DefaultTableCellRenderer();
        centerDL.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for (int i = 0; i < tblDeadline.getColumnCount(); i++) {
            tblDeadline.getColumnModel().getColumn(i).setCellRenderer(centerDL);
        }
        tblDeadline.setRowHeight(32);
        tblDeadline.setShowGrid(true);
        tblDeadline.setGridColor(new java.awt.Color(180, 190, 205));

        // ---- tblRecent ----
        javax.swing.table.DefaultTableCellRenderer centerRC
                = new javax.swing.table.DefaultTableCellRenderer();
        centerRC.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        for (int i = 0; i < tblRecent.getColumnCount(); i++) {
            tblRecent.getColumnModel().getColumn(i).setCellRenderer(centerRC);
        }
        tblRecent.setRowHeight(32);
        tblRecent.setShowGrid(true);
        tblRecent.setGridColor(new java.awt.Color(180, 190, 205));

        // Sau vòng for, set lại renderer tiến độ cho cột 0 của tblRecent
        tblRecent.getColumnModel().getColumn(0)
                .setCellRenderer(new TableRenderers.TienDoRenderer());

        loadDeadline();
    }

    /**
     * Nạp danh sách deadline cố định (5 mốc thời gian quan trọng)
     */
    private void loadDeadline() {
        deadlineModel.setRowCount(0);
        String[][] deadlines = {
            {"30/10", " Hạn đăng ký đề tài "},
            {"15/12", " Nghiệm thu cấp khoa "},
            {"20/12", " Nộp báo cáo cuối kỳ "},
            {"05/01", " Bảo vệ đề tài "},
            {"15/01", " Nộp điểm về Phòng NCKH "},};
        for (String[] dl : deadlines) {
            deadlineModel.addRow(new Object[]{dl[0], dl[1]});
        }
    }

    /**
     * Cập nhật 4 số liệu thống kê + bảng đề tài gần đây (tối đa 5 dòng)
     */
    public void refreshData() {
        int tong = AppContext.DATA_SERVICE.tongDeTai();
        int dangLam = AppContext.DATA_SERVICE.tongDangLam();
        int hoanThanh = AppContext.DATA_SERVICE.tongHoanThanh();
        int tile = tong == 0 ? 0 : (int) Math.round(hoanThanh * 100.0 / tong);

        lblTong.setText(String.valueOf(tong));
        lblDangLam.setText(String.valueOf(dangLam));
        lblHoanThanh.setText(String.valueOf(hoanThanh));
        lblTiLe.setText(tile + "%");

        recentModel.setRowCount(0);
        int count = 0;
        for (com.haui.model.DeTai dt : AppContext.DATA_SERVICE.layTatCa()) {
            if (count++ >= 5) {
                break;
            }
            recentModel.addRow(new Object[]{
                dt.getTienDo(), dt.getTenDT(), dt.getGiangVien()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblNgayHomNay = new javax.swing.JLabel();
        pnlTongDeTai = new javax.swing.JPanel();
        lblTitleTong = new javax.swing.JLabel();
        lblTong = new javax.swing.JLabel();
        pnlDangLam = new javax.swing.JPanel();
        lblTitleDangLam = new javax.swing.JLabel();
        lblDangLam = new javax.swing.JLabel();
        pnlHoanThanh = new javax.swing.JPanel();
        lblTitleHoanThanh = new javax.swing.JLabel();
        lblHoanThanh = new javax.swing.JLabel();
        pnlTiLe = new javax.swing.JPanel();
        lblTitleTiLe = new javax.swing.JLabel();
        lblTiLe = new javax.swing.JLabel();
        pnlDeadline = new javax.swing.JPanel();
        lblDeadlineTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeadline = new javax.swing.JTable();
        pnlRecent = new javax.swing.JPanel();
        lblRecentTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRecent = new javax.swing.JTable();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 0, 51));
        lblTitle.setText("Dashboard");

        lblNgayHomNay.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblNgayHomNay.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNgayHomNay.setText("Hôm nay: --/--/----");

        pnlTongDeTai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTitleTong.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitleTong.setForeground(new java.awt.Color(0, 102, 255));
        lblTitleTong.setText("Tổng đề tài");

        lblTong.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTong.setForeground(new java.awt.Color(0, 102, 255));
        lblTong.setText("0");

        javax.swing.GroupLayout pnlTongDeTaiLayout = new javax.swing.GroupLayout(pnlTongDeTai);
        pnlTongDeTai.setLayout(pnlTongDeTaiLayout);
        pnlTongDeTaiLayout.setHorizontalGroup(
            pnlTongDeTaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTongDeTaiLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlTongDeTaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTong, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitleTong))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        pnlTongDeTaiLayout.setVerticalGroup(
            pnlTongDeTaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTongDeTaiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitleTong)
                .addGap(49, 49, 49)
                .addComponent(lblTong)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pnlDangLam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTitleDangLam.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitleDangLam.setForeground(new java.awt.Color(230, 87, 37));
        lblTitleDangLam.setText("Đang thực hiện");

        lblDangLam.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDangLam.setForeground(new java.awt.Color(230, 87, 37));
        lblDangLam.setText("0");

        javax.swing.GroupLayout pnlDangLamLayout = new javax.swing.GroupLayout(pnlDangLam);
        pnlDangLam.setLayout(pnlDangLamLayout);
        pnlDangLamLayout.setHorizontalGroup(
            pnlDangLamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDangLamLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlDangLamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDangLam, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitleDangLam))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        pnlDangLamLayout.setVerticalGroup(
            pnlDangLamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDangLamLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitleDangLam)
                .addGap(40, 40, 40)
                .addComponent(lblDangLam)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pnlHoanThanh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTitleHoanThanh.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitleHoanThanh.setForeground(new java.awt.Color(22, 163, 74));
        lblTitleHoanThanh.setText("Hoàn thành");

        lblHoanThanh.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblHoanThanh.setForeground(new java.awt.Color(22, 163, 74));
        lblHoanThanh.setText("0");

        javax.swing.GroupLayout pnlHoanThanhLayout = new javax.swing.GroupLayout(pnlHoanThanh);
        pnlHoanThanh.setLayout(pnlHoanThanhLayout);
        pnlHoanThanhLayout.setHorizontalGroup(
            pnlHoanThanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoanThanhLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlHoanThanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleHoanThanh)
                    .addComponent(lblHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        pnlHoanThanhLayout.setVerticalGroup(
            pnlHoanThanhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoanThanhLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitleHoanThanh)
                .addGap(40, 40, 40)
                .addComponent(lblHoanThanh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTiLe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTitleTiLe.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitleTiLe.setForeground(new java.awt.Color(147, 51, 234));
        lblTitleTiLe.setText("Tỉ lệ hoàn thành");

        lblTiLe.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTiLe.setForeground(new java.awt.Color(147, 51, 234));
        lblTiLe.setText("0%");

        javax.swing.GroupLayout pnlTiLeLayout = new javax.swing.GroupLayout(pnlTiLe);
        pnlTiLe.setLayout(pnlTiLeLayout);
        pnlTiLeLayout.setHorizontalGroup(
            pnlTiLeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTiLeLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlTiLeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTiLe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitleTiLe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        pnlTiLeLayout.setVerticalGroup(
            pnlTiLeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTiLeLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitleTiLe)
                .addGap(27, 27, 27)
                .addComponent(lblTiLe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDeadline.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        lblDeadlineTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDeadlineTitle.setForeground(new java.awt.Color(255, 0, 51));
        lblDeadlineTitle.setText(" Lịch quan trọng");

        tblDeadline.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ngày", "Nội dung"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDeadline);

        javax.swing.GroupLayout pnlDeadlineLayout = new javax.swing.GroupLayout(pnlDeadline);
        pnlDeadline.setLayout(pnlDeadlineLayout);
        pnlDeadlineLayout.setHorizontalGroup(
            pnlDeadlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeadlineLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlDeadlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDeadlineTitle)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDeadlineLayout.setVerticalGroup(
            pnlDeadlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeadlineLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblDeadlineTitle)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pnlRecent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblRecentTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRecentTitle.setForeground(new java.awt.Color(255, 0, 51));
        lblRecentTitle.setText(" Đề tài gần đây");

        tblRecent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tiến độ", "Tên đề tài", "Giảng viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblRecent);

        javax.swing.GroupLayout pnlRecentLayout = new javax.swing.GroupLayout(pnlRecent);
        pnlRecent.setLayout(pnlRecentLayout);
        pnlRecentLayout.setHorizontalGroup(
            pnlRecentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecentLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlRecentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRecentTitle)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRecentLayout.setVerticalGroup(
            pnlRecentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecentLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblRecentTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlTongDeTai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlDangLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlDeadline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlRecent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgayHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnlTiLe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1057, 1057, 1057)))
                .addContainerGap(351, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle)
                    .addComponent(lblNgayHomNay))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDangLam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTongDeTai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHoanThanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTiLe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlRecent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDeadline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(64, 64, 64))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDangLam;
    private javax.swing.JLabel lblDeadlineTitle;
    private javax.swing.JLabel lblHoanThanh;
    private javax.swing.JLabel lblNgayHomNay;
    private javax.swing.JLabel lblRecentTitle;
    private javax.swing.JLabel lblTiLe;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleDangLam;
    private javax.swing.JLabel lblTitleHoanThanh;
    private javax.swing.JLabel lblTitleTiLe;
    private javax.swing.JLabel lblTitleTong;
    private javax.swing.JLabel lblTong;
    private javax.swing.JPanel pnlDangLam;
    private javax.swing.JPanel pnlDeadline;
    private javax.swing.JPanel pnlHoanThanh;
    private javax.swing.JPanel pnlRecent;
    private javax.swing.JPanel pnlTiLe;
    private javax.swing.JPanel pnlTongDeTai;
    private javax.swing.JTable tblDeadline;
    private javax.swing.JTable tblRecent;
    // End of variables declaration//GEN-END:variables
}
