(function (angular) {
    "use strict";
    angular.module('nc').run(['editableOptions', Run]);

    function Run(editableOptions) {
        editableOptions.theme = 'bs3';
    }

}(window.angular));