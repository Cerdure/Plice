$(function () {

    $(".title-popular").click(function(){
        if(!$(this).hasClass("clicked")){
            $(".top3-wrapper .body").fadeOut(100);
            $(".lastest").css('display','none');
            $(".popular").css('display','flex');
            $(".top3-wrapper .body").fadeIn(200);
            $(".title-lastest").css('color','dimgray')
                               .removeClass("clicked");
            $(this).css('color','rgb(56, 126, 255)')
                   .addClass("clicked");
        }
    });
    
    $(".title-lastest").click(function(){
        if(!$(this).hasClass("clicked")){
            $(".top3-wrapper .body").fadeOut(100);
            $(".popular").css('display','none');
            $(".lastest").css('display','flex');
            $(".top3-wrapper .body").fadeIn(200);
            $(".title-popular").css('color','dimgray')
                               .removeClass("clicked");
            $(this).css('color','rgb(56, 126, 255)')
                   .addClass("clicked");
        }
    });

    $(".my-room").click(function(){
        $(".right-side").css({'flex-direction':'row','align-items':'center'});
        $(".top3-wrapper").stop().animate({'width':'40%'});
        $(".popular, .lastest").css('flex-direction','column');
        $(".chat-wrapper").css('display','flex');
        $(".room").stop().animate({'height':'200px','margin':'20px 20px'},300);
    });
    $(".chat-wrapper .chat .head .close").click(function(){
        $(".right-side").css({'flex-direction':'column','align-items':'center'});
        $(".top3-wrapper").stop().animate({'width':'100%'});
        $(".popular, .lastest").css('flex-direction','row');
        $(".chat-wrapper").hide();
        $(".room").stop().animate({'height':'250px','margin':'0px 20px'},300);
    });

});