package be.mvalvekens.cv.elems;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TargetCounterHandler;

public class RuleRenderer extends AbstractRenderer {
    public RuleRenderer(Rule rule) {
        super(rule);
    }

    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutArea area = layoutContext.getArea();
        Float width = retrieveWidth(area.getBBox().getWidth());
        Float height = retrieveHeight();
        float raiseHeight = ((Rule) this.modelElement).getRaiseHeight();
        Rectangle bbox = new Rectangle(area.getBBox().getX(), raiseHeight + area.getBBox().getY() + area.getBBox().getHeight(), width, height);
        occupiedArea = new LayoutArea(area.getPageNumber(), bbox);

        TargetCounterHandler.addPageByID(this);

        return new LayoutResult(LayoutResult.FULL, occupiedArea, null, null);
    }

    @Override
    public void draw(DrawContext drawContext) {
        if (occupiedArea == null) {
            return;
        }

        boolean isTagged = drawContext.isTaggingEnabled();
        if (isTagged) {
            drawContext.getCanvas().openTag(new CanvasArtifact());
        }

        float raiseHeight = ((Rule) this.modelElement).getRaiseHeight();
        beginElementOpacityApplying(drawContext);
        drawContext.getCanvas()
                .rectangle(occupiedArea.getBBox().moveUp(raiseHeight)).fill();
        endElementOpacityApplying(drawContext);

        if (isTagged) {
            drawContext.getCanvas().closeTag();
        }
    }

    @Override
    public IRenderer getNextRenderer() {
        return new RuleRenderer((Rule) modelElement);
    }
}
