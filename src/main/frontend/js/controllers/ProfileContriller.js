(function (angular) {
    "use strict";
    angular.module('nc').controller("ProfileController", ['$http', ProfileController]);

    function ProfileController($http) {
        var pc = this;
        $http.get('/api/users/me')
            .success(function (data) {
                pc.user = data;
            })
            .error(function (err) {
                pc.error = err;
                console.error(err);
            });

        pc.saveUser = function () {
            // pc.user already updated!
            return $http.put('/api/users/me', pc.user)
                .success(function (data) {
                    pc.user = data;
                })
                .error(function (err) {
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

        pc.updatePassword = function () {
            // pc.user already updated!
            return $http.put('/api/users/me/updatePassword', pc.user)
                .success(function (data) {
                    pc.user = data;
                })
                .error(function (err) {
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