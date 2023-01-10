$(function () {
  // 모두 체크 & 해제
  $("#all_check").click(function () {
    if ($("#all_check").prop("checked")) {
      $(".term_check").prop("checked", true);
      $(".sign_term_btn")
        .css("backgroundColor", "#326cf9")
        .removeClass("disable");
    } else {
      $(".term_check").prop("checked", false);
      $(".sign_term_btn").css("backgroundColor", "#dfdfdf").addClass("disable");
    }
  });

  // 전체 체크박스 선택 중 하나를 풀었을 떄 '전체' 체크해제
  $(".term_check").click(function () {
    if ($("input[name='term_check']:checked").length == 4) {
      $("#all_check").prop("checked", true);
      $(".sign_term_btn")
        .css("backgroundColor", "#326cf9")
        .removeClass("disable");
    } else {
      $("#all_check").prop("checked", false);
      $(".sign_term_btn").css("backgroundColor", "#dfdfdf").addClass("disable");
    }
    // 필수 버튼 모두 체크 시 버튼 활성화
    if ($("input.impl_btn:checked").length == 3) {
      $(".sign_term_btn")
        .css("backgroundColor", "#326cf9")
        .removeClass("disable");
    }
  });

  // 동의 이후 체크 리셋
  $(".sign_term_btn").click(function () {
    $("#all_check").prop("checked", false);
    $(".term_check").prop("checked", false);
  });
});
