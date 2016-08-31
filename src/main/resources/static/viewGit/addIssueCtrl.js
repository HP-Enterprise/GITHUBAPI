define(['../scripts/git','jquery'],function(module,$){
    module.controller("addIssueCtrl",function($scope,$http){
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
        $scope.submit = function (addIssue) {
           var url="api/addIssue/"+loginCookie;
            $http.post(url,addIssue).success(function () {
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