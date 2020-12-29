window.onload = function(){

    let aid = "1";

    let EditInfo = document.querySelector(".left");
    EditInfo.onclick = function(){
        window.location.href="/applicant/jumpToApplicantData?aid="+aid;
    };

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
        },
        error: function(){
            alert("服务器出错了，请刷新重试！");
        }
    })
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

    $.ajax({
        type: "post",
        url: "/applicant/searchPosition",
        data: {
            "type":type,//公司或者职位字符串
            "content": content
        },
        success: function (res) {
            console.log(res);
            console.log(res[0]);//所有符合条件的职位
            console.log(res[1]);//所有符合条件的职位对应的公司
        },
        error: function () {
            console.log("服务器跑到太阳上去了，请稍后再试！");
        }
    })
}

document.onkeydown = function (event) {
    var e = event || window.event;
    if(e && e.keyCode == 13){
        search();
    }
};