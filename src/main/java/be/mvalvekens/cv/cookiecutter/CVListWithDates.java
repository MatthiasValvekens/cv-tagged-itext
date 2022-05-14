package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.components.CVItemList;
import be.mvalvekens.cv.context.CVContent;
import com.itextpdf.layout.element.Div;

public class CVListWithDates extends CVItemList {

    public CVListWithDates(boolean zeropad) {
        super(zeropad);
    }

    public CVListWithDates() {
        super();
    }

    public CVListWithDates since(int year, CVContent<Div> item) {
        addItem(year + "\u2013", item);
        return this;
    }

    public CVListWithDates fromTo(int fromYear, int toYear, CVContent<Div> item) {
        addItem(fromYear + "\u2013" + toYear, item);
        return this;
    }

    public CVListWithDates on(int year, int month, int day, CVContent<Div> item) {
        addItem(String.format("%d\u2013%02d\u2013%02d", year, month, day), item);
        return this;
    }
}
