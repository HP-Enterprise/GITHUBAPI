define(['../scripts/git','jquery'],function(module,$){
    module.controller("labelListCtrl",function($scope,$http,$routeParams){

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
       $scope.labelTemplate="labelList";

        $scope.repository = $routeParams.repository;
        $scope.ade = {
            params: {
                repository:  $scope.repository
            }
        };
        var url1="/api/labelList/"+loginCookie;
        $http.get(url1, $scope.ade).success(function (data) {
            $scope.labelList =data.message;

        }).error(function (err) {
            console.log(err);
        });
        $scope.deleteLabel=function(name){
            var url="/api/deleteLabel/"+$scope.repository+"/"+name+"/"+loginCookie;
            $http.delete(url).success(function () {
                alert("success");
            }).error(function (err) {
                console.log(err);
            })

        };
        $scope.addLabel=function(){
            $scope.labelTemplate="labelAdd";
        };
        $scope.cancel=function(){
            $scope.labelTemplate="labelList";
        }
        $scope.updateLabel = function (aa) {
            $scope.labels = aa;
            $scope.labelTemplate="labelModify";
        };
        $scope.modifyLabel = function (label) {
            var url="/api/editLabel/"+$scope.repository+"/"+$scope.labels.name+"/"+loginCookie;
            $http.post(url, label).success(function () {
                alert("success");
                $scope.labelTemplate="labelList";
            }).error(function () {
                alert("error");
            });

        };
        $scope.submit1 = function (label) {
            var url="/api/addLabel/"+ $scope.repository+"/"+loginCookie;
            $http.post(url, label).success(function () {
                alert("success");
                $scope.labelTemplate="labelList";
            }).error(function () {
                alert("error");
            })
        }

    });
});