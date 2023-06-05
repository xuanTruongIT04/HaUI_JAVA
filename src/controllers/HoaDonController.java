/*
 * Project Name:       Phan Mem Quan Ly Tien Dien Java
 * Project URI:        https://github.com/zadajtjeu/QuanLyTienDien
 * Description:       Bai tap lon Java Quan Ly Tien Dien JDBC + Swing
 * Version:           1.0
 * Author:            Nhom 18: Nam, Hao, Trung
 * Author URI:        https://nam.name.vn
 * 
 * Copyright (C) 2021-2022 Pham Thanh Nam - HAUI.
 * 
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.ChiSoDien;
import models.HoaDon;
import models.KhachHang;
import models.Thang;
import services.ChiSoDienService;
import services.ChiSoDienServiceImpl;
import services.HoaDonService;
import services.HoaDonServiceImpl;
import services.KhachHangService;
import services.KhachHangServiceImpl;
import services.ThangService;
import services.ThangServiceImpl;
import utility.ClassTableModel;

/**
 *
 * @author duato
 */
public class HoaDonController {
    // Thuoc tinh cho jpnHoaDonMain
    private static JPanel jpnHoaDonTable;
    private static JButton btnThanhToan;
    private static JTextField jtfSearch;
    private static JLabel jlbHoTenKH;
    private static JLabel jlbThang;
    private static JLabel jlbLuongDienTieuThu;
    private static JLabel jlbTongTien;
    
    //Thuoc tinh cho jpnLapHoaDon
    private static JPanel jpnLapHoaDonTable;
    private static JButton btnLapHoaDon;
    private static JComboBox jcbLoaiDien;
    private static JTextField jtfLHDSearch;
    private static JLabel jlbLHDHoTenKH;
    private static JLabel jlbLHDThang;
    private static JLabel jlbLHDLuongDienTieuThu;
    private static JLabel jlbLHDTienDien;
    private static JLabel jlbLHDTongTien;
    
    private static ClassTableModel classTableModel = null;

    
    
    private static HoaDonService hoaDonService = null;
    private static ChiSoDienService chiSoDienService = null;
    private static KhachHangService khachHangService = null;
    private static ThangService thangService = null;

    private static TableRowSorter<TableModel> rowSorter = null;

    public HoaDonController(JPanel jpnHoaDonTable, JButton btnThanhToan, JTextField jtfSearch, JLabel jlbHoTenKH, JLabel jlbThang, JLabel jlbLuongDienTieuThu, JLabel jlbTongTien,
            JPanel jpnLapHoaDonTable, JButton btnLapHoaDon, JComboBox jcbLoaiDien, JTextField jtfLHDSearch, JLabel jlbLHDHoTenKH, JLabel jlbLHDThang, JLabel jlbLHDLuongDienTieuThu, JLabel jlbLHDTienDien, JLabel jlbLHDTongTien) {
        //khoi tao thuoc tinh cho jpnHoaDonMain
        this.jpnHoaDonTable = jpnHoaDonTable;
        this.btnThanhToan = btnThanhToan;
        this.jtfSearch = jtfSearch;
        this.jlbHoTenKH = jlbHoTenKH;
        this.jlbThang = jlbThang;
        this.jlbLuongDienTieuThu = jlbLuongDienTieuThu;
        this.jlbTongTien = jlbTongTien;
        
        //khoi tao thuoc tinh cho jpnLapHoaDon
        this.jpnLapHoaDonTable = jpnLapHoaDonTable;
        this.btnLapHoaDon = btnLapHoaDon;
        this.jcbLoaiDien = jcbLoaiDien;
        this.jtfLHDSearch = jtfLHDSearch;
        this.jlbLHDHoTenKH = jlbLHDHoTenKH;
        this.jlbLHDThang = jlbLHDThang;
        this.jlbLHDLuongDienTieuThu = jlbLHDLuongDienTieuThu;
        this.jlbLHDTienDien = jlbLHDTienDien;
        this.jlbLHDTongTien = jlbLHDTongTien;
        
        this.classTableModel = new ClassTableModel();
        this.hoaDonService = new HoaDonServiceImpl();
        this.chiSoDienService = new ChiSoDienServiceImpl();
        this.khachHangService = new KhachHangServiceImpl();
        this.thangService = new ThangServiceImpl();
    }

    public HoaDonController() {
    }
   
    
    public static void setDataAndEventToTableHoaDon() {
        
        // Đẩy dữ liệu vào jpnHoaDonTable
        List<HoaDon> listHoaDon = hoaDonService.getList();
        List<KhachHang> listKhachHang = khachHangService.getList();
        List<Thang> listThang = thangService.getList();
        String[] COLUMNS = {"Mã hoá đơn", "Mã khách hàng", "Tên khách hàng", "Tháng ID",
        "Ngày Đầu", "Ngày Cuối", "Lượng điện tiêu thụ", "Tổng tiền", "Ngày lập phiếu", "Thanh toán", "Mã Nhân Viên"};
        DefaultTableModel model = classTableModel.setTableHoaDon(listHoaDon, listKhachHang, listThang, COLUMNS);
        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        //Chage width to Hide MaKH
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setPreferredWidth(0);
        //Chage width to Hide ThangID
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setPreferredWidth(0);
        //Chage width to Hide MaNV
        table.getColumnModel().getColumn(10).setMinWidth(0);
        table.getColumnModel().getColumn(10).setMaxWidth(0);
        table.getColumnModel().getColumn(10).setPreferredWidth(0);
        
        
        //Su kien chon 1 dong trong bang
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            //Sự kiện click đúp vào thì sẽ mở jdialog để chỉnh sửa
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                    //selectedRowIndex = table.convertColumnIndexToModel(selectedRowIndex);
                    
                    // Lay thong tin tren bang
                    String hoTenKH = model.getValueAt(selectedRowIndex, 2).toString() != null?
                            model.getValueAt(selectedRowIndex, 2).toString() : "";
                    String ngayDau = model.getValueAt(selectedRowIndex, 4).toString() != null?
                            model.getValueAt(selectedRowIndex, 4).toString() : "";
                    String ngayCuoi = model.getValueAt(selectedRowIndex, 5).toString() != null?
                            model.getValueAt(selectedRowIndex, 5).toString() : "";
                    int luongDienTieuThu = model.getValueAt(selectedRowIndex, 6).toString() != null
                            ? (int) model.getValueAt(selectedRowIndex, 6) : 0;
                    long tongTien = model.getValueAt(selectedRowIndex, 7).toString() != null
                            ? (long) model.getValueAt(selectedRowIndex, 7) : 0;
                    
                    // Set lable
                    
                    jlbHoTenKH.setText(hoTenKH); 
                    jlbThang.setText(ngayDau+"/"+ngayCuoi);
                    jlbLuongDienTieuThu.setText(String.valueOf(luongDienTieuThu));
                    java.text.NumberFormat currencyVN = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                    jlbTongTien.setText(currencyVN.format(tongTien));
                }
            }
            
            //Hiển thị Jpopupmenu khi chọn để sửa hoặc xoá

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                //row index is found...
                int rowindex = table.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createDeletePopUp(rowindex, table);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            
        });
        // design
        table.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        table.getTableHeader().setPreferredSize(new java.awt.Dimension(100, 50));
        table.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new java.awt.Dimension(1350, 400));
        jpnHoaDonTable.removeAll();
        jpnHoaDonTable.setLayout(new java.awt.CardLayout());
        jpnHoaDonTable.add(scroll);
        jpnHoaDonTable.validate();
        jpnHoaDonTable.repaint();
        
        
        // SỰ KIỆN CLICK BUTTON THANH TOÁN / IN HOÁ ĐƠN
        btnThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    DefaultTableModel model2 = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());

                    // Lay thong tin tren bang để tạo HoaDon
                    int maHD = model2.getValueAt(selectedRowIndex, 0).toString() != null
                            ? (int) model2.getValueAt(selectedRowIndex, 0) : 0;
                    boolean tinhTrang = model2.getValueAt(selectedRowIndex, 9).toString() != null
                            ? (boolean) model2.getValueAt(selectedRowIndex, 9) : false;
                    if (!tinhTrang) {
                        
                        int dialogResult = JOptionPane.showConfirmDialog(main.Main.mainFrame,
                                "Bạn muốn cập nhập thanh toán cho hoá đơn " + maHD + " không?", "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            //Update CSDL
                            int lastId = hoaDonService.updateBillStatus(maHD);
                            if (lastId != 0) {
                                JOptionPane.showMessageDialog(main.Main.mainFrame, "Xác nhận thanh toán thành công.\n"
                                        + "Đang in hoá đơn ... !");
                                try {
                                    Hashtable map = new Hashtable();
                                    map.put("MaHD", maHD);
                                    net.sf.jasperreports.engine.JasperReport report
                                            = (net.sf.jasperreports.engine.JasperReport) 
                                            net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile("src/views/template/HoaDonTienDien.jasper");
                                    net.sf.jasperreports.engine.JasperPrint print
                                            = net.sf.jasperreports.engine.JasperFillManager.fillReport(report, map, dao.MySQLConnect.getConnection());
                                    net.sf.jasperreports.view.JasperViewer.viewReport(print, false);
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                setDataAndEventToTableHoaDon();
                                setDataAndEventToTableLapHoaDon();
                            } else {
                                JOptionPane.showMessageDialog(main.Main.mainFrame, "Xác nhận thanh toán thất bại do lỗi hệ thống. Vui lòng khởi động lại phần mềm!",
                                         "Đã có lỗi xảy ra!", JOptionPane.WARNING_MESSAGE);
                            }

                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(main.Main.mainFrame, "Hoá đơn này đã được thanh toán rồi!",
                                "Không thể xác nhận!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

    }
    // Delete JPopupmenu
    public static JPopupMenu createDeletePopUp(int rowindex, JTable table) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Chỉnh sửa");
        edit.setIcon(new javax.swing.ImageIcon(HoTieuThuController.class.getResource("/images/edit.png"))); // NOI18N
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(main.Main.mainFrame, "Vì lý do toàn vẹn dữ liệu nên không thể sửa đổi hoá đơn.\n Bạn vui lòng xoá hoa đơn và lập lại!");
            }
        });
        JMenuItem delete = new JMenuItem("Xoá");
        delete.setIcon(new javax.swing.ImageIcon(HoTieuThuController.class.getResource("/images/delete.png"))); // NOI18N
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(main.Main.mainFrame,
                        "Bạn muốn xoá hoá đơn này không?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());

                    // Lay thong tin tren bang
                    long maHD = model.getValueAt(selectedRowIndex, 0).toString() != null
                            ? Long.valueOf(model.getValueAt(selectedRowIndex, 0).toString()) : 0;
                    boolean check = hoaDonService.deleteData(maHD);
                    if (check) {
                        JOptionPane.showMessageDialog(main.Main.mainFrame, "Delete scucessfuly");
                        setDataAndEventToTableHoaDon();
                        setDataAndEventToTableLapHoaDon();
                    } else {
                        JOptionPane.showMessageDialog(main.Main.mainFrame, "Delete failed");
                        
                    }
                }
            }
        });
        popup.add(edit);
        popup.add(delete);
        return popup;
    }
    
    
    
    
    //  LẬP HOÁ ĐƠN
    public static void setDataAndEventToTableLapHoaDon() {
        
        // Đẩy dữ liệu vào jpnLapHoaDonTable
        List<ChiSoDien> listChiSoDien = chiSoDienService.getListExcludedInHoaDon();
        List<KhachHang> listKhachHang = khachHangService.getList();
        List<Thang> listThang = thangService.getList();
        String[] COLUMNS = {"ID", "Mã khách hàng", "Tên khách hàng", "Tháng ID",
        "Ngày Đầu", "Ngày Cuối", "Chỉ số cũ", "Chỉ số mới", "Ngày ghi"};
        DefaultTableModel model = classTableModel.setTableLapHoaDon(listChiSoDien, listKhachHang, listThang, COLUMNS);
        JTable table = new JTable(model);

        TableRowSorter<TableModel> rowSorter1 = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter1);

        jtfLHDSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfLHDSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter1.setRowFilter(null);
                } else {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfLHDSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter1.setRowFilter(null);
                } else {
                    rowSorter1.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        //Chage width to Hide ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        //Chage width to Hide MaKH
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setPreferredWidth(0);
        //Chage width to Hide ThangID
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setPreferredWidth(0);
        
        
        //Su kien chon 1 dong trong bang
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            //Sự kiện click đúp vào thì sẽ mở jdialog để chỉnh sửa
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                    //selectedRowIndex = table.convertColumnIndexToModel(selectedRowIndex);
                    
                    // Lay thong tin tren bang
                    String hoTenKH = model.getValueAt(selectedRowIndex, 2).toString() != null?
                            model.getValueAt(selectedRowIndex, 2).toString() : "";
                    String ngayDau = model.getValueAt(selectedRowIndex, 4).toString() != null?
                            model.getValueAt(selectedRowIndex, 4).toString() : "";
                    String ngayCuoi = model.getValueAt(selectedRowIndex, 5).toString() != null?
                            model.getValueAt(selectedRowIndex, 5).toString() : "";
                    int chiSoCu = model.getValueAt(selectedRowIndex, 6).toString() != null
                            ? (int) model.getValueAt(selectedRowIndex, 6) : 0;
                    int chiSoMoi = model.getValueAt(selectedRowIndex, 7).toString() != null
                            ? (int) model.getValueAt(selectedRowIndex, 7) : 0;
                    int loaiDien = jcbLoaiDien.getSelectedIndex();
                    long tongTien = tinhTienDien(chiSoCu, chiSoMoi, loaiDien);
                    
                    // Set lable
                    
                    jlbLHDHoTenKH.setText(hoTenKH); 
                    jlbLHDThang.setText(ngayDau+"/"+ngayCuoi);
                    jlbLHDLuongDienTieuThu.setText(String.valueOf(chiSoMoi-chiSoCu));
                    java.text.NumberFormat currencyVN = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                    jlbLHDTienDien.setText(currencyVN.format(tongTien));
                    jlbLHDTongTien.setText(currencyVN.format(tongTien+tongTien*0.1));
                }
            }
            
        });
        // design
        table.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        table.getTableHeader().setPreferredSize(new java.awt.Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new java.awt.Dimension(1350, 400));
        jpnLapHoaDonTable.removeAll();
        jpnLapHoaDonTable.setLayout(new java.awt.CardLayout());
        jpnLapHoaDonTable.add(scroll);
        jpnLapHoaDonTable.validate();
        jpnLapHoaDonTable.repaint();
        
        
        
                // SỰ KIỆN THAY ĐỔI Ở JComobox
        jcbLoaiDien.addActionListener((ActionEvent e) -> {
            if (table.getSelectedRow() != -1) {
                DefaultTableModel model2 = (DefaultTableModel) table.getModel();
                int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                //selectedRowIndex = table.convertColumnIndexToModel(selectedRowIndex);

                // Lay thong tin tren bang
                String hoTenKH = model2.getValueAt(selectedRowIndex, 2).toString() != null
                        ? model2.getValueAt(selectedRowIndex, 2).toString() : "";
                String ngayDau = model2.getValueAt(selectedRowIndex, 4).toString() != null
                        ? model2.getValueAt(selectedRowIndex, 4).toString() : "";
                String ngayCuoi = model2.getValueAt(selectedRowIndex, 5).toString() != null
                        ? model2.getValueAt(selectedRowIndex, 5).toString() : "";
                int chiSoCu = model2.getValueAt(selectedRowIndex, 6).toString() != null
                        ? (int) model2.getValueAt(selectedRowIndex, 6) : 0;
                int chiSoMoi = model2.getValueAt(selectedRowIndex, 7).toString() != null
                        ? (int) model2.getValueAt(selectedRowIndex, 7) : 0;
                JComboBox cb = (JComboBox) e.getSource();
                int loaiDien = cb.getSelectedIndex();
                long tongTien = tinhTienDien(chiSoCu, chiSoMoi, loaiDien);

                // Set lable
                jlbLHDHoTenKH.setText(hoTenKH);
                jlbLHDThang.setText(ngayDau + "/" + ngayCuoi);
                jlbLHDLuongDienTieuThu.setText(String.valueOf(chiSoMoi - chiSoCu));
                java.text.NumberFormat currencyVN = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                jlbLHDTienDien.setText(currencyVN.format(tongTien));
                jlbLHDTongTien.setText(currencyVN.format(tongTien + tongTien * 0.1));
            }
        });
        
        
        // SỰ KIỆN CLICK LẬP HOÁ ĐƠN JBTNLAPHOADON
        btnLapHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    DefaultTableModel model2 = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());

                    // Lay thong tin tren bang để tạo HoaDon
                    int maKH = model2.getValueAt(selectedRowIndex, 1).toString() != null
                            ? (int) model2.getValueAt(selectedRowIndex, 1) : 0;
                    String hoTenKH = model2.getValueAt(selectedRowIndex, 2).toString() != null
                        ? model2.getValueAt(selectedRowIndex, 2).toString() : "";
                    int thangID = model2.getValueAt(selectedRowIndex, 3).toString() != null
                            ? (int) model2.getValueAt(selectedRowIndex, 3) : 0;
                    java.util.Date ngayLapPhieu = new java.util.Date();
                    int chiSoCu = model2.getValueAt(selectedRowIndex, 6).toString() != null
                            ? (int) model2.getValueAt(selectedRowIndex, 6) : 0;
                    int chiSoMoi = model2.getValueAt(selectedRowIndex, 7).toString() != null
                            ? (int) model2.getValueAt(selectedRowIndex, 7) : 0;
                    int luongDienTieuThu = chiSoMoi - chiSoCu;
                    int loaiDien = jcbLoaiDien.getSelectedIndex();
                    long tienDien = tinhTienDien(chiSoCu, chiSoMoi, loaiDien);
                    long tongTien = (long) (tienDien + tienDien*0.1);
                    
                    java.text.NumberFormat currencyVN = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                    int dialogResult = JOptionPane.showConfirmDialog(main.Main.mainFrame,
                                    "Bạn có muốn tạo hoá đơn cho khách hàng "+hoTenKH+" với số tiền thanh toán "+
                                    java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN")).format(tongTien)+
                                    " không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION)
                    {
                        HoaDon hoaDon = new HoaDon(0, maKH, thangID, luongDienTieuThu, tongTien, ngayLapPhieu, false, main.Main.nhanVien.getMaNV());
                        int lastId = hoaDonService.createHoaDon(hoaDon);
                            if (lastId != 0) {
                                JOptionPane.showMessageDialog(main.Main.mainFrame, "Tạo hoá đơn thành công. "
                                        + "Vui lòng chọn hoá đơn vừa tạo ở bảng phía dưới để tiến hành thanh toán!");
                                setDataAndEventToTableHoaDon();
                                setDataAndEventToTableLapHoaDon();
                            } else {
                                JOptionPane.showMessageDialog(main.Main.mainFrame, "Tạo hoá đơn thất bại do lỗi hệ thống. Vui lòng khởi động lại phần mềm!"
                                        , "Đã có lỗi xảy ra!", JOptionPane.WARNING_MESSAGE);
                            }
                    }
                    
                }
            }
        });
    }
    
    
    private static long tinhTienDien(int chiSoCu, int chiSoMoi, int loaiDien) {
        int luongDienTieuThu = chiSoMoi - chiSoCu > 0 ? chiSoMoi - chiSoCu : 0;
        long bill = 0;
        // Giá điện sinh hoạt
        final int[][] giaDienSinhHoat = {{1, 1678},
                            {51, 1734},
                            {101, 2014},
                            {201, 2536},
                            {301, 2834},
                            {401, 2927}};
        // Giá điện Kinh doanh
        final int giaDienKinhDoanh = 2500;
        //Giá điện Sản xuất
        final int giaDienSanXuat = 3000;
        
        // Tính giá điện
        // loaiDien: 0-Sinh hoạt, 1-Sản xuất, 2-Kinh doanh
        
        switch(loaiDien){
            case 0 -> { 
                for (int level = giaDienSinhHoat.length - 1; level >= 0; level--) {
                    if (luongDienTieuThu >= giaDienSinhHoat[level][0]) {
                        bill += (luongDienTieuThu - giaDienSinhHoat[level][0] + 1) * giaDienSinhHoat[level][1];
                        luongDienTieuThu = giaDienSinhHoat[level][0] - 1;
                    }
                }
            }
            case 1 -> {
                bill = luongDienTieuThu * giaDienSanXuat;
            }
            case 2 -> {
                bill = luongDienTieuThu * giaDienKinhDoanh;
            }
            default -> {
                bill = 0;
            }
        }
        
        return bill;
    }
}
