metridoc-grails-gate Documentation
==================================

Purpose:
--------
Search, view, and download the number of library entries by category during a specified time interval.

Installation:
-------------
In order to use this plugin, 6 extra MySQL tables are needed in the database:
1. metridoc_gate_affiliation
2. metridoc_gate_center
3. metridoc_gate_department
4. metridoc_gate_door
5. metridoc_gate_USC
6. metridoc_gate_entry_record


To create and populate these tables, run the queries in the 6 sql files with their corresponding table names under the directory **sql_table_init**.

Then, Metridoc-app also needs to know the existence of a new plugin. Therefore, under the line 
```groovy
grails.plugin.location."metridoc-rid" = "../metridoc-grails/metridoc-grails-rid"
```
in the file metridoc-app/grails-app/config/BuildConfig.groovy, add the following line:
```groovy
grails.plugin.location."metridoc-gate" = "../metridoc-grails/metridoc-grails-gate"
```
In the same file, under the line:
```groovy
compile (":metridoc-funds:${metridocVersion}")
```
add the following line:
```groovy
compile (":metridoc-gate:${metridocVersion}")
```
Finally, in the same file, under the line:
```groovy
compile(":metridoc-funds:${getVersion('metridoc-bd')}")
```
add the following line:
```groovy
compile(":metridoc-gate:${getVersion('metridoc-gate')}")
```

Usage:
------
/metridoc-reports/gateTransaction/index:
This is the landing page of the application, which shows a bunch of filters when searching for library entry records. Both start time and end time are in military time (24hr) and are default to 00:00 (midnight) if nothing is entered. All categories default options are **All ____**. Users can select one or more options for each category. When users are satisfied with the filter settings, they can click search to query the database for result.

/metridoc-reports/gateTransaction/query?(filters):
This page shows the search result according to the filters specified by users. There are 3 tables on this page: 
1. Entry by different affiliations. 
2. Entry by different centers. 
3. Entry by different USCs. 
Entry by departments is omitted for now since there are over 600 departments in the database. 
When a link at the top of the page is clicked, the page will job to its corresponding result table. For example, clicking **Center Summary** will jump to the beginning of the result table by different centers. Clicking **back to top** link will take the user back to the top of the page.
When the **Download Data** button is clicked, the application will convert the 3 tables into 3 sheets and put them in 1 csv file and start the download process of the csv file for the user.
When the **Back** button is clicked, the application will take the user back to the search page.


Documentation by file:
----------------------
**grails-app/conf/GateSQLQueries.groovy**:
This file contains all SQL queries this application uses.

**grails-app/conf/MetridocGateResources.groovy**:
This file contains all pointers to web files (js and css) this application uses.

**grails-app/controllers/metridoc/gate/GateTransactionController.groovy**:
The only controller of this application. It has functions that renders the landing page of the application, grabs filters to search for results of library entry records, and exports the data as csv files.

**grails-app/services/metridoc/gate/gateService.groovy**:
The only service file of this application. It contains an interface to the database and also some helper functions to parse and format data and create spreadsheets to export as csv files.

**grails-app/views**:
This directory contains gsp files, which control what the application looks like in the browser.

**web-app/css/GateTransaction.css**:
This css file contains style for the result tables that display library entry records.

**web-app/js/GateTransaction.js**:
The content of this js file controls the date picker on the search page of this application.

