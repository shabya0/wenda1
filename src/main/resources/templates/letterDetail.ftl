<#include "header.ftl">
<#--<title>-->
    <#--私信：${message.user.name}-->
<#--</title>-->
<link rel="stylesheet" media="all" href="../styles/letter.css">
<div id="main">
    <div class="zg-wrap zu-main clearfix ">
        <ul class="letter-chatlist">
             <#list messages as message>
            <li id="msg-item-4009580">
                <#if user.id != message.user.id>
                <#--对方消息，放左边-->
                    <a class="list-head">
                        <img alt="头像" src="${message.user.headUrl}">
                    </a>
                    <div class="list-name"><a class="user-name">${message.user.name}</a></div>
                <#else>
                <#--己方消息，放右边-->
                    <div >
                        <div class="list-name-right">
                            <span class="user-name-right">我</span>
                        </div>
                    <#--<div class="list-name-right">-->
                        <div class="listhead-righg-div">
                            <a class="list-head-right">
                                <img alt="头像" src="${message.user.headUrl}">
                            </a>
                        </div>
                    </div>
                    <#--</div>-->
                </#if>
                <div class="tooltip fade right in">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner letter-chat clearfix">
                        <div class="letter-info">
                            <p class="letter-time">${message.message.createdDate?string("yyyy-MM-dd HH:mm:ss")})</p>
                            <!-- <a href="javascript:void(0);" id="del-link" name="4009580">删除</a> -->
                        </div>
                        <p class="chat-content">
                            ${message.message.content}
                        </p>
                    </div>
                </div>
            </li>
             </#list>
        </ul>

    </div>
</div>
<#--<#include "js.html">-->
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl">