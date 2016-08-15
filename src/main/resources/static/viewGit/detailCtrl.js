define(['../scripts/git','jquery'],function(module,$){
    module.controller("detailCtrl",function($scope,$http){

        //分页
        $scope.workPageObject = {
            currentPage : 1,
            totalPage : 0,
            pageSize : 10,
            pages : []
        };

        $scope.worKeSelect = {};//搜索栏信息
        //分页

        $scope.getAllGitHubWork = function(flag){
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


        $scope.getAllGitHubWork('workDetail');
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
        }


    });
});