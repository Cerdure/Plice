$(function () {
    $(document).ready(function () {
        $(document).on("mouseover",".custom-overlay", function(){
            $(this).parent().css('z-index','1');
        });
        $(document).on("mouseleave",".custom-overlay", function(){
            $(this).parent().css('z-index','0');
        });
        $(document).on("click",".topData", function(){
            panTo($(this).data("lat"),$(this).data("lng"));
        });
        $(document).on("click", ".item", function(){
            $(".item").removeClass("active-item");
            $(this).addClass("active-item");
            const detailXml = apartDetailXmls[$(this).data('id')];
            const aptName = detailXml.querySelector("kaptName").innerHTML;
            const kaptAddr = detailXml.querySelector("kaptAddr").innerHTML;
            const doroJuso = detailXml.querySelector("doroJuso");
            const address = doroJuso == null ? kaptAddr.substring(0, kaptAddr.indexOf(aptName)) : doroJuso.innerHTML;
            const codeSaleNm = detailXml.querySelector("codeSaleNm");
            const kaptDongCnt = detailXml.querySelector("kaptDongCnt");
            const hoCnt = detailXml.querySelector("hoCnt");
            const kaptdaCnt = detailXml.querySelector("kaptdaCnt");
            const kaptBcompany = detailXml.querySelector("kaptBcompany");
            const kaptAcompany = detailXml.querySelector("kaptAcompany");
            const kaptTel = detailXml.querySelector("kaptTel");
            const kaptFax = detailXml.querySelector("kaptFax");
            const codeHeatNm = detailXml.querySelector("codeHeatNm");
            const codeMgrNm = detailXml.querySelector("codeMgrNm");
            const codeHallNm = detailXml.querySelector("codeHallNm");
            const kaptUsedate = detailXml.querySelector("kaptUsedate");
            const kaptTarea = detailXml.querySelector("kaptTarea");
            const kaptMarea = detailXml.querySelector("kaptMarea");
            const privArea = detailXml.querySelector("privArea");
            const kaptMparea_60 = detailXml.querySelector("kaptMparea_60");
            const kaptMparea_85 = detailXml.querySelector("kaptMparea_85");
            const kaptMparea_135 = detailXml.querySelector("kaptMparea_135");
            const kaptMparea_136 = detailXml.querySelector("kaptMparea_136");

            $(".apart-detail-wrapper .name").text(aptName);
            $(".apart-detail-wrapper .address").text(address);
            $(".apart-detail-wrapper .codeSaleNm .data").text(codeSaleNm == null ? '-' : codeSaleNm.innerHTML);
            $(".apart-detail-wrapper .kaptDongCnt .data").text(kaptDongCnt == null ? '-' : kaptDongCnt.innerHTML);
            $(".apart-detail-wrapper .hoCnt .data").text(hoCnt == null ? '-' : hoCnt.innerHTML);
            $(".apart-detail-wrapper .kaptdaCnt .data").text(kaptdaCnt == null ? '-' : kaptdaCnt.innerHTML);
            $(".apart-detail-wrapper .kaptBcompany .data").text(kaptBcompany == null ? '-' : kaptBcompany.innerHTML);
            $(".apart-detail-wrapper .kaptAcompany .data").text(kaptAcompany == null ? '-' : kaptAcompany.innerHTML);
            $(".apart-detail-wrapper .kaptTel .data").text(formatPhoneNumber(kaptTel == null ? '-' : kaptTel.innerHTML));
            $(".apart-detail-wrapper .kaptFax .data").text(formatPhoneNumber(kaptFax == null ? '-' : kaptFax.innerHTML));
            $(".apart-detail-wrapper .codeHeatNm .data").text(codeHeatNm == null ? '-' : codeHeatNm.innerHTML);
            $(".apart-detail-wrapper .codeMgrNm .data").text(codeMgrNm == null ? '-' : codeMgrNm.innerHTML);
            $(".apart-detail-wrapper .codeHallNm .data").text(codeHallNm == null ? '-' : codeHallNm.innerHTML);
            $(".apart-detail-wrapper .kaptUsedate .year").text(kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(0,4));
            $(".apart-detail-wrapper .kaptUsedate .month").text(kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(4,6));
            $(".apart-detail-wrapper .kaptUsedate .day").text(kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(6));
            $(".apart-detail-wrapper .kaptTarea .data").text(kaptTarea == null ? '-' : kaptTarea.innerHTML);
            $(".apart-detail-wrapper .kaptMarea .data").text(kaptMarea == null ? '-' : kaptMarea.innerHTML);
            $(".apart-detail-wrapper .privArea .data").text(privArea == null ? '-' : privArea.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_60 .data").text(kaptMparea_60 ==null ? '-' : kaptMparea_60.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_85 .data").text(kaptMparea_85 == null ? '-' : kaptMparea_85.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_135 .data").text(kaptMparea_135 == null ? '-' : kaptMparea_135.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_136 .data").text(kaptMparea_136 == null ? '-' : kaptMparea_136.innerHTML);
            $(".apart-detail-wrapper").stop().fadeIn(100);
            moveToPlace(this);
        });
        $(document).on("click", ".apart-detail .close-btn", function(){
            $(".item").removeClass("active-item");
            $(".apart-detail-wrapper").stop().fadeOut(100);
        }); 
        $(document).on("click", ".trend-wrapper .fold", function(){
            if (!$(this).hasClass("clicked")) {
                $(this).addClass("clicked").css('transform', 'translateY(-50%) rotate(180deg)');
                $(".trend-wrapper").css('overflow', 'visible');
            } else {
                $(this).removeClass("clicked").css('transform', 'translateY(-50%) rotate(0)');
                $(".trend-wrapper").css('overflow', 'hidden');
            }
        });
        $(document).on("click",".search-result-apart", function(){
            let apartName = $(this).data("value");
            findApartData('', apartName);
            moveToPlace(this);
        });
        $(document).on("click",".search-result-address", function(){
            let address = $(this).data("value");
            findApartData(address, '');
            searchRegion(address);
        }); 
    });


    options = {
        center: new kakao.maps.LatLng(37.5338259242698, 126.896882129276),
        level: 5
    };
    map = new kakao.maps.Map(document.getElementById('map'), options);
    map.addControl(new kakao.maps.MapTypeControl(), kakao.maps.ControlPosition.TOPRIGHT);
    map.addControl(new kakao.maps.ZoomControl(), kakao.maps.ControlPosition.RIGHT);
    map.setMaxLevel(8);

    let clusterer = new kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 6,
        gridSize: 200,
        styles: [
            {
                padding: '30px',
                paddingTop: '10px',
                paddingBottom: '17px',
                lineHeight: '20px',
                backgroundImage: 'url("/img/icon/window2.png")',
                backgroundSize: 'contain',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                fontSize: '16px',
                textAlign: 'center',
                color: 'white',
                textShadow: '0 0 5px black',
                filter: 'drop-shadow(rgba(0, 0, 0, 0.4) 2px 4px 2px) saturate(0.7) brightness(1.1)',
                animation: 'floating 1s infinite'
            }
        ]
    });

    clusterer.setTexts(function( size ) {
        let text = '';
            text = size + "<clusterUnit>건</clusterUnit>";
        return text;
    });

    const findData = region => fetch ("/find-data?region=" + region).then(res => res.json());
    const apartDetailData = kaptCode => fetch ('http://apis.data.go.kr/1613000/AptBasisInfoService1/getAphusBassInfo' 
    + '?' + encodeURIComponent('serviceKey') + '='+'t6FF%2FNmZ7E6CgombvVgPJl7z0Yv5oDesGF%2Bm78Hv%2BT5IMNGTDRd53t0wkPd9%2FoEx7X522aovCygHADH31fbWTg%3D%3D' 
    + '&' + encodeURIComponent('kaptCode') + '=' + encodeURIComponent(kaptCode)).then(res => res.text());

    function updateData(region){
        (async () => {
            try {
                let markers = [], customOverlaies = [];
                findDataList = await findData(region);
                for(const tradeData of findDataList[0]) {
                    if(!isNaN(tradeData.price)){
                        markers.push(new kakao.maps.Marker({
                            position : new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                            opacity: 1,
                            range: 100
                        }))
                        customOverlaies.push(new kakao.maps.CustomOverlay({
                            position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                            content: '<div class ="custom-overlay">'
                                + '<div class = info>'
                                + '<div class = "area">'
                                + Math.floor(tradeData.area) + '평' 
                                + '</div>'
                                + '<div class = "price">'
                                + ((tradeData.price / 10000) < 1 ? '' : Math.floor(tradeData.price / 10000) + '억')
                                + ((tradeData.price % 10000) / 1000 < 1 ? '' : Math.floor((tradeData.price % 10000) / 1000) + '천')
                                + (tradeData.price < 1000 ? tradeData.price + '만' : '')
                                +'</div>'
                                +'</div>'
                                +'</div>'
                        })) 
                    }
                }
                markers.forEach(marker => marker.setOpacity(0));
                clusterer.clear();
                clusterer.addMarkers(markers);
                $(".custom-overlay").remove();
                if(map.getLevel() < 6){
                    customOverlaies.forEach(CustomOverlay => CustomOverlay.setMap(map));
                }
                appendApartList(findDataList[1])
            } catch (e) {
                console.log(e);
            }
        })();
    }    
    updateData("당산동");

    function appendApartList(apartDataList){
        (async () => {
            let idx = 1;
            $(".item-wrapper .loading").stop().show(); 
            $(".item").remove();
            apartDetailXmls = [];
            for(let apartData of apartDataList){
                $(".right-box .loading").hide();
                const apartDetail = await apartDetailData(apartData.complexCode);
                const xml = new DOMParser().parseFromString(apartDetail, "text/xml");
                let thisTradeData;
                console.log(xml);
                try {
                    thisTradeData = findDataList[0].find(e => e.address == xml.querySelector("doroJuso").innerHTML);
                } catch(e) {
                    console.log(e);
                    thisTradeData = findDataList[0].find(e => e.address == xml.querySelector("kaptAddr").innerHTML);
                }
                const itemDiv = document.createElement('div');
                const aptName = xml.querySelector("kaptName").innerHTML;
                const kaptAddr = xml.querySelector("kaptAddr").innerHTML;
                const address = xml.querySelector("doroJuso") == null ? 
                                kaptAddr.substring(0, kaptAddr.indexOf(aptName)) : 
                                xml.querySelector("doroJuso").innerHTML;
                const kaptdaCnt = xml.querySelector("kaptdaCnt");
                const kaptUsedate = xml.querySelector("kaptUsedate");                 
                    itemDiv.setAttribute('class','item');
                    itemDiv.dataset.id = idx -1;
                    itemDiv.dataset.value = address;
                    itemDiv.innerHTML = 
                    "<div class='left'>"  
                        + "<img src='/img/icon/building-fill.svg'>"  
                    + "</div>" 
                    + "<div class='right'>" 
                        + "<div class='detail'>" 
                            + "<span class='text'>상세정보</span>" 
                            + "<img src='/img/icon/chevron-right.svg'>"
                        + "</div>"
                        + "<div class='name'>" + aptName + "</div>"
                        + "<div class='address'>" + address + "</div>"
                        + "<div class='data-wrapper'>"
                            + "<span class='text'>최근 거래 정보</span>"
                            + "<div class='area'>"
                                + "<span class='data'>" 
                                    + (thisTradeData == null ? '-' : Math.floor(thisTradeData.area) + '평')
                                + "</span>"
                            + "</div>"
                            + "<div class='price'>"
                                + "<span class='data-1'>" 
                                    + (thisTradeData == null ? '' : 
                                    ((thisTradeData.price/10000) < 1 ? '' : Math.floor(thisTradeData.price/10000) + '억'))
                                + "</span>"
                                + "<span class='data-2'>"
                                    + (thisTradeData == null ? '' : 
                                    ((thisTradeData.price%10000)/1000 < 1 ? '' : Math.floor((thisTradeData.price%10000)/1000) + '천')) 
                                + "</span>"
                            + "</div>"
                        + "</div>"
                        + "<div class='data-wrapper'>"
                            + "<span class='text'>세대수</span>"
                            + "<span class='data'>"
                                + (kaptdaCnt == null ? '-' : kaptdaCnt.innerHTML) + '세대'
                            + "</span>"
                        + "</div>"
                        + "<div class='data-wrapper'>"
                            + "<span class='text'>사용승인일</span>"
                            + "<span class='data'>"
                                + (kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(0,4)) + '년 '
                                + (kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(4,6)) + '월 '
                                + (kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(6)) + '일'
                            + "</span>"
                        + "</div>"
                    + "</div>";
                document.getElementById("items").appendChild(itemDiv);
                apartDetailXmls.push(xml);
                $(".item-wrapper .loading").text((idx / apartDataList.length * 100).toFixed(1) + '%');
                idx++;
                if(idx >= apartDataList.length){
                    $(".item-wrapper .loading").fadeOut(300);
                }
            }
        })();
    }
 
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
        switch(map.getLevel()){
            case 1: $(".custom-overlay-region").css('transform','scale(4)'); break;
            case 2: $(".custom-overlay-region").css('transform','scale(2.4)'); break;
            case 3: $(".custom-overlay-region").css('transform','scale(1.5)'); break;
            case 4: $(".custom-overlay-region").css('transform','scale(1)'); break;
            case 5: $(".custom-overlay-region").css('transform','scale(0.4)'); break;
            case 6: $(".custom-overlay-region").css('transform','scale(0.2)'); break;
            case 7: $(".custom-overlay-region").css('transform','scale(0.1)'); break;
            case 8: $(".custom-overlay-region").css('transform','scale(0)'); break;
        }
        $(".custom-overlay-apart").parent().css('z-index','20');
    });





    // 검색 시작 ------------------------------------------------------------------------

    
    $(".search-input").keyup(function(event){
        let inputVal = $(this).val();
        if(event.keycode == 13 || event.which == 13){
            moveToPlace(this); return;
        } else if(inputVal != '' && inputVal.length > 1){
            keySearch(inputVal);
        } else {
            $(".search-result-apart").remove();
            $(".search-result-address").remove();
            $(".search-result-outer-wrapper").hide();
        }
    });

    const resResult = inputVal => fetch("/map/input-search?inputVal=" + inputVal).then(res => res.text());     
    let prevResult;
    function keySearch(inputVal){
        (async () => {
            try {
                let result = await resResult(inputVal);
                $('#search-input-results').replaceWith(result);
                $(".search-result-apart-wrapper").css('display',$(".search-result-apart-wrapper").children().length == 0 ? 'none' : 'flex');
                $(".search-result-address-wrapper").css('display',$(".search-result-address-wrapper").children().length == 0 ? 'none' : 'flex');
                if(result != prevResult){
                    $(".search-result-outer-wrapper").show();
                    prevResult = result;
                }
            } catch (e) {
                console.log(e);
            }
        })();
    }

  $(".top-wrapper .left-box .reset").click(function () {
    $('.search-input').val('');
    $(".search-result-apart").remove();
    $(".search-result-address").remove();
    $(".search-result-outer-wrapper").hide();
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
  
// 검색 끝 ------------------------------------------------------------------------

searchAddrFromCoords(map.getCenter(), displayCenterInfo);

kakao.maps.event.addListener(map, 'idle', function() {
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

function searchAddrFromCoords(coords, callback) {
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById('centerAddr');

        for(var i = 0; i < result.length; i++) {
            if (result[i].region_type === 'H') {  // 행정동의 region_type 값은 'H'
                infoDiv.innerHTML = result[i].address_name;
                break;
            }
        }
    }    
}


let trendIdx = 1;

setInterval(function(){
    $(".trend-wrapper ul").stop().animate({'bottom': (50*trendIdx)+'px'},300,function(){
        if(trendIdx == 10){
            $(".trend-wrapper ul").css('bottom','0px');
            trendIdx = 1;
        } else {
            trendIdx++;
        }
    });
},3000);

const findApart = (address, apartName) => fetch("/find-apart?region=" + address + "&apart=" + apartName).then(res => res.json());

function findApartData(address, apartName) {
    (async () => {
            const apartList = await findApart(address, apartName);
            console.log(apartList);
            appendApartList(apartList);
    })();
}




});











// -----------------------------------------------------------------------------------------------------

let options, map, searchType, keyword,
    ps = new kakao.maps.services.Places(),
    geocoder = new kakao.maps.services.Geocoder(),
    findDataList, apartDetailXmls = [];

// -----------------------------------------------------------------------------------------------------

function moveToPlace(_this) {
    (async () => {
        $(".search-result-wrapper").hide();
        $(".search-result-outer-wrapper").hide();
        if($(_this).prop('tagName') == 'DIV'){
            searchType = 'apt';
            keyword = $(_this).data("value");
        } else {
            searchType = '';
            keyword = $(_this).val();
        }
        if (!keyword.replace(/^\s+|\s+$/g, '')) {
            alert('키워드를 입력해주세요!');
            return false;
        }
        ps.keywordSearch( keyword, placesSearchCB); 
    })();
}

const coordResolver = address => {
    return new Promise((resolve, reject) => {
        geocoder.addressSearch(address, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                resolve(result);
            } else {
                reject(status);
            }
        });
    });
};

function searchRegion(_address) {
    $(".search-result-outer-wrapper").hide();
    (async () => {
        keyword = _address;
        $('.custom-overlay-apart').remove();
        $('.custom-overlay-region').remove();
        let place = await coordResolver(_address),
            position = new kakao.maps.LatLng(place[0].y, place[0].x);
            overlay = new kakao.maps.CustomOverlay({
                position: position,
                content: '<div class ="custom-overlay-region"></div>'
            });
        overlay.setMap(map);
        let bounds = new kakao.maps.LatLngBounds().extend(position);
        map.setBounds(bounds);
        map.setLevel(4);
    })();
}

function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        displayPlaces(data);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

function displayPlaces(places) {
    $('.custom-overlay-apart').remove();
    $('.custom-overlay-region').remove();
    let overlay = new kakao.maps.CustomOverlay({
        position: new kakao.maps.LatLng(places[0].y, places[0].x),
        content: searchType == 'apt'
            ? '<div class ="custom-overlay-apart"></div>'
            : '<div class ="custom-overlay-region"></div>'
    });
    overlay.setMap(map);
    let bounds = new kakao.maps.LatLngBounds(),
        placePosition = new kakao.maps.LatLng(places[0].y, places[0].x);
    bounds.extend(placePosition);
    map.setBounds(bounds);
    map.setLevel(4);
}





function panTo(lat, lng) {
    let moveLatLon = new kakao.maps.LatLng(lat, lng);
    map.panTo(moveLatLon);
}

function zoomIn() {
    let level = map.getLevel();
    map.setLevel(level - 1); 
    displayLevel();
}

function zoomOut() {
    let level = map.getLevel();
    map.setLevel(level + 1);
    displayLevel();
}

const formatPhoneNumber = (input) => {
    const cleanInput = input.replaceAll(/[^0-9]/g, "");
    let result = "";
    const length = cleanInput.length;
    if(length === 8) {
        result = cleanInput.replace(/(\d{4})(\d{4})/, '$1-$2');
    } else if(cleanInput.startsWith("02") && (length === 9 || length === 10)) {
        result = cleanInput.replace(/(\d{2})(\d{3,4})(\d{4})/, '$1-$2-$3');
    } else if(!cleanInput.startsWith("02") && (length === 10 || length === 11)) {
        result = cleanInput.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
    } else {
        result = undefined;
    }
    return result;
}
