<#include "header.ftl">
<link rel="stylesheet" href="../styles/detail.css">
<link rel="stylesheet" href="../styles/changepwd.css">
<div class="desk-front sign-flow clearfix sign-flow-simple">
    <div class="view view-signin" data-za-module="SignInForm" style="display: block;">
        <form action="/changepwd" id="changepwdform" method="post">
            <input type="hidden" name="_xsrf" value="21aa1c8d254df2899b23ab9afbd62a53">
            <div class="group-inputs">
                <div class="email input-wrapper">
                    <span>旧密码：</span>
                    <input type="password" name="username" id="username" aria-label="旧密码" placeholder="请输入原来的密码" required="">
                </div>
                <div class="email input-wrapper">
                    <span>新密码：</span>
                    <input type="password" name="password" id="password" aria-label="新密码" placeholder="请输入新的密码" required="" onkeyup="validate()">
                </div>
                <div class="input-wrapper">
                    <span>确认密码：</span>
                    <input type="password" name="password_again" id="password_again" aria-label="确认密码" placeholder="请再次输入新的密码" required="" onkeyup="validate()">
                </div>
                <span id="tishi"><a style="color:red">${msg!''}</a></span>
            </div>
            <input type="hidden" name="next" value="${next!'/'}"/>     <!-- 返回跳转 -->

            <div class="button-wrapper command clearfix">
                <button id="submit" class="sign-button submit" type="submit" onclick="form=document.getElementById('changepwdform');  form.action='/changepwd/'" disabled="">提交修改</button>
            </div>
        </form>

        <script type="text/javascript" src="/scripts/main/jquery.js"></script>
        <script>
            function validate(){
                var pwd = document.getElementById("password").value;      //密码
                var pwd_again = document.getElementById("password_again").value; //确认密码
                if(pwd==pwd_again ){
                    document.getElementById("tishi").innerHTML="<a style='color:green'>两次密码一致 </a>";
                    document.getElementById("submit").disabled="";
                }else{
                    console.log(pwd+"  "+pwd_again)
                    document.getElementById("tishi").innerHTML="<a style='color:red'>两次密码不一致 </a>";
                    // document.getElementById("submit").disabled = true;
                    $('#submit').attr('disabled','disabled');
                }
            }
        </script>
    </div>
</div>
<#include "footer.ftl">