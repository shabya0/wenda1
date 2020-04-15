<#macro comment_question vo>
<div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
    <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
    <div class="feed-item-inner">
        <div class="avatar">
            <a title="${vo.username}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.userId?c}">
                <img src="${vo.userHead}" class="zm-item-img-avatar"></a>
        </div>
        <div class="feed-main">
            <div class="feed-content" data-za-module="AnswerItem">
                <meta itemprop="answer-id" content="389034">
                <meta itemprop="answer-url-token" content="13174385">

                <div class="expandable entry-body">

                    <div class="zm-item-answer-author-info">
                        <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.userId?c}">${vo.username}</a>
                        评论了以下问题 ， ${vo.createdDate?string("yyyy-MM-dd HH:mm:ss")}</div>

                    <div class="zm-item-rich-text expandable js-collapse-body"  data-resourceid="123114" data-action="/answer/content" data-author-name="${vo.username}" data-entry-url="/question/${vo.questionId}">
                        <div class="zh-summary summary clearfix"><a href="/question/${vo.questionId}"  target="_blank" >${vo.questionTitle}</a></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</#macro>

<#macro follow_question vo>
<div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
    <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
    <div class="feed-item-inner">
        <div class="avatar">
            <a title="${vo.username}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.userId?c}">
                <img src="${vo.userHead}" class="zm-item-img-avatar"></a>
        </div>
        <div class="feed-main">
            <div class="feed-content" data-za-module="AnswerItem">
                <meta itemprop="answer-id" content="389034">
                <meta itemprop="answer-url-token" content="13174385">

                <div class="expandable entry-body">

                    <div class="zm-item-answer-author-info">
                        <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.userId?c}">${vo.username}</a>
                        关注了以下问题 ，${vo.createdDate?string("yyyy-MM-dd HH:mm:ss")}</div>

                    <div class="zm-item-rich-text expandable js-collapse-body"  data-resourceid="123114" data-action="/answer/content" data-author-name="${vo.username}" data-entry-url="/question/${vo.questionId}">
                        <div class="zh-summary summary clearfix"><a href="/question/${vo.questionId}"  target="_blank" >${vo.questionTitle}</a></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</#macro>

<#macro add_question vo>
<div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
    <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
    <div class="feed-item-inner">
        <div class="avatar">
            <a title="${vo.username}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="/user/${vo.userId?c}">
                <img src="${vo.userHead}" class="zm-item-img-avatar"></a>
        </div>
        <div class="feed-main">
            <div class="feed-content" data-za-module="AnswerItem">
                <meta itemprop="answer-id" content="389034">
                <meta itemprop="answer-url-token" content="13174385">

                <div class="expandable entry-body">

                    <div class="zm-item-answer-author-info">
                        <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.userId?c}">${vo.username}</a>
                        发布了以下问题  ${vo.createdDate?string("yyyy-MM-dd HH:mm:ss")}</div>

                    <div class="zm-item-rich-text expandable js-collapse-body"  data-resourceid="123114" data-action="/answer/content" data-author-name="${vo.username}" data-entry-url="/question/${vo.questionId}">
                        <div class="zh-summary summary clearfix"><a href="/question/${vo.questionId}"  target="_blank" >${vo.questionTitle}</a></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</#macro>


<#list feeds as vo>
    <#if vo.type == 1>
        <@comment_question vo/>
    <#elseif vo.type==4>
        <@follow_question vo/>
    <#elseif vo.type==6>
        <@add_question vo/>
    </#if>
</#list>