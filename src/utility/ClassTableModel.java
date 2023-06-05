/*
 * Project Name:       Phan Mem Quan Ly Tien Dien Java
 * Description:       Bai tap lon Java Quan Ly Tien Dien JDBC + Swing
 * Author:            Nhom 9
 */
package utility;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.*;

/**
 *
 * @author duato
 */
public class ClassTableModel { //Trình bày cách dữ liệu hiển thị
    //Table KhachHang
    public DefaultTableModel setTableKhachHang(List<KhachHang> listItem, String[] listColumn) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;
        int rows = listItem.size();
        
        KhachHang khachHang = null;
        
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                khachHang = listItem.get(i);
                obj = new Object[columns];
                obj[0] = khachHang.getMaKH();
                obj[1] = khachHang.getHoTen();
                obj[2] = khachHang.getDiaChi();
                obj[3] = khachHang.getCCCD();
                obj[4] = khachHang.getNgaySinh().toString();
                obj[5] = khachHang.getSoDienThoai();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
    //Table HoaDon
    public DefaultTableModel setTableHoaDon(List<HoaDon> listHoaDon, List<KhachHang> listKhachHang, List<Thang> listThang, String[] listColumn) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 9 ? Boolean.class : super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;
        int rows = listHoaDon.size();
        
        HoaDon hoaDon = null;
        
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                hoaDon = listHoaDon.get(i);
                obj = new Object[columns];
                obj[0] = hoaDon.getMaHD();
                // Đổ KhachHang dựa vào MaKH
                obj[1] = hoaDon.getMaKH();
                int maKH = hoaDon.getMaKH();
                KhachHang khachHang = listKhachHang.stream()
                        .filter(kh -> kh.getMaKH() == maKH)
                        .findAny()
                        .orElse(null);
                obj[2] = khachHang.getHoTen();
                // Đổ tháng dựa vào ThangID
                obj[3] = hoaDon.getThangID();
                int thangID = hoaDon.getThangID();
                Thang thang = listThang.stream()
                        .filter(thg -> thg.getThangID()== thangID)
                        .findAny()
                        .orElse(null);
                obj[4] = thang.getNgayDau().toString();
                obj[5] = thang.getNgayCuoi().toString();
                
                obj[6] = hoaDon.getLuongDienTieuThu();
                obj[7] = hoaDon.getTongTien();
                obj[8] = hoaDon.getNgayLapPhieu();
                obj[9] = hoaDon.isTinhTrang();
                obj[10] = hoaDon.getMaNV();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
    
    //Table LapHoaDon
    public DefaultTableModel setTableLapHoaDon(List<ChiSoDien> listCSD, List<KhachHang> listKhachHang, List<Thang> listThang, String[] listColumn) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;
        int rows = listCSD.size();
        
        ChiSoDien chiSoDien = null;
        
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                chiSoDien = listCSD.get(i);
                obj = new Object[columns];
                obj[0] = chiSoDien.getId();
                // Đổ KhachHang dựa vào MaKH
                obj[1] = chiSoDien.getMaKH();
                int maKH = chiSoDien.getMaKH();
                KhachHang khachHang = listKhachHang.stream()
                        .filter(kh -> kh.getMaKH() == maKH)
                        .findAny()
                        .orElse(null);
                obj[2] = khachHang.getHoTen();
                // Đổ tháng dựa vào ThangID
                obj[3] = chiSoDien.getThangID();
                int thangID = chiSoDien.getThangID();
                Thang thang = listThang.stream()
                        .filter(thg -> thg.getThangID()== thangID)
                        .findAny()
                        .orElse(null);
                obj[4] = thang.getNgayDau().toString();
                obj[5] = thang.getNgayCuoi().toString();
                
                obj[6] = chiSoDien.getChiSoCu();
                obj[7] = chiSoDien.getChiSoMoi();
                obj[8] = chiSoDien.getNgayGhi();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
    
    //Table NhanVien
    public DefaultTableModel setTableNhanVien(List<NhanVien> listItem, String[] listColumn) {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;
        int rows = listItem.size();
        
        NhanVien nv = null;
        
        if (rows > 0) {
            for (int i = 0; i < rows; i++) {
                nv = listItem.get(i);
                obj = new Object[columns];
                obj[0] = nv.getMaNV();
                obj[1] = nv.getTenNV();
                obj[2] = nv.getChucVu();
                obj[3] = nv.getTaiKhoan();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
