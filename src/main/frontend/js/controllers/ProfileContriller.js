(function (angular) {
    "use strict";
    angular.module('nc').controller("ProfileController", ['$http', ProfileController]);

    function ProfileController($http) {
        var pc = this;
        $http.get('/users/me')
            .success(function (data) {
                pc.user = data;
            })
            .error(function (err) {
                pc.error = err;
                console.error(err);
            });

        pc.saveUser = function () {
            // pc.user already updated!
            return $http.post('/users/me', pc.user).error(function (err) {
                if (err && err instanceof Object) {
                    // err like {"name": "Server-side error for this username!"}
                    Object.keys(err).forEach(function (k) {
                        pc.editableForm.$setError(k, err[k]);
                    });
                } else {
                    // unknown error
                    pc.editableForm.$setError('username', 'Unknown error!');
                }
            });
        };
    }

}(window.angular));