<#include "header.ftl">
<link rel="stylesheet" media="all" href="../styles/letter.css">
    <div id="main">
        <div class="zg-wrap zu-main clearfix ">
            <div class="letter-title">
                <u>
                    私信列表
                </u>
            </div>
            <ul class="letter-list">
                <#list conversations as conversation>
                <li id="conversation-item-10005_622873">
                    <a class="letter-link" href="/msg/detail?conversationId=${conversation.message.conversationId}"></a>
                    <div class="letter-info">
                        <span class="l-time">${conversation.message.createdDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                        <div class="l-operate-bar">
                            <!--<a href="javascript:void(0);" class="sns-action-del" data-id="10005_622873">
                            删除
                            </a>-->
                            <a href="/msg/detail?conversationId=${conversation.message.conversationId}">
                                共${conversation.message.id}条会话
                            </a>
                        </div>
                    </div>
                    <div class="chat-headbox">
                        <#--未读标识-->
                        <#if conversation.unread != 0>
                            <span class="msg-num">
                                ${conversation.unread}
                            </span>
                        </#if>
                        <a class="list-head">
                            <img alt="头像" src="${conversation.user.headUrl}">
                        </a>
                    </div>
                    <div class="letter-detail">
                        <a title="通知" class="letter-name level-color-1">
                            ${conversation.user.name}
                        </a>
                        <p class="letter-brief">
                            ${conversation.message.content}
                        </p>
                    </div>
                </li>
                </#list>
                </ul>

        </div>
    </div>
<#include "js.ftl">
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl">