let result;
let JobList;
const spanListId = [{"Salary": ""}, {"ExperienceRequirement": ""}, {"": ""}, {"AcademicRequirements": ""}];

window.onload = function(){

    let aid = window.location.search.split("=")[1];

    let EditInfo = document.querySelector(".left");
    EditInfo.onclick = function(){
        window.location.href="/pages/Personalinfo.html?aid="+aid;
    };

    document.querySelector("#home").href = "./ApplicantHome.html?aid="+aid;
    document.querySelector("#personal").href = `./Personalinfo.html?aid=${aid}`;
    document.querySelector("#resumelist").href = `./ResumeList.html?aid=${aid}`;

    $.ajax({
        type: "post",
        url: "/applicant/applicantData",
        data: {
            "aid": aid
        },
        success: function(res){
            console.log(res);
            console.log(res[0]);//招聘者信息
            console.log(res[1]);//已经通过简历的数量
            console.log(res[2]);//已经拒绝简历的数量
            console.log(res[3]);//等待面试简历的数量
            console.log(res[4]);//所有简历的数量
            console.log(res[5]);//所有职位
            console.log(res[6]);//所有职位对应的公司
            result = res;
            JobList = res[5];

            // document.querySelectorAll("#UserName")[0].innerText = res[0].aname;
            document.querySelectorAll("#UserName")[1].innerText = res[0].aname;

            const totalPage = document.querySelector("#totalPage");
            totalPage.innerText = Math.ceil(JobList.length / 8);
            console.log(Math.ceil(JobList.length / 8));
            document.querySelector("#currentPage").innerText = 1;

            document.querySelector("#JobCount").innerText = res[4];
            document.querySelector("#InterviewCount").innerText = res[3];
            document.querySelector("#OfferCount").innerText = res[2];
            const ul = document.querySelector("ul");
            JobList.forEach((job, index) => {
                //一个页面最多显示8条
                if(index > 7){
                    return false;
                }
                const li = document.createElement("li");
                const JobDiv = document.createElement("div");
                JobDiv.className = "Job";
                const CompanyDiv = document.createElement("div");
                CompanyDiv.className = "Company";

                const a = document.createElement("a");
                a.href = `Javascript:jumpToPosition(${aid},${job["pid"]})`;
                // a.href = `Javascript:/applicant/jumpToPosition?pid=${job["pid"]}&cid=${res[6][index]["cid"]}`;
                const JobTitle = document.createElement("span");
                JobTitle.id = "JobTitle";
                JobTitle.innerText = job.pname;
                const WorkPlace = document.createElement("span");
                WorkPlace.id = "WorkPlace";
                const city = job.plocation.split(",")[1];
                WorkPlace.innerText = city.length > 4 ? `[${city.substr(0, 4)}]` : `[${city}]`;
                a.appendChild(JobTitle);
                a.appendChild(WorkPlace);
                JobDiv.appendChild(a);

                const ReleaseTime = document.createElement("span");
                ReleaseTime.id = "ReleaseTime";
                const time = new Date(Date.parse(job.preleasetime.replace("-", "/")));
                ReleaseTime.innerText = `发布于${time.getMonth() + 1}月${time.getDate()}日`;
                JobDiv.appendChild(ReleaseTime);

                const p = document.createElement("p");
                p.className = "ListBoxLeft";
                spanListId["Salary"] = job.psalary.replace(",", "-");
                spanListId["ExperienceRequirement"] = job.pexperience;
                spanListId["AcademicRequirements"] = job.pacademic;
                let spanList = [];
                for(let i=0; i<4; i++){
                    const span = document.createElement("span");
                    spanList.push(span);
                    spanList[i].id = Object.keys(spanListId[i])[0];
                }
                spanList.forEach(function (span){
                    if(spanListId[span.id] == "" || spanListId[span.id] == undefined){
                        span.innerText = "";
                    }else {
                        span.innerText = spanListId[span.id];
                    }
                    p.appendChild(span);
                })

                JobDiv.appendChild(p);

                const CompanyName = document.createElement("p");
                CompanyName.id = "CompanyName";
                CompanyName.innerText = res[6][index].cname;
                const CompanyAddress = document.createElement("p");
                CompanyAddress.id = "CompanyAddress";
                CompanyAddress.innerText = res[6][index].caddress;

                CompanyDiv.appendChild(CompanyName);
                CompanyDiv.appendChild(CompanyAddress);

                li.appendChild(JobDiv);
                li.appendChild(CompanyDiv);
                ul.appendChild(li);
            })
        },
        error: function(){
            alert("服务器出错了，请刷新重试！");
        }
    })
}

function similar(s, t, f) {
    if (!s || !t) {
        return 0
    }
    var l = s.length > t.length ? s.length : t.length
    var n = s.length
    var m = t.length
    var d = []
    f = f || 3
    var min = function(a, b, c) {
        return a < b ? (a < c ? a : c) : (b < c ? b : c)
    }
    var i, j, si, tj, cost
    if (n === 0) return m
    if (m === 0) return n
    for (i = 0; i <= n; i++) {
        d[i] = []
        d[i][0] = i
    }
    for (j = 0; j <= m; j++) {
        d[0][j] = j
    }
    for (i = 1; i <= n; i++) {
        si = s.charAt(i - 1)
        for (j = 1; j <= m; j++) {
            tj = t.charAt(j - 1)
            if (si === tj) {
                cost = 0
            } else {
                cost = 1
            }
            d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost)
        }
    }
    let res = (1 - d[n][m] / l)
    return res.toFixed(f)
}

function search(){
    let content = document.querySelector("#search").value;
    //去除字符串中所有的空格
    content = content.replace(/\s*/g, "");
    console.log(content);
    if(content == "" || content == null){
        return;
    }
    const selected = document.querySelector("#select");
    const index = selected.selectedIndex;
    const type = selected.options[index].value;

    if(type == "职位"){
        JobList.forEach(function (job, index){
            const simratio = similar(job.pname, content);
            job["similar"] = simratio;
            result[6][index]["similar"] = simratio;
        });
    }else {
        JobList.forEach(function (job, index){
            const simratio = similar(result[6][index].cname, content);
            job["similar"] = simratio;
            result[6][index]["similar"] = simratio;
        })
    }
    JobList.sort(function (a, b){
        return b.similar - a.similar;
    });
    result[6].sort(function (a, b){
        return b.similar - a.similar;
    })

    showInfo(1);
}

document.onkeydown = function (event) {
    var e = event || window.event;
    if(e && e.keyCode == 13){
        search();
    }
};

function showInfo(page){
    let ul = document.querySelector("ul");
    let pObjs = ul.childNodes;
    for(let i=pObjs.length - 1; i>=0; i--){
        ul.removeChild(pObjs[i]);
    }

    for(let j=(page - 1) * 8; j<page * 8; j++){

        if(j > JobList.length - 1){
            break;
        }
        let job = JobList[j];

        const li = document.createElement("li");
        const JobDiv = document.createElement("div");
        JobDiv.className = "Job";
        const CompanyDiv = document.createElement("div");
        CompanyDiv.className = "Company";

        const a = document.createElement("a");
        a.href = `Javascript:jumpToPosition(${aid},${job["pid"]})`;
        const JobTitle = document.createElement("span");
        JobTitle.id = "JobTitle";
        JobTitle.innerText = job.pname;
        const WorkPlace = document.createElement("span");
        WorkPlace.id = "WorkPlace";
        const city = job.plocation.split(",")[1];
        WorkPlace.innerText = city.length > 4 ? `[${city.substr(0, 4)}]` : `[${city}]`;
        a.appendChild(JobTitle);
        a.appendChild(WorkPlace);
        JobDiv.appendChild(a);

        const ReleaseTime = document.createElement("span");
        ReleaseTime.id = "ReleaseTime";
        const time = new Date(Date.parse(job.preleasetime.replace("-", "/")));
        ReleaseTime.innerText = `发布于${time.getMonth() + 1}月${time.getDate()}日`;
        JobDiv.appendChild(ReleaseTime);

        const p = document.createElement("p");
        p.className = "ListBoxLeft";
        spanListId["Salary"] = job.psalary.replace(",", "-");
        spanListId["ExperienceRequirement"] = job.pexperience;
        spanListId["AcademicRequirements"] = job.pacademic;
        let spanList = [];
        for(let i=0; i<4; i++){
            const span = document.createElement("span");
            spanList.push(span);
            spanList[i].id = Object.keys(spanListId[i])[0];
        }
        spanList.forEach(function (span){
            if(spanListId[span.id] == "" || spanListId[span.id] == undefined){
                span.innerText = "";
            }else {
                span.innerText = spanListId[span.id];
            }
            p.appendChild(span);
        })

        JobDiv.appendChild(p);

        const CompanyName = document.createElement("p");
        CompanyName.id = "CompanyName";
        CompanyName.innerText = result[6][j].cname;
        const CompanyAddress = document.createElement("p");
        CompanyAddress.id = "CompanyAddress";
        CompanyAddress.innerText = result[6][j].caddress;

        CompanyDiv.appendChild(CompanyName);
        CompanyDiv.appendChild(CompanyAddress);

        li.appendChild(JobDiv);
        li.appendChild(CompanyDiv);
        ul.appendChild(li);
    }
}

function changePage(o){
    const currentPage = Number(document.querySelector("#currentPage").innerText);
    const totalPage = Number(document.querySelector("#totalPage").innerText);
    if(o.id == "prePage"){
        if(currentPage == 1) return;
        else{
            showInfo(currentPage - 1);
            document.querySelector("#currentPage").innerText = currentPage - 1;
        }
    }else if(o.id == "backPage"){
        if(currentPage == totalPage) return;
        else{
            showInfo(currentPage + 1);
            document.querySelector("#currentPage").innerText = currentPage + 1;
        }
    }else{
        showInfo(1);
        document.querySelector("#currentPage").innerText = 1;
    }
}

function Exit(){
    if(confirm("您确定要退出系统吗？")){
        top.location = "../index.html";
        return false;
    }
}