-- Fix the role value for the Statistics department
update departments set role = 'DEPARTMENT' where role = '1';
