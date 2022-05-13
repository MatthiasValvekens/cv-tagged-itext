package be.mvalvekens.cv.utils;

import com.itextpdf.layout.element.Text;

public class ITextUtils {
    public static Text neutralText(String txt) {
        Text t = new Text(txt);
        t.getAccessibilityProperties().setRole(null);
        return t;
    }
}
