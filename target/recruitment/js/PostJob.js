let pisopen = "TurnOn";
(function setDefault(){

    var prov = document.querySelector("#province");
    let len = provice.length;
    for(let i=0; i<len; i++){
        var provOpt = document.createElement("option");
        provOpt.innerText = provice[i]["name"];
        provOpt.setAttribute("index", i);
        provOpt.value = provice[i]["name"];
        prov.appendChild(provOpt);
    }

    let checkedRadio = document.getElementById(pisopen);
    checkedRadio.parentElement.style.background = "#00c2b3";
})();


function select(o){
    let id = o.getAttribute("for");
    if(id != pisopen){
        document.querySelector(`#${pisopen}`).parentElement.style.background = "white";
        document.querySelector(`#${id}`).parentElement.style.background = "#00c2b3";
        pisopen = id;
    }
}

function showCity(o){
    var city = document.querySelector("#city");

    let val = o.options[o.selectedIndex].getAttribute("index");
    city.length = 1;
    
    if(val != ""){
        var cityLen = provice[val]["city"].length;
        for(let i=0; i<cityLen; i++){
            var cityOpt = document.createElement("option");
            cityOpt.innerText = provice[val]["city"][i].name;
            cityOpt.setAttribute("index", i);
            cityOpt.value = provice[val]["city"][i].name;
            city.appendChild(cityOpt);
        }
    }
}

function getTextLen(o){
    setInterval(function(){
        let newvalue = o.value.replace(/[^\x00-\xff]/g, "**");
        // console.log(newvalue);
        if(newvalue.length >= 0){
            if(newvalue.length > 1000){
                //TODO:这里想想应该怎么处理
                o.value = o.value.substr(0, 500);
            }else{
                document.getElementsByClassName(o.id)[0].innerText = parseInt((1000 - newvalue.length)/2);
            }
        }
    }, 1000);
}

getTextLen(document.querySelector("#JobDescription"));
getTextLen(document.querySelector("#OperatingDuty"));

function check(){

    let err = document.querySelector("#error");
    const JobName = document.querySelector("#JobName").value;
    const province = document.querySelector("#province");
    const city = document.querySelector("#city");
    
    const Address = province.options[province.selectedIndex].text + city.options[city.selectedIndex].text + document.querySelector("#location").value;
    const ExperienceRequirement = document.querySelector("#ExperienceRequirement");
    const Experience = ExperienceRequirement.options[ExperienceRequirement.selectedIndex].text;
    const AcademicRequirement = document.querySelector("#AcademicRequirements");
    const Academic = AcademicRequirement.options[AcademicRequirement.selectedIndex].text;
    let min = document.querySelector("#MinSalary");
    const MinSalary = min.options[min.selectedIndex].text;
    let max = document.querySelector("#MaxSalary");
    const MaxSalary = max.options[max.selectedIndex].text;

    const JobDescription = document.querySelector("#JobDescription").value;
    const OperatingDuty = document.querySelector("#OperatingDuty").value;

    if(JobName == "" || JobName == undefined){
        error.innerText = "职位名称不能为空";
        err.style.visibility = "visible";
        setTimeout(function(){error.style.visibility = "hidden"}, 2000);
        event.preventDefault();
        return false;
    }

    if(province.selectedIndex == 0 || city.selectedIndex == 0){
        error.innerText = "请选择工作城市";
        error.style.visibility = "visible";
        setTimeout(function(){error.style.visibility = "hidden"}, 2000);
        event.preventDefault();
        return false;
    }
    if(Number(MinSalary.substr(0, MinSalary.length - 1)) > Number(MaxSalary.substr(0, MaxSalary.length - 1))){
        error.innerText = "请选择合适的薪资范围";
        error.style.visibility = "visible";
        setTimeout(function(){error.style.visibility = "hidden"}, 2000);
        event.preventDefault();
        return false;
    }

    if(JobDescription == "" || JobDescription == undefined){
        error.innerText = "请填写职位描述";
        error.style.visibility = "visible";
        setTimeout(function(){error.style.visibility = "hidden"}, 2000);
        event.preventDefault();
        return false;
    }
    
    if(OperatingDuty == "" || OperatingDuty == undefined){
        error.innerText = "请填写任职要求";
        error.style.visibility = "visible";
        setTimeout(function(){error.style.visibility = "hidden"}, 2000);
        event.preventDefault();
        return false;
    }

    // let radios = document.querySelectorAll("input[type='radio']");
    // for(let i=0; i<radios.length; i++){
    //     if(radios[i].checked){
    //         console.log(radios[i].id);
    //     }
    // }
    return true;
}