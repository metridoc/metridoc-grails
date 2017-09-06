environments {
    development {
        dataSource {
            pooled = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/metridoc"
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            username = "root"
            password = "password"
            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
                validationQuery = "select 1"
            }
        }
    }

    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            pooled = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
        }
    }
}