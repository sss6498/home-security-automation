This README file will show you how to clone this facial recognition repository and run it for yourself.

EXPLANATION/FILE ORGANIZATION:

There are four directories in this folder:

    dataset/ : Contains face images organized into subfolders by name.
    
    images/ : Contains test images that will be used to verify the operation of the model.
    
    face_detection_model/ : Contains a pre-trained Caffe deep learning model provided by OpenCV to detect faces. This model detects and localizes faces in an image.
    
    output/ : Contains output pickle files, a specific type of byte data that is used by the model. The output files include:
        
        output/embeddings.pickle : A serialized facial embeddings file. Embeddings have been computed for every face in the dataset and are stored in this file.
        
        output/le.pickle : The label encoder. Contains the name labels for the people that our model can recognize.

        output/recognizer.pickle : The Linear Support Vector Machine (SVM) model. This is a machine learning model rather than a deep learning model and it is responsible for actually recognizing faces.

There are also five other files in the folder: 

    extract_embeddings.py : This file is responsible for using a deep learning feature extractor to generate a 128-D vector describing a face. All faces in our dataset will be passed through the neural network to generate embeddings.

    openface_nn4.small2.v1.t7 : A Torch deep learning model which produces the 128-D facial embeddings.

    train_model.py : The Linear SVM model (in recognizer.pickle) will be trained by this script. It'll detect faces, extract embeddings, and fit our SVM model to the embeddings data.
    
    recognize.py : This file detects faces, extracts embeddings, and queries the SVM model to determine who is in an image. It'll also draw boxes around faces and annotate each box with a name.

    recognize_video.py : This file does the same as recognize.py, but instead it is run on frames of a video stream. 

EXECUTION/COMPILATION:

    You will need to have the imutils and scikit-learn packages installed, which can be downloaded with the following commands: 

        pip install --upgrade imutils

        pip install scikit-learn

    Since this requires the OpenCV environment to work on, launch the environment with the following command: 

        workon cv 

        IF YOU DO NOT HAVE OPEN CV, FOLLOW THIS BLOG THAT SHOWS HOW TO DO SO: 

            https://www.pyimagesearch.com/2018/08/17/install-opencv-4-on-macos/ 

        SHOULD WORK FOR ANY UNIX/LINUX SYSTEM...

    To extract the embeddings, input the following command: 

        python extract_embeddings.py --dataset dataset \
	        --embeddings output/embeddings.pickle \
	        --detector face_detection_model \
	        --embedding-model openface_nn4.small2.v1.t7

    To train the model with the embeddings, input the following command: 

        python train_model.py --embeddings output/embeddings.pickle \
	        --recognizer output/recognizer.pickle \
	        --le output/le.pickle

    To apply the OpenCV face recognition pipeline to the test images in the images folder, input the following command and with any photo's filename of your choosing: 

        python recognize.py --detector face_detection_model \
            --embedding-model openface_nn4.small2.v1.t7 \
            --recognizer output/recognizer.pickle \
            --le output/le.pickle \
            --image images/<INSERT_IMAGE_NAME_HERE.extension>

    *You will need to extract embeddings and train the model every time there is a change to the dataset folder. *

