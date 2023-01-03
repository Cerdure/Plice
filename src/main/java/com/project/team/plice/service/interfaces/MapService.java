package com.project.team.plice.service.interfaces;

import com.project.team.plice.dto.Params;
import com.project.team.plice.dto.TradeData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public interface MapService {

    public List<TradeData> tradeDataSearch(Params params) throws Exception;

    default String getTagValue(String tag, Element eElement) {
        NodeList nList = null;
        Node nValue = null;
        try {
            nList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            nValue = (Node) nList.item(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    };
}
