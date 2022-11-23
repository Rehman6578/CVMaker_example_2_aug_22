package com.example.resume.fragments.infocategories;

import android.content.Context;

import com.example.resume.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class InfoCategoryContent {

    private static final int[] categoryIconIDs = {
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4,
            R.drawable.cv_4
    };
    /**
     * An array of info category items.
     */
    private final List<InfoCategoryItem> infoCategoryItems;
    private Context context;

    InfoCategoryContent(Context context) {
        this.context = context;
        infoCategoryItems = getInfoCategories();
    }

    List<InfoCategoryItem> getInfoCategoryItems() {
        return infoCategoryItems;
    }

    private List<InfoCategoryItem> getInfoCategories() {
        List<InfoCategoryItem> ITEMS = new ArrayList<>();
        String[] info_category_array = context.getResources().getStringArray(R.array.info_category_array);
        for (int i = 0; i < info_category_array.length; i++) {
            InfoCategoryItem item = new InfoCategoryItem(
                    String.valueOf(i), info_category_array[i], categoryIconIDs[i]
            );
            ITEMS.add(item);
        }
        return ITEMS;
    }

    /**
     * A info category item representing a piece of content.
     */
    public static class InfoCategoryItem {
        final String id;
        final String content;
        final int categoryIconID;

        InfoCategoryItem(String id, String content, int categoryIconID) {
            this.id = id;
            this.content = content;
            this.categoryIconID = categoryIconID;
        }

        public String getId() {
            return id;
        }

        String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
