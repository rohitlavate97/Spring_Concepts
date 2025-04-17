-- V1__Create_farms_and_crops_tables.sql

-- Create farms table
CREATE TABLE farms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
        farm_name VARCHAR(255) NOT NULL UNIQUE, -- Ensure farm_name is unique
    season VARCHAR(50) NOT NULL
);

-- Create crops table
CREATE TABLE crops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    farm_id BIGINT NOT NULL,
    crop_type VARCHAR(100) NOT NULL,
    planting_area DOUBLE NOT NULL,
    expected_product DOUBLE NOT NULL,
    actual_harvest DOUBLE,
    FOREIGN KEY (farm_id) REFERENCES farms(id)
);
