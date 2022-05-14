package be.mvalvekens.cv.context;

import com.itextpdf.layout.element.IBlockElement;

public interface CVContent<T extends IBlockElement> {

    T layoutContent(ICVContext context);

    static <T extends IBlockElement> CVContent<T> fromLayout(T item) {
        return (x) -> item;
    }
}
