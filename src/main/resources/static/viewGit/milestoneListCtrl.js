define(['../scripts/git','jquery'],function(module,$){
    module.controller("milestoneListCtrl",function($scope,$http,$routeParams){
        $scope.myMiles1=false;
        $scope.myMiles2=true;
        $scope.str = $routeParams.repository;
        console.log($scope.str)
        $scope.ade = {
            params: {
                repository:  $scope.str
            }
        };
        $http.get("/api/milestoneList", $scope.ade).success(function (data) {
            $scope.milestoneList =data.message;
            console.log( $scope.milestoneList)
        }).error(function (err) {
            console.log(err);
        });
        $scope.deleteMiles=function(number){
            var url="/api/deleteMiles/"+ $scope.str+"/"+number;
            $http.delete(url).success(function (data) {
                alert("success");
            }).error(function (err) {
                console.log(err);
            });
        };
        $scope.updateMilestone=function(a){
            console.log(a)
            $scope.updateMil=a;
            $scope.myMiles1=!$scope.myMiles1;
            $scope.myMiles2=!$scope.myMiles2;
        };
        $scope.modifyMilestone = function (milestone) {
            var url="/api/editMilestone/"+$scope.str;
            $http.post(url, milestone).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
            $scope.myMiles1=!$scope.myMiles1;
            $scope.myMiles2=!$scope.myMiles2;
        };
    });
});