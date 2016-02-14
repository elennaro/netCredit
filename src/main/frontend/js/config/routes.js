(function (angular) {
    "use strict";
    var nc = angular.module('nc').config(['$stateProvider', '$urlRouterProvider', '$locationProvider', ConfigRoutes]);

    function ConfigRoutes($stateProvider, $urlRouterProvider, $locationProvider) {
        $locationProvider.html5Mode({ enabled: true, requireBase: false });
        $stateProvider
            .state('profile', {
                abstract: true,
                controller: 'ProfileController as pc',
                template: '<ui-view/>'
            })
            .state('edit', {
                url: '/',
                parent: 'profile',
                templateUrl: '/resources/templates/profile-edit.html'
            })
            .state('password', {
                url: '/password',
                parent: 'profile',
                templateUrl: '/resources/templates/profile-password.html'
            });
        $urlRouterProvider.otherwise("/");
    }
}(window.angular));