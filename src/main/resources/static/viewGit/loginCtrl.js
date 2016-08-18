'use strict';
define(['../scripts/git','jquery'],function(module){
    module.controller("loginCtrl",function($scope,$http,$location){

        $scope.loginMgr = function (userAccount) {
            $http.post('/api/loginGit', userAccount).success(function (data, status, headers, config) {
                window.location.href ="/"
            }).error(function (data, status, headers, config) {
                console.log(status);
                alert("用户名或密码错误！");
            });
        }
    });
});