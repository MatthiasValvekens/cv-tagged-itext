package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.context.CVListContent;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.itextts.context.StyleType;
import be.mvalvekens.itextts.utils.ITextUtils;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class SpeakingEngagement implements CVListContent {
    // TODO move date logic into this class
    private final String title;
    private final String event;
    private final String location;
    private final boolean listAsInvited;
    private final String remark;

    public SpeakingEngagement(String title, String event, String location, boolean listAsInvited, String remark) {
        this.title = title;
        this.event = event;
        this.location = location;
        this.listAsInvited = listAsInvited;
        this.remark = remark;
    }

    public SpeakingEngagement(String title, String event, String location, boolean listAsInvited) {
        this(title, event, location, listAsInvited, null);
    }

    public SpeakingEngagement(String title, String event, String location) {
        this(title, event, location, false);
    }

    @Override
    public Div layoutContent(ICVContext context) {
        Div result = new Div();
        Paragraph p = context.createDefaultParagraph();
        p.getAccessibilityProperties().setRole(null);
        p.add(new Text(title).addStyle(context.getStyle(StyleType.ItalicText)));
        p.add(ITextUtils.neutralText(", "));
        p.add(new Text(event));
        if(listAsInvited) {
            p.add(new Text(" (invited speaker)").addStyle(context.getStyle(StyleType.ItalicText)));
        }
        if(location == null) {
            p.add(new Text(" (virtual)").addStyle(context.getStyle(StyleType.ItalicText)));
        } else {
            p.add(ITextUtils.neutralText(", "));
            p.add(location);
        }
        if(remark != null) {
            Text text = new Text("[" + remark + "]").addStyle(context.getStyle(StyleType.ItalicText));
            p.add(ITextUtils.neutralText(" ")).add(text);
        }
        result.add(p);
        return result;
    }
}
