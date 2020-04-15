<html>
<head>
    <title>${Title!'更换头像'}</title>

    <style type="text/css">

        #box-1{
            width: 200px;
            height: 200px;
            border: 1px solid gray;
            border-radius: 50%;
        }
        img{
            width: 100%;
            height: 100%;
            border-radius: 50%;

        }

    </style>
</head>
<body>

<div id="box-1">
    <img id="show-img" src="" alt=""/>
</div>

<div id="box-2">
    <form action="/UploadDemo/getFile" method="post" enctype="multipart/form-data">
        <input id="choose-file" type="file" name="file" accept="image/*"/>
        <input type="submit" value="提交"/>
    </form>
</div>
<script type="text/javascript" src="/scripts/main/jquery.js"></script>
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
</body>
</html>