package be.mvalvekens.cv.context;

import be.mvalvekens.cv.elems.Rule;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.UnitValue;

import java.util.Map;

public class CVContext implements ICVContext {
    private final float reducedLeading;
    private final Map<StyleType, Style> styles;
    private final String name;
    private final String cvTitle;

    // TODO make this a builder
    public CVContext(String name) {
        this(StyleType.defaultStyles(), 0.9f, name, "Curriculum Vit\u00e6");
    }

    public CVContext(Map<StyleType, Style> styles, float reducedLeading, String name, String cvTitle) {
        this.styles = styles;
        this.reducedLeading = reducedLeading;
        this.name = name;
        this.cvTitle = cvTitle;
    }

    @Override
    public Style getStyle(StyleType st) {
        return styles.get(st);
    }

    @Override
    public Paragraph createDefaultParagraph() {
        return new Paragraph().setMargin(0).setMultipliedLeading(this.getLeadingFactor());
    }

    @Override
    public Paragraph createHeading(HeadingType hType, String headingText) {
        final Paragraph h = new Paragraph()
                .addStyle(this.getStyle(StyleType.Heading))
                .setMarginBottom(0);
        final Paragraph innerH = new Paragraph().setMargin(0);
        innerH.getAccessibilityProperties().setRole(null);

        final String role;
        switch (hType) {
            case Section:
                role = StandardRoles.H2;
                h.add(new Rule(UnitValue.createPointValue(80), UnitValue.createPointValue(5), 4));
                innerH.setMarginLeft(10);
                break;
            case Subsection:
                role = StandardRoles.H3;
                h.setFontSize(13).setMarginBottom(0).setMarginTop(1);
                innerH.setMarginLeft(90);
                break;
            case Subsubsection:
                role = StandardRoles.H4;
                break;
            default:
                role = StandardRoles.P;
        }
        h.getAccessibilityProperties().setRole(role);
        Text hText = ITextUtils.neutralText(headingText);
        innerH.add(hText);
        h.add(innerH);
        return h;
    }

    public float getLeadingFactor() {
        return this.reducedLeading;
    }

    @Override
    public String getCVTitle() {
        return cvTitle;
    }

    @Override
    public String getName() {
        return name;
    }
}
