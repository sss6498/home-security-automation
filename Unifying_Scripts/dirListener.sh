#!/bin/bash

DIR="Test_Folder"
DIR_Dest='/Users/akondila/Documents/Destination_Folder'


inotifywait -m DIR -e create -e moved_to |
    while read path action file; do 
    echo $file
    echo $action
    echo $path
#    mv $file $DIR_Dest
#    ls $DIR_Dest
    python3 UploadToFirebase.py $file
  done