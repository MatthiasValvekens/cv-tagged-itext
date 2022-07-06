package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.context.CVContent;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.itextts.context.StyleType;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.util.ArrayList;
import java.util.List;

public class LanguageSkills implements CVContent<Table> {
    private final List<LanguageEntry> languageEntries;

    public LanguageSkills() {
        languageEntries = new ArrayList<>();
    }

    public void addEntry(String lang, String qual, String... comments) {
        languageEntries.add(new LanguageEntry(lang, qual, comments));
    }

    private static void addLangRow(ICVContext context, Table t, LanguageEntry entry) {
        t.startNewRow();
        Cell langCell = new Cell().add(context.createParagraphWithRole(entry.lang, null));
        AccessibilityProperties lblProps = langCell.getAccessibilityProperties();
        lblProps.setRole(StandardRoles.TH);
        PdfStructureAttributes attrs = new PdfStructureAttributes("Table");
        attrs.addEnumAttribute("Scope", "Row");
        lblProps.addAttributes(attrs);

        t.addCell(langCell.setBorder(Border.NO_BORDER).setPadding(0));

        t.addCell(new Cell().add(context.createParagraphWithRole(entry.qual, null))
                .setBorder(Border.NO_BORDER).setPadding(0));

        Cell commentCell = new Cell().addStyle(context.getStyle(StyleType.ItalicText)).setBorder(Border.NO_BORDER);
        if(entry.comments.length != 0) {
            for(String s : entry.comments) {
                commentCell.add(context.createDefaultParagraph(s)).setFontSize(10);
            }
        }
        t.addCell(commentCell.setPadding(0));
        commentCell.setTextAlignment(TextAlignment.RIGHT);
    }

    @Override
    public Table layoutContent(ICVContext context) {
        Table t = new Table(new UnitValue[] {
                UnitValue.createPointValue(80),
                UnitValue.createPointValue(200),
                UnitValue.createPointValue(200),
        }).setBorder(Border.NO_BORDER);
        for (LanguageEntry e : languageEntries) {
            addLangRow(context, t, e);
        }
        return t;
    }

    private static final class LanguageEntry {
        final String lang;
        final String qual;
        final String[] comments;

        private LanguageEntry(String lang, String qual, String[] comments) {
            this.lang = lang;
            this.qual = qual;
            this.comments = comments;
        }
    }


}
