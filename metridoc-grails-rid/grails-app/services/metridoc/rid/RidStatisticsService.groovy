package metridoc.rid

import java.text.SimpleDateFormat

import static java.lang.Math.abs
import static java.util.Calendar.MONTH
import static java.util.Calendar.YEAR

class RidStatisticsService {

    def getStats(Map params, String transType) {

        def results = new RidStatisticsReport()
        def query = RidConsTransaction.where {
            id >= 0
        }
        results.totalTransactions = query.count()



        try {
            Date begin = new Date().minus(365);
            Date end = new Date()
            query = query.where {
                dateOfConsultation > begin.previous() && dateOfConsultation < end
            }
        } catch (Exception e) {}
        results.yearTransactions = query.count()
        for (i in query) {
            results.yearInteractOccurences += i.interactOccurrences
            results.yearEventLength += i.eventLength
            results.yearPrepTime += i.prepTime
        }


        for (int m = 12; m >= 0; m--) {

            int mIO
            int mEL
            int mPT


            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end
            }

            results.monthTransactions.add(query.count())
            results.months.add(begin)
            for (i in query) {

                mIO += i.interactOccurrences
                mEL += i.eventLength
                mPT += i.prepTime
            }
            results.monthInteractOccurences.add(mIO)
            results.monthEventLength.add(mEL)
            results.monthPrepTime.add(mPT)
        }
        query = RidConsTransaction.where {
            id >= 0
        }

        def interactSum = 0
        for (i in query) {
            interactSum += i.interactOccurrences
        }
        results.totalInteractOccurences = interactSum
        results.avgInteractOccurrences = interactSum / query.count()


        def prepSum = 0
        for (i in query) {
            prepSum += i.prepTime
        }
        results.totalPrepTime = prepSum
        results.avgPrepTime = prepSum / query.count()


        def eventSum = 0
        for (i in query) {
            eventSum += i.eventLength
        }
        results.totalEventLength = eventSum
        results.avgEventLength = eventSum / query.count()
        //Pennkey data not currently in statistics display
        def pklist = query.list { order("staffPennkey") }
        def testCount = pklist.countBy { it.staffPennkey }
        def tempKey = ""
        def tempCount = 0
        def bestKey = ""
        def bestCount = 0
        for (i in testCount) {
            if (i.value > bestCount && !i.key.equals(null) && !i.key.toString().equals("")) {
                bestCount = i.value
                bestKey = i.key.toString()
            }
        }
        results.staffPennkey = bestKey
        results.pennkeyMax = bestCount

        //Cludgy code to remove Hibernate proxy classes
        def depts = RidDepartment.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in depts) {
            results.departments.add(d.getKey())
        }


        for (t in results.departments) {
            results.totalDepartments.add(0)
            results.yearDepartments.add(0)
            results.monthDepartments.add(new ArrayList())
        }
        for (t in results.totalDepartments) {
            t = 0
        }
        for (t in results.yearDepartments) {
            t = 0
        }
        for (t in results.monthDepartments) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && department != null
        }

        def dptList = query.list { order("department") }
        def dptCount = dptList.countBy { it.department.name }

        for (each in dptCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.departments.indexOf(each.getKey())
                results.totalDepartments[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && department != null
            }


            dptList = query.list { order("department") }
            dptCount = dptList.countBy { it.department.name }

            for (int d = 0; d < results.departments.size(); d++) {
                if (dptCount.containsKey(results.departments[d].toString())) {
                    results.yearDepartments[d] += dptCount.get(results.departments[d])
                    results.monthDepartments[d][abs(m - 12)] += (dptCount.get(results.departments[d]))
                }

            }
        }

        def maxValues = new ArrayList()
        def maxIndices = new ArrayList()

        def tempDepts = new ArrayList()
        tempDepts.addAll(results.departments)
        def tempTotals = new ArrayList()
        tempTotals.addAll(results.totalDepartments)
        def tempYear = new ArrayList()
        tempYear.addAll(results.yearDepartments)
        def tempMonth = new ArrayList()
        tempMonth.addAll(results.monthDepartments)

        for (int v = 0; v < 5; v++) {

            maxValues[v] = tempTotals.toArray().max()
            maxIndices[v] = tempTotals.indexOf(maxValues[v])
            results.topFiveDepartments.add(tempDepts[maxIndices[v]])
            results.topFiveTotalDepartments.add(tempTotals[maxIndices[v]])
            results.topFiveYearDepartments.add(tempYear[maxIndices[v]])
            results.topFiveMonthDepartments.add(tempMonth[maxIndices[v]])

            tempDepts.remove(maxIndices[v])
            tempTotals.remove(maxIndices[v])
            tempYear.remove(maxIndices[v])
            tempMonth.remove(maxIndices[v])
        }

        //Courses

        //Cludgy code to remove Hibernate proxy classes

        def allCourses = RidConsTransaction.where {
            id >= 0 && courseName != "" && courseName != null
        }.list() { order("courseName") }.countBy { it.courseName }

        for (d in allCourses) {
            if (d.getValue() != null) {
                results.courses.add(d.getKey())
            }
        }


        for (t in results.courses) {
            results.coursesAdded++
            results.totalCourses.add(0)
            results.yearCourses.add(0)
            results.monthCourses.add(new ArrayList())
        }

        for (t in results.totalCourses) {
            t = 0
        }
        for (t in results.yearCourses) {
            t = 0
        }
        for (t in results.monthCourses) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        for (each in allCourses) {
            if (each.getKey() != "" && each.getKey() != null && each.getValue() != null) {
                int index = results.courses.indexOf(each.getKey())
                results.totalCourses[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)

            allCourses = RidConsTransaction.where {
                dateOfConsultation > begin.previous() && dateOfConsultation < end &&
                        id >= 0 && courseName != "" && courseName != null
            }.list() { order("courseName") }.countBy { it.courseName }
            for (map in allCourses) {
                if (map.getValue() == null) {
                    allCourses.remove(map)
                }
            }
            for (int d = 0; d < results.courses.size(); d++) {
                if (allCourses.containsKey(results.courses[d].toString())) {
                    results.yearCourses[d] += allCourses.get(results.courses[d])
                    results.monthCourses[d][abs(m - 12)] += (allCourses.get(results.courses[d]))
                }

            }
        }

        maxValues = new ArrayList()
        maxIndices = new ArrayList()

        def tempCourses = new ArrayList()
        tempCourses.addAll(results.courses)
        tempTotals = new ArrayList()
        tempTotals.addAll(results.totalCourses)
        tempYear = new ArrayList()
        tempYear.addAll(results.yearCourses)
        tempMonth = new ArrayList()
        tempMonth.addAll(results.monthCourses)
        def xOutOfFive
        if (results.coursesAdded < 5) {
            xOutOfFive = results.coursesAdded
        } else {
            xOutOfFive = 5
        }

        for (int v = 0; v < xOutOfFive; v++) {

            maxValues[v] = tempTotals.toArray().max()
            maxIndices[v] = tempTotals.indexOf(maxValues[v])
            results.topFiveCourses.add(tempCourses[maxIndices[v]])
            results.topFiveTotalCourses.add(tempTotals[maxIndices[v]])
            results.topFiveYearCourses.add(tempYear[maxIndices[v]])
            results.topFiveMonthCourses.add(tempMonth[maxIndices[v]])

            tempCourses.remove(maxIndices[v])
            tempTotals.remove(maxIndices[v])
            tempYear.remove(maxIndices[v])
            tempMonth.remove(maxIndices[v])
        }

//Ranks

//Cludgy code to remove Hibernate proxy classes
        def allRanks = RidRank.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allRanks) {
            results.ranks.add(d.getKey())
        }


        for (t in results.ranks) {
            results.totalRanks.add(0)
            results.yearRanks.add(0)
            results.monthRanks.add(new ArrayList())
        }
        for (t in results.totalRanks) {
            t = 0
        }
        for (t in results.yearRanks) {
            t = 0
        }
        for (t in results.monthRanks) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && rank != null
        }

        def rankList = query.list { order("rank") }
        def rankCount = rankList.countBy { it.rank.name }

        for (each in rankCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.ranks.indexOf(each.getKey())
                results.totalRanks[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && rank != null
            }

            rankList = query.list { order("rank") }
            rankCount = rankList.countBy { it.rank.name }

            for (int d = 0; d < results.ranks.size(); d++) {
                if (rankCount.containsKey(results.ranks[d].toString())) {
                    results.yearRanks[d] += rankCount.get(results.ranks[d])
                    results.monthRanks[d][abs(m - 12)] += (rankCount.get(results.ranks[d]))
                }

            }
        }
        //Push "Other" to end of list

        def otherIndex = results.ranks.indexOf("Other (please indicate)")

        def tempRank = results.ranks.remove(otherIndex)
        def tempTotalRank = results.totalRanks.remove(otherIndex)
        def tempYearRank = results.yearRanks.remove(otherIndex)
        def tempMonthRank = results.monthRanks.remove(otherIndex)
        results.ranks.add(tempRank)
        results.totalRanks.add(tempTotalRank)
        results.yearRanks.add(tempYearRank)
        results.monthRanks.add(tempMonthRank)
        otherIndex = results.ranks.indexOf("Other (Please indicate)")

        //User goals

//Cludgy code to remove Hibernate proxy classes
        def allUserGoals = RidUserGoal.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allUserGoals) {
            results.userGoals.add(d.getKey())
        }


        for (t in results.userGoals) {
            results.totalUserGoals.add(0)
            results.yearUserGoals.add(0)
            results.monthUserGoals.add(new ArrayList())
        }
        for (t in results.totalUserGoals) {
            t = 0
        }
        for (t in results.yearUserGoals) {
            t = 0
        }
        for (t in results.monthUserGoals) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && userGoal != null
        }

        def userGoalList = query.list { order("userGoal") }
        def userGoalCount = userGoalList.countBy { it.userGoal.name }

        for (each in userGoalCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.userGoals.indexOf(each.getKey())
                results.totalUserGoals[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && userGoal != null
            }

            userGoalList = query.list { order("userGoal") }
            userGoalCount = userGoalList.countBy { it.userGoal.name }

            for (int d = 0; d < results.userGoals.size(); d++) {
                if (userGoalCount.containsKey(results.userGoals[d].toString())) {
                    results.yearUserGoals[d] += userGoalCount.get(results.userGoals[d])
                    results.monthUserGoals[d][abs(m - 12)] += (userGoalCount.get(results.userGoals[d]))
                }

            }
        }
        //Push "Other" options to the end to end of list

        otherIndex = results.userGoals.indexOf("Other (please indicate)")

        def tempUserGoal = results.userGoals.remove(otherIndex)
        def tempTotalUserGoal = results.totalUserGoals.remove(otherIndex)
        def tempYearUserGoal = results.yearUserGoals.remove(otherIndex)
        def tempMonthUserGoal = results.monthUserGoals.remove(otherIndex)
        results.userGoals.add(tempUserGoal)
        results.totalUserGoals.add(tempTotalUserGoal)
        results.yearUserGoals.add(tempYearUserGoal)
        results.monthUserGoals.add(tempMonthUserGoal)

//Mode of Consultation

//Cludgy code to remove Hibernate proxy classes
        def allModeOfConsultation = RidModeOfConsultation.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allModeOfConsultation) {
            results.modeOfConsultation.add(d.getKey())
        }


        for (t in results.modeOfConsultation) {
            results.totalModeOfConsultation.add(0)
            results.yearModeOfConsultation.add(0)
            results.monthModeOfConsultation.add(new ArrayList())
        }
        for (t in results.totalModeOfConsultation) {
            t = 0
        }
        for (t in results.yearModeOfConsultation) {
            t = 0
        }
        for (t in results.monthModeOfConsultation) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && modeOfConsultation != null
        }

        def modeOfConsultationList = query.list { order("modeOfConsultation") }
        def modeOfConsultationCount = modeOfConsultationList.countBy { it.modeOfConsultation.name }

        for (each in modeOfConsultationCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.modeOfConsultation.indexOf(each.getKey())
                results.totalModeOfConsultation[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && modeOfConsultation != null
            }

            modeOfConsultationList = query.list { order("modeOfConsultation") }
            modeOfConsultationCount = modeOfConsultationList.countBy { it.modeOfConsultation.name }

            for (int d = 0; d < results.modeOfConsultation.size(); d++) {
                if (modeOfConsultationCount.containsKey(results.modeOfConsultation[d].toString())) {
                    results.yearModeOfConsultation[d] += modeOfConsultationCount.get(results.modeOfConsultation[d])
                    results.monthModeOfConsultation[d][abs(m - 12)] += (modeOfConsultationCount.get(results.modeOfConsultation[d]))
                }

            }
        }

//Schools

//Cludgy code to remove Hibernate proxy classes
        def allSchools = RidSchool.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allSchools) {
            results.schools.add(d.getKey())
        }


        for (t in results.schools) {
            results.totalSchools.add(0)
            results.yearSchools.add(0)
            results.monthSchools.add(new ArrayList())
        }
        for (t in results.totalSchools) {
            t = 0
        }
        for (t in results.yearSchools) {
            t = 0
        }
        for (t in results.monthSchools) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && school != null
        }

        def schoolList = query.list { order("school") }
        def schoolCount = schoolList.countBy { it.school.name }

        for (each in schoolCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.schools.indexOf(each.getKey())
                results.totalSchools[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && school != null
            }

            schoolList = query.list { order("school") }
            schoolCount = schoolList.countBy { it.school.name }

            for (int d = 0; d < results.schools.size(); d++) {
                if (schoolCount.containsKey(results.schools[d].toString())) {
                    results.yearSchools[d] += schoolCount.get(results.schools[d])
                    results.monthSchools[d][abs(m - 12)] += (schoolCount.get(results.schools[d]))
                }

            }
        }
        //Push "Other" options to the end to end of list

        otherIndex = results.schools.indexOf("Penn Other (please indicate)")

        def tempSchool = results.schools.remove(otherIndex)
        def tempTotalSchool = results.totalSchools.remove(otherIndex)
        def tempYearSchool = results.yearSchools.remove(otherIndex)
        def tempMonthSchool = results.monthSchools.remove(otherIndex)
        results.schools.add(tempSchool)
        results.totalSchools.add(tempTotalSchool)
        results.yearSchools.add(tempYearSchool)
        results.monthSchools.add(tempMonthSchool)

        otherIndex = results.schools.indexOf("Outside Penn (please indicate)")

        tempSchool = results.schools.remove(otherIndex)
        tempTotalSchool = results.totalSchools.remove(otherIndex)
        tempYearSchool = results.yearSchools.remove(otherIndex)
        tempMonthSchool = results.monthSchools.remove(otherIndex)
        results.schools.add(tempSchool)
        results.totalSchools.add(tempTotalSchool)
        results.yearSchools.add(tempYearSchool)
        results.monthSchools.add(tempMonthSchool)

        otherIndex = results.schools.indexOf("Other location (please indicate)")

        tempSchool = results.schools.remove(otherIndex)
        tempTotalSchool = results.totalSchools.remove(otherIndex)
        tempYearSchool = results.yearSchools.remove(otherIndex)
        tempMonthSchool = results.monthSchools.remove(otherIndex)
        results.schools.add(tempSchool)
        results.totalSchools.add(tempTotalSchool)
        results.yearSchools.add(tempYearSchool)
        results.monthSchools.add(tempMonthSchool)

//Course Sponsors

//Cludgy code to remove Hibernate proxy classes
        def allCourseSponsors = RidCourseSponsor.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allCourseSponsors) {
            results.courseSponsors.add(d.getKey())
        }


        for (t in results.courseSponsors) {
            results.totalCourseSponsors.add(0)
            results.yearCourseSponsors.add(0)
            results.monthCourseSponsors.add(new ArrayList())
        }
        for (t in results.totalCourseSponsors) {
            t = 0
        }
        for (t in results.yearCourseSponsors) {
            t = 0
        }
        for (t in results.monthCourseSponsors) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && courseSponsor != null
        }

        def courseSponsorList = query.list { order("courseSponsor") }
        def courseSponsorCount = courseSponsorList.countBy { it.courseSponsor.name }

        for (each in courseSponsorCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.courseSponsors.indexOf(each.getKey())
                results.totalCourseSponsors[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && courseSponsor != null
            }

            courseSponsorList = query.list { order("courseSponsor") }
            courseSponsorCount = courseSponsorList.countBy { it.courseSponsor.name }

            for (int d = 0; d < results.courseSponsors.size(); d++) {
                if (courseSponsorCount.containsKey(results.courseSponsors[d].toString())) {
                    results.yearCourseSponsors[d] += courseSponsorCount.get(results.courseSponsors[d])
                    results.monthCourseSponsors[d][abs(m - 12)] += (courseSponsorCount.get(results.courseSponsors[d]))
                }

            }
        }
        //Push "Other" options to the end to end of list

        otherIndex = results.courseSponsors.indexOf("Outside Penn (please indicate)")

        def tempCourseSponsor = results.courseSponsors.remove(otherIndex)
        def tempTotalCourseSponsor = results.totalCourseSponsors.remove(otherIndex)
        def tempYearCourseSponsor = results.yearCourseSponsors.remove(otherIndex)
        def tempMonthCourseSponsor = results.monthCourseSponsors.remove(otherIndex)
        results.courseSponsors.add(tempCourseSponsor)
        results.totalCourseSponsors.add(tempTotalCourseSponsor)
        results.yearCourseSponsors.add(tempYearCourseSponsor)
        results.monthCourseSponsors.add(tempMonthCourseSponsor)

//Services provided

//Cludgy code to remove Hibernate proxy classes
        def allServiceProvided = RidServiceProvided.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allServiceProvided) {
            results.serviceProvided.add(d.getKey())
        }


        for (t in results.serviceProvided) {
            results.totalServiceProvided.add(0)
            results.yearServiceProvided.add(0)
            results.monthServiceProvided.add(new ArrayList())
        }
        for (t in results.totalServiceProvided) {
            t = 0
        }
        for (t in results.yearServiceProvided) {
            t = 0
        }
        for (t in results.monthServiceProvided) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && serviceProvided != null
        }

        def serviceProvidedList = query.list { order("serviceProvided") }
        def serviceProvidedCount = serviceProvidedList.countBy { it.serviceProvided.name }

        for (each in serviceProvidedCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.serviceProvided.indexOf(each.getKey())
                results.totalServiceProvided[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {

            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && serviceProvided != null
            }

            serviceProvidedList = query.list { order("serviceProvided") }
            serviceProvidedCount = serviceProvidedList.countBy { it.serviceProvided.name }

            for (int d = 0; d < results.serviceProvided.size(); d++) {
                if (serviceProvidedCount.containsKey(results.serviceProvided[d].toString())) {
                    results.yearServiceProvided[d] += serviceProvidedCount.get(results.serviceProvided[d])
                    results.monthServiceProvided[d][abs(m - 12)] += (serviceProvidedCount.get(results.serviceProvided[d]))
                }

            }
        }
        //Push "Other" options to the end to end of list

        otherIndex = results.serviceProvided.indexOf("Other (please indicate)")

        def tempServiceProvided = results.serviceProvided.remove(otherIndex)
        def tempTotalServiceProvided = results.totalServiceProvided.remove(otherIndex)
        def tempYearServiceProvided = results.yearServiceProvided.remove(otherIndex)
        def tempMonthServiceProvided = results.monthServiceProvided.remove(otherIndex)
        results.serviceProvided.add(tempServiceProvided)
        results.totalServiceProvided.add(tempTotalServiceProvided)
        results.yearServiceProvided.add(tempYearServiceProvided)
        results.monthServiceProvided.add(tempMonthServiceProvided)

//Library Units

//Cludgy code to remove Hibernate proxy classes
        def allLibraryUnits = RidLibraryUnit.where { name != "" }.list() { order("name") }.countBy { it.name }

        for (d in allLibraryUnits) {
            results.libraryUnits.add(d.getKey())
        }


        for (t in results.libraryUnits) {
            results.totalLibraryUnits.add(0)
            results.yearLibraryUnits.add(0)
            results.monthLibraryUnits.add(new ArrayList())
        }
        for (t in results.totalLibraryUnits) {
            t = 0
        }
        for (t in results.yearLibraryUnits) {
            t = 0
        }
        for (t in results.monthLibraryUnits) {
            for (int i = 0; i <= 12; i++) {
                t.add(0)
            }
        }

        query = RidConsTransaction.where {
            id >= 0 && ridLibraryUnit != null
        }

        def libraryUnitList = query.list { order("ridLibraryUnit") }
        def libraryUnitCount = libraryUnitList.countBy { it.ridLibraryUnit.name }

        for (each in libraryUnitCount) {
            if (each.getKey() != "" && each.getKey() != null) {
                int index = results.libraryUnits.indexOf(each.getKey())
                results.totalLibraryUnits[index] = each.getValue()
            }
        }


        for (int m = 12; m >= 0; m--) {
            Date begin = new Date()
            Date end = new Date()
            int newMonth = begin.getAt(MONTH) - m
            int newYear = begin.getAt(YEAR)
            if (newMonth < 0) {
                newMonth += 12
                newYear -= 1
            }
            begin.set([date: 1, month: newMonth, year: newYear])
            newMonth += 1
            if (newMonth == 12) {
                newMonth = 0
                newYear += 1
            }

            end.set(date: 1, month: newMonth, year: newYear)
            end = end.minus(1)
            query = RidConsTransaction.where {
                id >= 0 && dateOfConsultation > begin.previous() && dateOfConsultation < end && ridLibraryUnit != null
            }

            libraryUnitList = query.list { order("ridLibraryUnit") }
            libraryUnitCount = libraryUnitList.countBy { it.ridLibraryUnit.name }

            for (int d = 0; d < results.libraryUnits.size(); d++) {
                if (libraryUnitCount.containsKey(results.libraryUnits[d].toString())) {
                    results.yearLibraryUnits[d] += libraryUnitCount.get(results.libraryUnits[d])
                    results.monthLibraryUnits[d][abs(m - 12)] += (libraryUnitCount.get(results.libraryUnits[d]))
                }

            }
        }






        return results
    }

    def statGraph(Map params, String transType) {
        def results = new RidStatisticsGraphReport()

        def query = RidConsTransaction.where {
            id >= 0
        }
        try {
            Date begin = new Date().minus(30);
            Date end = new Date()
            query = query.where {
                dateOfConsultation > begin.previous() && dateOfConsultation < end
            }
        } catch (Exception e) {}
        /*
        if (params.dateOfConsultation_start && params.dateOfConsultation_end) {
            try {
                Date start = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation_start)
                Date end = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation_end)
                query = query.where {
                    dateOfConsultation >= start && dateOfConsultation < end.next()
                }
            } catch (Exception e) {}
        }
        */
        def qList = query.list()
        results.transByDate = qList.countBy { it.dateOfConsultation }

        return results
    }

    def searchStats(Map params, String transType) {

        def results = new RidStatisticsSearchReport()


        if (params.ridLibraryUnitSearch.getClass() == String && !params.ridLibraryUnitSearch.equals("0")) {
            results.totalParams.add("ridLibraryUnit")
            results.totalNames.add(RidLibraryUnit.get(params.ridLibraryUnitSearch).toString())
        }
        if (params.staffPennkey.getClass() == String && !params.staffPennkey.equals("")) {
            results.totalParams.add("staffPennkey")
            results.totalNames.add(params.staffPennkey)
        }

        if (params.ridSchoolSearch.getClass() == String && !params.ridSchoolSearch.equals("0")) {
            results.totalParams.add("school")
            results.totalNames.add(RidSchool.get(params.ridSchoolSearch).toString())
        }

        if (params.ridDepartmentSearch.getClass() == String && !params.ridDepartmentSearch.equals("0")) {
            results.totalParams.add("department")
            results.totalNames.add(RidDepartment.get(params.ridDepartmentSearch).toString())
        }

        if (params.iterateAll) {
            iterateChoices(params, results.totalParams, results.inputs)
            iterateChoices(params, results.totalNames, results.inputNames)
        } else {
            results.inputs.add(results.totalParams)
            results.inputNames.add(results.totalNames)
        }

        for (i in results.inputs) {
            ArrayList temp = i
            def query = RidConsTransaction.where {
                id >= 0
            }

            if (params.dateOfConsultation_start && params.dateOfConsultation_end) {
                try {
                    Date start = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation_start)
                    Date end = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation_end)
                    query = query.where {
                        dateOfConsultation > begin.previous() && dateOfConsultation < end
                    }
                } catch (Exception e) {}
            }

            if (temp.contains("ridLibraryUnit")) {
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

            if (temp.contains("staffPennkey")) {
                String[] staffPennkey_splits = params.staffPennkey.split(" ");
                for (String s in staffPennkey_splits) {
                    if (!s.trim().isEmpty()) {
                        query = query.where {
                            staffPennkey ==~ ~s.trim()
                        }
                    }
                }
            }

            if (temp.contains("school")) {
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

            if (temp.contains("department")) {
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

            if (query.count() > 0) {
                def interactSum = 0
                for (s in query) {
                    interactSum += s.interactOccurrences
                }
                results.avgInteractOccurrences.add(((double) (interactSum / query.count())).round(1))


                def prepSum = 0
                for (p in query) {
                    prepSum += p.prepTime
                }
                results.avgPrepTime.add(((double) (prepSum / query.count())).round(1))


                def eventSum = 0
                for (e in query) {
                    eventSum += e.eventLength
                }
                results.avgEventLength.add(((double) (eventSum / query.count())).round(1))
            }

            results.totalTransactions.add(query.count())


        }
        for (s in results.totalTransactions) {
            results.transactionSum += s
        }
        return results

    }

    def iterateChoices(Map params, ArrayList totalParams, ArrayList finished) {

        ArrayList temp = new ArrayList()

        if (totalParams.size() == 4) {
            finished.add(totalParams)
            temp.add(totalParams[0])
            temp.add(totalParams[1])
            temp.add(totalParams[2])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[1])
            temp.add(totalParams[3])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[2])
            temp.add(totalParams[3])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[1])
            temp.add(totalParams[2])
            temp.add(totalParams[3])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[1])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[2])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[3])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[1])
            temp.add(totalParams[2])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[1])
            temp.add(totalParams[3])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[2])
            temp.add(totalParams[3])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            finished.add(temp)
            temp = new ArrayList()
            temp.add(totalParams[1])
            finished.add(temp)
            temp = new ArrayList()
            temp.add(totalParams[2])
            finished.add(temp)
            temp = new ArrayList()
            temp.add(totalParams[3])
            finished.add(temp)

        } else if (totalParams.size() == 3) {
            finished.add(totalParams)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[1])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            temp.add(totalParams[2])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[1])
            temp.add(totalParams[2])
            finished.add(temp)

            temp = new ArrayList()
            temp.add(totalParams[0])
            finished.add(temp)
            temp = new ArrayList()
            temp.add(totalParams[1])
            finished.add(temp)
            temp = new ArrayList()
            temp.add(totalParams[2])
            finished.add(temp)

        } else if (totalParams.size() == 2) {
            finished.add(totalParams)
            temp = new ArrayList()
            temp.add(totalParams[0])
            finished.add(temp)
            temp = new ArrayList()
            temp.add(totalParams[1])
            finished.add(temp)
        } else {
            finished.add(totalParams)
        }
        return finished
    }


}
