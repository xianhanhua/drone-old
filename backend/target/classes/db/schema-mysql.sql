CREATE TABLE IF NOT EXISTS drones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    drone_type VARCHAR(50) NOT NULL,
    flight_minutes INT NOT NULL,
    speed_kmh INT NOT NULL,
    payload_kg DECIMAL(8,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    description TEXT,
    created_at VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
