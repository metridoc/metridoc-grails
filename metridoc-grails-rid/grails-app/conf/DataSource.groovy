dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"

    driverClassName = "org.h2.Driver"

    username = "root"
    password = "root"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            driverClassName = "com.mysql.jdbc.Driver"
            username = "root"
            password = "root"
//            dbCreate = "create-drop"
//            dbCreate "create"
//            dbCreate = "update"
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            url = "jdbc:mysql://localhost:3306/metridoc"
        }
    }
    test {
        dataSource {
            driverClassName = "org.h2.Driver"
//            dbCreate = "create-drop"
//            dbCreate "create"
            // dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            driverClassName = "com.mysql.jdbc.Driver"
            username = "root"
            password = "root"
            url = "jdbc:mysql://localhost:3306/metridoc"
//            driverClassName = "org.h2.Driver"
//            dbCreate = "update"
//            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            pooled = true
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
