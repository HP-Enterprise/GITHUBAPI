define(['../scripts/git','jquery','../scripts/service/baService'],function(module,$){
    module.controller("issueCtrl",function($scope,$http,$routeParams,baService){
        $scope.repository = $routeParams.repository;
        $scope.issueTemplate="issueList";

        //var getCookie = function(name){
        //    var arr = document.cookie.split("; ");
        //    for(var i=0,len=arr.length;i<len;i++){
        //        var item = arr[i].split("=");
        //        if(item[0]==name){
        //            return item[1]
        //        }
        //    }
        //    return "";
        //};
        var loginCookie = baService.getCookie('token');
        //分页条件
        $scope.workPageObject = {
            currentPage: 1,
            totalPage: 0,
            pageSize: 10,
            pages: []
        };
        $scope.worKeSelect = {};//搜索栏信息
        //分页

        $scope.getAllGitHubWork = function (flag) {

            if ($scope.worKeSelect.state == '') {
                $scope.worKeSelect.state = null;
            }


            $scope.workSearch = {
                params: {
                 repository:$scope.repository,
                    state:$scope.worKeSelect.state,
                    currentPage: $scope.workPageObject.currentPage,
                    pageSize: $scope.workPageObject.pageSize,
                }
            };

            $http.get("/api/LIssueList", $scope.workSearch).success(function (data, status, headers) {
                $scope.workPageObject.totalPage = headers('Page-Count'); //总页数
                $scope.issueList = data.message;
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
            }).error(function (err) {
                console.log(err);
            })
        };

        $scope.getAllGitHubWork('work');
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




      $scope.newIssue=function(){
          $scope.issueTemplate="issueAdd";
      };
        $scope.cancel=function(){
            $scope.issueTemplate="issueList";
        }
        $scope.updateIssue=function(issue){
            $scope.modifyIssue=issue;
            $scope.issueTemplate="issueModify";
        }

        $scope.addIssue=function(issue){
            var url="api/addIssue/"+"/"+ $scope.repository+"/"+loginCookie;
            $http.post(url,issue).success(function () {
                alert("success");
                $scope.issueTemplate="issueList";
            }).error(function () {
                alert("error");
            })

        };

        $scope.modifyissue=function(issue){
            var url="api/updateIssue/"+loginCookie;
            $http.post(url,issue).success(function () {
                alert("success");
                $scope.issueTemplate="issueList";
            }).error(function () {
                alert("error");
            })

        };
        $scope.ade = {
            params: {
                repository: $scope.repository
            }
        };

        $http.get("/api/milestoneList",  $scope.ade).success(function (data) {
            $scope.milestone = data.message;
        }).error(function (err) {
            console.log(err);
        });

        $http.get("/api/orgMembers").success(function (data) {
            $scope.assignees = data.message;
        }).error(function (err) {
            console.log(err);
        });
        var url="/api/labelList/"+loginCookie;
        $http.get(url,$scope.ade).success(function (data) {
            $scope.labels1 =data.message;
        }).error(function (err) {
            console.log(err);
        });

    });
});