$(function () {

    (async () => {
        const loginCheck = await fetch("/chat/login-check").then(res => res.text());
        if (loginCheck == "ok") {
        } else {
            alert('로그인 후 이용 가능합니다.');
            location.href = "/login";
        }
    })();

    $(document).on("click", ".modify-btn", function () {
        fetch("/my-page", {
            method: 'post',
            cache: 'no-cache',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: $(".profile_wrap").serialize()
        }).then(res => {
            if (res.json()) {
                alert("수정되었습니다.");
                location.reload();
            } else {
                alert("요청이 실패하였습니다.");
            }
        });
    });

    $(document).on("click", ".leave-open-btn", function () {
        $("body").css("overflow-y", 'hidden');
        $(window).scrollTop(0);
        $(".modal-background").fadeIn(100);
        $(".modal-window").css('display', 'flex').animate({ 'opacity': '1' }, 300);
    });

    $(document).on("click", ".leave-btn", function () {
        fetch("/leave").then(res => {
            if (res.json()) {
                alert("탈퇴되었습니다.");
                location.href = "/leave/after";
            } else {
                alert("요청이 실패하였습니다.");
            }
        });
    });

})

$(function () {
    $(".close").click(function () {
        $("body").css("overflow-y", 'scroll');
        $(".modal-window").animate({ 'opacity': '0' }, 300, () => { $(".modal-window").hide() });
        $(".modal-background").fadeOut(300);
    });
});

$(function () {
    $("#alert-success").hide();
    $("#alert-danger").hide();
    $("input").keyup(function () {
        var pwd1 = $("#pwd1").val();
        var pwd2 = $("#pwd2").val();
        if (pwd1 != "" || pwd2 != "") {
            if (pwd1 == pwd2) {
                $("#alert-success").show();
                $("#alert-danger").hide();
                $("#submit").removeAttr("disabled");
            } else {
                $("#alert-success").hide();
                $("#alert-danger").show();
                $("#submit").attr("disabled", "disabled")
            }
        }
    });
});

