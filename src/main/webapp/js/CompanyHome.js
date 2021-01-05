var endResult;
let JobList;
const spanListId = [{"Salary": ""}, {"ExperienceRequirement": ""}, {"": ""}, {"AcademicRequirements": ""}];
let res;

let totalPage;
let currentPage;


window.onload = function(){
    let EditInfo = document.querySelector("#left");
    EditInfo.onclick = function(){
        window.location.href="/company/jumpToCompanyData?cid="+cid;
    };

    $.ajax({
        type: "post",
        url: "/company/showTheData",
        data:{
            "cid":cid
        },
        success: function (result) {
            res = result;
            endResult = result[5];
            document.querySelector("#UserName").innerText = result[0]["cname"];
            document.querySelector("#Company").innerText = result[0]["cname"];
            document.querySelector("#JobCount").innerText = result[1];
            document.querySelector("#ResumeCount").innerText = result[2];
            document.querySelector("#InterviewCount").innerText = result[4];
            JobList = result[5];
            totalPage = JobList.length;
            const ul = document.querySelector("#right > ul");

            document.querySelector("#currentPage").innerText = 1;
            //每页显示8条
            document.querySelector("#totalPage").innerText = Math.ceil(totalPage / 8);
            if(JobList.length == 0){
                alert("您还没有发布职位，请发布新的职位！");
                return;
            }
            JobList.forEach(function (job, index){
                if(index > 7){
                    return false;
                }
                const li = document.createElement("li");
                const Job = document.createElement("div");
                let divlist;
                for(let i=1; i<=5; i++){
                    const div = document.createElement("div");
                    if(i == 1){
                        const a = document.createElement("a");
                        a.setAttribute("href", `JavaScript:jumpToResume(endResult[${index}].pid,cid)`);
                        const JobTitle = document.createElement("span");
                        JobTitle.setAttribute("id", "JobTitle");
                        JobTitle.innerText = job.pname;
                        const WorkPlace = document.createElement("span");
                        WorkPlace.setAttribute("id", "WorkPlace");
                        const address = job.plocation.split(",")[1];
                        WorkPlace.innerText = `[${address.length > 3 ? address.substr(0, 3) + "..." : address}]`;
                        a.appendChild(JobTitle);
                        a.appendChild(WorkPlace);
                        div.appendChild(a);
                        const ReleaseTime = document.createElement("span");
                        ReleaseTime.id = "ReleaseTime";
                        const time = new Date(Date.parse(job.preleasetime.replace("-", "/")));
                        ReleaseTime.innerText = `发布于${time.getMonth()}月${time.getDay()}日`;
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
                        div.appendChild(ReleaseTime);
                        div.appendChild(p);
                        Job.appendChild(div);
                        continue;
                    }
                    if(i == 2){
                        div.className = "ListBoxRight";
                        Job.appendChild(div);
                        divlist = div;
                        continue;
                    }else if(i == 4 || i == 5){
                        div.className = "radio";
                        const input = document.createElement("input");
                        input.type = "radio";
                        input.name = `state${index}`;
                        input.id = i == 4 ? `TurnOn${index}` : `TurnOff${index}`;
                        input.value = i == 4 ? 1 : 0;
                        if(i == 4 && Number(job.pisopen)){
                            div.style.background = "#53cac3";
                            input.checked = Number(job.pisopen);
                        }else if(i == 5 && !Number(job.pisopen)){
                            div.style.background = "#53cac3";
                            input.checked = !Number(job.pisopen);
                        }
                        const label = document.createElement("label");
                        label.setAttribute("for", input.id);
                        label.setAttribute("pid", job.pid);
                        label.setAttribute("onclick", "select(this)");
                        label.innerText = i == 4 ? "开放" : "关闭";
                        div.appendChild(input);
                        div.appendChild(label);
                    }else if(i == 3){
                        const a = document.createElement("a");
                        a.id = "getResume";
                        a.innerText = "未处理简历";
                        a.setAttribute("href", "#");
                        const span = document.createElement("span");
                        span.id = "count";
                        if(result[6][index] == 0){
                            span.style.background = "white";
                        }else {
                            span.innerText = result[6][index];
                        }
                        div.appendChild(a);
                        div.appendChild(span);
                    }
                    divlist.appendChild(div);
                }
                Job.className = "Job";
                li.appendChild(Job);
                ul.appendChild(li);
            })
        },
        error: function(){
            alert("服务器跑到火星去了，请稍后再试！");
        }
    });
};

function select(o){

    let preSelect;

    let bro = o.previousElementSibling;
    let radios = document.querySelectorAll(`input[name=${bro.name}]`);
    for (const radio of radios) {
        if(radio.checked){
            preSelect = radio.id;
        }
    }

    o.setAttribute("for", bro.id);
    let flag = o.innerText;
    let currentFor = o.getAttribute("for");
    o.removeAttribute("for");
    const pisopen = flag == "开放" ? 1 : 0;
    const pid = o.getAttribute("pid");

    //这里要拿到选中的单选框的id

    if(currentFor == preSelect){
        return;
    }else {
        if(confirm("确定更改该职位的发布状态吗？")){
            $.ajax({
                type: "post",
                url: "/company/updateJobStatus",
                async: false,
                data: {
                    "pisopen": pisopen,
                    "pid": pid
                },
                success: function (res, code){
                    o.setAttribute("for", currentFor);
                    for (const val of radios) {
                        const cflag = val.value;
                        if(cflag != res){
                            val.parentElement.style.background = "white";
                            continue;
                        }
                        val.parentElement.style.background = "#53cac3";
                    }
                },
                error: function (){
                    alert("服务器出错了，请待会再试！");
                }
            })
        }else {
            return;
        }
    }
}

function showInfo(page){
    let ul = document.querySelector("#right > ul");
    let pObjs = ul.childNodes;
    for(let i=pObjs.length - 1; i>=0; i--){
        ul.removeChild(pObjs[i]);
    }

    for(let j=(page - 1) * 8; j<page * 8; j++){
        const li = document.createElement("li");
        const Job = document.createElement("div");
        if(j > JobList.length - 1){
            break;
        }
        let job = JobList[j];
        let divlist;
        for(let i=1; i<=5; i++){
            const div = document.createElement("div");
            if(i == 1){
                const a = document.createElement("a");
                a.setAttribute("href", `JavaScript:jumpToResume(endResult[${j}].pid,cid)`);
                const JobTitle = document.createElement("span");
                JobTitle.setAttribute("id", "JobTitle");
                JobTitle.innerText = job.pname;
                const WorkPlace = document.createElement("span");
                WorkPlace.setAttribute("id", "WorkPlace");
                const address = job.plocation.split(",")[1];
                WorkPlace.innerText = `[${address.length > 3 ? address.substr(0, 3) + "..." : address}]`;
                a.appendChild(JobTitle);
                a.appendChild(WorkPlace);
                div.appendChild(a);
                const ReleaseTime = document.createElement("span");
                ReleaseTime.id = "ReleaseTime";
                const time = new Date(Date.parse(job.preleasetime.replace("-", "/")));
                ReleaseTime.innerText = `发布于${time.getMonth() + 1}月${time.getDate()}日`;
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
                div.appendChild(ReleaseTime);
                div.appendChild(p);
                Job.appendChild(div);
                continue;
            }
            if(i == 2){
                div.className = "ListBoxRight";
                Job.appendChild(div);
                divlist = div;
                continue;
            }else if(i == 4 || i == 5){
                div.className = "radio";
                const input = document.createElement("input");
                input.type = "radio";
                input.name = `state${j}`;
                input.id = i == 4 ? `TurnOn${j}` : `TurnOff${j}`;
                input.value = i == 4 ? 1 : 0;
                if(i == 4 && Number(job.pisopen)){
                    div.style.background = "#53cac3";
                    input.checked = Number(job.pisopen);
                }else if(i == 5 && !Number(job.pisopen)){
                    div.style.background = "#53cac3";
                    input.checked = !Number(job.pisopen);
                }
                const label = document.createElement("label");
                label.setAttribute("for", input.id);
                label.setAttribute("pid", job.pid);
                label.setAttribute("onclick", "select(this)");
                label.innerText = i == 4 ? "开放" : "关闭";
                div.appendChild(input);
                div.appendChild(label);
            }else if(i == 3){
                const a = document.createElement("a");
                a.id = "getResume";
                a.innerText = "未处理简历";
                a.setAttribute("href", "#");
                const span = document.createElement("span");
                span.id = "count";
                if(res[6][j] == 0){
                    span.style.background = "white";
                }else {
                    span.innerText = result[6][j];
                }
                div.appendChild(a);
                div.appendChild(span);
            }
            divlist.appendChild(div);
        }
        Job.className = "Job";
        li.appendChild(Job);
        ul.appendChild(li);
    }
}

function changePage(o){
    const currentPage = Number(document.querySelector("#currentPage").innerText);
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
    let text = document.querySelector("#search").value;
    if(text == "" || text == null){
        return;
    }else {
        text = text.trim();
        JobList.forEach(function (job, index){
            const simratio = similar(job.pname, text);
            job["similar"] = simratio;
        });

        JobList.sort(function (a, b){
            return b.similar - a.similar;
        });

        showInfo(1);
    }
}

