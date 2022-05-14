package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.renderer.IRenderer;

import java.util.List;

public class CVItemList implements IBlockElement {
    private final Table backingTable;
    private final boolean zeropad;
    private final ICVContext context;

    public CVItemList(ICVContext context, boolean zeropad) {
        this.zeropad = zeropad;
        this.backingTable = new Table(new UnitValue[] {
                UnitValue.createPointValue(80),
                UnitValue.createPointValue(400),
        }).setBorder(Border.NO_BORDER);
        this.context = context;
    }

    public CVItemList(ICVContext context) {
        this(context, false);
    }

    public void addItem(String label, Div content) {
        this.backingTable.startNewRow();
        content.getAccessibilityProperties().setRole(null);
        content.setMarginLeft(10);
        Cell lblCell = new Cell().setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP);
        if(zeropad) {
            lblCell.setPadding(0);
        }
        AccessibilityProperties lblProps = lblCell.getAccessibilityProperties();
        lblProps.setRole(StandardRoles.TH);
        PdfStructureAttributes attrs = new PdfStructureAttributes("Table");
        attrs.addEnumAttribute("Scope", "Row");
        lblProps.addAttributes(attrs);

        Paragraph lblPara = this.context.createDefaultParagraph(label)
                .setTextAlignment(TextAlignment.RIGHT);
        lblPara.getAccessibilityProperties().setRole(null);
        lblCell.add(lblPara);
        this.backingTable.addCell(lblCell);
        Cell c = new Cell().setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP);
        if(zeropad) {
            c.setPadding(0);
        }
        c.add(content);
        this.backingTable.addCell(c);
    }

    @Override
    public List<IElement> getChildren() {
        return backingTable.getChildren();
    }

    @Override
    public void setNextRenderer(IRenderer renderer) {
        backingTable.setNextRenderer(renderer);
    }

    @Override
    public IRenderer getRenderer() {
        return backingTable.getRenderer();
    }

    @Override
    public IRenderer createRendererSubTree() {
        return backingTable.createRendererSubTree();
    }

    @Override
    public boolean hasProperty(int property) {
        return backingTable.hasProperty(property);
    }

    @Override
    public boolean hasOwnProperty(int property) {
        return backingTable.hasOwnProperty(property);
    }

    @Override
    public <T1> T1 getProperty(int property) {
        return backingTable.getProperty(property);
    }

    @Override
    public <T1> T1 getOwnProperty(int property) {
        return backingTable.getOwnProperty(property);
    }

    @Override
    public <T1> T1 getDefaultProperty(int property) {
        return backingTable.getDefaultProperty(property);
    }

    @Override
    public void setProperty(int property, Object value) {
        backingTable.setProperty(property, value);
    }

    @Override
    public void deleteOwnProperty(int property) {
        backingTable.deleteOwnProperty(property);
    }

    protected ICVContext getContext() {
        return this.context;
    }

}
