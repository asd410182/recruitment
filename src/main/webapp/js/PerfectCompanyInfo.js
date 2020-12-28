function Submit(){
    const UserName = document.querySelectorAll("#UserName")[1].value;
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
