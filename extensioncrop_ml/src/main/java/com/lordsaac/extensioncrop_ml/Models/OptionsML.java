package com.lordsaac.extensioncrop_ml.Models;

import android.content.Context;

import com.google.mlkit.vision.barcode.Barcode;

import java.io.Serializable;

public class OptionsML implements Serializable {

    public static final int FORMAT_UNKNOWN = -1;
    public static final int FORMAT_ALL_FORMATS = 0;
    public static final int FORMAT_CODE_128 = 1;
    public static final int FORMAT_CODE_39 = 2;
    public static final int FORMAT_CODE_93 = 4;
    public static final int FORMAT_CODABAR = 8;
    public static final int FORMAT_DATA_MATRIX = 16;
    public static final int FORMAT_EAN_13 = 32;
    public static final int FORMAT_EAN_8 = 64;
    public static final int FORMAT_ITF = 128;
    public static final int FORMAT_QR_CODE = 256;
    public static final int FORMAT_UPC_A = 512;
    public static final int FORMAT_UPC_E = 1024;
    public static final int FORMAT_PDF417 = 2048;
    public static final int FORMAT_AZTEC = 4096;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_CONTACT_INFO = 1;
    public static final int TYPE_EMAIL = 2;
    public static final int TYPE_ISBN = 3;
    public static final int TYPE_PHONE = 4;
    public static final int TYPE_PRODUCT = 5;
    public static final int TYPE_SMS = 6;
    public static final int TYPE_TEXT = 7;
    public static final int TYPE_URL = 8;
    public static final int TYPE_WIFI = 9;
    public static final int TYPE_GEO = 10;
    public static final int TYPE_CALENDAR_EVENT = 11;
    public static final int TYPE_DRIVER_LICENSE = 12;

    public int option = Barcode.FORMAT_ALL_FORMATS;
}
