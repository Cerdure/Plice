$(function () {

   $(".item .detail").click(function(){
     $(".apart-detail-wrapper").stop().fadeIn(100);
   });
   $(".apart-detail .close-btn").click(function(){
    $(".apart-detail-wrapper").stop().fadeOut(100);
   });
});