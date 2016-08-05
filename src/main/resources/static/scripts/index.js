var app = angular.module('myApp', []);
app.controller('formCtrl', function ($scope, $http) {

    $scope.myLabel=false;
    $scope.myMilestone=false;
    $scope.bt=function(b) {
        switch (b) {
            case 1:$scope.myLabel = !$scope.myLabel;
                break;
            case 2:$scope.myMilestone = !$scope.myMilestone;
                break;
        }
    };

    $http.get("/api/repositoryList").success(function (data) {
        $scope.repository1 = data.message;
        console.log($scope.repository1)
    }).error(function (err) {
        console.log(err);
    });
    $scope.modifyDiv=function(a){
        $scope.myLabel=!$scope.myLabel;
        window.location.href="labelList.html#"+a;
    }
    $scope.modifyDiv2=function(a){
        $scope.myMilestone=!$scope.myMilestone;
        window.location.href="milestoneList.html#"+a;
    }


});

