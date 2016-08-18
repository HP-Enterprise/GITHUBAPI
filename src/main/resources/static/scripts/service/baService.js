
define([], function(module) {

    return module.service('baService', (function(){
        function BaService($http,$resource,$timeout,$location){
            this.$http = $http;
            this.$resource = $resource;
            this.$timeout = $timeout;
            this.$location = $location;
        }
        BaService.prototype.getCookie = function(name){
        var arr = document.cookie.split("; ");
        for(var i=0,len=arr.length;i<len;i++){
            var item = arr[i].split("=");
            if(item[0]==name){
                return item[1];
            }
        }
        return "";
    };

        return ['$http', '$resource','$timeout','$location', BaService];
    })());


});


