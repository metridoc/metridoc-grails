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

import java.text.SimpleDateFormat

class RidTransactionService {

    def queryMethod(Map params, String transType) {

        if (transType == "consultation") {
            def query = RidConsTransaction.where {
                id >= 0
            }

            if (params.dateOfConsultation_start && params.dateOfConsultation_end) {
                try {
                    Date start = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation_start)
                    Date end = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation_end)
                    query = query.where {
                        dateOfConsultation >= start && dateOfConsultation < end.next()
                    }
                } catch (Exception e) {}
            }

            if (params.ridLibraryUnitSearch) {
                def UnitList = params.list('ridLibraryUnitSearch')
                if (UnitList.size() > 0 && !UnitList.contains("0")) {
                    List<Long> uList = new LinkedList<Long>()
                    for (String id in UnitList)
                        uList.add(Long.valueOf(id))
                    query = query.where {
                        ridLibraryUnit in RidLibraryUnit.findAllByIdInList(uList)
                    }
                }
            }


            if (params.staffPennkey) {
                String[] staffPennkey_splits = params.staffPennkey.tokenize(" ");
                for (String s in staffPennkey_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            staffPennkey ==~ ~s.trim()
                        }
                    }
                }
            }

            if (params.ridSchoolSearch) {
                def SchoolList = params.list('ridSchoolSearch')
                if (SchoolList.size() > 0 && !SchoolList.contains("0")) {
                    List<Long> sList = new LinkedList<Long>()
                    for (String id in SchoolList)
                        sList.add(Long.valueOf(id))
                    query = query.where {
                        school in RidSchool.findAllByIdInList(sList)
                    }
                }
            }

            if (params.ridDepartmentSearch) {
                def DepartmentList = params.list('ridDepartmentSearch')
                if (DepartmentList.size() > 0 && !DepartmentList.contains("0")) {
                    List<Long> dList = new LinkedList<Long>()
                    for (String id in DepartmentList)
                        dList.add(Long.valueOf(id))
                    query = query.where {
                        department in RidDepartment.findAllByIdInList(dList)
                    }
                }
            }

            if (params.userName) {
                String[] userName_splits = params.userName.tokenize(" ");
                for (String s in userName_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            //userQuestion ==~ ~"^.+ba\$"
                            //userQuestion ==~ ~"^k.*"
                            userName ==~ ~s.trim()
                        }
                    }
                }
            }

            if (params.userQuestion) {
                String[] userQuestion_splits = params.userQuestion.tokenize(" ");
                for (String s in userQuestion_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            //userQuestion ==~ ~"^.+ba\$"
                            //userQuestion ==~ ~"^k.*"
                            userQuestion ==~ ~s.trim()
                        }
                    }
                }
            }

            if (params.notes) {
                String[] notes_splits = params.notes.tokenize(" ");
                for (String s in notes_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            notes ==~ ~s.trim()
                        }
                    }
                }
            }

            return query
        } else {
            def query = RidInsTransaction.where {
                id >= 0
            }

            if (params.dateOfInstruction_start && params.dateOfInstruction_end) {
                try {
                    Date start = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfInstruction_start)
                    Date end = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfInstruction_end)
                    query = query.where {
                        dateOfInstruction >= start && dateOfInstruction < end.next()
                    }
                } catch (Exception e) {
//            Date start = Date.parse("E MMM dd H:m:s z yyyy", params.dateOfConsultation_start)
//            Date end = Date.parse("E MMM dd H:m:s z yyyy", params.dateOfConsultation_end)
//            query = query.where {
//                dateOfConsultation >= start && dateOfConsultation < end.next()
//            }
                }
            }

            if (params.ridLibraryUnitSearch) {
                def UnitList = params.list('ridLibraryUnitSearch')
                if (UnitList.size() > 0 && !UnitList.contains("0")) {
                    List<Long> uList = new LinkedList<Long>()
                    for (String id in UnitList)
                        uList.add(Long.valueOf(id))
                    query = query.where {
                        ridLibraryUnit in RidLibraryUnit.findAllByIdInList(uList)
                    }
                }
            }

            if (params.instructorPennkey) {
                String[] instructorPennkey_splits = params.instructorPennkey.tokenize(" ");
                for (String s in instructorPennkey_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            instructorPennkey ==~ ~s.trim()
                        }
                    }
                }
            }
            if (params.courseName) {
                String[] courseName_splits = params.courseName.tokenize(" ");
                for (String s in courseName_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            courseName ==~ ~s.trim()
                        }
                    }
                }
            }

            if (params.ridDepartmentSearch) {
                def DepartmentList = params.list('ridDepartmentSearch')
                if (DepartmentList.size() > 0 && !DepartmentList.contains("0")) {
                    List<Long> dList = new LinkedList<Long>()
                    for (String id in DepartmentList)
                        dList.add(Long.valueOf(id))
                    query = query.where {
                        department in RidDepartment.findAllByIdInList(dList)
                    }
                }
            }


            if (params.ridSchoolSearch) {
                def SchoolList = params.list('ridSchoolSearch')
                if (SchoolList.size() > 0 && !SchoolList.contains("0")) {
                    List<Long> sList = new LinkedList<Long>()
                    for (String id in SchoolList)
                        sList.add(Long.valueOf(id))
                    query = query.where {
                        school in RidSchool.findAllByIdInList(sList)
                    }
                }
            }

            if (params.ridLocationSearch) {
                def LocationList = params.list('ridLocationSearch')
                if (LocationList.size() > 0 && !LocationList.contains("0")) {
                    List<Long> lList = new LinkedList<Long>()
                    for (String id in LocationList)
                        lList.add(Long.valueOf(id))
                    query = query.where {
                        location in RidLocation.findAllByIdInList(lList)
                    }
                }
            }

            if (params.ridSessionTypeSearch) {
                def SessionTypeList = params.list('ridSessionTypeSearch')
                if (SessionTypeList.size() > 0 && !SessionTypeList.contains("0")) {
                    List<Long> stList = new LinkedList<Long>()
                    for (String id in SessionTypeList)
                        stList.add(Long.valueOf(id))
                    query = query.where {
                        sessionType in RidSessionType.findAllByIdInList(stList)
                    }
                }
            }


            if (params.notes) {
                String[] notes_splits = params.notes.tokenize(" ");
                for (String s in notes_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            notes ==~ ~s.trim()
                        }
                    }
                }
            }
            if (params.sessionDescription) {
                String[] sessionDescription_splits = params.sessionDescription.tokenize(" ");
                for (String s in sessionDescription_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            sessionDescription ==~ ~s.trim()
                        }
                    }
                }
            }

            return query
        }
    }

    def getFieldsByLibraryUnit(Map params, String transType) {
        if (transType == "consultation") {
            def userGoals = RidUserGoal.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 1)
            def consultations = RidModeOfConsultation.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 1)
            def services = RidServiceProvided.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 1)
            if (!params.goalID.isEmpty()) {
                def goal = RidUserGoal.findByRidLibraryUnitAndId(RidLibraryUnit.get(params.typeId), params.goalID)
                if (goal != null && !userGoals.contains(goal))
                    userGoals.add(0, goal)
            }
            if (!params.modeID.isEmpty()) {
                def mode = RidModeOfConsultation.findByRidLibraryUnitAndId(RidLibraryUnit.get(params.typeId), params.modeID)
                if (mode != null && !consultations.contains(mode))
                    consultations.add(0, mode)
            }
            if (!params.serviceID.isEmpty()) {
                def service = RidServiceProvided.findByRidLibraryUnitAndId(RidLibraryUnit.get(params.typeId), params.serviceID)
                if (service != null && !userGoals.contains(service))
                    services.add(0, service)
            }
            userGoals.sort { it.name }.addAll(RidUserGoal.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 2))
            consultations.sort { it.name }.addAll(RidModeOfConsultation.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 2))
            services.sort { it.name }.addAll(RidServiceProvided.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 2))
            return ['userGoal': userGoals,
                    'modeOfConsultation': consultations,
                    'serviceProvided': services]
        }
        else{
            def sessionTypes = RidSessionType.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 1)
            def instructionalMaterials = RidInstructionalMaterials.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 1)
            def locations = RidLocation.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 1)


            if (!params.sessionID.isEmpty()) {
                def session = RidSessionType.findByRidLibraryUnitAndId(RidLibraryUnit.get(params.typeId), params.sessionID)
                if (session != null && !sessionTypes.contains(session))
                    sessionTypes.add(0, session)
            }

            if (!params.materialsID.isEmpty()) {
                def material = RidInstructionalMaterials.findByRidLibraryUnitAndId(RidLibraryUnit.get(params.typeId), params.materialsID)
                if (material != null && !instructionalMaterials.contains(material))
                    instructionalMaterials.add(0, material)
            }

            if (!params.locationID.isEmpty()) {
                def location = RidLocation.findByRidLibraryUnitAndId(RidLibraryUnit.get(params.typeId), params.locationID)
                if (location != null && !locations.contains(location))
                    locations.add(0, location)
            }
            sessionTypes.sort { it.name }.addAll(RidSessionType.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 2))
            instructionalMaterials.sort { it.name }.addAll(RidInstructionalMaterials.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 2))
            locations.sort { it.name }.addAll(RidLocation.findAllByRidLibraryUnitAndInForm(RidLibraryUnit.get(params.typeId), 2))

            return ['sessionType':sessionTypes,
                    'instructionalMaterials':instructionalMaterials,
                    'location':locations]

        }
    }

    def createNewConsInstanceMethod(Map params, RidConsTransactionBase ridTransactionInstance) {
        String otherRank = params.otherRank
        if (otherRank != null && !otherRank.isEmpty()) {
            if (RidRank.findAllByName(otherRank).size() == 0) {
                def c = new RidRank(name: otherRank, inForm: 0)
                c.save()
                if (c.hasErrors()) println c.errors
            }
            if (RidRank.findAllByName(otherRank).size() > 0)
                ridTransactionInstance.rank = RidRank.findByName(otherRank)
        }

        String otherSchool = params.otherSchool
        if (otherSchool != null && !otherSchool.isEmpty()) {
            if (RidSchool.findAllByName(otherSchool).size() == 0) {
                def e = new RidSchool(name: otherSchool, inForm: 0)
                e.save()
                if (e.hasErrors()) println e.errors
            }
            if (RidSchool.findAllByName(otherSchool).size() > 0)
                ridTransactionInstance.school = RidSchool.findByName(otherSchool)
        }

        String otherCourseSponsor = params.otherCourseSponsor
        if (otherCourseSponsor != null && !otherCourseSponsor.isEmpty()) {
            if (RidCourseSponsor.findAllByName(otherCourseSponsor).size() == 0) {
                def c = new RidCourseSponsor(name: otherCourseSponsor, inForm: 0)
                c.save()
                if (c.hasErrors()) println c.errors
            }
            if (RidCourseSponsor.findAllByName(otherCourseSponsor).size() > 0)
                ridTransactionInstance.courseSponsor = RidCourseSponsor.findByName(otherCourseSponsor)
        }

        String otherModeOfConsultation = params.otherModeOfConsultation
        if (otherModeOfConsultation != null && !otherModeOfConsultation.isEmpty()) {
            if (RidModeOfConsultation.findAllByNameAndRidLibraryUnit(otherModeOfConsultation,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() == 0) {
                def s = new RidModeOfConsultation(name: otherModeOfConsultation, inForm: 0,
                        ridLibraryUnit: RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
                s.save()
                if (s.hasErrors()) println s.errors
            }
            if (RidModeOfConsultation.findAllByNameAndRidLibraryUnit(otherModeOfConsultation,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() > 0)
                ridTransactionInstance.modeOfConsultation = RidModeOfConsultation.findByNameAndRidLibraryUnit(
                        otherModeOfConsultation, RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
        }

        String otherService = params.otherService
        if (otherService != null && !otherService.isEmpty()) {
            if (RidServiceProvided.findAllByNameAndRidLibraryUnit(otherService,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() == 0) {
                def s = new RidServiceProvided(name: otherService, inForm: 0,
                        ridLibraryUnit: RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
                s.save()
                if (s.hasErrors()) println s.errors
            }
            if (RidServiceProvided.findAllByNameAndRidLibraryUnit(otherService,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() > 0)
                ridTransactionInstance.serviceProvided = RidServiceProvided.findByNameAndRidLibraryUnit(otherService,
                        RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
        }

        String otherUserGoal = params.otherUserGoal
        if (otherUserGoal != null && !otherUserGoal.isEmpty()) {
            if (RidUserGoal.findAllByNameAndRidLibraryUnit(otherUserGoal,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() == 0) {
                def s = new RidUserGoal(name: otherUserGoal, inForm: 0,
                        ridLibraryUnit: RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
                s.save()
                if (s.hasErrors()) println s.errors
            }
            if (RidUserGoal.findAllByNameAndRidLibraryUnit(otherUserGoal,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() > 0)
                ridTransactionInstance.userGoal = RidUserGoal.findByNameAndRidLibraryUnit(otherUserGoal,
                        RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
        }
    }

    def createNewInsInstanceMethod(Map params, RidInsTransactionBase ridTransactionInstance) {

        String otherLocation = params.otherLocation
        if (otherLocation != null && !otherLocation.isEmpty()) {
            if (RidLocation.findAllByName(otherLocation).size() == 0) {
                def e = new RidLocation(name: otherLocation, inForm: 0)
                e.save()
                if (e.hasErrors()) println e.errors
            }
            if (RidLocation.findAllByName(otherLocation).size() > 0)
                ridTransactionInstance.location = RidLocation.findByName(otherLocation)
        }
        String otherInstructionalMaterials = params.otherInstructionalMaterials
        if (otherInstructionalMaterials != null && !otherInstructionalMaterials.isEmpty()) {
            if (RidInstructionalMaterials.findAllByName(otherInstructionalMaterials).size() == 0) {
                def e = new RidInstructionalMaterials(name: otherInstructionalMaterials, inForm: 0)
                e.save()
                if (e.hasErrors()) println e.errors
            }
            if (RidInstructionalMaterials.findAllByName(otherInstructionalMaterials).size() > 0)
                ridTransactionInstance.instructionalMaterials = RidInstructionalMaterials.findByName(otherInstructionalMaterials)
        }
        String otherExpertise = params.otherExpertise
        if (otherExpertise != null && !otherExpertise.isEmpty()) {
            if (RidExpertise.findAllByName(otherExpertise).size() == 0) {
                def e = new RidExpertise(name: otherExpertise, inForm: 0)
                e.save()
                if (e.hasErrors()) println e.errors
            }
            if (RidExpertise.findAllByName(otherExpertise).size() > 0)
                ridTransactionInstance.expertise = RidExpertise.findByName(otherExpertise)
        }
        String otherSessionType = params.otherSessionType
        if (otherSessionType != null && !otherSessionType.isEmpty()) {
            if (RidSessionType.findAllByNameAndRidLibraryUnit(otherSessionType,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() == 0) {
                def s = new RidSessionType(name: otherSessionType, inForm: 0,
                        ridLibraryUnit: RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
                s.save()
                if (s.hasErrors()) println s.errors
            }
            if (RidSessionType.findAllByNameAndRidLibraryUnit(otherSessionType,
                    RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id))).size() > 0)
                ridTransactionInstance.sessionType = RidSessionType.findByNameAndRidLibraryUnit(
                        otherSessionType, RidLibraryUnit.get(Long.valueOf(params.ridLibraryUnit.id)))
        }
    }
}
