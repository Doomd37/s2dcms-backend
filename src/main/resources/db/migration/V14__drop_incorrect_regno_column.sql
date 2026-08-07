-- Drop the incorrect regno column (without underscore) since the correct column is reg_no
ALTER TABLE students DROP COLUMN IF EXISTS regno;
