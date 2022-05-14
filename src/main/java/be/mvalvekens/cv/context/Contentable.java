package be.mvalvekens.cv.context;

import com.itextpdf.layout.element.Div;

public interface Contentable {

    Div asContent(ICVContext context);
}
