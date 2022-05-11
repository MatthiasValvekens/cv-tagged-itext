package be.mvalvekens.cv.utils;

import be.mvalvekens.cv.components.StyleType;
import com.itextpdf.layout.Style;

import java.util.Map;

public class StyleManager {
    public final static float reducedLeading = 0.9f;
    private final Map<StyleType, Style> styles;

    public StyleManager() {
         this(StyleType.defaultStyles());
    }

    public StyleManager(Map<StyleType, Style> styles) {
        this.styles = styles;
    }

    public Style get(StyleType st) {
        return styles.get(st);
    }
}
