databaseChangeLog = {

	changeSet(author: "zhixu (generated)", id: "1477424102041-1") {
		addColumn(tableName: "rid_cons_transaction") {
			column(name: "expertise_id", type: "bigint")
		}
	}

	changeSet(author: "zhixu (generated)", id: "1477424102041-2") {
		addColumn(tableName: "rid_cons_transaction_template") {
			column(name: "other_expertise", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "zhixu (generated)", id: "1477424102041-3") {
		dropForeignKeyConstraint(baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DB91C3B077")
	}

	changeSet(author: "zhixu (generated)", id: "1477424102041-5") {
		createIndex(indexName: "FKF73AE3C291C3B077", tableName: "rid_cons_transaction") {
			column(name: "expertise_id")
		}
	}

	changeSet(author: "zhixu (generated)", id: "1477424102041-6") {
		dropColumn(columnName: "expertise_id", tableName: "rid_ins_transaction")
	}

	changeSet(author: "zhixu (generated)", id: "1477424102041-7") {
		dropColumn(columnName: "other_expertise", tableName: "rid_ins_transaction_template")
	}

	changeSet(author: "zhixu (generated)", id: "1477424102041-4") {
		addForeignKeyConstraint(baseColumnNames: "expertise_id", baseTableName: "rid_cons_transaction", constraintName: "FKF73AE3C291C3B077", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "rid_expertise", referencesUniqueColumn: "false")
	}
}
