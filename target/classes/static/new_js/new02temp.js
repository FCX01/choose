var yesorno=true;
var count=0;
var jsgroup=new Object();
var Alljson2={};
var temparr=new Array();

/*文件上传*/
function imageup() {
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

    var textarealist= document.getElementsByTagName("textarea")
    for (var i = 0; i < textarealist.length; i++) {
        // 判断textarea内容是否为空
        if (textarealist[i].value == "") {
            yesorno=false;
        }
        else{
            yesorno=true;
        }
    }

    //内容是否全部填写完毕
    if (yesorno==true){
        for (var i = 0; i < count; i++) {
            var num=i;
            if (imagenumber[i]>0){
                console.log(imagenumber[i]);
                for (var temp=0;temp<imagenumber[i];temp++){
                    var formdata = new FormData();
                    console.log(temp)
                    console.log(imagenumber[i]);
                    formdata.append('fileName', $('#filename' + i+'0000'+temp).get(0).files[0]);
                    $.ajax({
                        async: false,
                        type: 'POST',
                        url: "/imageUpload",
                        dataType: 'json',
                        data: formdata,
                        contentType: false,//ajax上传图片需要添加
                        processData: false,//ajax上传图片需要添加
                        success: function (data) {
                            var urltemp=data.relativePath;
                            urltemp=urltemp.toString();
                            var url=urltemp.substring(2);
                            $("#imageurl" + num+'0000'+temp).attr("value", url);
                        },
                        error: function (e) {
                            alert("请上传图片");
                            break;
                        }
                    })
                }
            }
            else{
                console.log("meitupain")
            }
        }
        alldata();
    }
    else {
        alert("请将内容填写完整");
    }
}

function alldata() {
    var para=$("#templatename").val();
    jsgroup['name']=para;
    for (var i=0;i<count;i++ ) {
        var strdata = $("#form" + i).serializeArray();
        if (strdata.length != 0) {
            var gata = trans.serialize(strdata);
            temparr.push(gata);
        } else {
            continue;
        }
    }
    console.log(temparr);
    for (var i = 0; i < temparr.length; i++) {
        Alljson2[i] = temparr[i];
    }
    jsgroup['content']=Alljson2;
    console.log(jsgroup);

    addhomepage();




}

function addhomepage() {
    $.ajax({
        // async: false,
        type: 'POST',
        url: "/homePage/addHomePageInfo",
        data: {
            name:jsgroup.name,
            content:JSON.stringify(jsgroup.content),

        },
        success: function (data) {

            alert("保存成功");

        },
        error: function (e) {
            alert("保存失败");
        }
    })

    var searchUrl =encodeURI("test");
    window.location.href=searchUrl;
}

var trans={
    serialize:function(obj){
        var o ={};
        $.each(obj,function(){
            if(o[this.name]){
                if(!o[this.name].push){
                    o[this.name]=[o[this.name]];
                }
                o[this.name].push(this.value||"");
            }else {
                o[this.name] = this.value || "";
            }
        });
        return o;
    }
}

/*删除模板*/
function delform(obj) {
    obj.parents(".rep").remove();
}

jQuery(document).ready(function() {

    $('.launch-modal').on('click', function(e){
        e.preventDefault();
        $( '#' + $(this).data('modal-id') ).modal();
    });



});
var imagenumber=new Array();
function tianjia() {
    var name=$('input[name="temp"]:checked').val();
    console.log(name);
    $.ajax({
        type: "post",
        url: "/template/getTemplate",
        data: name,
        dataType: "json",
        contentType: "application/json",
        success: function(result) {
            //stringify()用于从一个对象解析出字符串
            var tempnumber=0;
            console.log(result);
            console.log(result.data);
            var AllTemp = $.parseJSON(result.data);
            $("#TemplateArea").append('<div  id="TemplateArea'+count+'" ></div>');
            $("#TemplateArea"+count).append('<form  class="rep" id="form'+count+'" name="form'+count+'" action="imageUpload" method="post" enctype="multipart/form-data"></form>')
            for ( var i in AllTemp)
            {
                var choicetemp=AllTemp[i];
                choicetemp=choicetemp.toString();
                var choice=choicetemp.substring(0,2);
                var nicknametemp=AllTemp[i];
                nicknametemp=nicknametemp.toString();
                var nickname=nicknametemp.substring(2);

                if (i=="id"){
                    $("#form"+count).append('<input type="hidden" name="' + i + '" id="' + i + '" value="' + AllTemp[i] + '"/><br/><br/>');
                }
                else if (i=="name"){
                    $("#form"+count).append('<input type="hidden" name="' + i + '" id="' + i + '" value="' + AllTemp[i] + '"/><br/><br/>');
                    $("#form"+count).append('<span>'+AllTemp[i]+'</span><br/><br/>');
                }
                else if(choice=="c1"){
                    $("#form"+count).append('<label>' + nickname + ':</label><input type="text" name="' + i + '" id="' + i + '"/><br/><br/>');
                }
                else if(choice=="c2"){
                    $("#form"+count).append('<label>' + nickname + ':</label><textarea name="' + i + '" id="' + i + '"></textarea><br/><br/>');
                }
                else if(choice=="c3"){


                    $("#form"+count).append('<div id="onLoadImage'+count+'0000'+tempnumber+'"></div>');
                    $("#form"+count).append('<a href="javascript:;" class="a-upload"> <input type="file" name="fileName'+count+'0000'+tempnumber+'" id="filename'+count+'0000'+tempnumber+'" accept="image/png, image/jpeg, image/jpg\" onchange=\"checkImage(event)\">点击这里上传文件 </a><br>');
                    // $("#form"+count).append('<input type="file" name="fileName'+count+'" id="filename'+count+'" accept="image/png, image/jpeg, image/jpg" onchange="checkImage(event)">')
                    $("#form"+count).append('<input type="hidden" id="imageurl'+count+'0000'+tempnumber+'" name="'+i+'" value="">')
                    tempnumber++;
                }
                else  {
                    continue;

                }
            }
            imagenumber.push(tempnumber);


            $("#form"+count).append('<a class="btn btn-link-1" id="DelTextBox'+count+'" onclick="delform($(this))">删除</a>');

            count++;


        }
    });

    console.log(count);
    console.log(imagenumber);


}

function getAlltemplate() {
    $("#selectdiv").html('');           //div赋值
    $.ajax({
        type: "post",
        url: "/template/getAllTemplate",
        data: "",
        dataType: "json",
        contentType: "application/json",
        success: function(result) {     //请求成功执行result

            //stringify()用于从一个对象解析出字符串

            var AllTemp1 = result.data; //模板数组
            console.log(AllTemp1);

            $("#selectdiv").append('<form role="form" th:action="" method="get" class="registration-form" id="selectform" enctype="multipart/form-data"></form>');
            for(var i=0;i<AllTemp1.length;i++){

                var AllTemp = $.parseJSON(AllTemp1[i]);
                console.log(AllTemp)
                var imageurltemp=AllTemp.imageUrl;
                imageurltemp=imageurltemp.toString();
                var imageurl=imageurltemp.substring(2);


                $("#selectform").append('<div class="col-sm-6 layout-box"> ' +
                                            '<p>'+
                                            '<label for="'+AllTemp.name+'"> ' +
                                            '<img src="'+imageurl+'" > ' +
                                            '<input id="'+AllTemp.id+'" type="radio" name="temp" value="'+AllTemp.name+'" >' +
                                            ''+AllTemp.name+' ' +
                                            '</label></p></div> ');

            }

            $("#selectform").append('<button type="button" class="btn" onclick="tianjia()" data-dismiss="modal">Submit!</button>');
        },
        error: function(){          //请求失败
            alert("查询失败");
        }

    });

}

// var  handTitle;

// console.log(handTitle);


//检查图片
function checkImage(e) {
    var val=$(e.target).attr("id");
    console.log(val);
    var num= val.replace(/[^0-9]/ig,"");
    console.log(num);

    var fileName=$("#filename"+num).val();
    fileName=fileName.replace("C:\\fakepath\\","");
    var flag=true;
    if(fileName==""){
        flag=false;
        alert("请选择图片");
    }
    else{
        var size = $("#filename"+num)[0].files[0].size;
        if(size/1000> 51200){
            flag=false;
            result_msg="图片大小不能超过50MB";
        }

    }
    if(flag){
        var file=$('#filename'+num).get(0).files[0];
        var reader = new FileReader();
        //将文件以Data URL形式读入页面
        reader.readAsDataURL(file);
        reader.onload=function(e){
            //显示文件
            $("#onLoadImage"+num).html('<img src="' + this.result +'" alt="" />');
        }
    }
    else{//清除input type=file的显示内容
        $("#filename"+num).val("");
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
}