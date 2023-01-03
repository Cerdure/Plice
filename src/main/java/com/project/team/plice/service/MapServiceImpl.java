package com.project.team.plice.service;

import com.project.team.plice.dto.Params;
import com.project.team.plice.dto.TradeData;
import com.project.team.plice.service.interfaces.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private String xml;

    @Override
    public List<TradeData> tradeDataSearch(Params params) throws Exception {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=t6FF%2FNmZ7E6CgombvVgPJl7z0Yv5oDesGF%2Bm78Hv%2BT5IMNGTDRd53t0wkPd9%2FoEx7X522aovCygHADH31fbWTg%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode(params.getLAWD_CD(), "UTF-8")); /*지역코드*/
            urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode(params.getDEAL_YMD(), "UTF-8")); /*계약월*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            xml = sb.toString();

        } catch (Exception e) {
            System.out.println("다운로드에러" + e.getMessage());
        }
        log.info("xml 내용 = " + xml);

        Document documentInfo = null;
        try {
                documentInfo = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new ByteArrayInputStream(xml.getBytes()));
                documentInfo.getDocumentElement().normalize();
                log.info("documentInfo 내용 = " + documentInfo.toString());

                return parseXml(documentInfo.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    private List<TradeData> parseXml(Element root) {
        NodeList nList = root.getElementsByTagName("items").item(0).getChildNodes();
        List<TradeData> tradeDataList = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            TradeData tradeData = new TradeData();
            Element eElement = (Element) nNode;

            tradeData.setAptName(getTagValue("아파트",eElement));
            tradeData.setPrice(getTagValue("거래금액",eElement));
            tradeData.setBuildYear(getTagValue("건축년도",eElement));
            tradeData.setYear(getTagValue("년",eElement));
            tradeData.setMonth(getTagValue("월",eElement));
            tradeData.setDay(getTagValue("일",eElement));
            tradeData.setRoadName(getTagValue("도로명",eElement));
            tradeData.setSiGunGuCode(getTagValue("법정동시군구코드",eElement));
            tradeData.setEupMyeonDongCode(getTagValue("법정동읍면동코드",eElement));
            tradeData.setDong(getTagValue("법정동",eElement));
            tradeData.setArea(getTagValue("전용면적",eElement));
            tradeData.setFloor(getTagValue("층",eElement));
            tradeDataList.add(tradeData);
        }
        return tradeDataList;
    }

}
