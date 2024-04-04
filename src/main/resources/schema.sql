CREATE TABLE IF NOT EXISTS matrixcode (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data VARCHAR(4000) NOT NULL,
    url VARCHAR(255),
    size VARCHAR(20) DEFAULT '200x200',
    format VARCHAR(20) DEFAULT 'qrcode',
    type VARCHAR(20) DEFAULT 'gif',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE matrixcode
ALTER COLUMN id SET DEFAULT uuid_generate_v4();

-- ALTER TABLE matrixcode
-- ADD CONSTRAINT unique_data UNIQUE (data, size, format, type);