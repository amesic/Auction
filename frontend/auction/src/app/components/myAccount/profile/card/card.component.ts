import { Component, OnInit, Input, NgZone } from '@angular/core';
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  @Input() cardInfo;
  @Input() yearExp;
  @Input() monthExp;

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  incorrectNameInput = false;
  incorrectNumberInput = false;
  incorrectCvcInput = false;
  incorrectYear = false;
  incorrectMonth = false;

  messageNameInput;
  messageNumberInput;
  messageCvcInput;
  messageExpDate;

  cardForm = new FormGroup({
    name: new FormControl({value: null, disabled: true}, [Validators.required]),
    number: new FormControl({value: null, disabled: true}, [Validators.required]),
    cvccw: new FormControl({value: null, disabled: true}, [Validators.required])
  });

  enableSaveCardButton = false;
  loading = false;
  changeNameInput = false;
  changeNumberInput = false;
  changeCvcInput = false;
  changeMonth = false;
  changeYear = false;

  constructor(
    private userService: UserService, 
    private loginService: LoginService,
    private _zone: NgZone) { }

  ngOnInit() {
    this.cardForm.controls['name'].valueChanges.subscribe(value => {
      if (value == "") {
      this.incorrectNameInput = true;
      this.messageNameInput = "Enter your name!";
      this.changeNameInput = false;
      } else {
      this.incorrectNameInput = false;
      this.messageNameInput = "";
      }
      if (this.cardInfo != undefined && value != null && value != this.cardInfo.name) {
        this.changeNameInput = true;
      }
    });
    this.cardForm.controls['number'].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectNumberInput = true;
        this.messageNumberInput = "Enter card number!";
        this.changeNumberInput = false;
      } else {
        this.incorrectNumberInput = false;
        this.messageNumberInput = "";
      }
      if (this.cardInfo != undefined && value != null && value != "************" + this.cardInfo.number) {
        this.changeNumberInput = true;
      }
    });
    this.cardForm.controls['cvccw'].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectCvcInput = true;
        this.messageCvcInput = "Enter cvc/cw!";
        this.changeCvcInput = false;
      } else {
        this.incorrectCvcInput = false;
        this.messageCvcInput = "";
      }
      if (value != null) {
        this.changeCvcInput = true;
      }
    });
  }

  pickAYear(year) {
    if (this.yearExp != null && this.yearExp != year) {
      this.changeYear = true;
    }
    this.yearExp = year; 
  }

  pickAMonth(month) {
    if (this.monthExp != null && this.monthExp != month) {
      this.changeMonth = true;
    } 
    this.monthExp = month;
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
    // card info
    if (this.name.errors != null) {
      this.incorrectNameInput = true;
      this.messageNameInput = "Enter your name!"
     cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectNameInput = false;
      this.messageNameInput = "";
    }
    if (this.number.errors != null) {
      this.incorrectNumberInput = true;
      this.messageNumberInput = "Enter card number!"
     cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectNumberInput = false;
      this. messageNumberInput = "";
    }
    if (this.cvccw.errors != null) {
      this.incorrectCvcInput = true;
      this.messageCvcInput = "Enter cvc/cw!"
     cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectCvcInput = false;
      this.messageCvcInput = "";
    }
    if (this.yearExp == "Year") {
      this.incorrectYear = true;
      this.messageExpDate = "Choose expiration date!"
      cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectYear = false;
      this.messageExpDate = "";
    }
    if (this.monthExp == "Month") {
      this.incorrectMonth = true;
      this.messageExpDate = "Choose expiration date!"
      cardHtmlElement.scrollIntoView();
    } else {
      this.incorrectMonth = false;
      if (this.yearExp != "Year") {
        this.messageExpDate = "";
      }
    }
    if (this.name.errors == null &&
      this.number.errors == null &&
      this.cvccw.errors == null &&
      this.yearExp != "Year" &&
      this.monthExp != "Month") {   
      this.loading = true;
      (<any>window).Stripe.card.createToken({
        number: this.cardForm.get("number").value,
        exp_month: this.monthExp,
        exp_year: this.yearExp,
        cvc: this.cardForm.get("cvccw").value,
        name: this.cardForm.get("name").value
      }, (status: number, response: any) => {
        this._zone.run(() => {
        if (status === 200) {
         this.userService.saveCardInformation(response.id, this.loginService.getUserEmail())
         .subscribe(cardInfo => {
          this.cardInfo = cardInfo;
          this.loading = false;
          this.incorrectNumberInput = false;
          this.incorrectCvcInput = false;
          this.enableSaveCardButton = false;
          this.cardForm.get("name").disable({onlySelf: true});
          this.cardForm.get("number").disable({onlySelf: true});
          this.cardForm.get("cvccw").disable({onlySelf: true});
          this.cardForm.get("cvccw").setValue(null);
          this.cardForm.get("number").setValue("************" + this.cardInfo.number);
          this.changeNameInput = false;
          this.changeNumberInput = false;
          this.changeCvcInput = false;
          this.changeMonth = false;
          this.changeYear = false;
         });
        } else {
          this.loading = false;
         if (response.error.message == "The card number is not a valid credit card number."
          || response.error.message == "Your card number is incorrect.") {
            this.messageNumberInput = response.error.message;
            this.incorrectNumberInput = true;
            this.loading = false;
          } else if (response.error.message == "Your card's security code is invalid.") {
            this.messageCvcInput = response.error.message;
            this.incorrectCvcInput = true;
            this.loading = false;
          }
        console.log(response.error.message);
        }
      });  
    });  
  }
 
}
  editCard() {
  this.enableSaveCardButton = true;
  this.cardForm.get("name").enable({onlySelf: true});
  this.cardForm.get("number").enable({onlySelf: true});
  this.cardForm.get("cvccw").enable({onlySelf: true});

  if (this.cardInfo != null) {
  this.cardForm.get("name").setValue(this.cardInfo.name)
  this.cardForm.get("number").setValue("************" + this.cardInfo.number);
  this.monthExp = this.cardInfo.exp_month;
  this.yearExp = this.cardInfo.exp_year;
  }
} 
  cancel() {
  this.enableSaveCardButton = false;
  this.cardForm.get("name").disable({onlySelf: true});
  this.cardForm.get("number").disable({onlySelf: true});
  this.cardForm.get("cvccw").disable({onlySelf: true});
  this.cardForm.get("cvccw").setValue(null);

  if (this.cardInfo != null) {
  this.cardForm.get("name").setValue(this.cardInfo.name)
  this.cardForm.get("number").setValue("************" + this.cardInfo.number);
  this.monthExp = this.cardInfo.exp_month;
  this.yearExp = this.cardInfo.exp_year;
  } else {
  this.cardForm.get("name").setValue(null)
  this.cardForm.get("number").setValue(null);
  this.monthExp = "Month";
  this.yearExp = "Year";
  }
  this.incorrectNameInput = false;
  this.incorrectNumberInput = false;
  this.incorrectCvcInput = false;
  this.incorrectYear = false;
  this.incorrectMonth = false;

  this.changeNameInput = false;
  this.changeNumberInput = false;
  this.changeCvcInput = false;
  this.changeMonth = false;
  this.changeYear = false;

  this.messageCvcInput = "";
  this.messageExpDate = "";
  this.messageNameInput = "";
  this.messageNumberInput = "";
}

}
