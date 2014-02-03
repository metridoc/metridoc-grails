Installation
------------

Within your grails application (would recommend using the 
[template](https://github.com/metridoc/metridoc-template-grails-app)), add the following to your `BuildConfig.groovy` file

```groovy
grails.project.dependency.resolution = {
    //other settings
    repositories {
        //other repos like mavenCentral, etc
        mavenRepo "http://dl.bintray.com/upennlib/metridoc"
        mavenRepo "http://dl.bintray.com/upennlib/maven"
        
    }

    plugins {
        //other plugins
        
        //you can change the illiad version here
        compile (":metridoc-illiad:0.4.2") {
            excludes "metridoc-core"
        }
    }
}
```

You can find the most recent version of the plugin [here](https://bintray.com/upennlib/metridoc/metridoc-illiad).  
`metridoc-core` is excluded in case the plugin is using an outdated version of `metridoc-core`.

This plugin assumes that you have run the [illiad job](http://github.com/metridoc/metridoc-job-illiad) to migrate the data 
into the central repo.  All the plugin does is create a basic dashboard of illiad stats and download buttons to grab the 
data.

If all goes well, you should see a dashboard at `http://metridoc.host:port/metridoc/illiad` similar to:

![Illiad Dashboard](https://raw.github.com/metridoc/metridoc-grails-illiad/master/docs/dashboardScreenForREADME.png)

The plugin only relies on two tables that were created by the
[illiad job](http://github.com/metridoc/metridoc-job-illiad), `ill_cache` and `ill_transaction`.  `ill_cache` contains
all the dashboard data in json format while `ill_transaction` provides all the data that can be downloaded.  See the 
[illiad job](http://github.com/metridoc/metridoc-job-illiad) for more details about the illiad table structure.


Local Development
-----------

Developing on a local instance of Illiad requires a local copy of the Illiad database tables. The following is a guide to acquiring these tables. Note that this guide assumes a remote server is set up with MySQL and.

1. Run the [Illiad Metridoc job](https://github.com/metridoc/metridoc-job/tree/master/metridoc-job-illiad) to populate the remote database with Illiad data.
2. ssh into your remote server and identify the Illiad tables. Use `mysql -u username -p password database_foo -e'show tables where Tables_in_database_foo like "ill_%"'` to find them quickly. 
3. Use mysqldump and gzip to export the desired tables: `sudo sh -c 'mysqldump -u username -p password database_foo ill_table... | gzip > /data/illTables.sql.gz'`
4. Use scp to transfer the zipped table data to your local machine and unzip the files. 
5. Import them into a local MySQL database: `mysql foo < ~/scratch/illTables.sql`


