# replace "root" with your own username
mysql -u root -p metridoc < metridoc_gate_USC.sql
mysql -u root -p metridoc < metridoc_gate_affiliation.sql
mysql -u root -p metridoc < metridoc_gate_center.sql
mysql -u root -p metridoc < metridoc_gate_department.sql
mysql -u root -p metridoc < metridoc_gate_door.sql
mysql -u root -p metridoc < metridoc_gate_entry_record.sql