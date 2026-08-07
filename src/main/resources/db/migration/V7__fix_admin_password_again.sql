-- Fix admin password to admin123 (correct hash)
update departments set password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy' where email = 'admin@s2dcms.com';
