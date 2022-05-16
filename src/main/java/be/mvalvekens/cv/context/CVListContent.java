package be.mvalvekens.cv.context;

import com.itextpdf.layout.element.Div;

public interface CVListContent extends CVContent<Div> {
    /**
     * Returns an explicit role that the item handling logic
     * will apply
     */
    default String getRole() {
        return null;
    }

    /**
     * Returns an explicit namespace that the item handling logic will apply.
     */
    default String getNamespace() {
        return null;
    }

    static CVListContent fromLayout(Div item) {
        return (x) -> item;
    }
}
