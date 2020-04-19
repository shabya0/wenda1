<#--<#setting bodyclass = "page-search">-->
<#include "header.ftl">
<link rel="stylesheet" href="../styles/detail.css">
<link rel="stylesheet" href="../styles/result.css">
<div class="zg-wrap zu-main clearfix" role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <ul class="list contents navigable" id="search-res">
                <p style="font-size: 16px;border-bottom: 0.1px solid #f5efef;padding-bottom: 10px;color: #a9abc5;"> 搜索结果 </p>
                <#if vos_len==0 ><div class="none-content"><p style="font-size: 16px; padding-top:20px;color: #6f7584;">找不到相关内容呢,要不要换个问题看看...</p></div></#if>
                <#list vos as vo>
                <li class="item clearfix" id="item">
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
            <a href="javascript:;" id="zh-load-more" data-method="next" class="zg-btn-white zg-r3px zu-button-more" style="">更多</a></div>
            <script type="text/javascript" src="/scripts/main/jquery.js"></script>
            <script language="javascript" type="text/javascript">

                var pagesize = 10, currpage = 2; //默认第一页,点击跳转到第二页
                var Dom = jQuery('#search-res');
                jQuery('#zh-load-more').on('click', function() {
                    var url = 'http://localhost:8080/searchMore?q=${keyword!""}&&offset='+currpage;
                    jQuery.ajax({
                        type: 'POST',//请求类型
                        url: url,//请求地址
                        dataType: 'html',//返回数据类型
                        success: function (response, status) {//请求成功
                            console.log(response)
                            //如果Data不等于空，证明有数据，开始执行以下方法
                            if (response != '') {
                                Dom.append(response);
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
<#include "js.ftl"/>
<#include "footer.ftl"/>