package be.mvalvekens.cv.context;

import be.mvalvekens.itextts.context.IDocumentContext;
import com.itextpdf.layout.element.Paragraph;

public interface ICVContext extends IDocumentContext {
    String getName();
    String getCVTitle();

    default Paragraph createParagraphWithRole(String content, String role) {
        Paragraph p = createDefaultParagraph(content);
        p.getAccessibilityProperties().setRole(role);
        return p;
    }

    Paragraph createMainTextParagraph();
}
