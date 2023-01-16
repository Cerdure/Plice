// JavaScript
function onlyNumber(obj) {
  obj.value = obj.value.replace(/[^0-9]/g, "");
}

// Jquery
$(function () {
  $(".login_repwd_btn").click(function () {
    $(".pwd_find_container").show().addClass("bg_white");
    $(".login_js, .user, .login_btn").addClass("bg_black");
  });

  $(".fa-times").click(function () {
    $(".pwd_find_container").hide().removeClass("bg_white");
    $(".login_js, .user, .login_btn").removeClass("bg_black");
  });

  $(".accept_btn").click(function () {
    $(".pwd_find_container").hide().removeClass("bg_white");
    $(".login_js, .user, .login_btn").removeClass("bg_black");
  });
});
