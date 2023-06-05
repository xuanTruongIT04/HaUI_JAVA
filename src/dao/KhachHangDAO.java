/*
 * Project Name:       Phan Mem Quan Ly Tien Dien Java
 * Description:       Bai tap lon Java Quan Ly Tien Dien JDBC + Swing
 * Author:            Nhom 9
 */
package dao;

import java.util.List;
import models.KhachHang;

/**
 *
 * @author duato
 */
public interface KhachHangDAO {
    public List<KhachHang> getList();
    
    public int createORUpdate(KhachHang khachHang);
    
    public boolean deleteData(KhachHang khachHang);
    
    public KhachHang getKhachHang(int maKH);
}
