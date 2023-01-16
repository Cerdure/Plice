$(function(){
    $("header .my-page").click(function(){
        (async () => {
            const loginCheck = await fetch("/chat/login-check").then(res => res.text());
            if(loginCheck == "ok"){
                location.href = "/my-page";
            } else {
                alert('로그인 후 이용 가능합니다.');
                location.href = "/login";
            }
        })();
    });
});