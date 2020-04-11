<#list vos as vo>
<div class="feed-item folding feed-item-hook feed-item-1" feed-item-p="" data-type="p" id="feed-1" data-za-module="FeedItem" data-za-index="">
    <meta itemprop="ZReactor" data-id="113477" data-meta="{&quot;source_type&quot;: &quot;promotion_article&quot;, &quot;voteups&quot;: 1082, &quot;comments&quot;: 100, &quot;source&quot;: []}">
    <div class="feed-item-inner">
        <div class="avatar">
            <a title="${vo.user.name}" data-tip="p$t$zhao-yong-feng" class="zm-item-link-avatar" target="_blank" href="/user/${vo.user.id?c}">
                <img src="${vo.user.headUrl}" class="zm-item-img-avatar"></a>   <#--头像-->
        </div>
        <div class="feed-main">
            <div class="feed-content" data-za-module="PostItem">
                <meta itemprop="post-id" content="113477">
                <meta itemprop="post-url-token" content="19831487">
                <h2 class="feed-title">
                    <a rel="noopener noreferrer" target="_blank" class="post-link" href="localhost:8080/question/${vo.question.id?c}">${vo.question.title}</a></h2>     <!--问题标题-->
                <div class="entry-body post-body js-collapse-body">
                    <div class="zm-item-vote">
                        <a class="zm-item-vote-count js-expand js-vote-count" href="#" data-bind-votecount="">内容</a>
                    </div>
                    <div class="zm-votebar" data-za-module="VoteBar">
                        <button class="up" aria-pressed="false" title="赞同">     <!--//点赞用户数量-->
                            <i class="icon vote-arrow"></i>
                            <span class="count">1082</span>
                            <span class="label sr-only">赞同</span></button>
                    </div>
                    <div class="author-info">
                        <a href="/user/${vo.user.id?c}" data-tip="p$t$zhao-yong-feng" class="name">${vo.user.name}</a> ,  发布于${vo.question.createdDate?string("yyyy-MM-dd HH:mm:ss")}</div>       <#--用户名字  salt-->
                    <div class="zm-item-vote-info" data-votecount="1082" data-za-module="VoteInfo">
                        <span class="voters text">
                            <a href="#" class="more text">
                                <span class="js-voteCount">1082</span>&nbsp;人赞</a></span>
                    </div>
                    <div class="post-content" data-author-name="${vo.user.name}" data-entry-url="http://zhuanlan.zhihu.com/p/19831487">
                        <textarea class="content hidden">${vo.question.content}</textarea>
                        <div class="zh-summary summary clearfix">${vo.question.content}
                        </div>
                    </div>
                </div>
                <div class="feed-meta">
                    <div class="zm-item-meta meta clearfix js-contentActions">
                        <div class="zm-meta-panel">
                            <a data-follow="c:link" class="zg-follow meta-item" href="#" id="cl-2180">
                                <i class="z-icon-follow"></i>关注问题</a>
                            <a href="#" class="meta-item toggle-comment js-toggleCommentBox">
                                <i class="z-icon-comment"></i>${vo.question.commentCount} 条评论</a>
                            <a href="#" class="meta-item zu-autohide js-share goog-inline-block goog-menu-button" role="button" aria-expanded="false" tabindex="0" aria-haspopup="true" style="-webkit-user-select: none;">
                                <div class="goog-inline-block goog-menu-button-outer-box">
                                    <div class="goog-inline-block goog-menu-button-inner-box">
                                        <div class="goog-inline-block goog-menu-button-caption">
                                            <i class="z-icon-share"></i>分享</div>
                                        <div class="goog-inline-block goog-menu-button-dropdown">&nbsp;</div></div>
                                </div>
                            </a>
                            <span class="zg-bull zu-autohide">•</span>
                            <a href="#" class="meta-item zu-autohide js-report">举报</a>
                            <button class="meta-item item-collapse js-collapse">
                                <i class="z-icon-fold"></i>收起</button>
                        </div>
                    </div>
                    <a href="#" class="ignore zu-autohide" name="dislike" data-tip="s$b$不感兴趣"></a>
                </div>
            </div>
        </div>
    </div>
    <div class="undo-dislike-options" data-item_id="1">此内容将不会在动态中再次显示
        <span class="zg-bull">•</span>
        <a href="#" class="meta-item revert">撤销</a>
        <a href="#" class="ignore zu-autohide close"></a>
    </div>
</div>
</#list>