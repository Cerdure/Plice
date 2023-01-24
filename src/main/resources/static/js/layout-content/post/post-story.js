$(function () {

    $(".search").mouseover(function () {
        $(".search .reset").stop().fadeIn(200);
    }).mouseleave(function () {
        $(".search .reset").stop().fadeOut(200);
    });

    $(".search .reset").click(function () {
        location.href = "/post/story";
    });

    $(".story-write-btn").click(function () {
        (async () => {
            const loginCheck = await fetch("/chat/login-check").then(res => res.text());
            if (loginCheck == "ok") {
                $(".story-write-wrapper").fadeIn(300);
            } else {
                alert('로그인 후 이용 가능합니다.');
                location.href = "/login";
            }
        })();
    });

    $(".write-form .close").click(function () {
        $(".close-alert-wrapper").fadeIn(300);
    });


    $(document).on("keyup", ".title-input", function () {
        let val = $(this).val();
        if (val != '') {
            headerPassed = true;
        } else {
            headerPassed = false;
        }
        registCheck(headerPassed, mainPassed);
    });

    $(document).on("keyup", ".content-input", function () {
        let val = $(this).val();
        $(".write-form-wrapper .count").text(val.length + "/3000");
        if (val.length > 9) {
            mainPassed = true;
        } else {
            mainPassed = false;
        }
        registCheck(headerPassed, mainPassed);
    });

});

let headerPassed = false;
let mainPassed = false;

function registCheck(...passed) {
    if (passed.every(e => { return e; })) {
        $(".regist-button").removeClass("disable");
    } else {
        $(".regist-button").addClass("disable");
    }
}

function formClose() {
    $(".close-alert-wrapper").fadeOut(100);
    $(".write-form-wrapper").fadeOut(100);
    $(".write-form input").val("");
    $(".write-form .count").text("0/3000");
    headerPassed = false;
    mainPassed = false;
}

function hideAlert() {
    $(".close-alert-wrapper").fadeOut(100);
}
