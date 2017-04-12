metridoc-grails
===============

Please see the [wiki](https://github.com/metridoc/metridoc-grails/wiki) for documentation

App-specific README files:
--------------------------
* [Borrow Direct](https://github.com/metridoc/metridoc-grails/blob/master/metridoc-grails-bd/README.md)
* [Illiad](https://github.com/metridoc/metridoc-grails/blob/master/metridoc-grails-illiad/README.md)
* [RID (wiki)](https://github.com/metridoc/metridoc-grails/wiki/Metridoc-rid)

Requirements:
=============

Java 1.7.0_80

Groovy 2.4.10

Grails 2.3.4


Installation Guide:
===================
(This installation guide is written for Ubuntu 16.04)

### LANGUAGES and DEPENDENCIES

If you do not yet have the right version of Java, Groovy, and Grails, please install The Software Development Kit Manager by typing the following command in your terminal:

(You can check if you already have sdk installed by typing "sdk version")

```sh
curl -s "https://get.sdkman.io" | bash
``` 

After this command, the terminal will print a message asking you to finalize the installation of SDK with a command similar to this

```sh
source "~/.sdkman/bin/sdkman-init.sh"
```

Please run that command and continue with the installation

For more instruction on how to install the SDK, please refer to:
http://sdkman.io/install.html


After installing SDK, please install the correct version of Java, Groovy, and Grails by typing:

```sh
sdk install java 7u80

sdk install groovy 2.4.10

sdk install grails 2.3.4
```

You can check what version you are currently using by typing:

```sh
sdk current
```

If you are using a version not specified above, please switch to the correct version by typing:

```sh
sdk use java 7u80

sdk use groovy 2.4.10

sdk use grails2.3.4
```

For more information about how to install and switch to the correct version, please refer to: http://sdkman.io/usage.html


### MYSQL SETUP

You will also need to connect to a MySQL database to run the application.

If you already have a MySQL connection, you can edit your connection and login information in the DataSource files located at metridoc-grails/metridoc-grails-"plugin"/grails-app/conf/DataSource.groovy.

If you do not have a MySQL connection, you can install and start a MySQL server locally. Here are the instructions:

```sh
sudo apt-get update 

sudo apt-get install mysql-server
```
The default port for MySQL server in DataSource files is 3306 and the default name of the database is "metridoc". You should have your server running on port 3306 and create a database called "metridoc" unless you modify the connection in DataSource files.

To create a database called "metridoc", please do the following:

```sh
mysql -u root -p
```
Then in the mysql prompt, type:

```sql
CREATE DATABASE metridoc

quit
```

Then run this to start the server.

```sh
sudo service mysql start
```
If you wish to create a table in the database, please refer to this page:https://dev.mysql.com/doc/refman/5.5/en/creating-tables.html

Please note that you do not need to manually create any tables for the application, it will create them for you automatically when you run it.

If later when you run the application, it gives you an error that database connection refused or failed, please check whether your MySQL server is running and whether you have your application connected to the right port with the right credentials.


### NECESSARY REPOSITORIES

Go to the directory where you wish to install and run metridoc, then type the following commands to clone metridoc-grails and metridoc-app:

```sh
git clone https://github.com/metridoc/metridoc-grails

git clone https://github.com/metridoc/metridoc-app
```

### BUILD and RUN

Go to metridoc-app

```sh
cd metridoc-app
```


And then run the build file:

```sh
./buildAll.sh
```  

or

```sh
sh ./buildAll.sh
```
(If you run into an error related to JAVA_HOME environment varialble not being set, please refer to the "Other Potential Problems section below")

This build process will take a while if this is your first time building the application, as it needs to download all necessary packages and dependencies.

After the build file finished running with no error (you probably will see some warning messages, but they are fine. As long as the process does not terminate by itself with an error, it should have built successfully), you need to add a configuration file in order to connect the application with the database. Go to your home directory and then cd into the metridoc folder by typing:

```sh
cd

cd .metridoc 
```
In this directory, please add a file called "MetridocConfig.groovy" and replace its content with the following code snipped:

```groovy
environments {
    development {
        dataSource {
    pooled = true
    dbCreate = "update"
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = MySQL5InnoDBDialect
    url = "jdbc:mysql://localhost:3306/metridoc"
    password = "YOUR PASSWORD"
    username = "YOUR USERNAME"
    properties {
        maxActive = -1
        minEvictableIdleTimeMillis = 1800000
        timeBetweenEvictionRunsMillis = 1800000
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1"
      }
    }
  }
}
```
Be sure to replace "YOUR PASSWORD" and "YOUR USERNAME" with your actual password and username to your MySQL database.

You can then start the application in metridoc-app with the following command:

```sh
./grailsw run-app
```

You should then be able to see the application here:
http://localhost:8080/metridoc-reports/

To test and sign into the application, the default username is "admin" and the password is "password".

### Other Potential Problems

#### JAVA_HOME environment variable problem

When running buildAll.sh, if it exits with an error "JAVA_HOME environment variable is not set", please refer to this page and set up your JAVA_HOME variable:
https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/

or you can also simply type the following commands:

```sh
export JAVA_HOME="~/.sdkman/candidates/java/current"

export PATH="/bin:$PATH JAVA_HOME"
```

If these commands do not work, please try this set of commands:

```sh
export JAVA_HOME="~/.sdkman/candidates/java/current"

export PATH="$PATH:$JAVA_HOME/bin"
```

If after the above commands, you still run into the same error when you try to build the app, please make sure you have closed the terminal where you applied the change and then opened a new terminal to build the app in the application folder.

If the above directory does not exist, you should be able to find where your Java is installed this way:

Go to your home directory by typing
```sh
cd
```
Then you can go to the sdk directory
```sh
cd .sdkman
```
Inside the .sdkman folder, there should be a "candidates" folder. And inside that folder, there should be folders of languages you installed through sdkman. In our case, there should be 3 folders each for Grails, Groovy, and Java. Inside the Java folder, you should be able to find the correct directory for JAVA_HOME.