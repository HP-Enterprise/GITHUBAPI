
    var routeApp = angular.module('routeApp', ['ngRoute','ngResource']).config(['$routeProvider', function($routeProvider){
        $routeProvider
            .when('/',{template:'---------------------------a'})
            .when('/week',{templateUrl:'index-week.html',controller:'indexCtrl-week'})
            .when('/printers',{template:'这是打印机页面'})
            .otherwise({redirectTo:'/'});
    }]);
    routeApp.controller("indexCtrl-week", function($scope,$http, $location, $resource){
        //分页条件
        $scope.workPageObject= {
            currentPage : 1,
            totalPage : 0,
            pageSize : 10,
            pages : []
        };
        $scope.worKeSelect = {};//搜索栏信息
        //分页

        $scope.getAllGitHubWork = function(flag){
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
                    username:$scope.worKeSelect.username,
                    weekNum:$scope.worKeSelect.weekNum,
                    currentPage:$scope.workPageObject.currentPage,
                    pageSize:$scope.workPageObject.pageSize,
                    fuzzy:1
                }
            };

            $http.get("/api/work",$scope.workSearch).success(function(data,status,headers){
                $scope.workPageObject.totalPage = headers('Page-Count'); //总页数
                $scope.allWork = data.message;
                $scope.workPageObject.pages = [];
                for(var i=1;i<=$scope.workPageObject.totalPage;i++){
                    $scope.workPageObject.pages.push(i);
                }
                if ($scope.workPageObject.totalPage>5){
                    document.getElementById("selectPage_1").style.height="110px"; //查过5条数据会出现滑动条
                }else{
                    document.getElementById("selectPage_1").style.height=22*($scope.workPageObject.totalPage)+"px";
                    document.getElementById("selectPage_1").style.overflowY ="hidden";
                }
            }).error(function(err){
                console.log(err);
            })
        };

        $scope.getAllGitHubWork('work');
        this.init = function(){
            $scope.getAllGitHubWork();
            $scope.$watch('workPageObject.totalPage',function(){
                $scope.workPageObject.currentPage = 1;
            });
        };

        this.init();

        $scope.$watch('workPageObject.currentPage',function(){
            $scope.getAllGitHubWork();
        });

        //上下翻页
        $scope.changePage = function(operation){
            if(operation == 'next'){
                $scope.workPageObject.currentPage = ($scope.workPageObject.currentPage+1) > $scope.workPageObject.totalPage
                    ? $scope.workPageObject.currentPage : ($scope.workPageObject.currentPage+1);
            } else if(operation == 'prev'){
                $scope.workPageObject.currentPage = ($scope.workPageObject.currentPage-1) < 1
                    ? $scope.workPageObject.currentPage : ($scope.workPageObject.currentPage-1);
            }
        };

        $scope.pageSelector = {
            show : false,
            focus: false
        };

        $(document).click(function(){
            if(!$scope.pageSelector.focus){
                $scope.$apply(function(){
                    $scope.pageSelector.show = false;
                });
            }
        });

        //选择页
        $scope.selectPage = function(page){
            $scope.workPageObject.currentPage = page;
        };


        $scope.goHtml = function (obj){
            //console.log(obj);
            window.location.href ="index_personal.html?userName="+obj.username+"&week="+obj.weekInYear;
        };
        $scope.DownloadExcel = function(flag){

            if($scope.worKeSelect.weekNum=='' || $scope.worKeSelect.weekNum==undefined ){
                $scope.worKeSelect.weekNum= null;
                $scope.WEEKNUM = null;
            }
            else{
                $scope.WEEKNUM="weekNum="+$scope.worKeSelect.weekNum;
            }

            if($scope.worKeSelect.username=='' || $scope.worKeSelect.username== undefined ){
                $scope.worKeSelect.username= null;
                $scope.USERNAME = null;
            }
            else{
                $scope.USERNAME="username="+$scope.worKeSelect.username;
            }

            if($scope.worKeSelect.realname=='' || $scope.worKeSelect.realname== undefined ){
                $scope.worKeSelect.realname= null;
                $scope.REALNAME = null;
            }
            else{
                $scope.REALNAME="realname="+$scope.worKeSelect.realname;
            }

            //$scope.url="http://localhost:7890/api/exportExcel?"+ $scope.USERNAME +"&"+ $scope.WEEKNUM;
            $scope.url="http://localhost:8080/api/exportExcel?"+ $scope.REALNAME+"&"+ $scope.USERNAME +"&"+ $scope.WEEKNUM;
            window.open($scope.url);
            //console.log($scope.url);
        }

    });


    routeApp.controller('formCtrl', function ($scope, $http) {
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
            window.location.href = "labelList.html#" + a;
        }
        $scope.modifyDiv1 = function (a) {
            $scope.myChart = !$scope.myChart;
            window.location.href = "highchart.html#" + a;
        }

        $scope.modifyDiv2 = function (a) {
            $scope.myMilestone = !$scope.myMilestone;
            window.location.href = "milestoneList.html#" + a;
        }


    });

