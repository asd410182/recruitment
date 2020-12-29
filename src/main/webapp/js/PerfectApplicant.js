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