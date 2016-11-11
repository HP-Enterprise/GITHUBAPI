define(['../scripts/git','jquery'],function(module,$){
    module.controller("labelListCtrl",function($scope,$http,$routeParams){
        $scope.organization = $routeParams.organization;
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

        var   getLabelList=function(){
            $scope.repository = $routeParams.repository;
            $scope.ade = {
                params: {
                    repository:  $scope.repository,
                    organization:$scope.organization
                }
            };
            var url1="/api/labelList/"+loginCookie;
            $http.get(url1, $scope.ade).success(function (data) {
                $scope.labelList =data.message;

            }).error(function (err) {
                console.log(err);
            });
            return $scope.labelList;

        }
       $scope.labelTemplate="labelList";
       $scope.labelList=getLabelList();


        $scope.deleteLabel=function(name){
            var url="/api/deleteLabel/"+$scope.repository+"/"+$scope.organization+"/"+name+"/"+loginCookie;
            $http.delete(url).success(function () {
                alert("success");
                //删除数据后，从新调用列表（相当于刷新页面）
                $scope.labelList=getLabelList();
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
            var url="/api/editLabel/"+$scope.repository+"/"+$scope.organization+"/"+$scope.labels.name+"/"+loginCookie;
            $http.post(url, label).success(function () {
                alert("success");
                $scope.labelTemplate="labelList";
                //更新数据后，从新调用列表（相当于刷新页面）
                $scope.labelList=getLabelList();
            }).error(function () {
                alert("error");
            });
        };
        $scope.submit1 = function (label) {
            console.log(label)
            var url="/api/addLabel/"+ $scope.repository+"/"+$scope.organization+"/"+loginCookie;
            $http.post(url, label).success(function () {
                alert("success");
                $scope.labelTemplate="labelList";
                //添加新数据后，从新调用列表（相当于刷新页面）
                $scope.labelList=getLabelList();
            }).error(function () {
                alert("error");
            })
        }

    });
});