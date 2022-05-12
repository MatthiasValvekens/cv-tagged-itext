package be.mvalvekens.cv.utils;

import be.mvalvekens.cv.components.StyleType;
import com.itextpdf.layout.Style;

import java.util.Map;

public class StyleManager {
    private final float reducedLeading;
    private final Map<StyleType, Style> styles;

    // TODO make this a builder
    public StyleManager() {
         this(StyleType.defaultStyles(), 0.9f);
    }

    public StyleManager(Map<StyleType, Style> styles, float reducedLeading) {
        this.styles = styles;
        this.reducedLeading = reducedLeading;
    }

    public Style get(StyleType st) {
        return styles.get(st);
    }

    public float getLeadingFactor() {
        return this.reducedLeading;
    }
}
