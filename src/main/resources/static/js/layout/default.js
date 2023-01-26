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
        seletBtn = $(this);
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
        seletBtn = $(this);
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
    seletBtn,
    type;

function formSubmit(_this) {
    const form = $(_this).closest("form");
    const textareas = form.find("textarea").get();
    textareas.forEach(e => { e.value = e.value.replace(/\n/g, "<br>") });
    form.submit();
}

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
            await fetch("/admin/block?blockType=" + type
                + (type == "ip" ? "&ip=" + selectIp : "&id=" + selectId) + ""
                + "&date=" + $("#block-date option:selected").val()
                + "&reason=" + $("#block-reason option:selected").val())
                .then(res => {
                    if (res.json()) {
                        alert(type == 'ip' ? "IP가 차단되었습니다." : "계정이 차단되었습니다.")
                    } else {
                        alert("요청이 실패하였습니다.");
                    }
                });
            seletBtn.removeClass("block-btn").addClass("block-cancel-btn");
            seletBtn.text("차단됨");
            $(".alert-window").hide();
            $(".modal-background").hide();
        } else {
            alert("이미 차단된 " + (type == "ip" ? "IP" : "계정") + "입니다.");
        }
    })();
}

function blockCancel() {
    (async () => {
        await fetch("/admin/block-cancel?blockType=" + type
            + (type == "ip" ? "&ip=" + selectIp : "&id=" + selectId))
            .then(res => {
                if (res.json()) {
                    alert("차단이 해제되었습니다.")
                    location.reload();
                } else {
                    alert("요청이 실패하였습니다.");
                }
            });
        seletBtn.removeClass("block-cancel-btn").addClass("block-btn");
        seletBtn.text(type == 'ip' ? "IP 차단" : "계정 차단");
        $(".alert-window").hide();
        $(".modal-background").hide();
    })();
}