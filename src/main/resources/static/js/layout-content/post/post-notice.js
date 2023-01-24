$(function () {

    $(".search").mouseover(function () {
        $(".search .reset").stop().fadeIn(200);
    }).mouseleave(function () {
        $(".search .reset").stop().fadeOut(200);
    });

    $(".search .reset").click(function () {
        location.href = "/post/notice";
    });

});

