define(['../scripts/git','jquery'],function(module,$){
    module.controller("addRepositoryCtrl",function($scope,$http,$location){
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
        $scope.repository = {};
        $scope.submit = function (repository) {
           var url="/api/addOrgRepository/"+loginCookie;
            $http.post(url,repository).success(function () {
                alert("GitHub add success");

                $http.post("/api/addProject",repository).success(function () {
                    alert("location add success");
                }).error(function () {
                    alert("location add error");
                });
            }).error(function () {
                alert("GitHub add error");
            });
        }
        $scope.cancel=function(){
            //window.location.href="/gitHubApi/repositoryList";
            $location.path("/gitHubApi/repositoryList");
        }
    });
});