metridoc-core
=============
[![Build Status](https://drone.io/github.com/metridoc/metridoc-core/status.png)](https://drone.io/github.com/metridoc/metridoc-core/latest)

all grails functionality for metridoc views / grails plugins.  More information available in the wiki at 
https://github.com/metridoc/metridoc-wiki/wiki/Metridoc-grails-core.

#### Installation
If you are generating or using views, it is recommended that you use the metridoc template 
[app](https://github.com/metridoc/metridoc-template-grails-app) to get started.  In general, the plugin installs like a 
standard grails plugin.  The plugin itself is stored in maven format at bintray 
[here](https://bintray.com/upennlib/metridoc).  To make grails find it, your `grails-app/conf/BuildConfig.groovy`
should look like:

```groovy
grails.project.dependency.resolution = {
    
    repositories {
        mavenRepo "http://dl.bintray.com/upennlib/metridoc"
    }
    
    plugins {
        compile ':metridoc-core:0.7.7'
    }

}
```

