package ua.nure.tsuvarev;


import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 05.04.2017.
 */
public class Parser {
    
    private static final int ONLY = 0;
    private static final int ONE = 1;
    
    
    private TagNode document;
    
    public Parser(String path) {
        try {
            URL url = new URL(path);
            InputStream input = url.openConnection().getInputStream();
            HtmlCleaner cleaner = new HtmlCleaner();
            document = cleaner.clean(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    public TagNode getSingleByXpath(String xPath) {
        Object[] resList;
        try {
            resList = document.evaluateXPath(xPath);
        } catch (XPatherException e) {
            e.printStackTrace();
            return null;
        }
        if (resList.length != ONE) {
            throw new NotOnlyElementException("This element is not the only one or there is no such elements. Count =" +
                Integer.toString(resList.length));
        }
        return (TagNode) resList[ONLY];
    }
    
    public List<TagNode> getAllByXpath(String xPath) {
        Object[] resList;
        try {
            resList = document.evaluateXPath(xPath);
        } catch (XPatherException e) {
            e.printStackTrace();
            return null;
        }
        
        return nodesFromXpath(resList);
    }
    
    /*public static void main(String[] args) throws Exception {
        Parser p = new Parser(PATH);
        
        TagNode minTemperature = new TagNode("000");
        List<TagNode> winds = new ArrayList<>();
        try {
            minTemperature =
                p.getSingleByXpath(
                    String.format(FORMAT_TEMPERATURE_XPATH, MIN));
            winds = p.getAllByXpath(FORMAT_WINDS_XPATH);
        } catch (NotOnlyElementException e) {
            e.printStackTrace();
        }
        System.out.println(minTemperature.getAllChildren().get(0).toString());
        System.out.println(winds.size());
        
        return;
    }*/
    
    private List<TagNode> nodesFromXpath(Object[] xPathRes) {
        List<TagNode> res = new ArrayList<>();
        for (Object part : xPathRes) {
            res.add((TagNode) part);
        }
        return res;
    }
}