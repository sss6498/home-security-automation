import sys
from firebase.firebase import FirebaseApplication
import firebase as firebase 
#import firebase_admin
from google.cloud import storage
from firebase_admin import db
from firebase_admin import credentials
import firebase_admin
  

image_url = sys.argv[1]
databaseURL = "https://homesecurityautomation-a58f3.firebaseio.com/"
storageURL = "homesecurityautomation-a58f3.appspot.com"


#Initalizing the firebase connection 
firebaseApp = FirebaseApplication(databaseURL)
client = storage.Client();
bucket = client.get_bucket(storageURL)

#firebase database connection
cred = credentials.Certificate('HomeAutomationCreds.json')
config = {
    'apiKey': 'AIzaSyCOv0XFhxg1Sa8vttbk6wVBJc4RkxIOV2s',
    'authDomain': 'homesecurityautomation-a58f3.firebaseapp.com',
    'databaseURL': 'https://homesecurityautomation-a58f3.firebaseio.com/',
    'storageBucket': 'homesecurityautomation-a58f3.appspot.com'}
#firebase.initializeApp(config);


firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://homesecurityautomation-a58f3.firebaseio.com/'
})

#putting the image into firebase
blob = bucket.blob(image_url)
imageBlob = bucket.blob("Stored_Images/" + "UniverseImg")
imageBlob.upload_from_filename(image_url);
imgURL = imageBlob.public_url
print(imgURL)


#Store into firebase database
root = db.reference(); 
root.child('All_Image_Uploads_Database').push({"photoURL": imgURL})


"""
bucket = storage.bucket()

image_data = requests.get(image_url).content

blob.upload_from_string(
        image_data,
        content_type='image/jpg'
    )
print(blob.public_url)
"""


