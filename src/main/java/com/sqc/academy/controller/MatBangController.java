package com.sqc.academy.controller;

import com.sqc.academy.model.MatBang;
import com.sqc.academy.service.MatBangService;
import com.sqc.academy.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/matbang")
public class MatBangController {

    private final MatBangService service = new MatBangService();

    @GetMapping
    public ApiResponse<List<MatBang>> getAll() {
        return ApiResponse.success(service.getAll());
    }

    @GetMapping("/{ma}")
    public ApiResponse<MatBang> getById(@PathVariable String ma) {
        MatBang mb = service.getById(ma);
        if (mb == null) {
            return ApiResponse.error("Không tìm thấy mặt bằng với mã: " + ma);
        }
        return ApiResponse.success(mb);
    }

    @PostMapping
    public ApiResponse<Boolean> create(@RequestBody MatBang mb) {
        return ApiResponse.success(service.create(mb));
    }

    @DeleteMapping("/{ma}")
    public ApiResponse<Boolean> delete(@PathVariable String ma) {
        return ApiResponse.success(service.delete(ma));
    }

    @GetMapping("/search")
    public ApiResponse<List<MatBang>> search(
            @RequestParam(required = false) String ma,
            @RequestParam(required = false) String ten,
            @RequestParam(required = false) String diaChi,
            @RequestParam(required = false) BigDecimal dienTichMin,
            @RequestParam(required = false) BigDecimal dienTichMax,
            @RequestParam(required = false) Integer loaiId,
            @RequestParam(required = false) BigDecimal giaMin,
            @RequestParam(required = false) BigDecimal giaMax,
            @RequestParam(required = false) LocalDate ngayMin,
            @RequestParam(required = false) LocalDate ngayMax
    ) {
        List<MatBang> list = service.search(ma, ten, diaChi, dienTichMin, dienTichMax, loaiId, giaMin, giaMax, ngayMin, ngayMax);
        return ApiResponse.success(list);
    }
}
