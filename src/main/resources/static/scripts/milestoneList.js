var app = angular.module('myApp', []);
app.controller('formCtrl', function ($scope, $http,$location) {
    $scope.str = $location.url().substring(1); //取得#整个地址栏
    console.log($scope.str)
         $scope.ade = {
             params: {
                 repository:  $scope.str
             }
         };
         $http.get("/api/milestoneList", $scope.ade).success(function (data) {
             $scope.milestoneList =data.message;
         }).error(function (err) {
             console.log(err);
         });
});

