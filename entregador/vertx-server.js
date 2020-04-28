var Vertx = require("vertx-js/vertx");
var options = {};
Vertx.clusteredVertx(options, function (res, res_err) {
    if (res_err == null) {
        var vertx = res;
        var eventBus = vertx.eventBus();
        console.log("We now have a clustered event bus: " + eventBus);

    } else {
        console.log("Failed: " + res_err);
    }
});
