$(function () {

    $(document).on("click", ".chat-open-btn", function () {
      (async () => {
        const report = $(this).closest("row");
        report.addClass("active-row");
        const chatId = report.find("#chat-id").text();
        const result = await fetch("/admin/chat-view?chatId=" + chatId).then(res => res.text());
        $("#chat").replaceWith(result);
        $(".chat-wrapper").fadeIn(300);
        $("body").css("overflow-y", 'hidden');
        const position = $(".reportedChat").position().top;
        const height = $(".reportedChat").height();
        const sh = document.querySelector("#chat-box").scrollHeight;
        const ih = $("#chat-box").innerHeight();
        console.log("position = " + position)
        console.log("height = " + height)
        console.log("sh = " + sh)
        console.log("ih = " + ih)
        $(".chat-wrapper .chat .body").scrollTop(position / sh * (sh - ih));
      })();
    });

    $(document).on("click", ".chat-wrapper .chat .head .close", function () {
      chatWindowHide();
    });

  $('html').click(function (e) {
    if ($(e.target).is(".chat-wrapper")) {
      chatWindowHide();
    }
  });

});

function complete(state, clickType, _this){
  (async () => {
    let reportId;  console.log(clickType)
    switch(clickType) {
      case 0:
        reportId = $(_this).closest(".row").find("#report-id").data("value");
        break;
      case 1:
        reportId = $(".active-row").find("#report-id").data("value");
        break;
    }
    const result = await fetch("/admin/report/state?reportId=" + reportId + "&state=" + state).then(res => res.text());
    $(".right-side .contents").replaceWith(result);
  })();
}

function hideAlert(){
  $("option:selected").prop("selected", false);
  $(".alert-window").hide();
  $(".modal-background").hide();
  $(".alert-window .data").text("");
}

function chatWindowHide() {
  $(".chat-wrapper").fadeOut(100);
  $("body").css("overflow-y", 'scroll');
  $("row").removeClass("active-row");
}