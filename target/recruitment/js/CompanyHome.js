var endResult;
let JobList;
const spanListId = [{"Salary": ""}, {"ExperienceRequirement": ""}, {"": ""}, {"AcademicRequirements": ""}];


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
            endResult = result[5];
            // console.log(result[0]);
            document.querySelector("#UserName").innerText = result[0]["cid"];
            document.querySelector("#JobCount").innerText = result[1];
            document.querySelector("#ResumeCount").innerText = result[2];
            document.querySelector("#InterviewCount").innerText = result[4];
            JobList = result[5];
            const ul = document.querySelector("#right > ul");
            JobList.forEach(function (job, index){
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
                        spanListId["Salary"] = job.psalary;
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
                        input.id = i == 4 ? "TurnOn" : "TurnOff";
                        input.checked = job.pisopen;
                        if(i == 4 && input.checked){
                            div.style.borderColor = "#53cac3";
                        }else if(i == 5 && !input.checked){
                            div.style.borderColor = "#53cac3";
                        }
                        const label = document.createElement("label");
                        label.setAttribute("for", input.id);
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
    let id = o.getAttribute("for");
    //这里要拿到选中的单选框的id
    let bro = o.previousElementSibling;
    let radios = document.querySelectorAll(`input[name=${bro.name}]`);
    for (const val of radios) {
        if(val.id != id){
            val.parentElement.style.borderColor = "#9fa3b0";
            continue;
        }
        val.parentElement.style.borderColor = "#53cac3";
    }
}