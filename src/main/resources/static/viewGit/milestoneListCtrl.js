define(['../scripts/git','jquery'],function(module,$){
    module.controller("milestoneListCtrl",function($scope,$http,$routeParams,$filter){
        $scope.milestoneTemplate="milestoneList";
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
        console.log($scope.repository)
        $scope.ade = {
            params: {
                repository:  $scope.repository
            }
        };
        $http.get("/api/milestoneList", $scope.ade).success(function (data) {
            $scope.milestoneList =data.message;
            console.log( $scope.milestoneList)
        }).error(function (err) {
            console.log(err);
        });


        $scope.addMilestone=function(){
            $scope.milestoneTemplate="milestoneAdd";
        };
        $scope.cancel=function(){
            $scope.milestoneTemplate="milestoneList";
        }
        $scope.deleteMiles=function(number){
            var url="/api/deleteMiles/"+ $scope.repository+"/"+number+"/"+loginCookie;
            $http.delete(url).success(function (data) {
                alert("success");
            }).error(function (err) {
                console.log(err);
            });
        };

        $scope.updateMilestone=function(a){
            $scope.ab=$filter('date')(a.dueOn,"yyyy-MM-dd");
            $scope.updateMil=a;
            //{"title": a.title,"state": a.state,"description": a.description,"dueOn":$scope.ab}
            $scope.milestoneTemplate="milestoneModify";
        };
        $scope.modifyMilestone = function (milestone) {
            var url="/api/editMilestone/"+$scope.repository+"/"+loginCookie;
            $http.post(url, milestone).success(function () {
                alert("success");
                $scope.milestoneTemplate="milestoneList";
            }).error(function () {
                alert("error");
            })
        };

        $scope.submit1 = function (milestone) {
            var url="/api/addMilestone/"+$scope.repository+"/"+loginCookie;
            $http.post(url, milestone).success(function () {
                alert("success");
                $scope.milestoneTemplate="milestoneList";
            }).error(function () {
                alert("error");
            })
        };
        $scope.addUsualMilestone=function(){
            var url="/api/addAllMilestones/"+loginCookie;
            $http.post(url,$scope.repository ).success(function () {
                alert("success");
                $scope.milestoneTemplate="milestoneList";
            }).error(function () {
                alert("error");
            })
        }

    });
});