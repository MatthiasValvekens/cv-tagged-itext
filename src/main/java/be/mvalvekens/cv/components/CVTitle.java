package be.mvalvekens.cv.components;

import be.mvalvekens.cv.context.CVContent;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.cv.context.StyleType;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.IOException;
import java.util.List;

public class CVTitle implements CVContent<IBlockElement> {
    private final List<CVInfoLink> infoLinks;
    private final ImageData profilePicData;
    private UnitValue[] tableCellDimensions = new UnitValue[] {
            UnitValue.createPercentValue(50),
            UnitValue.createPercentValue(30),
            UnitValue.createPercentValue(15)
    };
    private String profilePicAlt = "Profile picture";

    protected CVTitle(List<CVInfoLink> infoLinks, ImageData profilePicData) {
        this.profilePicData = profilePicData;
        this.infoLinks = infoLinks;
    }

    public CVTitle(List<CVInfoLink> infoLinks, String profilePicPath) throws IOException {
        this(infoLinks, ImageDataFactory.create(profilePicPath));
    }

    public CVTitle(List<CVInfoLink> infoLinks, byte[] profilePicData) {
        this(infoLinks, ImageDataFactory.create(profilePicData));
    }

    @Override
    public IBlockElement layoutContent(ICVContext context) {
        Table container = new Table(tableCellDimensions);
        container.addStyle(context.getStyle(StyleType.NormalText));
        container.getAccessibilityProperties().setRole(null);
        container.startNewRow();

        Cell titleCell = new Cell();
        titleCell.setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.BOTTOM);
        // Title is not in the PDF 1.7 namespace
        titleCell.getAccessibilityProperties().setRole(StandardRoles.H1);
        Paragraph name = context.createDefaultParagraph()
                .setMargin(3)
                .setFontSize(25)
                .addStyle(context.getStyle(StyleType.NormalText));
        name.getAccessibilityProperties().setRole(null);
        name.add(context.getName());
        titleCell.add(name);

        Paragraph cvSub = new Paragraph(context.getCVTitle())
                .setFontSize(18)
                .addStyle(context.getStyle(StyleType.ObliqueText));
        cvSub.getAccessibilityProperties().setRole(null);
        titleCell.add(cvSub);
        container.addCell(titleCell);

        Cell linksCell = new Cell();
        linksCell.setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.BOTTOM);
        linksCell.getAccessibilityProperties().setRole(StandardRoles.L);
        linksCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        for(CVInfoLink lnk : infoLinks) {
            linksCell.add(lnk.formatSocialLink(context));
        }

        container.addCell(linksCell);
        Cell profilePicCell = new Cell();
        profilePicCell.getAccessibilityProperties().setRole(null);
        Image profilePic = new Image(this.profilePicData);
        profilePic.setWidth(UnitValue.createPercentValue(100));
        profilePic
                .getAccessibilityProperties()
                .setAlternateDescription(profilePicAlt);
        PdfStructureAttributes picAttrs = new PdfStructureAttributes("Layout");
        picAttrs.addEnumAttribute("Placement", "Block");
        profilePic
                .getAccessibilityProperties()
                .addAttributes(picAttrs);
        profilePicCell.add(profilePic);
        container.addCell(profilePicCell);
        return container;
    }

    public CVTitle setProfilePicAlt(String profilePicAlt) {
        this.profilePicAlt = profilePicAlt;
        return this;
    }

    public CVTitle setTableCellDimensions(UnitValue[] tableCellDimensions) {
        this.tableCellDimensions = tableCellDimensions;
        return this;
    }

}
