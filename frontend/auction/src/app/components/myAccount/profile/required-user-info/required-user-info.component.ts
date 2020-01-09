import { Component, OnInit, Input, AfterViewInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-required-user-info",
  templateUrl: "./required-user-info.component.html",
  styleUrls: ["./required-user-info.component.css"]
})
export class RequiredUserInfoComponent implements OnInit {
  @Input() userInfo;
  @Input() day;
  @Input() month;
  @Input() year;
  @Input() gender;

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  incorrectUsernameInput = false;
  incorrectPhonenumberInput = false;
  incorrectEmailInput = false;
  incorrectYearBirth = false;
  incorrectMonthBirth = false;
  incorrectDayBirth = false;
  incorrectGender = false;

  userRequiredInfoForm = new FormGroup({
    username: new FormControl("", [Validators.required]),
    phonenumber: new FormControl("", [Validators.required]),
    email: new FormControl("", [Validators.required])
  });

  constructor() {}

  ngOnInit() {
  }

  pickAGender(gender) {
    this.gender = gender;
  }
  pickAMonthOfBirth(month) {
    this.month = month;
  }
  pickADayOfBirth(day) {
    this.day = day;
  }
  pickAYearOfBirth(year) {
    this.year = year;
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
}
 // required info
    /*if (this.username.errors != null) {
      this.incorrectUsernameInput = true;
      requiredHtmlElement.scrollIntoView();
      console.log(this.username);
    } else {
      this.incorrectUsernameInput = false;
    }
    if (this.email.errors != null) {
      this.incorrectEmailInput = true;
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectEmailInput = false;
    }
    if (this.phonenumber.errors != null) {
      this.incorrectPhonenumberInput = true;
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectPhonenumberInput = false;
    }
    if (this.pickedGender == "Your Gender") {
      this.incorrectGender = true;
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectGender = false;
    }
    if (this.pickedYearOfBirth == "Year") {
      this.incorrectYearBirth = true;
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectYearBirth = false;
    }
    if (this.pickedMonthOfBirth == "Month") {
      this.incorrectMonthBirth = true;
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectMonthBirth = false;
    }
    if (this.pickedDayOfBirth == "Day") {
      this.incorrectDayBirth = true;
      requiredHtmlElement.scrollIntoView();
    } else {
      this.incorrectDayBirth = false;
    }*/