package be.mvalvekens.cv;

import be.mvalvekens.cv.components.CVInfoLink;
import be.mvalvekens.cv.components.CVSection;
import be.mvalvekens.cv.components.CVTitle;
import be.mvalvekens.cv.context.CVContextBuilder;
import be.mvalvekens.cv.context.ICVContext;
import be.mvalvekens.cv.cookiecutter.CVListWithDates;
import be.mvalvekens.cv.cookiecutter.ExperienceItem;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static be.mvalvekens.cv.utils.ITextUtils.getResourceBytes;

public class MyExampleCV extends CVDocument {

    public MyExampleCV(PdfDocument pdfDoc, ICVContext context) {
        super(pdfDoc, context);
    }

    protected void addWorkExperience(CVSection container) {
        CVSection industrySub = initSubSection("Industry", "industry");
        CVListWithDates industry = new CVListWithDates(true);
        ExperienceItem item1 = new ExperienceItem("District manager", "ACME Corp", "Somewhere",
                "Some responsibilities",
                "A critical task I helped accomplish",
                "Stuff I did");
        ExperienceItem item2 = new ExperienceItem("Assistant", "Widgets R Us", "Somewhere",
                "Some responsibilities",
                "A critical task I helped accomplish",
                "Stuff I did");
        industry.since(2021, item1).fromTo(2018, 2021, item2);
        industrySub.add(industry.layoutContent(getContext()));
        container.add(industrySub);

        CVSection volunteerSub = initSubSection("Volunteering", "volunteering");
        CVListWithDates volunteering = new CVListWithDates(true);
        ExperienceItem item3 = new ExperienceItem("Administrator", "Charity Org", "Somewhere",
                "Things I did", "Bla bla");
        volunteering.fromTo(2015, 2020, item3);
        volunteerSub.add(volunteering.layoutContent(getContext()));
        container.add(volunteerSub);
    }

    public CVTitle buildTitle(PdfFont symbolFont) {
        List<CVInfoLink> links = new ArrayList<>();
        // Cellphone
        links.add(new CVInfoLink(
                new CVInfoLink.Label("Mobile", "T", symbolFont),
                "+32 000 000 000",
                "tel:+32000000000"));
        links.add(new CVInfoLink(
                new CVInfoLink.Label("E-mail", "E", symbolFont),
                "me@example.com",
                "mailto:me@example.com"));

        return new CVTitle(links, getResourceBytes(null, "pic.png"));
    }

    public static void write(String outfile) throws IOException {
        try(PdfDocument pdfDoc = CVDocument.initPdfDoc(new FileOutputStream(outfile))) {
            PdfFont mainFont = loadDefaultMainFont();
            ICVContext context = new CVContextBuilder()
                    .setMainFont(mainFont)
                    .setStyles(loadDefaultStyles(mainFont))
                    .setName("John Doe")
                    .build();
            MyExampleCV cv = new MyExampleCV(pdfDoc, context);

            Document doc = cv.initLayout();
            doc.add(cv.buildTitle(mainFont).layoutContent(context));

            CVSection experience = cv.initSection("Work experience", "experience");
            cv.addWorkExperience(experience);
            doc.add(experience);

            // etc. etc.

            doc.close();
        }
    }

    public static void main(String[] args) throws Exception {
        write("testcv.pdf");
    }
}

