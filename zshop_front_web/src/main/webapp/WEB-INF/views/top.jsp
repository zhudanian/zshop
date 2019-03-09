<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/js/template.js"></script>
<script  id="welcome" type="text/html">

    <li class="userName">
        您好：{{name}}
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle user-active" data-toggle="dropdown" role="button">
            <img class="img-circle" src="${pageContext.request.contextPath}/images/user.jpeg" height="30" />
            <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
            <li>
                <a href="#" data-toggle="modal" data-target="#modifyPasswordModal">
                    <i class="glyphicon glyphicon-cog"></i>修改密码
                </a>
            </li>
            <li>
                <a href="#" onclick="loginOut()">
                    <i class="glyphicon glyphicon-off"></i> 退出
                </a>
            </li>
        </ul>
    </li>

</script>

<script id="loginOut" type="text/html">

    <li>
        <a href="#" data-toggle="modal" data-target="#loginModal">登陆</a>
    </li>
    <li>
        <a href="#" data-toggle="modal" data-target="#registModal">注册</a>
    </li>
</script>

<script>
    //登录
    function loginByAccount(){
        //alert(1);
        $.post('${pageContext.request.contextPath}/front/customer/loginByAccount',
               $('#frmLoginByAccount').serialize(),function(result){

                    //登录成功，
                    if(result.status==1){
                        //刷新整个页面
                        //location.href='${pageContext.request.contextPath}/front/product/main';

                        //局部刷新页面
                        //通过template引擎获取整段html代码
                        let content=template('welcome',result.obj);
                        //console.log(content);
                        //将登录模态框关闭
                        $('#loginModal').modal('hide');


                        $('#navInfo').html(content);


                    }
                    //登录失败
                    else{
                        //在登录模态框显示出错信息
                        $('#loginInfo').html(result.message);

                    }
                } );
    }


    //退出
    function loginOut(){
        $.post('${pageContext.request.contextPath}/front/customer/loginOut',function(result){
            //退出成功
            if (result.status==1){
                //刷新整个页面
                //location.href='${pageContext.request.contextPath}/front/product/main';

                //局部刷新页面
                let content=template('loginOut');
                $('#navInfo').html(content);


            }
        });
    }

    //发送验证码
    function sendVerificationCode(btn){
        $.post('${pageContext.request.contextPath}/sms/sendVerificationCode',
                {'phone':$('#phone').val()},function(result){
                    //console.log(result.message);
                    if(result.status==1){
                        let time=60;
                        let timer=setInterval(function(){
                            if(time>0){
                                //将按钮禁用
                                $(btn).attr('disabled',true);
                                //重新修改按钮上的文字
                                $(btn).html('重新发送('+time+')');
                                //时间--1
                                time--;

                            }
                            else{
                                //将按钮启用
                                $(btn).attr('disabled',false);
                                $(btn).html('重新发送');
                                //停用计数器
                                clearInterval(timer);

                            }
                        },1000);

                    }





                });
    }

    //短信快捷登录
    function loginBySms(){
        $.post('${pageContext.request.contextPath}/front/customer/loginBySms',
                $('#frmSms').serialize(),
                function(result){
                    if(result.status==1){

                        //将登录模态框隐藏
                        $('#loginModal').modal('hide');
                        let content=template('welcome',result.obj);
                        $('#navInfo').html(content);



                    }
                    else{
                        $('#loginInfoSms').html(result.message);
                    }

                }
        );
    }

    $(function(){
        //获取当前页的索引
        let index=${requestScope.index};
//        let a= $('ul.nav li').length;
        //alert(a);
        $('ul.nav li').each(function(i){
            //alert(i);
            $(this).removeClass("active");
            if(index==i){
                $(this).addClass("active");
            }
        });


    });

</script>
<div class="navbar navbar-default clear-bottom">
    <div class="container">

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/front/product/main">商城主页</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/front/product/toOrder">我的订单</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/front/product/toCart">购物车</a>
                </li>
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/front/product/toCenter">会员中心</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right" id="navInfo">
                <c:choose>
                    <c:when test="${empty customer}">
                        <li>
                            <a href="#" data-toggle="modal" data-target="#loginModal">登陆</a>
                        </li>
                        <li>
                            <a href="#" data-toggle="modal" data-target="#registModal">注册</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="userName">
                            您好：${customer.loginName}
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle user-active" data-toggle="dropdown" role="button">
                                <img class="img-circle" src="${pageContext.request.contextPath}/images/user.jpeg" height="30" />
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#" data-toggle="modal" data-target="#modifyPasswordModal">
                                        <i class="glyphicon glyphicon-cog"></i>修改密码
                                    </a>
                                </li>
                                <li>
                                    <a href="#" onclick="loginOut()">
                                        <i class="glyphicon glyphicon-off"></i> 退出
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>

<!-- 修改密码模态框 start -->
<div class="modal fade" id="modifyPasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>
            <form action="" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">原密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">新密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">重复密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-warning">重&nbsp;&nbsp;置</button>
                    <button type="submit" class="btn btn-warning">修&nbsp;&nbsp;改</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 修改密码模态框 end -->

<!-- 登录模态框 start  -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <!-- 用户名密码登陆 start -->
        <div class="modal-content" id="login-account">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">用户名密码登录<small class="text-danger" id="loginInfo"></small></h4>
            </div>
            <form action="" class="form-horizontal" method="post" id="frmLoginByAccount">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" placeholder="请输入用户名" name="loginName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password" placeholder="请输入密码" name="password">
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <a class="btn-link">忘记密码？</a> &nbsp;
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="button" class="btn btn-warning" onclick="loginByAccount()">登&nbsp;&nbsp;陆</button> &nbsp;&nbsp;
                    <a class="btn-link" id="btn-sms-back">短信快捷登录</a>
                </div>
            </form>
        </div>
        <!-- 用户名密码登陆 end -->
        <!-- 短信快捷登陆 start -->
        <div class="modal-content" id="login-sms" style="display: none;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">短信快捷登陆&nbsp;&nbsp;<small class="text-danger" id="loginInfoSms"></small></h4>
            </div>
            <form class="form-horizontal" method="post" id="frmSms">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">手机号：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text" placeholder="请输入手机号" id="phone" name="phone">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">验证码：</label>
                        <div class="col-sm-4">
                            <input class="form-control" type="text" placeholder="请输入验证码" name="verificationCode">
                        </div>
                        <div class="col-sm-2">
                            <button class="pass-item-timer" type="button" onclick="sendVerificationCode(this)">发送验证码</button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <a class="btn-link">忘记密码？</a> &nbsp;
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="button" class="btn btn-warning" onclick="loginBySms()">登&nbsp;&nbsp;陆</button> &nbsp;&nbsp;
                    <a class="btn-link" id="btn-account-back">用户名密码登录</a>
                </div>
            </form>
        </div>
        <!-- 短信快捷登陆 end -->
    </div>
</div>
<!-- 登录模态框 end  -->

<!-- 注册模态框 start -->
<div class="modal fade" id="registModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">会员注册</h4>
            </div>
            <form action="" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户姓名:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">登陆账号:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">登录密码:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系电话:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系地址:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-warning">重&nbsp;&nbsp;置</button>
                    <button type="submit" class="btn btn-warning">注&nbsp;&nbsp;册</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 注册模态框 end -->