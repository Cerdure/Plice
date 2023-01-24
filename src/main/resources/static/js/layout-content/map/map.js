$(function () {
    $(document).ready(function () {
        $(document).on("mouseover", ".custom-overlay", function () {
            $(this).parent().css('z-index', '1');
        });

        $(document).on("mouseleave", ".custom-overlay", function () {
            $(this).parent().css('z-index', '0');
        });

        $(document).on("click", ".topData", function () {
            panTo($(this).data("lat"), $(this).data("lng"));
        });

        $(document).on("click", ".item, .custom-overlay-apart, .custom-overlay-apart-pin", function () {
            $(".item").removeClass("active-item");
            const id = $(this).data('id');
            $(".item[data-id=" + id + "]").addClass("active-item");
            const detailXml = apartDetailXmls[id],
                aptName = detailXml.querySelector("kaptName").innerHTML,
                kaptAddr = detailXml.querySelector("kaptAddr").innerHTML,
                doroJuso = detailXml.querySelector("doroJuso"),
                address = doroJuso == null ? kaptAddr.substring(0, kaptAddr.indexOf(aptName)) : doroJuso.innerHTML,
                codeSaleNm = detailXml.querySelector("codeSaleNm"),
                kaptDongCnt = detailXml.querySelector("kaptDongCnt"),
                hoCnt = detailXml.querySelector("hoCnt"),
                kaptdaCnt = detailXml.querySelector("kaptdaCnt"),
                kaptBcompany = detailXml.querySelector("kaptBcompany"),
                kaptAcompany = detailXml.querySelector("kaptAcompany"),
                kaptTel = detailXml.querySelector("kaptTel"),
                kaptFax = detailXml.querySelector("kaptFax"),
                codeHeatNm = detailXml.querySelector("codeHeatNm"),
                codeMgrNm = detailXml.querySelector("codeMgrNm"),
                codeHallNm = detailXml.querySelector("codeHallNm"),
                kaptUsedate = detailXml.querySelector("kaptUsedate"),
                kaptTarea = detailXml.querySelector("kaptTarea"),
                kaptMarea = detailXml.querySelector("kaptMarea"),
                privArea = detailXml.querySelector("privArea"),
                kaptMparea_60 = detailXml.querySelector("kaptMparea_60"),
                kaptMparea_85 = detailXml.querySelector("kaptMparea_85"),
                kaptMparea_135 = detailXml.querySelector("kaptMparea_135"),
                kaptMparea_136 = detailXml.querySelector("kaptMparea_136");
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
            $(".apart-detail-wrapper .kaptUsedate .year").text(kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(0, 4));
            $(".apart-detail-wrapper .kaptUsedate .month").text(kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(4, 6));
            $(".apart-detail-wrapper .kaptUsedate .day").text(kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(6));
            $(".apart-detail-wrapper .kaptTarea .data").text(kaptTarea == null ? '-' : kaptTarea.innerHTML);
            $(".apart-detail-wrapper .kaptMarea .data").text(kaptMarea == null ? '-' : kaptMarea.innerHTML);
            $(".apart-detail-wrapper .privArea .data").text(privArea == null ? '-' : privArea.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_60 .data").text(kaptMparea_60 == null ? '-' : kaptMparea_60.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_85 .data").text(kaptMparea_85 == null ? '-' : kaptMparea_85.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_135 .data").text(kaptMparea_135 == null ? '-' : kaptMparea_135.innerHTML);
            $(".apart-detail-wrapper .kaptMparea_136 .data").text(kaptMparea_136 == null ? '-' : kaptMparea_136.innerHTML);
            $(".apart-detail-wrapper").stop().fadeIn(100);
            moveToPlace(this);
        });

        $(document).on("mouseover", ".item, .custom-overlay-apart", function () {
            pinOverlaies.forEach(e => e.setMap(null));
            const place = apartCoords[$(this).data('id')];
            if (place != null) {
                const overlay = new kakao.maps.CustomOverlay({
                    position: new kakao.maps.LatLng(place[0].y, place[0].x),
                    content: '<div class ="custom-overlay-apart-pin" data-id="' + $(this).data('id') + '"></div>'
                });
                overlay.setMap(map);
                pinOverlaies.push(overlay);
            }
        });

        $(document).on("click", ".apart-detail .close-btn", function () {
            $(".item").removeClass("active-item");
            $(".apart-detail-wrapper").stop().fadeOut(100);
        });

        trendInterval = setInterval(trendAnimation, 3000);

        $(document).on("click", ".trend-wrapper .fold", function () {
            if (!$(this).hasClass("clicked")) {
                clearInterval(trendInterval);
                $(this).addClass("clicked").css({ 'transform': 'translateY(-50%) rotate(180deg)', 'top': '20px' });
                $(".trend-wrapper ul").css('bottom', '0px');
                trendIdx = 1;
                $(".trend-wrapper").animate({ 'height': '500px' }, 300);
            } else {
                trendInterval = setInterval(trendAnimation, 3000);
                $(".trend-wrapper").animate({ 'height': '50px' }, 300, function () {
                    $(".trend-wrapper .fold").removeClass("clicked").css({ 'transform': 'translateY(-50%) rotate(0)', 'top': '50%' });
                });
            }
        });

        $(document).on("click", ".search-result-apart", function () {
            $(".custom-overlay-apart").remove();
            let apartName = $(this).data("value");
            $(".search-input").val(apartName);
            currentItemsIdx = 0;
            update = true;
            findApartData('', apartName);
            moveToPlace(this);
        });

        $(document).on("click", ".search-result-address", function () {
            $(".custom-overlay-apart").remove();
            let address = $(this).data("value");
            $(".search-input").val(address);
            currentItemsIdx = 0;
            update = true;
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
        styles: [{
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
        }]
    });

    clusterer.setTexts(function (size) {
        let text = '';
        text = size + "<clusterUnit>건</clusterUnit>";
        return text;
    });

    const apartDetailData = kaptCode => fetch('http://apis.data.go.kr/1613000/AptBasisInfoService1/getAphusBassInfo'
        + '?' + encodeURIComponent('serviceKey') + '=' + 't6FF%2FNmZ7E6CgombvVgPJl7z0Yv5oDesGF%2Bm78Hv%2BT5IMNGTDRd53t0wkPd9%2FoEx7X522aovCygHADH31fbWTg%3D%3D'
        + '&' + encodeURIComponent('kaptCode') + '=' + encodeURIComponent(kaptCode)).then(res => res.text());

    const searchInput = $(".search-input");
    const searchInputVal = searchInput.val();
    const searchApart = $("#search-input-apart");
    const searchApartVal = searchApart.data("value");
    const searchAddress = $("#search-input-address");
    const searchAddressVal = searchAddress.data("value");
    updateData(searchInputVal != '' ? searchInputVal : "당산동");

    function updateData(region) {
        (async () => {
            try {
                let markers = [], idx = 0; customOverlaies = [];
                findDataList = await fetch("/find-data?region=" + region).then(res => res.json());
                for (const tradeData of findDataList[0]) {
                    if (!isNaN(tradeData.price)) {
                        markers.push(new kakao.maps.Marker({
                            position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                            opacity: 1,
                            range: 100
                        }));
                        customOverlaies.push(new kakao.maps.CustomOverlay({
                            position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                            content: '<div class ="custom-overlay" data-id="' + idx + '">'
                                + '<div class = info>'
                                + '<div class = "area">'
                                + Math.floor(tradeData.area) + '평'
                                + '</div>'
                                + '<div class = "price">'
                                + ((tradeData.price / 10000) < 1 ? '' : Math.floor(tradeData.price / 10000) + '억')
                                + ((tradeData.price % 10000) / 1000 < 1 ? '' : Math.floor((tradeData.price % 10000) / 1000) + '천')
                                + (tradeData.price < 1000 ? tradeData.price + '만' : '')
                                + '</div>'
                                + '</div>'
                                + '</div>'
                        }));
                        rvCustomOverlaies.push(new kakao.maps.CustomOverlay({
                            position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                            content: '<div class ="custom-overlay" data-id="' + idx + '">'
                                + '<div class = info>'
                                + '<div class = "area">'
                                + Math.floor(tradeData.area) + '평'
                                + '</div>'
                                + '<div class = "price">'
                                + ((tradeData.price / 10000) < 1 ? '' : Math.floor(tradeData.price / 10000) + '억')
                                + ((tradeData.price % 10000) / 1000 < 1 ? '' : Math.floor((tradeData.price % 10000) / 1000) + '천')
                                + (tradeData.price < 1000 ? tradeData.price + '만' : '')
                                + '</div>'
                                + '</div>'
                                + '</div>'
                        }));
                        idx++;
                    }
                }
                markers.forEach(marker => marker.setOpacity(0));
                clusterer.clear();
                clusterer.addMarkers(markers);
                customOverlaies.forEach(e => e.setMap(map));
                if (map.getLevel() >= 6) {
                    $(".custom-overlay").hide();
                }
                apartDataList = findDataList[1];
                currentItemsIdx = 0;

                if (searchInputVal != '') {
                    currentItemsIdx = 0;
                    moveToPlace(searchInput);
                    findApartData(searchInputVal, '');
                    keySearch(searchInputVal);
                    $(".search-result-outer-wrapper").hide();
                } else if (searchApartVal != '' && searchApartVal != null) {
                    $(".custom-overlay-apart").remove();
                    $(".search-input").val(searchApartVal);
                    currentItemsIdx = 0;
                    update = true;
                    findApartData('', searchApartVal);
                    moveToPlace("#search-input-apart");
                } else if (searchAddressVal != '' && searchAddressVal != null) {
                    $(".custom-overlay-apart").remove();
                    $(".search-input").val(searchAddressVal);
                    currentItemsIdx = 0;
                    update = true;
                    findApartData(searchAddressVal, '');
                    searchRegion(searchAddressVal);
                } else {
                    appendApartList();
                }
            } catch (e) {
                console.log(e);
            }
        })();
    }

    function appendApartList() {
        (async () => {
            console.log("appendApart")
            let endNum = currentItemsIdx + 12 <= apartDataList.length ? currentItemsIdx + 12 : apartDataList.length;
            $(".item-wrapper .loading").stop().show();
            if (update) {
                $(".item").remove();
                apartDetailXmls = [];
                apartCoords = [];
            }
            beforeLoad = false;

            for (let i = currentItemsIdx; i < endNum; i++) {
                $(".right-box .loading").hide();
                const apartData = apartDataList[i];
                const apartDetail = await apartDetailData(apartData.complexCode);
                const xml = new DOMParser().parseFromString(apartDetail, "text/xml");
                let thisTradeData;
                try {
                    thisTradeData = findDataList[0].find(e => e.address == xml.querySelector("doroJuso").innerHTML);
                } catch (e) {
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
                itemDiv.setAttribute('class', 'item');
                itemDiv.dataset.id = i;
                itemDiv.dataset.value = address;
                itemDiv.dataset.code = apartData.complexCode;
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
                        ((thisTradeData.price / 10000) < 1 ? '' : Math.floor(thisTradeData.price / 10000) + '억'))
                    + "</span>"
                    + "<span class='data-2'>"
                    + (thisTradeData == null ? '' :
                        ((thisTradeData.price % 10000) / 1000 < 1 ? '' : Math.floor((thisTradeData.price % 10000) / 1000) + '천'))
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
                    + (kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(0, 4)) + '년 '
                    + (kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(4, 6)) + '월 '
                    + (kaptUsedate == null ? '-' : kaptUsedate.innerHTML.substring(6)) + '일'
                    + "</span>"
                    + "</div>"
                    + "</div>";
                document.getElementById("items").appendChild(itemDiv);
                apartDetailXmls.push(xml);

                const place = await coordResolver(address);
                apartOverlaies.push(new kakao.maps.CustomOverlay({
                    position: new kakao.maps.LatLng(place[0].y, place[0].x),
                    content: '<div class ="custom-overlay-apart" data-id="' + i + '"></div>',
                    map: map
                }));
                rvApartOverlaies.push(new kakao.maps.CustomOverlay({
                    position: new kakao.maps.LatLng(place[0].y, place[0].x),
                    content: '<div class ="custom-overlay-apart" data-id="' + i + '"></div>',
                    map: map
                }));
                apartCoords.push(place);

                $(".item-wrapper .loading").text(((i - currentItemsIdx + 1) / 12 * 100).toFixed(1) + '%');

                if (i >= endNum - 1) {
                    currentItemsIdx = i + 1;
                    beforeLoad = true;
                    loadComplete = i >= apartDataList.length - 1 ? true : false;
                    $(".item-wrapper .loading").fadeOut(300, function () {
                        $(".item-wrapper .loading").text('0%');
                    });
                }
            }
        })();
    }

    $(".top-wrapper .right-box input").click(function () {
        $(".right-box .loading").fadeIn(100, function () {
            updateMarkers();
        });
    });

    $(".top-wrapper .right-box .reset-btn").click(function () {
        $(".right-box .loading").fadeIn(100, function () {
            $(".trade-type input").prop('checked', false);
            $(".area input").prop('checked', false);
            updateMarkers();
        });
    });

    function updateMarkers() {
        const tradeType = [], area = [], markers = [];
        document.querySelectorAll(".trade-type input").forEach(e => { if (e.checked) tradeType.push(e) });
        document.querySelectorAll(".area input").forEach(e => { if (e.checked) area.push(e) });
        customOverlaies.forEach(e => e.setMap(null));
        customOverlaies = [];
        clusterer.clear();
        let idx = 0;

        for (const tradeData of findDataList[0]) {
            const tradeDataArea = Number(tradeData.area);
            if ((tradeType.length == 0 ? true : tradeType.some(e => { return tradeData.tradeType == e.value }))
                && (area.length == 0 ? true : area.some(e => {
                    switch (e.value) {
                        case '1': return (tradeDataArea <= 60);
                        case '2': return (tradeDataArea > 60 && tradeDataArea <= 85);
                        case '3': return (tradeDataArea > 85 && tradeDataArea <= 135);
                        case '4': return (tradeDataArea > 135);
                    }
                }))
            ) {
                if (!isNaN(tradeData.price)) {
                    markers.push(new kakao.maps.Marker({
                        position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                        opacity: 1,
                        range: 100
                    }));
                    customOverlaies.push(new kakao.maps.CustomOverlay({
                        position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                        content: '<div class ="custom-overlay" data-id="' + idx + '">'
                            + '<div class = info>'
                            + '<div class = "area">'
                            + Math.floor(tradeData.area) + '평'
                            + '</div>'
                            + '<div class = "price">'
                            + ((tradeData.price / 10000) < 1 ? '' : Math.floor(tradeData.price / 10000) + '억')
                            + ((tradeData.price % 10000) / 1000 < 1 ? '' : Math.floor((tradeData.price % 10000) / 1000) + '천')
                            + (tradeData.price < 1000 ? tradeData.price + '만' : '')
                            + '</div>'
                            + '</div>'
                            + '</div>'
                    }));
                    rvCustomOverlaies.push(new kakao.maps.CustomOverlay({
                        position: new kakao.maps.LatLng(tradeData.lat, tradeData.lng),
                        content: '<div class ="custom-overlay" data-id="' + idx + '">'
                            + '<div class = info>'
                            + '<div class = "area">'
                            + Math.floor(tradeData.area) + '평'
                            + '</div>'
                            + '<div class = "price">'
                            + ((tradeData.price / 10000) < 1 ? '' : Math.floor(tradeData.price / 10000) + '억')
                            + ((tradeData.price % 10000) / 1000 < 1 ? '' : Math.floor((tradeData.price % 10000) / 1000) + '천')
                            + (tradeData.price < 1000 ? tradeData.price + '만' : '')
                            + '</div>'
                            + '</div>'
                            + '</div>'
                    }));
                    idx++;
                }
            }
        }
        markers.forEach(marker => marker.setOpacity(0));
        clusterer.addMarkers(markers);
        customOverlaies.forEach(e => e.setMap(map));
        $(".right-box .loading").hide();
        if (map.getLevel() >= 6) {
            $(".custom-overlay").hide();
        }
    }

    let update = true,
        beforeLoad = true,
        loadComplete = false;

    $("#items").scroll(function () {
        let st = $(this).scrollTop(),
            ih = $(this).innerHeight(),
            sh = document.querySelector("#items").scrollHeight;

        if ((st + ih >= sh - 1) && beforeLoad && !loadComplete) {
            update = false;
            appendApartList();
        }
    })

    kakao.maps.event.addListener(map, 'center_changed', function () {
        if (map.getLevel() < 6) {
            $(".custom-overlay").show();
        } else {
            $(".custom-overlay").hide();
        }
    });

    kakao.maps.event.addListener(map, 'dragstart', function () {
        if (map.getLevel() < 6) {
            $(".custom-overlay").show();
        } else {
            $(".custom-overlay").hide();
        }
        $(".custom-overlay-apart-pin").remove();
    });

    kakao.maps.event.addListener(map, 'dragend', function () {
        if (map.getLevel() < 6) {
            $(".custom-overlay").show();
            $(".custom-overlay-apart").show();
            $(".custom-overlay-region").show();
        } else {
            $(".custom-overlay").hide();
            $(".custom-overlay-apart").hide();
            $(".custom-overlay-region").hide();
        }
    });

    kakao.maps.event.addListener(map, 'zoom_changed', function () {
        if (map.getLevel() < 6) {
            $(".custom-overlay").show();
            $(".custom-overlay-apart").show();
            $(".custom-overlay-region").show();
        } else {
            $(".custom-overlay").hide();
            $(".custom-overlay-apart").hide();
            $(".custom-overlay-region").hide();
        }
        switch (map.getLevel()) {
            case 1: $(".custom-overlay-region").css('transform', 'scale(4)'); break;
            case 2: $(".custom-overlay-region").css('transform', 'scale(2.4)'); break;
            case 3: $(".custom-overlay-region").css('transform', 'scale(1.5)'); break;
            case 4: $(".custom-overlay-region").css('transform', 'scale(1)'); break;
            case 5: $(".custom-overlay-region").css('transform', 'scale(0.4)'); break;
            case 6: $(".custom-overlay-region").css('transform', 'scale(0.2)'); break;
            case 7: $(".custom-overlay-region").css('transform', 'scale(0.1)'); break;
            case 8: $(".custom-overlay-region").css('transform', 'scale(0)'); break;
        }
    });

    $(".search-input").keyup(function (event) {
        let inputVal = $(this).val();
        if (event.keycode == 13 || event.which == 13) {
            moveToPlace(this);
            currentItemsIdx = 0;
            update = true;
            findApartData(inputVal, '');
            searchRegion(inputVal);
            const result = fetch("/map/keyword-save?keyword=" + inputVal).then(res => res.text());
            $("#trend").replaceWith(result);
            return;
        } else if (inputVal != '' && inputVal.length > 1) {
            keySearch(inputVal);
        } else {
            $(".search-result-apart").remove();
            $(".search-result-address").remove();
            $(".search-result-outer-wrapper").hide();
        }
    });

    let prevResult;

    function keySearch(inputVal) {
        (async () => {
            try {
                let result = await fetch("/map/input-search?inputVal=" + inputVal).then(res => res.text());
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

    $(".top-wrapper .left-box .reset").click(function () {
        $('.search-input').val('');
        $(".search-result-apart").remove();
        $(".search-result-address").remove();
        $(".search-result-outer-wrapper").hide();
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


    searchAddrFromCoords();

    kakao.maps.event.addListener(map, 'idle', searchAddrFromCoords);

    function searchAddrFromCoords() {
        geocoder.coord2Address(map.getCenter().getLng(), map.getCenter().getLat(), function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
                const infoDiv = document.getElementById('centerAddr'),
                    addrArray = result[0].address.address_name.split(" ");
                currentCenterAddr = addrArray[addrArray.length - 2];
                infoDiv.innerHTML = '';
                for (let i = 0; i < addrArray.length - 1; i++) {
                    infoDiv.innerHTML += addrArray[i] + " ";
                }
            }
        });
    }

    $("#currentCenterSearch").click(function () {
        $(".custom-overlay-apart").remove();
        $(".search-input").val(currentCenterAddr);
        currentItemsIdx = 0;
        update = true;
        findApartData(currentCenterAddr, '');
        searchRegion(currentCenterAddr);
    });

    function findApartData(address, apartName) {
        (async () => {
            $(".item-wrapper .null").hide();
            apartDataList = await fetch("/find-apart?region=" + address + "&apart=" + apartName).then(res => res.json());
            $(".search-result-chart .left-side .data").text("'" + address + apartName + "'");
            $(".search-result-chart .right-side .data").text(apartDataList.length);
            appendApartList();
        })();
    }

    let rvContainer = document.getElementById('roadview'),
        rv = new kakao.maps.Roadview(rvContainer),
        rvClient = new kakao.maps.RoadviewClient(),
        markImage = new kakao.maps.MarkerImage(
            'https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png',
            new kakao.maps.Size(26, 46),
            {
                spriteSize: new kakao.maps.Size(1666, 168),
                spriteOrigin: new kakao.maps.Point(705, 114),
                offset: new kakao.maps.Point(13, 46)
            }
        ),
        marker = new kakao.maps.Marker({
            image: markImage,
            position: new kakao.maps.LatLng(map.getCenter().getLat(), map.getCenter().getLng()),
            draggable: true
        });

    kakao.maps.event.addListener(marker, 'dragend', function (mouseEvent) {
        let position = marker.getPosition();
        toggleRoadview(position);
    });

    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
        if ($(".roadviewControl").hasClass("rv-ctrl-active")) {
            let position = mouseEvent.latLng;
            marker.setPosition(position);
            toggleRoadview(position);
        }
    });

    kakao.maps.event.addListener(rv, 'position_changed', function () {
        let rvPosition = rv.getPosition();
        map.setCenter(rvPosition);
        if (overlayOn) {
            marker.setPosition(rvPosition);
        }
    });

    function toggleRoadview(position) {
        if (position == null) {
            customOverlaies.forEach(e => e.setMap(map));
            apartOverlaies.forEach(e => e.setMap(map));
            rvContainer.style.display = 'none';
            marker.setMap(null);
            $("#map").css({
                'position': 'relative',
                'top': 'auto',
                'right': 'auto',
                'width': '100%',
                'height': '100%',
                'border-radius': '0',
                'box-shadow': 'none',
                'z-index': '0'
            });
            map.relayout();
            map.setCenter(marker.getPosition())
            marker.setMap(null);
        } else {
            rvClient.getNearestPanoId(position, 50, function (panoId) {
                if (panoId === null) {
                    rvContainer.style.display = 'none';
                } else {
                    rvCustomOverlaies.forEach(e => { e.setMap(rv); e.setRange(300); });
                    rvApartOverlaies.forEach(e => { e.setMap(rv); e.setRange(300); });
                    let rMarker = new kakao.maps.Marker({ position: position }),
                        projection = rv.getProjection(),
                        viewpoint = projection.viewpointFromCoords(rMarker.getPosition(), rMarker.getAltitude());
                    rv.setViewpoint(viewpoint);
                    rvContainer.style.display = 'block';
                    rvContainer.style.position = 'fixed';
                    $(".roadview-close").show();
                    rv.setPanoId(panoId, position);
                    $("#map").css({
                        'position': 'fixed',
                        'top': '130px',
                        'right': '10px',
                        'width': '20%',
                        'height': '20%',
                        'border-radius': '5px',
                        'box-shadow': '0 2px 5px 0 black',
                        'z-index': '3'
                    });
                    map.relayout();
                    marker.setMap(map);
                }
            });
        }
    }

    function toggleOverlay(active) {
        if (active) {
            overlayOn = true;
            map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
        } else {
            overlayOn = false;
            map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
        }
    }

    $(".roadviewControl").click(function () {
        if (!$(this).hasClass("rv-ctrl-active")) {
            $(this).addClass("rv-ctrl-active");
            toggleOverlay(true);
        } else {
            $(this).removeClass("rv-ctrl-active");
            toggleOverlay(false);
        }
    });

    $(".custom-overlay-apart-pin, .custom-overlay-apart").click(function () {
        if ($(".roadviewControl").hasClass("rv-ctrl-active")) {
            const position = apartOverlaies[$(this).data("id")].getPosition();
            marker.setPosition(position);
            toggleRoadview(position);
        }
    });
    $(".custom-overlay").click(function () {
        if ($(".roadviewControl").hasClass("rv-ctrl-active")) {
            const position = customOverlaies[$(this).data("id")].getPosition();
            marker.setPosition(position);
            toggleRoadview(position);
        }
    });

    $(".roadview-close").click(function () {
        $(this).hide();
        toggleRoadview();
    });

});


let options, map, searchType, keyword, apartCoords, currentCenterAddr,
    findDataList, tradeDataList, apartDataList, apartDetailXmls, currentItemsIdx,
    customOverlaies = [], apartOverlaies = [], regionOverlaies = [],
    pinOverlaies = [], rvCustomOverlaies = [], rvApartOverlaies = [],
    ps = new kakao.maps.services.Places(), geocoder = new kakao.maps.services.Geocoder(),
    trendIdx = 1, trendInterval;


function moveToPlace(_this) {
    (async () => {
        $(".search-result-wrapper").hide();
        $(".search-result-outer-wrapper").hide();
        console.log(_this)
        if ($(_this).prop('tagName') == 'DIV') {
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
        ps.keywordSearch(keyword, placesSearchCB);
    })();
}

const coordResolver = address => {
    return new Promise((resolve, reject) => {
        geocoder.addressSearch(address, function (result, status) {
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
        apartOverlaies.forEach(e => e.setMap(null));
        regionOverlaies.forEach(e => e.setMap(null));
        try {
            let place = await coordResolver(_address),
                position = new kakao.maps.LatLng(place[0].y, place[0].x);
            overlay = new kakao.maps.CustomOverlay({
                position: position,
                content: '<div class ="custom-overlay-region"></div>'
            });
            overlay.setMap(map);
            regionOverlaies.push(overlay);
            let bounds = new kakao.maps.LatLngBounds().extend(position);
            map.setBounds(bounds);
            map.setLevel(5);
        } catch (e) {
            console.log(e);
            $(".item-wrapper .loading").hide();
            $(".item-wrapper .null").css('display', 'flex');
        }
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
    if (searchType != 'apt') {
        $('.custom-overlay-region').remove();
        let overlay = new kakao.maps.CustomOverlay({
            position: new kakao.maps.LatLng(places[0].y, places[0].x),
            content: '<div class ="custom-overlay-region"></div>'
        });
        overlay.setMap(map);
        regionOverlaies.push(overlay);
    }
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
    if (length === 8) {
        result = cleanInput.replace(/(\d{4})(\d{4})/, '$1-$2');
    } else if (cleanInput.startsWith("02") && (length === 9 || length === 10)) {
        result = cleanInput.replace(/(\d{2})(\d{3,4})(\d{4})/, '$1-$2-$3');
    } else if (!cleanInput.startsWith("02") && (length === 10 || length === 11)) {
        result = cleanInput.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
    } else {
        result = undefined;
    }
    return result;
}

function trendAnimation() {
    $(".trend-wrapper ul").stop().animate({ 'bottom': (50 * trendIdx) + 'px' }, 300, function () {
        if (trendIdx == 10) {
            $(".trend-wrapper ul").css('bottom', '0px');
            trendIdx = 1;
        } else {
            trendIdx++;
        }
    });
}
