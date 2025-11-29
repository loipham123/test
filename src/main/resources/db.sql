use tmarketplace;

CREATE TABLE LoaiMatBang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_loai VARCHAR(100) NOT NULL
);

-- Dữ liệu demo
INSERT INTO LoaiMatBang(ten_loai) VALUES
 ('Văn phòng'),
('Cửa hàng'),
('Nhà ở'),
('Kho bãi');

CREATE TABLE MatBang (
ma_matbang VARCHAR(20) PRIMARY KEY,
ten_matbang VARCHAR(100) NOT NULL,
dia_chi VARCHAR(200),
dien_tich DECIMAL(10,2),
loai_id INT,
gia_thue DECIMAL(15,2),
ngay_bat_dau DATE,
FOREIGN KEY (loai_id) REFERENCES LoaiMatBang(id)
);


INSERT INTO MatBang(ma_matbang, ten_matbang, dia_chi, dien_tich, loai_id, gia_thue, ngay_bat_dau) VALUES
('MB001', 'Mặt bằng 1', '123 Đường A, Đà Nẵng', 50, 1, 5000000, '2025-01-01'),
('MB002', 'Mặt bằng 2', '456 Đường B, Đà Nẵng', 30, 2, 2000000, '2025-02-01'),
('MB003', 'Mặt bằng 3', '789 Đường C, Đà Nẵng', 70, 1, 10000000, '2025-03-01'),
('MB004', 'Mặt bằng 4', '321 Đường D, Đà Nẵng', 40, 3, 3000000, '2025-04-01');

