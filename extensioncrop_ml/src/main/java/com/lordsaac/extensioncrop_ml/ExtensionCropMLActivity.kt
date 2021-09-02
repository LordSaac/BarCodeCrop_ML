package com.lordsaac.extensioncrop_ml

import android.Manifest
import android.content.ContentValues
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.lordsaac.extensioncrop_ml.Helpers.BundleKeys
import com.lordsaac.extensioncrop_ml.Interfaces.ListenerBarcodes
import com.lordsaac.extensioncrop_ml.Interfaces.ResponseExtenCropML
import com.lordsaac.extensioncrop_ml.ML_Helpers.BarcodeProcess
import com.lordsaac.extensioncrop_ml.Models.ExtensionCropML
import com.lordsaac.extensioncrop_ml.Models.OptionsML
import com.lordsaac.extensioncrop_ml.Models.Response
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ExtensionCropMLActivity : AppCompatActivity(), ListenerBarcodes {

    private lateinit var imageUri: Uri

    private val CAMERA_REQUEST_CODE = 100
    private val READ_EXTERNAL_STORAGE = 101
    private val WRITE_EXTERNAL_STORAGE = 102
    private val ACCESS_FINE_LOCATION = 103

    private lateinit var optionsML : OptionsML
    private lateinit var responseML : Response
    private var isClose = false

    companion object{

        lateinit var  anotherContext: ResponseExtenCropML;

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extension_crop_m_l)

        this.requestValidatePermission()
        this.getPutExtraIntent()

    }




    fun  getPutExtraIntent() {

        val extras = intent.getBundleExtra(BundleKeys.PutExtraKey);

        if (extras != null) {

            val obj =  extras.get(BundleKeys.ExtensionMLKey) as OptionsML

            optionsML = obj
        }
    }

    fun deleteUri(){
        val fdelete = File(getFilePath(imageUri))

        if (fdelete.exists()) {
            if (fdelete.delete()) {
                println("file Deleted :")
            } else {
                println("file not Deleted :")
            }
        }
    }



    //getting real path from uri
    private fun getFilePath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex: Int = cursor.getColumnIndex(projection[0])
            val picturePath: String = cursor.getString(columnIndex) // returns null
            cursor.close()
            return picturePath
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this.isClose = false

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {

                val resultUri: Uri = result.uri
                val barcodeProcess = BarcodeProcess(this, optionsML.option)
                val image = InputImage.fromFilePath(this, resultUri)

                barcodeProcess.proccessImage(image)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }else if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){


            CropImage.activity(imageUri)
                    .setOutputUri(this.imageUri)
                    .start(this);
        }else {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.count() > 0){
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                    this.checkPermissionAndOpenCamera(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                }

                else {
                    finish()
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
                }
            }
            else if (requestCode == READ_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()

                    if(this.checkPermissionAndOpenCamera(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)){
                        this.openCamera()
                    }else{
                        this.checkPermissionAndOpenCamera(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
                    }


                }

                else {
                    finish()
                    Toast.makeText(this, "Read External Storage permission denied", Toast.LENGTH_LONG).show()
                }
            }
            else if (requestCode == WRITE_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                    this.openCamera()

                }

                else {
                    finish()
                    Toast.makeText(this, "Write External Storage permission denied", Toast.LENGTH_LONG).show()
                }
            }

        }

    }


     /**
     * @Private Funtions
     */

    private fun openCamera(){

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, ExtensionCropML.OPTIONS_NEW_PICTURE)
        values.put(MediaStore.Images.Media.DESCRIPTION, ExtensionCropML.OPTIONS_NEW_PICTURE_DESC)
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

        val action = Intent("android.media.action.IMAGE_CAPTURE")

        action.putExtra("android.intent.extras.CAMERA_FACING", 1)

        action.putExtra("android.intent.extras.FLASH_MODE_ON", 1)
        action.putExtra("android.intent.extras.QUALITY_HIGH", 1)
        action.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        startActivityForResult(action, CAMERA_REQUEST_CODE)
    }

    private fun requestValidatePermission(){
        if(this.checkPermissionAndOpenCamera(Manifest.permission.CAMERA, CAMERA_REQUEST_CODE)){
            if(this.checkPermissionAndOpenCamera(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)){

                if(this.checkPermissionAndOpenCamera(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)){
                    this.openCamera()
                }

            }else{
                this.checkPermissionAndOpenCamera(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
            }
        }
    }



    private fun checkPermissionAndOpenCamera(request: String, code: Int) : Boolean {
        if (ContextCompat.checkSelfPermission(applicationContext, request) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(request), code)
            return  false;
        } else {
            return true;
        }
    }

    private fun openSelectCode(codes: Array<String>){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle(ExtensionCropML.OPTIONS_DIALOG_TITLE)
            .setMultiChoiceItems(codes, null,
                OnMultiChoiceClickListener { dialog, item, isChecked ->

                    this.responseML.barcodes[item].selected = isChecked

                    Log.i(
                        "Log",
                        "selected options: " + codes.get(item)
                    )
                })

        builder.setPositiveButton("" + ExtensionCropML.OPTIONS_DIALOG_BUTTON_ACCEPT,
            DialogInterface.OnClickListener { dialog, which ->
                    anotherContext.responseListener(responseML)
                    this.finish()
            })

        val dialog: AlertDialog = builder.create()

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun responseListener(response: Response) {

        this.responseML = response
        this.deleteUri()

        if(response.count > 0) {

            val values: ArrayList<String> = ArrayList()

            var index = 0

            for (item in response.barcodes){
                values.add(item.code)
                index++
            }

            if(!ExtensionCropML.OPTIONS_SELECTED_ALL){
                val stringArray: Array<String> = values.toArray(arrayOfNulls<String>(values.size))
                this.openSelectCode(stringArray)
            }else{
                anotherContext.responseListener(responseML)
                this.finish()
            }


        }else{
            // Bad Success or image not process
            anotherContext.responseListener(responseML)
            this.finish()
        }

    }


}
