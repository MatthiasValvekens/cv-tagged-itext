package be.mvalvekens.cv.components.md;

import be.mvalvekens.cv.context.Contentable;
import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.layout.element.Div;
import org.commonmark.node.Node;
import org.commonmark.node.Visitor;
import org.commonmark.parser.Parser;

import java.util.Collections;

public class SimpleRichText implements Contentable<Div> {
    private final String markdownInput;
    private final boolean isSnippet;

    public SimpleRichText(String markdownInput) {
        this(markdownInput, false);
    }

    public SimpleRichText(String markdownInput, boolean isSnippet) {
        this.markdownInput = markdownInput;
        this.isSnippet = isSnippet;
    }

    @Override
    public Div asContent(ICVContext context) {
        // no special blocks enabled
        Parser p = Parser.builder()
                .enabledBlockTypes(Collections.emptySet())
                .build();
        Node node = p.parse(this.markdownInput);
        Div result = new Div();
        result.getAccessibilityProperties().setRole(null);
        Visitor v = new ITextLayoutVisitor(context, result::add, isSnippet);
        node.accept(v);
        return result;
    }
}
