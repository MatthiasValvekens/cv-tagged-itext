package be.mvalvekens.cv.components.md;

import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.layout.element.Div;
import org.commonmark.node.Node;
import org.commonmark.node.Visitor;
import org.commonmark.parser.Parser;

import java.util.Collections;

public class SimpleRichText {
    private final ICVContext context;
    private final String markdownInput;

    public SimpleRichText(ICVContext context, String markdownInput) {
        this.context = context;
        this.markdownInput = markdownInput;
    }

    public Div asContent() {
        // no special blocks enabled
        Parser p = Parser.builder()
                .enabledBlockTypes(Collections.emptySet())
                .build();
        Node node = p.parse(this.markdownInput);
        Div result = new Div();
        Visitor v = new ITextLayoutVisitor(this.context, result::add);
        node.accept(v);
        return result;
    }
}
