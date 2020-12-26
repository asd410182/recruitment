window.onload = function(){
    let inputs = document.querySelectorAll("input[type='text']");
    inputs = Array.from(inputs);
    for(const val of inputs){
        val.disabled = "disabled";
        val.style.background = "white";
    }

    let selects = document.querySelectorAll("select");
    selects = Array.from(selects);
    for(const val of selects){
        val.disabled = "disabled";
        val.style.background = "white";
        val.style.color = "black";
    }
}

function Edit(o){
    o.style.display = "none";
    console.log("11111111111");

    let reset = document.querySelector("input[type='reset']");
    let submit = document.querySelector("input[type='submit']");

    console.log("333333333");
    reset.style.display = "inline-block";
    submit.style.display = "inline-block";

    let inputs = document.querySelectorAll("input[type='text']");
    inputs = Array.from(inputs);
    for(const val of inputs){
        val.removeAttribute("disabled");
    }

    let selects = document.querySelectorAll("select");
    selects = Array.from(selects);
    for(const val of selects){
        val.removeAttribute("disabled");
    }
}