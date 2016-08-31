define(['../scripts/git','jquery'],function(module,$){
    module.controller("repositoryListCtrl",function($scope,$http){
        $scope.myVar1 = false;
        $scope.myVar2 = true;
        $scope.workPageObject = {
            currentPage: 1,
            totalPage: 0,
            pageSize: 10,
            pages: []
        };
        $scope.worKeSelect = {};//搜索栏信息
        //分页

        $scope.getAllGitHubWork = function (flag) {

            if ($scope.worKeSelect.name == '') {
                $scope.worKeSelect.name = null;
            }


            $scope.workSearch = {
                params: {
                    name:$scope.worKeSelect.name,
                    currentPage: $scope.workPageObject.currentPage,
                    pageSize: $scope.workPageObject.pageSize,
                }
            };

            $http.get("/api/projectList", $scope.workSearch).success(function (data, status, headers) {
                $scope.workPageObject.totalPage = headers('Page-Count'); //总页数
                $scope.repository = data.message;
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

        $scope.deleteRepository = function (aw) {
            console.log(aw)

            $scope.ade = {
                params: {
                    repository: aw
                }
            };
            var url1="/api/deleteOrgRepository/" +loginCookie;
            $http.delete(url1,$scope.ade).success(function () {
                alert("success");
            }).error(function (err) {
                console.log(err);
            })

        };


        $scope.returnRepository=function(){
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;
        };
        $scope.updateRepository = function (aa) {
            $scope.updateRep = aa;
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;

        };
        $scope.modifyRepository = function (a) {

            var url="/api/updateOrgRepository/"+$scope.updateRep.name+"/"+loginCookie;

            $http.post(url, a).success(function () {
                alert("success");
            }).error(function () {
                alert("error");
            })
            $scope.myVar1 = !$scope.myVar1;
            $scope.myVar2 = !$scope.myVar2;
        };
        //var url="/api/projectList"
        //$http.get(url).success(function (data) {
        //    $scope.repository = data.message;
        //    console.log($scope.repository)
        //
        //}).error(function (err) {
        //    console.log(err);
        //})
    });
});