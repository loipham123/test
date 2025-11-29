package com.sqc.academy.service;

import com.sqc.academy.model.MatBang;
import com.sqc.academy.repository.MatBangRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MatBangService {
    private final MatBangRepository repository = new MatBangRepository();

    public List<MatBang> getAll() {
        return repository.findAll();
    }

    public MatBang getById(String ma) {
        return repository.findById(ma);
    }

    public boolean create(MatBang mb) {
        return repository.save(mb);
    }

    public boolean delete(String ma) {
        return repository.delete(ma);
    }

    public List<MatBang> search(String ma, String ten, String diaChi,
                                BigDecimal dienTichMin, BigDecimal dienTichMax,
                                Integer loaiId, BigDecimal giaMin, BigDecimal giaMax,
                                LocalDate ngayMin, LocalDate ngayMax) {
        return repository.search(ma, ten, diaChi, dienTichMin, dienTichMax, loaiId, giaMin, giaMax, ngayMin, ngayMax);
    }
}
