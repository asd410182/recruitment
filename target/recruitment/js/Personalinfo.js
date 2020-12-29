window.onload = function(){

    let aid = "";

    let inputs = document.querySelectorAll("input[type='text']");
    inputs = Array.from(inputs);
    for(const val of inputs){
        val.disabled = "disabled";
        val.style.background = "white";
    }

    let selects = document.querySelectorAll("select");
    selects = Array.from(selects);
    for(const val of selects){
        val.disabled = "disabled";
        val.style.background = "white";
        val.style.color = "black";
    }

    $.ajax({
        type: "post",
        url: "/applicant/showMyData",
        data: {
            "aid": aid
        },
        success: function (res, code) {
            console.log(res);//应聘者信息
        },
        error: function () {
            alert("服务器跑到太阳上去了，请稍后再试！");
        }
    })

}

function Edit(o){
    o.style.display = "none";

    let reset = document.querySelector("input[type='reset']");
    let submit = document.querySelector("input[type='submit']");
    let cancel = document.querySelector("#cancel");

    reset.style.display = "inline-block";
    submit.style.display = "inline-block";
    cancel.style.display = "inline-block";

    let inputs = document.querySelectorAll("input[type='text']");
    inputs = Array.from(inputs);
    for(const val of inputs){
        val.removeAttribute("disabled");
    }

    let selects = document.querySelectorAll("select");
    selects = Array.from(selects);
    for(const val of selects){
        val.removeAttribute("disabled");
    }
}

function Cancel(o) {

    document.querySelector(".NameError").style.visibility = "hidden";
    document.querySelector(".PhoneError").style.visibility = "hidden";
    document.querySelector(".EmailError").style.visibility = "hidden";

    let reset = document.querySelector("input[type='reset']");
    let submit = document.querySelector("input[type='submit']");

    reset.style.display = "none";
    submit.style.display = "none";
    o.style.display = "none";

    let edit = document.querySelector("#edit");
    edit.style.display = "inline-block";

    let inputs = document.querySelectorAll("input[type='text']");
    inputs = Array.from(inputs);
    for(const val of inputs){
        val.setAttribute("disabled", true);
    }

    let selects = document.querySelectorAll("select");
    selects = Array.from(selects);
    for(const val of selects){
        val.setAttribute("disabled", true);
    }
}

function Submit() {
    const UserName = document.querySelectorAll("#UserName")[1].value;
    const PhoneNumber = document.querySelector("#PhoneNumber").value;
    const Email = document.querySelector("#Email").value;

    if(UserName == "" || UserName == null){
        document.querySelector(".NameError").style.visibility = "visible";
        return false;
    }else{
        document.querySelector(".NameError").style.visibility = "hidden";
    }

    if(PhoneNumber == "" || PhoneNumber == null){
        document.querySelector(".PhoneError").style.visibility = "visible";
        return false;
    }else{
        document.querySelector(".PhoneError").style.visibility = "hidden";
    }

    if(!(/^1[34578]\d{9}$/.test(PhoneNumber) || /^400[0-9]{7}/.test(PhoneNumber) || /^800[0-9]{7}/.test(PhoneNumber) || /^0[0-9]{2,3}-[0-9]{8}/.test(PhoneNumber))){
        document.querySelector(".PhoneError").innerText = "请填写正确的手机号";
        document.querySelector(".PhoneError").style.visibility = "visible";
        return false;
    }else{
        document.querySelector(".PhoneError").style.visibility = "hidden";
    }

    var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;

    if(Email == "" || Email == null){
        document.querySelector(".EmailError").style.visibility = "visible";
        return false;
    }else{
        document.querySelector(".EmailError").style.visibility = "hidden";
    }

    if(!reg.test(Email)){
        document.querySelector(".EmailError").innerText = "请填写正确的邮箱";
        document.querySelector(".EmailError").style.visibility = "visible";
        return false;
    }else{
        document.querySelector(".EmailError").style.visibility = "hidden";
    }

    return true;
}

function Reset(){
    document.querySelector(".NameError").style.visibility = "hidden";
    document.querySelector(".PhoneError").style.visibility = "hidden";
    document.querySelector(".EmailError").style.visibility = "hidden";
}