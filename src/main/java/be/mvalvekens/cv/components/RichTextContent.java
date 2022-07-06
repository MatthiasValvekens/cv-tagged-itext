package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.CVContent;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.itextts.md.SimpleRichText;
import com.itextpdf.layout.element.Div;

public class RichTextContent extends SimpleRichText implements CVContent<Div> {
    public RichTextContent(String markdownInput) {
        super(markdownInput);
    }

    public RichTextContent(String markdownInput, boolean isSnippet) {
        super(markdownInput, isSnippet);
    }

    @Override
    public Div layoutContent(ICVContext context) {
        return super.performLayout(context);
    }
}
