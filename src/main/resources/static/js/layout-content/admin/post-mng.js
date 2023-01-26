$(function () {

  $(document).on("click", ".open-btn, .row .title", function () {
    const row = $(this).closest(".row");
    $(".row").removeClass("active-row");
    row.addClass("active-row");
    openWindow(row.find("#id").data("value"), row.find("#id").data("type"));
    windowOpen = true;
  });

  $(document).on("click", ".window-close", function () {
    hideWindow();
  });

  $('html').click(function (e) {
    if ($(e.target).is(".window-wrapper")) {
      hideWindow();
    }
  });

  $(".create-btn").click(function () {
    $(".notice-write-wrapper .head .text").text("공지사항 등록");
    $(".notice-write-wrapper").fadeIn(300);
  });

  $(document).on("click", ".mod-btn", function () {
    (async () => {
      const row = $(this).closest(".row");
      const id = row.find("#id").data("value");
      const form = $(".notice-write-wrapper");
      const result = await fetch("/admin/post-mng/notice/modify?id=" + id).then(res => res.text());
      row.addClass("active-row");
      form.replaceWith(result);
      $(".notice-write-wrapper .head .text").text("공지사항 수정");
      $(".notice-write-wrapper").fadeIn(300);
    })();
  });

  $(document).on("click", ".modify-regist-btn", function () {
    fetch("/admin/post-mng/notice/modify", {
      method: 'post',
      cache: 'no-cache',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: $("#notice-form").serialize()
    }).then(res => {
      if (res.json()) {
        alert("공지글이 수정되었습니다.");
        location.reload();
      } else {
        alert("요청이 실패했습니다.");
      }
    });
  });

  $(document).on("click", ".del-btn", function () {
    if (windowOpen) {
      selectRow = $(".active-row");
      selectPostId = selectRow.find("#id").data("value");
      $(".post-window").css("filter", "brightness(0.9)");
    } else {
      selectRow = $(this).closest(".row");
      selectPostId = selectRow.find("#id").data("value");
    }
    $(".del-alert .data").text("게시글 관리 ID : " + selectPostId);
    $(".modal-background").fadeIn(100);
    $(".del-alert").fadeIn(300);
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

  $(document).on("click", ".write-form .close", function () {
    $(".write-form").css("filter", 'brightness(0.9)');
    $(".close-alert-wrapper").fadeIn(300);
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

  $('html').click(function (e) {
    if (!$(e.target).is(".opt-btn, .box, .edit, .remove, .reply")) {
      $(".box").fadeOut(0);
      $(".clicked").removeClass("clicked");
    }
  });

});

let selectRow, selectPostId, selectMemberId, windowOpen = false,
  headerPassed = false, mainPassed = false;

function registCheck(...passed) {
  if (passed.every(e => { return e; })) {
    $(".notice-write-form .row-btn-2").removeClass("disable");
  } else {
    $(".notice-write-form .row-btn-2").addClass("disable");
  }
}

function deletePost(type) {
  (async () => {
    await fetch("/admin/post-mng/" + type + "/delete?id=" + selectPostId).then(res => {
      if (res.json()) {
        alert("글이 삭제되었습니다.");
        location.reload();
      } else {
        alert("요청이 실패하였습니다.");
      }
    });
  })();
}

function formClose() {
  $(".close-alert-wrapper").fadeOut(100);
  $(".write-form-wrapper").fadeOut(100);
  $(".write-form input").val("");
  $(".write-form textarea").val("");
  $(".write-form .count").text("0/3000");
  $(".write-form").css("filter", 'brightness(1)');
  $(".row").removeClass("active-row");
  headerPassed = false;
  mainPassed = false;
}

function openWindow(id, type) {
  (async () => {
    const result = await fetch("/admin/post-mng/" + type + "/detail?id=" + id).then(res => res.text());
    $("#post").replaceWith(result);
    $(".window-wrapper").css('display', 'flex').animate({ 'opacity': '1' }, 300);
    $("body").css("overflow", "hidden");
  })();
}

function hideWindow() {
  $(".modal-background").fadeOut(0);
  $(".window-wrapper").fadeOut(100);
  $("body").css("overflow", "auto");
  $(".row").removeClass("active-row");
  windowOpen = false;
}

function hideAlert() {
  $("option:selected").prop("selected", false);
  $(".alert-window").hide();
  $(".alert-window .data").text("");
  $(".write-form").css("filter", 'brightness(1)');
  $(".modal-background").hide();
  $(".post-window").css("filter", "brightness(1)");
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

let origin, originText, selectReplyId

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
