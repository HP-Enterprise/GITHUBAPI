var app = angular.module('myApp', []);
app.controller('formCtrl', function ($scope, $http,$location) {
    $scope.myVar1 = false;
    $scope.myVar2 = true;
    $scope.str = $location.url().substring(1); //ȡ��#������ַ��
    console.log($scope.str)
         $scope.ade = {
             params: {
                 repository:  $scope.str
             }
         };
         $http.get("/api/labelList", $scope.ade).success(function (data) {
             $scope.labelList =data.message;

         }).error(function (err) {
             console.log(err);
         });
    $scope.updateLabel = function (aa) {
        $scope.labels = aa;
        $scope.myVar1 = !$scope.myVar1;
        $scope.myVar2 = !$scope.myVar2;

    };
    $scope.modifyLabel = function (label) {

        var url="/api/addLabel/"+$scope.str;
        $http.post(url, label).success(function () {
            alert("success");
        }).error(function () {
            alert("error");
        });
        $scope.myVar1 = !$scope.myVar1;
        $scope.myVar2 = !$scope.myVar2;
    };
});
