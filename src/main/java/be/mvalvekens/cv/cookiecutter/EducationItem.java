package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.context.Contentable;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.cv.context.StyleType;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class EducationItem implements Contentable<Div> {
    private final String credential;
    private final String institution;
    private final String subline;

    public EducationItem(String credential, String institution, String subline) {
        this.credential = credential;
        this.institution = institution;
        this.subline = subline;
    }

    @Override
    public Div asContent(ICVContext context) {
        Div bsc = new Div();
        Paragraph bscLine = context.createDefaultParagraph();
        bscLine.add(new Text(credential).addStyle(context.getStyle(StyleType.BoldText)));
        bscLine.add(ITextUtils.neutralText(", "));
        bscLine.add(new Text(institution).addStyle(context.getStyle(StyleType.ObliqueText)));
        Paragraph bscSubline = context.createDefaultParagraph(subline);
        bscSubline.setFontSize(10);
        bsc.add(bscLine);
        bsc.add(bscSubline);
        return bsc;
    }
}
