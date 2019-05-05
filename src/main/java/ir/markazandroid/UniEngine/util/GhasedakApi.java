package ir.markazandroid.UniEngine.util;

import ir.markazandroid.UniEngine.JSONParser.Parser;
import ir.markazandroid.UniEngine.object.GhasedakResponse;
import ir.markazandroid.UniEngine.object.InternetPackage;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Ali on 2/18/2019.
 */
@Component
public class GhasedakApi {
    private final OkHttpClient httpClient;
    private final Parser parser;

    @Autowired
    public GhasedakApi(OkHttpClient httpClient, Parser parser) {
        this.httpClient = httpClient;
        this.parser = parser;
    }

    public ArrayList<InternetPackage> getInternetPackages(int operator) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/xml"), String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n  <soap12:Body>\r\n    <GetInternetPackages xmlns=\"http://www.toranjsoft.com\">\r\n      <OperatorID>%d</OperatorID>\r\n    </GetInternetPackages>\r\n  </soap12:Body>\r\n</soap12:Envelope>",operator));
        Request request = new Request.Builder()
                .url("http://ws.elkapos.com/UserWebservice.asmx?op=GetInternetPackages")
                .post(body)
                .addHeader("Content-Type", "text/xml")
                .build();
        Response response = httpClient.newCall(request).execute();
        //System.out.println(response.body().string());
        String s = parseXML(response.body().byteStream());
        response.close();
        return parser.get(InternetPackage.class,new JSONArray(s));
    }

    public GhasedakResponse chargeAccount(long amount,String transactionCode) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/xml"), String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n  <soap:Body>\r\n    <CreditCharge xmlns=\"http://www.toranjsoft.com\">\r\n      <Username>ws147056</Username>\r\n      <Password>SfeyGEs4855</Password>\r\n      <Amount>%d</Amount>\r\n      <CallBackURL>http://89.42.210.32/advertiserv4/web/user/phone/buyPackageBack/%s/{0}/{2}</CallBackURL>\r\n    </CreditCharge>\r\n  </soap:Body>\r\n</soap:Envelope>",amount,transactionCode));
        Request request = new Request.Builder()
                .url("http://ws.elkapos.com/UserWebservice.asmx?op=CreditCharge")
                .post(body)
                .addHeader("Content-Type", "text/xml")
                .build();
        Response response = httpClient.newCall(request).execute();
        //System.out.println(response.body().string());
        String s = parseXML(response.body().byteStream());
        response.close();
        return parser.get(GhasedakResponse.class,new JSONObject(s));
    }

    public GhasedakResponse sendPackage(String phoneNumber, long serviceID, long amount, long transactionId) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/xml"), String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n  <soap:Body>\r\n    <Recharge xmlns=\"http://www.toranjsoft.com\">\r\n    \t\t<Username>ws147056</Username>\r\n      <Password>SfeyGEs4855</Password>\r\n      <MobileNumber>%s</MobileNumber>\r\n      <Amount>%d</Amount>\r\n      <Service>5</Service>\r\n      <Params>%d</Params>\r\n      <UserOrderID>%d</UserOrderID>\r\n    </Recharge>\r\n  </soap:Body>\r\n</soap:Envelope>",phoneNumber,amount,serviceID,transactionId));
        Request request = new Request.Builder()
                .url("http://ws.elkapos.com/UserWebservice.asmx?op=Recharge")
                .post(body)
                .addHeader("Content-Type", "text/xml")
                .build();
        Response response = httpClient.newCall(request).execute();
        //System.out.println(response.body().string());
        String s = parseXML(response.body().byteStream());
        response.close();
        return parser.get(GhasedakResponse.class,new JSONObject(s));
    }


    public static String parseXML(InputStream inputStream){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            return doc.getDocumentElement().getFirstChild().getFirstChild().getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
