metridoc-grails
===============

Please see the [wiki](https://github.com/metridoc/metridoc-grails/wiki) for documentation

App-specific README files:
--------------------------
* [Borrow Direct](https://github.com/metridoc/metridoc-grails/blob/master/metridoc-grails-bd/README.md)
* [Illiad](https://github.com/metridoc/metridoc-grails/blob/master/metridoc-grails-illiad/README.md)
* [RID (wiki)](https://github.com/metridoc/metridoc-grails/wiki/Metridoc-rid)


Installation Guide:
-------------------

### LANGUAGES and DEPENDENCIES

If you do not yet have the right version of Java, Groovy, and Grails, please install The Software Development Kit Manager by typing the following command in your terminal:

```sh
curl -s "https://get.sdkman.io" | bash
``` 

For more instruction on how to install the SDK, please refer to:
http://sdkman.io/install.html

After installing SDK, please install the right version of Java, Groovy, and Grils by typing:

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
sudo apt-get update sudo apt-get install mysql-server

/usr/bin/mysql_secure_installation
```
The second command will let you set up root user, password, and other options. You should put your credentials in the corresponding fields in the DataSource files. For simplicity, I suggest you to use "root" as your username and "password" as your password, since they are the default values in the files. The default port for MySQL server in DataSource files is 3306 and the default name of the database is "metridoc". You should have your server running on port 3306 and create a database called "metridoc" unless you modify the connection in DataSource files.

Then run this to start the server.

```sh
sudo service mysql start
```

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

After the build file finished running with no error, you can start the application in metridoc-app with the following command:

```sh
./grailsw run-app
```

You should then be able to see the application here:
http://localhost:8080/metridoc-reports/