/*
  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
modules = {

    home {
        dependsOn 'jquery'
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'home/js', file: 'home.js'],
                attrs: [type: 'js']
    }

    manageConfig {
        resource id: 'js',
                url: [dir: "manageConfig", file: "manageConfig.css", plugin: "metridocCore"],
                attrs: [type: "css"]
    }

    manageAccess {
        dependsOn 'manageReport', 'user', 'role'
        resource id: 'js',
                url: [dir: "manageAccess/js", file: "manageAccess.js", plugin: "metridocCore"],
                attrs: [type: "js"]
    }

    manageReport {
        dependsOn 'jquery'
        resource id: 'css',
                url: [plugin: "metridocCore", dir: 'manageReport/css', file: 'manageReport.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'manageReport/js', file: 'manageReport.js'],
                attrs: [type: 'js']

    }

    changePassword {
        dependsOn 'jquery'
        resource id: 'css',
                url: [plugin: "metridocCore", dir: 'css', file: 'changePassword.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'js', file: 'changePassword.js'],
                attrs: [type: 'js']
    }

    user {
        dependsOn 'jquery', 'changePassword'
        resource id: 'css',
                url: [plugin: "metridocCore", dir: 'user/css', file: 'user.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'user/js', file: 'user.js'],
                attrs: [type: 'js']
    }

    role {
        dependsOn 'jquery'
        resource id: 'css',
                url: [plugin: "metridocCore", dir: 'role/css', file: 'role.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'role/js', file: 'role.js'],
                attrs: [type: 'js']
    }

    ldapRole {
        dependsOn 'jquery'
        resource id: 'css',
                url: [plugin: "metridocCore", dir: 'ldapRole/css', file: 'ldapRole.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'ldapRole/js', file: 'ldapRole.js'],
                attrs: [type: 'js']
    }

    application {
        dependsOn 'jquery', 'bootstrap', 'bootstrap-responsive-less', 'font-awesome'
        resource id: 'appJs',
                url: [plugin: "metridocCore", dir: 'js', file: 'application.js'],
                attrs: [type: 'js']
        resource id: 'faviconIco',
                url: [plugin: "metridocCore", dir: 'images', file: 'favicon.ico']
        resource id: 'mainCss',
                url: [plugin: "metridocCore", dir: 'css', file: 'main.css'],
                attrs: [type: 'css']
    }

    login {
        dependsOn 'jquery'
        resource id: 'loginCss',
                url: [plugin: "metridocCore", dir: 'auth/css', file: 'login.css'],
                attrs: [type: 'css']
        resource id: 'loginJs',
                url: [plugin: "metridocCore", dir: 'auth/js', file: 'login.js'],
                attrs: [type: 'js']
    }

    status {
        dependsOn 'jquery'
    }

    log {
        dependsOn 'jquery'
        resource id: 'css',
                url: [plugin: "metridocCore", dir: 'log/css', file: 'log.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: "metridocCore", dir: 'log/js', file: 'log.js'],
                attrs: [type: 'js']
    }
}