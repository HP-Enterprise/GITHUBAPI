var app = angular.module('LoginApp',['ngResource']);

app.controller("loginCtrl",function($scope,$http,$location){
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
    if (loginCookie=='') {
        $scope.loginMgr = function (userAccount) {
            $http.post('/api/loginGit', userAccount).success(function (data, status, headers, config) {
                if(data.status=="true"){
                    window.location.href="/gitHubApi/repositoryList";
                }else{
                    alert("用户名或密码错误！");
                }

            }).error(function (data, status, headers, config) {

                alert("用户名或密码错误！");
            });
        }
    }else{
        window.location.href="/gitHubApi/repositoryList";
    }

    });
