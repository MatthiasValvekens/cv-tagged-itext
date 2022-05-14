package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.context.Contentable;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.cv.context.StyleType;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class SpeakingEngagement implements Contentable<Div> {
    // TODO move date logic into this class
    private final String title;
    private final String event;
    private final String location;
    private final boolean invited;

    public SpeakingEngagement(String title, String event, String location, boolean invited) {
        this.title = title;
        this.event = event;
        this.location = location;
        this.invited = invited;
    }

    @Override
    public Div asContent(ICVContext context) {
        Div result = new Div();
        Paragraph p = context.createDefaultParagraph();
        p.getAccessibilityProperties().setRole(null);
        p.add(new Text(title).addStyle(context.getStyle(StyleType.ItalicText)));
        p.add(ITextUtils.neutralText(", "));
        p.add(new Text(event));
        if(invited) {
            p.add(new Text(" (invited speaker)").addStyle(context.getStyle(StyleType.ItalicText)));
        }
        if(location == null) {
            p.add(new Text(" (virtual)").addStyle(context.getStyle(StyleType.ItalicText)));
        } else {
            p.add(ITextUtils.neutralText(", "));
            p.add(location);
        }
        result.add(p);
        return result;
    }
}
