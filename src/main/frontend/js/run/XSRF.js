(function (angular) {
    "use strict";
    angular.module('nc').run(['$http', XSRFConfig]);

    function XSRFConfig($http) {

        var token = document.querySelector("meta[name='_csrf']").getAttribute("content");
        var header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
        $http.defaults.headers.common[header] = token;

    }

}(window.angular));