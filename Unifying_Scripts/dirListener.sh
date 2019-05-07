#!/bin/bash

DIR="/home/pi/Arduino/ArduCAM_OV562-master/data/capture/"
#DIR="./Test_Folder"
#DIR1="~/home/pi/Desktop/uploadToFirebase/Test_Folder/"
EXT=".jpg"
COMP=15

export GOOGLE_APPLICATION_CREDENTIALS='HomeAutomationCreds.json'

inotifywait -m $DIR -e create -e moved_to |
    while read path action file; do 
    echo ${#file}
    if [ ${#file} -lt $COMP ];
    then
	    echo $file
	    file=${file:0:9}$EXT
	    echo $file
	    echo $action
	    echo $path
	    python3 UploadToFirebase.py "$file"
	    mv $DIR$file ~/Desktop/DEMO/images
	    
	    cd ../
	    result=$(./facialrec.sh $file)
	    cd uploadToFirebase
	    python3 FaceRegBooleanUpload.py $result
   	    echo "Done"
    fi

  done
