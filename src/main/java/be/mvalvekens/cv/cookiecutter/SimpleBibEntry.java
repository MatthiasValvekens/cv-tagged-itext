package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.components.md.SimpleRichText;
import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.Div;

public class SimpleBibEntry extends SimpleRichText {
    public SimpleBibEntry(String markdownInput) {
        super(markdownInput, true);
    }

    public Div layoutContent(ICVContext context) {
        Div result = super.layoutContent(context);
        result.getAccessibilityProperties().setRole(StandardRoles.BIBENTRY);
        return result;
    }
}
