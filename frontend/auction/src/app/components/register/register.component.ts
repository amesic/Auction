import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms"; //da pokupimo podatke sa forme
import { User } from "../../models/User";
import { RegisterService } from "../../services/register.service";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.css"]
})
export class RegisterComponent implements OnInit {
  registerForm = new FormGroup({
    firstName: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.pattern("^[A-Z][a-z]+$")
      ])
    ),
    lastName: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.pattern("^[A-Z][a-z]+$")
      ])
    ),
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
  errorMessageFirst = "";
  errorMessageLast = "";
  errorMessageEmail = "";
  errorMessagePass = "";
  user: User = new User();
  message = "";
  color;

  constructor(private registerService: RegisterService) {}

  ngOnInit() {}
  get firstName() {
    return this.registerForm.get("firstName");
  }
  get lastName() {
    return this.registerForm.get("lastName");
  }
  get email() {
    return this.registerForm.get("email");
  }
  get password() {
    return this.registerForm.get("password");
  }

  onSubmit() {
    //first name errors
    if (this.firstName.errors != null && this.firstName.errors.required) {
      this.errorMessageFirst = "First Name is required!";
    } else if (this.firstName.errors != null && this.firstName.errors.pattern) {
      this.errorMessageFirst = "First Name is not valid!";
    } else {
      this.errorMessageFirst = "";
    }
    //last name errors
    if (this.lastName.errors != null && this.lastName.errors.required) {
      this.errorMessageLast = "Last Name is required!";
    } else if (this.lastName.errors != null && this.lastName.errors.pattern) {
      this.errorMessageLast = "Last Name is not valid!";
    } else {
      this.errorMessageLast = "";
    }
    //email errors
    if (this.email.errors != null && this.email.errors.required) {
      this.errorMessageEmail = "Email is required!";
    } else if (this.email.errors != null && this.email.errors.pattern) {
      this.errorMessageEmail = "Email is not valid!";
    } else {
      this.errorMessageEmail = "";
    }
    //password errors
    if (this.password.errors != null && this.password.errors.required) {
      this.errorMessagePass = "Password is required!";
    } else if (!this.password.valid) {
      this.errorMessagePass = "Password must be minimum 8 character!";
    } else {
      this.errorMessagePass = "";
    }
    //valid both
    if (
      this.email.valid &&
      this.password.valid &&
      this.firstName.valid &&
      this.lastName.valid
    ) {
      this.user.userName =
        this.registerForm.value.firstName +
        " " +
        this.registerForm.value.lastName;
      this.user.email = this.registerForm.value.email;
      this.user.password = this.registerForm.value.password;

      this.registerService.saveUserData(this.user).subscribe(
        message => {
          this.color = "green";
          this.message = message;
          this.registerForm.reset();
          window.scrollTo(0, 0);
        },
        err => {
          this.color = "red";
          this.message = err.error;
          window.scrollTo(0, 0);
        }
      );
    }
  }
}
