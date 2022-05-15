package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.CVContent;
import be.mvalvekens.cv.context.CVListContent;
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

import java.util.ArrayList;
import java.util.List;

public class CVItemList implements CVContent<Table> {
    private final boolean zeropad;
    private final List<ListItem> items;

    public CVItemList(boolean zeropad) {
        this.zeropad = zeropad;
        this.items = new ArrayList<>();
    }

    public CVItemList() {
        this(false);
    }

    public void addItem(String label, CVListContent content) {
        this.items.add(new ListItem(label, content));
    }

    @Override
    public Table layoutContent(ICVContext context) {
        Table backingTable = new Table(new UnitValue[] {
                UnitValue.createPointValue(80),
                UnitValue.createPointValue(400),
        }).setBorder(Border.NO_BORDER);
        this.items.forEach(item -> addRenderedItem(context, backingTable, item));
        return backingTable;
    }

    private void addRenderedItem(ICVContext context, Table backingTable, ListItem item) {
        backingTable.startNewRow();
        Div content = item.content.layoutContent(context);
        content.getAccessibilityProperties().setRole(item.content.getRole());
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

        Paragraph lblPara = context.createDefaultParagraph(item.label)
                .setTextAlignment(TextAlignment.RIGHT);
        lblPara.getAccessibilityProperties().setRole(null);
        lblCell.add(lblPara);
        backingTable.addCell(lblCell);
        Cell c = new Cell().setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP);
        if(zeropad) {
            c.setPadding(0);
        }
        c.add(content);
        backingTable.addCell(c);
    }

    private static final class ListItem {
        private final String label;
        private final CVListContent content;

        private ListItem(String label, CVListContent content) {
            this.label = label;
            this.content = content;
        }
    }
}
