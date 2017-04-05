metridoc-grails
===============

To set up, run ./buildAll.sh in your terminal at the file's directory.

Please see the [wiki](https://github.com/metridoc/metridoc-grails/wiki) for documentation

App-specific README files:
--------------------------
* [Borrow Direct](https://github.com/metridoc/metridoc-grails/blob/master/metridoc-grails-bd/README.md)
* [Illiad](https://github.com/metridoc/metridoc-grails/blob/master/metridoc-grails-illiad/README.md)
* [RID (wiki)](https://github.com/metridoc/metridoc-grails/wiki/Metridoc-rid)


Installation Guide:
-------------------

* LANGUAGES and DEPENDENCIES:

If you do not yet have the right version of Java, Groovy, and Grails, please install The Software Development Kit Manager by typing

```sh
curl -s "https://get.sdkman.io" | bash
```

in your terminal. 

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

* NECESSARY REPOSITORIES:

Go to the directory where you wish to install and run metridoc, then type the following commands to clone metridoc-grails and metridoc-app:

```sh
git clone https://github.com/metridoc/metridoc-grails

git clone https://github.com/metridoc/metridoc-app
```

* BUILD and RUN:

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