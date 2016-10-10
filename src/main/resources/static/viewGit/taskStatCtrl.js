define(['../scripts/git','jquery'],function(module,$){
    module.controller("taskStatCtrl",function($scope,$http,$routeParams){
     $scope.projectTemplate="taskList";
        $scope.repository = $routeParams.repository;
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
            if ($scope.worKeSelect.realname == '') {
                $scope.worKeSelect.realname = null;
            }
            if ($scope.worKeSelect.username == '') {
                $scope.worKeSelect.username = null;
            }
            if ($scope.worKeSelect.weekInYear == '') {
                $scope.worKeSelect.weekInYear = null;
            }
            if ($scope.worKeSelect.year == '') {
                $scope.worKeSelect.year = null;
            }

            $scope.workSearch = {
                params: {
                    project:$scope.repository,
                    username: $scope.worKeSelect.username,
                    realname: $scope.worKeSelect.realname,
                    weekInYear: $scope.worKeSelect.weekInYear,
                    currentPage: $scope.workPageObject.currentPage,
                    pageSize: $scope.workPageObject.pageSize,
                }
            };

            $http.get("/api/taskList", $scope.workSearch).success(function (data, status, headers) {
                $scope.workPageObject.totalPage = headers('Page-Count'); //总页数
                $scope.allWork = data.message;
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
        $scope.cancel=function(){
            $scope.projectTemplate="taskList";
        }
        $scope.projectDetail=function(a){
            $scope.projectTemplate="projectDetailList";
            $scope.ProjectSearch = {
                params: {
                    project:$scope.repository,
                    userName: a.username,
                    week: a.weekInYear,
                    year:a.year,
                    currentPage: 1,
                    pageSize: 15,
                }
            };


            $http.get("/api/projectWorkDetail", $scope.ProjectSearch).success(function (data, status, headers) {
                $scope.projectWorkDetail = data.message;
                $scope.Project=data.message[0];
                $scope.$watch('Project',function(newValue,oldValue, scope){
                    $scope.oneProject=newValue;
                });

            }).error(function (err) {
                console.log(err);
            })
        }
    });
});