import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ToastController } from 'ionic-angular';
import { AngularFireAuth } from 'angularfire2/auth';
import firebase from 'firebase';

import { HomePage } from '../home/home'

1@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {
  email: string = "";
  password: string = "";

  constructor(public navCtrl: NavController,
     public navParams: NavParams,
     private fire: AngularFireAuth,
     private toastCtrl: ToastController) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }

  

  login(){
    var email = this.email;
    var password = this.password;
    if (this.email !== "" && this.password !== "") {
      this.fire.auth.signInWithEmailAndPassword(email,password)
      .then(res => {
        this.navCtrl.setRoot(HomePage);
      })
      .catch(res => {
        console.log(res);
      });
    }else{
      this.presentToast("Please fill all the details and login.");
    }
  }

 

  register() {
    var email = this.email;
    var password = this.password;
    if (this.email !== "" && this.password !== "") {
      this.fire.auth.createUserWithEmailAndPassword(email,password)
      .then(res => {
        console.log(res);
        this.presentToast("Registered Successfully.");
      });
    }else{
      this.presentToast("Please fill all the details and login.");
    }
  }

  /*
    This method will show the Toast messages.
  */
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
