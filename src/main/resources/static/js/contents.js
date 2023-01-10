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

$(document).ready(function () {
    $.get(
        "https://www.googleapis.com/youtube/v3/search", {
            part: 'snippet',
            q: '아파트 분양',
            key: 'AIzaSyCLqtyChxbeBX49wVfHFo726QVBTWdRbFE',
            maxResults: 1,
            regionCode:"KR",

        },

        function (data) {
            var output;
            $.each(data.items, function (i, item) {
                console.log(item);

                thumbnail = item.snippet.thumbnails.medium.url;
                output = '<img width="600" height="300" src ="' + thumbnail + '">';
                // output= '<li>'+vTitle+'<iframe width="560" height="340" src=\"//www.youtube.com/embed/'+playlist+'\"></iframe></li>';
                $(".youtube").append(output);
                $(".youtube").append('<iframe width="560" height="315" src="https://www.youtube.com/embed/'+item.id.videoId+'" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>')
            })
        }
    );

});

