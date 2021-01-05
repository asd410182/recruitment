let DefaultUserName;
let DefaultAge;
let DefaultEdu;
let DefaultStatus;
let DefaultPhone;
let DefaultEmail;
let aid;

window.onload = function(){
    aid = window.location.search.split("=")[1];
    console.log(aid);
    document.querySelector("input[name='aid']").value = aid;

    document.querySelector("#home").href = "./ApplicantHome.html?aid="+aid;
    document.querySelector("#personal").href = `./Personalinfo.html?aid=${aid}`;
    document.querySelector("#resumelist").href = `./ResumeList.html?aid=${aid}`;

    $.ajax({
        url: "/applicant/showMyData",
        type: "post",
        async: false,
        data: {
            "aid": aid
        },
        success: function (res){
            console.log(res);
            // console.log(document.querySelectorAll("#UserName"));
            // document.querySelectorAll("#UserName")[0].innerText = res[0].aname;
            document.querySelectorAll("#UserName")[1].value = res[0].aname;
            document.querySelector(".UserName").innerText = res[0].aname;
            DefaultUserName = res[0].aname;
            document.querySelector(".UserAccount").innerText = res[0].aid;

            document.querySelector("#UserAge").value = res[0].aage;
            DefaultAge = res[0].aage;
            let selectEdu = document.querySelector("#UserEdu");
            for(let i=0; i<selectEdu.options.length; i++){
                if(selectEdu.options[i].innerText == res[0].aeducation){
                    selectEdu.options[i].selected = true;
                    break;
                }
            }
            // document.querySelector("#UserEdu").value = res[0].aeducation;
            DefaultEdu = res[0].aeducation;
            console.log(DefaultEdu);
            // document.querySelector("#JobSS").value = res[0].ajobstatus;
            let selectStatus = document.querySelector("#JobSS");
            for(let i=0; i<selectEdu.options.length; i++){
                if(selectStatus.options[i].innerText == res[0].ajobstatus){
                    selectStatus.options[i].selected = true;
                    break;
                }
                selectStatus.options[i].selected = false;
            }
            DefaultStatus = res[0].ajobstatus;
            document.querySelector("#PhoneNumber").value = res[0].aphone;
            DefaultPhone = res[0].aphone;
            document.querySelector("#Email").value = res[0].aemail;
            DefaultEmail = res[0].aemail;
        },
        error: function (err){
            alert("服务器出错了，请稍后重试！");
        }
    })

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
}

function Edit(o){
    o.style.display = "none";

    let reset = document.querySelector("input[type='reset']");
    let submit = document.querySelector("input[type='submit']");

    reset.style.display = "inline-block";
    submit.style.display = "inline-block";

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

function Reset(){
    document.querySelector(".NameError").style.visibility = "hidden";
    document.querySelector(".PhoneError").style.visibility = "hidden";
    document.querySelector(".EmailError").style.visibility = "hidden";
    document.querySelectorAll("#UserName")[1].setAttribute("value", DefaultUserName);
    // console.log(DefaultUserName);
    // console.log(document.querySelectorAll("#UserName")[1]);
    document.querySelector("#UserAge").setAttribute("value", DefaultAge);
    console.log(DefaultEdu);
    // document.querySelector("#UserEdu").setAttribute("value", DefaultEdu);
    let selectEdu = document.querySelector("#UserEdu");
    for(let i=0; i<selectEdu.options.length; i++){
        if(selectEdu.options[i].innerText == DefaultEdu){
            selectEdu.options[i].selected = true;
            break;
        }
        selectEdu.options[i].selected = false;
    }
    // document.querySelector("#JobSS").setAttribute("value", DefaultStatus);
    let selectStatus = document.querySelector("#JobSS");
    for(let i=0; i<selectEdu.options.length; i++){
        if(selectStatus.options[i].innerText == DefaultStatus){
            selectStatus.options[i].selected = true;
            break;
        }
        selectStatus.options[i].selected = false;
    }
    document.querySelector("#PhoneNumber").setAttribute("value", DefaultPhone);
    document.querySelector("#Email").setAttribute("value", DefaultEmail);

    return false;
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

function Exit(){
    if(confirm("您确定要退出系统吗？")){
        top.location = "../index.html";
        return false;
    }
}