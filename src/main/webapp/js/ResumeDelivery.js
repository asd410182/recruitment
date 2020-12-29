function getFile(o){

    if(confirm("只能上传一次简历，你确定要上传简历吗？")){
        let file = "";
        file = o.files[0].name;
        let len = file.length;
        document.querySelector("#resume").innerText = len > 8 ? `${file.substr(0, 8)}***` : file;
        o.parentElement.disabled = true;
        o.disabled = true;
    }else{
        return;
    }
}