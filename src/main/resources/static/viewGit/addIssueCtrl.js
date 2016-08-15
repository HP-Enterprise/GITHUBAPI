define(['../scripts/git','jquery'],function(module,$){
    module.controller("addIssueCtrl",function($scope,$http){

        $scope.submit = function (addIssue) {
            console.log(addIssue);
            $http.post("api/addIssue", addIssue).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
        };
        $scope.labels1=[];

        $scope.show = function (a) {

            $scope.ade = {
                params: {
                    repository: a
                }
            };
            $http.get("/api/labelList", $scope.ade).success(function (data) {
                $scope.labels1 =data.message;
                console.log($scope.labels1);
            }).error(function (err) {
                console.log(err);
            });
            $http.get("/api/milestoneList", $scope.ade).success(function (data) {
                $scope.milestone = data.message;
            }).error(function (err) {
                console.log(err);
            });
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

    });
});