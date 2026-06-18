package com.haui.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haui.model.DeTai;
import com.haui.model.TaiLieu;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class JsonService {

    private static final String DATA_FILE    = "data.json";
    private static final String TAILIEU_FILE = "tailieu.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // =========== DeTai ===========

    public boolean save(TreeSet<DeTai> ds) {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(new ArrayList<>(ds), writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public TreeSet<DeTai> load() {
        TreeSet<DeTai> result = new TreeSet<>();
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) return result;
            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<List<DeTai>>() {}.getType();
                List<DeTai> list = gson.fromJson(reader, type);
                if (list != null) result.addAll(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // =========== TaiLieu ===========

    public boolean saveTaiLieu(List<TaiLieu> ds) {
        try (FileWriter writer = new FileWriter(TAILIEU_FILE)) {
            gson.toJson(ds, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TaiLieu> loadTaiLieu() {
        List<TaiLieu> result = new ArrayList<>();
        try {
            File file = new File(TAILIEU_FILE);
            if (!file.exists()) return result;
            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<List<TaiLieu>>() {}.getType();
                List<TaiLieu> list = gson.fromJson(reader, type);
                if (list != null) result.addAll(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}