define(['../scripts/git','jquery'],function(module,$){
    module.controller("addRepositoryCtrl",function($scope,$http){
        $scope.repository = {};
        $scope.submit = function (repository) {
            console.log(repository)
            $http.post("/api/addOrgRepository", repository).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
        }

    });
});