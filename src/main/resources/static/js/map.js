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

        let regionCodes = [];
        let tradeDatas = {};

        for (let i = 0; i < 22; i++) {
            $.ajax({
                type: "post",
                url: "http://apis.data.go.kr/1741000/StanReginCd/getStanReginCdList?"
                    + "ServiceKey=t6FF%2FNmZ7E6CgombvVgPJl7z0Yv5oDesGF%2Bm78Hv%2BT5IMNGTDRd53t0wkPd9%2FoEx7X522aovCygHADH31fbWTg%3D%3D"
                    + "&type=json"
                    + "&pageNo=" + i
                    + "&numOfRows=1000"
                    + "&flag=Y",
            }).done(function (data) {
                JSON.parse(data).StanReginCd[1].row.forEach(element => {
                    let regionCode = element.region_cd.substr(0, 5);
                    if (regionCodes.indexOf(regionCode) == -1) {
                        regionCodes.push(regionCode);
                    }
                });
                if (i == 21) {
                    console.log(regionCodes[0]);
                    aptTradeSearch(regionCodes[0], 202201, 202212);
                }
            });
        }


    });
    

    function aptTradeSearch(_regionCode, startDate, endDate) {
        for (let i = startDate; i < endDate + 1; i++) {
            let params = {
                LAWD_CD: _regionCode,
                DEAL_YMD: i,
                serviceKey: "t6FF%2FNmZ7E6CgombvVgPJl7z0Yv5oDesGF%2Bm78Hv%2BT5IMNGTDRd53t0wkPd9%2FoEx7X522aovCygHADH31fbWTg%3D%3D"
            }
            $.ajax({
                type: "post",
                url: "/map/trade-search",
                data: params,
                dataType: "json"
            }).done(function (data) {
              console.log(data);
            });
        }
    }




    var container = document.getElementById('map');
    var options = {
        center: new kakao.maps.LatLng(37.5338259242698, 126.896882129276),
        level: 4
    };

    var map = new kakao.maps.Map(container, options);

    var mapTypeControl = new kakao.maps.MapTypeControl();
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);



    var geocoder = new kakao.maps.services.Geocoder();

    let tradeDatas = [
        {
            address: '서울특별시 영등포구 선유동2로 57',
            price: '12억'
        }, {
            address:'서울 영등포구 당산로47길 11',
            price: '18억'
        }, {
            address: '서울 영등포구 당산로49길 14',
            price: '19억'
        }, {
            address: '서울 영등포구 영신로 247',
            price: '8억'
        }, {
            address: '서울 영등포구 양평로 8',
            price: '11억'
        }, {
            address: '서울 영등포구 양평로 68',
            price: '28억'
        }, {
            address: '서울 영등포구 국회대로34길',
            price: '17억'
        }, {
            address: '서울 영등포구 당산로31길 20',
            price: '6억'
        }, {
            address: '서울 영등포구 당산로47길 14',
            price: '14억'
        }, {
            address: '서울 영등포구 당산로 244',
            price: '22억'
        }, {
            address: '서울 영등포구 당산로45길 1',
            price: '26억'
        }
    ]

    var clusterer = new kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 6,
        calculator: [3, 4],
        styles: [
            {
                width: '52px',
                height: '52px',
                borderRadius: '52px',
                textAlign: 'center',
                lineHeight: '60px',
                backgroundColor: 'rgba(0, 93, 235, 0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '25px',
                fontFamily: 'KOHIBaeumOTF',
            },
            {
                width: '62px',
                height: '62px',
                borderRadius: '52px',
                textAlign: 'center',
                lineHeight: '70px',
                backgroundColor: 'rgb(34, 0, 255, 0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '25px',
                fontFamily: 'KOHIBaeumOTF',
            },
            {
                width: '72px',
                height: '72px',
                borderRadius: '52px',
                textAlign: 'center',
                lineHeight: '80px',
                backgroundColor: 'rgb(81,54,255,0.7)',
                boxShadow: '0 2px 5px 0 black',
                color: 'white',
                fontSize: '25px',
                fontFamily: 'KOHIBaeumOTF',
            }
        ]
    });


    const addressSearch = tradeData => {
        return new Promise((resolve, reject) => {
            geocoder.addressSearch(tradeData.address, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    resolve({"lat": result[0].y, "lng": result[0].x, "price": tradeData.price});
                } else {
                    reject(status);
                }
            });
        });
    };
    
    // async-await
    (async () => {
        try {
            const positions = [];
    
            for(const tradeData of tradeDatas) {
                const result = await addressSearch(tradeData);
                positions.push(result)
            }
    
            var markers = positions.map(function(position) {
                return new kakao.maps.Marker({
                    position : new kakao.maps.LatLng(position.lat, position.lng),
                    opacity: 1
                });
            }); 
            markers.forEach(marker => marker.setOpacity(0));
            clusterer.addMarkers(markers);
    
            var customOverlaies = positions.map(function(position) {
                return new kakao.maps.CustomOverlay({
                position: new kakao.maps.LatLng(position.lat, position.lng),
                content: '<div class ="custom-overlay">'+position.price+'</div>'   
                });
            });
            customOverlaies.forEach(CustomOverlay => CustomOverlay.setMap(map));

        } catch (e) {
            console.log(e);
        }
    })();


    kakao.maps.event.addListener( clusterer, 'clustered', function( clusters ) {
        $(".custom-overlay").hide();
    });

    kakao.maps.event.addListener(map, 'zoom_changed', function() {
        if(map.getLevel() < 6){ 
            $(".custom-overlay").show();
        }
    });


    // function doSomething(idx) {
    //     var callback = function (result, status) {
    //         if (status === kakao.maps.services.Status.OK) {

    //             console.log('지역 명칭 : ' + result[0].address_name);
    //             console.log('행정구역 코드 : ' + result[0].code);
    //         }
    //     };

    //     geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
    // }


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

