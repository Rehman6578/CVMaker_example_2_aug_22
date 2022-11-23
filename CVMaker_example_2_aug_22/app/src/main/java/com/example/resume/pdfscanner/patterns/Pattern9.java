package com.example.resume.pdfscanner.patterns;

import android.content.Context;
import androidx.annotation.NonNull;

import com.example.resume.models.Education;
import com.example.resume.models.Experience;
import com.example.resume.models.Project;
import com.example.resume.utils.Logger;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.IOException;
import java.util.List;


public class Pattern9 extends GeneratorPDF {

    private static final String TAG = "Pattern 9";

    private PdfPTable mainTable;
    private PdfPTable topTable;
    private PdfPTable footerTable;
    Context context;

    public Pattern9(@NonNull Context context, @NonNull List<Boolean> selectedFieldList, BaseColor selectedColor) throws IOException, DocumentException {
        super(context, selectedFieldList, selectedColor);
        this.context=context;
    }

    @Override
    protected void createDocument() {
        document = new Document(PageSize.A4, 0, 0, 0, 0);
    }

    @Override
    protected void write() throws DocumentException {
        if (selectedFieldList.get(0)) {
            addPersonalInfo();
        }
        if (selectedFieldList.get(1)) {
            addObjective();
        }
        if (selectedFieldList.get(4)) {
            addExperience();
        }
        if (selectedFieldList.get(2)) {
            addEducation();
        }
        if (selectedFieldList.get(8)) {
            addProjects();
        }

        mainTable.addCell(getHeadingCell(OTHER_INFO_ICON, "OTHER INFO"));

        if (selectedFieldList.get(7)) {
            addAchievements();
        }
        if (selectedFieldList.get(3)) {
            addSkills();
        }
        if (selectedFieldList.get(5)) {
            addHobbies();
        }
        if (selectedFieldList.get(6)) {
            addLanguages();
        }
    }

    @Override
    protected void setUP() throws DocumentException {

        //==========================================================================================

        mainTable = new PdfPTable(1);
        mainTable.setWidthPercentage(100);
        mainTable.setSplitLate(false);

        //==========================================================================================

        footerTable = new PdfPTable(2);
        footerTable.setWidthPercentage(100);
        footerTable.setSplitLate(false);

        //==========================================================================================

        topTable = new PdfPTable(3);
        topTable.setWidthPercentage(100);
        topTable.setSplitLate(false);

        //==========================================================================================
    }

    @Override
    protected void closeUp() throws DocumentException {

        //==========================================================================================

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(25);
        cell.setPaddingTop(5);
        cell.addElement(footerTable);
        mainTable.addCell(cell);

        //==========================================================================================

        document.add(mainTable);
    }

    @Override
    protected void addPersonalInfo() throws DocumentException {

        //==========================================================================================

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        //==========================================================================================

        cell.addElement(new Paragraph(Chunk.NEWLINE));

        //==========================================================================================

        mainTable.addCell(cell);

        //==========================================================================================

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);

        //==========================================================================================

        addElementInFirstCell(cell);

        //==========================================================================================

        topTable.addCell(cell);

        //==========================================================================================

        if (selectedFieldList.get(10))
            addImage();

        //==========================================================================================

        cell = new PdfPCell();
        cell.setPadding(10);
        cell.setBorder(Rectangle.NO_BORDER);

        //==========================================================================================

        addElementInLastCell(cell);

        //==========================================================================================

        topTable.addCell(cell);

        //==========================================================================================

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(selectedColor);

        //==========================================================================================

        cell.addElement(topTable);

        //==========================================================================================

        mainTable.addCell(cell);

        //==========================================================================================

        cell = new PdfPCell();
        cell.setPadding(10);
        cell.setBorder(Rectangle.NO_BORDER);

        //==========================================================================================

        Paragraph paragraph = new Paragraph(personalInfo.getName());
        paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, selectedColor));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        //==========================================================================================

        mainTable.addCell(cell);

    }

    private void addElementInFirstCell(PdfPCell cell) throws BadElementException {
        cell.setPaddingTop(50);
        cell.setPaddingLeft(20);
        cell.addElement(getParagraphWithImage(PHONE_ICON, personalInfo.getPhoneNumber()));
        cell.addElement(new Paragraph(Chunk.NEWLINE));
        cell.addElement(getParagraphWithImage(EMAIL_ICON, personalInfo.getEmail()));
        cell.addElement(new Paragraph(Chunk.NEWLINE));
        cell.addElement(getParagraphWithImage(ADDRESS_ICON, personalInfo.getAddress()));
    }

    private Paragraph getParagraphWithImage(String fileName, String content) throws BadElementException {
        Paragraph paragraph = new Paragraph();
        try {
            paragraph.add(new Chunk(getIcon(fileName, 15, 15), -3, -3));
        } catch (Exception ignored) {
        }
        paragraph.add("  " + content);
        paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA, 10));
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        return paragraph;
    }

    private void addElementInLastCell(PdfPCell cell) throws BadElementException {
        cell.setPaddingTop(50);
        cell.setPaddingLeft(20);
        cell.addElement(getParagraphWithImage("", "DOB  "+personalInfo.getDateOfBirth()));
        cell.addElement(new Paragraph(Chunk.NEWLINE));
        cell.addElement(getParagraphWithImage("","Indentification #  "+ personalInfo.getCNIC()));
        cell.addElement(new Paragraph(Chunk.NEWLINE));
        cell.addElement(getParagraphWithImage("","Nationality "+ personalInfo.getNationality()));
    }

    @Override
    protected void addImage() throws DocumentException {
        super.addImage();
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        if (profileImage != null) {
            cell.addElement(profileImage);
        }
        topTable.addCell(cell);
    }

    @Override
    protected void setPageEvent() {
    }

    @Override
    protected void addObjective() throws DocumentException {

        //==========================================================================================

        PdfPCell cell = new PdfPCell();
        cell.setPadding(10);
        cell.setBorder(Rectangle.NO_BORDER);

        //==========================================================================================

        Paragraph paragraph = new Paragraph(getParagraphWithImage(OBJECTIVE_ICON,objective.getObjectiveStatement()));

        paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA, 14));
        paragraph.setAlignment(Element.ALIGN_CENTER);
       // cell.addElement(getParagraphWithImage(OBJECTIVE_ICON,objective.getObjectiveStatement()));
        cell.addElement(paragraph);

        //==========================================================================================

        mainTable.addCell(cell);

        //==========================================================================================

    }

    @Override
    protected void addEducation() throws DocumentException {

        //==========================================================================================

        mainTable.addCell(getHeadingCell(EDUCATION_ICON, educationList.get(0).getHeading()));

        //==========================================================================================

        float[] width = {4, 2, 2};
        PdfPCell cell;
        PdfPTable table = new PdfPTable(width);

        //==========================================================================================

        for (Education education : educationList) {

            //==========================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    education.getDegreeTitle().toUpperCase() + " from " +
                            education.getCollegeName()
            ));
            table.addCell(cell);

            //==========================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    education.getStartingYear() + " - " + education.getCompletingYear()
            ));
            table.addCell(cell);

            //==========================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    education.getObtainedMarks() + " / " + education.getTotalMarks()
            ));
            table.addCell(cell);
        }

        cell = getCell();
        cell.addElement(table);
        mainTable.addCell(cell);
    }

    @Override
    protected void addExperience() throws DocumentException {

        //==========================================================================================

        mainTable.addCell(getHeadingCell(EXPERIENCE_ICON, experienceList.get(0).getHeading()));

        //==========================================================================================

        float[] width = {1, 1, 2};
        PdfPCell cell;
        PdfPTable table = new PdfPTable(width);

        //==========================================================================================

        for (Experience experience : experienceList) {

            //======================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    experience.getCompanyName().toUpperCase()
            ));
            cell.addElement(new Paragraph(
                    experience.getJobTitle().toUpperCase()
            ));
            table.addCell(cell);

            //======================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    experience.getStartDate() + " - " + experience.getEndDate()
            ));
            table.addCell(cell);

            //======================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    experience.getJobDescription()
            ));
            table.addCell(cell);
        }

        cell = getCell();
        cell.addElement(table);
        mainTable.addCell(cell);
    }

    @Override
    protected void addProjects() throws DocumentException {

        //==========================================================================================

        mainTable.addCell(getHeadingCell(PROJECT_ICON, projectList.get(0).getHeading()));

        //==========================================================================================

        float[] width = {2, 4};
        PdfPCell cell;
        PdfPTable table = new PdfPTable(width);

        //==========================================================================================

        for (Project project : projectList) {

            //======================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    project.getName().toUpperCase()
            ));
            table.addCell(cell);

            //======================================================================================

            cell = getCell();
            cell.addElement(new Paragraph(
                    project.getDescription()
            ));
            table.addCell(cell);
        }

        cell = getCell();
        cell.addElement(table);
        mainTable.addCell(cell);
    }

    private PdfPCell getHeadingCell(String fileName, String heading) throws BadElementException {
        PdfPCell cell = new PdfPCell();

        LineSeparator line = new LineSeparator();
        line.setOffset(-2);
        line.setLineColor(selectedColor);
        line.setLineWidth(1);

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(getIcon(fileName, 20, 20), 0, -3));
        paragraph.add("  " + heading.toUpperCase());
        paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, selectedColor));
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

        paragraph.add(line);

        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(10);
        cell.setPaddingLeft(20);
        return cell;
    }

    @Override
    protected void addSkills() throws DocumentException {

        //==========================================================================================

        PdfPCell cell = getCell();
        cell.addElement(getHeading(skills.getHeading()));
        cell.addElement(getContent(skills.getSkills()));
        footerTable.addCell(cell);

        //==========================================================================================

    }

    @Override
    protected void addHobbies() throws DocumentException {

        //==========================================================================================

        PdfPCell cell = getCell();
        cell.addElement(getHeading(hobbies.getHeading()));
        cell.addElement(getContent(hobbies.getHobbies()));
        footerTable.addCell(cell);

        //==========================================================================================

    }

    @Override
    protected void addLanguages() throws DocumentException {

        //==========================================================================================

        PdfPCell cell = getCell();
        cell.addElement(getHeading(languages.getHeading()));
        cell.addElement(getContent(languages.getLanguages()));
        footerTable.addCell(cell);

        //==========================================================================================

    }

    @Override
    protected void addAchievements() throws DocumentException {

        //==========================================================================================

        if (achievements == null) {
            Logger.i(TAG,"Null");
            return;
        }
        PdfPCell cell = getCell();
        cell.addElement(getHeading(achievements.getHeading()));
        cell.addElement(getContent(achievements.getAchievements()));
        footerTable.addCell(cell);

        //==========================================================================================

    }

    private Paragraph getHeading(String heading) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(heading.toUpperCase());
        paragraph.setFont(FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, selectedColor));
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        return paragraph;
    }

    private Paragraph getContent(String content) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(content);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        return paragraph;
    }

    private PdfPCell getCell() {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(10);
        return cell;
    }

    @Override
    protected void addReferences() throws DocumentException {

    }
}
