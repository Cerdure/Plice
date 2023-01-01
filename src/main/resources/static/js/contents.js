$(function () {

    $(".article-index").click(function(){
        if(!$(this).hasClass("active-index")){
            $(".youtube-wrapper").stop().hide();
            $(".article-wrapper").stop().fadeIn(300);
            $(".youtube-index").removeClass("active-index");
            $(this).addClass("active-index");
        }
    });
    $(".youtube-index").click(function(){
        if(!$(this).hasClass("active-index")){
            $(".article-wrapper").stop().hide();
            $(".youtube-wrapper").stop().fadeIn(300).css('display','flex');
            $(".article-index").removeClass("active-index");
            $(this).addClass("active-index");
        }
    });

});

