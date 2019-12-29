import { Component, OnInit, AfterViewInit} from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { StripeService, Element as StripeElement, ElementsOptions, Elements } from 'ngx-stripe';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  userInfo;
  cardInfo;
  month = "";
  year = "";
  day = "";

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  pickedYear = "Year";
  pickedMonth = "Month";

  incorrectNameInput = false;
  incorrectNumberInput = false;
  incorrectCvcInput = false;
  incorrectYear = false;
  incorrectMonth = false;

  card: StripeElement;
  elements: Elements;
  elementsOptions: ElementsOptions = {
    locale: 'en'
  };

  cardForm = new FormGroup({
    name: new FormControl("", [Validators.required]),
    number: new FormControl("", [Validators.required]),
    cvccw: new FormControl("", [Validators.required])
  })

  constructor(
    private userService: UserService, 
    private loginService: LoginService,
    private stripeService: StripeService) { }

  ngOnInit() {
    this.userService.getUserInfo(this.loginService.getUserEmail()).subscribe(user => {
      this.userInfo = user;
      if (this.userInfo.birthDate != null) {
      let date = new Date(Date.parse(this.userInfo.birthDate));
      this.day =  date.toDateString().split(" ")[2];
      this.year =  date.toDateString().split(" ")[3];
      this.month = date.toDateString().split(" ")[1];
      }
    });
  }

  pickAYear(year) {
    this.pickedYear = year;
  }

  pickAMonth(month) {
    this.pickedMonth = month;
  }

  get name() {
    return this.cardForm.get("name");
  }
  get number() {
    return this.cardForm.get("number");
  }
  get cvccw() {
    return this.cardForm.get("cvccw");
  }

  saveInformations(cardHtmlElement) {
    if (this.name.errors != null) {
      this.incorrectNameInput = true;
     cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectNameInput = false;
    }
    if (this.number.errors != null) {
      this.incorrectNumberInput = true;
     cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectNumberInput = false;
    }
    if (this.cvccw.errors != null) {
      this.incorrectCvcInput = true;
     cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectCvcInput = false;
    }
    if (this.pickedYear == "Year") {
      this.incorrectYear = true;
      cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectYear = false;
    }
    if (this.pickedMonth == "Month") {
      this.incorrectMonth = true;
      cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectMonth = false;
    }
    if (this.name.errors == null &&
      this.number.errors == null &&
      this.cvccw.errors == null &&
      this.pickedYear != "Year" &&
      this.pickedMonth != "Month") {
        let number = this.cardForm.get("number").value;
        let cvccw = this.cardForm.get("cvccw").value;
        if (this.cardInfo != undefined) {
          if (this.cardForm.get("number").value == this.cardInfo.number) {
          number = "";
        }
        if (this.cardForm.get("cvccw").value == this.cardInfo.cvc) {
          cvccw = "";
        }
      }
    this.userService.saveCardInformation(
      number,
      this.pickedMonth,
      this.pickedYear,
      cvccw,
      this.cardForm.get("name").value,
      this.loginService.getUserEmail(),
      ).subscribe(cardInfo => {
        this.cardInfo = cardInfo;
        console.log(this.cardInfo);
      },
      err => {
        if (err.error == "incorrect_number" || 
        err.error == "invalid_number") {
          this.incorrectNumberInput = true;
         cardHtmlElement.scrollIntoView();
        } else {
          this.incorrectNumberInput = false;
        }
        if (err.error == "invalid_cvc") {
          this.incorrectCvcInput = true;
         cardHtmlElement.scrollIntoView();
        } else {
          this.incorrectCvcInput = false;
        }
        console.log(err);
      });
  }
}

}
