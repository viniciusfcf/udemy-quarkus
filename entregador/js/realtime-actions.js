
function init() {
    registerHandler();
};

function registerHandler() {
    var eventBus = new EventBus('http://localhost:8082/localizacoes');
    eventBus.onopen = function () {
        eventBus.registerHandler('novaLocalizacao', function (error, message) {
            document.getElementById('localizacoes').value +=message.body+'\n\n----------------\n\n';
        });
    }
};

