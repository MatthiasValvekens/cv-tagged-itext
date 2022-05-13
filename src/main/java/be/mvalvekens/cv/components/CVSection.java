package be.mvalvekens.cv.components;

import be.mvalvekens.cv.elems.Rule;
import be.mvalvekens.cv.utils.ITextUtils;
import be.mvalvekens.cv.utils.StyleManager;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;

import java.util.List;

/**
 * A section in a CV.
 */
public class CVSection implements IBlockElement {
    private final Div sectContainer;
    private final StyleManager styles;
    private PdfOutline outline = null;

    protected CVSection(StyleManager styles) {
        this.styles = styles;
        this.sectContainer = new Div();
        this.sectContainer.getAccessibilityProperties().setRole(StandardRoles.SECT);
    }

    protected Paragraph prepareHeading(String headingText) {
        Paragraph h = new Paragraph().addStyle(this.styles.get(StyleType.Heading));
        h.setMarginBottom(0);
        h.add(new Rule(UnitValue.createPointValue(80), UnitValue.createPointValue(5), 4));
        h.getAccessibilityProperties().setRole(StandardRoles.H2);

        Paragraph innerH = new Paragraph().setMargin(0).setMarginLeft(10);
        innerH.getAccessibilityProperties().setRole(null);
        Text hText = ITextUtils.neutralText(headingText);
        innerH.add(hText);
        h.add(innerH);
        return h;
    }

    protected void addHeading(String headingText,
                              PdfOutline parentOutline, String destName) {
        Paragraph heading = this.prepareHeading(headingText);
        heading.setProperty(Property.DESTINATION, destName);
        sectContainer.add(heading);
        this.outline = parentOutline.addOutline(headingText);
        this.outline.addDestination(PdfDestination.makeDestination(new PdfString(destName)));
    }

    public PdfOutline getOutline() {
        return this.outline;
    }

    /**
     * Adds a block element to the section.
     *
     * @param element an {@link IBlockElement}
     * @return this {@link CVSection}
     */
    public CVSection add(IBlockElement element) {
        sectContainer.add(element);
        return this;
    }

    /**
     * Adds an image to the section.
     *
     * @param element an {@link Image}
     * @return this {@link CVSection}
     */
    public CVSection add(Image element) {
        sectContainer.add(element);
        return this;
    }

    /**
     * Adds an area break to the section.
     *
     * @param areaBreak an {@link AreaBreak}
     * @return this {@link CVSection}
     */
    public CVSection add(AreaBreak areaBreak) {
        sectContainer.add(areaBreak);
        return this;
    }

    /**
     * Initialises a new section.
     *
     * @param styles a {@link StyleManager}
     * @param headingText the text to display in the heading
     * @param parentOutline the parent outline (or "bookmark") of which this section is a child
     * @param destName the name of the document destination to associate with this section
     * @return the new {@link CVSection}
     */
    public static CVSection initSection(StyleManager styles, String headingText,
                                        PdfOutline parentOutline, String destName) {
        CVSection section = new CVSection(styles);
        section.addHeading(headingText, parentOutline, destName);
        return section;
    }

    @Override
    public List<IElement> getChildren() {
        return sectContainer.getChildren();
    }

    @Override
    public void setNextRenderer(IRenderer renderer) {
        sectContainer.setNextRenderer(renderer);
    }

    @Override
    public IRenderer getRenderer() {
        return sectContainer.getRenderer();
    }

    @Override
    public IRenderer createRendererSubTree() {
        return sectContainer.createRendererSubTree();
    }

    @Override
    public boolean hasProperty(int property) {
        return sectContainer.hasProperty(property);
    }

    @Override
    public boolean hasOwnProperty(int property) {
        return sectContainer.hasOwnProperty(property);
    }

    @Override
    public <T1> T1 getProperty(int property) {
        return sectContainer.getProperty(property);
    }

    @Override
    public <T1> T1 getOwnProperty(int property) {
        return sectContainer.getOwnProperty(property);
    }

    @Override
    public <T1> T1 getDefaultProperty(int property) {
        return sectContainer.getDefaultProperty(property);
    }

    @Override
    public void setProperty(int property, Object value) {
        sectContainer.setProperty(property, value);
    }

    @Override
    public void deleteOwnProperty(int property) {
        sectContainer.deleteOwnProperty(property);
    }
}
