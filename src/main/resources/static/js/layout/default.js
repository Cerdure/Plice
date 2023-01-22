$(function () {

    $(document).on("click", ".block-btn", function () {
        switch ($(this).data("type")) {
            case "ip":
                type = "ip";
                selectIp = $(this).closest(".row").find("#ip").data("value");
                $(".block-alert .data").text("IP : " + selectIp);
                $(".block-alert .text").text("해당 IP를 차단하겠습니까?");
                break;
            case "member":
                type = "member";
                selectId = $(this).closest(".row").find("#member-id").data("value");
                $(".block-alert .data").text("회원 관리 ID : " + selectId);
                $(".block-alert .text").text("해당 계정을 차단하겠습니까?");
                break;
        }
        $(".modal-background").fadeIn(100);
        $(".block-alert").fadeIn(300);
    });

    $(document).on("click", ".block-cancel-btn", function () {
        switch ($(this).data("type")) {
            case "ip":
                type = "ip";
                selectIp = $(this).closest(".row").find("#ip").data("value");
                $(".block-cancel-alert .data").text("IP : " + selectIp);
                break;
            case "member":
                type = "member";
                selectId = $(this).closest(".row").find("#member-id").data("value");
                $(".block-cancel-alert .data").text("회원 관리 ID : " + selectId);
                break;
        }
        $(".modal-background").fadeIn(100);
        $(".block-cancel-alert").fadeIn(300);
    });

    $("header .my-page").click(function () {
        (async () => {
            const loginCheck = await fetch("/chat/login-check").then(res => res.text());
            if (loginCheck == "ok") {
                location.href = "/my-page";
            } else {
                alert('로그인 후 이용 가능합니다.');
                location.href = "/login";
            }
        })();
    });

    const adminNavIdx = $(".admin-nav .index").get();

    if (adminNavIdx.length != 0) {
        adminNavIdx.forEach(e => {
            e.classList.remove("active-index");
            if (e.querySelector(".text").innerHTML == $("#page-title").data("value")) {
                e.classList.add("active-index");
            }
        });
    }

});

let selectIp,
    selectId,
    type;

function authorityCheck(page) {
    (async () => {
        const authority = await fetch("/admin/authority-check?page=" + page).then(res => res.json());
        if (authority) {
            switch (page) {
                case "admin": location.href = "/admin/admin-mng"; break;
                case "member": location.href = "/admin/member-mng"; break;
                case "chat": location.href = "/admin/chat-mng"; break;
                case "post": location.href = "/admin/post-mng/story"; break;
                case "inquiry": location.href = "/admin/inquiry-mng"; break;
            }
        } else {
            alert("권한이 없습니다.");
        }
    })();
}

function block() {
    (async () => {
        const notBlocked = await fetch("/admin/block-check?blockType=" + type + (type == "ip" ? "&ip=" + selectIp : "&id=" + selectId))
            .then(res => res.json());
        if (notBlocked) {
            location.href = "/admin/block?blockType=" + type
                + (type == "ip" ? "&ip=" + selectIp : "&id=" + selectId) + ""
                + (type == "ip" ? "&pageType=accessIp" : "&pageType=accessMember")
                + "&date=" + $("#block-date option:selected").val()
                + "&reason=" + $("#block-reason option:selected").val();
        } else {
            alert("이미 차단된 " + (type == "ip" ? "IP" : "계정") + "입니다.");
        }
    })();
}

function blockCancel() {
    location.href = "/admin/block-cancel?blockType=" + type
        + (type == "ip" ? "&ip=" + selectIp : "&id=" + selectId) + ""
        + (type == "ip" ? "&pageType=accessIp" : "&pageType=accessMember");
}