'use strict';
define(['../scripts/git','jquery'],function(module){
    module.controller("loginCtrl",function($scope,$http,$location){

        $scope.loginMgr = function (userAccount) {

            $http.post('/api/loginGit', userAccount).success(function (data, status, headers, config) {
                console.log(11111111111111);
                window.location.href ="/"

            }).error(function (data, status, headers, config) {
                console.log(2222222222);
                console.log(status);
                alert("error");
            });
        }
    });
});