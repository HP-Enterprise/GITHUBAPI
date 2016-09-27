define(['../scripts/git','jquery'],function(module,$){
    module.controller("weekDetailCtrl",function($scope,$http,$routeParams,$location){

        //分页
        $scope.workPageObject = {
            currentPage : 1,
            totalPage : 0,
            pageSize : 10,
            pages : []
        };

        $scope.worKeSelect = {};//搜索栏信息
        $scope.getAllGitHubWork = function(flag){
            var str1=$routeParams.username;
            var str2=$routeParams.weekInYear;
            if($scope.worKeSelect.realname==''){
                $scope.worKeSelect.realname = null;
            }
            if($scope.worKeSelect.username==''){
                $scope.worKeSelect.username = null;
            }
            if($scope.worKeSelect.weekNum==''){
                $scope.worKeSelect.weekNum = null;
            }

            $scope.workSearch={
                params:{
                    realname:$scope.worKeSelect.realname,
                    userName:str1,
                    week:str2,
                    currentPage:$scope.workPageObject.currentPage,
                    pageSize:$scope.workPageObject.pageSize,
                    fuzzy:1
                }
            };
         $scope.returnWeek=function(){
             $location.path("/gitHubApi/week");
         };

            $http.get("/api/personalWorkDetail",$scope.workSearch).success(function(data,status,headers){
                $scope.workPageObject.totalPage = headers('Page-Count'); //总页数
                $scope.allWork = data.message;
                $scope.oneWork = data.message[0];
                $scope.workPageObject.pages = [];
                for (var i = 1; i <= $scope.workPageObject.totalPage; i++) {
                    $scope.workPageObject.pages.push(i);
                }
                if ($scope.workPageObject.totalPage > 5) {
                    document.getElementById("selectPage_1").style.height = "110px"; //查过5条数据会出现滑动条
                } else {
                    document.getElementById("selectPage_1").style.height = 22 * ($scope.workPageObject.totalPage) + "px";
                    document.getElementById("selectPage_1").style.overflowY = "hidden";
                }
            }).error(function(err){
                console.log(err);
            })
        };

        $scope.getAllGitHubWork('personalWorkDetail');
        this.init = function () {
            $scope.getAllGitHubWork();
            $scope.$watch('workPageObject.totalPage', function () {
                $scope.workPageObject.currentPage = 1;
            });
        };

        this.init();

        $scope.$watch('workPageObject.currentPage', function () {
            $scope.getAllGitHubWork();
        });

        //上下翻页
        $scope.changePage = function (operation) {
            if (operation == 'next') {
                $scope.workPageObject.currentPage = ($scope.workPageObject.currentPage + 1) > $scope.workPageObject.totalPage
                    ? $scope.workPageObject.currentPage : ($scope.workPageObject.currentPage + 1);
            } else if (operation == 'prev') {
                $scope.workPageObject.currentPage = ($scope.workPageObject.currentPage - 1) < 1
                    ? $scope.workPageObject.currentPage : ($scope.workPageObject.currentPage - 1);
            }
        };

        $scope.pageSelector = {
            show: false,
            focus: false
        };

        $(document).click(function () {
            if (!$scope.pageSelector.focus) {
                $scope.$apply(function () {
                    $scope.pageSelector.show = false;
                });
            }
        });

        //选择页
        $scope.selectPage = function (page) {
            $scope.workPageObject.currentPage = page;
        };

    });
});