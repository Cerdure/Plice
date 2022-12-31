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
        $(".chat-wrapper").stop().fadeOut(200).fadeIn(300);
        $(".my-room").removeClass("active-room");
        $(this).addClass("active-room");
        $(".right-side").css({'flex-direction':'row','align-items':'center'});
        $(".top3-wrapper").hide();
        $(".top3-wrapper .head").stop().animate({'margin-bottom':'0px'},300);
        $(".top3-wrapper").css({'width':'40%','margin-top':'0px'}).show();
        $(".popular, .lastest").css('flex-direction','column');
        $(".chat-wrapper").css('display','flex');
        $(".room").css({'height':'10%','margin':'20px 20px'});
    });
    $(".chat-wrapper .chat .head .close").click(function(){
        $(".my-room").removeClass("active-room");
        $(".right-side").css({'flex-direction':'column','align-items':'center'});
        $(".top3-wrapper").hide();
        $(".top3-wrapper .head").stop().animate({'margin-bottom':'40px'},300);
        $(".top3-wrapper").css({'width':'100%','margin-top':'80px'}).show();
        $(".popular, .lastest").css('flex-direction','row');
        $(".chat-wrapper").hide();
        $(".room").css({'height':'200px','margin':'0px 20px'});
    });

});