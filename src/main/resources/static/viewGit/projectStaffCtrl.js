define(['../scripts/git','jquery'],function(module,$){
    module.controller("projectStaffCtrl",function($scope,$http,$routeParams){
        $scope.repository = $routeParams.repository;
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

        var url1="/api/contributorList/"+$scope.repository+"/"+loginCookie;
        $http.get(url1).success(function (data) {
            $scope.contributorList =data.message;
        }).error(function (err) {
            console.log(err);
        });
    });
});