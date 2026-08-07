-- Fix statistics department password to statistics1
update departments set password = '$2a$10$.smqB/QRYZKnRw7aKfapAeBIhORDAwO7ZKM1CuZc.DfYR8Oqu/p8m' where email = 'stat@university.edu';
