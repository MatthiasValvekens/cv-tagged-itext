package be.mvalvekens.cv.context;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;

import java.util.Map;

public class CVContext implements ICVContext {
    private final float reducedLeading;
    private final Map<StyleType, Style> styles;

    // TODO make this a builder
    public CVContext() {
        this(StyleType.defaultStyles(), 0.9f);
    }

    public CVContext(Map<StyleType, Style> styles, float reducedLeading) {
        this.styles = styles;
        this.reducedLeading = reducedLeading;
    }

    @Override
    public Style getStyle(StyleType st) {
        return styles.get(st);
    }

    @Override
    public Paragraph createDefaultParagraph() {
        return new Paragraph().setMargin(0).setMultipliedLeading(this.getLeadingFactor());
    }

    public float getLeadingFactor() {
        return this.reducedLeading;
    }
}
