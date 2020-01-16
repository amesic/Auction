import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { UserService } from "src/app/services/user.service";
import { LoginService } from "src/app/services/login.service";

@Component({
  selector: "app-required-user-info",
  templateUrl: "./required-user-info.component.html",
  styleUrls: ["./required-user-info.component.css"]
})
export class RequiredUserInfoComponent implements OnInit, OnChanges {
  @Input() userInfo;
  @Input() day;
  @Input() month;
  @Input() year;
  @Input() gender;
  @Input() image;

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  monthCopy;
  yearCopy;
  dayCopy;
  genderCopy;

  incorrectUsernameInput = false;
  incorrectPhonenumberInput = false;
  incorrectEmailInput = false;
  incorrectYearBirth = false;
  incorrectMonthBirth = false;
  incorrectDayBirth = false;
  incorrectGender = false;

  enableSaveCardButton = false;
  changeUsernameInput = false;
  changePhonenumberInput = false;
  changeEmailInput = false;
  changeGender = false;
  changeYear = false;
  changeMonth = false;
  changeDay = false;

  loading = false;

  messageUsernameInput;
  messageGender;
  messageBirthDate;
  messagePhonenumberInput;
  messageEmailInput;
  messagePhoto;

  fileData: File;
  previewUrl: any;

  userRequiredInfoForm = new FormGroup({
    username: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    phonenumber: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    email: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ])
  });

  constructor(
    private userService: UserService,
    private loginService: LoginService
  ) {}

  ngOnInit() {
    this.userRequiredInfoForm.controls["username"].valueChanges.subscribe(
      value => {
        if (value == "") {
          this.incorrectUsernameInput = true;
          this.messageUsernameInput = "Enter your name!";
          this.changeUsernameInput = false;
        } else {
          this.incorrectUsernameInput = false;
          this.messageUsernameInput = "";
        }
        if (
          (this.userInfo != undefined &&
          value != null &&
          value != this.userInfo.userName)  ||
          (this.userInfo == null && value != null)
        ) {
          this.changeUsernameInput = true;
        }
      }
    );
    this.userRequiredInfoForm.controls["phonenumber"].valueChanges.subscribe(
      value => {
        if (value == "") {
          this.incorrectPhonenumberInput = true;
          this.messagePhonenumberInput = "Enter your phone number!";
          this.changePhonenumberInput = false;
        } else {
          this.incorrectPhonenumberInput = false;
          this.messagePhonenumberInput = "";
        }
        if (
          (this.userInfo != undefined &&
          value != null &&
          value != this.userInfo.phoneNumber) ||
          (this.userInfo == null && value != null)
        ) {
          this.changePhonenumberInput = true;
        }
      }
    );
    this.userRequiredInfoForm.controls["email"].valueChanges.subscribe(
      value => {
        if (value == "") {
          this.incorrectEmailInput = true;
          this.messageEmailInput = "Enter your email!";
          this.changeEmailInput = false;
        } else {
          this.incorrectEmailInput = false;
          this.messageEmailInput = "";
        }
        if (
          (this.userInfo != undefined &&
          value != null &&
          value != this.userInfo.email) ||
          (this.userInfo == null && value != null)
        ) {
          this.changeEmailInput = true;
        }
      }
    );
  }
  ngOnChanges() {
    this.monthCopy = this.month;
    this.yearCopy = this.year;
    this.dayCopy = this.day;
    this.genderCopy = this.gender;
  }

  pickAGender(gender) {
    if (this.gender != null && this.gender != gender) {
      this.changeGender = true;
    }
    this.gender = gender;
    if (this.gender != "Your Gender") {
      this.incorrectGender = false;
      this.messageGender = "";
    }
  }
  pickAMonthOfBirth(month) {
    if (this.month != null && this.month != month) {
      this.changeMonth = true;
    }
    this.month = month;
    if (this.month != "Month") {
      this.incorrectMonthBirth = false;
      if (this.day != "Day" && this.year != "Year") {
        this.messageBirthDate = "";
      }
    }
  }
  pickADayOfBirth(day) {
    if (this.day != null && this.day != day) {
      this.changeDay = true;
    }
    this.day = day;
    if (this.day != "Day") {
      this.incorrectDayBirth = false;
      if (this.month != "Month" && this.year != "Year") {
        this.messageBirthDate = "";
      }
    }
  }
  pickAYearOfBirth(year) {
    if (this.year != null && this.year != year) {
      this.changeYear = true;
    }
    this.year = year;
    if (this.year != "Year") {
      this.incorrectYearBirth = false;
      if (this.day != "Day" && this.month != "Month") {
        this.messageBirthDate = "";
      }
    }
  }

  get username() {
    return this.userRequiredInfoForm.get("username");
  }
  get email() {
    return this.userRequiredInfoForm.get("email");
  }
  get phonenumber() {
    return this.userRequiredInfoForm.get("phonenumber");
  }
  saveInformations(requiredHtmlElement) {
    // card info
    if (this.username.errors != null) {
      this.incorrectUsernameInput = true;
      this.messageUsernameInput = "Enter your name!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectUsernameInput = false;
      this.messageUsernameInput = "";
    }
    if (this.email.errors != null) {
      this.incorrectEmailInput = true;
      this.messageEmailInput = "Enter your email!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectEmailInput = false;
      this.messageEmailInput = "";
    }
    if (this.phonenumber.errors != null) {
      this.incorrectPhonenumberInput = true;
      this.messagePhonenumberInput = "Enter your phone number!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectEmailInput = false;
      this.messageEmailInput = "";
    }
    if (this.month == "Month") {
      this.incorrectMonthBirth = true;
      this.messageBirthDate = "Choose birth date!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectMonthBirth = false;
      this.messageBirthDate = "";
    }
    if (this.day == "Day") {
      this.incorrectDayBirth = true;
      this.messageBirthDate = "Choose birth date!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectDayBirth = false;
      if (this.month != "Month" && this.year != "Year") {
        this.messageBirthDate = "";
      }
    }
    if (this.year == "Year") {
      this.incorrectYearBirth = true;
      this.messageBirthDate = "Choose birth date!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectYearBirth = false;
      if (this.day != "Day" && this.month != "Month") {
        this.messageBirthDate = "";
      }
    }
    if (this.gender == "Your Gender") {
      this.incorrectGender = true;
      this.messageGender = "Choose your gender";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectGender = false;
      this.messageGender = "";
    }
    if (this.userInfo.image == null) {
      this.messagePhoto = "Choose profile photo!";
      requiredHtmlElement.scrollIntoView();
    } else {
      this.messagePhoto = "";
    }
    if (
      this.username.errors == null &&
      this.phonenumber.errors == null &&
      this.email.errors == null &&
      this.year != "Year" &&
      this.month != "Month" &&
      this.day != "Day" &&
      this.userInfo.image != null
    ) {
      this.loading = true;
      let dateString = this.month + " " + this.day + " " + this.year;
      let date = new Date(dateString);
      this.userService
        .saveRequiredInfoFromUser(
          this.userRequiredInfoForm.get("username").value,
          this.userInfo.image,
          this.gender,
          date,
          this.userRequiredInfoForm.get("phonenumber").value,
          this.userRequiredInfoForm.get("email").value,
          this.loginService.getUserEmail()
        )
        .subscribe(
          savedInfo => {
            this.loading = false;
            this.userInfo = savedInfo;
            if (this.userInfo.token != null) {
              this.loginService.setSession(
                this.userInfo.token,
                this.userInfo.userName,
                this.userInfo.email
              );
            }
            this.incorrectEmailInput = false;
            this.incorrectPhonenumberInput = false;
            this.incorrectUsernameInput = false;
            this.enableSaveCardButton = false;
            this.userRequiredInfoForm
              .get("username")
              .disable({ onlySelf: true });
            this.userRequiredInfoForm
              .get("phonenumber")
              .disable({ onlySelf: true });
            this.userRequiredInfoForm.get("email").disable({ onlySelf: true });
            this.changeUsernameInput = false;
            this.changePhonenumberInput = false;
            this.changeEmailInput = false;
            this.changeGender = false;
            this.changeYear = false;
            this.changeMonth = false;
            this.changeDay = false;
          },
          err => {
            console.log(err.error);
            this.loading = false;
            if (
              err.error == "Email is invalid!" ||
              err.error == "Profile with this email already exist!"
            ) {
              this.incorrectEmailInput = true;
              this.messageEmailInput = err.error;
            } else if (err.error == "Phone number is invalid!") {
              this.incorrectPhonenumberInput = true;
              this.messagePhonenumberInput = err.error;
            }
          }
        );
    }
  }

  editCard() {
    this.enableSaveCardButton = true;
    this.userRequiredInfoForm.get("username").enable({ onlySelf: true });
    this.userRequiredInfoForm.get("phonenumber").enable({ onlySelf: true });
    this.userRequiredInfoForm.get("email").enable({ onlySelf: true });

    if (this.userInfo != null) {
      this.userRequiredInfoForm
        .get("username")
        .setValue(this.userInfo.userName);
      this.userRequiredInfoForm
        .get("phonenumber")
        .setValue(this.userInfo.phoneNumber);
      this.userRequiredInfoForm.get("email").setValue(this.userInfo.email);
    }
  }
  cancel() {
    this.enableSaveCardButton = false;
    this.userRequiredInfoForm.get("username").disable({ onlySelf: true });
    this.userRequiredInfoForm.get("email").disable({ onlySelf: true });
    this.userRequiredInfoForm.get("phonenumber").disable({ onlySelf: true });
    this.userInfo.image = this.image;

    if (this.userInfo != null) {
      this.userRequiredInfoForm
        .get("username")
        .setValue(this.userInfo.userName);
      this.userRequiredInfoForm
        .get("phonenumber")
        .setValue(this.userInfo.phoneNumber);
      this.userRequiredInfoForm.get("email").setValue(this.userInfo.email);
      this.month = this.monthCopy;
      this.year = this.yearCopy;
      this.day = this.dayCopy;
    } else {
      this.userRequiredInfoForm.get("username").setValue(null);
      this.userRequiredInfoForm.get("phonenumber").setValue(null);
      this.userRequiredInfoForm.get("email").setValue(null);
    }

    this.incorrectUsernameInput = false;
    this.incorrectPhonenumberInput = false;
    this.incorrectEmailInput = false;
    this.incorrectYearBirth = false;
    this.incorrectMonthBirth = false;
    this.incorrectDayBirth = false;
    this.incorrectGender = false;

    this.changeUsernameInput = false;
    this.changePhonenumberInput = false;
    this.changeEmailInput = false;
    this.changeGender = false;
    this.changeYear = false;
    this.changeMonth = false;
    this.changeDay = false;

    this.messageUsernameInput = "";
    this.messageGender = "";
    this.messageBirthDate = "";
    this.messagePhonenumberInput = "";
    this.messageEmailInput = "";

    this.loading = false;
  }

  onFileSelect(fileInput: any) {
    this.fileData = <File>fileInput.target.files[0];
    this.preview();
  }

  preview() {
    var mimeType = this.fileData.type;
    if (mimeType.match(/image\/*/) == null) {
      this.messagePhoto = "Image is invalid!";
    } else {
      this.messagePhoto = "";
      var reader = new FileReader();
      reader.readAsDataURL(this.fileData);
      reader.onload = _event => {
        this.previewUrl = reader.result;
        this.userInfo.image = this.previewUrl;
      };
    }
  }
}
