package com.haui.ui;

import com.haui.service.DataService;
import com.haui.service.JsonService;

public class AppContext {

    public static final JsonService JSON_SERVICE  = new JsonService();
    public static final DataService DATA_SERVICE  = new DataService();

    // Reference tới MainFrame để các panel gọi refreshAll()
    public static MainFrame MAIN_FRAME;

    static {
        DATA_SERVICE.loadData(JSON_SERVICE);
    }
}
