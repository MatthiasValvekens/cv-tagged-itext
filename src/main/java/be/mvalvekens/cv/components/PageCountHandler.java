package be.mvalvekens.cv.components;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class PageCountHandler implements IEventHandler {

    private final PdfFont font;

    public PageCountHandler(PdfFont font) {
        this.font = font;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        int pageNum = docEvent.getDocument().getPageNumber(page);
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.beginText()
                .setFontAndSize(this.font, 12)
                .beginMarkedContent(PdfName.Artifact)
                .moveText(PageSize.A4.getWidth() / 2, 25)
                .showText(String.format("%d", pageNum))
                .endText()
                .endMarkedContent()
                .release();
    }
}
