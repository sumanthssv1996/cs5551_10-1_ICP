import { Component } from '@angular/core';
import { NavController, App, ToastController } from 'ionic-angular';
import { AngularFireAuth } from 'angularfire2/auth';
import { LoginPage } from '../login/login';
import { Camera, CameraOptions } from '@ionic-native/camera';
import { AngularFireDatabase } from 'angularfire2/database';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  public base64Image: string = "";
  public dataAr: any[] = []

  constructor(public navCtrl: NavController,
    private fire: AngularFireAuth,
    public fireDb: AngularFireDatabase,
    private toastCtrl: ToastController,
    private camera: Camera,
    private app: App) {
    this.fire.authState.subscribe(auth => {
      if (auth.uid !== null) {
        this.fireDb.list(`images/${auth.uid}`).valueChanges().subscribe(data => {
          let keys = Object.keys(data);
          this.dataAr = [];
          for (let i = 0; i < keys.length; i++) {
            this.dataAr.push(data[keys[i]]);
          }
        });
      }
    })
  }

  ionViewDidLoad() {
    this.fire.authState.subscribe(data => {
      //console.log(data);
    });
  }

  logout() {
   this.navCtrl.setRoot(LoginPage);
  }

  picture() {
    const options: CameraOptions = {
      quality: 50,
      destinationType: this.camera.DestinationType.DATA_URL,
      encodingType: this.camera.EncodingType.JPEG,
      mediaType: this.camera.MediaType.PICTURE,
      targetWidth: 1000,
      targetHeight: 1000
    }

    this.camera.getPicture(options).then((imageData) => {
      this.base64Image = 'data:image/jpeg;base64,' + imageData;
      let push=true;
      this.fire.authState.subscribe(auth => {
        if (auth.uid !== null && push === true) {
        this.fireDb.list(`images/${auth.uid}`).push(this.base64Image)
          .then(() => {
            this.presentToast("Image Saved.");
            push=false;
          });
        }
      })
     
    }, (err) => {
      // Handle error
    });

  }

 a
  presentToast(msg) {
    let toast = this.toastCtrl.create({
      message: msg,
      duration: 3000,
      position: 'top'
    });

    toast.onDidDismiss(() => {
      console.log('Dismissed toast');
    });

    toast.present();
  }

}
