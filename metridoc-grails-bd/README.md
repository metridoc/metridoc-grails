metridoc-penn-bd
================

Borrow Direct stand alone application.  We are currently in the process of migrating this into 
[metridoc-grails](http://github.org/metridoc/metridoc-grails) as a grails plugin.

Developer Info:
==============

Developing on a local instance of Borrow Direct requires a local copy of the Borrow Direct and EZ Borrow database tables. The following is a guide to acquiring these tables. Note that this guide assumes a remote server is set up with MySQL and.

1. Run the [BD Metridoc job](https://github.com/metridoc/metridoc-job/tree/master/metridoc-job-bd) to populate the remote database with BD data.
2. ssh into your remote server and identify the Borrow Direct and EZ Borrow tables. Use `mysql -u username -p password database_foo -e'show tables where Tables_in_database_foo like "<bd_% | ezb_%>"'` to find them quickly. 
3. Use mysqldump and gzip to export the desired tables: `sudo sh -c 'mysqldump -u username -p password database_foo bd_table... | gzip > /data/bdTables.sql.gz'`
4. Use scp to transfer the zipped table data to your local machine and unzip the files. 
5. Import them into a local MySQL database: `mysql foo < ~/scratch/bdTables.sql`
