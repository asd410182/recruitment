//应聘者

//跳转到查看职位信息页面
function jumpToPosition(aid,pid) {
    window.location.href="/pages/ResumeDelivery.html?aaid="+aid+"&apid="+pid;
}


//下载文件
function fileDownload(aid,pid) {
    window.location.href="/company/fileDownload?aaid="+aid+"&apid="+pid;
}
