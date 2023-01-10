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

function joinDD(obj) {
  obj.value = obj.value.replace(/[^0-9]/g, "");
}

// 아이디 - 전화번호
$("#join_id").on("keyup", function () {
  const idRgx = /^[0-9]{11}$/;
  const idInput = document.querySelector("#join_id").value;
  if (idRgx.test(idInput)) {
    $(".id_success_text").css("display", "inline");
    $(".id_failed_text").css("display", "none");
  } else {
    $(".id_success_text").css("display", "none");
    $(".id_failed_text").css("display", "inline");
  }
  if (idInput == "") {
    $(".id_success_text").css("display", "none");
    $(".id_failed_text").css("display", "inline").html("번호를 입력해주세요.");
  }
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
    $(".name_failed_text")
      .css("display", "inline")
      .html("이름을 입력해주세요.");
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
    $(".nick_failed_text")
      .css("display", "inline")
      .html("닉네임을 입력해주세요.");
  }
});

// 비밀번호
$("#join_pwd").on("keyup", function () {
  const pwdRgx =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
  const idRgx = /^[0-9]{4,15}$/;
  const pwdInput = document.querySelector("#join_pwd").value;
  if (!pwdRgx.test(pwdInput)) {
    if (idRgx.test(pwdInput)) {
      $(".pwd_success_text").css("display", "none");
      $(".pwd_failed_text")
        .css("display", "inline")
        .html("숫자만 입력할 수 없습니다.");
    } else {
      $(".pwd_success_text").css("display", "none");
      $(".pwd_failed_text").css("display", "inline").html("다시 작성해주세요.");
    }
  } else {
    $(".pwd_success_text")
      .css("display", "inline")
      .html("정상 확인 되었습니다.");
    $(".pwd_failed_text").css("display", "none");
  }
  if (pwdInput == "") {
    $(".pwd_success_text").css("display", "none");
    $(".pwd_failed_text")
      .css("display", "inline")
      .html("비밀번호를 작성해주세요.");
  }
});

// 비밀번호 확인
$("#join_repwd").on("keyup", function () {
  const pwdInput = document.querySelector("#join_pwd").value;
  const repwd = document.querySelector("#join_repwd").value;
  if (pwdInput != repwd) {
    $(".repwd_success_text").css("display", "none");
    $(".repwd_failed_text")
      .css("display", "inline")
      .html("비밀번호가 일치하지 않습니다.");
  } else {
    $(".repwd_success_text")
      .css("display", "inline")
      .html("비밀번호가 일치합니다.");
    $(".repwd_failed_text").css("display", "none");
  }
  if (pwdInput == "") {
    $(".repwd_failed_text")
      .css("display", "inline")
      .html("비밀번호부터 입력하세요.");
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
  const yyReg = /^[0-9]{4}$/;
  if (!yyReg.test(yy)) {
    $(".yy_success_text").css("display", "none");
    $(".yy_failed_text")
      .css("display", "inline")
      .css("fontSize", "12px")
      .html("다시 입력");
  } else {
    $(".yy_failed_text").css("display", "none");
    $(".yy_success_text")
      .css("display", "inline")
      .css("fontSize", "12px")
      .html("정상 확인");
  }
  if (yy == "") {
    $(".yy_success_text").css("display", "none");
    $(".yy_failed_text")
      .css("display", "inline")
      .css("fontSize", "12px")
      .html("년도입력");
  }
});

// 월
$("#join_sel").click(function () {
  const mm = $("#join_sel option:selected").val();
  if (mm == "00") {
    $(".mm_success_text").css("display", "none");
    $(".mm_failed_text").css("display", "inline").html("월 선택");
  } else {
    $(".mm_success_text").css("display", "inline").html("선택 완료");
    $(".mm_failed_text").css("display", "none");
  }
});

// 일
$("#dd").on("keyup", function () {
  const dd = document.querySelector("#dd").value;
  const ddReg = /^[0-9]{1,2}$/;
  if (!ddReg.test(dd)) {
    $(".dd_success_text").css("display", "none");
    $(".dd_failed_text")
      .css("display", "inline")
      .css("fontSize", "12px")
      .html("다시 입력");
  } else {
    $(".dd_failed_text").css("display", "none");
    $(".dd_success_text")
      .css("display", "inline")
      .css("fontSize", "12px")
      .html("정상 확인");
  }
  if (dd == "") {
    $(".dd_success_text").css("display", "none");
    $(".dd_failed_text")
      .css("display", "inline")
      .css("fontSize", "12px")
      .html("일자 입력");
  }
});

// 최종가입
