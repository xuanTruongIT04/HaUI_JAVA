/*
 * Project Name:       Phan Mem Quan Ly Tien Dien Java
 * Description:       Bai tap lon Java Quan Ly Tien Dien JDBC + Swing
 * Author:            Nhom 9
 */
package services;

import java.util.List;
import models.HoaDon;

/**
 *
 * @author duato
 */
public interface HoaDonService {
    public List<HoaDon> getList();
    public boolean deleteData(long maHoaDon);
    public int createHoaDon(HoaDon hoaDon);
    public int updateBillStatus(int maHD);
}
