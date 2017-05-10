/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  *	Educational Community License, Version 2.0 (the "License"); you may
  *	not use this file except in compliance with the License. You may
  *	obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  *	Unless required by applicable law or agreed to in writing,
  *	software distributed under the License is distributed on an "AS IS"
  *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  *	or implied. See the License for the specific language governing
  *	permissions and limitations under the License.
  */

package metridoc.rid

import org.apache.commons.lang.RandomStringUtils

class TestDataService {
    /**
     * For each domain object, check if any exist in the database
     * If not, populate the table with initial domain objects
     */
    List<String> lUnit = Arrays.asList("Commons","HSL", "CDM", "LIPPINCOTT", "TRL",
            "Science Libraries", "Canvas", "General", "KISLAK")
    List<String> dps = Arrays.asList(
            "AAMW", "ABIO", "ACCT", "ADMN", "ADMS", "AFAM", "AFRC", "AFST", "AMCS", "AMCV", "AMES",
            "ANAT", "ANCH", "ANCS", "ANNS", "ANTH", "ARBR", "ARCH", "ARTH", "AS", "ASAM", "ASTR",
            "BE", "BENF", "BEPP", "BIBB", "BIOC", "BIOL", "BIOM", "BIOP", "BIOT", "BLAW", "BMB", "BMP",
            "BURS", "CAMB", "CBE", "CELL", "CENG", "CGS", "CHE", "CHEM", "CINE", "CIS", "CISE", "CIT",
            "CIVE", "CLAR", "CLCT", "CLSD", "CLST", "CMSC", "COGS", "COLH", "COLL", "COML", "COMM",
            "CONV", "CPLN", "CRIM", "CSEN", "DANH", "DBCH", "DCAR", "DCOH", "DEMG", "DEND", "DENT",
            "DENV", "DMCB", "DOMD", "DORT", "DOSP", "DPED", "DPRD", "DPTH", "DRAD", "DRST", "DSCI",
            "DYNM", "EALC", "EARC", "EAS", "ECAP", "ECON", "EDUC", "EE", "EENG", "EESC", "EMTM", "ENAS",
            "ENGL", "ENMG", "ENVS", "EPID", "ESE", "EXEN", "FILM", "FINA", "FNAR", "FNCE", "FOLK",
            "GAFH", "GAFL", "GAS", "GCB", "GENE", "GENH", "GEOL", "GEPH", "GRMN", "GSFA", "GSWS", "HCAD",
            "HCMG", "HGEN", "HIST", "HSOC", "HSPV", "HSSC", "HSTP", "HUMA", "IDAT", "IMUN", "INSC",
            "INSP", "INTL", "INTR", "INTS", "IPD", "JIO", "JWST", "LALS", "LARP", "LAUD", "LAW", "LGIC",
            "LGST", "LIBS", "LING", "LSMP", "LTAM", "MAPP", "MATH", "MEAM", "MED", "MEDE", "MGMT", "MICB",
            "MICR", "MKSE", "MKTG", "MMES", "MMP", "MOLB", "MSCI", "MSE", "MSEN", "MUSC", "NELC", "NETS",
            "NGG", "NSCI", "NURS", "OBGY", "OPIM", "OPRE", "ORST", "PARA", "PATH", "PEDI", "PHIL", "PHRM",
            "PHSO", "PHYS", "PPE", "PPMT", "PRES", "PROV", "PSCI", "PSOS", "PSYC", "PTHB", "PUBH", "PYCH",
            "RADI", "REAL", "REGR", "RELS", "ROML", "ROMP", "RSCI", "RSMD", "SARS", "SAST", "SEAS", "SENG",
            "SGER", "SLAV", "SOCI", "SS", "SSYS", "STAT", "STUH", "SWRK", "SYS", "SYSE", "SYST", "TCOM",
            "THAR", "UDES", "UPRF", "URBS", "USA", "VANB", "VCSN", "VCSP", "VET", "VIPR", "VISR", "VLST",
            "VMED", "VPTH", "VPUL", "VSUR", "WCIT", "WEM", "WH", "WHCP", "WHG", "WSTD"
    )
    List<String> dpsa = Arrays.asList(
            "ART & ARCHEOLOGY OF MEDITERRANEAN WORLD", "ANIMAL BIOLOGY", "ACCOUNTING",
            "ADMINISTRATIVE OFFICE - NOT SCHL RELATED", "ADMINISTRATIVE OFFICE - SCHOOL RELATED",
            "AFRO-AMERICAN STUDIES", "AFRICANA STUDIES", "AFRICAN STUDIES PROGRAM",
            "APPLIED MATHEMATICS & COMPUTATIONAL SCI.", "AMERICAN CIVILIZATION",
            "ASIAN & MIDDLE EASTERN STUDIES", "ANATOMY", "ANCIENT HISTORY", "ANCIENT STUDIES",
            "ANNENBERG SCHOOL", "ANTHROPOLOGY", "ARBORETUM", "ARCHITECTURE", "ART HISTORY",
            "ARTS & SCIENCES", "ASIAN AMERICAN STUDIES", "ASTRONOMY", "BIOENGINEERING",
            "BENJAMIN FRANKLIN SEMINARS", "BUSINESS ECONOMICS & PUBLIC POLICY",
            "BIOLOGICAL BASIS OF BEHAVIOR", "BIOCHEMISTRY", "BIOLOGY", "BIOMEDICAL STUDIES",
            "BIOPHYSICS", "BIOTECHNOLOGY", "BUSINESS LAW", "BIOCHEMISTRY & MOLECULAR BIOPHYSICS",
            "BIOMEDICAL GRADUATE STUDIES", "BURSARS OFFICE", "CELL AND MOLECULAR BIOLOGY",
            "CHEMICAL AND BIOMOLECULAR ENGINEERING", "CELL BIOLOGY", "CHG TO 'CHE'",
            "COLLEGE OF LIBERAL & PROF STUDIES", "CHEMICAL ENGINEERING", "CHEMISTRY", "CINEMA STUDIES",
            "COMPUTER AND INFORMATION SCIENCE", "CHG TO 'CIS'", "COMPUTER & INFORMATION TECHNOLOGY",
            "CIVIL ENGINEERING (OBSOLETE)", "CLASSICAL ARCHAEOLOGY", "COLLECTIONS OFFICE",
            "CLINICAL STUDIES PHILA (VET SCHOOL)", "CLASSICAL STUDIES",
            "COMPARATIVE MEDICAL SCIENCES", "COGNITIVE SCIENCE", "COLLEGE FRESHMEN HOLDS", "COLLEGE",
            "COMPARATIVE LITERATURE", "COMMUNICATIONS", "CONVERTED SUBJECTS", "CITY PLANNING",
            "CRIMINOLOGY", "CHG TO 'CIS'", "ANATOMY/HISTOLOGY", "BIOCHEMISTRY", "DENTAl CARE SYSTEM",
            "COMMUNITY ORAL HEALTH", "DEMOGRAPHY", "ENDODONTICS", "DENTAL", "DESIGN OF ENVIRONMENT",
            "MICROBIOLOGY", "ORAL MEDICINE", "ORTHODONTICS", "ORAL SURGERY AND PHARMACOLOGY",
            "PEDIATRIC DENTISTRY", "PERIODONTICS", "PATHOLOGY", "RADIOLOGY", "RESTORATIVE DENTISTRY",
            "DECISION SCIENCES", "ORGANIZATIONAL DYNAMICS",
            "CENTER FOR EAST ASIAN LANGUAGES AND CIVILIZATIONS", "ECOLOGICAL ARCHITECTURE",
            "ENGINEERING & APPLIED SCIENCE", "APPLIED ECONOMICS", "ECONOMICS", "EDUCATION",
            "ELECTRICAL ENGINEERING", "CHG TO 'EE'", "EARTH AND ENVIRONMENTAL SCIENCE",
            "EXECUTIVE MASTERS IN TECHNOLOGY MGMT.", "CHG TO 'EAS'", "ENGLISH",
            "ENERGY MANAGEMENT & POLICY", "ENVIRONMENTAL STUDIES", "EPIDEMIOLOGY",
            "ELECTRICAL & SYSTEMS ENGINEERING", "EXECUTIVE ENGINEERING", "CINEMA STUDIES",
            "CHG TO 'FNCE'", "FINE ARTS", "FINANCE", "FOLKLORE", "GOVERNMENT ADMIN/HARRISBURG",
            "GOVERNMENT ADMINISTRATION", "GRADUATE ARTS & SCIENCES",
            "GENOMICS AND COMPUTATIONAL BIOLOGY", "GENETICS", "GENERAL HONORS",
            "EARTH AND ENVIRONMENTAL SCIENCE", "GRADUATE ENGINEERING HOLDS",
            "GERMANIC LANGUAGES AND LITERATURES", "SCHOOL OF DESIGN",
            "GENDER, SEXUALITY & WOMEN'S STUDIES", "CHG TO 'HCMG'",
            "HEALTH CARE MANAGEMENT", "HUMAN GENETICS", "HISTORY", "HEALTH AND SOCIETIES",
            "HISTORIC PRESERVATION", "HISTORY & SOCIOLOGY OF SCIENCE", "CHG TO 'HSPV'", "HUMANITIES",
            "INTL DEVL & APPROPRIATE TECHNOLOGY", "IMMUNOLOGY", "INSTITUTE OF NEUROLOGICAL SCIENCES",
            "INTERNATIONAL STUDIES", "INTERNATIONAL PROGRAMS", "INTERNATIONAL RELATIONS",
            "INTERNATIONAL STUDIES", "INTEGRATED PRODUCT DESIGN", "JUDICIAL INQUIRY OFFICER",
            "JEWISH STUDIES PROGRAM", "LATIN AMERICAN AND LATINO STUDIES",
            "LANDSCAPE ARCH & REGIONAL PLANNING", "LAUDER INSTITUTE", "LAW",
            "LOGIC, INFORMATION AND COMPUTATION", "LEGAL STUDIES AND BUSINESS ETHICS",
            "LIBERAL STUDIES", "LINGUISTICS", "LIFE SCIENCES MANAGEMENT",
            "LATIN AMERICAN AND LATINO STUDIES", "MASTER OF APPLIED POSITIVE PSYCHOLOGY",
            "MATHEMATICS", "MECH ENGR AND APPLIED MECHANICS",
            "MEDICAL", "DEPARTMENT OF MEDICAL ETHICS", "MANAGEMENT", "MICROBIOLOGY (DENTAL SCHOOL)",
            "MICROBIOLOGY", "MARKET & SOCIAL SYSTEMS ENGINEERING", "MARKETING",
            "MODERN MIDDLE EASTERN STUDIES", "MASTER OF MEDICAL PHYSICS", "MOLECULAR BIOLOGY",
            "MILITARY SCIENCE", "MATERIALS SCIENCE AND ENGINEERING", "CHG TO 'MSE'", "MUSIC",
            "NEAR EASTERN LANGUAGES AND CIVILIZATIONS", "NETWORKED AND SOCIAL SYSTEMS",
            "NEUROSCIENCE", "NAVAL SCIENCE", "NURSING", "OB/GYN",
            "OPERATIONS AND INFORMATION MANAGEMENT", "OPERATIONS RESERACH", "ORIENTAL STUDIES",
            "PARASITOLOGY", "PATHOLOGY", "PEDIATRICS", "PHILOSOPHY", "PHARMACOLOGY", "PHYSIOLOGY",
            "PHYSICS", "PHILOSOPHY, POLITICS AND ECON.", "PUBLIC POLICY & MANAGEMENT",
            "PRESIDENT'S OFFICE", "PROVOST", "POLITICAL SCIENCE", "PENN - SUMMER OF SERVICE",
            "PSYCHOLOGY", "PATHOBIOLOGY", "PUBLIC HEALTH STUDIES", "PSYCHIATRY", "RADIOLOGY",
            "REAL ESTATE", "REGISTRARS OFFICE", "RELIGIOUS STUDIES", "ROMANCE LANGUAGES",
            "ROMANCE PHILOLOGY", "REGIONAL SCIENCES", "RESEARCH MEDICINE",
            "SOUTH ASIA REGIONAL STUDIES", "SOUTH ASIA STUDIES", "SCHOOL OF ENGINEERING",
            "CHG TO 'SYS'", "SOCIAL GERONTOLOGY", "SLAVIC LANGUAGES", "SOCIOLOGY", "SUMMER SESSIONS",
            "SOCIAL SYSTEMS SCIENCE", "STATISTICS", "STUDENT HEALTH INSURANCE", "SOCIAL WORK",
            "SYSTEMS ENGINEERING", "CHG TO 'SYS'", "CHG TO 'SYS'", "TELECOMMUNICATIONS & NETWORKING",
            "THEATRE ARTS", "URBAN DESIGN", "UNIVERSITY PROFESSORSHIP", "URBAN STUDIES",
            "URBAN SPATIAL ANALYTICS", "ANIMAL BIOLOGY", "CLINICAL STUDIES - NBC ELECTIVES",
            "CLINICAL STUDIES - PHILA ELECTIVES", "VETERINARY MEDICINE",
            "PROGRAM IN ENERGY RESEARCH (VIPER)", "VET SCHOOL INDEPENDENT STUDY & RESEARCH",
            "VISUAL STUDIES", "CSP/CSN MEDICINE COURSES", "PATHOBIOLOGY",
            "VICE PROVOST FOR UNIVERSITY LIFE", "CSP/CSN SURGERY COURSES",
            "WHARTON COMPUTER&INSTRUCTIONAL TCH", "WHARTON EXECUTIVE MBA PROGRAM",
            "WHARTON UNDERGRADUATE", "WHARTON COMMUNICATION PGM", "WHARTON GRADUATE",
            "WOMEN'S STUDIES"
    )
    List<String> schools = Arrays.asList("SAS", "SEAS", "Wharton", "GSE", "Vet", "Nursing", "Med",
            "Dental", "SP2", "Design", "UPHS", "CHOP", "Annenberg", "Law")
    List<String> exp = Arrays.asList("Non-professional", "Professional", "Expert")
    List<String> cSponsor = Arrays.asList("SAS", "SEAS", "Wharton", "GSE", "Vet", "Nursing", "Med",
                    "Dental", "SP2", "Design", "Annenberg", "Law", "Coursera",
                    "Independent Research")
    List<String> ranks = Arrays.asList("Undergrad student", "Grad student", "PhD/PostDoc",
        "Clinical: intern, resident, fellow", "Clinical: other", "Faculty", "Alumni", "Staff")


    //This is a function for ad hoc changes in database, i.e., if you want to add/update extra rows, delete some rows,
    // or merge some rows in the database, you can put code in this function.
    // If you are changing some schemata in the database, do not do it here, as schemata are tied with domains 
    // and should be modified there.
    def adhocUpdate(){
        //Change Courseware to Canvas in Library Unit
        def coursewareToCanvas = RidLibraryUnit.findByName("Courseware")
        if(coursewareToCanvas != null){
            coursewareToCanvas.name = "Canvas"
            coursewareToCanvas.save()
        }
        //Change RIS to TRL in Library Unit
        def risToTRL = RidLibraryUnit.findByName("RIS")
        if(risToTRL != null){
            risToTRL.name = "TRL"
            risToTRL.save()
        }
        //Merge Biomed into HSL and make sure that all other domains that have attribute that pointed to Biomed
        // now point to HSL
        def biomed = RidLibraryUnit.findByName("Biomed")
        def hsl = RidLibraryUnit.findByName("HSL")
        if(hsl != null && biomed != null){
            RidUserGoal.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidModeOfConsultation.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidServiceProvided.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidSessionType.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidInstructionalMaterials.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidLocation.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidConsTransaction.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
            RidInsTransaction.findAllByRidLibraryUnit(biomed).each{
                it.ridLibraryUnit = hsl
                it.save()
            }
        }
        if(biomed != null){
            biomed.delete(flush: true)
        }
    }

    //The following update functions make sure that each domain list is up to date

    def updateRidLibraryUnit(){
        for (String i in lUnit.sort()) {
            if (!RidLibraryUnit.findByName(i)) {
                def gt = new RidLibraryUnit(name: i)
                gt.save()
                if (gt.hasErrors()) println gt.errors
            }
        }
    }

    def updateRidDepartment(){
        if(!RidDepartment.findByName("NA")){
            new RidDepartment(name: "NA", fullName: "NA").save()
        }
        for (int i = 1; i < dps.size(); i++) {
            if (!RidDepartment.findByName(dps.get(i).trim())) {
                def da = new RidDepartment(name: dps.get(i).trim(), fullName: dpsa.get(i).trim())
                da.save()
                if (da.hasErrors()) println da.errors
            }
        }
    }

    def updateRidSchool(){
        for (String i in schools.sort()) {
            if (!RidSchool.findByName(i)) {
                def e = new RidSchool(name: i, inForm: 1)
                e.save()
                if (e.hasErrors()) println e.errors
            }
        }
        def outsidePleaseIndicate = "Outside Penn (please indicate)"
        def pennOtherPleaseIndicate = "Penn Other (please indicate)"
        if (!RidSchool.findByName(pennOtherPleaseIndicate)) {
            new RidSchool(name: pennOtherPleaseIndicate, inForm: 2).save()
        }

        if (!RidSchool.findByName(outsidePleaseIndicate)) {
            new RidSchool(name: outsidePleaseIndicate, inForm: 2).save()
        }
    }

    //The helper functions eliminate repetitive code in some update functions

    def locationUpdateHelper(String lunit, List<String> loc){
        def libunit = RidLibraryUnit.findByName(lunit)
        if(libunit){
            if(!RidLocation.findByNameAndRidLibraryUnit("NA",libunit)){
                new RidLocation(name:"NA",inForm: 1, ridLibraryUnit:libunit).save(validate: false)
            }
            for (String i in loc.sort()) {
                if (!RidLocation.findByNameAndRidLibraryUnit(i, libunit)) {
                    def p = new RidLocation(name: i, inForm: 1, ridLibraryUnit: libunit)
                    p.save()
                    if (p.hasErrors()) println p.errors
                }
            }
            if(!RidLocation.findByNameAndRidLibraryUnit("Other (please indicate)",libunit)){
                new RidLocation(name:"Other (please indicate)",inForm: 2, ridLibraryUnit:libunit).save()
            }
        }
    }

    def updateRidLocation(){
        // for location -- Canvas
        List<String> loc = Arrays.asList("Classroom",  "Lecture hall")
        locationUpdateHelper("Canvas",loc)
        // for location -- HSL
        loc = Arrays.asList("Classroom", "Library conference room")
        locationUpdateHelper("HSL",loc)
        // for location -- CDM
        loc = Arrays.asList("Library conference room", "Lecture hall")
        locationUpdateHelper("CDM",loc)
        // for location -- LIPPINCOTT
        loc = Arrays.asList("Classroom", "Library conference room", "Lecture hall")
        locationUpdateHelper("LIPPINCOTT",loc)
        // for location -- TRL
        loc = Arrays.asList("Classroom", "Library conference room", "Lecture hall")
        locationUpdateHelper("TRL",loc)
        // for location -- Science Libraries
        loc = Arrays.asList("Classroom", "Library conference room", "Lecture hall")
        locationUpdateHelper("Science Libraries",loc)
        // for location -- General
        loc = Arrays.asList("Classroom", "Library conference room", "Lecture hall")
        locationUpdateHelper("General",loc)
        // for location -- KISLAK
        loc = Arrays.asList("Classroom", "Library conference room", "Lecture hall")
        locationUpdateHelper("KISLAK",loc)
    }

    def imUpdateHelper(String lunit, List<String> im){
        def libunit = RidLibraryUnit.findByName(lunit)
        if(libunit){
            if(!RidInstructionalMaterials.findByNameAndRidLibraryUnit("NA",libunit)){
                new RidInstructionalMaterials(name:"NA",inForm: 1, ridLibraryUnit:libunit).save(validate: false)
            }
            for (String i in im.sort()) {
                if (!RidInstructionalMaterials.findByNameAndRidLibraryUnit(i, libunit)) {
                    def p = new RidInstructionalMaterials(name: i, inForm: 1, ridLibraryUnit: libunit)
                    p.save()
                    if (p.hasErrors()) println p.errors
                }
            }
            if(!RidInstructionalMaterials.findByNameAndRidLibraryUnit("Other (please indicate)",libunit)){
                new RidInstructionalMaterials(name:"Other (please indicate)",inForm: 2, ridLibraryUnit:libunit).save()
            }
        }
    }

    def updateRidInstructionalMaterials(){
        // for instructionalMaterials -- Commons
        List<String> im = Arrays.asList("Handout", "Quiz")
        imUpdateHelper("Canvas",im)
        // for instructionalMaterials -- HSL
        im = Arrays.asList("PowerPoint", "Handout")
        imUpdateHelper("HSL",im)
        // for instructionalMaterials -- CDM
        im = Arrays.asList("PowerPoint", "Quiz")
        imUpdateHelper("CDM",im)
        // for instructionalMaterials -- LIPPINCOTT
        im = Arrays.asList("PowerPoint", "Handout", "Quiz")
        imUpdateHelper("LIPPINCOTT",im)
        // for instructionalMaterials -- TRL
        im = Arrays.asList("PowerPoint", "Handout", "Quiz")
        imUpdateHelper("TRL",im)
        // for instructionalMaterials -- Science Libraries
        im = Arrays.asList("PowerPoint", "Handout", "Quiz")
        imUpdateHelper("Science Libraries",im)
        // for instructionalMaterials -- General
        im = Arrays.asList("PowerPoint", "Handout", "Quiz")
        imUpdateHelper("General",im)
        // for instructionalMaterials -- KISLAK
        im = Arrays.asList("PowerPoint", "Handout", "Quiz")
        imUpdateHelper("KISLAK",im)
    }

    def updateRidExpertise(){
        if(!RidExpertise.findByName("NA")){
            new RidExpertise(name: "NA", inForm: 1).save(validate: false)
        }
        for (String i in exp) {
            if (!RidExpertise.findByName(i)) {
                def nl = new RidExpertise(name: i, inForm: 1)
                nl.save()
                if (nl.hasErrors()) println nl.errors
            }
        }
        def otherExpertise = "Other expertise (please indicate)"
        if (!RidExpertise.findByName(otherExpertise)) {
            new RidExpertise(name: otherExpertise, inForm: 2).save()
        }
    }

    def updateRidCourseSponsor(){
        // since course sponsor is not required, this stands for null value
        if(!RidCourseSponsor.findByName("NA")){
            new RidCourseSponsor(name: "NA", inForm: 1).save(validate: false)
        }
        for (String i in cSponsor.sort()) {
            if (!RidCourseSponsor.findByName(i)) {
                def c = new RidCourseSponsor(name: i, inForm: 1)
                c.save()
                if (c.hasErrors()) println c.errors
            }
        }
        def outsidePleaseIndicate = "Outside Penn (please indicate)"
        if (!RidCourseSponsor.findByName(outsidePleaseIndicate)) {
            new RidCourseSponsor(name: outsidePleaseIndicate, inForm: 2).save()
        }
    }

    def updateRidRank(){
        for (String i in ranks.sort()) {
            if (!RidRank.findByName(i)) {
                def c = new RidRank(name: i, inForm: 1)
                c.save()
                if (c.hasErrors()) println c.errors
            }
        }
        def otherPleaseIndicate = "Other (please indicate)"
        if (!RidRank.findByName(otherPleaseIndicate)) {
            new RidRank(name: otherPleaseIndicate, inForm: 2).save()
        }
    }

    def uGoalUpdateHelper(String lunit, List<String> uGoal){
        def libunit = RidLibraryUnit.findByName(lunit)
        if(libunit){
            if(!RidUserGoal.findByNameAndRidLibraryUnit("NA",libunit)){
                new RidUserGoal(name:"NA",inForm: 1, ridLibraryUnit:libunit).save(validate: false)
            }
            for (String i in uGoal.sort()) {
                if (!RidUserGoal.findByNameAndRidLibraryUnit(i, libunit)) {
                    def p = new RidUserGoal(name: i, inForm: 1, ridLibraryUnit: libunit)
                    p.save()
                    if (p.hasErrors()) println p.errors
                }
            }
            if(!RidUserGoal.findByNameAndRidLibraryUnit("Other (please indicate)",libunit)){
                new RidUserGoal(name:"Other (please indicate)",inForm: 2, ridLibraryUnit:libunit).save()
            }
        }
    }

    def updateRidUserGoal(){
        // ---------------------------------------------------------------------------------------------
        // for user goal -- Canvas
        List<String> uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation",
                "Independent Research", "Improvement in Teaching")
        uGoalUpdateHelper("Canvas",uGoal)
        // for user goal -- HSL
        uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation", "Independent Research")
        uGoalUpdateHelper("HSL",uGoal)
        // for user goal -- CDM
        uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation", "Independent Research")
        uGoalUpdateHelper("CDM",uGoal)
        // for user goal -- LIPPINCOTT
        uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation", "Independent Research")
        uGoalUpdateHelper("LIPPINCOTT",uGoal)
        // for user goal -- TRL
        uGoal = Arrays.asList("Research Paper", "Course Project", "Senior Thesis", "Master Thesis",
                "Dissertation", "Research article", "Monograph", "Data Management",
                "Independent Research", "Course Creation", "Grant Proposal")
        uGoalUpdateHelper("TRL",uGoal)
        // for user goal -- Science Libraries
        uGoal = Arrays.asList("Research Paper", "Course Project", "Senior Thesis", "Master Thesis",
                "Dissertation", "Research article", "Monograph", "Data Management",
                "Independent Research", "Course Creation", "Grant Proposal")
        uGoalUpdateHelper("Science Libraries",uGoal)
        // for user goal -- General
        uGoal = Arrays.asList("Research Paper", "Course Project", "Senior Thesis", "Master Thesis",
                "Dissertation", "Research article", "Monograph", "Data Management",
                "Independent Research", "Course Creation", "Grant Proposal")
        uGoalUpdateHelper("General",uGoal)
        // for user goal -- KISLAK
        uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation", "Independent Research")
        uGoalUpdateHelper("KISLAK",uGoal)
        
    }

    def mcUpdateHelper(String lunit, List<String> cMode){
        def libunit = RidLibraryUnit.findByName(lunit)
        if(libunit){
            if(!RidModeOfConsultation.findByNameAndRidLibraryUnit("NA",libunit)){
                new RidModeOfConsultation(name:"NA",inForm: 1, ridLibraryUnit:libunit).save(validate: false)
            }
            for (String i in cMode.sort()) {
                if (!RidModeOfConsultation.findByNameAndRidLibraryUnit(i, libunit)) {
                    def p = new RidModeOfConsultation(name: i, inForm: 1, ridLibraryUnit: libunit)
                    p.save()
                    if (p.hasErrors()) println p.errors
                }
            }
        }
    }

    def updateRidModeOfConsultation(){
        // ---------------------------------------------------------------------------------------------
        // for mode of consultation -- Canvas
        List<String> cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "Video or web conference", "In person (in library)", "In person (outside library)")
        mcUpdateHelper("Canvas",cMode)
        if(!RidModeOfConsultation.findByNameAndRidLibraryUnit("Other (please indicate)",RidLibraryUnit.findByName("Canvas"))){
            new RidModeOfConsultation(name:"Other (please indicate)",inForm: 2, ridLibraryUnit:RidLibraryUnit.findByName("Canvas")).save()
        }
        // for mode of consutlation -- HSL
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("HSL",cMode)
        // for mode of consutlation -- CDM
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("CDM",cMode)
        // for mode of consutlation -- LIPPINCOTT
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("LIPPINCOTT",cMode)
        // for mode of consutlation -- TRL
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("TRL",cMode)
        // for mode of consutlation -- Science Libraries
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("Science Libraries",cMode)
        // for mode of consutlation -- General
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("General",cMode)
        //for mode of consultation -- KISLAK
        cMode = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        mcUpdateHelper("KISLAK",cMode)
    }


    def stUpdateHelper(String lunit, List<String> cType){
        def libunit = RidLibraryUnit.findByName(lunit)
        if(libunit){
            if(!RidSessionType.findByNameAndRidLibraryUnit("NA",libunit)){
                new RidSessionType(name:"NA",inForm: 1, ridLibraryUnit:libunit).save(validate: false)
            }
            for (String i in cType.sort()) {
                if (!RidSessionType.findByNameAndRidLibraryUnit(i, libunit)) {
                    def p = new RidSessionType(name: i, inForm: 1, ridLibraryUnit: libunit)
                    p.save()
                    if (p.hasErrors()) println p.errors
                }
            }
        }
    }

    def updateRidSessionType(){
        // ---------------------------------------------------------------------------------------------
        // for session type -- Canvas
        List<String> cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "Video or web conference", "In person (in library)", "In person (outside library)")
        stUpdateHelper("Canvas",cType)
        if(!RidSessionType.findByNameAndRidLibraryUnit("Other (please indicate)",RidLibraryUnit.findByName("Canvas"))){
            new RidSessionType(name:"Other (please indicate)",inForm: 2, ridLibraryUnit:RidLibraryUnit.findByName("Canvas")).save()
        }
        // for session type -- HSL
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("HSL",cType)
        // for session type -- CDM
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("CDM",cType)
        // for session type -- LIPPINCOTT
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("LIPPINCOTT",cType)
        // for session type -- TRL
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("TRL",cType)
        // for session type -- Science Libraries
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("Science Libraries",cType)
        // for session type -- General
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("General",cType)
        // for session type -- KISLAK
        cType = Arrays.asList("Email", "Phone", "Chat", "Conferencing software",
                "In person (in library)", "In person (outside library)")
        stUpdateHelper("KISLAK",cType)
    }

    def spUpdateHelper(String lunit, List<String> sProvided){
        def libunit = RidLibraryUnit.findByName(lunit)
        if(libunit){
            if(!RidServiceProvided.findByNameAndRidLibraryUnit("NA",libunit)){
                new RidServiceProvided(name:"NA",inForm: 1, ridLibraryUnit:libunit).save(validate: false)
            }
            for (String i in sProvided.sort()) {
                if (!RidServiceProvided.findByNameAndRidLibraryUnit(i, libunit)) {
                    def p = new RidServiceProvided(name: i, inForm: 1, ridLibraryUnit: libunit)
                    p.save()
                    if (p.hasErrors()) println p.errors
                }
            }
            if(!RidServiceProvided.findByNameAndRidLibraryUnit("Other (please indicate)",libunit)){
                new RidServiceProvided(name:"Other (please indicate)",inForm: 2, ridLibraryUnit:libunit).save()
            }
        }
    }

    def updateRidServiceProvided(){
        // ---------------------------------------------------------------------------------------------
        // for service provided -- Canvas
        List<String> sProvided = Arrays.asList("Course design", "Research assistance",
                "Instructional support (apart from course design)", "Tour",
                "Tech/Software instruction", "Mobile technology", "Assistance to undergraduates")
        spUpdateHelper("Canvas",sProvided)
        // for service provided -- HSL
        sProvided = Arrays.asList("Research assistance", "Acquisitions/Collections",
                "Copyright assistance for author", "Copyright assistance for rank",
                "Correct an operational or service breakdown (incl. tech support)",
                "Instructional support (apart from course design, including courseware)",
                "Clinical decision making", "Tech/Software instruction", "Search instruction",
                "Literature search", "Mobile technology", "Bibliometrics or citation metrics",
                "Consumer health", "Admin/policy questions", "Citation management instruction",
                "Scholarly Commons/Repository Services", "Creating faculty profiles/selected works/VIVO",
                "Comprehensive Lit Search/Systematic Reviews")
        spUpdateHelper("HSL",sProvided)
        // for service provided -- CDM
        sProvided = Arrays.asList("Course design", "Research assistance",
                "Acquisitions/Collections", "Copyright assistance for author", "Copyright assistance for rank",
                "Correct an operational or service breakdown (incl. tech support)",
                "Instructional support (apart from course design)", "Clinic", "Tour",
                "Tech/Software instruction", "Search instruction",
                "Literature search", "Mobile technology", "Bibliometrics or citation metrics",
                "Admin/policy questions", "Citation management instruction",
                "Scholarly Commons/Repository Services", "Creating faculty profiles/selected works/VIVO",
                "Coursera/MOOCs support", "Research practice support")
        spUpdateHelper("CDM",sProvided)
        // for service provided -- LIPPINCOTT
        sProvided = Arrays.asList("Research assistance", "Acquisitions/Collections",
                "Correct an operational or service breakdown (incl. tech support)",
                "Instructional support (apart from course design)", "Tour",
                "Tech/Software instruction", "Search instruction",
                "Literature search", "Bibliometrics or citation metrics",
                "Admin/policy questions", "Citation management instruction")
        spUpdateHelper("LIPPINCOTT",sProvided)
        // for service provided -- TRL
        sProvided = Arrays.asList("Research assistance", "Clinic", "Tour",
                "Acquisitions/Collections", "Citation management instruction", "Instructional Support",
                "Tech/Software instruction", "Mobile technology", "Correct an Operational or Service Breakdown",
                "Admin/policy questions", "Bibliometrics or citation metrics", "Copyright",
                "Scholarly Commons/Repository Services")
        spUpdateHelper("TRL",sProvided)
        // for service provided -- Science Libraries
        sProvided = Arrays.asList("Research assistance", "Clinic", "Tour",
                "Acquisitions/Collections", "Citation management instruction", "Instructional Support",
                "Tech/Software instruction", "Mobile technology", "Correct an Operational or Service Breakdown",
                "Admin/policy questions", "Bibliometrics or citation metrics", "Copyright",
                "Scholarly Commons/Repository Services")
        spUpdateHelper("Science Libraries",sProvided)
        // for service provided -- General
        sProvided = Arrays.asList("Research assistance", "Clinic", "Tour",
                "Acquisitions/Collections", "Citation management instruction", "Instructional Support",
                "Tech/Software instruction", "Mobile technology", "Correct an Operational or Service Breakdown",
                "Admin/policy questions", "Bibliometrics or citation metrics", "Copyright",
                "Scholarly Commons/Repository Services")
        spUpdateHelper("General",sProvided)
        // for service provided -- KISLAK
        sProvided = Arrays.asList("Research assistance", "Acquisitions/Collections",
                "Correct an operational or service breakdown (incl. tech support)",
                "Instructional support (apart from course design)", "Tour",
                "Tech/Software instruction", "Search instruction",
                "Literature search", "Bibliometrics or citation metrics",
                "Admin/policy questions", "Citation management instruction")
        spUpdateHelper("KISLAK",sProvided)
    }


    //This function runs on application startup. It will apply changes in the ad hoc update function 
    //and make sure every domain list is up to date
    def initialization(){
        adhocUpdate() //see comment of this function top
        updateRidLibraryUnit()
        updateRidDepartment()
        updateRidSchool()
        updateRidLocation()
        updateRidInstructionalMaterials()
        updateRidExpertise()
        updateRidCourseSponsor()
        updateRidRank()
        updateRidUserGoal()
        updateRidModeOfConsultation()
        updateRidSessionType()
        updateRidServiceProvided()
    }


    //This function runs after application startup. In case that if any domain is empty (probably won't happen unless 
    //some really weird bug pops up during runtime), re-populate the domain.
    def populateTestFields() {
        log.info("Importing University of Pennsylvania default data")
        if (!RidLibraryUnit.first()) {
            updateRidLibraryUnit()
        }
        if (!RidDepartment.first()) {
            updateRidDepartment()
        }
        if (!RidSchool.first()) {
            updateRidSchool()
        }
        if (!RidLocation.first()) {
            updateRidLocation()
        }
        if (!RidInstructionalMaterials.first()) {
            updateRidInstructionalMaterials()
        }
        if (!RidExpertise.first()) {
            updateRidExpertise()
        }
        if (!RidCourseSponsor.first()) {
            updateRidCourseSponsor()
        }
        if (!RidRank.first()) {
            updateRidRank()
        }
        if (!RidUserGoal.first()) {
            updateRidUserGoal()
        }
        if (!RidModeOfConsultation.first()) {
            updateRidModeOfConsultation()
        }
        if (!RidSessionType.first()) {
            updateRidSessionType()
        }
        if (!RidServiceProvided.first()) {
            updateRidServiceProvided()
        }
    }
    

    def createTestTransactions(){
        log.info("Importing University of Pennsylvania test transactions")
        if (!RidLibraryUnit.first()){
          populateTestFields()
        }
        if (!RidConsTransaction.first()) {
            println "Creating test data consultation for RID database"
            // ---------------------------------------------------------------------------------------------
                // for ridTransaction (only for demo)
                for (int i = 0; i < 50; i++) {
                    def t = new RidConsTransaction(staffPennkey: "012345667",
                            userQuestion: RandomStringUtils.randomAlphanumeric(i % 50 + 1),
                            interactOccurrences: i % 50, prepTime: i % 40,
                            eventLength: i % 50, notes: "Sample Notes",
                            facultySponsor: "Sample Sponsor", courseName: "Sample Course Name",
                            courseNumber: "LIB001", dateOfConsultation: new Date(),
                            department: RidDepartment.get(i % 6 + 1),
                            courseSponsor: RidCourseSponsor.get(1),
                            userGoal: RidUserGoal.findByRidLibraryUnit(RidLibraryUnit.get(1)),
                            modeOfConsultation: RidModeOfConsultation.findByRidLibraryUnit(RidLibraryUnit.get(1)),
                            rank: RidRank.get(1),
                            serviceProvided: RidServiceProvided.findByRidLibraryUnit(RidLibraryUnit.get(1)),
                            school: RidSchool.get(1),
                            ridLibraryUnit: RidLibraryUnit.get(1 % 6 + 1)
                    )

                    try {
                        if (!t.save()) {
                            if (t.hasErrors()) println t.errors
                        }
                    } catch (Exception e) {
                        log.error("an error occurred during bootstrap that will crash the entire application")
                        throw e
                    }
                }
            }


        if (!RidInsTransaction.first()) {

            println "Creating test instructional data for RID database"
            for (int i = 0; i < 50; i++) {
                    def rand = new Random()
                    def t = new RidInsTransaction(instructorPennkey: "012345667",
                            eventLength: i % 50, notes: "Sample Notes",
                            sessionDescription: "Sample description",
                            facultySponsor: "Sample Sponsor", courseName: "Sample Course Name",
                            courseNumber: "LIB001", dateOfInstruction: new Date(),
                            //department: RidDepartment.get(i % 6 + 1),
                            sessionType: RidSessionType.get(1),
                            instructionalMaterials: RidInstructionalMaterials.get(1),
                            expertise: RidExpertise.get(1),
                            school: RidSchool.get(1),
                            location: RidLocation.get(1 % 3 + 1),
                            attendanceTotal: 42,
                            ridLibraryUnit: RidLibraryUnit.get(rand.nextInt(4) + 1)

                    )

                    try {
                        if (!t.save()) {
                            println "\n"
                            if (t.hasErrors()) println t.errors
                            println "\n"
                        }
                    } catch (Exception e) {
                        log.error("an error occurred during bootstrap that will crash the entire application")
                        throw e
                    }
                }
            }

    }
}
