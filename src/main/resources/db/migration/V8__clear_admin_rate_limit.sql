-- Clear rate limit for admin user to allow login testing
delete from user_action_limit where email = 'admin@s2dcms.com';
