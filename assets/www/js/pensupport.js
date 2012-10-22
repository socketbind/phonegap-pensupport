var PenSupport = function() {
    cordova.exec.setNativeToJsBridgeMode(cordova.exec.nativeToJsModes.PRIVATE_API); // this is quite important as the other approaches lack the necessary performance. works only on 3.2+
};

PenSupport.prototype.enableEvents = function(element) {
    cordova.exec(function(param) {
        var targetedElement = document.elementFromPoint(param.x, param.y);

        if (targetedElement == element) {

            var correctedX = param.x - element.offsetLeft;
            var correctedY = param.y - element.offsetTop;

            var penEvent = document.createEvent("Event");
            penEvent.initEvent("pen", false, true);

            penEvent.action = param.action;
            penEvent.x = correctedX;
            penEvent.y = correctedY;

            element.dispatchEvent(penEvent);
        }

    }, function(error) {
        alert("Unable to initialize pen support: " + error);
    }, "PenSupport", "start", []);
};

PenSupport.prototype.disableEvents = function() {
    cordova.exec(function(param) {
        // ok
    }, function(error) {
        alert("Unable to stop pen support: " + eerror);
    }, "PenSupport", "stop", []);
};

