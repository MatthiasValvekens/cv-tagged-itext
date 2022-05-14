package be.mvalvekens.cv.context;

import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;

public interface ICVContext {
    String getName();
    String getCVTitle();
    Style getStyle(StyleType styleType);
    Paragraph createDefaultParagraph();
    Paragraph createHeading(HeadingType hType, String headingText);

    default Paragraph createDefaultParagraph(String content) {
        Paragraph p = createDefaultParagraph();
        if(content != null) {
            p.add(ITextUtils.neutralText(content));
        }
        return p;
    }

    default Paragraph createParagraphWithRole(String content, String role) {
        Paragraph p = createDefaultParagraph(content);
        p.getAccessibilityProperties().setRole(role);
        return p;
    }

    Paragraph createMainTextParagraph();
}
