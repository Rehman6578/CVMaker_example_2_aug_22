package com.example.resume.pdfscanner.patterns;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.example.resume.CVMaker;
import com.example.resume.models.Achievements;
import com.example.resume.models.Education;
import com.example.resume.models.Experience;
import com.example.resume.models.Hobbies;
import com.example.resume.models.Languages;
import com.example.resume.models.Objective;
import com.example.resume.models.PersonalInformation;
import com.example.resume.models.Project;
import com.example.resume.models.Reference;
import com.example.resume.models.Skills;
import com.example.resume.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.realm.Realm;

import static com.example.resume.activities.Constants.EXTENSION;
import static com.example.resume.activities.Constants.FOLDER_LOCATION;
import static com.example.resume.activities.Constants.KEY_FILE_LOCATION;
import static com.example.resume.activities.Constants.SEPARATOR;

/**
 * Project Name : TextToPDF
 * Created by   : Ummer Siddique
 * Created on   : July 20, 2017
 * Created at   : 11:09 AM
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class GeneratorPDF {

    //==============================================================================================
    protected static final String CV_NAME_PREFIX = "MY CV ";
    protected static final float MARGIN_LEFT = 15;
    protected static final float MARGIN_RIGHT = 15;
    protected static final float MARGIN_TOP = 15;
    protected static final float MARGIN_BOTTOM = 15;
    protected static final String fileName;

    //==============================================================================================

    protected static final String PROFILE_ICON = "profile.png";
    protected static final String CONTACT_ME_ICON = "contact_me.png";
    protected static final String EMAIL_ICON = "email.png";
    protected static final String PHONE_ICON = "phone.png";
    protected static final String OTHER_INFO_ICON = "other_info.png";
    protected static final String OBJECTIVE_ICON = "objective.png";
    protected static final String EDUCATION_ICON = "education.png";
    protected static final String EXPERIENCE_ICON = "experience.png";
    protected static final String HOBBIES_ICON = "hobbies.png";
    protected static final String LANGUAGES_ICON = "languages.png";
    protected static final String ADDRESS_ICON = "address.png";
    protected static final String PROJECT_ICON = "project.png";
    protected static final String ACHIEVEMENTS_ICON = "achievements.png";
    protected static final String REFERENCES_ICON = "reference.png";
    protected static final String SKILLS_ICON = "skills.png";

    // ==============================================================================================

    protected static final String HEADING_SEPARATOR = " : ";

    //==============================================================================================

    private static final String TAG = "GeneratorPDF";

    static {
       // fileName = CV_NAME_PREFIX + DateFormat.getDateTimeInstance().format(new Date());
       // fileName = CV_NAME_PREFIX + Calendar.getInstance().get(Calendar.MILLISECOND);;
        Random rand = new Random();

        int  n = rand.nextInt(100) + 1;
        fileName = CV_NAME_PREFIX +System.currentTimeMillis()+n;;
    }

    protected String IMAGE_LOCATION;

    //==============================================================================================
    protected File pdfFile;

    protected Document document;

    protected PdfWriter writer;

    protected List<Boolean> selectedFieldList;

    //==============================================================================================

    protected PersonalInformation personalInfo;

    protected Objective objective;

    protected List<Education> educationList;

    protected Skills skills;

    protected List<Experience> experienceList;

    protected Hobbies hobbies;

    protected Languages languages;

    protected Achievements achievements;

    protected List<Project> projectList;

    protected List<Reference> referenceList;

    //==============================================================================================

    protected Context context;

    //==============================================================================================

    protected Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
    protected Font headingFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
    protected Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

    protected Font defaultHeadingFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);

    //==============================================================================================

    protected BaseColor selectedColor;

    protected BaseColor defaultHeadingColor = BaseColor.BLUE;

    //==============================================================================================

    protected Image profileImage;
    @Inject
    SharedPreferences sharedPreferences;

    public GeneratorPDF(@NonNull Context context, @NonNull List<Boolean> selectedFieldList, BaseColor selectedColor)
            throws IOException, DocumentException {
        this.context = context;
        this.selectedFieldList = selectedFieldList;
        this.selectedColor = selectedColor;
        createRootFolder();
        createFile();
        createDocument();
        openDocument();
        getSavedInfo();
        setUP();
        write();
        closeUp();
        closeDocument();
    }

    protected Bitmap getCroppedBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffff0000;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) 4);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    protected final boolean createRootFolder() {
        File rootFolder = new File(FOLDER_LOCATION(context));
        return rootFolder.exists() || rootFolder.mkdirs();
    }

    protected final boolean createFile() throws IOException {
        pdfFile = new File(
                FOLDER_LOCATION(context) + SEPARATOR + fileName + EXTENSION
        );
        return pdfFile.exists() || pdfFile.createNewFile();
    }

    protected void createDocument() {
        document = new Document(PageSize.A4, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);
    }

    protected final void openDocument() throws FileNotFoundException, DocumentException {
        writer = PdfWriter.getInstance(document,
                new FileOutputStream(pdfFile.getAbsoluteFile()));
        setPageEvent();
        document.open();
    }

    protected void setPageEvent() {
        Borders event = new Borders();
        writer.setPageEvent(event);
    }

    protected final void closeDocument() throws DocumentException, IOException {
        document.add(new Chunk(""));
        document.close();
    }

    public final File getCVFile() {
        return pdfFile;
    }

    protected final void getSavedInfo() {
        Realm realm = Realm.getDefaultInstance();
        personalInfo = realm.where(PersonalInformation.class).findFirst();
        objective = realm.where(Objective.class).findFirst();
        educationList = realm.where(Education.class).findAll();
        skills = realm.where(Skills.class).findFirst();
        experienceList = realm.where(Experience.class).findAll();
        hobbies = realm.where(Hobbies.class).findFirst();
        languages = realm.where(Languages.class).findFirst();
        achievements = realm.where(Achievements.class).findFirst();
        projectList = realm.where(Project.class).findAll();
        referenceList = realm.where(Reference.class).findAll();
    }

    protected void write() throws DocumentException {
        if (selectedFieldList.get(0)) {
            addPersonalInfo();
        }
        if (selectedFieldList.get(1)) {
            addObjective();
        }
        if (selectedFieldList.get(2)) {
            addEducation();
        }
        if (selectedFieldList.get(3)) {
            addSkills();
        }
        if (selectedFieldList.get(4)) {
            addExperience();
        }
        if (selectedFieldList.get(5)) {
            addHobbies();
        }
        if (selectedFieldList.get(6)) {
            addLanguages();
        }
        if (selectedFieldList.get(7)) {
            addAchievements();
        }
        if (selectedFieldList.get(8)) {
            addProjects();
        }
        if (selectedFieldList.get(9)) {
            addReferences();
        }
    }

    protected void newLine() throws DocumentException {
        document.add(new Paragraph(Chunk.NEWLINE));
    }

    public void headingCell(PdfPCell cell, Chunk heading) {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
        heading.setFont(titleFont);
        cell.addElement(heading);
        cell.setPadding(15);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(selectedColor);
    }

    public void personalInfoRow(Paragraph paragraph, String heading, String content) {

        Chunk chunkHeading = new Chunk(heading);
        chunkHeading.setFont(headingFont);

        Chunk chunkContent = new Chunk(content);
        chunkContent.setFont(contentFont);

        //==========================================================================================

        paragraph.add(chunkHeading);
        paragraph.add(HEADING_SEPARATOR);
        paragraph.add(chunkContent);
    }

    protected abstract void setUP() throws DocumentException;

    protected abstract void addPersonalInfo() throws DocumentException;

    private Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    protected void addImage() throws DocumentException {

        ((CVMaker) context.getApplicationContext()).getAppComponent().inject(this);


        IMAGE_LOCATION = sharedPreferences.getString(KEY_FILE_LOCATION, null);

        if (IMAGE_LOCATION != null) {
            try {

                File file = new File(IMAGE_LOCATION);
                Uri uri = Uri.parse(file.toString());

                InputStream ims = context.getContentResolver().openInputStream(uri);

//                Bitmap bmp =transform( BitmapFactory.decodeStream(ims));//Either this
                Bitmap bmp = BitmapFactory.decodeStream(ims);// Or that
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                profileImage = Image.getInstance(stream.toByteArray());
            } catch (IOException e) {
                Logger.i(TAG, e.toString(), e);
            }
        }
    }

    protected PdfPCell getIconCell(String fileName) throws BadElementException {
        return getIconCell(fileName, 40, 40);
    }

    protected PdfPCell getIconCell(String fileName, int width, int height) throws BadElementException {
        // load image
        PdfPCell cell = null;
        try {
            // get input stream
            InputStream ims = context.getAssets().open(fileName);
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            cell = new PdfPCell(image, false);
            //image.scaleToFit(width, height);

//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setVerticalAlignment(Element.ALIGN_CENTER);
//            image.setScaleToFitHeight(true);
//            image.setWidthPercentage(100);
            cell.addElement(image);
        } catch (IOException ex) {
            Logger.i(TAG, "Image not added");
        }
        return cell;
    }

    protected Image getIcon(String fileName, int width, int height) throws BadElementException {
        // load image
        Image image = null;
        try {
            // get input stream
            InputStream ims = context.getAssets().open(fileName);
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = Image.getInstance(stream.toByteArray());
            image.scaleToFit(width, height);
        } catch (IOException ex) {
            Logger.i(TAG, "Image not added");
        }
        return image;
    }

    protected abstract void addObjective() throws DocumentException;

    protected abstract void addEducation() throws DocumentException;

    protected abstract void addSkills() throws DocumentException;

    protected abstract void addExperience() throws DocumentException;

    protected abstract void addHobbies() throws DocumentException;

    protected abstract void addLanguages() throws DocumentException;

    protected abstract void addAchievements() throws DocumentException;

    protected abstract void addProjects() throws DocumentException;

    protected abstract void addReferences() throws DocumentException;

    protected abstract void closeUp() throws DocumentException;

    class Borders extends PdfPageEventHelper {
        private int firstPadding = 2;
        private int secondPadding = 4;

        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = new Rectangle(
                    firstPadding, firstPadding,
                    document.getPageSize().getWidth() - firstPadding,
                    document.getPageSize().getHeight() - firstPadding
            );
            rect.enableBorderSide(Rectangle.LEFT);
            rect.enableBorderSide(Rectangle.RIGHT);
            rect.enableBorderSide(Rectangle.BOTTOM);
            rect.enableBorderSide(Rectangle.TOP);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(1);
            rect.setBorderColor(selectedColor);
            try {
                document.add(rect);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            rect = new Rectangle(
                    secondPadding, secondPadding,
                    document.getPageSize().getWidth() - secondPadding,
                    document.getPageSize().getHeight() - secondPadding
            );
            rect.enableBorderSide(Rectangle.LEFT);
            rect.enableBorderSide(Rectangle.RIGHT);
            rect.enableBorderSide(Rectangle.BOTTOM);
            rect.enableBorderSide(Rectangle.TOP);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(1);
            rect.setBorderColor(selectedColor);
            try {
                document.add(rect);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

}
