# Edge_Computing_With_NeuralNetwork

Overview:

This is an Android app which performs image recognition by running ImageNet Inception V3 model locally. It uses Image 
Recognition to continuosly classify the images given as input from the app's assets folder and the app classifies the frames
in real-time and gives top five most probable classifications. Inference is given by using the TensorFlow Lite Java API's.

These steps would help you to import and build the application and run the model on mobile. You can also take a look at the 
TensorFlow Lite Android Image Classification examples (https://www.tensorflow.org/lite/models/image_classification/android) 
for more explanation.

Model:

I have used Inception V3 from the Large Scale Visual Recognition Challenge. The app's assets folder has the tflite file for
Inception V3 along with the labels.txt which has the labels for target classes of the model. Upon starting the application,
the model files are loaded into the Model Java API's and can be used directly.


Build and run

Step 1. Clone the TensorFlow examples source code

Clone the TensorFlow examples GitHub repository to your computer to get the demo application.

git clone <Repository_URL>

Open the TensorFlow source code in Android Studio. To do this, open Android Studio and select Open an existing project, setting the folder to examples/lite/examples/image_classification/android

Step 2. Build the Android Studio project

Select Build -> Make Project and check that the project builds successfully. You will need Android SDK configured in the settings. You'll need at least SDK version 23. The build.gradle file will prompt you to download any missing libraries.

Step 3. Install and run the app

Connect the Android device to the computer and be sure to approve any ADB permission prompts that appear on your phone. Select Run -> Run app. Select the deployment target in the connected devices to the device on which the app will be installed. This will install the app on the device.

Note: Do not delete the files in assets folder. If that happen, you may have to reclone the assets folders from the project 
to your local.

Step 4. Running the Application:

When you start the app, you will see the following apps Homescreen and upon clicking the "Start Image Recognition" button, it will start
taking the images from the assets folder and give it as input to the model and will give the results.

 

 


 
