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
        if (time == 150) {
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
    $(".code-send")
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
});
