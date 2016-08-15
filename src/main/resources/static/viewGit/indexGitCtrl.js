define(['../scripts/git','jquery'],function(module,$){
    module.controller("indexGitCtrl",function($scope,$http,$resource,$location){

        $scope.myLabel = false;
        $scope.myMilestone = false;
        $scope.myChart = false;
        $scope.bt = function (b) {
            switch (b) {
                case 1:
                    $scope.myLabel = !$scope.myLabel;
                    break;
                case 2:
                    $scope.myMilestone = !$scope.myMilestone;
                    break;
                case 3:
                    $scope.myChart = !$scope.myChart;
                    break;
            }
        };
        $http.get("/api/repositoryList").success(function (data) {
            $scope.repository1 = data.message;
            console.log($scope.repository1)
        }).error(function (err) {
            console.log(err);
        });
        $http.get("/api/orgMembers").success(function (data) {
            $scope.assignees = data.message;
            console.log($scope.assignees);
        }).error(function (err) {
            console.log(err);
        });

        $scope.modifyDiv = function (a) {
            $scope.myLabel = !$scope.myLabel;
            $scope.url="/gitHubApi/labelList/"+a;
            $location.path( $scope.url);
        }
        $scope.modifyDiv1 = function (a) {
            $scope.myChart = !$scope.myChart;

            $scope.url="/gitHubApi/highChart/"+a;
            $location.path( $scope.url);
        }

        $scope.modifyDiv2 = function (a) {
            $scope.myMilestone = !$scope.myMilestone;
            $scope.url="/gitHubApi/milestoneList/"+a;
            $location.path( $scope.url);
        }
    });
});