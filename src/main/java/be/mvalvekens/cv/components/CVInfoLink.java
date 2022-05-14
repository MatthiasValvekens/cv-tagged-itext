package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.cv.context.StyleType;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class CVInfoLink {
    private final Label label;
    private final String displayText;
    private final String uri;

    // TODO need builder with sane defaults
    public CVInfoLink(Label label, String displayText, String uri) {
        this.label = label;
        this.displayText = displayText;
        this.uri = uri;
    }

    protected IBlockElement formatSocialLink(ICVContext context) {
        Paragraph para = new Paragraph().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        para.getAccessibilityProperties().setRole(StandardRoles.LI);
        para.add(label.asText());

        Paragraph body = new Paragraph().setMargin(0);
        body.addStyle(context.getStyle(StyleType.ObliqueText));
        body.getAccessibilityProperties().setRole(StandardRoles.LBODY);
        body.add(ITextUtils.borderlessLink(displayText, uri, label.replacementText + " link"));
        para.add(body);
        para.setFixedLeading(14);
        return para;
    }

    public static class Label {
        private final String replacementText;
        private final String symbolText;
        private final PdfFont symbolFont;

        public Label(String replacementText, String symbolText, PdfFont symbolFont) {
            this.replacementText = replacementText;
            this.symbolText = symbolText;
            this.symbolFont = symbolFont;
        }

        private Text asText() {
            Text iconText = new Text(symbolText + " ").setFont(this.symbolFont);
            iconText.getAccessibilityProperties()
                    .setActualText(replacementText)
                    .setRole(StandardRoles.LBL);
            return iconText;
        }

    }

}
