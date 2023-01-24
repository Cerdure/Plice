$(function () {

    $(".search-input").keyup(function (event) {
        let inputVal = $(this).val();
        if (event.keycode == 13 || event.which == 13) {
            location.href = "/map?keyword=" + inputVal;
        } else if (inputVal != '' && inputVal.length > 1) {
            keySearch(inputVal);
        } else {
            $(".search-result-apart").remove();
            $(".search-result-address").remove();
            $(".search-result-outer-wrapper").hide();
        }
    });

    $('html').click(function (e) {
        if (!$(e.target).is(".search-result-wrapper,"
            + ".search-result-wrapper div,"
            + ".search-result-wrapper span,"
            + ".search-result-wrapper strong,"
            + ".search-result-wrapper img,"
            + ".search-result-wrapper input,"
            + ".search-input")) {
            $(".search-result-outer-wrapper").hide();
        }
    });

    $(".search-input").click(function () {
        if ($(this).val() != '') {
            $(".search-result-wrapper").show();
            $(".search-result-outer-wrapper").show();
        }
    });

    $(document).on("click", ".search-result-apart", function () {
        location.href = "/map?apart=" + $(this).data("value");
    });

    $(document).on("click", ".search-result-address", function () {
        location.href = "/map?address=" + $(this).data("value");
    });

    $(".search-wrapper .search-form .reset").click(function () {
        $('.search-input').val('');
        $(".search-result-apart").remove();
        $(".search-result-address").remove();
        $(".search-result-outer-wrapper").hide();
    });


    $(document).on("click", ".room", function () {
        (async () => {
            const loginCheck = await fetch("/chat/login-check").then(res => res.text());
            if (loginCheck == "ok") {
                location.href = "/chat?selectRoomId=" + $(this).data("id");
            } else {
                alert('로그인 후 이용 가능합니다.');
                location.href = "/login";
            }
        })();
    });


    $(".title-article").click(function () {
        if (!$(this).hasClass("active-sort")) {
            $(".youtube-wrapper").hide();
            $(".article-wrapper").fadeIn(300).css('display', 'flex');
            $(".title-youtube").removeClass("active-sort");
            $(this).addClass("active-sort");
        }
    });

    $(".title-youtube").click(function () {
        if (!$(this).hasClass("active-sort")) {
            youtubeUpdate("아파트 신축");
            $(".article-wrapper").hide();
            $(".youtube-wrapper").fadeIn(300).css('display', 'flex');
            $(".title-article").removeClass("active-sort");
            $(this).addClass("active-sort");
        }
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

    let idx = 1;

    setInterval(function () {
        $(".notice ul").stop().animate({ 'bottom': (83 * idx) + 'px' }, 300, function () {
            if (idx == 5) {
                $(".notice ul").css('bottom', '0px');
                idx = 1;
            } else {
                idx++;
            }
        });
    }, 3000);

});

const resResult = inputVal => fetch("/map/input-search?inputVal=" + inputVal).then(res => res.text());
let prevResult;

function keySearch(inputVal) {
    (async () => {
        try {
            let result = await resResult(inputVal);
            $('#search-input-results').replaceWith(result);
            $(".search-result-apart-wrapper").css('display', $(".search-result-apart-wrapper").children().length == 0 ? 'none' : 'flex');
            $(".search-result-address-wrapper").css('display', $(".search-result-address-wrapper").children().length == 0 ? 'none' : 'flex');
            if (result != prevResult) {
                $(".search-result-outer-wrapper").show();
                prevResult = result;
            }
        } catch (e) {
            console.log(e);
        }
    })();
}

function youtubeUpdate(keyword) {
    (async () => {
        $(".youtube-wrapper .loading").fadeIn(0);

        let url = "https://www.googleapis.com/youtube/v3/search"
            + "?key=AIzaSyDIgbt4H6nuG9fR7exf9067MGNk1DQa8C4"
            + "&q=" + keyword
            + "&part=snippet&maxResults=4&regionCode=KR&chart=mostPopular";
        // AIzaSyCLqtyChxbeBX49wVfHFo726QVBTWdRbFE 
        // AIzaSyDg3HhsyLwHbvR_okr6UND03D5cI27EHsk 
        // AIzaSyBG-5Krvf-tRP-0pqFCzNpuFUUb8opOw3c
        // AIzaSyDIgbt4H6nuG9fR7exf9067MGNk1DQa8C4
        // AIzaSyCUFMhMo-xE6QzSHDVN19FTwLY-ns0U-RI

        const containers = $(".youtube-box").get(), iframe = $("iframe").get();
        const result = await fetch(url).then(res => res.json());

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
    })();
}

function getYmd10(_date) {
    let d = new Date(_date);
    return d.getFullYear() + "-"
        + ((d.getMonth() + 1) > 9 ? (d.getMonth() + 1).toString() : "0" + (d.getMonth() + 1)) + "-"
        + (d.getDate() > 9 ? d.getDate().toString() : "0" + d.getDate().toString());
}

$(function () {

    let autoUpdate = true,
        timeTrans = 6000;

    let cdSlider = document.querySelector('.cd-slider');
    let item = cdSlider.querySelectorAll("li");
    let nav = cdSlider.querySelector("nav");

    item[0].className = "current_slide";

    for (var i = 0, len = item.length; i < len; i++) {
        var color = item[i].getAttribute("data-color");
        item[i].style.backgroundColor = color;
    }

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE");
    if (msie > 0) {
        var version = parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
        if (version === 9) { cdSlider.className = "cd-slider ie9"; }
    }

    if (item.length <= 1) {
        nav.style.display = "none";
    }

    function prevSlide() {
        var currentSlide = cdSlider.querySelector("li.current_slide"),
            prevElement = currentSlide.previousElementSibling,
            prevSlide = (prevElement !== null) ? prevElement : item[item.length - 1],
            prevColor = prevSlide.getAttribute("data-color"),
            el = document.createElement('span');

        currentSlide.className = "";
        prevSlide.className = "current_slide";

        nav.children[0].appendChild(el);

        var size = (cdSlider.clientWidth >= cdSlider.clientHeight) ? cdSlider.clientWidth * 2 : cdSlider.clientHeight * 2,
            ripple = nav.children[0].querySelector("span");

        ripple.style.height = size + 'px';
        ripple.style.width = size + 'px';
        ripple.style.backgroundColor = prevColor;

        ripple.addEventListener("webkitTransitionEnd", function () {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        });

        ripple.addEventListener("transitionend", function () {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        });

    }

    function nextSlide() {
        var currentSlide = cdSlider.querySelector("li.current_slide"),
            nextElement = currentSlide.nextElementSibling,
            nextSlide = (nextElement !== null) ? nextElement : item[0],
            nextColor = nextSlide.getAttribute("data-color"),
            el = document.createElement('span');

        currentSlide.className = "";
        nextSlide.className = "current_slide";

        nav.children[1].appendChild(el);

        var size = (cdSlider.clientWidth >= cdSlider.clientHeight) ? cdSlider.clientWidth * 2 : cdSlider.clientHeight * 2,
            ripple = nav.children[1].querySelector("span");

        ripple.style.height = size + 'px';
        ripple.style.width = size + 'px';
        ripple.style.backgroundColor = nextColor;

        ripple.addEventListener("webkitTransitionEnd", function () {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        });

        ripple.addEventListener("transitionend", function () {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        });

    }

    updateNavColor();

    function updateNavColor() {
        var currentSlide = cdSlider.querySelector("li.current_slide");

        var nextColor = (currentSlide.nextElementSibling !== null) ? currentSlide.nextElementSibling.getAttribute("data-color") : item[0].getAttribute("data-color");
        var prevColor = (currentSlide.previousElementSibling !== null) ? currentSlide.previousElementSibling.getAttribute("data-color") : item[item.length - 1].getAttribute("data-color");

        if (item.length > 2) {
            nav.querySelector(".prev").style.backgroundColor = prevColor;
            nav.querySelector(".next").style.backgroundColor = nextColor;
        }
    }

    nav.querySelector(".next").addEventListener('click', function (event) {
        clearInterval(autoInterval);
        autoInterval = setInterval(function () {
            if (autoUpdate) {
                nextSlide();
                updateNavColor();
            };
        }, timeTrans);
        event.preventDefault();
        nextSlide();
        updateNavColor();
    });

    nav.querySelector(".prev").addEventListener("click", function (event) {
        clearInterval(autoInterval);
        autoInterval = setInterval(function () {
            if (autoUpdate) {
                nextSlide();
                updateNavColor();
            };
        }, timeTrans);
        event.preventDefault();
        prevSlide();
        updateNavColor();
    });

    let autoInterval = setInterval(function () {
        if (autoUpdate) {
            nextSlide();
            updateNavColor();
        };
    }, timeTrans);

});