$(function () {

  $(document).on("click", ".row .btn", function () {
    (async () => {
      const row = $(this).closest(".row");
      const id = row.find("#id").data("value");
      const result = await fetch("/admin/inquiry-mng/detail?id=" + id).then(res => res.text());
      $(".window-wrapper").replaceWith(result);
      $(".window-wrapper").css('display', 'flex').animate({ 'opacity': '1' }, 300);
      $("body").css("overflow-y", 'hidden');
      row.addClass("active-row");
    })();
  });

  $(document).on("click", ".regist-btn", function () {
    const textareas = $("textarea").get();
    textareas.forEach(e => { e.value = e.value.replace(/\n/g, "<br>") });
    fetch("/admin/inquiry-mng/answer", {
      method: 'post',
      cache: 'no-cache',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: $(".answer-window").serialize()
    }).then(res => {
      if (res.json()) {
        alert("답변이 등록되었습니다.");
        location.reload();
      } else {
        alert("요청이 실패했습니다.");
      }
    });
  });

  $(document).on("click", ".modify-btn", function () {
    fetch("/admin/inquiry-mng/answer/modify", {
      method: 'post',
      cache: 'no-cache',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: $(".answer-window").serialize()
    }).then(res => {
      if (res.json()) {
        alert("답변이 수정되었습니다.");
        location.reload();
      } else {
        alert("요청이 실패했습니다.");
      }
    });
  });

  $('html').click(function (e) {
    if ($(e.target).is(".window-wrapper")) {
      hideWindow();
    }
  });

});

function hideAlert() {
  $(".alert-window").hide();
  $(".modal-background").hide();
}

function hideWindow() {
  $(".window-wrapper").animate({ 'opacity': '0' }, 200, function () {
    $(".window-wrapper").hide();
  });
  $("body").css("overflow-y", 'scroll');
  $("row").removeClass("active-row");
}