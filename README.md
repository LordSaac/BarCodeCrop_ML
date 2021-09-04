
# Description

Scan image captured image and crop image for select specificate code or multiple codes.

# Implementations 

1.Add implementation in your class: 
```kotlin 
ResponseExtenCropML

```
<br>

2.Add function  ```responseListener```, this function you can using for listener response. 

#### Example: 

```kotlin

    override fun responseListener(response: Response?) {
            
	    if(response!!.result.equals(Result.OK)){
	       
	       var concat: String = ""
	       
	        response!!.barcodes.forEach {
                    if(it.selected){
                        concat += "\n" + it.code
                    }
                }
		
		print(concat)
		
	    }else if(response!!.result.equals(Result.BAD_SUCCESS)){
	    
               print("Image not process for any error")
	    
            }else if(response!!.result.equals(Result.TIME_OUT)){
	    
	       print("Process time out. ")
	       
          }
    }

```

<br>
3.For start the action calling the next code:

```kotlin 
 
 fun onclick(view: View){
      
      ExtensionCropML.start(this,OptionsML.FORMAT_ALL_FORMATS)
  
  }

```

### * For more information you can see  complete code into the next link [Code](https://github.com/LordSaac/ExtensionCrop_ML/blob/master/app/src/main/java/com/lordsaac/extensioncropml/MainActivity.kt) 


## Table Options

| Config | Type | Description |
| --- | --- | --- |
| OPTIONS_SELECTED_ALL | Boolean | Return at once the scan options |
| OPTIONS_DIALOG_TITLE | String | Set your title dialog. |
| OPTIONS_DIALOG_BUTTON_ACCEPT | String | Set title button dialog. |
| OPTIONS_NEW_PICTURE | String | Change crop title |
| OPTIONS_NEW_PICTURE_DESC |  String | Change description crop for add rules or any text.|

#### Example: 

```kotlin
 fun onclick(view: View){

        ExtensionCropML.OPTIONS_DIALOG_TITLE = "My dialog"
        ExtensionCropML.OPTIONS_SELECTED_ALL = true
        ExtensionCropML.OPTIONS_DIALOG_BUTTON_ACCEPT = "Please acept options"
        ExtensionCropML.OPTIONS_NEW_PICTURE = "New barcode capture"
        ExtensionCropML.OPTIONS_NEW_PICTURE_DESC = "Cut picture on barcode"

        ExtensionCropML.start(this,OptionsML.FORMAT_ALL_FORMATS)
    }

```

# Gradle Dependency

* Add the JitPack repository to your project's build.gradle file

```

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

* Add the dependency in your app's build.gradle file

```
    
    dependencies {
	        implementation 'com.github.LordSaac:ProgressLottieIGB:v1.0.2'
	}
    
```

# Contribution

You are most welcome to contribute to this project!

*  Buy me a  [Coffee](https://paypal.me/LordSaac?locale.x=es_XC)  &nbsp; :coffee:

*  Give me a [Star](https://github.com/LordSaac/ExtensionCrop_ML) &nbsp; :star:

<br>
<h2>Released August 29, 2021</h2>

## Licence

Copyright 2021 Isaac G. Banda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

