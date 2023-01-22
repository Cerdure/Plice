$(function () {

  $(".search").mouseover(function () {
    $(".search .reset").stop().fadeIn(200);
  }).mouseleave(function () {
    $(".search .reset").stop().fadeOut(200);
  });
  $(".search .reset").click(function () {
    $(this).closest(".search").find("input").val("");
  });

  $(".modify-btn").click(function () {
    $(".modify-form-wrapper").fadeIn(300);
  });

  $(".modify-form .close").click(function () {
    $(".modal-background").fadeIn(200);
    $(".close-alert").fadeIn(300);
  });






  $(document).ready(function () {

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
      $(".modify-form-wrapper .count").text(val.length + "/3000");
      if (val.length > 9) {
        mainPassed = true;
      } else {
        mainPassed = false;
      }
      registCheck(headerPassed, mainPassed);
    });

    $(document).on("mouseover", ".my-comment", function () {
      $(".option img:not(.clicked)").stop().fadeOut(100);
      $(this).find(".option img").stop().fadeIn(300);
    });
    $(document).on("mouseleave", ".my-comment", function () {
      $(".option img:not(.clicked)").stop().fadeOut(100);
    });
    $(document).on("click", ".opt-btn", function () {
      if (!$(this).hasClass("clicked")) {
        $(".opt-btn").stop().fadeOut(0);
        $(this).fadeIn(0);
        let parent = $(this).parent();
        $(".clicked").removeClass("clicked");
        $(this).addClass("clicked");
        $(".box").fadeOut(0);
        parent.find(".box").stop().fadeIn(300);
      } else {
        $(".box").fadeOut(0);
        $(".clicked").removeClass("clicked");
      }
    });
    $(document).on("click", ".reply", function () {
      $(".box").fadeOut(0);
      $(".inner-comment").remove();
      let parent = $(this).closest(".my-comment");
      let parentId = parent.find("#replyId").val();
      let form = document.createElement("form");
      form.setAttribute('class', 'inner-comment');
      form.innerHTML =
          '<input id="parentId" type="hidden" value="' + parentId + '">' +
          '<img src="../static/img/icon/arrow-return-right.svg">' +
          '<textarea class="comment" name="content" maxlength="300" onkeydown="resize(this)" ' +
          'onkeyup="resize(this)" onclick="commentClick(this)" onfocusout="commentFocusout(this)" ' +
          'placeholder="내용을 입력해주세요."></textarea>' +
          '<div class="comment-underline"><div class="comment-underline-back"></div></div>' +
          '<div class="comment-button-save" value="save" onclick="replySave(this)">등록</div>' +
          '<div class="comment-button-cancel" onclick="myCommentCancel(this)">취소</div>';
      $(this).closest('.my-comment').append(form);
    });

    $(document).on("click", ".my-comment .edit", function () {
      $(this).closest(".my-comment").find(".option").hide();
      origin = $(this).closest(".my-comment").find(".body"); //+origin.text().length
      originText = origin.text(); console.log(originText);
      if (origin.find("strong").text() == '') {
        console.log('if')
        commentBodyText = origin.text();
      } else {
        commentBodyText = origin.text().substring(origin.text().indexOf(" ") + 1, origin.text().length);
      }
      origin.text('');
      let form = document.createElement("form");
      form.setAttribute('class', 'inner-comment');
      form.innerHTML =
          '<textarea class="mod-comment" name="content" maxlength="300" onkeydown="resize(this)" ' +
          'onkeyup="resize(this)" onclick="commentClick(this)" onfocusout="commentFocusout(this)" ' +
          'placeholder="수정할 내용을 입력해주세요."></textarea>' +
          '<div class="comment-underline"><div class="comment-underline-back"></div></div>' +
          '<div class="comment-button-save" value="save" onclick="replyModify(this)">수정</div>' +
          '<div class="comment-button-cancel" onclick="myCommentCancel(this)">취소</div>';
      $(this).closest('.my-comment').find(".body").append(form);
      $(".mod-comment").text(commentBodyText.replace(/\s/gi, ""));
      resize(document.querySelector(".mod-comment"));
    });
    $(document).on("focus", ".mod-comment", function () {
      resize(this);
    });

    $(document).on("click", ".my-comment .remove", function () {
      replyId = $(this).closest(".my-comment").find("#replyId").val();
      $(".modal-background").fadeIn(200);
      $(".reply-delete-alert").fadeIn(300);
    });

  });

  $('html').click(function (e) {
    if (!$(e.target).is(".opt-btn, .box, .edit, .remove, .reply")) {
      $(".box").fadeOut(0);
      $(".clicked").removeClass("clicked");
    }
  });





});


let headerPassed = true;
let mainPassed = true;

function registCheck(...passed){
  if(passed.every(e => {return e;})) {
    $(".regist-button").removeClass("disable");
  } else {
    $(".regist-button").addClass("disable");
  }
}

function formClose() {
  hideAlert();
  $(".modify-form-wrapper").fadeOut(100);
  $(".modify-form input").val("");
  $(".modify-form .count").text("0/3000");
  headerPassed = false;
  mainPassed = false;
}

function hideAlert() {
  $(".modal-background").fadeOut(100);
  $(".alert-window").fadeOut(100);
}

function resize(_this) {
  _this.style.height = "1px";
  _this.style.height = (12 + _this.scrollHeight) + "px";
}

function commentClick(_this) {
  $(_this).parent().find(".comment-underline-back").stop().animate({ 'width': '100%' }, 200, 'easeInOutQuad');
  $(_this).parent().find(".comment-button-save").stop().fadeIn(300);
  $(_this).parent().find(".comment-button-cancel").stop().fadeIn(300);
}

let origin;
let originText;

function commentCancel(_this) {
  let textarea = $(_this).parent().find("textarea");
  textarea.val('');
  $(_this).parent().find(".comment-underline-back").stop().animate({ 'width': '0%' }, 100, 'easeInOutQuad');
  $(_this).parent().find(".comment-button-save").stop().fadeOut(100);
  $(_this).parent().find(".comment-button-cancel").stop().fadeOut(100);
  textarea.css('height', '1px');
  textarea.css('height', (12 + textarea.prop('scrollHeight')) + 'px');
}

function myCommentCancel(_this) {
  location.reload();
  $(_this).parent().remove();
}


function postDelete() {
  // $.ajax({
  //     url: "/post-delete/"+inquireId,
  //     type: "post",
  //     error: function (xhr, status, error) {
  //         console.log(error);
  //     }
  // }).done(function(result){
  //     document.location.replace("/inquire");
  // });
}

function replySave(_this){
  let data = {
    content: $(_this).parent().find(".comment").val(),
    parentId: $(_this).parent().find("#parentId").val()
  };
  $.ajax({
    url: "/reply/" + $("#post-id").val(),
    type: "post",
    data: data,
    dataType: "html",
    async: true,
    error: function (xhr, status, error) {
      console.log(error);
    }
  }).done(function (replies) {
    document.location.replace("/story-detail/"+$("#post-id").val());
  });
}


function replyModify(_this){
  let data = {
    replyId: $(_this).closest(".my-comment").find("#replyId").val(),
    content: $(_this).parent().find(".mod-comment").val(),
  };
  $.ajax({
    url: "/reply-modify/" + $("#inquireId").val(),
    type: "post",
    data: data,
    dataType: "html",
    async: true,
    error: function (xhr, status, error) {
      console.log(error);
    }
  }).done(function (replies) {
    document.location.replace("/inquire-detail/"+$("#inquireId").val());
  });
}

function replyDelete(_this){
  let data = {
    replyId: replyId,
  };
  $.ajax({
    url: "/reply-delete/" + $("#inquireId").val(),
    type: "post",
    data: data,
    dataType: "html",
    async: true,
    error: function (xhr, status, error) {
      console.log(error);
    }
  }).done(function (replies) {
    document.location.replace("/inquire-detail/"+$("#inquireId").val());
  });
}
