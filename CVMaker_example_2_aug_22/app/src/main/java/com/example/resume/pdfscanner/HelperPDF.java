package com.example.resume.pdfscanner;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.CheckResult;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.example.resume.pdfscanner.patterns.GeneratorPDF;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.example.resume.pdfscanner.patterns.Pattern9;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class HelperPDF extends Thread {

    /**
     * Create CV from format 0
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT0 = 0;

    /**
     * Create CV from format 1
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT1 = 1;

    /**
     * Create CV from format 2
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT2 = 2;

    /**
     * Create CV from format 3
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT3 = 3;

    /**
     * Create CV from format 4
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT4 = 4;

    /**
     * Create CV from format 5
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT5 = 5;

    /**
     * Create CV from format 6
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT6 = 6;

    /**
     * Create CV from format 7
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT7 = 7;

    /**
     * Create CV from format 8
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT8 = 8;

    /**
     * Create CV from format 9
     * Use with {@link #getFormatting()}
     */
    public static final int FORMAT9 = 9;

    public static final int CVGenerated = 1;


    private Context context;
    private int formatNumber;
    private List<Boolean> selectedFieldList;
    private GeneratorPDF generatorPDF;
    private Handler handler;
    private BaseColor selectedColor;

    public HelperPDF(@NonNull Context context,
                     @CVFormats int formatNumber,
                     @NonNull List<Boolean> selectedFieldList,
                     @NonNull Handler handler) {
        this.context = context;
        this.formatNumber = formatNumber;
        this.selectedFieldList = selectedFieldList;
        this.handler = handler;
    }

    public void setColor(BaseColor selectedColor) {
        this.selectedColor = selectedColor;
    }

    @Override
    public void run() {
        super.run();
        try {
            formatResolver();
            File file = generatorPDF.getCVFile();
            handler.obtainMessage(CVGenerated, file).sendToTarget();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void formatResolver() throws IOException, DocumentException {
        switch (getFormatting()) {
//            case HelperPDF.FORMAT0:
//                generatorPDF = new Pattern0(context, selectedFieldList, selectedColor);
//                break;
//            case HelperPDF.FORMAT1:
//                generatorPDF = new Pattern1(context, selectedFieldList, selectedColor);
//                break;
//
            case HelperPDF.FORMAT9:
                generatorPDF = new Pattern9(context, selectedFieldList, selectedColor);
                break;
        }
    }


    @CheckResult
    @CVFormats
    private int getFormatting() {
        return this.formatNumber;
    }

    /**
     * @hide
     */
    @IntDef({
            FORMAT0, FORMAT1, FORMAT2,
            FORMAT3, FORMAT4, FORMAT5,
            FORMAT6, FORMAT7, FORMAT8,
            FORMAT9
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface CVFormats {
    }
}