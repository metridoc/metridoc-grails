metridoc-grails-gate Documentation
==================================

Purpose:
--------
Search, view, and download the number of library entries by category during a specified time interval.

Installation:
-------------
In order to use this plugin, 6 extra MySQL tables are needed in the database:
    metridoc_gate_affiliation
    metridoc_gate_center
    metridoc_gate_department
    metridoc_gate_door
    metridoc_gate_USC
    metridoc_gate_entry_record
To create and populate these tables, run the queries in the 6 sql files with their corresponding table names under the directory “sql_table_init”.

Then, Metridoc-app also needs to know the existence of a new plugin. Therefore, under the line 
grails.plugin.location."metridoc-rid" = "../metridoc-grails/metridoc-grails-rid"
in the file metridoc-app/grails-app/config/BuildConfig.groovy, add the following line:
grails.plugin.location."metridoc-gate" = "../metridoc-grails/metridoc-grails-gate"
In the same file, under the line:
compile (":metridoc-funds:${metridocVersion}")
add the following line:
compile (":metridoc-gate:${metridocVersion}")
Finally, in the same file, under the line:
compile(":metridoc-funds:${getVersion('metridoc-bd')}")
add the following line:
compile(":metridoc-gate:${getVersion('metridoc-gate')}")

Usage:
------
/metridoc-reports/gateTransaction/index:
This is the landing page of the application, which shows a bunch of filters when searching for library entry records. Both start time and end time are in military time (24hr) and are default to 00:00 (midnight) if nothing is entered. All categories default options are “All ____”. Users can select one or more options for each category. When users are satisfied with the filter settings, they can click search to query the database for result.

/metridoc-reports/gateTransaction/query?(filters):
This page shows the search result according to the filters specified by users. There are 3 tables on this page: 1. Entry by different affiliations. 2. Entry by different centers. 3. Entry by different USCs. Entry by departments is omitted for now since there are over 600 departments in the database. 
When a link at the top of the page is clicked, the page will job to its corresponding result table. For example, clicking “Center Summary” will jump to the beginning of the result table by different centers. Clicking “back to top” link will take the user back to the top of the page.
When the “Download Data” button is clicked, the application will convert the 3 tables into 3 sheets and put them in 1 csv file and start the download process of the csv file for the user.
When the “Back” button is clicked, the application will take the user back to the search page.

Structure and files:
--------------------
.
├── application.properties
├── Create table statement
├── grails-app
│   ├── conf
│   │   ├── BuildConfig.groovy
│   │   ├── Config.groovy
│   │   ├── DataSource.groovy
│   │   ├── GateSQLQueries.groovy
│   │   ├── hibernate
│   │   ├── MetridocGateResources.groovy
│   │   ├── spring
│   │   └── UrlMappings.groovy
│   ├── controllers
│   │   └── metridoc
│   │       └── gate
│   │           └── GateTransactionController.groovy
│   ├── domain
│   │   └── metridoc
│   │       └── gate
│   │           └── GateDoor.groovy
│   ├── i18n
│   │   └── shiro.properties
│   ├── realms
│   ├── services
│   │   └── metridoc
│   │       └── gate
│   │           └── gateService.groovy
│   ├── taglib
│   ├── utils
│   └── views
│       ├── error.gsp
│       ├── gateTransaction
│       │   ├── index.gsp
│       │   └── searchResult.gsp
│       └── layouts
├── grails-metridoc-gate-0.9.0.zip
├── grails-metridoc-gate-0.9.0.zip.sha1
├── grailsw
├── grailsw.bat
├── lib
├── MetridocGateGrailsPlugin.groovy
├── plugin.xml
├── scripts
│   ├── _Install.groovy
│   ├── _Uninstall.groovy
│   └── _Upgrade.groovy
├── src
│   ├── groovy
│   └── java
├── target
│   ├── classes
│   │   ├── application.properties
│   │   ├── BuildConfig.class
│   │   ├── BuildConfig$_run_closure1.class
│   │   ├── BuildConfig$_run_closure1_closure2.class
│   │   ├── BuildConfig$_run_closure1_closure3.class
│   │   ├── BuildConfig$_run_closure1_closure3_closure5.class
│   │   ├── BuildConfig$_run_closure1_closure4.class
│   │   ├── BuildConfig$_run_closure1_closure4_closure6.class
│   │   ├── BuildConfig$_run_closure1_closure4_closure7.class
│   │   ├── Config.class
│   │   ├── Config$_run_closure1.class
│   │   ├── Config$_run_closure1_closure3.class
│   │   ├── Config$_run_closure1_closure4.class
│   │   ├── Config$_run_closure2.class
│   │   ├── Config$_run_closure2_closure5.class
│   │   ├── Config$_run_closure2_closure6.class
│   │   ├── DataSource.class
│   │   ├── DataSource$_run_closure1.class
│   │   ├── DataSource$_run_closure2.class
│   │   ├── DataSource$_run_closure3.class
│   │   ├── DataSource$_run_closure3_closure4.class
│   │   ├── DataSource$_run_closure3_closure4_closure7.class
│   │   ├── DataSource$_run_closure3_closure5.class
│   │   ├── DataSource$_run_closure3_closure5_closure8.class
│   │   ├── DataSource$_run_closure3_closure6.class
│   │   ├── DataSource$_run_closure3_closure6_closure9.class
│   │   ├── DataSource$_run_closure3_closure6_closure9_closure10.class
│   │   ├── gateSQLQueries.class
│   │   ├── GateSQLQueries.class
│   │   ├── gateSQLQueries$_run_closure1.class
│   │   ├── GateSQLQueries$_run_closure1.class
│   │   ├── metridoc
│   │   │   └── gate
│   │   │       ├── GateAffiliation.class
│   │   │       ├── GateAffiliation$__clinit__closure1.class
│   │   │       ├── GateCenter.class
│   │   │       ├── GateCenter$__clinit__closure1.class
│   │   │       ├── GateDepartment.class
│   │   │       ├── GateDepartment$__clinit__closure1.class
│   │   │       ├── GateDoor.class
│   │   │       ├── GateDoor$__clinit__closure1.class
│   │   │       ├── GateEntryRecord.class
│   │   │       ├── GateEntryRecord$__clinit__closure1.class
│   │   │       ├── GateSearchService.class
│   │   │       ├── gateService.class
│   │   │       ├── gateService$_exportAsFile_closure2.class
│   │   │       ├── gateService$_query_closure1.class
│   │   │       ├── GateTransactionController.class
│   │   │       ├── GateTransactionController$_createNameArray_closure1.class
│   │   │       ├── GateTransactionController$_createNameArray_closure2.class
│   │   │       ├── GateTransactionController$_createNameArray_closure7.class
│   │   │       ├── GateTransactionController$_processTableData_closure2.class
│   │   │       ├── GateTransactionController$_processTableData_closure2_closure4.class
│   │   │       ├── GateTransactionController$_processTableData_closure3.class
│   │   │       ├── GateTransactionController$_query_closure1.class
│   │   │       ├── GateTransactionController$_query_closure1_closure3.class
│   │   │       ├── GateTransactionController$_query_closure1_closure8.class
│   │   │       ├── GateTransactionController$_query_closure2.class
│   │   │       ├── GateTransactionController$_query_closure3.class
│   │   │       ├── GateTransactionController$_query_closure3_closure9.class
│   │   │       ├── GateTransactionController$_query_closure4.class
│   │   │       ├── GateTransactionController$_query_closure5.class
│   │   │       ├── GateTransactionController$_query_closure5_closure10.class
│   │   │       ├── GateTransactionController$_query_closure6.class
│   │   │       ├── GateUSC.class
│   │   │       └── GateUSC$__clinit__closure1.class
│   │   ├── MetridocGateGrailsPlugin.class
│   │   ├── MetridocGateGrailsPlugin$_closure1.class
│   │   ├── MetridocGateResources.class
│   │   ├── MetridocGateResources$_run_closure1.class
│   │   ├── MetridocGateResources$_run_closure1_closure2.class
│   │   ├── UrlMappings.class
│   │   ├── UrlMappings$__clinit__closure1.class
│   │   ├── UrlMappings$__clinit__closure1_closure2.class
│   │   └── UrlMappings$__clinit__closure1_closure2_closure3.class
│   ├── grails-metridoc-gate-0.9.0.zip.sha1
│   ├── grails-metridoc-grails-gate-0.1.0.zip.sha1
│   ├── grails-metridoc-grails-gate-0.9.0.zip.sha1
│   ├── pom.xml
│   ├── pom.xml.sha1
│   ├── test-classes
│   │   ├── SpockGrailsPlugin.class
│   │   └── unit
│   └── test-reports
│       ├── html
│       │   ├── all.html
│       │   ├── failed.html
│       │   ├── index.html
│       │   └── stylesheet.css
│       ├── plain
│       └── TESTS-TestSuites.xml
├── test
│   ├── integration
│   └── unit
├── web-app
│   ├── css
│   │   └── GateTransaction.css
│   ├── images
│   ├── js
│   │   └── GateTransaction.js
│   ├── META-INF
│   └── WEB-INF
│       ├── applicationContext.xml
│       ├── sitemesh.xml
│       └── tld
│           ├── c.tld
│           ├── fmt.tld
│           ├── grails.tld
│           ├── spring-form.tld
│           └── spring.tld
└── wrapper
    ├── grails-wrapper.properties
    ├── grails-wrapper-runtime-2.3.4.jar
    └── springloaded-core-1.1.4.jar

45 directories, 120 files

Documentation by file:
----------------------
grails-app/conf/GateSQLQueries.groovy:
This file contains all SQL queries this application uses.

grails-app/conf/MetridocGateResources.groovy:
This file contains all pointers to web files (js and css) this application uses.

grails-app/controllers/metridoc/gate/GateTransactionController.groovy:
The only controller of this application. It has functions that renders the landing page of the application, grabs filters to search for results of library entry records, and exports the data as csv files.

grails-app/services/metridoc/gate/gateService.groovy:
The only service file of this application. It contains an interface to the database and also some helper functions to parse and format data and create spreadsheets to export as csv files.

 grails-app/views:
This directory contains gsp files, which control what the application looks like in the browser.

web-app/css/GateTransaction.css:
This css file contains style for the result tables that display library entry records.

web-app/js/GateTransaction.js:
The content of this js file controls the date picker on the search page of this application.

