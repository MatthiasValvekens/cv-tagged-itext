package be.mvalvekens.cv.components.md;

import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.cv.context.StyleType;
import be.mvalvekens.cv.utils.ITextUtils;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AbstractElement;
import com.itextpdf.layout.hyphenation.HyphenationConfig;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Code;
import org.commonmark.node.Emphasis;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;

import java.util.function.Consumer;

/**
 * Commonmark visitor to handle simple rich text. Non-paragraph
 * block elements are ignored.
 *
 * It emits paragraphs.
 */
public class ITextLayoutVisitor extends AbstractVisitor {
    private final ICVContext context;
    private final Consumer<? super com.itextpdf.layout.element.Paragraph> paraConsumer;
    private boolean strongFlag = false;
    private boolean emphasisFlag = false;
    private boolean isLink = false;

    // Note: paras can be both actual paragraphs and inline ones here (aka inline-block spans).
    // Inline paras aren't optimal, but it's hard to do better with the current
    // tagging API
    private com.itextpdf.layout.element.Paragraph currentPara;

    public ITextLayoutVisitor(ICVContext context,
                              Consumer<? super com.itextpdf.layout.element.Paragraph> paraConsumer) {
        this.context = context;
        this.paraConsumer = paraConsumer;
    }

    private com.itextpdf.layout.element.Paragraph justifiedPara() {
        // TODO make these configurable!
        return context.createDefaultParagraph()
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setHyphenation(new HyphenationConfig("en", "GB", 2, 2))
                .setMarginBottom(4f)
                .setFontSize(11);
    }

    @Override
    public void visit(Paragraph para) {
        com.itextpdf.layout.element.Paragraph topLevelPara = justifiedPara();
        topLevelPara.addStyle(this.context.getStyle(StyleType.NormalText));
        this.currentPara = topLevelPara;
        visitChildren(para);
        this.paraConsumer.accept(topLevelPara);
    }

    @Override
    public void visit(Emphasis emphasis) {
        this.emphasisFlag = true;
        // in 1.7, this is the best we got
        this.currentPara.add(visitSpan(emphasis, StandardRoles.SPAN, null));
        this.emphasisFlag = false;
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        this.strongFlag = true;
        this.currentPara.add(visitSpan(strongEmphasis, StandardRoles.SPAN, null));
        this.strongFlag = false;
    }

    @Override
    public void visit(Code code) {
        com.itextpdf.layout.element.Text text = new com.itextpdf.layout.element.Text(code.getLiteral());
        text.addStyle(this.context.getStyle(StyleType.Code));
        text.getAccessibilityProperties().setRole(isLink ? StandardRoles.SPAN : StandardRoles.CODE);
        this.currentPara.add(text);
    }

    @Override
    public void visit(Link link) {
        String dest = link.getDestination();
        if(dest.isEmpty()) {
            return;
        }
        Style linkStyle = this.context.getStyle(StyleType.Link);
        this.isLink = true;
        com.itextpdf.layout.element.Paragraph span
                = visitSpan(link, StandardRoles.LINK, linkStyle);
        this.isLink = false;
        PdfAction action;
        if(dest.charAt(0) == '#') {
            // put in a goto action to a named destination
            action = PdfAction.createGoTo(dest.substring(1));
        } else {
            action = PdfAction.createURI(dest);
        }
        PdfLinkAnnotation linkAnnot = new PdfLinkAnnotation(new Rectangle(0, 0, 0, 0));
        if(link.getTitle() != null) {
            linkAnnot.setContents(link.getTitle());
        }
        linkAnnot.setAction(action);
        linkAnnot.setFlags(PdfAnnotation.PRINT)
                .setBorder(new PdfArray(new float[]{0, 0, 0}));
        span.setProperty(Property.ACTION, action);
        this.currentPara.add(span);
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        com.itextpdf.layout.element.Text textEl = ITextUtils
                .neutralText("\n");
        this.currentPara.add(setCurrentStyle(textEl));
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        com.itextpdf.layout.element.Text textEl = ITextUtils
                .neutralText(" ");
        this.currentPara.add(setCurrentStyle(textEl));
    }

    @Override
    public void visit(Text text) {
        com.itextpdf.layout.element.Text textEl = ITextUtils
                .neutralText(text.getLiteral());
        currentPara.add(setCurrentStyle(textEl));
    }

    private com.itextpdf.layout.element.Paragraph visitSpan(Node span, String role,
                                                            Style globalStyle) {
        com.itextpdf.layout.element.Paragraph parentPara = this.currentPara;
        // Again, not much we can do about this with the current
        // autotagger/layout interaction
        com.itextpdf.layout.element.Paragraph inlinePara
                = this.context.createDefaultParagraph().setMargin(0);
        if(globalStyle != null) {
            inlinePara.addStyle(globalStyle);
        }
        inlinePara.getAccessibilityProperties().setRole(role);

        this.currentPara = inlinePara;
        visitChildren(span);
        this.currentPara = parentPara;
        return inlinePara;
    }

    private <T extends AbstractElement<?>> T setCurrentStyle(T el) {
        final StyleType st;
        if(strongFlag && emphasisFlag) {
            st = StyleType.BoldItalicText;
        } else if(strongFlag) {
            st = StyleType.BoldText;
        } else if(emphasisFlag) {
            st = StyleType.ItalicText;
        } else {
            st = null;
        }
        if(st != null) {
            el.addStyle(this.context.getStyle(st));
        }
        return el;
    }
}
