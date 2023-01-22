
$(function(){
    $("#btn-modal").click(function(){
        $("body").css("overflow-y",'hidden');
        $(window).scrollTop(0);
        $(".modal-background").fadeIn(100);
        $(".modal-window").fadeIn(300);
    })
})

$(function(){
    $(".close-area").click(function(){
        $("body").css("overflow-y",'scroll');
        $(".modal-window").fadeOut(0);
        $(".modal-background").fadeOut(0);
    })
})

// const closeBtn = modal.querySelector(".close-area")
// closeBtn.addEventListener("click", e => {
//     modal.style.display = "none"
// })

// modal.addEventListener("click", e => {
//     const evTarget = e.target
//     if(evTarget.classList.contains("modal-overlay")) {
//         modal.style.display = "none"
//     }
// })  

// window.addEventListener("keyup", e => {
//     if(modal.style.display === "flex" && e.key === "Escape") {
//         modal.style.display = "none"
//     }
// })

// document.getElementById('send').onclick = function() {
//     var disabled = document.getElementById("confirm").disabled;
//     if (disabled) {
//         document.getElementById("confirm").disabled = false;
//     }
//     else {
//         document.getElementById("confirm").disabled = true;
//     }
// }

$(function(){
    $("#alert-success").hide();
    $("#alert-danger").hide();
    $("input").keyup(function(){
        var pwd1=$("#pwd1").val();
        var pwd2=$("#pwd2").val();
        if(pwd1 != "" || pwd2 != ""){
            if(pwd1 == pwd2){
                $("#alert-success").show();
                $("#alert-danger").hide();
                $("#submit").removeAttr("disabled");
            }else{
                $("#alert-success").hide();
                $("#alert-danger").show();
                $("#submit").attr("disabled", "disabled")
            }
        }
    });
});

