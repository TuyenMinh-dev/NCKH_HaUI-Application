package com.haui.util;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class StyleManager {

    // HAUI color palette
    public static final Color HAUI_ORANGE   = new Color(230, 87, 37);
    public static final Color HAUI_BLUE     = new Color(0, 90, 170);
    public static final Color HAUI_DARK     = new Color(30, 40, 55);
    public static final Color SIDEBAR_BG    = new Color(25, 35, 50);
    public static final Color SIDEBAR_HOVER = new Color(230, 87, 37, 200);
    public static final Color CARD_BG       = new Color(255, 255, 255);
    public static final Color BG_LIGHT      = new Color(245, 247, 252);
    public static final Color TEXT_PRIMARY  = new Color(30, 40, 55);
    public static final Color TEXT_SECONDARY= new Color(110, 120, 140);
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);
    public static final Color WARNING_AMBER = new Color(251, 191, 36);
    public static final Color DANGER_RED    = new Color(239, 68, 68);
    public static final Color ACCENT_PURPLE = new Color(139, 92, 246);

    public static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE  = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_BODY      = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_CARD_NUM  = new Font("Segoe UI", Font.BOLD, 32);

    public static void setup() {
        try {
            FlatLightLaf.setup();

            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("defaultFont", FONT_BODY);
            UIManager.put("Table.rowHeight", 32);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.intercellSpacing", new Dimension(0, 1));
            UIManager.put("ScrollBar.width", 8);
            UIManager.put("TabbedPane.tabHeight", 38);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Tạo nút với màu nền tùy chỉnh */
    public static JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(FONT_BODY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    /** Tạo label tiêu đề section */
    public static JLabel createSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }
}
