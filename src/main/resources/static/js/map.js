$(function () {

    $(".item").click(function () {
        $(".item").removeClass("active-item");
        $(this).addClass("active-item");
        $(".apart-detail-wrapper").stop().fadeIn(100);
    });
    $(".apart-detail .close-btn").click(function () {
        $(".item").removeClass("active-item");
        $(".apart-detail-wrapper").stop().fadeOut(100);
    });
    $(".trend-wrapper .fold").click(function () {
        if (!$(this).hasClass("clicked")) {
            $(this).addClass("clicked").css('transform', 'translateY(-50%) rotate(180deg)');
            $(".trend-wrapper").css('overflow', 'visible');
        } else {
            $(this).removeClass("clicked").css('transform', 'translateY(-50%) rotate(0)');
            $(".trend-wrapper").css('overflow', 'hidden');
        }
    });

   

    $(document).ready(function () {
        $(document).on("mouseover",".custom-overlay", function(){
            $(this).parent().css('z-index','1');
        });
        $(document).on("mouseleave",".custom-overlay", function(){
            $(this).parent().css('z-index','0');
        })
    });
    

    var options = {
        center: new kakao.maps.LatLng(37.5338259242698, 126.896882129276),
        level: 5
    };
    var map = new kakao.maps.Map(document.getElementById('map'), options);
    map.addControl(new kakao.maps.MapTypeControl(), kakao.maps.ControlPosition.TOPRIGHT);
    map.addControl(new kakao.maps.ZoomControl(), kakao.maps.ControlPosition.RIGHT);
    map.setMaxLevel(8);

    var clusterer = new kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 6,
        calculator: [3, 5, 20, 60],
        gridSize: 200,
        styles: [
            {
                width: '52px',
                height: '52px',
                borderRadius: '100%',
                textAlign: 'center',
                lineHeight: '57px',
                backgroundColor: 'rgba(15, 178, 235, 0.8)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '18px',
                fontFamily: 'ChosunBg'
            },
            {
                width: '62px',
                height: '62px',
                borderRadius: '100%',
                textAlign: 'center',
                lineHeight: '67px',
                backgroundColor: 'rgb(0, 137, 255, 0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '24px',
                fontFamily: 'ChosunBg'
            },
            {
                width: '90px',
                height: '90px',
                borderRadius: '100%',
                textAlign: 'center',
                lineHeight: '98px',
                backgroundColor: 'rgb(0,98,255,0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '30px',
                fontFamily: 'ChosunBg'
            }, {
                width: '120px',
                height: '120px',
                borderRadius: '100%',
                textAlign: 'center',
                lineHeight: '128px',
                backgroundColor: 'rgb(18,36,255,0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '36px',
                fontFamily: 'ChosunBg'
            }, {
                width: '170px',
                height: '170px',
                borderRadius: '100%',
                textAlign: 'center',
                lineHeight: '178px',
                backgroundColor: 'rgb(96,38,242,0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '42px',
                fontFamily: 'ChosunBg'
            }
        ]
    });
    clusterer.setTexts(function( size ) {
        var text = '';
            text = size + "<clusterUnit>건</clusterUnit>";
        return text;
    });

    const firstFind = fetch("/find-data"
        + "?neLng=" + map.getBounds().getNorthEast().getLng() + "&neLat=" + map.getBounds().getNorthEast().getLat()
        + "&swLng=" + map.getBounds().getSouthWest().getLng() + "&swLat=" + map.getBounds().getSouthWest().getLat())
        .then(res => res.json());

    (async () => {
        try {
            let firstFindDataList = await firstFind;
            let markers = [];
            let customOverlaies = [];
            for(const tradeData of firstFindDataList) {
                if(!isNaN(tradeData.price)){
                    markers.push(new kakao.maps.Marker({
                        position : new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                        opacity: 1,
                        range: 100
                    }))
                    customOverlaies.push(new kakao.maps.CustomOverlay({
                        position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                        content: '<div class ="custom-overlay">'
                            + '<div class = "area">'
                            + Math.floor(tradeData.area) + '평' 
                            + '</div>'
                            + '<div class = "price">'
                            + ((tradeData.price / 10000) < 1 ? '' : Math.floor(tradeData.price / 10000)) + '억'
                            + (Math.floor((tradeData.price % 10000) / 1000) == 0 ? '' : Math.floor((tradeData.price % 10000) / 1000) + '천')
                            +'</div>'
                            +'</div>'
                    })) 
                }
            }
            markers.forEach(marker => marker.setOpacity(0));
            clusterer.addMarkers(markers);
            customOverlaies.forEach(CustomOverlay => CustomOverlay.setMap(map));

        } catch (e) {
            console.log(e);
        }
    })();
 
    kakao.maps.event.addListener(map, 'center_changed', function() {
         if(map.getLevel() < 6){
            $(".custom-overlay").show();
        } else {
            $(".custom-overlay").hide();
        }
    });
    kakao.maps.event.addListener(map, 'dragstart', function() {
        if(map.getLevel() < 6){
            $(".custom-overlay").show();
        } else {
            $(".custom-overlay").hide();
        }
    });
    kakao.maps.event.addListener(map, 'dragend', function() {
        if(map.getLevel() < 6){
            $(".custom-overlay").show();
        } else {
            $(".custom-overlay").hide();
        }
    });
    kakao.maps.event.addListener(map, 'zoom_changed', function() {
        if(map.getLevel() < 6){
            $(".custom-overlay").show();
        } else {
            $(".custom-overlay").hide();
        }
    });

});
let resultX = [];
let resultY = [];

function saveCoord(_x, _y) {
    resultX.push(_x);
    resultY.push(_y);
}

function setCenter() {    // 중심 이동    
    var moveLatLon = new kakao.maps.LatLng(33.452613, 126.570888);
    map.setCenter(moveLatLon);
}

function panTo() {    // 부드럽게 이동
    var moveLatLon = new kakao.maps.LatLng(33.450580, 126.574942);
    map.panTo(moveLatLon);
}



function zoomIn() {
    var level = map.getLevel();
    map.setLevel(level - 1); 
    displayLevel();
}

function zoomOut() {
    var level = map.getLevel();
    map.setLevel(level + 1);
    displayLevel();
}

