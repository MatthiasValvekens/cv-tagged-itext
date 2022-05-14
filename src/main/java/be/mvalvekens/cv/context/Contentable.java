package be.mvalvekens.cv.context;

import com.itextpdf.layout.element.IBlockElement;

public interface Contentable<T extends IBlockElement> {

    T asContent(ICVContext context);

    static <T extends IBlockElement> Contentable<T> fromLayout(T item) {
        return (x) -> item;
    }
}
