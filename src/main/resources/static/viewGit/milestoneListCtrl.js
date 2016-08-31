define(['../scripts/git','jquery'],function(module,$){
    module.controller("milestoneListCtrl",function($scope,$http,$routeParams,$filter){
        $scope.milestoneTemplate="milestoneList";
        $scope.repository = $routeParams.repository;
        console.log($scope.repository)
        $scope.ade = {
            params: {
                repository:  $scope.repository
            }
        };
        $http.get("/api/milestoneList", $scope.ade).success(function (data) {
            $scope.milestoneList =data.message;
            console.log( $scope.milestoneList)
        }).error(function (err) {
            console.log(err);
        });


        $scope.addMilestone=function(){
            $scope.milestoneTemplate="milestoneAdd";
        };
        $scope.cancel=function(){
            $scope.milestoneTemplate="milestoneList";
        }
        $scope.deleteMiles=function(number){
            var url="/api/deleteMiles/"+ $scope.repository+"/"+number;
            $http.delete(url).success(function (data) {
                alert("success");
            }).error(function (err) {
                console.log(err);
            });
        };

        $scope.updateMilestone=function(a){
            $scope.ab=$filter('date')(a.dueOn,'yyyy/MM/dd');
            $scope.updateMil={"title": a.title,"state": a.state,"description": a.description,"dueOn":$scope.ab}
            $scope.milestoneTemplate="milestoneModify";
        };
        $scope.modifyMilestone = function (milestone) {
            var url="/api/editMilestone/"+$scope.repository;
            $http.post(url, milestone).success(function () {
                alert("success");
                $scope.milestoneTemplate="milestoneList";
            }).error(function () {
                alert("error");
            })
        };

        $scope.submit1 = function (milestone) {
            var url="/api/addMilestone/"+$scope.repository;
            $http.post(url, milestone).success(function () {
                alert("success");
                $scope.milestoneTemplate="milestoneList";
            }).error(function () {
                alert("error");
            })
        };
        $scope.addUsualMilestone=function(){
            var url="/api/addAllMilestones";
            $http.post(url,$scope.repository ).success(function () {
                alert("success");
                $scope.milestoneTemplate="milestoneList";
            }).error(function () {
                alert("error");
            })
        }

    });
});