package be.mvalvekens.cv.context;

import be.mvalvekens.cv.elems.Rule;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.hyphenation.HyphenationConfig;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.util.Map;

public class CVContext implements ICVContext {
    private final float reducedLeading;
    private final Map<StyleType, Style> styles;
    private final String name;
    private final String cvTitle;
    private final String language;
    private final HyphenationConfig hyphenationConfig;
    private final PdfFont defaultFont;

    // TODO make this a builder
    public CVContext(PdfFont defaultFont, Map<StyleType, Style> styles, String name) {
        this(defaultFont, styles, 0.9f, name, "Curriculum Vit\u00e6", "en-GB",
                new HyphenationConfig("en", "GB", 2, 2));
    }

    public CVContext(PdfFont defaultFont,
                     Map<StyleType, Style> styles, float reducedLeading, String name,
                     String cvTitle, String language, HyphenationConfig hyphenationConfig) {
        this.defaultFont = defaultFont;
        this.styles = styles;
        this.reducedLeading = reducedLeading;
        this.name = name;
        this.cvTitle = cvTitle;
        this.hyphenationConfig = hyphenationConfig;
        this.language = language;
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

    @Override
    public PdfFont getDefaultFont() {
        return defaultFont;
    }

    @Override
    public Paragraph createMainTextParagraph() {
        return this.createDefaultParagraph()
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setHyphenation(this.hyphenationConfig)
                .setMarginBottom(4f)
                .setFontSize(11);
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

    @Override
    public String getLang() {
        return this.language;
    }

}
