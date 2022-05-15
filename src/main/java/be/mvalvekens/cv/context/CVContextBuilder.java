package be.mvalvekens.cv.context;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.hyphenation.HyphenationConfig;

import java.util.Map;

public class CVContextBuilder {
    private PdfFont mainFont;
    private Map<StyleType, Style> styles;
    private String name;
    private float reducedLeading = 0.9f;
    private String cvTitle = "Curriculum Vit\u00e6";
    private String language = "en-GB";
    private HyphenationConfig hyphenationConfig = new HyphenationConfig("en", "GB", 2, 2);

    public CVContextBuilder setMainFont(PdfFont mainFont) {
        this.mainFont = mainFont;
        return this;
    }

    public CVContextBuilder setStyles(Map<StyleType, Style> styles) {
        this.styles = styles;
        return this;
    }

    public CVContextBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CVContextBuilder setReducedLeading(float reducedLeading) {
        this.reducedLeading = reducedLeading;
        return this;
    }

    public CVContextBuilder setCvTitle(String cvTitle) {
        this.cvTitle = cvTitle;
        return this;
    }

    public CVContextBuilder setLanguage(String language) {
        this.language = language;
        return this;
    }

    public CVContextBuilder setHyphenationConfig(HyphenationConfig hyphenationConfig) {
        this.hyphenationConfig = hyphenationConfig;
        return this;
    }

    public CVContext build() {
        return new CVContext(mainFont, styles, reducedLeading, name, cvTitle, language, hyphenationConfig);
    }
}