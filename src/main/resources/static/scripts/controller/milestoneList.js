var app = angular.module('myApp', []). directive('dateFormat', ['$filter',function($filter) {
    var dateFilter = $filter('date');
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            function formatter(value) {
                return dateFilter(value, 'yyyy/MM/dd'); //format
            }
            function parser() {
                return ctrl.$modelValue;
            }
            ctrl.$formatters.push(formatter);
            ctrl.$parsers.unshift(parser);
        }
    };
}]);
app.controller('formCtrl', function ($scope, $http,$location) {
    $scope.myMiles1=false;
    $scope.myMiles2=true;
    $scope.str = $location.url().substring(1); //取得#整个地址栏
    console.log($scope.str)
         $scope.ade = {
             params: {
                 repository:  $scope.str
             }
         };
         $http.get("/api/milestoneList", $scope.ade).success(function (data) {
             $scope.milestoneList =data.message;
             console.log( $scope.milestoneList)
         }).error(function (err) {
             console.log(err);
         });
    $scope.deleteMiles=function(number){
        var url="/api/deleteMiles/"+ $scope.str+"/"+number;
        $http.delete(url).success(function (data) {
            alert("success");
        }).error(function (err) {
            console.log(err);
        });
    };
    $scope.updateMilestone=function(a){
        console.log(a)
        $scope.updateMil=a;
        $scope.myMiles1=!$scope.myMiles1;
        $scope.myMiles2=!$scope.myMiles2;
    };
    $scope.modifyMilestone = function (milestone) {
        var url="/api/editMilestone/"+$scope.str;
        $http.post(url, milestone).success(function () {
            alert("success");
        }).error(function () {
            alert("error");
        })
        $scope.myMiles1=!$scope.myMiles1;
        $scope.myMiles2=!$scope.myMiles2;
    };
});

