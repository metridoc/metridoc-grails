databaseChangeLog = {

    changeSet(author: "zhixu (generated)", id: "1477422681158-1") {
        createTable(tableName: "app_category") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "admin_only", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "icon_name", type: "VARCHAR(255)")

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-2") {
        createTable(tableName: "controller_data") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "app_description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "app_name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }

            column(name: "category_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "controller_path", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }

            column(name: "home_page", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "validity", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-3") {
        createTable(tableName: "crypt_key") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "encrypt_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-4") {
        createTable(tableName: "ldap_data") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "allow_empty_passwords", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "encrypt_strong", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "encrypted_password", type: "VARCHAR(255)")

            column(name: "group_search_base", type: "VARCHAR(255)")

            column(name: "group_search_filter", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "managerdn", type: "VARCHAR(255)")

            column(name: "rootdn", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "server", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "skip_authentication", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "skip_credentials_check", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "user_search_base", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_search_filter", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-5") {
        createTable(tableName: "ldap_role_mapping") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-6") {
        createTable(tableName: "ldap_role_mapping_shiro_role") {
            column(name: "ldap_role_mapping_roles_id", type: "BIGINT")

            column(name: "shiro_role_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-7") {
        createTable(tableName: "manage_report") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "controller_name", type: "VARCHAR(75)") {
                constraints(nullable: "false")
            }

            column(name: "is_protected", type: "BIT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-8") {
        createTable(tableName: "manage_report_shiro_role") {
            column(name: "manage_report_roles_id", type: "BIGINT")

            column(name: "shiro_role_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-9") {
        createTable(tableName: "notification_emails") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(75)") {
                constraints(nullable: "false")
            }

            column(name: "scope", type: "VARCHAR(75)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-10") {
        createTable(tableName: "remember_cookie_age") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "age_in_seconds", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-11") {
        createTable(tableName: "rid_cons_transaction") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "course_name", type: "VARCHAR(100)")

            column(name: "course_number", type: "VARCHAR(100)")

            column(name: "course_sponsor_id", type: "BIGINT")

            column(name: "date_of_consultation", type: "DATETIME") {
                constraints(nullable: "false")
            }

            column(name: "department_id", type: "BIGINT")

            column(name: "event_length", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "faculty_sponsor", type: "VARCHAR(100)")

            column(name: "interact_occurrences", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "mode_of_consultation_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "notes", type: "LONGTEXT")

            column(name: "prep_time", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "rank_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "school_id", type: "BIGINT")

            column(name: "service_provided_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "spreadsheet_name", type: "VARCHAR(255)")

            column(name: "staff_pennkey", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "user_goal_id", type: "BIGINT")

            column(name: "user_name", type: "VARCHAR(50)")

            column(name: "user_question", type: "LONGTEXT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-12") {
        createTable(tableName: "rid_cons_transaction_template") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "course_name", type: "VARCHAR(100)")

            column(name: "course_number", type: "VARCHAR(100)")

            column(name: "course_sponsor_id", type: "BIGINT")

            column(name: "date_of_consultation", type: "DATETIME") {
                constraints(nullable: "false")
            }

            column(name: "department_id", type: "BIGINT")

            column(name: "event_length", type: "INT")

            column(name: "faculty_sponsor", type: "VARCHAR(100)")

            column(name: "interact_occurrences", type: "INT")

            column(name: "mode_of_consultation_id", type: "BIGINT")

            column(name: "notes", type: "LONGTEXT")

            column(name: "prep_time", type: "INT")

            column(name: "rank_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")

            column(name: "school_id", type: "BIGINT")

            column(name: "service_provided_id", type: "BIGINT")

            column(name: "staff_pennkey", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "template_owner", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_goal_id", type: "BIGINT")

            column(name: "user_name", type: "VARCHAR(50)")

            column(name: "user_question", type: "LONGTEXT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-13") {
        createTable(tableName: "rid_course_sponsor") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-14") {
        createTable(tableName: "rid_department") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "full_name", type: "VARCHAR(255)")

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-15") {
        createTable(tableName: "rid_expertise") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-16") {
        createTable(tableName: "rid_ins_transaction") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "attendance_total", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "co_instructor_pennkey", type: "VARCHAR(100)")

            column(name: "course_name", type: "VARCHAR(100)")

            column(name: "course_number", type: "VARCHAR(100)")

            column(name: "date_of_instruction", type: "DATETIME") {
                constraints(nullable: "false")
            }

            column(name: "department_id", type: "BIGINT")

            column(name: "event_length", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "expertise_id", type: "BIGINT")

            column(name: "faculty_sponsor", type: "VARCHAR(100)")

            column(name: "instructional_materials_id", type: "BIGINT")

            column(name: "instructor_pennkey", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "location_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "notes", type: "LONGTEXT")

            column(name: "prep_time", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "requestor", type: "VARCHAR(50)")

            column(name: "rid_library_unit_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "school_id", type: "BIGINT")

            column(name: "sequence_name", type: "VARCHAR(100)")

            column(name: "sequence_unit", type: "INT")

            column(name: "session_description", type: "LONGTEXT")

            column(name: "session_type_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "spreadsheet_name", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-17") {
        createTable(tableName: "rid_ins_transaction_template") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "attendance_total", type: "INT")

            column(name: "co_instructor_pennkey", type: "VARCHAR(100)")

            column(name: "course_name", type: "VARCHAR(100)")

            column(name: "course_number", type: "VARCHAR(100)")

            column(name: "date_of_instruction", type: "DATETIME") {
                constraints(nullable: "false")
            }

            column(name: "department_id", type: "BIGINT")

            column(name: "event_length", type: "INT")

            column(name: "faculty_sponsor", type: "VARCHAR(100)")

            column(name: "instructional_materials_id", type: "BIGINT")

            column(name: "instructor_pennkey", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "location_id", type: "BIGINT")

            column(name: "notes", type: "LONGTEXT")

            column(name: "other_expertise", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "prep_time", type: "INT")

            column(name: "requestor", type: "VARCHAR(50)")

            column(name: "rid_library_unit_id", type: "BIGINT")

            column(name: "school_id", type: "BIGINT")

            column(name: "sequence_name", type: "VARCHAR(100)")

            column(name: "sequence_unit", type: "INT")

            column(name: "session_description", type: "LONGTEXT")

            column(name: "session_type_id", type: "BIGINT")

            column(name: "template_owner", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-18") {
        createTable(tableName: "rid_instructional_materials") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-19") {
        createTable(tableName: "rid_library_unit") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-20") {
        createTable(tableName: "rid_location") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-21") {
        createTable(tableName: "rid_mode_of_consultation") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-22") {
        createTable(tableName: "rid_rank") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-23") {
        createTable(tableName: "rid_school") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-24") {
        createTable(tableName: "rid_service_provided") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-25") {
        createTable(tableName: "rid_session_type") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-26") {
        createTable(tableName: "rid_statistics_graph_report") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "transaction_sum", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-27") {
        createTable(tableName: "rid_statistics_graph_report_trans_by_date") {
            column(name: "trans_by_date", type: "BIGINT")

            column(name: "trans_by_date_idx", type: "VARCHAR(255)")

            column(name: "trans_by_date_elt", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-28") {
        createTable(tableName: "rid_statistics_report") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "avg_event_length", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "avg_interact_occurrences", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "avg_prep_time", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "courses_added", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "pennkey_max", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "staff_pennkey", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "total_event_length", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "total_interact_occurences", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "total_prep_time", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "total_transactions", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "year_event_length", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "year_interact_occurences", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "year_prep_time", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "year_transactions", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-29") {
        createTable(tableName: "rid_statistics_search_report") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "transaction_sum", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-30") {
        createTable(tableName: "rid_test") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "test", type: "VARCHAR(150)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-31") {
        createTable(tableName: "rid_test_rid_cons_transaction") {
            column(name: "rid_test_rid_cons_transaction_id", type: "BIGINT")

            column(name: "rid_cons_transaction_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-32") {
        createTable(tableName: "rid_test_rid_ins_transaction") {
            column(name: "rid_test_rid_ins_transaction_id", type: "BIGINT")

            column(name: "rid_ins_transaction_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-33") {
        createTable(tableName: "rid_user_goal") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "in_form", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rid_library_unit_id", type: "BIGINT")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-34") {
        createTable(tableName: "shiro_role") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(75)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-35") {
        createTable(tableName: "shiro_role_permissions") {
            column(name: "shiro_role_id", type: "BIGINT")

            column(name: "permissions_string", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-36") {
        createTable(tableName: "shiro_user") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "email_address", type: "VARCHAR(75)") {
                constraints(nullable: "false")
            }

            column(name: "password_hash", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(75)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-37") {
        createTable(tableName: "shiro_user_permissions") {
            column(name: "shiro_user_id", type: "BIGINT")

            column(name: "permissions_string", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-38") {
        createTable(tableName: "shiro_user_roles") {
            column(name: "shiro_role_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "shiro_user_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-39") {
        addPrimaryKey(columnNames: "shiro_user_id, shiro_role_id", tableName: "shiro_user_roles")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-88") {
        createIndex(indexName: "name", tableName: "app_category", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-89") {
        createIndex(indexName: "app_name", tableName: "controller_data", unique: "true") {
            column(name: "app_name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-90") {
        createIndex(indexName: "controller_path", tableName: "controller_data", unique: "true") {
            column(name: "controller_path")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-91") {
        createIndex(indexName: "name", tableName: "ldap_role_mapping", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-92") {
        createIndex(indexName: "idx_report_permissions_controller_name", tableName: "manage_report", unique: "false") {
            column(name: "controller_name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-93") {
        createIndex(indexName: "scope", tableName: "notification_emails", unique: "true") {
            column(name: "scope")

            column(name: "email")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-94") {
        createIndex(indexName: "name", tableName: "rid_course_sponsor", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-95") {
        createIndex(indexName: "name", tableName: "rid_department", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-96") {
        createIndex(indexName: "name", tableName: "rid_expertise", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-97") {
        createIndex(indexName: "name", tableName: "rid_library_unit", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-98") {
        createIndex(indexName: "name", tableName: "rid_rank", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-99") {
        createIndex(indexName: "name", tableName: "rid_school", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-100") {
        createIndex(indexName: "test", tableName: "rid_test", unique: "true") {
            column(name: "test")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-101") {
        createIndex(indexName: "name", tableName: "shiro_role", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-102") {
        createIndex(indexName: "email_address", tableName: "shiro_user", unique: "true") {
            column(name: "email_address")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-103") {
        createIndex(indexName: "username", tableName: "shiro_user", unique: "true") {
            column(name: "username")
        }
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-40") {
        addForeignKeyConstraint(baseColumnNames: "category_id", baseTableName: "controller_data", baseTableSchemaName: "metridoc", constraintName: "FKE825266DC19009D5", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "app_category", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-41") {
        addForeignKeyConstraint(baseColumnNames: "ldap_role_mapping_roles_id", baseTableName: "ldap_role_mapping_shiro_role", baseTableSchemaName: "metridoc", constraintName: "FKE029BE6BDEEE42A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "ldap_role_mapping", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-42") {
        addForeignKeyConstraint(baseColumnNames: "shiro_role_id", baseTableName: "ldap_role_mapping_shiro_role", baseTableSchemaName: "metridoc", constraintName: "FKE029BE6E074BD57", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "shiro_role", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-43") {
        addForeignKeyConstraint(baseColumnNames: "manage_report_roles_id", baseTableName: "manage_report_shiro_role", baseTableSchemaName: "metridoc", constraintName: "FK47F029958BB98C4D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "manage_report", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-44") {
        addForeignKeyConstraint(baseColumnNames: "shiro_role_id", baseTableName: "manage_report_shiro_role", baseTableSchemaName: "metridoc", constraintName: "FK47F02995E074BD57", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "shiro_role", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-45") {
        addForeignKeyConstraint(baseColumnNames: "course_sponsor_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C2FAFC0B60", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_course_sponsor", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-46") {
        addForeignKeyConstraint(baseColumnNames: "department_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C248229FBD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_department", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-47") {
        addForeignKeyConstraint(baseColumnNames: "mode_of_consultation_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C282AF530B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_mode_of_consultation", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-48") {
        addForeignKeyConstraint(baseColumnNames: "rank_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C2E83427BD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_rank", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-49") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C269ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-50") {
        addForeignKeyConstraint(baseColumnNames: "school_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C2DBEF2DFD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_school", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-51") {
        addForeignKeyConstraint(baseColumnNames: "service_provided_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C26E1630C2", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_service_provided", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-52") {
        addForeignKeyConstraint(baseColumnNames: "user_goal_id", baseTableName: "rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FKF73AE3C2CD628AD4", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_user_goal", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-53") {
        addForeignKeyConstraint(baseColumnNames: "course_sponsor_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C3457FAFC0B60", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_course_sponsor", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-54") {
        addForeignKeyConstraint(baseColumnNames: "department_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C345748229FBD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_department", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-55") {
        addForeignKeyConstraint(baseColumnNames: "mode_of_consultation_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C345782AF530B", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_mode_of_consultation", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-56") {
        addForeignKeyConstraint(baseColumnNames: "rank_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C3457E83427BD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_rank", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-57") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C345769ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-58") {
        addForeignKeyConstraint(baseColumnNames: "school_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C3457DBEF2DFD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_school", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-59") {
        addForeignKeyConstraint(baseColumnNames: "service_provided_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C34576E1630C2", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_service_provided", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-60") {
        addForeignKeyConstraint(baseColumnNames: "user_goal_id", baseTableName: "rid_cons_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FK7F9C3457CD628AD4", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_user_goal", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-61") {
        addForeignKeyConstraint(baseColumnNames: "department_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DB48229FBD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_department", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-62") {
        addForeignKeyConstraint(baseColumnNames: "expertise_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DB91C3B077", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_expertise", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-63") {
        addForeignKeyConstraint(baseColumnNames: "instructional_materials_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DB4015B10A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_instructional_materials", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-64") {
        addForeignKeyConstraint(baseColumnNames: "location_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DB901159D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_location", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-65") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DB69ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-66") {
        addForeignKeyConstraint(baseColumnNames: "school_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DBDBEF2DFD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_school", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-67") {
        addForeignKeyConstraint(baseColumnNames: "session_type_id", baseTableName: "rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKD4E5A8DBCBB97FA4", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_session_type", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-68") {
        addForeignKeyConstraint(baseColumnNames: "department_id", baseTableName: "rid_ins_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FKC06B1B5E48229FBD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_department", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-69") {
        addForeignKeyConstraint(baseColumnNames: "instructional_materials_id", baseTableName: "rid_ins_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FKC06B1B5E4015B10A", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_instructional_materials", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-70") {
        addForeignKeyConstraint(baseColumnNames: "location_id", baseTableName: "rid_ins_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FKC06B1B5E901159D", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_location", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-71") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_ins_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FKC06B1B5E69ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-72") {
        addForeignKeyConstraint(baseColumnNames: "school_id", baseTableName: "rid_ins_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FKC06B1B5EDBEF2DFD", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_school", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-73") {
        addForeignKeyConstraint(baseColumnNames: "session_type_id", baseTableName: "rid_ins_transaction_template", baseTableSchemaName: "metridoc", constraintName: "FKC06B1B5ECBB97FA4", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_session_type", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-74") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_instructional_materials", baseTableSchemaName: "metridoc", constraintName: "FKEEF87B7469ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-75") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_location", baseTableSchemaName: "metridoc", constraintName: "FK624DF6A769ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-76") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_mode_of_consultation", baseTableSchemaName: "metridoc", constraintName: "FK26C8094769ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-77") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_service_provided", baseTableSchemaName: "metridoc", constraintName: "FK6E0A711F69ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-78") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_session_type", baseTableSchemaName: "metridoc", constraintName: "FKE24E813569ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-79") {
        addForeignKeyConstraint(baseColumnNames: "rid_cons_transaction_id", baseTableName: "rid_test_rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FK7DFED49DD76B3C42", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_cons_transaction", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-80") {
        addForeignKeyConstraint(baseColumnNames: "rid_test_rid_cons_transaction_id", baseTableName: "rid_test_rid_cons_transaction", baseTableSchemaName: "metridoc", constraintName: "FK7DFED49DE7D1F612", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_test", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-81") {
        addForeignKeyConstraint(baseColumnNames: "rid_ins_transaction_id", baseTableName: "rid_test_rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKE9C2B0A0CB1606D2", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_ins_transaction", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-82") {
        addForeignKeyConstraint(baseColumnNames: "rid_test_rid_ins_transaction_id", baseTableName: "rid_test_rid_ins_transaction", baseTableSchemaName: "metridoc", constraintName: "FKE9C2B0A0A531F72F", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_test", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-83") {
        addForeignKeyConstraint(baseColumnNames: "rid_library_unit_id", baseTableName: "rid_user_goal", baseTableSchemaName: "metridoc", constraintName: "FK43037A5569ECFC", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "rid_library_unit", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-84") {
        addForeignKeyConstraint(baseColumnNames: "shiro_role_id", baseTableName: "shiro_role_permissions", baseTableSchemaName: "metridoc", constraintName: "FK389B46C9E074BD57", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "shiro_role", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-85") {
        addForeignKeyConstraint(baseColumnNames: "shiro_user_id", baseTableName: "shiro_user_permissions", baseTableSchemaName: "metridoc", constraintName: "FK34555A9E859F8137", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "shiro_user", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-86") {
        addForeignKeyConstraint(baseColumnNames: "shiro_role_id", baseTableName: "shiro_user_roles", baseTableSchemaName: "metridoc", constraintName: "FKBA221057E074BD57", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "shiro_role", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    changeSet(author: "zhixu (generated)", id: "1477422681158-87") {
        addForeignKeyConstraint(baseColumnNames: "shiro_user_id", baseTableName: "shiro_user_roles", baseTableSchemaName: "metridoc", constraintName: "FKBA221057859F8137", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "shiro_user", referencedTableSchemaName: "metridoc", referencesUniqueColumn: "false")
    }

    include file: '2016-10-25-move-expertise.groovy'
}
