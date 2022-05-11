package be.mvalvekens.cv.utils;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class ITextUtils {
    public static Text neutralText(String txt) {
        Text t = new Text(txt);
        t.getAccessibilityProperties().setRole(null);
        return t;
    }

    public static Paragraph neutralPara(String txt) {
        Paragraph p = new Paragraph(neutralText(txt)).setMargin(0).setMultipliedLeading(StyleManager.reducedLeading);
        p.getAccessibilityProperties().setRole(null);
        return p;
    }
}
