package com.haui.service;

import com.haui.model.DeTai;
import com.haui.model.TaiLieu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DataService {

    private final TreeSet<DeTai> dsDeTai = new TreeSet<>();
    private final List<TaiLieu> dsTaiLieu = new ArrayList<>();

    public DataService() {}

    // ======= Load =======

    public void loadData(JsonService js) {
        dsDeTai.clear();
        TreeSet<DeTai> data = js.load();
        if (data != null) dsDeTai.addAll(data);

        dsTaiLieu.clear();
        List<TaiLieu> tl = js.loadTaiLieu();
        if (tl != null) dsTaiLieu.addAll(tl);
    }

    // ======= DeTai =======

    private void save() {
        com.haui.ui.AppContext.JSON_SERVICE.save(dsDeTai);
    }

    public TreeSet<DeTai> layTatCa() { return dsDeTai; }

    public boolean themDeTai(DeTai dt) {
        boolean ok = dsDeTai.add(dt);
        if (ok) save();
        return ok;
    }

    public boolean xoaDeTai(String maDT) {
        boolean ok = dsDeTai.removeIf(dt -> dt.getMaDT().equalsIgnoreCase(maDT));
        if (ok) save();
        return ok;
    }

    public DeTai timTheoMa(String maDT) {
        for (DeTai dt : dsDeTai)
            if (dt.getMaDT().equalsIgnoreCase(maDT)) return dt;
        return null;
    }

    public boolean suaDeTai(DeTai moi) {
        DeTai cu = timTheoMa(moi.getMaDT());
        if (cu == null) return false;
        // preserve progress flags
        moi.setDeCuong(cu.isDeCuong());
        moi.setThuThapDuLieu(cu.isThuThapDuLieu());
        moi.setCodeDemo(cu.isCodeDemo());
        moi.setVietBaoCao(cu.isVietBaoCao());
        dsDeTai.remove(cu);
        dsDeTai.add(moi);
        save();
        return true;
    }

    public int tongDeTai() { return dsDeTai.size(); }

    public int tongDangLam() {
        return (int) dsDeTai.stream().filter(d -> d.getTienDo() < 100).count();
    }

    public int tongHoanThanh() {
        return (int) dsDeTai.stream().filter(d -> d.getTienDo() == 100).count();
    }

    public List<DeTai> timKiem(String keyword) {
        String k = keyword.toLowerCase();
        return dsDeTai.stream()
                .filter(d -> d.getMaDT().toLowerCase().contains(k)
                          || d.getTenDT().toLowerCase().contains(k)
                          || d.getGiangVien().toLowerCase().contains(k)
                          || d.getMssv().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    // ======= TaiLieu =======

    private void saveTaiLieu() {
        com.haui.ui.AppContext.JSON_SERVICE.saveTaiLieu(dsTaiLieu);
    }

    public List<TaiLieu> layTatCaTaiLieu() { return dsTaiLieu; }

    public void themTaiLieu(TaiLieu tl) {
        dsTaiLieu.add(tl);
        saveTaiLieu();
    }

    public boolean xoaTaiLieu(String id) {
        boolean ok = dsTaiLieu.removeIf(t -> t.getId().equals(id));
        if (ok) saveTaiLieu();
        return ok;
    }

    public List<TaiLieu> layTaiLieuTheoDeTai(String maDT) {
        return dsTaiLieu.stream()
                .filter(t -> t.getMaDeTai().equalsIgnoreCase(maDT))
                .collect(Collectors.toList());
    }
}
