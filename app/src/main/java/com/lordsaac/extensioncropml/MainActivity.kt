package com.lordsaac.extensioncropml

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lordsaac.*
import com.lordsaac.extensioncrop_ml.Interfaces.ResponseExtenCropML
import com.lordsaac.extensioncrop_ml.Models.ExtensionCropML
import com.lordsaac.extensioncrop_ml.Models.OptionsML
import com.lordsaac.extensioncrop_ml.Models.Response
import com.lordsaac.extensioncrop_ml.Models.Result


class MainActivity : AppCompatActivity(), ResponseExtenCropML {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun onclick(view: View){

        val toggle: Switch = findViewById(R.id.switch1)

        ExtensionCropML.OPTIONS_DIALOG_TITLE = "My dialog"
        ExtensionCropML.OPTIONS_SELECTED_ALL = toggle.isChecked
        ExtensionCropML.OPTIONS_DIALOG_BUTTON_ACCEPT = "Please acept options"
        ExtensionCropML.OPTIONS_NEW_PICTURE = "New barcode capture"
        ExtensionCropML.OPTIONS_NEW_PICTURE_DESC = "Cut picture on barcode"
        
        ExtensionCropML.start(this,OptionsML.FORMAT_ALL_FORMATS)
    }

    private fun simpleAlert(desc: String){
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage(desc)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()

    }

    override fun responseListener(response: Response?) {

        if(response!!.result.equals(Result.OK)){
            if(response!!.barcodes.count() > 0 ){
                val textView: TextView = findViewById(R.id.test) as TextView
                var concat: String = ""

                response!!.barcodes.forEach {
                      if(it.selected){ // OPTIONAL
                        concat += "\n" + it.code
                     }else if(ExtensionCropML.OPTIONS_SELECTED_ALL){
                          concat += "\n" + it.code
                      }
                }

                textView.text = concat
            }else{
                this.simpleAlert("barcodes not found.")
            }
        }else if(response!!.result.equals(Result.BAD_SUCCESS)){
            this.simpleAlert("Image not process for any error")
        }else if(response!!.result.equals(Result.TIME_OUT)){
            this.simpleAlert("Process time out. ")
        }



    }

}
