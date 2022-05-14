package be.mvalvekens.cv;

import be.mvalvekens.cv.components.CVSection;
import be.mvalvekens.cv.components.PageCountHandler;
import be.mvalvekens.cv.context.HeadingType;
import be.mvalvekens.cv.context.ICVContext;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.pdfa.PdfADocument;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CVDocument implements AutoCloseable{

    private final ICVContext context;
    private final PdfDocument pdfDoc;
    private PdfOutline currentOutline = null;

    public CVDocument(PdfDocument pdfDoc, ICVContext context) {
        this.context = context;
        this.pdfDoc = pdfDoc;
        PageCountHandler pch = new PageCountHandler(context.getDefaultFont());
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, pch);
        pdfDoc.getCatalog().setLang(new PdfString(context.getLang()));

        PdfDocumentInfo info = pdfDoc.getDocumentInfo();
        info.setAuthor(context.getName());
        info.setTitle(String.format("%s \u2014 %s", context.getName(), context.getCVTitle()));
    }

    public CVSection initSubSection(String heading, String destName) {
        return CVSection.initSection(getContext(), HeadingType.Subsection, heading, currentOutline, destName);
    }

    public CVSection initSection(String heading, String destName) {
        PdfOutline parentOutline = currentOutline == null ?
                pdfDoc.getOutlines(false) : currentOutline.getParent();
        CVSection section = CVSection.initSection(
                getContext(), HeadingType.Section, heading, parentOutline, destName);
        this.currentOutline = section.getOutline();
        return section;
    }

    protected ICVContext getContext() {
        return context;
    }

    protected PdfDocument getPdfDoc() {
        return pdfDoc;
    }

    protected Document initLayout() {
        Document doc = new Document(pdfDoc);
        doc.setFont(getContext().getDefaultFont())
                .setFontColor(DeviceRgb.BLACK)
                .setFontSize(11);
        doc.setLeftMargin(60);
        doc.setRightMargin(60);
        return doc;
    }

    public static PdfDocument initPdfDoc(OutputStream out) throws IOException {
        WriterProperties wp = new WriterProperties()
                .setFullCompressionMode(true) // lots of tagging = benefit from object streams
                .setPdfVersion(PdfVersion.PDF_1_7)
                .addUAXmpMetadata();

        ClassLoader cl = CVDocument.class.getClassLoader();
        PdfOutputIntent oi;
        try(InputStream icc = cl.getResourceAsStream("icc-profiles/sRGB2014.icc")) {
            if(icc == null) {
                throw new FileNotFoundException();
            }
            oi = new PdfOutputIntent(
                    "Custom", "",
                    "https://www.color.org", null, icc);
        }
        PdfWriter w = new PdfWriter(out, wp);
        PdfADocument pdfDoc = new PdfADocument(w, PdfAConformanceLevel.PDF_A_2A, oi);
        pdfDoc.setTagged();
        pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        return pdfDoc;
    }

    @Override
    public void close() {
        pdfDoc.close();
    }
}
