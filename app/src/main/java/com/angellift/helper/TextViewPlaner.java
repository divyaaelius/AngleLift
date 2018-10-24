package com.angellift.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Locale;

public class TextViewPlaner extends AppCompatTextView {
    private static final String sAttribute = "customFont";
    private static final String sScheme = "http://schemas.android.com/apk/res-auto";

    enum CustomFont {
        FONTAWSOME("fonts/fontawesome.ttf"),
        PLANER("fonts/cantarell.ttf"),
        MATERIAL("fonts/materialicons.ttf");

        private final String fileName;

        private CustomFont(String fileName) {
            this.fileName = fileName;
        }

        static CustomFont fromString(String fontName) {
            return valueOf(fontName.toUpperCase(Locale.US));
        }

        public Typeface asTypeface(Context context) {
            return Typeface.createFromAsset(context.getAssets(), this.fileName);
        }
    }

    public TextViewPlaner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            String fontName = attrs.getAttributeValue(sScheme, sAttribute);
            if (fontName == null) {
                throw new IllegalArgumentException("You must provide \"customFont\" for your text view");
            }
            setTypeface(CustomFont.fromString(fontName).asTypeface(context));
        }
    }
}
