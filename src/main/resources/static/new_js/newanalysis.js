
//检查图片
function checkImage() {
    var fileName=$("#filename").val();
    fileName=fileName.replace("C:\\fakepath\\","");
    var flag=true;
    if(fileName==""){
        flag=false;
        alert("请选择图片");
    }
    else{
        var size = $("#filename")[0].files[0].size;
        if(size/1000> 51200){
            flag=false;
            result_msg="图片大小不能超过50MB";
        }
    }
    if(flag){
        onLoadImage();
    }
    else{//清除input type=file的显示内容
        $("#filename").val("");
    }
    return flag;
}

// 预览图片
function onLoadImage() {
    var file=$('#filename').get(0).files[0];
    var reader = new FileReader();
    //将文件以Data URL形式读入页面
    reader.readAsDataURL(file);
    reader.onload=function(e){
        //显示文件
        $("#onLoadImage").html('<img src="' + this.result +'" alt="" />');
    }
}
var yesorno=true;
var template=new Object();
function uptemplate() {
    var inputlist = document.getElementsByTagName("input");
    for (var i = 0; i < inputlist.length; i++) {
        // 判断是否为文本框
        if (inputlist[i].type == "text") {
            // 判断文本框内容是否为空
            if (inputlist[i].value == "") {
                yesorno=false;
            }
            else{
                yesorno=true;
            }
        }
    }
    if (yesorno==true) {
        var formdata=new FormData();
        formdata.append('fileName',$('#filename').get(0).files[0]);
        $.ajax({
            async: false,
            type: 'POST',
            url: "/imageUpload",
            dataType: 'json',
            data: formdata,
            contentType:false,//ajax上传图片需要添加
            processData:false,//ajax上传图片需要添加
            success: function (data) {
                if(data.hasOwnProperty("relativePath")){
                    // $("#showImage").html("<img src='"+data.relativePath+"'/>");
                    $("#imageurl").attr("value",data.relativePath)
                }
                else {
                    $("#showImage").html("上传失败");
                }
                alert(data.result_msg);
            },
            error: function (e) {
                alert("error");
            }
        })


        var bmdata= new Array();
        $("input[name='bm']").each(function(){
            bmdata.push($(this).val());
        });
        var name= new Array();
        $("input[name='littlename']").each(function(){
            name.push($(this).val());
        });
        var select= new Array();
        for(var i=0;i<name.length;i++){
            var nn1=$("#Select"+i).val();
            select.push(nn1);
        }
        var obj = {};
        var selectandbm=new Array();
        for(var i=0;i<select.length;i++){
            var temp;
            temp=select[i]+bmdata[i];
            selectandbm.push(temp);
        }
        for (var i = 0; i < name.length; i++) {
            obj[name[i]] = selectandbm[i];
        }




        var para=$("#input1").val();
        template['name']=para;
        template['content']=obj;
        var htmlcontent=$("#analysistextarea").val();
        template['htmlData']=htmlcontent;
        var para1=$("#imageurl").val();
        template['imageUrl']=para1;

        console.log(template);
        addtemplate();
    }
    else {
        alert("请输入完整");
    }


}
function addtemplate() {
    $.ajax({
        async: false,
        type: 'POST',
        url: "/template/addTemplate",
        data: {
            name:template.name,
            content:JSON.stringify(template.content),
            htmlData:template.htmlData,
            imageUrl:template.imageUrl
        },
        success: function (data) {

            alert("保存成功");

        },
        error: function (e) {
            alert("保存失败");
        }
    })
    location.reload();
}

function checkname() {
    var checkname=$("#input1").val();
    var checkname1=checkname.toString();
    $.ajax({
        async: false,
        type: 'POST',
        url: "/template/forVerifyName",
        data: {
            name:checkname1
        },
        success: function (result) {

            console.log(result);
            if (result.errorMsg=="该模板名称已存在") {
                alert("模板名称已存在请重新命名！")
            }
            else if (result.errorMsg=="可以使用该名称") {
                uptemplate();
            }


        },
        error: function (e) {
            alert("保存失败");
        }
    })



}

function analysistemp() {
    $("#selectform").html('');
    var content=$("#analysistextarea").val();
    console.log(content);
    if (content==""||content==null) {
        alert("请输入代码后解析")
    }
    else{
        var regex=/\$\{(.+?)\}/g;
        var result=new Array();
        var temp;
        while(( temp =regex.exec(content))!=null) {
            console.log(temp[0]);
            console.log(temp[1]);
            result.push(temp[1]);

        }
        if (result.length==0){
            alert("未解析到变量请检查后输入 变量模板为${**}")
        }
        else {
            console.log(result);

            for (var i=0;i<result.length;i++ ){
                console.log(result[i]);
                $("#selectform").append('<label>$'+result[i]+'</label><input type="hidden" class="form-control" value="'+result[i]+'" name="littlename"> <SELECT ID="Select'+i+'" NAME="inputselect"> <OPTION VALUE="c1" SELECTED>input框 <OPTION VALUE="c2">textarea <OPTION VALUE="c3">图片上传按钮</SELECT>别名输入：<input id="nickname'+i+'" name="bm"></input> <br>')

            }

            $("#name").show();
            $("#selectdiv").show();
            $("#imagearea").show();
            $("#save").show();
        }


    }


}


/*上传图片
详细参考链接：https://www.cnblogs.com/qiumingcheng/p/6854933.html
1.processData设置为false。因为data值是FormData对象，不需要对数据做处理。
2.<form>标签添加enctype="multipart/form-data"属性。
3.cache设置为false，上传文件不需要缓存。
4.contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
*/
function checkSubmit() {
    var formdata=new FormData();
    formdata.append('fileName',$('#filename').get(0).files[0]);
    console.log(formdata);
    $.ajax({
        async: false,
        type: 'POST',
        url: "/imageUpload",
        dataType: 'json',
        data: formdata,
        contentType:false,//ajax上传图片需要添加
        processData:false,//ajax上传图片需要添加
        success: function (data) {
            if(data.hasOwnProperty("relativePath")){
                // $("#showImage").html("<img src='"+data.relativePath+"'/>");
                $("#imageurl").attr("value",data.relativePath);
            }
            else {
                $("#showImage").html("上传失败");
            }
            alert(data.result_msg);
        },
        error: function (e) {
            alert("error");
        }
    })

    var template=new Array();
    var para=$("#input1").val();
    var strdata = $("#selectform").serializeArray();
    var para1=$("#imageurl").val();
    var content=$("#analysistextarea").val();
    template.push(para);
    template.push(strdata);
    template.push(para1);
    template.push(content);
    console.log(template);
}