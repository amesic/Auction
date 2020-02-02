import { Component, OnInit } from "@angular/core";
import { LoginService } from "../../services/login.service"; //servis za login
import { FormGroup, FormControl, Validators } from "@angular/forms"; //da pokupimo podatke sa forme
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  loginForm = new FormGroup({
    email: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.pattern("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")
      ])
    ),
    password: new FormControl(
      "",
      Validators.compose([Validators.required, Validators.minLength(8)])
    )
  });

  userToken;
  userName;
  errorMessageEmail = "";
  errorMessagePass = "";
  color;
  message;

  constructor(private loginService: LoginService, private router: Router, private activatedRoute: ActivatedRoute) {}
  previousUrl: string;

  ngOnInit() {
  }

  get email() {
    return this.loginForm.get("email");
  }
  get password() {
    return this.loginForm.get("password");
  }

  onSubmit() {
    //email errors
    if (this.email.errors != null && this.email.errors.required) {
      this.errorMessageEmail = "Email is requierd!";
    } else if (this.email.errors != null && this.email.errors.pattern) {
      this.errorMessageEmail = "Email is not valid!";
    } else {
      this.errorMessageEmail = "";
    }
    //password errors
    if (this.password.errors != null && this.password.errors.required) {
      this.errorMessagePass = "Password is requierd!";
    } else if (!this.password.valid) {
      this.errorMessagePass = "Password must be minimum 8 character!";
    } else {
      this.errorMessagePass = "";
    }
    //valid both
    if (this.email.valid && this.password.valid) {
      this.loginService
        .authenticate(
          this.loginForm.value.email,
          this.loginForm.value.password
        )
        .subscribe(
          userData => {
            this.userName = userData.username;
            this.userToken = userData.token;
            this.router.navigate(["/home"]);
          },
          err => {
            this.color = "red";
            this.message =
              "Oops, that's not a match. Check your email/password!";
          }
        );
    }
  }
}
