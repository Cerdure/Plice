// JavaScript
function joinId(obj) {
  obj.value = obj.value.replace(/[^0-9]/g, "");
}

function joinName(obj) {
  obj.value = obj.value.replace(/[^ㄱ-ㅎ|가-힣]/g, "");
}

function joinNick(obj) {
  obj.value = obj.value.replace(/[^ㄱ-ㅎ|가-힣|a-z|A-Z]/g, "");
}

function joinYY(obj) {
  obj.value = obj.value.replace(/[^0-9]/g, "");
}

// 아이디 - 전화번호
$("#join_id").on("keyup", function () {
  const idRgx = /01[016789][^0][0-9]{3,4}[0-9]{3,4}/;
  const idInput = document.querySelector("#join_id").value;
  if (idRgx.test(idInput)) {
    $(".id_success_text").css("display", "inline").html("정상 확인");
    $(".id_failed_text").css("display", "none");
    $(".join_id_label .id_btn").removeClass("disable");
    $(".join_id_label .id_btn").addClass("able");
  } else {
    $(".id_success_text").css("display", "none");
    $(".id_failed_text").css("display", "inline").html("다시 작성해주세요.");
    $(".join_id_label .id_btn").removeClass("able");
    $(".join_id_label .id_btn").addClass("disable");
  }
  if (idInput == "") {
    $(".id_success_text").css("display", "none");
    $(".id_failed_text").css("display", "inline").html("번호를 입력해주세요.");
    $(".join_id_label .id_btn").removeClass("able");
    $(".join_id_label .id_btn").addClass("disable");
  }
});

// 인증번호 버튼 클릭
$(".join_id_label .id_btn").click(function (e) {
  e.preventDefault();
  $("#join_form .accept_number").fadeIn(500);
  $(".join_id_label .id_btn").html("전송중").css({ "background-color": "rgb(172, 0, 92)" });
  // 타이머
  let time = 180; // 기준 시간
  let min = ""; // 분
  let sec = ""; // 초

  let timer = setInterval(function () {
    min = String(parseInt(time / 60)).padStart(2, "0"); // 몫을 계산
    sec = String(time % 60).padStart(2, "0"); // 나머지 계산

    document.querySelector("#timer").innerHTML = min + ":" + sec;
    time--;

    $("#join_form .accept_btn").click(function () {
      clearInterval(timer);
    });

    // 타임 아웃 시
    if (time < 0) {
      clearInterval(timer); // setInterval() 실행을 끝냄
      document.querySelector(".join_id_label .id_btn").innerHTML = "시간초과";
      $(".join_id_label .id_btn").css({ "background-color": "black" });
      $("#join_form .accept_btn").addClass("disable");
    }
  }, 1000);
  $(".join_id_label .id_btn").css({ "pointer-events": "none" });
  $("#join_form #join_id").attr("readonly", true);
});

// 인증번호 확인 버튼
$("#join_form .accept_btn").click(function (e) {
  e.preventDefault();
  $("#join_form .accept_number").fadeOut(500);
  $(".join_id_label .id_btn").removeClass("able");
  $(".join_id_label .id_btn").html("인증완료").css({ backgroundColor: "#326cf9", color: "white" }).addClass("disable");
  $("#join_form #join_id").attr("readonly", true);
});

// 이름
$("#join_name").on("keyup", function () {
  const nameRgx = /^[가-힣]{2,3}$/;
  const nameInput = document.querySelector("#join_name").value;
  if (nameRgx.test(nameInput)) {
    $(".name_success_text").css("display", "inline");
    $(".name_failed_text").css("display", "none");
  } else {
    $(".name_success_text").css("display", "none");
    $(".name_failed_text").css("display", "inline");
  }
  if (nameInput == "") {
    $(".name_success_text").css("display", "none");
    $(".name_failed_text").css("display", "inline").html("이름을 입력해주세요.");
  }
});

// 닉네임
$("#join_nick").on("keyup", function () {
  const nickRgx = /^[가-힣|a-z|A-Z]{3,10}$/;
  const nickInput = document.querySelector("#join_nick").value;
  if (nickRgx.test(nickInput)) {
    $(".nick_success_text").css("display", "inline");
    $(".nick_failed_text").css("display", "none");
  } else {
    $(".nick_success_text").css("display", "none");
    $(".nick_failed_text").css("display", "inline");
  }
  if (nickInput == "") {
    $(".nick_success_text").css("display", "none");
    $(".nick_failed_text").css("display", "inline").html("닉네임을 입력해주세요.");
  }
});

// 비밀번호
$("#join_pwd").on("keyup", function () {
  const pwdRgx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
  const idRgx = /^[0-9]{4,15}$/;
  const pwdInput = document.querySelector("#join_pwd").value;
  const repwd = document.querySelector("#join_repwd").value;
  if (!pwdRgx.test(pwdInput)) {
    if (idRgx.test(pwdInput)) {
      $(".pwd_success_text").css("display", "none");
      $(".pwd_failed_text").css("display", "inline").html("숫자만 입력할 수 없습니다.");
    } else {
      $(".pwd_success_text").css("display", "none");
      $(".pwd_failed_text").css("display", "inline").html("다시 작성해주세요.");
    }
  } else {
    $(".pwd_success_text").css("display", "inline").html("정상 확인 되었습니다.");
    $(".pwd_failed_text").css("display", "none");
  }
  if (pwdInput == "") {
    $(".pwd_success_text").css("display", "none");
    $(".pwd_failed_text").css("display", "inline").html("비밀번호를 작성해주세요.");
  }
});

// 비밀번호 확인
$("#join_repwd").on("keyup", function () {
  const pwdInput = document.querySelector("#join_pwd").value;
  const repwd = document.querySelector("#join_repwd").value;
  if (pwdInput != repwd) {
    $(".repwd_success_text").css("display", "none");
    $(".repwd_failed_text").css("display", "inline").html("비밀번호가 일치하지 않습니다.");
  } else {
    $(".repwd_success_text").css("display", "inline").html("비밀번호가 일치합니다.");
    $(".repwd_failed_text").css("display", "none");
  }
  if (pwdInput == "") {
    $(".repwd_failed_text").css("display", "inline").html("비밀번호부터 입력하세요.");
    $("#join_repwd").val("");
    $("#join_pwd").focus();
  }

  if (repwd == "") {
    $(".repwd_success_text").css("display", "none");
    $(".repwd_failed_text").css("display", "inline");
  }
});

// 출생년도
$("#yy").on("keyup", function () {
  const yy = document.querySelector("#yy").value;
  const yyReg = /^[0-9]{6}$/;
  if (!yyReg.test(yy)) {
    $(".yy_success_text").css("display", "none");
    $(".yy_failed_text").css("display", "inline").css("fontSize", "12px").html("다시 입력");
  } else {
    $(".yy_failed_text").css("display", "none");
    $(".yy_success_text").css("display", "inline").css("fontSize", "12px").html("정상 확인");
  }
  if (yy == "") {
    $(".yy_success_text").css("display", "none");
    $(".yy_failed_text").css("display", "inline").css("fontSize", "12px").html("년도입력");
  }
});

// 최종가입

$(function () {
  $(".update_btn").on("keyup", function () {
    const idRgx = /01[016789][^0][0-9]{3,4}[0-9]{3,4}/;
    const nameRgx = /^[가-힣]{2,3}$/;
    const nickRgx = /^[가-힣|a-z|A-Z]{3,10}$/;
    const pwdRgx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
    const yyReg = /^[0-9]{6}$/;

    let join_id = $("#join_form #join_id").val();
    let join_name = $("#join_form #join_name").val();
    let join_nick = $("#join_form #join_nick").val();
    let join_pwd = $("#join_form #join_pwd").val();
    let join_repwd = $("#join_form #join_repwd").val();
    let join_yy = $("#join_form #yy").val();
    const join_btn = $("#join_form .join_btn");

    console.log(join_yy);

    if (idRgx.test(join_id) && nameRgx.test(join_name) && nickRgx.test(join_nick) && pwdRgx.test(join_pwd) && join_pwd == join_repwd && yyReg.test(join_yy)) {
      join_btn.attr("disabled", false);
    } else {
      join_btn.attr("disabled", true);
    }
  });
});
