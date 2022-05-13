package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.StyleType;
import be.mvalvekens.cv.utils.ITextUtils;
import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class MultilineItem {
    private final String title;
    private final String org;
    private final String loc;
    private final String[] others;
    private final ICVContext context;

    public MultilineItem(ICVContext context, String title, String org, String loc, String... others) {
        this.context = context;
        this.title = title;
        this.org = org;
        this.loc = loc;
        this.others = others;
    }

    public Div asContent() {
        Div d = new Div();
        Paragraph p = this.context.createDefaultParagraph();
        p.add(new Text(title).addStyle(context.getStyle(StyleType.BoldText)));
        p.add(ITextUtils.neutralText(", "));
        p.add(new Text(this.org).addStyle(context.getStyle(StyleType.ObliqueText)));
        if (this.loc != null) {
            p.add(ITextUtils.neutralText(", "));
            p.add(this.loc);
        }
        d.add(p);


        Div l = new Div();
        l.getAccessibilityProperties().setRole(StandardRoles.L);
        for (String s : others) {
            Paragraph li = this.context.createDefaultParagraph().setFontSize(10);
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
