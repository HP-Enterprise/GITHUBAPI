define(['../scripts/git','jquery'],function(module,$){
    module.controller("addMilestoneCtrl",function($scope,$http){
        $scope.myMiles1=true;
        $scope.myMiles2=false;
        $scope.bt=function(){
            $scope.myMiles1=!$scope.myMiles1;
            $scope.myMiles2=!$scope.myMiles2;
        };
        $http.get("/api/repositoryList").success(function (data) {
            $scope.repository1 = data.message;
            console.log($scope.repository1)
        }).error(function (err) {
            console.log(err);
        });

        $scope.submit1 = function (a,milestone) {
            var url="/api/addMilestone/"+a;
            $http.post(url, milestone).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
        }

        $scope.submit2 = function (repository,year) {
            console.log(year);
            var url="/api/addAllMilestones/"+year;
            $http.post(url,repository ).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
        }

    });
});