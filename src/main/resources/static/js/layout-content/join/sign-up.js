$(function () {

  $("#all_check").click(function () {
    if ($("#all_check").prop("checked")) {
      $(".term_check").prop("checked", true);
      $(".sign_term_btn")
        .css("backgroundColor", "#3b5987")
        .removeClass("disable");
    } else {
      $(".term_check").prop("checked", false);
      $(".sign_term_btn").css("backgroundColor", "#dfdfdf").addClass("disable");
    }
  });

  $(".term_check").click(function () {
    if ($("input[name='term_check']:checked").length == 4) {
      $("#all_check").prop("checked", true);
      $(".sign_term_btn")
        .css("backgroundColor", "#3b5987")
        .removeClass("disable");
    } else {
      $("#all_check").prop("checked", false);
      $(".sign_term_btn").css("backgroundColor", "#dfdfdf").addClass("disable");
    }
    if ($("input.impl_btn:checked").length == 3) {
      $(".sign_term_btn")
        .css("backgroundColor", "#3b5987")
        .removeClass("disable");
    }
  });

  $(".sign_term_btn").click(function () {
    $("#all_check").prop("checked", false);
    $(".term_check").prop("checked", false);
  });

  $(".term-ser").click(function () {
    $("#all_check").prop("checked", false);
    $(".term_check").prop("checked", false);
  });
});
