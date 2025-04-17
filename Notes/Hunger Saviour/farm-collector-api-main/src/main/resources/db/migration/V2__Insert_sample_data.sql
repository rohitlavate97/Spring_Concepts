-- Insert sample data into farms
INSERT INTO farms (farm_name, season) VALUES ('MyFarm', 'Summer');
INSERT INTO farms (farm_name, season) VALUES ('GreenField', 'Winter');
INSERT INTO farms (farm_name, season) VALUES ('SunshineFarm', 'Spring');
INSERT INTO farms (farm_name, season) VALUES ('HappyHarvest', 'Fall');
INSERT INTO farms (farm_name, season) VALUES ('RiverView', 'Summer');

-- Insert sample data into crops
-- Data for MyFarm (Summer)
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (1, 'Corn', 10, 100, 90);
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (1, 'Potato', 20, 200, 180);

-- Data for GreenField (Winter)
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (2, 'Wheat', 15, 150, 140);
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (2, 'Carrot', 12, 120, 110);

-- Data for SunshineFarm (Spring)
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (3, 'Rice', 25, 250, 240);
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (3, 'Tomato', 30, 300, 290);

-- Data for HappyHarvest (Fall)
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (4, 'Apple', 40, 400, 380);
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (4, 'Grape', 50, 500, 470);

-- Data for RiverView (Summer)
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (5, 'Corn', 12, 120, 110);
INSERT INTO crops (farm_id, crop_type, planting_area, expected_product, actual_harvest)
VALUES (5, 'Rice', 10, 100, 90);
