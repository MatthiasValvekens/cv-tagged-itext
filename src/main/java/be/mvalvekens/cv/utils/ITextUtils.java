package be.mvalvekens.cv.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfAnnotationBorder;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Text;

import java.io.IOException;
import java.io.InputStream;

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
    public static byte[] getResourceBytes(ClassLoader cl, String resourceName) {
        if(cl == null) {
            cl = ITextUtils.class.getClassLoader();
        }
        try(InputStream is = cl.getResourceAsStream(resourceName)) {
            if(is == null) {
                throw new RuntimeException("Resource " + resourceName + " not found");
            }
            return is.readAllBytes();
        } catch (IOException ex) {
            throw new RuntimeException("Resource " + resourceName + " could not be read");
        }
    }


    public static PdfFont fontFromResource(String file) throws IOException {
        ClassLoader cl = ITextUtils.class.getClassLoader();
        return PdfFontFactory.createFont(getResourceBytes(cl, file), PdfEncodings.IDENTITY_H,
                PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

    }

}
