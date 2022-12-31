$(function () {

   $(".item .detail").click(function(){
     $(".apart-detail-wrapper").stop().fadeIn(100);
   });
   $(".apart-detail .close-btn").click(function(){
    $(".apart-detail-wrapper").stop().fadeOut(100);
   });
   $(".trend-wrapper .fold").click(function(){
    if(!$(this).hasClass("clicked")){
        $(this).addClass("clicked").css('transform', 'translateY(-50%) rotate(180deg)');
        $(".trend-wrapper").css('overflow','visible');
    } else {
        $(this).removeClass("clicked").css('transform', 'translateY(-50%) rotate(0)');
        $(".trend-wrapper").css('overflow','hidden');
    }
   });

   $(".index").click(function(){
    $(".index").removeClass("active-index");
    $(this).addClass("active-index");
   });
});