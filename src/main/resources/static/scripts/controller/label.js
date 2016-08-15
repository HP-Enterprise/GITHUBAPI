var app = angular.module('myApp', []);
app.controller('formCtrl', function ($scope, $http) {
    $scope.myMiles1=true;
    $scope.myMiles2=false;
    $scope.bt=function(){
        $scope.myMiles1=!$scope.myMiles1;
        $scope.myMiles2=!$scope.myMiles2;
    };
    $http.get("/api/repositoryList").success(function (data) {
        $scope.repository1 = data.message;
    }).error(function (err) {
        console.log(err);
    });
    $scope.submit1 = function (a,label) {
       var url="/api/addLabel/"+a;
        $http.post(url, label).success(function () {
            alert("success");
        }).error(function () {
            alert("error");
        })
    }
    $scope.submit2 = function (a) {
        $http.post("api/addAllLabels",a ).success(function () {
            alert("success");
        }).error(function () {
            alert("error");
        })
    }

});

