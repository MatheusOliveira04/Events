CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE address(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    city VARCHAR(50) NOT NULL,
    uf VARCHAR(2) NOT NULL
);
