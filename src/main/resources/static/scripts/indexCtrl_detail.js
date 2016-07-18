var app = angular.module('indexApp_detail',['ngResource']);

app.controller("indexCtrlDetail", function($scope,$http, $location, $resource){
    //分页
    $scope.workPageObject = {
        currentPage : 1,
        totalPage : 0,
        pageSize : 10,
        pages : []
    };
    $scope.getPageData = function(pageObject,page){
        if(pageObject.totalPage==0||pageObject.totalPage==null){
                pageObject.totalPage=1;
        }
        if (pageObject.currentPage > 1 && pageObject.currentPage < pageObject.totalPage) {
            $scope.workPageObject.pages = [
                pageObject.currentPage - 1,
                pageObject.currentPage,
                pageObject.currentPage + 1
            ];
        } else if (pageObject.currentPage == 1 && pageObject.totalPage == 1) {
            $scope.workPageObject.pages = [
                1
            ];
        } else if (pageObject.currentPage == 1 && pageObject.totalPage == 2) {
            $scope.pageObject.pages = [
                1,2
            ];
        } else if (pageObject.currentPage == 1 && pageObject.totalPage > 2) {
            $scope.workPageObject.pages = [
                pageObject.currentPage,
                pageObject.currentPage + 1,
                pageObject.currentPage + 2
            ];
        } else if (pageObject.currentPage == pageObject.totalPage && pageObject.totalPage == 1) {
            $scope.workPageObject.pages = [
                1
            ];
        } else if (pageObject.currentPage == pageObject.totalPage && pageObject.totalPage == 2) {
            $scope.workPageObject.pages = [
                1,2
            ];
        } else if (pageObject.currentPage == pageObject.totalPage && pageObject.totalPage > 2) {
            $scope.workPageObject.pages = [
                pageObject.currentPage - 2,
                pageObject.currentPage - 1,
                pageObject.currentPage
            ];
        }
    };

    //上一页
    $scope.upPageClick = function(pageObject,page){
        console.log(JSON.stringify(pageObject));
        if(pageObject.currentPage == 1){
            return;
        };
        $scope.workPageObject.currentPage --;
        $scope.getPageData(pageObject,page);
    };

    //下一页
    $scope.downPageClick = function(pageObject,page){
        if(pageObject.currentPage >= pageObject.totalPage){
            return;
        };
        $scope.workPageObject.currentPage ++;
        $scope.getPageData(pageObject,page);
    };

    //当前页
    $scope.showCurrentPageContent = function(pageObject,page){
        $scope.workPageObject.currentPage = page;
        $scope.getPageData(pageObject,page);
    };

    $scope.showFirstPageContent = function(pageObject,page){
        $scope.workPageObject.currentPage = 1;
        $scope.getPageData(pageObject,page);
    };

    $scope.worKeSelect = {};//搜索栏信息
    //分页

    $scope.getAllGitHubWork = function(flag){
        console.log(flag);
        if($scope.worKeSelect.userName==''){
            $scope.worKeSelect.userName = null;
        }
        if($scope.worKeSelect.project==''){
            $scope.worKeSelect.project = null;
        }
        if($scope.worKeSelect.state==''){
            $scope.worKeSelect.state = null;
        }
        if($scope.worKeSelect.week==''){
            $scope.worKeSelect.week = null;
        }
        if($scope.worKeSelect.month==''){
            $scope.worKeSelect.month = null;
        }
        if($scope.worKeSelect.year==''){
            $scope.worKeSelect.year = null;
        }
        if($scope.worKeSelect.quarter==''){
            $scope.worKeSelect.quarter = null;
        }

        $scope.workSearch={
            params:{
                userName:$scope.worKeSelect.userName,
                project:$scope.worKeSelect.project,
                state:$scope.worKeSelect.state,
                week:$scope.worKeSelect.week,
                month:$scope.worKeSelect.month,
                year:$scope.worKeSelect.year,
                quarter:$scope.worKeSelect.quarter,
                currentPage:$scope.workPageObject.currentPage,
                pageSize:$scope.workPageObject.pageSize,
                fuzzy:1
            }
        };
        $http.get("/api/workDetail",$scope.workSearch).success(function(data,status,headers){

            $scope.workPageObject.totalPage = headers('Page-Count'); //总页数
            $scope.allWork = data.message;
            if(flag == 'workDetail'){
                $scope.showFirstPageContent($scope.workPageObject,1);
            }
        }).error(function(err){
            console.log(err);
        })
    };

    $scope.DownloadExcel = function(flag) {
        //$scope.REALNAME==$scope.worKeSelect.realname=='' ?  $scope.REALNAME="realname="+$scope.worKeSelect.realname : $scope.REALNAME='';
        $scope.USERNAME == $scope.worKeSelect.userName == '' ? $scope.USERNAME = "userName=" + $scope.worKeSelect.userName : $scope.USERNAME = null;
        $scope.PROJECT == $scope.worKeSelect.project == '' ? $scope.PROJECT = "project=" + $scope.worKeSelect.project : $scope.PROJECT = null;
        $scope.STATE == $scope.worKeSelect.state == '' ? $scope.STATE = "state=" + $scope.worKeSelect.state : $scope.STATE = null;
        $scope.YEAR == $scope.worKeSelect.year == '' ? $scope.YEAR = "year=" + $scope.worKeSelect.year : $scope.YEAR = null;
        $scope.QUARTER == $scope.worKeSelect.quarter == '' ? $scope.QUARTER = "quarter=" + $scope.worKeSelect.quarter : $scope.QUARTER = null;
        $scope.MONTH == $scope.worKeSelect.month == '' ? $scope.MONTH = "month=" + $scope.worKeSelect.month : $scope.MONTH = null;
        $scope.WEEK == $scope.worKeSelect.week == '' ? $scope.WEEK = "week=" + $scope.worKeSelect.week : $scope.WEEK = null;

        if($scope.worKeSelect.userName=='' || $scope.worKeSelect.userName==undefined ){
            $scope.worKeSelect.userName= null;
            $scope.USERNAME = null;
        }
        else{
            $scope.USERNAME="userName="+$scope.worKeSelect.userName;
        }

        if($scope.worKeSelect.project=='' || $scope.worKeSelect.project==undefined ){
            $scope.worKeSelect.project= null;
            $scope.PROJECT = null;
        }
        else{
            $scope.PROJECT="project="+$scope.worKeSelect.project;
        }

        if($scope.worKeSelect.state=='' || $scope.worKeSelect.state==undefined ){
            $scope.worKeSelect.state= null;
            $scope.STATE = null;
        }
        else{
            $scope.STATE="state="+$scope.worKeSelect.state;
        }

        if($scope.worKeSelect.year=='' || $scope.worKeSelect.year==undefined ){
            $scope.worKeSelect.year= null;
            $scope.YEAR = null;
        }
        else{
            $scope.YEAR="year="+$scope.worKeSelect.year;
        }

        if($scope.worKeSelect.quarter=='' || $scope.worKeSelect.quarter==undefined ){
            $scope.worKeSelect.quarter= null;
            $scope.WEEKNUM = null;
        }
        else{
            $scope.QUARTER="quarter="+$scope.worKeSelect.quarter;
        }

        if($scope.worKeSelect.month=='' || $scope.worKeSelect.month==undefined ){
            $scope.worKeSelect.month= null;
            $scope.MONTH = null;
        }
        else{
            $scope.MONTH="month="+$scope.worKeSelect.month;
        }

        if($scope.worKeSelect.week=='' || $scope.worKeSelect.week==undefined ){
            $scope.worKeSelect.week= null;
            $scope.WEEK = null;
        }
        else{
            $scope.WEEK="week="+$scope.worKeSelect.week;
        }

        $scope.workSearch = {
            params: {
                userName: $scope.worKeSelect.userName,
                project: $scope.worKeSelect.project,
                state: $scope.worKeSelect.state,
                week: $scope.worKeSelect.week,
                month: $scope.worKeSelect.month,
                year: $scope.worKeSelect.year,
                quarter: $scope.worKeSelect.quarter,
                currentPage: $scope.workPageObject.currentPage,
                pageSize: $scope.workPageObject.pageSize,
                fuzzy: 1
            }
        };
            $scope.url = "http://localhost:8080/api/workDetail/exportExcel?" +
                $scope.USERNAME + "&" +  $scope.PROJECT+ "&" + $scope.STATE+ "&" +$scope.YEAR+ "&" +$scope.QUARTER+ "&" +$scope.MONTH + "&" +$scope.WEEK;
        window.open($scope.url);
        //
        //console.log($scope.url);
    }


    $scope.getAllGitHubWork('workDetail');
    $scope.$watch('workPageObject.currentPage',function(){$scope.getAllGitHubWork();});

});