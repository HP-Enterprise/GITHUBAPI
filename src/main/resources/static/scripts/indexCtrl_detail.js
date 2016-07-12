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

        $scope.workSearch={
            params:{
                userName:$scope.worKeSelect.userName,
                project:$scope.worKeSelect.project,
                state:$scope.worKeSelect.state,
                week:$scope.worKeSelect.week,
                month:$scope.worKeSelect.month,
                year:$scope.worKeSelect.year,
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

    $scope.getAllGitHubWork('workDetail');
    $scope.$watch('workPageObject.currentPage',function(){$scope.getAllGitHubWork();});

});