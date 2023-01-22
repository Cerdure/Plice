$(function () {

  $(".hits-chart-box .title .text").click(function () {
    $(".hits-chart-box .title .text").removeClass("active-text");
    $(this).addClass("active-text");
    dateType = $(this).data("type");
    drawLineChart();
  });

  $(".chart-type .type").click(function () {
    $(".chart-type .type").removeClass("active-text");
    $(this).addClass("active-text");
    countType = $(this).data("type");
    drawLineChart();
  });
});

let dateType = 'day', countType = 'page';


google.charts.load('current', { 'packages': ['line'] });
google.charts.setOnLoadCallback(drawLineChart);

function drawLineChart() {
  (async () => {
    $(".loading").fadeIn(0);
    var data = new google.visualization.DataTable();
    data.addColumn('string', '');

    switch (countType) {
      case 'total':
        data.addColumn('number', '전체');
        break;
      case 'page':
        data.addColumn('number', '홈페이지');
        data.addColumn('number', '지도');
        data.addColumn('number', '채팅');
        data.addColumn('number', '이야기');
        data.addColumn('number', '컨텐츠');
        break;
    }

    const logCountMap = await fetch("/admin/logCount/" + dateType + "?countType=" + countType).then(res => res.json());

    switch (countType) {

      case 'total':
        $.each(logCountMap, (key, value) => {
          const year = new Date(key).getFullYear().toString().substring(2, 4) + '년',
            month = year + ' ' + (new Date(key).getMonth() + 1).toString() + '월',
            day = month + ' ' + new Date(key).getDate().toString() + '일'

          switch (dateType) {
            case 'day': data.addRow([day, value]); break;
            case 'month': data.addRow([month, value]); break;
            case 'year': data.addRow([year, value]); break;
          }
        });
        break;

      case 'page':
        let homeMap = new Map(),
          mapMap = new Map(),
          chatMap = new Map(),
          postMap = new Map(),
          contentsMap = new Map();
        $.each(logCountMap.home, (key, value) => homeMap.set(key, value));
        $.each(logCountMap.map, (key, value) => mapMap.set(key, value));
        $.each(logCountMap.chat, (key, value) => chatMap.set(key, value));
        $.each(logCountMap.post, (key, value) => postMap.set(key, value));
        $.each(logCountMap.contents, (key, value) => contentsMap.set(key, value));

        $.each(logCountMap.home, (key, value) => {
          const year = new Date(key).getFullYear().toString().substring(2, 4) + '년',
            month = year + ' ' + (new Date(key).getMonth() + 1).toString() + '월',
            day = (new Date(key).getMonth() + 1).toString() + '월 ' + new Date(key).getDate().toString() + '일';

          switch (dateType) {
            case 'day':
              data.addRow([
                day,
                homeMap.get(key),
                mapMap.get(key),
                chatMap.get(key),
                postMap.get(key),
                contentsMap.get(key)
              ]);
              break;
            case 'month':
              data.addRow([
                month,
                homeMap.get(key),
                mapMap.get(key),
                chatMap.get(key),
                postMap.get(key),
                contentsMap.get(key)
              ]);
              break;
            case 'year':
              data.addRow([
                year,
                homeMap.get(key),
                mapMap.get(key),
                chatMap.get(key),
                postMap.get(key),
                contentsMap.get(key)
              ]);
              break;
          }
        });
        break;
    }


    var options = {
      width: '100%',
      height: '100%',
      colors: ['#bd84ff', '#737aff', '#76b4ff', '#77d5f2', '#89d5c8'],
      legend: { position: 'top' },
      vAxis: { gridlines: { count: 1 } }
    };

    var chart = new google.charts.Line(document.getElementById('chart'));
    chart.draw(data, google.charts.Line.convertOptions(options));
    $(".loading").hide();
  })();
}



google.charts.load("current", { packages: ["corechart"] });
google.charts.setOnLoadCallback(drawPieChart);

function drawPieChart() {
  (async () => {
    const pageCount = await fetch("/admin/logCount/daily").then(res => res.json());
    let home, map, chat, post, contents; 
    $.each(pageCount.home, (key, value) => home = value);
    $.each(pageCount.map, (key, value) => map = value);
    $.each(pageCount.chat, (key, value) => chat = value);
    $.each(pageCount.post, (key, value) => post = value);
    $.each(pageCount.contents, (key, value) => contents = value);

    var data = google.visualization.arrayToDataTable([
      ['Task', 'Hours per Day'],
      ['홈페이지', home],
      ['지도', map],
      ['채팅', chat],
      ['이야기', post],
      ['컨텐츠', contents]
    ]);
  
    var options = {
      chartArea: { width: '800px' },
      width: '100%',
      height: '100%',
      chartArea: { top: 20, width: '80%', height: '80%', marginBottom: '40px' },
      pieHole: 0.4,
      backgroundColor: 'transparent',
      colors: ['#6885ff', '#93acfd', '#b3c5ff', '#dbe4ff', '#eef2ff'],
      legend: { position: 'bottom' },
      pieSliceBorderColor: 'cornflowerblue',
      pieSliceTextStyle: { fontSize: 20 }
    };
  
    var chart = new google.visualization.PieChart(document.getElementById('day-chart'));
    chart.draw(data, options);
    const texts = $("#day-chart g text").get();
    console.log(texts)
  })();
}


google.charts.setOnLoadCallback(drawVisualization);

function drawVisualization() {
  (async () => {
    const memberCount = await fetch("/admin/memberCount").then(res => res.json());
    var data = new google.visualization.DataTable();
    data.addColumn('string', '');
    data.addColumn('number', '신규 가입');
    data.addColumn('number', '전일 대비 증감');
    let increase, prev = 50;
    $.each(memberCount, (key, value) => {
      day = (new Date(key).getMonth() + 1).toString() + '월 ' + new Date(key).getDate().toString() + '일';
      increase = value-prev;
      data.addRow([day, value, increase]);
      prev = value;
    });
  
    var options = {
      width: '100%',
      height: '100%',
      seriesType: 'bars',
      series: { 1: { type: 'bars' } },
      colors: ['#6885ff', '#77d5f2'],
      chartArea: { top: 60, left: 100, width: '70%', height: '70%', marginBottom: 0 },
      legend:{position:'top'}
    };
  
    var chart = new google.visualization.ComboChart(document.getElementById('join-chart'));
    chart.draw(data, options);
  })();
}



google.charts.load('current', { 'packages': ['treemap'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  (async () => {
    const result = await fetch("/admin/keywords").then(res => res.json());
    var data = new google.visualization.DataTable();
    data.addColumn('string','keyword');
    data.addColumn('string','parent');
    data.addColumn('number','volume');
    data.addColumn('number','increase/decrease');
    data.addRow(['키워드', null, 0, 0]);
    result.forEach(e => {
      data.addRow([e.keyword, '키워드', e.count, e.count]);
    });

    tree = new google.visualization.TreeMap(document.getElementById('search-chart'));

    tree.draw(data, {
      minColor: '#eef2ff',
      midColor: '#b3c5ff',
      maxColor: '#9bb2ff',
      fontColor: '#316ea5',
      fontFamily: 'ChosunBg',
      fontSize: 14,
      showScale: true,
      headerHeight: 0,
    });
  })();
}

function dateFormat(date) {
  let dateFormat2 = date.getFullYear() +
    '-' + ((date.getMonth() + 1) < 9 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) +
    '-' + ((date.getDate()) < 9 ? "0" + (date.getDate()) : (date.getDate()));
  return dateFormat2;
}

