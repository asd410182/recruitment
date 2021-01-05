/*
    注册：
    1. 检查手机号格式
    2. 发送验证码
    3. 检查短信验证码（验证码是从后端获取的）
*/

// //用来标识是否发送验证码

var flag = true;
let type = "applicant";

window.onload = function(){
    var g = document.getElementsByTagName("input");
    for(let i=0; i<g.length; i++){
        g[i].addEventListener("focus", function(){
            this.parentElement.className += "border";
        })
        g[i].addEventListener("focusout", function(){
            this.parentElement.className = "";
        })
    }
    document.getElementById("applicant").parentElement.style.border = "1px solid #00d7c6";
}

function register(){
    if(!checkPhone()){
        return false;
    }
    // if(!checkVerification()){
    //     return false;
    // }
    if(!checkPassword()){
        return false;
    }
    let role = document.getElementById(type).id;
    let phone = document.getElementById("phone-number").value;
    let pwd = document.getElementById("password").value;

    $.ajax({
        method: "post",
        url: "/user/save",
        data: data = {
            "role": role,
            "phone": phone,
            "pwd": pwd
        },
        success: function(res){
            console.log(res);
            if(res === "fail"){
                alert("注册成功请直接登录")
            }else if(res === "successfail"){
                alert("账号已经被注册，请换个账号再注册")
            }else{
                console.log(res);
                window.location.href ="/user/jumpAfterRegister?uid="+res;
            }
        },
        error: function(){
            alert("服务器跑到火星去了，登陆异常！");
        }
    })
}

function select(o){
    let id = o.getAttribute("for");
    if(id == type){
        return false;
    }
    o.parentElement.style.border = "1px solid #00d7c6";
    let radio = document.getElementById(type);
    radio.parentElement.style.border = "1px solid #9fa3b0";
    type = id;
}



//检查手机号的格式是否正确
function checkPhone(){
    var phone = document.getElementById('phone-number').value;
    if(phone == "" || phone == null){
        document.getElementById('err').innerHTML = "手机号不能为空";
        return false;
    }else if(!(/^1[34578]\d{9}$/.test(phone))){
        document.getElementById('err').innerHTML = "请填写正确的手机号";
        return false;
    }else{
        document.getElementById('err').innerHTML = "";
        return true;
    }
}


function checkPassword(){
    let password = document.getElementById("password").value;
    if(password == null || password == ""){
        document.getElementById("err").innerHTML = "密码不能为空";
        return false;
    }
    let confirm_PassWord = document.getElementById("confirm").value;
    if(password != confirm_PassWord){
        document.getElementById("err").innerHTML = "密码确认错误，从重新确认密码";
        return false;
    }
    return true;
}