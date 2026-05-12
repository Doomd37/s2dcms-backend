
-- flyway: disableTransaction

-- ======================================
-- Message table indexes
-- ======================================


CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_message_student_sentat
ON messages(student_id, sentat);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_message_student_status_sentat
ON messages(student_id, status, sentat);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_message_department_sentat
ON messages(department_id, sentat);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_message_department_status_sentat
ON messages(department_id, status, sentat);

-- ======================================
-- RefreshToken table indexes
-- ======================================

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS idx_refresh_token_token
ON refreshtoken(token);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_refresh_token_expiry
ON refreshtoken(expirydate);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_refresh_token_revoked_true
ON refreshtoken(expirydate)
WHERE revoked = true;

-- ======================================
-- UserActionLimit table index
-- ======================================

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_useraction_email_action
ON user_action_limit(email, action);

-- ======================================
-- Student table indexes
-- ======================================

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS idx_student_email
ON students(email);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_student_reset_token
ON students(passwordresettoken);

-- ======================================
-- Department table indexes
-- ======================================

CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS idx_department_email
ON departments(email);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_department_reset_token
ON departments(passwordresettoken);