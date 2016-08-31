define(['../scripts/git','jquery'],function(module,$){
    module.controller("issueCtrl",function($scope,$http,$routeParams){
        $scope.repository = $routeParams.repository;
        $scope.issueTemplate="issueList";
        //��ҳ����
        $scope.workPageObject = {
            currentPage: 1,
            totalPage: 0,
            pageSize: 10,
            pages: []
        };
        $scope.worKeSelect = {};//��������Ϣ
        //��ҳ

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
                $scope.workPageObject.totalPage = headers('Page-Count'); //��ҳ��
                $scope.issueList = data.message;
                console.log( $scope.issueList)
                $scope.workPageObject.pages = [];
                for (var i = 1; i <= $scope.workPageObject.totalPage; i++) {
                    $scope.workPageObject.pages.push(i);
                }
                if ($scope.workPageObject.totalPage > 5) {
                    document.getElementById("selectPage_1").style.height = "110px"; //���5�����ݻ���ֻ�����
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

        //���·�ҳ
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

        //ѡ��ҳ
        $scope.selectPage = function (page) {
            $scope.workPageObject.currentPage = page;
        };

      $scope.addIssue=function(){
          $scope.issueTemplate="issueAdd";
      };
        $scope.cancel=function(){
            $scope.issueTemplate="issueList";
        }
        $scope.updateIssue=function(issue){
            console.log(issue)
            $scope.modifyIssue=issue;
            $scope.issueTemplate="issueModify";

        }


    });
});