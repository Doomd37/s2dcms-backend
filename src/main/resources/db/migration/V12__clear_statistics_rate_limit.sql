-- Clear rate limit for statistics department to allow login testing
delete from user_action_limit where email = 'stat@university.edu';
