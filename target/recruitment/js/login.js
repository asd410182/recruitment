window.onload = function(){
    var g = document.getElementsByTagName("input");
    for(let i=0; i<g.length; i++){
        g[i].addEventListener("focus", function(){
            this.parentElement.style.border = "1px solid #00d7c6";
        })
        g[i].addEventListener("focusout", function(){
            this.parentElement.style.border = "1px solid gainsboro";
        })
    }
}


function login(){
    if(!checkAccount()){
        return false;
    }
    if(!checkPassword()){
        return false;
    }
    console.log("可以登录了");
    let account = document.querySelector("#account").value;
    let password = document.querySelector("#password").value;

    $.ajax({
        method: "post",
        url: "/user/login",
        data: {
            'account': account,
            'pwd': password
        },
        success: function(res){
            if(res == "fail"){
                alert("账号或密码错误");
            }else{
                window.location.href = "/user/jump?uid="+res;
            }
        },
        error: function(){
            alert("服务器跑到火星去了，登陆异常！");
        }
    })
}

function checkAccount(){
    let account = document.getElementById("account").value;
    if(account == "" || account == null){
        document.getElementById("err").innerHTML = "账号不能为空";
        return false;
    }
    return true;
}

function checkPassword(){
    let password = document.getElementById("password").value;
    if(password == "" || password == null){
        document.getElementById("err").innerHTML = "密码不能为空";
        return false;
    }
    return true;
}