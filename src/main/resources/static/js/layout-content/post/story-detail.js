$(function () {

  $(".modify-btn").click(function () {
    $(".modify-form-wrapper").fadeIn(300);
  });

  $(".modify-form .close").click(function () {
    $(".modal-background").fadeIn(200);
    $(".close-alert").fadeIn(300);
  });

  $(".delete-btn").click(function () {
    $(".modal-background").fadeIn(200);
    $(".delete-alert").fadeIn(300);
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
      const parent = $(this).closest(".my-comment");
      const parentId = parent.data("id");
      const postId = parent.data("post-id");
      let form = document.createElement("form");
      form.action = "/post/reply/save";
      form.method = "post";
      form.setAttribute('class', 'inner-comment');
      form.innerHTML =
        '<input name="postId" type="hidden" value="' + postId + '">' +
        '<input name="parentId" type="hidden" value="' + parentId + '">' +
        '<img src="/img/icon/arrow-return-right.svg">' +
        '<textarea class="comment" name="content" maxlength="300" onkeydown="resize(this)" ' +
        'onkeyup="resize(this)" onclick="commentClick(this)"' +
        'placeholder="내용을 입력해주세요."></textarea>' +
        '<div class="comment-underline"><div class="comment-underline-back"></div></div>' +
        '<div class="comment-button-save" value="save" onclick="formSubmit(this)">등록</div>' +
        '<div class="comment-button-cancel" onclick="myCommentCancel(this)">취소</div>';
      $(this).closest('.my-comment').append(form);
    });

    $(document).on("click", ".my-comment .edit", function () {
      const myComment = $(this).closest(".my-comment");
      const postId = myComment.data("post-id");
      const id = myComment.data("id");
      myComment.find(".option").hide();
      origin = myComment.find(".body");
      originText = origin.text();
      if (origin.find("strong").text() == '') {
        commentBodyText = origin.text();
      } else {
        commentBodyText = origin.text().substring(origin.text().indexOf(" ") + 1, origin.text().length);
      }
      origin.text('');
      let form = document.createElement("form");
      form.action = "/post/reply/modify";
      form.method = "post";
      form.setAttribute('class', 'inner-comment');
      form.innerHTML =
        '<input name="postId" type="hidden" value="' + postId + '">' +
        '<input name="replyId" type="hidden" value=' + id + '>' +
        '<textarea class="mod-comment" name="content" maxlength="300" onkeydown="resize(this)" ' +
        'onkeyup="resize(this)" onclick="commentClick(this)"' +
        'placeholder="수정할 내용을 입력해주세요."></textarea>' +
        '<div class="comment-underline"><div class="comment-underline-back"></div></div>' +
        '<div class="comment-button-save" value="save" onclick="formSubmit(this)">수정</div>' +
        '<div class="comment-button-cancel" onclick="myCommentCancel(this)">취소</div>';
      $(this).closest('.my-comment').find(".body").append(form);
      $(".mod-comment").text(commentBodyText.replace(/\s/gi, ""));
      resize(document.querySelector(".mod-comment"));
    });
    
    $(document).on("focus", ".mod-comment", function () {
      resize(this);
    });

    $(document).on("click", ".my-comment .remove", function () {
      selectReplyId = $(this).closest(".my-comment").data("id");
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

let headerPassed = true, mainPassed = true,
  origin, originText, selectReplyId

function registCheck(...passed) {
  if (passed.every(e => { return e; })) {
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

function postDelete(id) {
  location.href = "/post/story/delete?id=" + id;
}

function resize(_this) {
  _this.style.height = "1px";
  _this.style.height = (12 + _this.scrollHeight) + "px";
}

function commentClick(_this) {
  (async () => {
    const loginCheck = await fetch("/chat/login-check").then(res => res.text());
    if (loginCheck == "ok") {
      $(".comment-underline-back").stop().animate({ 'width': '0%' }, 100);
      $(".comment-button-save").stop().fadeOut(100);
      $(".comment-button-cancel").stop().fadeOut(100);
      $(_this).parent().find(".comment-underline-back").stop().animate({ 'width': '100%' }, 200);
      $(_this).parent().find(".comment-button-save").stop().fadeIn(300);
      $(_this).parent().find(".comment-button-cancel").stop().fadeIn(300);
    } else {
      alert('로그인 후 이용 가능합니다.');
      location.href = "/login";
    }
  })();
}

function commentCancel(_this) {
  let textarea = $(_this).parent().find("textarea");
  textarea.val('');
  $(_this).parent().find(".comment-underline-back").stop().animate({ 'width': '0%' }, 100);
  $(_this).parent().find(".comment-button-save").stop().fadeOut(100);
  $(_this).parent().find(".comment-button-cancel").stop().fadeOut(100);
  textarea.css('height', '1px');
  textarea.css('height', (12 + textarea.prop('scrollHeight')) + 'px');
}

function myCommentCancel(_this) {
  location.reload();
  $(_this).parent().remove();
}

function replyDelete() {
  fetch("/post/reply/delete?id=" + selectReplyId).then(res => {
    if (res.json()) {
      alert("댓글이 삭제되었습니다.");
      location.reload();
    } else {
      alert("요청이 실패하였습니다.");
    }
  });
}
