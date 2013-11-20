modules = {

    tableModule {
        dependsOn "application"
        resource id: 'css',
                url: [plugin: 'metridocRid', dir: 'css', file: 'pagination.css'],
                attrs: [type: 'css']
        resource id: 'css',
                url: [plugin: 'metridocRid', dir: 'css', file: 'table.css'],
                attrs: [type: 'css']
        resource id: 'css',
                url: [plugin: 'metridocRid', dir: 'css', file: 'floating_table.css'],
                attrs: [type: 'css']
    }


    ridTransaction {
        dependsOn "application", "datePicker"
        resource id: 'css',
                url: [plugin: 'metridocRid', dir: 'css', file: 'RidTransaction.css'],
                attrs: [type: 'css']
        resource id: 'js',
                url: [plugin: 'metridocRid', dir: 'js', file: 'RidTransaction.js'],
                attrs: [type: 'js']
    }

    statistics {
        resource id: 'js',
                url: [plugin: 'metridocRid', dir: 'js', file: 'RidStatistics.js'],
                attrs: [type: 'js']
        resource id: 'js',
                url: [plugin: 'metridocRid', dir: 'js', file: 'RidStatisticsGraph.js'],
                attrs: [type: 'js']
    }


}