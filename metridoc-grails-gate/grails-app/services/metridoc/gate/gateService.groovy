package metridoc.gate

import groovy.sql.Sql
import org.slf4j.LoggerFactory

import javax.sql.DataSource
import java.sql.ResultSet

class gateService {
	DataSource dataSource;
	def grailsApplication;

	private getSql() {
		return new Sql(dataSource);
	}

	ConfigObject getConfig() {
        grailsApplication.config
    }

    def getAllDoors() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllDoors);
    }

    def getAllAffiliations() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllAffiliations);	
    }

    def getAllCenters() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllCenters);
    }

    def getAllDepartments() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllDepartments);
    }

    def getAllUSCs() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllUSCs);
    }


}