define(['../scripts/git','jquery'],function(module,$){
    module.controller("repositoryListCtrl",function($scope,$http){
        $scope.myVar1 = false;
        $scope.myVar2 = true;
        $scope.deleteRepository = function (aw) {
            console.log(aw)

            $scope.ade = {
                params: {
                    repository: aw
                }
            };
            $http.delete("/api/deleteOrgRepository", $scope.ade).success(function () {
                alert("success");
            }).error(function (err) {
                console.log(err);
            })

        };

        $scope.updateRepository = function (aa) {
            $scope.updateRep = aa;
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;

        };
        $scope.modifyRepository = function (a) {

            var url="api/updateOrgRepository/"+$scope.updateRep.name;

            $http.post(url, a).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;
        };
        $http.get("/api/repositoryList").success(function (data) {
            $scope.repository = data.message;
            console.log($scope.repository)

        }).error(function (err) {
            console.log(err);
        })
    });
});