import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service'; //servis za login
import { FormGroup, FormControl, Validators } from '@angular/forms'; //da pokupimo podatke sa forme

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
loginForm = new FormGroup({
  email: new FormControl('', Validators.compose([
    Validators.required,
    Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
  ])),
  password: new FormControl('', Validators.compose([
    Validators.required,
    Validators.minLength(8)
  ]))
});
  userData;
  url = "http://localhost:8080/users/login";
  idLogedUser;
  dataFromUser;
  errorMessageEmail="";
  errorMessagePass="";

  constructor(private loginService: LoginService) {}

  ngOnInit() {

  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
  
  onSubmit(){
    //email errors
    if(this.email.errors!=null && this.email.errors.required) this.errorMessageEmail="Email is requierd!";
    else if(this.email.errors!=null && this.email.errors.pattern) this.errorMessageEmail="Email is not valid!";
    else this.errorMessageEmail="";
    //password errors
    if(this.password.errors!=null && this.password.errors.required) this.errorMessagePass="Password is requierd!";
    else if(!this.password.valid) this.errorMessagePass="Password must be minimum 8 character!";
    else this.errorMessagePass="";
    //valid both
    if(this.email.valid && this.password.valid){
      this.dataFromUser={
        email: this.loginForm.value.email,
        password: this.loginForm.value.password
      };
      this.loginService.getUserId(this.url, this.dataFromUser).subscribe(idUser=>{
        this.idLogedUser=idUser;
      });
    }
  }

}
