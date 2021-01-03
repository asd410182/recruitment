let aid ;
let pid ;

function change(o){
    document.querySelector("#resubmit").innerText = "重新选择";
    let file = "";
    if(o.files[0] == undefined || o.files[0] == null){
        document.querySelector("#resume").style.visibility = "hidden";
        return;
    }
    file = o.files[0].name;
    let len = file.length;
    document.querySelector("#resume").innerText = len > 8 ? `${file.substr(0, 8)}***` : file;
    document.querySelector("#resume").style.visibility = "visible";
}

function getFile(o){
    if(confirm("只能上传一次简历，你确定要上传简历吗？")){
        let file = "";
        file = document.querySelector("#PostResume").files[0];

        var formData = new FormData();
        console.log(aid);
        console.log(pid);
        console.log(file);
        formData.append("aaid", aid);
        formData.append("apid", pid);
        formData.append("astatus", "未处理");
        formData.append("files", file);
        console.log(formData);
        console.log(formData.get("aaid"));
        console.log(formData.get("apid"));
        console.log(formData.get("astatus"));
        console.log(formData.get("files"));

        console.log(document.querySelector("#PostResume"));
        console.log(document.querySelector("#posta"));

        $.ajax({
            url: "/applicant/fileUpload",
            type: "post",
            async: true,
            cache: false,
            data: formData,
            dataType: "json",
            processData: false,
            mimeType: "multipart/form-data",
            success: function (res){
                console.log(res);
                o.style.display = "none";
                document.querySelector("#PostResume").disabled = true;
                document.querySelector("#posta").disabled = true;
                document.querySelector("#resume").style.right = "110px";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest);
                console.log(textStatus);
                console.log(errorThrown);
            }
        })
    }else{
        return;
    }
}

window.onload = function (){
    aid = window.location.search.split("&")[0];
    aid = aid.split("=")[1];
    console.log(aid);
    pid = window.location.search.split("=")[2];
    console.log(pid);


    $.ajax({
        url: "/applicant/submitPosition",
        type: "post",
        data: {
            "pid": pid
        },
        success: function (res){
            console.log(res);
            document.querySelector("#city").innerText = `${res[0].plocation.split(",")[1]}·`;
            document.querySelector("#ExperienceRequirement").innerText = `${res[0].pexperience}·`;
            document.querySelector("#AcademicRequirements").innerText = `${res[0].pacademic}`;

            document.querySelector("#JobName").innerText = res[0].pname;
            document.querySelector("#JobSalary").innerText = res[0].psalary.replace(",", "-");
            document.querySelector("#JobDescription").innerText = res[0].pcontent;
            document.querySelector("#OperatingDuty").innerText = res[0].pneed;
            document.querySelector("#location").innerText = res[0].plocation.replace(",", "");
            document.querySelector("#CompanyIntro").innerText = res[1].cintroduction;
            document.querySelector("#PhoneNumber").innerText = res[1].cphone;
            document.querySelector("#Email").innerText = res[1].cemail;
        },
        error: function (res){
            alert("服务器出错了，请稍后再试！");
        }
    })
}