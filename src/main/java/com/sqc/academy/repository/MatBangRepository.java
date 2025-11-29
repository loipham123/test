package com.sqc.academy.repository;

import com.sqc.academy.model.LoaiMatBang;
import com.sqc.academy.model.MatBang;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatBangRepository {

    public List<MatBang> findAll() {
        List<MatBang> list = new ArrayList<>();
        String sql = "SELECT m.ma_matbang, m.ten_matbang, m.dia_chi, m.dien_tich, " +
                "l.id AS loai_id, l.ten_loai, m.gia_thue, m.ngay_bat_dau " +
                "FROM MatBang m JOIN LoaiMatBang l ON m.loai_id = l.id";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LoaiMatBang loai = new LoaiMatBang(rs.getInt("loai_id"), rs.getString("ten_loai"));
                MatBang mb = new MatBang(
                        rs.getString("ma_matbang"),
                        rs.getString("ten_matbang"),
                        rs.getString("dia_chi"),
                        BigDecimal.valueOf(rs.getDouble("dien_tich")),
                        loai,
                        BigDecimal.valueOf(rs.getDouble("gia_thue")),
                        rs.getDate("ngay_bat_dau").toLocalDate()
                );
                list.add(mb);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public MatBang findById(String ma) {
        String sql = "SELECT m.ma_matbang, m.ten_matbang, m.dia_chi, m.dien_tich, " +
                "l.id AS loai_id, l.ten_loai, m.gia_thue, m.ngay_bat_dau " +
                "FROM MatBang m JOIN LoaiMatBang l ON m.loai_id = l.id " +
                "WHERE m.ma_matbang = ?";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LoaiMatBang loai = new LoaiMatBang(rs.getInt("loai_id"), rs.getString("ten_loai"));
                    return new MatBang(
                            rs.getString("ma_matbang"),
                            rs.getString("ten_matbang"),
                            rs.getString("dia_chi"),
                            BigDecimal.valueOf(rs.getDouble("dien_tich")),
                            loai,
                            BigDecimal.valueOf(rs.getDouble("gia_thue")),
                            rs.getDate("ngay_bat_dau").toLocalDate()
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(MatBang mb) {
        String sql = "INSERT INTO MatBang(ma_matbang, ten_matbang, dia_chi, dien_tich, loai_id, gia_thue, ngay_bat_dau) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mb.getMaMatBang());
            ps.setString(2, mb.getTenMatBang());
            ps.setString(3, mb.getDiaChi());
            ps.setDouble(4, mb.getDienTich().doubleValue());
            ps.setInt(5, mb.getLoaiMatBang().getId());
            ps.setDouble(6, mb.getGiaThue().doubleValue());
            ps.setDate(7, Date.valueOf(mb.getNgayBatDauThue()));
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String ma) {
        String sql = "DELETE FROM MatBang WHERE ma_matbang = ?";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MatBang> search(
            String ma,
            String ten,
            String diaChi,
            BigDecimal dienTichMin,
            BigDecimal dienTichMax,
            Integer loaiId,
            BigDecimal giaMin,
            BigDecimal giaMax,
            LocalDate ngayMin,
            LocalDate ngayMax
    ) {
        List<MatBang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT m.ma_matbang, m.ten_matbang, m.dia_chi, m.dien_tich, " +
                        "l.id AS loai_id, l.ten_loai, m.gia_thue, m.ngay_bat_dau " +
                        "FROM MatBang m " +
                        "JOIN LoaiMatBang l ON m.loai_id = l.id WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (ma != null && !ma.isEmpty()) {
            sql.append(" AND m.ma_matbang = ?");
            params.add(ma);
        }
        if (ten != null && !ten.isEmpty()) {
            sql.append(" AND m.ten_matbang LIKE ?");
            params.add("%" + ten + "%");
        }
        if (diaChi != null && !diaChi.isEmpty()) {
            sql.append(" AND m.dia_chi LIKE ?");
            params.add("%" + diaChi + "%");
        }
        if (dienTichMin != null) {
            sql.append(" AND m.dien_tich >= ?");
            params.add(dienTichMin.doubleValue());
        }
        if (dienTichMax != null) {
            sql.append(" AND m.dien_tich <= ?");
            params.add(dienTichMax.doubleValue());
        }
        if (loaiId != null) {
            sql.append(" AND m.loai_id = ?");
            params.add(loaiId);
        }
        if (giaMin != null) {
            sql.append(" AND m.gia_thue >= ?");
            params.add(giaMin.doubleValue());
        }
        if (giaMax != null) {
            sql.append(" AND m.gia_thue <= ?");
            params.add(giaMax.doubleValue());
        }
        if (ngayMin != null) {
            sql.append(" AND m.ngay_bat_dau >= ?");
            params.add(Date.valueOf(ngayMin));
        }
        if (ngayMax != null) {
            sql.append(" AND m.ngay_bat_dau <= ?");
            params.add(Date.valueOf(ngayMax));
        }

        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LoaiMatBang loai = new LoaiMatBang(rs.getInt("loai_id"), rs.getString("ten_loai"));
                    MatBang mb = new MatBang(
                            rs.getString("ma_matbang"),
                            rs.getString("ten_matbang"),
                            rs.getString("dia_chi"),
                            BigDecimal.valueOf(rs.getDouble("dien_tich")),
                            loai,
                            BigDecimal.valueOf(rs.getDouble("gia_thue")),
                            rs.getDate("ngay_bat_dau").toLocalDate()
                    );
                    list.add(mb);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }
}
