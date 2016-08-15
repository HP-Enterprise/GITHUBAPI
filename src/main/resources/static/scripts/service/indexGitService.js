/**
 * User
 * 用户
 */

define([], function() {

    function indexGitService($resource) {
        this.userApi = $resource('/api/user'); //通过条件查询用户
        this.userByIdApi = $resource('/api/user/:uid/uid'); //查询单个用户
        this.updateUserApi = $resource('/api/user/:uid',null,{'update': { method:'PUT'}}); //修改用户信息
     //   this.sendEmailApi = $resource('/api/user/:uid/mail'); //发送邮箱验证
        this.validateEmailApi = $resource('/api/user/:uid/mail/:code'); //验证邮箱
        this.findDrivingLicenceApi = $resource('/api/user/:uid/drivinglicence'); //查询驾照信息
        this.updateDrivingLicenceApi = $resource('/api/user/:uid/drivinglicence',null,{'update': { method:'PUT'}}); //修改驾照信息
        this.createAccountApi = $resource('/api/account'); //后台管理员开通租车用户账号(生成随机密码发送到注册手机中)


        //-----------客户级用户信息操作-----------------------------------------------------------------------------------------
        this.meApi = $resource('/api/me'); //用户查询自己的信息
        this.updateMeApi = $resource('/api/me',null,{'update': { method:'PUT'}}); //修改自己的信息
        this.updatePhoneApi = $resource('/api/me/:phone',null,{'update': { method:'PUT'}}); //修改手机号
        this.modifyPasswordApi = $resource('/api/me/password',null,{'update':{ method:'PUT'}}); //修改密码
    }

    //通过条件查询用户
    indexGitService.prototype.findUserShows = function(user,cb){
        this.userApi.get(user,function(data,headers){
            cb(data,headers);
        },function(errData){
            cb(errData.data.error);
        })
    };

    //查询用户
    indexGitService.prototype.findUserById = function(uid,cb){
        this.userByIdApi.get({uid:uid},function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        })
    };

    //修改用户信息
    indexGitService.prototype.updateUserById = function(userShow,cb){
        this.updateUserApi.update({uid:userShow.id},userShow,function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };

    //发送邮箱验证
  /*  indexGitService.prototype.sendEmail = function(uid,cb){
        this.sendEmailApi.get({uid:uid},function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };*/

    //验证邮箱
    indexGitService.prototype.validateEmail = function(uid,code,cb){
        this.validateEmailApi.get({uid:uid},{code:code},function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };

    //查询驾照信息
    indexGitService.prototype.findDrivingLicence = function(uid,cb){
        this.findDrivingLicenceApi.get({uid:uid},function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };

    //修改驾照信息
    indexGitService.prototype.updateDrivingLicence = function(uid,drivingLicenceShow,cb){
        this.updateDrivingLicenceApi.update({uid:uid},drivingLicenceShow,function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };

    //用户查询自己的信息
    indexGitService.prototype.findMeShows = function(cb){
        this.meApi.get(function(data,headers){
            cb(data,headers);
        },function(errData){
            cb(errData.data.error);
        })
    };

    //修改自己的信息
    indexGitService.prototype.updateMe = function(userShow,cb){
        this.updateMeApi.update(userShow,function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };


    //修改手机号
    indexGitService.prototype.updatePhone = function(phone,userValidate,cb){
        this.updatePhoneApi.update({phone:phone},userValidate,function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };

    //修改密码
    indexGitService.prototype.modifyPassword = function(userValidate,cb){
        this.modifyPasswordApi.update(userValidate,function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        });
    };


    //后台管理员开通租车用户账号(生成随机密码发送到注册手机中)
    //用户注册
    indexGitService.prototype.createAccount = function(postData,cb){
        this.createAccountApi.save(postData,function(data){
            cb(data);
        },function(errData){
            cb(errData.data.error);
        })
    };

    return indexGitService;


});


