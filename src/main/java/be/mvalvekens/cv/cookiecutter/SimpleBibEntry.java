package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.context.CVListContent;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.itextts.md.SimpleRichText;
import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.Div;

public class SimpleBibEntry extends SimpleRichText implements CVListContent {
    public SimpleBibEntry(String markdownInput) {
        super(markdownInput, true);
    }

    public Div layoutContent(ICVContext context) {
        setRole(StandardRoles.BIBENTRY, StandardNamespaces.PDF_1_7);
        return super.performLayout(context);
    }
}
