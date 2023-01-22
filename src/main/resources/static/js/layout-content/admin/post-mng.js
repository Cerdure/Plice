$(function(){

  $(document).on("click", ".open-btn", function(){
    openWindow();
  });

  $(document).on("click", ".iframe-close", function(){
    hideWindow();
  });

  $('html').click(function (e) {
    if ($(e.target).is(".window-wrapper")) {
      hideWindow();
    }
  });

  $(".del-btn").click(function(){
    selectRow = $(this).closest(".row");
    selectPostId = selectRow.find("#id").data("value");
    $(".del-alert .data").text("게시글 관리 ID : " + selectPostId);
    $(".modal-background").fadeIn(100);
    $(".del-alert").fadeIn(300);
  }); 

  $(".create-btn").click(function(){
    $(".notice-write-wrapper").fadeIn(300);
  });

  $(document).on("keyup", ".title-input", function () {
    let val = $(this).val();
    if (val != '') {
      headerPassed = true;
    } else {
      headerPassed = false;
    }
    registCheck(headerPassed, mainPassed);
  });

  $(document).on("keyup", ".content-input", function () {
    let val = $(this).val();
    $(".write-form-wrapper .count").text(val.length + "/3000");
    if (val.length > 9) {
      mainPassed = true;
    } else {
      mainPassed = false;
    }
    registCheck(headerPassed, mainPassed);
  });

  $(".write-form .close").click(function(){
    $(".write-form").css("filter",'brightness(0.9)');
    $(".close-alert-wrapper").fadeIn(300);
  });
})

let selectRow, selectPostId,
headerPassed = false, mainPassed = false;

function registCheck(...passed){
  if(passed.every(e => {return e;})) {
      $(".regist-button").removeClass("disable");
  } else {
      $(".regist-button").addClass("disable");
  }
}

function deletePost(type){
  location.href = "/admin/post-mng/" + type + "/delete?id=" + selectPostId;
}

function formClose() {
  $(".close-alert-wrapper").fadeOut(100);
  $(".write-form-wrapper").fadeOut(100);
  $(".write-form input").val("");
  $(".write-form .count").text("0/3000");
  $(".write-form").css("filter",'brightness(1)');
  headerPassed = false;
  mainPassed = false;
}

function openWindow(){
  $(".modal-background").fadeIn(300);
  $(".window-wrapper").fadeIn(500);
  $("body").css("overflow","hidden");
}

function hideWindow(){
  $(".modal-background").fadeOut(0);
  $(".window-wrapper").fadeOut(100);
  $("body").css("overflow","auto");
}

function hideAlert(){
  $("option:selected").prop("selected", false);
  $(".alert-window").hide();
  $(".modal-background").hide();
  $(".alert-window .data").text("");
  $(".write-form").css("filter",'brightness(1)');
}