<#--<#setting bodyclass = "page-search">-->
<#include "header.ftl">
<link rel="stylesheet" href="../styles/detail.css">
<link rel="stylesheet" href="../styles/result.css">
<div class="zg-wrap zu-main clearfix" role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <ul class="list contents navigable">
                <#if vos_len==0 ><div class="none-content"><p style="font-size: 16px; padding-top:20px;color: #6f7584;">找不到相关内容呢,要不要换个问题看看...</p></div></#if>
                <#list vos as vo>
                <li class="item clearfix">
                    <div class="title">
                        <a target="_blank" href="/question/${vo.question.id?c}" class="js-title-link">${vo.question.title}</a>
                    </div>
                    <div class="content">

                        <ul class="answers">
                            <li class="answer-item clearfix">
                                <div class="entry answer">
                                    <#--关注问题人数-->
                                    <#--<div class="entry-left hidden-phone">
                                        <a class="zm-item-vote-count hidden-expanded js-expand js-vote-count" data-bind-votecount="">${vo.followCount}</a>
                                    </div>
                                    -->
                                    <div class="entry-body">
                                        <div class="entry-meta">
                                            <strong class="author-line"><a class="author" href="/user/${vo.user.id?c}">${vo.user.name}</a>， ${vo.question.createdDate?string("yyyy-MM-dd HH:mm:ss")}</strong>
                                        </div>
                                        <div class="entry-content js-collapse-body">
                                            <div class="summary hidden-expanded" style="">
                                            ${vo.question.content}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
                </#list>

            </ul>
        </div>
    </div>
</div>
<#include "js.ftl"/>
<#include "footer.ftl"/>