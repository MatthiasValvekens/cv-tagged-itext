package be.mvalvekens.cv.utils;

import com.itextpdf.kernel.pdf.PdfAnnotationBorder;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Text;

public class ITextUtils {
    public static Text neutralText(String txt) {
        Text t = new Text(txt);
        t.getAccessibilityProperties().setRole(null);
        return t;
    }

    public static Link borderlessLink(String text, String uri, String alt) {
        Link lnk = new Link(text, PdfAction.createURI(uri));
        lnk.getLinkAnnotation()
                .setBorder(new PdfAnnotationBorder(0, 0, 0))
                .setContents(alt);
        return lnk;
    }
}
