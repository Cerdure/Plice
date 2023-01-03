$(function () {
  $(".clear_parent").hover(
    function () {
      $(this).children(".clear_btn").stop().fadeIn(300);
    },
    function () {
      $(this).children(".clear_btn").stop().fadeOut(100);
    }
  );
  $(".clear_btn").click(function () {
    $(this).parent().find("input").val("").focus();
  });

  $(".phone").on("keyup focus", function () {
    const rgx1 = /^[0-9]{0,11}$/;
    const rgx2 = /^[0-9]{11}$/;
    let val = $(this).val();
    if (!rgx1.test(val)) {
      $(this).val(val.substr(0, val.length - 1));
    }
    if (rgx2.test(val)) {
      $(".member_join_phone_btn").css({
        "pointer-events": "all",
        color: "rgb(98,98,98)",
      });
    } else {
      $(".member_join_phone_btn").css({
        "pointer-events": "none",
        color: "rgb(170, 170, 170)",
      });
    }
    join();
  });

  let timerInterval;
  $(".member_join_phone_btn").click(function () {
    let time = 180;
    clearInterval(timerInterval);
    $(".timer").hide().text("03:00");
    $(".timer").css("color", "red").css("font-weight", "bold");
    if (!$(this).hasClass("clicked")) {
      $(this)
        .hide()
        .text("발송 완료")
        .css({
          "pointer-events": "none",
          "line-height": "40px",
          color: "rgb(170,170,170)",
        })
        .addClass("clicked")
        .fadeIn(300);
      $(".timer").fadeIn(300);
      $(".verify")
        .show()
        .animate({ height: "60px" }, 200, function () {
          $(".code-check").fadeIn(300);
        });
      timerInterval = setInterval(function () {
        time--;
        $(".timer").text(
          "0" +
            Math.floor(time / 60) +
            ":" +
            (time % 60 < 10 ? "0" + (time % 60) : time % 60)
        );
        if (time == 180) {
          $(".member_join_phone_btn")
            .text("다시 보내기")
            .css({ "pointer-events": "all", color: "rgb(98,98,98)" })
            .removeClass("clicked");
        } else if (time <= 0) {
          $(".timer").text("시간 만료");
        }
      }, 1000);
    }
  });
  $(".code-check").click(function () {
    $(".verify").animate({ height: "0" }, 300, function () {
      $(".verify").hide();
    });
    clearInterval(timerInterval);
    $(".code_number")
      .hide()
      .text("인증 완료")
      .css({
        "pointer-events": "none",
        "line-height": "60px",
        color: "rgb(170,170,170)",
      })
      .addClass("clicked")
      .fadeIn(300);
    $(".timer").hide();
    // $('.phone').attr('disabled','true');

    $(".phone, .code").parent().addClass("passed");
    $(".phone").parent().find(".clear_btn").remove();
    join();
  });

  $("#pwd").on("keyup", function () {
    const pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/;
    const pwd = document.querySelector("#pwd").value;

    if (!pwdReg.test(pwd)) {
      $("#pwd").css("border", "1px solid red");
      $(".pwd_mis").css("display", "block");
      $(".pwd_suc").css("display", "none");
    } else {
      $("#pwd").css("border", "1px solid blue");
      $(".pwd_suc").css("display", "block");
      $(".pwd_mis").css("display", "none");
    }
    if (pwd == "") {
      $("#pwd").css("border", "var(--main-border)");
      $(".pwd_mis").css("display", "none");
      $(".pwd_suc").css("display", "none");
    }
  });

  $("#repwd").on("keyup", function () {
    const repwd = document.querySelector("#repwd").value;
    if ($("#pwd").val() != $("#repwd").val()) {
      $("#repwd").css("border", "1px solid red");
      $(".repwd_mis").css("display", "block");
      $(".repwd_suc").css("display", "none");
    } else {
      $("#repwd").css("border", "1px solid blue");
      $(".repwd_suc").css("display", "block");
      $(".repwd_mis").css("display", "none");
    }
    if (repwd == "") {
      $("#repwd").css("border", "var(--main-border)");
      $(".repwd_mis").css("display", "none");
      $(".repwd_suc").css("display", "none");
    }
  });

  $("#nickname").on("keyup", function () {
    const nickReg = /^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{4,15}$/;
    const nick = document.querySelector("#nickname").value;
    if (!nickReg.test(nick)) {
      $("#nickname").css("border", "1px solid red");
      $(".en_nick_alert").css("display", "block");
      $(".nick_success_alert").css("display", "none");
    } else {
      $("#nickname").css("border", "1px solid blue");
      $(".nick_success_alert").css("display", "block");
      $(".en_nick_alert").css("display", "none");
    }
    if (nick == "") {
      $("#nickname").css("border", "var(--main-border)");
      $(".en_nick_alert").css("display", "none");
      $(".nick_success_alert").css("display", "none");
    }
  });

  $("#email").on("keyup", function () {
    const emailReg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
    const email = document.querySelector("#email").value;
    if (!emailReg.test(email)) {
      $("#email").css("border", "1px solid red");
      $(".email_mis").css("display", "block");
      $(".email_suc").css("display", "none");
    } else {
      $("#email").css("border", "1px solid blue");
      $(".email_suc").css("display", "block");
      $(".email_mis").css("display", "none");
    }
    if (email == "") {
      $("#email").css("border", "var(--main-border)");
      $(".email_suc").css("display", "none");
      $(".email_mis").css("display", "none");
    }
  });

  $("#birthday").on("keyup", function () {
    const birthReg = /^[0-9]{4}$/;
    const birth = document.querySelector("#birthday").value;
    if (!birthReg.test(birth)) {
      $("#birthday").css("border", "1px solid red");
      $(".yy_mis").css("display", "block");
      $(".mm_mis").css("display", "none");
    } else {
      $("#birthday").css("border", "1px solid blue");
      $(".yy_mis").css("display", "none");
      $(".mm_mis").css("display", "block");
    }
    if (birth == "") {
      $("#birthday").css("border", "var(--main-border)");
      $(".yy_mis").css("display", "none");
      $(".mm_mis").css("display", "none");
    }
  });

  $(".sel").on("click", function () {
    const option = $(".sel option:selected").val();
    if (option == "00") {
      $(".sel").css("border", "1px solid red");
      $(".mm_mis").css("display", "block");
    } else {
      $(".sel").css("border", "1px solid blue");
      $(".mm_mis").css("display", "none");
      $(".dd_mis").css("display", "block");
    }
  });
  $(".dd").on("keyup", function () {
    const ddReg = /^[0-9]{1,2}$/;
    const dd = $(".dd").val();
    if (!ddReg.test(dd)) {
      $(".dd").css("border", "1px solid red");
      $(".dd_mis").css("display", "block");
      $(".all_mis").css("display", "none");
    } else {
      $(".dd").css("border", "1px solid blue");
      $(".dd_mis").css("display", "none");
      $(".all_mis").css("display", "block");
    }
  });
});
