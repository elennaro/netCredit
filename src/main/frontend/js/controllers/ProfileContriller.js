(function (angular) {
    "use strict";
    angular.module('nc').controller("ProfileController", [ProfileController]);

    function ProfileController(){
        var pc = this;
        pc.hello = "Ahoj";
        console.log("Hello", pc.hello);
    }

}(window.angular));