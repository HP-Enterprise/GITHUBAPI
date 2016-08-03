var app = angular.module('myApp', []);
app.controller('formCtrl', function ($scope, $http) {
    $scope.submit = function (repository) {
        console.log(repository)
        $http.post("/api/addOrgRepository", repository).success(function () {
            alert("success");
        }).error(function () {
            alert("error");
        })
    }

});

