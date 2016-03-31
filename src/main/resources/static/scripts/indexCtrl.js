var app = angular.module('indexApp',['ngResource']);

app.controller("indexCtrl", function($scope, $http, $location, $resource){
    $scope.getAllGitHubWork = function(){
        $http.get("/api/work").success(function(data,status,headers){
            console.log(JSON.stringify(data));
        }).error(function(err){
            console.log(err);
        })
    };
    $scope.getAllGitHubWork();

});