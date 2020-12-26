//应聘者

//跳转到应聘者主页面
function jumpToApplicantHomepage() {
    window.location.href="/applicant/jumpToApplicantHomepage?aid="+aid;
}


//跳转到招聘者信息管理页面
function jumpToApplicantData(aid) {
    window.location.href="/applicant/jumpToApplicantData?aid="+aid;
}

//跳转到查看职位信息页面
function jumpToPosition(aid,pid) {
    window.location.href="/applicant/jumpToPosition?aid="+aid+"&pid="+pid;
}

//招聘公司


//跳转到发布职位界面
function jumpToPostJob(cid) {
    window.location.href="/company/jumpToPostJob?cid="+cid;
}

//跳转到公司信息管理界面
function jumpToCompanyData(cid) {
    window.location.href="/company/jumpToCompanyData?cid="+cid;
}

//跳转到查看职位信息页面
function jumpToResume(pid,cid) {
    window.location.href="/company/jumpToResume?pid="+pid+"&cid="+cid;
}





//跳转到应聘者主页面
function jumpToCompanyHomepage(cid) {
    window.location.href="/company/jumpToCompanyHomepage?cid="+cid;
}



//下载文件

function fileDownload(aaid,apid) {
    window.location.href="/company/fileDownload?aaid="+aaid+"&apid="+apid;
}
