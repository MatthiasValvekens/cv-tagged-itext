package be.mvalvekens.cv.elems;

import com.itextpdf.layout.element.AbstractElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;

public class Rule extends AbstractElement<Rule> implements ILeafElement {

    private final float raiseHeight;

    public Rule(UnitValue width, UnitValue height, float raiseHeight) {
        this.raiseHeight = raiseHeight;
        setProperty(Property.WIDTH, width);
        setProperty(Property.HEIGHT, height);
    }

    @Override
    protected IRenderer makeNewRenderer() {
        return new RuleRenderer(this);
    }

    public float getRaiseHeight() {
        return raiseHeight;
    }

}
