define(['../scripts/git','jquery'],function(module,$){
    module.controller("indexGitCtrl",function($scope,$http,$resource,$location,$route) {

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
        var deleteCookie = function(name){
            var exp = new Date();
            exp.setTime(exp.getTime() - 1000);
            var cval=getCookie(name);
            if(cval!=null){
                document.cookie= name+"="+cval+";expires="+exp.toUTCString()+"; path=/";
            }
        };
        var loginCookie = getCookie('token');
        $scope.deletecookie=function(){
            var url="/api/loginOut/"+ loginCookie;
            $http.delete(url).success(function () {
                deleteCookie('token');
                $scope.myLogin =false ;
                $location.path("/gitHubApi/login");
            }).error(function (err) {
                console.log(err);
            });
        };


        $scope.myLogin =true ;
        console.log(loginCookie);
        if (loginCookie=='') {
            $scope.myLogin =false ;
            $location.path("/gitHubApi/login");
    }

       var as="/api/getLoginInfo/"+loginCookie;
        $http.get(as).success(function (data) {
            $scope.user = data.message;
        }).error(function (err) {
            console.log(err);
        });

        $scope.myLabel = false;
        $scope.myMilestone = false;
        $scope.myChart = false;
        $scope.bt = function (b) {
            switch (b) {
                case 1:
                    $scope.myLabel = !$scope.myLabel;
                    break;
                case 2:
                    $scope.myMilestone = !$scope.myMilestone;
                    break;
                case 3:
                    $scope.myChart = !$scope.myChart;
                    break;
            }
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

        $scope.modifyDiv = function (a) {
            $scope.myLabel = !$scope.myLabel;
            $scope.url="/gitHubApi/labelList/"+a;
            $location.path( $scope.url);
        }
        $scope.modifyDiv1 = function (a) {
            $scope.myChart = !$scope.myChart;

            $scope.url="/gitHubApi/highChart/"+a;
            $location.path( $scope.url);
        }

        $scope.modifyDiv2 = function (a) {
            $scope.myMilestone = !$scope.myMilestone;
            $scope.url="/gitHubApi/milestoneList/"+a;
            $location.path( $scope.url);
        }
    });

});