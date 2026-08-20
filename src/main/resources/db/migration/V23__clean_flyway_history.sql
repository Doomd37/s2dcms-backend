-- Clean flyway_schema_history by removing entries for deleted migrations (V15-V22)
DELETE FROM flyway_schema_history WHERE version IN ('15', '16', '17', '18', '19', '20', '21', '22');
