<html>
<title><#include "home.ftl"/></title>
<body>

<pre>



    ${value1}
    <#list colors as list>
        <b>colors里面的颜色: ${list}</b>
    </#list>

    <#list map?keys as key>
        <b>map第${key}项元素是： ${map["${key}"]}</b>
    </#list>
    User: ${user.name}
    ${user.getDescription()};
    ${user.description};
    <#assign keys= "shabi"/>
    ${keys}


    <#assign h="hello"/>
    <#assign h1="${h} world1"/>
    <#assign h2='${h} world2'/>
    ${h}

    ${h1}

    ${h2}
</pre>

</body>
</html>