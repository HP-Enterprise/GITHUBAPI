define(['../scripts/git','jquery'],function(module,$){
    module.controller("RepositoryDetailCtrl",function($scope,$http,$routeParams){
        $scope.repository = $routeParams.repository;
        $scope.organization = $routeParams.organization;
        console.log( $scope.organization);
        var getCookie = function(name){
            var arr = document.cookie.split("; ");
            for(var i=0,len=arr.length;i<len;i++){
                var item = arr[i].split("=");
                if(item[0]==name){
                    return item[1]
                }
            }
            return "";
        };
        var loginCookie = getCookie('token');

        var url1="/api/repositoryBranch/"+$scope.repository+"/"+$scope.organization+"/"+loginCookie;
        $http.get(url1).success(function (data) {
            $scope.repositoryBranch =data.message;
            $scope.branches=data.message.length;
        }).error(function (err) {
            console.log(err);
        });
        var url2="/api/contributorList/"+$scope.repository+"/"+$scope.organization+"/"+loginCookie;
        $http.get(url2).success(function (data) {
            $scope.contributorList =data.message;
            $scope.contributors =data.message.length;
        }).error(function (err) {
            console.log(err);
        });
        var url3="/api/repositoryCommit/"+$scope.repository+"/"+$scope.organization+"/"+loginCookie;
        $http.get(url3).success(function (data) {
            $scope.commitList =data.message;
            $scope.commits =data.message.length;
        }).error(function (err) {
            console.log(err);
        });
    });
});