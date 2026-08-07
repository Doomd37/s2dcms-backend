-- Fix admin password to admin123 using fresh BCrypt hash from PasswordHasher
update departments set password = '$2a$10$WddScER7xnrp3F77QwpyeO8AGBQSOlAIavE8qCCyjDd7L8Q3haW1m' where email = 'admin@s2dcms.com';
