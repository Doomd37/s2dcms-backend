-- Fix admin password to admin123
update departments set password = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH' where email = 'admin@s2dcms.com';
