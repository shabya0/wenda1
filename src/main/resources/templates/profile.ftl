<#include "header.ftl">
<link rel="stylesheet" href="../styles/detail.css">
<script type="text/javascript">
    function show(){
        var loadbox =document.getElementById("loadlayer");
        var overlayer = document.getElementById("overlayer");
        var wholePage = document.getElementById("wholePage");
        loadbox.style.display = "block" ;
        overlayer.style.display = "block" ;
        wholePage.style.display = "block";
    }

    function hide(){
        var loadbox =document.getElementById("loadlayer");
        var overlayer = document.getElementById("overlayer");
        var wholePage = document.getElementById("wholePage");
        loadbox.style.display = "none" ;
        overlayer.style.display = "none" ;
        wholePage.style.display = "none";
    }
</script>
<style type="text/css">
    #overlayer{
        position: absolute;
        top: 25%;
        left: 30%;
        z-index: 90;
        width: 40%;
        height: 50%;
        background-color: #c1bfec;
        filter:alpha(opacity=60);
        -moz-opacity: 0.5;
        opacity: 0.5;
        display:none;
    }

    #loadbox{
        position: absolute;
        top: 45%;
        left: 0;
        width: 100%;
        z-index: 100;
        text-align: center;
    }

    #loadlayer{
        display:none;

    }
    #box-1 {
        width: 200px;
        height: 200px;
        margin-left: 44%;
        margin-top: -8%;
        border: 1px solid gray;
        border-radius: 50%;
    }
    #box-2{
        padding-top:40px;
    }
    img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
    }
    #submit {
        background: linear-gradient(to bottom,#6a636f3b,#46424a66);
        border: 0px solid #e6e9ff;
        box-shadow: 0 1px 0 rgba(255,255,255,.5) inset, 0 1px 0 rgba(0,0,0,.2);
        border-radius: 5px;
        color: white;
        font-size: 14px;
    }
    #cancel{
        position: absolute;
        margin-top: -10%;
        margin-left: 21%;
        font-size: 22px;
        color: #b4b0bf;
        border: 0px;
        background: #c1bfec00;
    }
    input#cancel:hover {
        color: #080808d6;
    }
    #wholePage{
        position: absolute;
        z-index:100;
        width: 100%;
        height: 100%;
        filter: alpha(opacity=60);
        -moz-opacity: 0.5;
        opacity: 0.1;
        display: none;
    }
</style>
<div id="wholePage"></div>
<div id="overlayer"></div>
<div id="loadbox" >
    <script type="text/javascript" src="/scripts/main/jquery.js"></script>
    <div id="loadlayer">
            <input id="cancel" type="button" value="x" onclick="hide()"/>
        <a>点击选择要上传的图片</a>
        <div id="box-1">
            <img id="show-img" src="../images/res/2ed48.png" alt=""/>
        </div>
        <div id="box-2">
            <form action="/UploadDemo/getFile" method="post" enctype="multipart/form-data">
                <input id="choose-file" type="file" name="file" accept="image/*"/>
                <input id="submit" type="submit" value="提交"/>
            </form>
        </div>
        <script type="text/javascript">
            $(function(){
                //在input file内容改变的时候触发事件
                $('#choose-file').change(function(){
                    //获取input file的files文件数组;
                    //$('#filed')获取的是jQuery对象，.get(0)转为原生对象;
                    //这边默认只能选一个，但是存放形式仍然是数组，所以取第一个元素使用[0];
                    var file = $('#choose-file').get(0).files[0];
                    //创建用来读取此文件的对象
                    var reader = new FileReader();
                    //使用该对象读取file文件
                    reader.readAsDataURL(file);
                    //读取文件成功后执行的方法函数
                    reader.onload=function(e){
                        //读取成功后返回的一个参数e，整个的一个进度事件
                        console.log(e);
                        //选择所要显示图片的img，要赋值给img的src就是e中target下result里面
                        //的base64编码格式的地址
                        $('#show-img').get(0).src = e.target.result;
                    }
                });

                //隐藏input file控件
                $("#choose-file").hide();

                $("#box-1").bind('click',function(){
                    //当点击头像框时，就会弹出文件选择对话框
                    $("#choose-file").click();

                });

            });
        </script>
    </div>
</div>
    <div class="zg-wrap zu-main clearfix " role="main">
        <div class="zm-profile-section-wrap zm-profile-followee-page">
            <!--
                <div class="zm-profileMore.ftl-section-head">
                    <span class="zm-profileMore.ftl-section-name">
                        <a href="#">${profileUser.user.name}</a> 关注了 ${profileUser.followerCount} 人
                    </span>
                </div>-->
                <div class="zm-profile-section-list">
                    <div id="zh-profile-follows-list">
                        <div class="zh-general-list clearfix">
                            <div class="zm-profile-card zm-profile-section-item zg-clear no-hovercard">
                                <div class="zg-right">
                                    <#if profileUser.followed>
                                    <button class="zg-btn zg-btn-unfollow zm-rich-follow-btn small nth-0
                                    js-follow-user" data-status="1" data-id="${profileUser.user.id?c}">取消关注</button>
                                    <#else>
                                    <button class="zg-btn zg-btn-follow zm-rich-follow-btn small nth-0
                                    js-follow-user" data-id="${profileUser.user.id}">关注</button>
                                    </#if>
                                </div>
                                <a title="Barty" class="zm-item-link-avatar" href="/user/${profileUser.user.id?c}">
                                    <img src="${profileUser.user.headUrl}" class="zm-item-img-avatar">
                                </a>
                                <div class="zm-list-content-medium">
                                    <h2 class="zm-list-content-title"><a data-tip="p$t$buaabarty" href="/user/${profileUser.user.id?c}" class="zg-link">${profileUser.user.name}</a></h2>

                                    <div class="details zg-gray">
                                        <a target="" href="/user/${profileUser.user.id}/followers" class="zg-link-gray-normal">${profileUser.followerCount?c}粉丝</a>
                                        /
                                        <a target="" href="/user/${profileUser.user.id}/followees" class="zg-link-gray-normal">${profileUser.followeeCount?c}关注</a>
                                        /
                                        <a class="zg-link-gray-normal">${profileUser.commentCount?c} 回答</a>
                                        <#--/-->
                                        <#--<a target="_blank" href="#" class="zg-link-gray-normal">548 赞同</a>-->
                                    </div>
                                    <div class="chgpassword">
                                        <a href="/chgpw">修改密码</a>
                                        /
                                        <a class="info" onclick="show()"> 修改头像</a>   <!-- href="/info_pic/"-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <div class="zu-main-content">
            <div class="zu-main-content-inner">
                <div class="zg-section" id="zh-home-list-title">
                    <i class="zg-icon zg-icon-feedlist"></i>最新动态
                    <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <#--<a href="#" class="zg-link-gray-normal">-->
                            <#--<i class="zg-icon zg-icon-settings"></i>设置-->
                        <#--</a>-->
                    </span>
                </div>
                <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list">
                    <div id="js-home-feed-list" class="zh-general-list topstory clearfix">
                        <#list vos as vo>
                        <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
                            <meta itemprop="ZReactor" data-id="389034" data-meta="">
                            <div class="feed-item-inner">
                                <div class="avatar">
                                    <a title="${vo.user.name}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="https://nowcoder.com/people/amuro1230">
                                        <img src="${vo.user.headUrl}" class="zm-item-img-avatar"></a>
                                </div>
                                <div class="feed-main">
                                    <div class="feed-content" data-za-module="AnswerItem">
                                        <meta itemprop="answer-id" content="389034">
                                        <meta itemprop="answer-url-token" content="13174385">
                                        <h2 class="feed-title">
                                            <a class="question_link" target="_blank" href="/question/${vo.question.id?c}">${vo.question.title}</a></h2>
                                        <div class="feed-question-detail-item">
                                            <div class="question-description-plain zm-editable-content"></div>
                                        </div>
                                        <div class="expandable entry-body">
                                            <#--<div class="zm-item-vote">-->
                                                <#--<a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">${vo.followCount}</a>-->
                                            <#--</div>-->
                                            <div class="zm-item-answer-author-info">
                                                <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.user.id?c}">${vo.user.name}</a>
                                                ${vo.question.createdDate?string("yyyy-MM-dd HH:mm:ss")}</div>
                                            <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount"></span>&nbsp;人赞同</a></span>
                                            </div>
                                            <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                                                <div class="zh-summary summary clearfix">${vo.question.content}</div>
                                            </div>
                                        </div>
                                        <div class="feed-meta">
                                            <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                                <div class="zm-meta-panel">
                                                    <a data-follow="q:link" class="follow-link zg-follow meta-item" href="javascript:;" id="sfb-123114">
                                                        <#--<i class="z-icon-follow"></i>关注问题</a>-->
                                                    <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                                        <i class="z-icon-comment"></i>${vo.question.commentCount?c} 条评论</a>

                                                    <button class="meta-item item-collapse js-collapse">
                                                        <i class="z-icon-fold"></i>收起</button>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </#list>

                    </div>
                    <a href="javascript:;" id="zh-load-more" class="zg-btn-white zg-r3px zu-button-more">更多</a>
                    <script language="javascript" type="text/javascript">
                        var pagesize = 10, currpage = 2; //默认第一页
                        var Dom = jQuery('#js-home-feed-list');
                        jQuery('#zh-load-more').on('click', function() {
                            var url = 'http://localhost:8080/moreProfile?pages='+currpage;
                            jQuery.ajax({
                                type: 'POST',//请求类型
                                url: url,//请求地址
                                dataType: 'html',//返回数据类型
                                success: function (response, status) {//请求成功
                                    if (response != '') {
                                        Dom.append(response),
                                                //分页+=1
                                                currpage += 1;
                                    }
                                    //全部加载完毕提示
                                    else {
                                        jQuery('#zh-load-more').text("没有更多内容");
                                    }
                                },
                                //请求失败
                                error: function (xhr, status, error) {
                                    console.log("请求对象XMLHttpRequest: " + xhr);
                                    console.log("错误类型textStatus: " + status);
                                    console.log("异常对象errorThrown: " + error);
                                }
                            })
                        })
                    </script>
                </div>
            </div>
        </div>
    </div>
<#include "js.ftl">
<script type="text/javascript" src="/scripts/main/site/profile.js"></script>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#--<#include "footer.ftl">-->
</body>
</html>