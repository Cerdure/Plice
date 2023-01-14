$(function () {
    $(document).ready(function () {

        $(document).on("click", ".my-room, .search-result-chat-room, .room", function () {
            (async () => {
                const loginCheck = await fetch("/chat/login-check").then(res => res.text());
                if (loginCheck == "ok") {
                    if ($(this).hasClass("my-room")) {
                        $(".my-room").removeClass("active-room");
                        $(this).addClass("active-room");
                        newJoin = false;
                        chatRoomOpen(this);
                    } else {
                        const myRoomCount = await fetch("/chat/my-rooms/count").then(res => res.text());
                        if (myRoomCount == 5) {
                            alert("채팅방은 최대 5개까지 참여할 수 있습니다.");
                        } else {
                            $(".my-room").removeClass("active-room");
                            if ($(".my-room").get().every(e => { return e.dataset.id != $(this).data("id") })) {
                                newJoin = true;
                                const memberCount = await fetch("/chat/in?roomId=" + $(this).data("id")).then(res => res.text());
                                $(this).attr("data-member-count", memberCount);
                            }
                            myRoomUpdate();
                            $(".my-room:last-child").addClass("active-room");
                            chatRoomOpen(this);
                        }
                    }
                } else {
                    alert('로그인 후 이용 가능합니다.');
                    location.href = "/login";
                }
            })();
        });

        $(document).on("keyup", ".search-input", function () {
            let inputVal = $(this).val();
            if (inputVal != '' && inputVal.length > 1) {
                keySearch(inputVal);
            } else {
                $(".search-result-apart").remove();
                $(".search-result-outer-wrapper").hide();
            }
        });

        $(document).on("click", ".search-input", function () {
            if ($(this).val() != '') $(".search-result-outer-wrapper").show();
        });

        $(document).on("click", ".chat-wrapper .chat .head .close", chatRoomClose);

        $(document).on("keyup", ".chat-input", function () {
            $(".chat-wrapper .regist-btn .count").text($(this).val().length + '/300');
        });

        $(document).on("click", ".room-exit", function () {
            (async () => {
                let roomId = '';
                roomId = $(".active-room").data("id");
                await fetch("/chat/room-exit?roomId=" + roomId).then(res => console.log(res.text()));
                chatRoomClose();
                myRoomUpdate();
            })();
        });

        $(document).on("click", ".last-chat-viewer", function () {
            moveToBottom();
        });

        initConnect();
        noticeAnimation();
        totalCountAnimation();
    });

    document.addEventListener('scroll', function (event) {
        if (event.target.id === 'chat-box') {
            let st = $("#chat-box").scrollTop();
            const ih = $("#chat-box").innerHeight();
            const sh = event.target.scrollHeight;
            if ((st + ih) >= sh - 10) {
                $(".last-chat-viewer").stop().fadeOut(300);
            }
        }
    }, true);

    function chatRoomOpen(_this) {
        (async () => {
            currentRoomId = $(_this).data("id");
            const update = await fetch("/chat/update?roomId=" + currentRoomId).then(res => res.text());
            $("#chat").replaceWith(update);
            $(".chat-wrapper .chat .head .title .member-count")
                .text('참여인원 ' + $(".my-room[data-id=" + currentRoomId + "]").data("member-count") + '명');
            $(".chat-wrapper .chat .head .title .data").text($(_this).data("name"));
            $(".chat-wrapper").stop().fadeOut(200).fadeIn(300);
            $(".right-side").css({ 'flex-direction': 'row', 'align-items': 'center' });
            $(".top3-wrapper").hide();
            $(".top3-wrapper .head").stop().animate({ 'margin-bottom': '20px' }, 300);
            $(".top3-wrapper .body").css('height', 'auto');
            $(".top3-wrapper").css({ 'width': 'auto', 'margin-top': '0px', 'margin-right': '10%', 'justify-content': 'center', 'animation-delay': '0s' }).show();
            $(".popular").css('flex-direction', 'column');
            $(".chat-wrapper").css('display', 'flex');
            $(".room").css({ 'height': '10%', 'margin': '20px 20px' });
            $(".search-result-outer-wrapper").hide();
            $(".current-data-wrapper").hide();
            $(".chat-wrapper .regist-btn .btn").remove();
            const div = document.createElement("div");
            div.setAttribute("class", "btn send-btn-" + currentRoomId);
            div.innerHTML = "<img src='/img/icon/send-fill.svg'>";
            $(".chat-wrapper .regist-btn .count").before(div);
            moveToBottom();
            subscribe(currentRoomId);
            if ($("#chat-box").innerHeight() <= document.querySelector("#chat-box").scrollHeight) {
                $(".last-chat-viewer").hide();
            }
            chatOpen = true;
        })();
    }

    function chatRoomClose() {
        $(".my-room").removeClass("active-room");
        $(".right-side").css({ 'flex-direction': 'column', 'align-items': 'center' });
        $(".top3-wrapper").hide();
        $(".top3-wrapper .head").stop().animate({ 'margin-bottom': '40px' }, 300);
        $(".top3-wrapper .body").css('height', '100%');
        $(".top3-wrapper").css({ 'width': '100%', 'margin-right': '0', 'animation-delay': '1s' }).show();
        $(".popular").css('flex-direction', 'row');
        $(".current-data-wrapper").show();
        $(".chat-wrapper").hide();
        $(".info-wrapper").remove();
        $(".content-wrapper").remove();
        $(".room").css({ 'height': '200px', 'margin': '0px 20px' });
        chatOpen = false;
        totalCountAnimation();
    }

    $(".search-wrapper .reset").click(function () {
        $('.search-input').val('');
        $(".search-result-chat-room").remove();
        $(".search-result-outer-wrapper").hide();
    });

    const resResult = inputVal => fetch("/chat/input-search?inputVal=" + inputVal).then(res => res.text());
    let prevResult;

    function keySearch(inputVal) {
        (async () => {
            try {
                let result = await resResult(inputVal);
                $('#search-input-results').replaceWith(result);
                $(".search-result-apart-wrapper").css('display', $(".search-result-apart-wrapper").children().length == 0 ? 'none' : 'flex');
                if (result != prevResult) {
                    $(".search-result-outer-wrapper").show();
                    prevResult = result;
                }
            } catch (e) {
                console.log(e);
            }
        })();
    }

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
});

let newJoin = false, chatOpen = false;
let currentRoomId, currentSubscribe = new Map([]);
let st, ih, sh, isFirstMessage = true;

function initConnect() {
    $(".my-room").get().forEach(e => { subscribe(e.dataset.id) });
}

function subscribe(_roomId) {
    let messageInput = $('.chat-input');
    let sendBtn = $('.send-btn-' + _roomId);
    let roomId = _roomId;

    const sock = new SockJS("/ws");
    const client = Stomp.over(sock);

    if (currentSubscribe.get(roomId) != null) {
        currentSubscribe.get(roomId).unsubscribe();
    }

    client.connect({}, function () {
        if (newJoin) {
            client.send('/publish/chat/in-message', {}, JSON.stringify({ chatRoomId: roomId }));
        }
        currentSubscribe.set(roomId,
            client.subscribe('/subscribe/chat/room/' + roomId, function (chat) {
                const content = JSON.parse(chat.body);
                let messagebox;
                if (content.chatRoomId == currentRoomId) {
                    if (content.type == 'INFO') {
                        messagebox = document.createElement("div");
                        messagebox.setAttribute('class', 'info-wrapper');
                        messagebox.innerHTML =
                            "<div class='chat-box'>"
                            + "<div class='content'>" + content.message + "</div>"
                            + "</div>";
                        $(".chat-wrapper .chat .head .title .member-count").text('참여인원 ' + content.memberCount + '명');
                    } else {
                        let hour = new Date(content.regDate).getHours();
                        let minute = new Date(content.regDate).getMinutes();
                        messagebox = document.createElement("div");
                        hour = hour <= 12 ? '오전 ' + hour : '오후' + (Number(hour) - 12);
                        minute = minute < 10 ? '0' + minute : minute;
                        const phone = $("#chat").data("phone");
                        if (content.member.phone == phone) {
                            messagebox.setAttribute('class', 'content-wrapper my-chat');
                            messagebox.innerHTML =
                                "<div class='chat-box'>"
                                + "<div class='content'>" + content.message + "</div>"
                                + "<div class='name-date-box'>"
                                + "<strong>" + content.member.nickname + "</strong>"
                                + "<div class='date'>" + hour + ":" + minute + "</div>"
                                + "</div>"
                                + "<div class='profile-wrapper'>"
                                + "<img src='" + content.member.profileImgPath + "'>"
                                + "</div>"
                                + "<div>";
                            $(".last-chat-viewer").addClass("isMine");
                        } else {
                            messagebox.setAttribute('class', 'content-wrapper');
                            messagebox.innerHTML =
                                "<div class='chat-box'>"
                                + "<div class='profile-wrapper'>"
                                + "<img src='" + content.member.profileImgPath + "'>"
                                + "</div>"
                                + "<div class='name-date-box'>"
                                + "<strong>" + content.member.nickname + "</strong>"
                                + "<div class='date'>" + hour + ":" + minute + "</div>"
                                + "</div>"
                                + "<div class='content'>" + content.message + "</div>"
                                + "<div>";
                            $(".last-chat-viewer").removeClass("isMine");
                            $(".last-chat-viewer .text").text(content.member.nickname + ' : ' + content.message);
                        }
                    }
                    ih = $("#chat-box").innerHeight();
                    sh = document.querySelector("#chat-box").scrollHeight;
                    document.getElementById("chat-box").appendChild(messagebox);
                    newMessage(content.type);
                }
                myRoomUpdate();
            })
        )

        sendBtn.click(function () {
            var message = messageInput.val();
            client.send('/publish/chat/message', {}, JSON.stringify({ chatRoomId: roomId, message: message }));
            messageInput.val('');
            $(".chat-wrapper .regist-btn .count").text('0/300');
            moveToBottom();
        });

    });
}

function myRoomUpdate() {
    (async () => {
        const update = await fetch("/chat/my-rooms").then(res => res.text());
        $("#my-rooms").replaceWith(update);
        if (chatOpen) {
            $(".my-room[data-id=" + currentRoomId + "]").addClass("active-room");
        }
    })();
}

function moveToBottom() {
    const chatBody = $(".chat-wrapper .chat .body");
    chatBody.scrollTop(chatBody.prop('scrollHeight'));
}

function newMessage(type) {
    st = $("#chat-box").scrollTop();
    if (st + ih >= sh - 1) {
        moveToBottom();
    } else if (!$(".last-chat-viewer").hasClass('isMine') && type == 'NONE') {
        $(".last-chat-viewer").stop().fadeIn(300);
    }
}

function noticeAnimation() {
    let idx = 1;
    setInterval(function () {
        $(".notice ul").stop().animate({ 'bottom': (59 * idx) + 'px' }, 300, function () {
            if (idx == 5) {
                $(".notice ul").css('bottom', '0px');
                idx = 1;
            } else {
                idx++;
            }
        });
    }, 3000);
}

function totalCountAnimation() {
    let num = 0;
    setTimeout(() => {
        let totalCountUp = setInterval(function () {
            const dataView = $(".current-data-wrapper .data");
            const maxNum = dataView.data("num");
            num++;
            dataView.text(num);
            if (num == maxNum) {
                clearInterval(totalCountUp);
            }
        }, 80);
    }, 500);
}