var app = angular.module('chartApp',['ngResource'])

app.controller("chartCtrl", function($scope,$http, $location, $resource){
    $scope.check = function(flag){
        $http.get("/api/json",$scope.workSearch).success(function(data,status,headers){

            $scope.actual = data.message.actual_data;
            $scope.expect = data.message.expect_data;
            $scope.cate = data.message;
        }).error(function(err){
            console.log(err);
        })
        console.log(coulp);



    }
    var coulp=['一月', '二月', '三月', '四月', '五月', '六月'
        ,'七月', '八月', '九月', '十月', '十一月', '十san月','十三月'];




});