<#include "header.ftl">

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


<link rel="stylesheet" href="../styles/index.css">
<link rel="stylesheet" href="../styles/detail.css">
    <div class="zg-wrap zu-main clearfix " role="main">
        <div class="zu-main-content">
            <div class="zu-main-content-inner">
                <div class="zg-section" id="zh-home-list-title">
                    <i class="zg-icon zg-icon-feedlist"></i>最新动态
                    <input type="hidden" id="is-topstory">
                    <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">
                            <i class="zg-icon zg-icon-settings"></i>设置</a></span>
                </div>
                <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                    <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                    <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">

                        <#list feeds as vo>
                            <#if vo.type == 1>
                                <@comment_question vo/>
                            <#elseif vo.type==4>
                                <@follow_question vo/>
                            <#elseif vo.type==6>
                                <@add_question vo/>
                            </#if>
                        </#list>
                    </div>
                    <a href="javascript:;" id="zh-load-more" data-method="next" class="zg-btn-white zg-r3px zu-button-more" style="">更多</a></div>
                <script type="text/javascript" src="/scripts/main/jquery.js"></script>
                <script language="javascript" type="text/javascript">

                    var pagesize = 10, currpage = 2; //默认第一页
                    var Dom = jQuery('#js-home-feed-list');
                    jQuery('#zh-load-more').on('click', function() {
                        var url = 'http://localhost:8080/foundMore?pages='+currpage;
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
<#include "js.ftl">
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl">