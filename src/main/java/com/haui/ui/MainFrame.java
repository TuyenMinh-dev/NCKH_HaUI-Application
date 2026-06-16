package com.haui.ui;

import com.haui.util.StyleManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private DashboardPanel dashboardPanel;
    private ProgressForm progressForm;
    private TaiLieuForm taiLieuForm;
    private ThongKePanel thongKePanel;   // <<< THÊM MỚI

    private final List<JPanel> navItems = new ArrayList<>();

    // <<< THÊM "thongke" vào mảng navKeys (index 5)
    private final String[] navKeys = {
        "dashboard", "detai", "progress", "tailieu", "thongke", "about"
    };
    private int activeNav = 0;

    public MainFrame() {
        AppContext.MAIN_FRAME = this;
        initUI();
        setVisible(true);
    }

    private void initUI() {
        setTitle("Hệ thống Hỗ trợ Nghiên cứu Khoa học – HaUI");
        setSize(1280, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(900, 600));
        createSidebar();
        createContent();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(StyleManager.SIDEBAR_BG);
        sidebar.setLayout(new BorderLayout());

        // Logo
        JPanel logoPanel = new JPanel(new BorderLayout(8, 0));
        logoPanel.setBackground(StyleManager.SIDEBAR_BG);
        logoPanel.setBorder(new EmptyBorder(24, 18, 20, 18));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(StyleManager.HAUI_ORANGE);
        accentBar.setPreferredSize(new Dimension(4, 0));

        JLabel logoTitle = new JLabel("NCKH HaUI");
        logoTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoTitle.setForeground(Color.WHITE);

        JLabel logoSub = new JLabel("Nghiên cứu khoa học");
        logoSub.setFont(StyleManager.FONT_SMALL);
        logoSub.setForeground(new Color(180, 190, 210));

        JPanel logoText = new JPanel(new GridLayout(2, 1, 0, 2));
        logoText.setOpaque(false);
        logoText.setBorder(new EmptyBorder(0, 10, 0, 0));
        logoText.add(logoTitle);
        logoText.add(logoSub);

        logoPanel.add(accentBar, BorderLayout.WEST);
        logoPanel.add(logoText, BorderLayout.CENTER);
        sidebar.add(logoPanel, BorderLayout.NORTH);

        // Nav — <<< THÊM icon "📈" và label "Thống kê" vào vị trí index 4
        String[] icons = {"⊞", "≡", "◉", "📁", "📈", "ℹ"};
        String[] labels = {"Dashboard", "Quản lý đề tài", "Tiến độ",
            "Kho tài liệu", "Thống kê", "Giới thiệu"};

        JPanel navPanel = new JPanel();
        navPanel.setBackground(StyleManager.SIDEBAR_BG);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(new EmptyBorder(8, 0, 8, 0));

        for (int i = 0; i < labels.length; i++) {
            JPanel item = buildNavItem(icons[i], labels[i], i == 0, i);
            navItems.add(item);
            navPanel.add(item);
        }

        sidebar.add(navPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(18, 26, 38));
        footer.setBorder(new EmptyBorder(12, 18, 12, 18));
        JLabel ver = new JLabel("v2.0  •  HaUI 2025");
        ver.setFont(StyleManager.FONT_SMALL);
        ver.setForeground(new Color(130, 145, 165));
        footer.add(ver);
        sidebar.add(footer, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);
    }

    private JPanel buildNavItem(String icon, String label, boolean active, int idx) {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(active ? new Color(230, 87, 37, 60) : StyleManager.SIDEBAR_BG);
        outer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        outer.setPreferredSize(new Dimension(230, 50));
        outer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel indicatorBar = new JPanel();
        indicatorBar.setPreferredSize(new Dimension(4, 0));
        indicatorBar.setBackground(active ? StyleManager.HAUI_ORANGE : StyleManager.SIDEBAR_BG);
        outer.add(indicatorBar, BorderLayout.WEST);

        JPanel inner = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 13));
        inner.setOpaque(false);

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        iconLbl.setForeground(active ? Color.WHITE : new Color(170, 185, 210));

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(active
                ? new Font("Segoe UI", Font.BOLD, 14)
                : new Font("Segoe UI", Font.PLAIN, 14));
        textLbl.setForeground(active ? Color.WHITE : new Color(170, 185, 210));

        inner.add(iconLbl);
        inner.add(textLbl);
        outer.add(inner, BorderLayout.CENTER);

        outer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigate(idx);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (idx != activeNav) {
                    outer.setBackground(new Color(45, 58, 78));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (idx != activeNav) {
                    outer.setBackground(StyleManager.SIDEBAR_BG);
                }
            }
        });

        return outer;
    }

    private void navigate(int idx) {
        activeNav = idx;
        cardLayout.show(contentPanel, navKeys[idx]);

        // <<< THÊM case idx == 4 cho Thống kê
        if (idx == 0) {
            dashboardPanel.refreshData();
        }
        if (idx == 2) {
            progressForm.loadCombo();
        }
        if (idx == 3) {
            taiLieuForm.loadTable();
        }
        if (idx == 4) {
            thongKePanel.lamMoi();   // refresh khi chuyển sang
        }
        updateNavHighlight();
    }

    private void updateNavHighlight() {
        for (int i = 0; i < navItems.size(); i++) {
            JPanel item = navItems.get(i);
            boolean active = (i == activeNav);
            item.setBackground(active ? new Color(230, 87, 37, 60) : StyleManager.SIDEBAR_BG);

            JPanel bar = (JPanel) item.getComponent(0);
            bar.setBackground(active ? StyleManager.HAUI_ORANGE : StyleManager.SIDEBAR_BG);

            JPanel inner = (JPanel) item.getComponent(1);
            JLabel iconLbl = (JLabel) inner.getComponent(0);
            JLabel textLbl = (JLabel) inner.getComponent(1);
            iconLbl.setForeground(active ? Color.WHITE : new Color(170, 185, 210));
            textLbl.setForeground(active ? Color.WHITE : new Color(170, 185, 210));
            textLbl.setFont(active
                    ? new Font("Segoe UI", Font.BOLD, 14)
                    : new Font("Segoe UI", Font.PLAIN, 14));
        }
        revalidate();
        repaint();
    }

    private void createContent() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(StyleManager.BG_LIGHT);

        dashboardPanel = new DashboardPanel();
        progressForm = new ProgressForm();
        taiLieuForm = new TaiLieuForm();
        thongKePanel = new ThongKePanel();   // <<< THÊM MỚI

        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(new JScrollPane(new DeTaiForm()), "detai");
        contentPanel.add(new JScrollPane(progressForm), "progress");
        contentPanel.add(new JScrollPane(new TaiLieuForm()), "tailieu");
        contentPanel.add(thongKePanel, "thongke"); // <<< THÊM MỚI
        contentPanel.add(new AboutPanel(), "about");

        add(contentPanel, BorderLayout.CENTER);
        dashboardPanel.refreshData();
    }

    public void refreshAll() {
        dashboardPanel.refreshData();
        progressForm.loadCombo();
    }
}
