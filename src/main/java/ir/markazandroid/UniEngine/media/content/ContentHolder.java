package ir.markazandroid.UniEngine.media.content;

import ir.markazandroid.UniEngine.media.annotations.View;
import ir.markazandroid.UniEngine.media.annotations.XmlAttribute;
import ir.markazandroid.UniEngine.media.layout.Layout;
import ir.markazandroid.UniEngine.media.layout.Orientation;
import ir.markazandroid.UniEngine.media.layout.Region;
import ir.markazandroid.UniEngine.media.layout.Side;
import ir.markazandroid.UniEngine.media.views.PlayListView;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Ali on 4/22/2019.
 */
public class ContentHolder {
    
    private Layout layout;
    private Map<Region,Object> views;


    public ContentHolder(Layout layout) {
        this.layout = layout;
        views=new HashMap<>();
    }

    public void assignViewToRegion(Region region,Object view){
        if (!layout.getRegions().contains(region)) throw new  RuntimeException("Region is not part of Layout");
        views.put(region, view);
    }


    public CampaignEntity.Data generateData(){
        Map<String,Object> extras= new HashMap<>();
        views.forEach((region, o) -> extras.put(region.getId(),o));
        return new CampaignEntity.Data(generateXml(),extras);
    }
    
    private String generateXml(){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            doc.setXmlStandalone(true);
            Element root = doc.createElement("android.support.constraint.ConstraintLayout");
            root.setAttribute("xmlns:android", "http://schemas.android.com/apk/res/android");
            root.setAttribute("xmlns:app","http://schemas.android.com/apk/res-auto");

            root.setAttribute("android:layout_width","match_parent");
            root.setAttribute("android:layout_height","match_parent");

            doc.appendChild(root);

            buildSides(root);
            buildViews(root);

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void buildSides(Element root) {
        layout.getSides()
                .forEach(side -> {
                    if (side.isParentSide()) return;
                    Element guideLine = root.getOwnerDocument().createElement("android.support.constraint.Guideline");
                    guideLine.setAttribute("android:layout_width","match_parent");
                    guideLine.setAttribute("android:layout_height","match_parent");
                    guideLine.setAttribute("app:layout_constraintGuide_percent",((float)side.getPercent())/100f+"");
                    guideLine.setAttribute("android:orientation",side.getOrientation().name());
                    guideLine.setAttribute("android:id","@+id/"+side.getId());
                    root.appendChild(guideLine);
                });
    }

    private void buildViews(Element root) {
        views.forEach((region, o) -> {
            String viewName =o.getClass().getAnnotation(View.class).value();
            Element view = root.getOwnerDocument().createElement(viewName);
            view.setAttribute("android:layout_width","0dp");
            view.setAttribute("android:layout_height","0dp");
            view.setAttribute("android:id","@+id/"+region.getId());

            if (region.getTop().isParentSide())
                view.setAttribute("app:layout_constraintTop_toTopOf","parent");
            else
                view.setAttribute("app:layout_constraintTop_toBottomOf","@id/"+region.getTop().getId());

            if (region.getBottom().isParentSide())
                view.setAttribute("app:layout_constraintBottom_toBottomOf","parent");
            else
                view.setAttribute("app:layout_constraintBottom_toTopOf","@id/"+region.getBottom().getId());

            if (region.getRight().isParentSide())
                view.setAttribute("app:layout_constraintRight_toRightOf","parent");
            else
                view.setAttribute("app:layout_constraintRight_toLeftOf","@id/"+region.getRight().getId());

            if (region.getLeft().isParentSide())
                view.setAttribute("app:layout_constraintLeft_toLeftOf","parent");
            else
                view.setAttribute("app:layout_constraintLeft_toRightOf","@id/"+region.getLeft().getId());

            getViewAttributes(o).forEach(view::setAttribute);

            root.appendChild(view);
        });
    }

    private Map<String,String> getViewAttributes(Object o){
        Map<String, String> attributes = new HashMap<>();
        Class clazz = o.getClass();
        do {
            attributes.putAll(getObjectViewAttributes(o,clazz));
            clazz=clazz.getSuperclass();
        }
        while (!clazz.equals(Object.class));

        return attributes;
    }

    private Map<String,String> getObjectViewAttributes(Object o,Class clazz){
        Map<String,String> attributes = new HashMap<>();
        for (Method method:clazz.getDeclaredMethods()){
            XmlAttribute attribute = method.getAnnotation(XmlAttribute.class);
            if (attribute!=null){
                try {
                    attributes.put(attribute.value(), method.invoke(o).toString());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return attributes;
    }


    public static void main(String[] args) {
        Layout layout = new Layout();
        Side fv=layout.newSide(33, Orientation.vertical);
        Side sv=layout.newSide(66, Orientation.vertical);
        Side fh=layout.newSide(33, Orientation.horizontal);
        Side sh=layout.newSide(66, Orientation.horizontal);

        Region fr=layout.newRegion(layout.getLeftParentSide(),layout.getTopParentSide(),fv,fh);
        Region sr=layout.newRegion(fv,fh,sv,sh);
        Region tr=layout.newRegion(sv,sh,layout.getRightParentSide(),layout.getBottomParentSide());

        ContentHolder holder = new ContentHolder(layout);
        holder.assignViewToRegion(fr,new PlayListView().getData());
        holder.assignViewToRegion(sr,new PlayListView());
        holder.assignViewToRegion(tr,new PlayListView());

        System.out.println(holder.generateXml());
    }
}
