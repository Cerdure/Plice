$(function () {

    $(".login_repwd_btn").click(function () {
        $(".pwd_find_container").show().addClass("bg_white");
        $(".background").addClass("bg_black");
    });

    $(".fa-times").click(function () {
        $(".pwd_find_container").hide().removeClass("bg_white");
        $(".pwd_find_check_input").val("").css("border", "1px solid #c9d5ff").attr("readonly", false);
        $(".accept_opt").fadeOut(300);
        $(".accept_btn").attr("disabled", true);
        $(".background").removeClass("bg_black");
        $(".pwd_find_btn").css("background-color", "#dfdfdf").css("color", "white").html("인증번호 받기");
    });


    $(".pwd_find_container .pwd_find_check_input").on("input", function () {
        (async () => {
            const idRgx = /01[016789][0-9]{4}[0-9]{4}/;
            const idInput = document.querySelector(".pwd_find_container .pwd_find_check_input").value;
            const result = await fetch("/login/check?idInput=" + idInput).then(res => res.text());
            console.log(result);
            if (result != "ok") {
                $(".accept_opt").fadeOut(300);
                $(".pwd_find_check_input").css("border", "1px solid red");
                $(".pwd_find_btn").css("background-color", "red").css("color", "white").html("가입된 번호가 없습니다.");
            } else {
                const accNumber = await fetch("/login/send-message?phone=" + idInput).then(res => res.json());
                $(".accept_opt").fadeIn(300);
                $(".pwd_find_check_input").css("border", "1px solid #1a5ae8").attr("readonly", true);
                $(".pwd_find_btn").css("background-color", "#1a5ae8").css("color", "white").html("문자인증을 진행해주세요.");
                $(".pwd_find_container .accept_opt .accept_btn").attr("disabled", false).css("background-color", "#1a5ae8").css("color", "white");
                console.log("인증번호 - accNumber = " + accNumber);
                $(".pwd_find_container .accept_opt .accept_btn").click(function (e) {
                    e.preventDefault();
                    const userNum = $("#accept_code").val();
                    if (userNum == accNumber) {
                        alert("인증번호가 일치합니다.");
                        console.log("일치할 때 userNum = " + userNum);
                        location.href = "/login/update?phone=" + $(".pwd_find_container .pwd_find_check_input").val();
                    } else {
                        alert("인증번호가 일치하지 않습니다. 다시 입력해주세요.");
                        $("#accept_code").val("").focus();
                        console.log("일치하지 않을 때 userNum = " + userNum);
                    }
                    if (userNum == "") {
                        alert("인증번호를 입력해주세요");
                        $("#accept_code").val("").focus();
                    }
                })
            }
        })();
    });

    $(".login_btn").click(function (e) {
        e.preventDefault();
        const phone = document.querySelector("#login_id").value;
        const pw = document.querySelector("#login_pwd").value;
        if (phone == "") {
            Swal.fire({
                icon: 'warning',
                title: '아이디를 입력해주세요.'
            })
        } else {
            (async () => {
                const result = await fetch("/login/validate?phone=" + phone + "&pw=" + pw).then(res => res.json());
                if (result) {
                    Swal.fire({
                        icon: 'success',
                        title: '로그인에 성공하였습니다.'
                    });
                    $("html").click(function () {
                        $(".login_info").submit();
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '일치하는 정보가 없습니다.',
                        text: '다시 입력해주세요.'
                    });
                }
            })();
        }
    })

    $("#auto-login-btn").click(function () {
        if (!$(this).hasClass('clicked')) {
            $("#rememberMe").prop('checked', true);
            $(this).css('filter', 'none')
                .animate({ 'backgroundColor': 'rgb(101, 168, 255)' }, 200)
                .addClass('clicked');
        } else {
            $("#rememberMe").prop('checked', false);
            $(this).css('filter', 'grayscale(1) opacity(0.4)')
                .animate({ 'backgroundColor': 'white' }, 200)
                .removeClass('clicked');
        }
    });
});

function onlyNumber(obj) {
    obj.value = obj.value.replace(/[^0-9]/g, "");
}