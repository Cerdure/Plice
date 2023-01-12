$(function () {
    $(document).ready(function(){

        $(document).on("click", ".my-room, .search-result-chat-room", function(){
            (async () => {
                const loginCheck = await fetch("/chat/login-check").then(res => res.text());
                if(loginCheck == "ok"){
                    if($(this).hasClass("my-room")) {
                        $(".my-room").removeClass("active-room");
                        $(this).addClass("active-room");
                        newJoin = false;
                        chatRoomOpen(this);
                    } else {
                        const myRoomCount = await fetch("/chat/my-rooms/count").then(res => res.text());
                        if(myRoomCount == '5'){
                            alert("채팅방은 최대 5개까지 참여할 수 있습니다.");
                        } else {
                            $(".my-room").removeClass("active-room");
                            const memberCount = await fetch("/chat/in?roomId=" + $(this).data("id")).then(res => res.text());
                            $(this).attr("data-member-count", memberCount);
                            const update = await fetch("/chat/my-rooms").then(res => res.text());
                            $("#my-rooms").replaceWith(update);
                            $(".my-room:last-child").addClass("active-room"); 
                            newJoin = true;
                            chatRoomOpen(this);
                        }
                    }
                } else {
                    alert('로그인 후 이용 가능합니다.');
                    location.href = "/login";
                }
            })();
        });

        $(document).on("click", ".chat-wrapper .chat .head .close", chatRoomClose);

        $(document).on("click", ".room-exit", function(){
            (async () => {
                let roomId = '';
                roomId = $(".active-room").data("id");
                await fetch("/chat/room-exit?roomId=" + roomId).then(res => console.log(res.text()));
                chatRoomClose();
                const update = await fetch("/chat/my-rooms").then(res => res.text());
                $("#my-rooms").replaceWith(update);
            })();
        });
    });

    function chatRoomOpen(_this){
        (async () => {
            const update = await fetch("/chat/update?roomId=" + $(_this).data("id")).then(res => res.text());
            $("#chat-box").replaceWith(update);
            $(".chat-wrapper").stop().fadeOut(200).fadeIn(300);
            $(".right-side").css({'flex-direction':'row','align-items':'center'});
            $(".top3-wrapper").hide();
            $(".top3-wrapper .head").stop().animate({'margin-bottom':'0px'},300);
            $(".top3-wrapper").css({'width':'auto','margin-top':'0px', 'margin-right':'10%'}).show();
            $(".popular, .lastest").css('flex-direction','column');
            $(".chat-wrapper").css('display','flex');
            $(".chat-wrapper .chat .head .title .data").text($(_this).data("name"));
            $(".chat-wrapper .chat .head .title .member-count").text('참여인원 ' + $(_this).data("member-count") + '명');
            $(".room").css({'height':'10%','margin':'20px 20px'});
            $(".search-result-outer-wrapper").hide();
            $(".chat-wrapper .regist-btn .btn").remove();
            const div = document.createElement("div");
            div.setAttribute("class","btn send-btn-" + $(_this).data("id"));
            div.innerHTML = "<img src='/img/icon/send-fill.svg'>";
            $(".chat-wrapper .regist-btn .count").before(div);
            subscribe($(_this).data("id"));
        })();
    }

    function chatRoomClose(){
        $(".my-room").removeClass("active-room");
        $(".right-side").css({'flex-direction':'column','align-items':'center'});
        $(".top3-wrapper").hide();
        $(".top3-wrapper .head").stop().animate({'margin-bottom':'40px'},300);
        $(".top3-wrapper").css({'width':'100%','margin-top':'80px', 'margin-right':'0'}).show();
        $(".popular, .lastest").css('flex-direction','row');
        $(".chat-wrapper").hide();
        $(".info-wrapper").remove();
        $(".content-wrapper").remove();
        $(".room").css({'height':'200px','margin':'0px 20px'});
    }

    $(".search-input").keyup(function(){
        let inputVal = $(this).val();
        if(inputVal != '' && inputVal.length > 1){
            keySearch(inputVal);
        } else {
            $(".search-result-apart").remove();
            $(".search-result-outer-wrapper").hide();
        }
    }).click(function(){
        if($(this).val()!='') $(".search-result-outer-wrapper").show();
    });

    
    $(".search-wrapper .reset").click(function () {
        $('.search-input').val('');
        $(".search-result-chat-room").remove();
        $(".search-result-outer-wrapper").hide();
    });

    const resResult = inputVal => fetch("/chat/input-search?inputVal=" + inputVal).then(res => res.text());     
    let prevResult;

    function keySearch(inputVal){
        (async () => {
            try {
                let result = await resResult(inputVal);
                $('#search-input-results').replaceWith(result);
                $(".search-result-apart-wrapper").css('display',$(".search-result-apart-wrapper").children().length == 0 ? 'none' : 'flex');
                if(result != prevResult){
                    $(".search-result-outer-wrapper").show();
                    prevResult = result;
                }
            } catch (e) {
                console.log(e);
            }
        })();
    }

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

   
   

    $(".chat-input").keyup(function(){
        $(".chat-wrapper .regist-btn .count").text($(this).val().length + '/300');
    });

    $('html').click(function (e) {
        if (!$(e.target).is(".search-result-wrapper,"
                            +".search-result-wrapper div,"
                            +".search-result-wrapper span,"
                            +".search-result-wrapper strong,"
                            +".search-result-wrapper img,"
                            +".search-result-wrapper input,"
                            +".search-input")) {
          $(".search-result-outer-wrapper").hide();
        }
      });
      $(".search-input").click(function(){
        if($(this).val()!=''){
            $(".search-result-wrapper").show();
            $(".search-result-outer-wrapper").show();
        }
      });
});

let newJoin = false;
let client, sock;
let currentSubscribe;

function subscribe(_roomId) {
        var messageInput = $('.chat-input');
        var sendBtn = $('.send-btn-' + _roomId);
        var roomId = _roomId;
        sock = new SockJS("/ws");
        client = Stomp.over(sock); 
    
        client.connect({},function(){
            if(newJoin) {
                client.send('/publish/chat/in-message', {}, JSON.stringify({chatRoomId: roomId})); 
            }
            if(currentSubscribe != null){
                currentSubscribe.unsubscribe();
            }
            currentSubscribe =  client.subscribe('/subscribe/chat/room/' + roomId, function (chat) {
                (async () => {
                    const content = JSON.parse(chat.body);
                    if(content.type == 'INFO'){
                        let messagebox = document.createElement("div");
                        messagebox.setAttribute('class','info-wrapper');
                        messagebox.innerHTML = content.message;
                        document.getElementById("chat-box").appendChild(messagebox);
                        $(".chat-wrapper .chat .head .title .member-count").text('참여인원 ' + content.memberCount + '명');
                        $(".my-room .info .title .number").text(content.memberCount + '명');
                    } else {
                            let hour = new Date(content.regDate).getHours();
                            let minute = new Date(content.regDate).getMinutes();
                            let messagebox = document.createElement("div");
                            hour = hour <= 12 ? '오전 ' + hour : '오후' + (Number(hour) - 12);
                            minute = minute < 10 ? '0' + minute : minute;
                            const loginInfo = await fetch("/login-info").then(res => res.json());
                            if(content.member.phone == loginInfo.phone){
                                messagebox.setAttribute('class','content-wrapper my-chat');
                                messagebox.innerHTML = 
                                "<div class='content'>"
                                    + content.message
                                    + "<strong>" + content.member.nickname + "</strong>"
                                    + "<div class='date'>" + hour + ":" + minute + "</div>"
                                + "</div>"
                                + "<div class='profile-wrapper'>"
                                + "<img src='" + content.member.profileImgPath + "'>"
                            + "</div>"
                            } else {
                                messagebox.setAttribute('class','content-wrapper');
                                messagebox.innerHTML = 
                                "<div class='profile-wrapper'>"
                                    + "<img src='" + content.member.profileImgPath + "'>"
                                + "</div>"
                                + "<div class='content'>"
                                    + "<strong>" + content.member.nickname + "</strong>"
                                    + content.message
                                    + "<div class='date'>" + hour + ":" + minute + "</div>"
                                + "</div>"
                            }
                            document.getElementById("chat-box").appendChild(messagebox);
                    }
                })();
            });
        
            sendBtn.click(function () {
                var message = messageInput.val();
                client.send('/publish/chat/message', {}, JSON.stringify({chatRoomId: roomId, message: message}));
                messageInput.val('');
                $(".chat-wrapper .regist-btn .count").text('0/300');
            });
        });
}
