package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

public class CVItemList {
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

    public void addToContainer(Div container) {
        container.add(backingTable);
    }

    public void addToContainer(CVSection container) {
        container.add(backingTable);
    }

}
