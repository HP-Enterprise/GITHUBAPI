
define(['../git','jquery','angular'], function(module) {

    return module.service('baService', (function(){
        function BaService($http,$resource,$timeout,$location){
            this.$http = $http;
            this.$resource = $resource;
            this.$timeout = $timeout;
            this.$location = $location;
        }
        //��ȡcookie
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
        //ɾ��cookie
        BaService.prototype.deleteCookie = function(name){
            var exp = new Date();
            exp.setTime(exp.getTime() - 1000);
            var cval=this.getCookie(name);
            if(cval!=null){
                document.cookie= name+"="+cval+";expires="+exp.toUTCString()+"; path=/";
            }
        };

        //����ת����
        BaService.prototype.dateFormat = function(time, format){
            var t = new Date(time);
            var tf = function(i){return (i < 10 ? '0' : '') + i};
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
                switch(a){
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        };

        //��ȡ��¼�û���Ϣ
        BaService.prototype.getLoginInfo = function(cb){
            var loginReInfoApi = this.$resource('/api/login/:token');  //���ص�¼�û�����Ϣ
            var token = this.getCookie("token");
            loginReInfoApi.get({token:token},function(data){
                cb(data);
            },function(errData){
                console.log(errData.data.error);
            })
        };

        return ['$http', '$resource','$timeout','$location', BaService];
    })());


});


