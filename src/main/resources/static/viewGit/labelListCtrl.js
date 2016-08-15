define(['../scripts/git','jquery'],function(module,$){
    module.controller("labelListCtrl",function($scope,$http,$routeParams){

        $scope.myVar1 = false;
        $scope.myVar2 = true;
        $scope.str = $routeParams.repository;
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
        $scope.deleteLabel=function(name){
            var url="/api/deleteLabel/"+$scope.str+"/"+name;
            $http.delete(url).success(function () {
                alert("success");
            }).error(function (err) {
                console.log(err);
            })

        };
        $scope.updateLabel = function (aa) {
            $scope.labels = aa;
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;
        };
        $scope.modifyLabel = function (label) {
            var url="/api/editLabel/"+$scope.str+"/"+$scope.labels.name;
            $http.post(url, label).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            });
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;
        };


    });
});