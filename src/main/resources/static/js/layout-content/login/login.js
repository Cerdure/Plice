// JavaScript
function onlyNumber(obj) {
  obj.value = obj.value.replace(/[^0-9]/g, "");
}


// Jquery
$(function () {
  $(".login_repwd_btn").click(function () {
    $(".pwd_find_container").show().addClass("bg_white");
    $(".background").addClass("bg_black");
  });

  $(".fa-times").click(function () {
      $(".pwd_find_container").hide().removeClass("bg_white");
      $(".pwd_find_check_input").val("").css("border","1px solid #c9d5ff").attr("readonly", false);
      $(".accept_opt").fadeOut(300); // 0.3초
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
     if(result != "ok") {    // 일치하는 번호가 없음
         $(".accept_opt").fadeOut(300); // 0.3초
         $(".pwd_find_check_input").css("border", "1px solid red");
         $(".pwd_find_btn").css("background-color", "red").css("color", "white").html("가입된 번호가 없습니다.");
     }else{   // 일치함
         /*fetch("/login/send-message?phone=" + idInput).then(res => console.log(res.text()));*/
         const accNumber = await fetch("/login/send-message?phone=" + idInput).then(res => res.json());  // 인증번호 저장
         $(".accept_opt").fadeIn(300); // 0.3초
         $(".pwd_find_check_input").css("border", "1px solid #1a5ae8").attr("readonly", true);
         $(".pwd_find_btn").css("background-color", "#1a5ae8").css("color", "white").html("문자인증을 진행해주세요.");
         $(".pwd_find_container .accept_opt .accept_btn").attr("disabled", false).css("background-color", "#1a5ae8").css("color", "white");
         console.log("인증번호 - accNumber = " + accNumber);
         $(".pwd_find_container .accept_opt .accept_btn").click(function(e) {
            e.preventDefault();
            const userNum = $("#accept_code").val();    // 유저가 입력한 인증번호
            if(userNum == accNumber) {
                alert("인증번호가 일치합니다.");
                console.log("일치할 때 userNum = " + userNum);
                location.href = "/login/update?phone=" + $(".pwd_find_container .pwd_find_check_input").val();
            } else {
                alert("인증번호가 일치하지 않습니다. 다시 입력해주세요.");
                $("#accept_code").val("").focus();
                console.log("일치하지 않을 때 userNum = " + userNum);
            }
            if(userNum == "") {
                alert("인증번호를 입력해주세요");
                $("#accept_code").val("").focus();
            }
         })
     }
   })();
 });

$(".login_btn").click(function(e) {
    e.preventDefault();
    const loginId = document.querySelector("#login_id").value;
    const loginPwd = document.querySelector("#login_pwd").value;
    if(loginId == "") {
        Swal.fire({
            icon: 'warning',
            title: '아이디를 등록해주세요....'
        })
    } else if(loginId != "") {
        (async () => {
            const result = await fetch("/login/check?idInput=" + loginId).then(res => res.text());
            console.log("result = " + result);
            console.log("loginId = " + loginId);
            if(result != "ok") {    // 등록된 회원이 없다.
                Swal.fire({
                  icon: 'error',
                  title: '등록된 회원이 아닙니다....',
                  text: '다시 입력해주세요!'
                })
                $("#login_id").val("");
                $("#login_pwd").val("");
            } else {
                Swal.fire({
                  icon: 'success',
                  title: '로그인에 성공하였습니다!!'
                })
                $(".swal2-confirm").click(function() {
                    $(".login_info").submit();
                })
            }
        })();
    }
})

});
