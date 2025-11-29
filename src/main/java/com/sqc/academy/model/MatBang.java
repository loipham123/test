package com.sqc.academy.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MatBang {
    String maMatBang;
    String tenMatBang;
    String diaChi;
    BigDecimal dienTich;
    LoaiMatBang loaiMatBang;
    BigDecimal giaThue;
    LocalDate ngayBatDauThue;
}
