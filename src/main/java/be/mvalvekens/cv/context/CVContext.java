package be.mvalvekens.cv.context;

import be.mvalvekens.cv.elems.Rule;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
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
    private final PdfFont mainFont;
    private final TaggingMode taggingMode;
    private final TagStructureContext tagStructureContext;

    CVContext(PdfFont mainFont,
              Map<StyleType, Style> styles, float reducedLeading, String name,
              String cvTitle, String language, HyphenationConfig hyphenationConfig,
              TaggingMode taggingMode, TagStructureContext tagStructureContext) {
        this.mainFont = mainFont;
        this.styles = styles;
        this.reducedLeading = reducedLeading;
        this.name = name;
        this.cvTitle = cvTitle;
        this.hyphenationConfig = hyphenationConfig;
        this.language = language;
        this.taggingMode = taggingMode;
        this.tagStructureContext = tagStructureContext;
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

        boolean pdf2 = getTaggingMode() == TaggingMode.PDF_2_0;

        final String role;
        switch (hType) {
            case Section:
                role = pdf2 ? StandardRoles.H1 : StandardRoles.H2;
                h.add(new Rule(UnitValue.createPointValue(80), UnitValue.createPointValue(5), 4));
                innerH.setMarginLeft(10);
                break;
            case Subsection:
                role = pdf2 ? StandardRoles.H2 : StandardRoles.H3;
                h.setFontSize(13).setMarginBottom(0).setMarginTop(1);
                innerH.setMarginLeft(90);
                break;
            case Subsubsection:
                role = pdf2 ? StandardRoles.H3 : StandardRoles.H4;
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
    public PdfFont getMainFont() {
        return mainFont;
    }

    @Override
    public Paragraph createMainTextParagraph() {
        return this.createDefaultParagraph()
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setHyphenation(this.hyphenationConfig)
                .setMarginBottom(4f)
                .setFontSize(11);
    }

    public static CVContextBuilder builder(PdfDocument pdfDocument) {
        return new CVContextBuilder(pdfDocument);
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

    @Override
    public TaggingMode getTaggingMode() {
        return taggingMode;
    }

    @Override
    public TagStructureContext getTagStructureContext() {
        return tagStructureContext;
    }
}
