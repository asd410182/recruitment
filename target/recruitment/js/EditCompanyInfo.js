let inputs = document.querySelectorAll("input[type='text']");
inputs = Array.from(inputs);
let textarea = document.querySelector("textarea");
let DefaultUserName;
let DefaultPhoneNumber;
let DefaultEmail;
let DefaultLocation;
let DefalutCompanyIntro;

let reset = document.querySelector("#reset");
let submit = document.querySelector("#submit");
let cancel = document.querySelector("#cancel");
let edit = document.querySelector("#edit");

window.onload = function(){

    let inputs = document.querySelectorAll("input[type='text']");
    inputs = Array.from(inputs);
    for(const val of inputs){
        val.disabled = "disabled";
        val.style.background = "white";
    }
    $.ajax({
        type: "post",
        url: "/company/companyData",
        data:{
            "cid":cid
        },
        success: function (result) {
            let names = document.querySelectorAll("#UserName");
            for (const name of names) {
                name.innerText = result.cname;
            }
            names[2].value = result.cname;
            document.querySelector("#PhoneNumber").value = result.cphone;
            document.querySelector("#Email").value = result.cemail;
            document.querySelector("#Location").value = result.caddress;
            document.querySelector("#CompanyIntro").value = result.cintroduction;

            let textarea = document.querySelector("textarea");
            DefaultUserName = document.querySelectorAll("#UserName")[2].value;
            DefaultPhoneNumber = document.querySelector("#PhoneNumber").value;
            DefaultEmail = document.querySelector("#Email").value;
            DefaultLocation = document.querySelector("#Location").value;
            DefalutCompanyIntro = document.querySelector("#CompanyIntro").value;
            textarea.disabled = "disabled";
            textarea.style.background = "white";
        },
        error: function(){
            alert("服务器跑到火星去了，请稍后再试！");
        }
    });
}

function Edit(o){

    for(const val of inputs){
        val.removeAttribute("disabled");
    }
    textarea.removeAttribute("disabled");
    textarea.removeAttribute("style");
    textarea.className = "CIntro";
    o.style.display = "none";

    reset.style.display = "inline-block";
    submit.style.display = "inline-block";
    cancel.style.display = "inline-block";
}

function Reset(){
    document.querySelectorAll("#UserName")[2].value = DefaultUserName;
    document.querySelector("#PhoneNumber").value = DefaultPhoneNumber;
    document.querySelector("#Email").value = DefaultEmail;
    document.querySelector("#Location").value = DefaultLocation;
    document.querySelector("#CompanyIntro").value = DefalutCompanyIntro;
}

function Cancel(){
    Reset();
    for(const val of inputs){
        val.setAttribute("disabled", true);
    }
    textarea.setAttribute("disabled", true);
    textarea.removeAttribute("class");
    textarea.style.background = "white";

    reset.style.display = "none";
    submit.style.display = "none";
    cancel.style.display = "none";
    edit.style.display = "inline-block";

    const ErrorId = ["#UserNameError", "#PhoneNumberError", "#EmailError", "#LocationError", "#CompanyIntroError"];
    ErrorId.forEach(function(item){
        document.querySelector(item).innerText = "";
    })
}

//声明一个对象，把检测方法写到里面

function Submit(){
    const UserName = document.querySelectorAll("#UserName")[2].value;
    const PhoneNumber = document.querySelector("#PhoneNumber").value;
    const Email = document.querySelector("#Email").value;
    const Location = document.querySelector("#Location").value;
    const CompanyIntro = document.querySelector("#CompanyIntro").value;
    // const ErrorId = ["#UserNameError", "#PhoneNumberError", "#EmailError", "#LocationError", "#CompanyIntroError"]
    let err;

    if(UserName == "" || UserName == undefined){
        err = document.querySelector("#UserNameError");
        err.innerText = "企业名称不允许为空";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#UserNameError");
        err.style.visibility = "hidden";
    }
    if(PhoneNumber == "" || PhoneNumber == undefined){
        err = document.querySelector("#PhoneNumberError");
        err.innerText = "手机号不允许为空";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#PhoneNumberError");
        err.style.visibility = "hidden";
    }
    if(!(/^1[34578]\d{9}$/.test(PhoneNumber) || /^400[0-9]{7}/.test(PhoneNumber) || /^800[0-9]{7}/.test(PhoneNumber) || /^0[0-9]{2,3}-[0-9]{8}/.test(PhoneNumber))){
        err = document.querySelector("#PhoneNumberError");
        err.innerText = "请输入正确的手机号";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#PhoneNumberError");
        err.style.visibility = "hidden";
    }
    var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(Email == "" || Email == undefined){
        err = document.querySelector("#EmailError");
        err.innerText = "邮箱不允许为空";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#EmailError");
        err.style.visibility = "hidden";
    }

    if(!reg.test(Email)){
        err = document.querySelector("#EmailError");
        err.innerText = "请填写正确的邮箱";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#EmailError");
        err.style.visibility = "hidden";
    }

    if(Location == "" || Location == undefined){
        err = document.querySelector("#LocationError");
        err.innerText = "请填写企业地址";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#LocationError");
        err.style.visibility = "hidden";
    }

    if(CompanyIntro == "" || Location == undefined){
        err = document.querySelector("#CompanyIntroError");
        err.innerText = "公司简介不能为空";
        err.style.visibility = "visible";
        event.preventDefault();
        return false;
    }else{
        err = document.querySelector("#CompanyIntroError");
        err.style.visibility = "hidden";
    }

    return true;
}