-- Create departments table
CREATE TABLE IF NOT EXISTS departments (
    id BIGSERIAL PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    password_reset_token VARCHAR(255) UNIQUE,
    password_reset_token_expiry TIMESTAMP,
    department_profile VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create students table
CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    reg_no VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    verification_token VARCHAR(255) UNIQUE,
    verification_token_expiry TIMESTAMP,
    password_reset_token VARCHAR(255) UNIQUE,
    password_reset_token_expiry TIMESTAMP,
    department_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_student_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Create messages table
CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    attachment_path TEXT,
    reply TEXT,
    reply_attachment_path TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    student_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    replied_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    seen_by_student BOOLEAN DEFAULT FALSE,
    seen_by_department BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_message_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_message_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Create refreshtoken table
CREATE TABLE IF NOT EXISTS refreshtoken (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    student_id BIGINT,
    department_id BIGINT,
    CONSTRAINT fk_refresh_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_refresh_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Create user_action_limit table
CREATE TABLE IF NOT EXISTS user_action_limit (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    action VARCHAR(255),
    count INTEGER DEFAULT 0,
    last_request TIMESTAMP
);
