let aid;
let pid;

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

function getFile(){
    if(confirm("只能上传一次简历，你确定要上传简历吗？")){
        return true;
    }else{
        return false;
    }
}

window.onload = function (){
    aid = window.location.search.split("&")[0];
    aid = aid.split("=")[1];
    console.log(aid);
    pid = window.location.search.split("=")[2];
    console.log(pid);

    document.querySelector("#aaid").value = aid;
    document.querySelector("#apid").value = pid;

    $.ajax({
        url: "/applicant/showPositionAndResume",
        type: "post",
        data: {
            "pid": pid,
            "aid": aid
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
            if(res[2] !== "没有投递过简历"){
                document.querySelector("form").style.visibility = "hidden";
                document.querySelector("#resume").innerText = "您已经投递过简历了";
                document.querySelector("#resume").style.visibility = "visible";
            }
        },
        error: function (res){
            alert("服务器出错了，请稍后再试！");
        }
    })
}