CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
        id_user UUID DEFAULT gen_random_uuid() PRIMARY KEY,
        name_user VARCHAR(50) NOT NULL,
        email_user VARCHAR(100) NOT NULL,
        password_user VARCHAR(100) NOT NULL,
        roles_user VARCHAR(50) NOT NULL
);