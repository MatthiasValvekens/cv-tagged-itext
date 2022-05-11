package be.mvalvekens.cv.components;

import com.itextpdf.layout.Style;

import java.util.HashMap;
import java.util.Map;

public enum StyleType {
    NormalText, BoldText, BoldItalicText, ItalicText, ObliqueText, Heading, Code, Link;

    public static Map<StyleType, Style> defaultStyles() {
        Map<StyleType, Style> styles = new HashMap<>();
        Style normal = new Style().setFontFamily("roman");
        styles.put(StyleType.NormalText, normal);
        styles.put(StyleType.BoldItalicText, new Style().setFontFamily("bolditalic"));
        styles.put(StyleType.BoldText, new Style().setFontFamily("bold"));
        styles.put(StyleType.ItalicText, new Style().setFontFamily("italic"));
        styles.put(StyleType.ObliqueText, new Style().setFontFamily("oblique"));
        styles.put(StyleType.Heading, new Style(normal).setFontSize(18));
        styles.put(StyleType.Code, new Style().setFontFamily("monospace"));
        return styles;
    }
}
