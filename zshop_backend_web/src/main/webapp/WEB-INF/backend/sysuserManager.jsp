<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>backend</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/userSetting.js"></script>
    <script SRC="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script SRC="${pageContext.request.contextPath}/layer/layer.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zshop.css">
    <script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css"/>

    <script>
        $(function () {
            //在页面加载完成后初始化分页条
            $('#pagination').bootstrapPaginator({

                //主版本号
                bootstrapMajorVersion: 3,
                //当前页
                currentPage:${data.pageNum},
                //总页数
                totalPages:${data.pages},//el表达式取的是对应属性的get方法
                //分页时用到的url请求
                //page:当前页
                onPageClicked: function (event, originalEvent, type, page) {
                    //alert(page);
                    //给隐藏表单域的pageNum重新赋值为当前点击的页号page
                    $('#pageNum').val(page);
                    //重新提交表单
                    $('#frmQuery').submit();


                },
                itemTexts: function (type, page, current) {//根据type的值，显示对应的分页栏
                    switch (type) {
                        case "first":
                            return '首页';

                        case "prev":
                            return '上一页';
                        case "next":
                            return '下一页';
                        case "last":
                            return '尾页';
                        case "page":
                            return page;
                    }


                }

            });

            //添加用户表单的客户端校验
            $('#frmAddSysuser').bootstrapValidator({
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'

                },
                fields: {
                    name: {
                        validators: {
                            notEmpty: {
                                message: '用户姓名不能为空'
                            }
                        }
                    },
                    loginName: {
                        validators: {
                            notEmpty: {
                                message: '登录账号不能为空'
                            },
                            remote: {
                                //ajax后端校验该登录账号是否已经存在
                                url: '${pageContext.request.contextPath}/backend/sysuser/checkName'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            }
                        }
                    },
                    phone: {
                        validators: {
                            notEmpty: {
                                message: '电话号码不能为空'
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: '邮箱不能为空'
                            },
                            emailAddress: {
                                message: '邮箱格式不正确'
                            }
                        }
                    },
                    roleId: {
                        validators: {
                            notEmpty: {
                                message: '请选择角色'
                            }
                        }
                    }


                }

            });
        });

        function addSysuser() {
            //进行表单校验
            let bv = $('#frmAddSysuser').data('bootstrapValidator');
            bv.validate();
            if (bv.isValid()) {
                //alert(1);
                //调用ajax到后台执行添加用户
                $.post('${pageContext.request.contextPath}/backend/sysuser/add',
                        //将表单中的元素以key=value的形式序列化，key就是name属性的值，value就是value属性的值
                        $('#frmAddSysuser').serialize(), function (result) {

                            if (result.status == 1) {
                                layer.msg(result.message, {
                                    time: 2000,
                                    skin: 'successMsg'
                                }, function () {
                                    location.href = '${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum='
                                            +${data.pageNum};
                                });

                            }
                            else if (result.status == 0) {
                                layer.msg(result.message, {
                                    time: 2000,
                                    skin: 'errorMsg'
                                });
                            }

                        });
            }

        }

        function modifyStatus(id, btn) {
            //alert(id);
            $.post('${pageContext.request.contextPath}/backend/sysuser/modifyStatus',
                    {'id': id}, function (result) {
                        if (result.status == 1) {

                            let $td = $(btn).parent().parent().children().eq(5);
                            if($td.text().trim()=='无效'){
                                $td.text('有效');
                                $(btn).val('启用').removeClass('btn-danger').addClass('btn-success');
                            }
                            else{
                                $td.text('无效');
                                $(btn).val('禁用').removeClass('btn-success').addClass('btn-danger');
                            }


                        }


                    });
        }

    </script>
</head>

<body>
<!-- 系统用户管理 -->
<div class="panel panel-default" id="adminSet">
    <div class="panel-heading">
        <h3 class="panel-title">系统用户管理</h3>
    </div>
    <div class="panel-body">
        <div class="showmargersearch">
            <form class="form-inline" action="${pageContext.request.contextPath}/backend/sysuser/findByParams"
                  method="post" id="frmQuery">
                <input type="hidden" id="pageNum" name="pageNum" value="${data.pageNum}"/>

                <div class="form-group">
                    <label for="userName">姓名:</label>
                    <input type="text" class="form-control" id="userName" placeholder="请输入姓名" name="name"
                           value="${sysuserParam.name}">
                </div>
                <div class="form-group">
                    <label for="loginName">帐号:</label>
                    <input type="text" class="form-control" id="loginName" placeholder="请输入帐号" name="loginName"
                           value="${sysuserParam.loginName}">
                </div>
                <div class="form-group">
                    <label for="phone">电话:</label>
                    <input type="text" class="form-control" id="phone" placeholder="请输入电话" name="phone"
                           value="${sysuserParam.phone}">
                </div>
                <div class="form-group">
                    <label for="role">角色</label>
                    <select class="form-control" name="roleId" id="role">
                        <option value="-1">全部</option>
                        <c:forEach items="${roles}" var="role">
                            <option value="${role.id}"
                                    <c:if test="${role.id==sysuserParam.roleId}">selected</c:if>>${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status">状态</label>
                    <select class="form-control" name="isValid" id="status">
                        <option value="-1">全部</option>
                        <option value="1" <c:if test="${sysuserParam.isValid==1}">selected</c:if>>---有效---</option>
                        <option value="0" <c:if test="${sysuserParam.isValid==0}">selected</c:if>>---无效---</option>
                    </select>
                </div>
                <input type="submit" value="查询" class="btn btn-primary" id="doSearch">
            </form>
        </div>
        <br>
        <input type="button" value="添加系统用户" class="btn btn-primary" id="doAddManger">

        <div class="show-list text-center" style="position: relative; top: 10px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">帐号</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">邮箱</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">注册时间</th>
                    <th class="text-center">角色</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${data.list}" var="sysuser">

                    <tr>
                        <td>${sysuser.id}</td>
                        <td>${sysuser.name}</td>
                        <td>${sysuser.loginName}</td>
                        <td>${sysuser.phone}</td>
                        <td>${sysuser.email}</td>
                        <td>
                            <c:if test="${sysuser.isValid==1}">有效</c:if>
                            <c:if test="${sysuser.isValid==0}">无效</c:if>

                        </td>
                        <td><fmt:formatDate value="${sysuser.createDate}" pattern="yyyy年MM月dd日"/></td>
                        <td>${sysuser.role.roleName}</td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doMangerModify" value="修改">
                            <c:if test="${sysuser.isValid==1}">

                                <input type="button" class="btn btn-danger btn-sm doMangerDisable" value="禁用"
                                       onclick="modifyStatus(${sysuser.id},this)">
                            </c:if>
                            <c:if test="${sysuser.isValid==0}">

                                <input type="button" class="btn btn-success btn-sm doMangerDisable" value="启用"
                                       onclick="modifyStatus(${sysuser.id},this)">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <ul id="pagination"></ul>
        </div>
    </div>
</div>

<!-- 添加系统用户 start -->
<div class="modal fade" tabindex="-1" id="myMangerUser">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <form id="frmAddSysuser">
            <div class="modal-content">
                <!-- 头部、主体、脚注 -->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">添加系统用户</h4>
                </div>
                <div class="modal-body text-center">
                    <div class="row text-right">
                        <label for="marger-username" class="col-sm-4 control-label">用户姓名：</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="marger-username" name="name">
                        </div>
                    </div>
                    <br>

                    <div class="row text-right">
                        <label for="marger-loginName" class="col-sm-4 control-label">登录帐号：</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="marger-loginName" name="loginName">
                        </div>
                    </div>
                    <br>

                    <div class="row text-right">
                        <label for="marger-password" class="col-sm-4 control-label">登录密码：</label>

                        <div class="col-sm-4">
                            <input type="password" class="form-control" id="marger-password" name="password">
                        </div>
                    </div>
                    <br>

                    <div class="row text-right">
                        <label for="marger-phone" class="col-sm-4 control-label">联系电话：</label>

                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="marger-phone" name="phone">
                        </div>
                    </div>
                    <br>

                    <div class="row text-right">
                        <label for="marger-adrees" class="col-sm-4 control-label">联系邮箱：</label>

                        <div class="col-sm-4">
                            <input type="email" class="form-control" id="marger-email" name="email">
                        </div>
                    </div>
                    <br>

                    <div class="row text-right">
                        <label for="role" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>

                        <div class=" col-sm-4">
                            <select class="form-control" name="roleId">
                                <option value="">--请选择--</option>
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role.id}">${role.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary" type="button" onclick="addSysuser()">添加</button>
                    <button class="btn btn-primary cancel" data-dismiss="modal" type="button">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- 添加系统用户 end -->

<!-- 修改系统用户 start -->
<div class="modal fade" tabindex="-1" id="myModal-Manger">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">系统用户修改</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="MargerUsername" class="col-sm-4 control-label">用户编号：</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerStaffId" readonly="readonly">
                    </div>
                </div>
                <br>

                <div class="row text-right">
                    <label for="MargerUsername" class="col-sm-4 control-label">用户姓名：</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerStaffname">
                    </div>
                </div>
                <br>

                <div class="row text-right">
                    <label for="MargerLoginName" class="col-sm-4 control-label">登录帐号：</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerLoginName" readonly="readonly">
                    </div>
                </div>
                <br>

                <div class="row text-right">
                    <label for="MargerPhone" class="col-sm-4 control-label">联系电话：</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerPhone">
                    </div>
                </div>
                <br>

                <div class="row text-right">
                    <label for="MargerAdrees" class="col-sm-4 control-label">联系邮箱：</label>

                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="MargerAdrees">
                    </div>
                </div>
                <br>

                <div class="row text-right">
                    <label for="MargerRole" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>

                    <div class=" col-sm-4">
                        <select class="form-control" id="MargerRole">
                            <option value="">--请选择--</option>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary doMargerModal">修改</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 修改系统用户 end -->

</body>

</html>