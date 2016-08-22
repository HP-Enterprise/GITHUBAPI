define(['../scripts/git','jquery'],function(module,$){
    module.controller("milestoneListCtrl",function($scope,$http,$routeParams,$filter){
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


        $scope.returnMilestone=function(){
            $scope.myMiles1=!$scope.myMiles1;
            $scope.myMiles2=!$scope.myMiles2;
        };
        $scope.deleteMiles=function(number){
            var url="/api/deleteMiles/"+ $scope.str+"/"+number;
            $http.delete(url).success(function (data) {
                alert("success");
            }).error(function (err) {
                console.log(err);
            });
        };

        $scope.updateMilestone=function(a){
            $scope.ab=$filter('date')(a.dueOn,'yyyy/MM/dd');
            $scope.updateMil={"title": a.title,"state": a.state,"description": a.description,"dueOn":$scope.ab}
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