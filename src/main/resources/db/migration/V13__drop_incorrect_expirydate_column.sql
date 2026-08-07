-- Drop the incorrect expirydate column (expiry_date already exists from schema)
ALTER TABLE refreshtoken DROP COLUMN IF EXISTS expirydate;
