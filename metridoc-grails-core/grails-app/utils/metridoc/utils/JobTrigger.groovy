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
  *	permissions and limitations under the License.  */

package metridoc.utils

/**
 * Created with IntelliJ IDEA.
 * User: tbarker
 * Date: 3/4/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public enum JobTrigger {
    NEVER, EVERY_MINUTE, EVERY_5_MINUTES, EVERY_10_MINUTES, EVERY_15_MINUTES, EVERY_30_MINUTES, EVERY_HOUR,
    EVERY_6_HOURS, EVERY_12_HOURS, EVERY_DAY, MIDNIGHT, TIME_1_00, TIME_2_00, TIME_3_00, TIME_4_00, TIME_5_00,
    TIME_6_00, TIME_7_00, TIME_8_00, TIME_9_00, TIME_10_00, TIME_11_00, TIME_12_00, TIME_13_00, TIME_14_00, TIME_15_00,
    TIME_16_00, TIME_17_00, TIME_18_00, TIME_19_00, TIME_20_00, TIME_21_00, TIME_22_00, TIME_23_00, IN_CODE, CUSTOM_CRON
}