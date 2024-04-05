-- Create the schema
--CREATE SCHEMA IF NOT EXISTS application;

-- Create the table
CREATE TABLE IF NOT EXISTS matrixcode (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data VARCHAR(4000) NOT NULL,
    size VARCHAR(20) DEFAULT '200x200',
    format VARCHAR(20) DEFAULT 'qrcode',
    type VARCHAR(20) DEFAULT 'gif',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add constraint
-- ALTER TABLE matrixcode
-- ADD CONSTRAINT unique_data UNIQUE (data, size, format, type);