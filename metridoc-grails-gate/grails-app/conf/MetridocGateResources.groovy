modules = {
    gateTransaction {
        dependsOn "application", "datePicker"
        resource id: 'js',
                url: [plugin: 'metridocGate', dir: 'js', file: 'GateTransaction.js'],
                attrs: [type: 'js']
    }
}