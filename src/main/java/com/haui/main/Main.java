package com.haui.main;

import com.haui.ui.MainFrame;
import com.haui.util.StyleManager;

public class Main {

    public static void main(String[] args) {

        StyleManager.setup();

        javax.swing.SwingUtilities.invokeLater(
                () -> new MainFrame()
        );
    }
}