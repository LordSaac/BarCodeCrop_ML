package com.lordsaac.extensioncrop_ml.Models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lordsaac.extensioncrop_ml.ExtensionCropMLActivity;
import com.lordsaac.extensioncrop_ml.Helpers.BundleKeys;
import com.lordsaac.extensioncrop_ml.Interfaces.ResponseExtenCropML;

import java.io.Serializable;

public class ExtensionCropML {

    public static Boolean OPTIONS_SELECTED_ALL =  false;
    public static String  OPTIONS_DIALOG_TITLE = "Selecting";
    public static String  OPTIONS_DIALOG_BUTTON_ACCEPT = "Accept";
    public static String  OPTIONS_NEW_PICTURE = "New Picture";
    public static String  OPTIONS_NEW_PICTURE_DESC = "From your Camera";

    
    public static void start(Context context, int options){

        OptionsML optionsML = new OptionsML();

        optionsML.option = options;

        if(context != null){

            ExtensionCropMLActivity.anotherContext = (ResponseExtenCropML) context;

            Bundle bundle = new Bundle();
            Intent intent = new Intent(context, ExtensionCropMLActivity.class);

            bundle.putSerializable(BundleKeys.ExtensionMLKey, (Serializable) optionsML);

            intent.putExtra(BundleKeys.PutExtraKey, bundle);

            context.startActivity(intent);
        }
    }
}
