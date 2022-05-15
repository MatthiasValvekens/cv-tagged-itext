package be.mvalvekens.cv.components.md;

import be.mvalvekens.cv.context.CVContent;
import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.layout.element.Div;
import org.commonmark.node.Node;
import org.commonmark.node.Visitor;
import org.commonmark.parser.Parser;

import java.util.Collections;

public class SimpleRichText implements CVContent<Div> {
    private final String markdownInput;
    private final boolean isSnippet;
    private String role = null;

    public SimpleRichText(String markdownInput) {
        this(markdownInput, false);
    }

    public SimpleRichText(String markdownInput, boolean isSnippet) {
        this.markdownInput = markdownInput;
        this.isSnippet = isSnippet;
    }

    public String getRole() {
        return role;
    }

    public SimpleRichText setRole(String role) {
        this.role = role;
        return this;
    }


    @Override
    public Div layoutContent(ICVContext context) {
        // no special blocks enabled
        Parser p = Parser.builder()
                .enabledBlockTypes(Collections.emptySet())
                .build();
        Node node = p.parse(this.markdownInput);
        Div result = new Div();
        Visitor v = new ITextLayoutVisitor(context, result::add, isSnippet);
        node.accept(v);
        return result;
    }
}
