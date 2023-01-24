$(function () {
    $(document).ready(function () {

        $(document).on("click", ".article-wrapper .head .keyword .text", function () {
            articleUpdate('keyword', this);
        });

        $(document).on("click", ".pagenation a", function () {
            articleUpdate('page', this);
        });

        $(document).on("click", ".pagenation .btn-left", function () {
            articleUpdate('page', $(".active-page").prev());
        });

        $(document).on("click", ".pagenation .btn-right", function () {
            articleUpdate('page', $(".active-page").next());
        });

        youtubeUpdate('keyword', $(".youtube-wrapper .active-keyword"));

        $(document).on("click", ".youtube-wrapper .head .keyword .text", function () {
            youtubeUpdate('keyword', this);
        });

        $(document).on("click", ".controller div", function () {
            youtubeUpdate('page', this);
        });

        $(document).on("click", ".youtube", function () {
            $(".modal-background").fadeIn(300);
            $("body").css('overflow', 'hidden');
            const iframeWrapper = $(this).parent().find(".iframe-wrapper");
            iframeWrapper.addClass("active-iframe");
            iframeWrapper.fadeIn(0, function () {
                iframeWrapper.animate({ 'width': '70%' }, 300);
            });
            iframeWrapper.find("iframe")[0].contentWindow.postMessage('{"event":"command","func":"' + 'playVideo' + '","args":""}', '*');
        });
        $(document).on("click", ".iframe-close", function () {
            const iframeWrapper = $(this).closest(".iframe-wrapper");
            iframeWrapper.animate({ 'width': '0%' }, 300, function () {
                iframeWrapper.hide();
            });
            iframeWrapper.find("iframe")[0].contentWindow.postMessage('{"event":"command","func":"' + 'stopVideo' + '","args":""}', '*');
            $(".modal-background").fadeOut(100);
            $("body").css('overflow', 'scroll');
        });
    });

    $(".article-index").click(function () {
        if (!$(this).hasClass("active-index")) {
            $(".youtube-wrapper").stop().hide();
            $(".article-wrapper").stop().fadeIn(300);
            $(".youtube-index").removeClass("active-index");
            $(this).addClass("active-index");
        }
    });
    $(".youtube-index").click(function () {
        if (!$(this).hasClass("active-index")) {
            $(".article-wrapper").stop().hide();
            $(".youtube-wrapper").stop().fadeIn(300).css('display', 'flex');
            $(".article-index").removeClass("active-index");
            $(this).addClass("active-index");
        }
    });

    $('html').click(function (e) {
        if ($(e.target).is(".modal-background")) {
            const iframeWrapper = $(".active-iframe");
            iframeWrapper.animate({ 'width': '0%' }, 300, function () {
                iframeWrapper.hide();
            });
            iframeWrapper.find("iframe")[0].contentWindow.postMessage('{"event":"command","func":"' + 'stopVideo' + '","args":""}', '*');
            $(".modal-background").fadeOut(100);
            $("body").css('overflow', 'scroll');
        }
    });

});

function articleUpdate(clickType, _this) {
    (async () => {
        $(".article-wrapper .loading").fadeIn(0);
        let keyword, page;

        if (clickType == 'keyword') {
            keyword = $(_this).data("keyword");
            page = 1;
            $(".article-wrapper .head .keyword .text").removeClass("active-keyword");
            $(_this).addClass("active-keyword");
        } else {
            keyword = $(".article-wrapper .active-keyword").data("keyword");
            page = $(_this).text();
        }

        const result = await fetch("/contents/search?keyword=" + keyword + "&page=" + page).then(res => res.text());

        setTimeout(function () {
            $("#articles").replaceWith(result);
        }, 500);
        $(window).scrollTop(0);
    })();
}

function youtubeUpdate(clickType, _this) {
    (async () => {
        $(".youtube-wrapper .loading").fadeIn(0);

        let keyword, page = '',
            url = "https://www.googleapis.com/youtube/v3/search"
                + "?key=AIzaSyDIgbt4H6nuG9fR7exf9067MGNk1DQa8C4"
                + "&part=snippet&maxResults=4&regionCode=KR&chart=mostPopular";
        // AIzaSyCLqtyChxbeBX49wVfHFo726QVBTWdRbFE 
        // AIzaSyDg3HhsyLwHbvR_okr6UND03D5cI27EHsk 
        // AIzaSyBG-5Krvf-tRP-0pqFCzNpuFUUb8opOw3c
        // AIzaSyDIgbt4H6nuG9fR7exf9067MGNk1DQa8C4
        // AIzaSyCUFMhMo-xE6QzSHDVN19FTwLY-ns0U-RI

        const containers = $(".youtube-box").get(), iframe = $("iframe").get();

        if (clickType == 'keyword') {
            $(".youtube-wrapper .head .keyword .text").removeClass("active-keyword");
            $(_this).addClass("active-keyword");
            keyword = "&q=" + $(_this).data("keyword");
        } else {
            keyword = "&q=" + $(".youtube-wrapper .active-keyword").data("keyword");
            page = "&pageToken=" + $(_this).data("token");
        }

        const result = await fetch(url + keyword + page).then(res => res.json());

        for (let i = 0; i < 4; i++) {
            const thumbnail = result.items[i].snippet.thumbnails.high.url,
                id = result.items[i].id.videoId,
                title = result.items[i].snippet.title,
                channel = result.items[i].snippet.channelTitle;
            pubTime = result.items[i].snippet.publishTime,
                prevPage = result.prevPageToken,
                nextPage = result.nextPageToken;
            const date = getYmd10(pubTime);

            containers[i].querySelector(".youtube").style.backgroundImage = "url(" + thumbnail + ")";
            containers[i].querySelector(".youtube").dataset.id = id;
            containers[i].querySelector(".title").innerHTML = title;
            containers[i].querySelector(".channel").innerHTML = channel;
            containers[i].querySelector(".date").innerHTML = date;
            iframe[i].src = "https://www.youtube.com/embed/" + id + "?enablejsapi=1&version=3&playerapiid=ytplayer";

            if (prevPage == null) {
                $(".controller .btn-left").addClass("disable");
            } else {
                $(".controller .btn-left").removeClass("disable");
                $(".controller .btn-left").attr("data-token", prevPage);
            }
            if (nextPage == null) {
                $(".controller .btn-right").addClass("disable");
            } else {
                $(".controller .btn-right").removeClass("disable");
                $(".controller .btn-right").attr("data-token", nextPage);
            }
            if (i == 3) {
                setTimeout(function () { $(".youtube-wrapper .loading").hide() }, 500);
            }
        }
        $(window).scrollTop(0);
    })();
}

function getYmd10(_date) {
    let d = new Date(_date);
    return d.getFullYear() + "-"
        + ((d.getMonth() + 1) > 9 ? (d.getMonth() + 1).toString() : "0" + (d.getMonth() + 1)) + "-"
        + (d.getDate() > 9 ? d.getDate().toString() : "0" + d.getDate().toString());
}
