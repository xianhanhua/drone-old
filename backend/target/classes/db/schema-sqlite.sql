CREATE TABLE IF NOT EXISTS drones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) NOT NULL,
    drone_type VARCHAR(50) NOT NULL,
    flight_minutes INTEGER NOT NULL,
    speed_kmh INTEGER NOT NULL,
    payload_kg REAL NOT NULL,
    status VARCHAR(30) NOT NULL,
    description TEXT,
    created_at VARCHAR(30) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_drones_serial ON drones(serial_number);

INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    flight_minutes, speed_kmh, payload_kg, status, description, created_at
)
SELECT '巡检一号', 'AI-210-A', 'AeroVision', 'SD-10001', '巡检型',
       45, 72, 1.5, '在役', '适合电力巡检和园区巡逻。', datetime('now')
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SD-10001');

INSERT INTO drones (
    name, model, manufacturer, serial_number, drone_type,
    flight_minutes, speed_kmh, payload_kg, status, description, created_at
)
SELECT '测绘二号', 'AI-320-B', 'SkyMatrix', 'SD-10002', '测绘型',
       60, 68, 0.8, '待检', '适合地形测绘和航拍建模。', datetime('now')
WHERE NOT EXISTS (SELECT 1 FROM drones WHERE serial_number = 'SD-10002');
