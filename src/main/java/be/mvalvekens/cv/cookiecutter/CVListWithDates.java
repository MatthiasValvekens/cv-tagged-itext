package be.mvalvekens.cv.cookiecutter;

import be.mvalvekens.cv.components.CVItemList;
import be.mvalvekens.cv.context.Contentable;
import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.layout.element.Div;

public class CVListWithDates extends CVItemList {

    public CVListWithDates(ICVContext context, boolean zeropad) {
        super(context, zeropad);
    }

    public CVListWithDates(ICVContext context) {
        super(context);
    }

    public CVListWithDates since(int year, Contentable item) {
        return since(year, item.asContent(getContext()));
    }

    public CVListWithDates since(int year, Div item) {
        addItem(year + "\u2013", item);
        return this;
    }

    public CVListWithDates fromTo(int fromYear, int toYear, Contentable item) {
        return fromTo(fromYear, toYear, item.asContent(getContext()));
    }

    public CVListWithDates fromTo(int fromYear, int toYear, Div item) {
        addItem(fromYear + "\u2013" + toYear, item);
        return this;
    }

    public CVListWithDates on(int year, int month, int day, Contentable item) {
        return on(year, month, day, item.asContent(getContext()));
    }

    public CVListWithDates on(int year, int month, int day, Div item) {
        addItem(String.format("%d\u2013%02d\u2013%02d", year, month, day), item);
        return this;
    }
}
