package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.context.CVListContent;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.itextts.context.StyleType;
import be.mvalvekens.itextts.utils.ITextUtils;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class ExperienceItem implements CVListContent {
    private final String title;
    private final String org;
    private final String location;
    private final String[] lines;

    public ExperienceItem(String title, String org, String location, String... lines) {
        this.title = title;
        this.org = org;
        this.location = location;
        this.lines = lines;
    }

    @Override
    public Div layoutContent(ICVContext context) {
        Div d = new Div();
        Paragraph p = context.createDefaultParagraph();
        p.add(new Text(title).addStyle(context.getStyle(StyleType.BoldText)));
        p.add(ITextUtils.neutralText(", "));
        p.add(new Text(this.org).addStyle(context.getStyle(StyleType.ObliqueText)));
        if (this.location != null) {
            p.add(ITextUtils.neutralText(", "));
            p.add(this.location);
        }
        d.add(p);

        Div l = new Div();
        l.getAccessibilityProperties().setRole(StandardRoles.L);
        for (String s : lines) {
            Paragraph li = context.createDefaultParagraph().setFontSize(10);
            li.getAccessibilityProperties().setRole(StandardRoles.LI);
            Paragraph lbody = new Paragraph(ITextUtils.neutralText(s)).setMargin(0);
            lbody.getAccessibilityProperties().setRole(StandardRoles.LBODY);
            li.add(lbody);
            l.add(li);
        }
        d.add(l);
        return d;
    }
}
