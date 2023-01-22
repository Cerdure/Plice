// 비밀번호
$(function () {
  $("#reset_pw").on("input", function () {
    const pwdRgx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
    const pwdInput = document.querySelector("#reset_pw").value;
    const repwd = document.querySelector("#reset_repw").value;
    if (!pwdRgx.test(pwdInput)) {
      $("#reset_pw").css("border", "1px solid red");
      $(".new_pw").css("color", "red").html("조건에 부합하지 않습니다.");
      if (!pwdRgx.test(pwdInput) && repwd != "") {
        $(".new_repw").css("color", "red").html("비밀번호가 일치하지 않습니다.");
        $("#reset_repw").css("border", "1px solid red");
      }
    } else if (pwdRgx.test(pwdInput) && pwdInput === repwd) {
      $("#reset_repw").css("border", "1px solid #1a5ae8");
      $(".new_repw").css("color", "#1a5ae8").html("비밀번호 일치 확인");
      $("#reset_pw").css("border", "1px solid #1a5ae8");
      $(".new_pw").css("color", "#1a5ae8").html("정상 확인");
    } else if (pwdRgx.test(pwdInput)) {
      $("#reset_pw").css("border", "1px solid #1a5ae8");
      $(".new_pw").css("color", "#1a5ae8").html("정상 확인");
    }

    if (pwdInput == "") {
      $("#reset_pw").css("border", "1px solid #1a5ae8");
      $(".new_pw").html("");
    }
  });

  // 비밀번호 확인
  $("#reset_repw").on("keyup", function () {
    const pwdInput = document.querySelector("#reset_pw").value;
    const repwd = document.querySelector("#reset_repw").value;
    if (pwdInput != repwd) {
      $("#reset_repw").css("border", "1px solid red");
      $(".new_repw").css("color", "red").html("비밀번호와 일치하지 않습니다.");
    } else {
      $("#reset_repw").css("border", "1px solid #1a5ae8");
      $(".new_repw").css("color", "#1a5ae8").html("비밀번호 일치 확인");
    }
    if (pwdInput == "") {
      $(".new_repw").css("color", "red").html("비밀번호부터 입력해주세요.");
      $("#reset_repw").css("border", "1px solid rgba(0, 0, 0, 0.1)");
      $("#reset_repw").val("");
      $("#reset_pw").focus();
    }
  });

  // 활성화 버튼
  $(".reset_input").on("keyup", function () {
    const pwdRgx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
    const pwdInput = document.querySelector("#reset_pw").value;
    const repwd = document.querySelector("#reset_repw").value;
    if (pwdRgx.test(pwdInput) && pwdInput == repwd) {
      $(".reset_btn").attr("disabled", false).css("background-color", "#326CF9");
    } else {
      $(".reset_btn").attr("disabled", true).css("background-color", "#dfdfdf");
    }
  });
});
