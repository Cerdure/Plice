$(function () {
  $(".change_container").on("click", function () {
    $(".change_circle").toggleClass("moving");
  });

  $(".repwd").on("click", function () {
    $("body").addClass("bg_black");
    // $(".pwd_find").css("display", "block");
    $(".pwd_find").slideDown();
  });

  $(".fa-times").on("click", function () {
    $("body").removeClass("bg_black");
    // $(".pwd_find").css("display", "none");
    $(".pwd_find").slideUp();
  });
});
