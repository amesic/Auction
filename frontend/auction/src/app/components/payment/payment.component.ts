import { Component, OnInit, NgZone } from "@angular/core";
import { UserService } from "src/app/services/user.service";
import { LoginService } from "src/app/services/login.service";
import { ActivatedRoute, Router } from "@angular/router";
import { ProductService } from "src/app/services/product.service";
import { BidsService } from "src/app/services/bids.service";
import { StarRatingComponent } from "ng-starrating";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-payment",
  templateUrl: "./payment.component.html",
  styleUrls: ["./payment.component.css"]
})
export class PaymentComponent implements OnInit {
  cardInfo;
  userInfo;
  product;
  highestBid;
  yearExp = "Year";
  monthExp = "Month";

  paid;

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  showMessageError = false;
  enableSaveCardButton = false;
  loading = false;

  incorrectStreetInput = false;
  incorrectCityInput = false;
  incorrectZipcodeInput = false;
  incorrectPhoneInput = false;
  incorrectCountryInput = false;

  incorrectNameInput = false;
  incorrectNumberInput = false;
  incorrectCvcInput = false;
  incorrectYear = false;
  incorrectMonth = false;

  changeStreetInput = false;
  changeCityInput = false;
  changeZipcodeInput = false;
  changePhoneInput = false;
  changeCountryInput = false;

  changeNameInput = false;
  changeNumberInput = false;
  changeCvcInput = false;
  changeMonth = false;
  changeYear = false;

  messageStreetInput;
  messageCityInput;
  messageZipcodeInput;
  messagePhoneInput;
  messageCountryInput;

  messageNameInput;
  messageNumberInput;
  messageCvcInput;
  messageExpDate;

  ratingValue;

  hidePopUp = true;

  saveAddress = false;
  saveCard = false;

  addressForm = new FormGroup({
    phonenumber: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    street: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    city: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    country: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    zipcode: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ])
  });
  cardForm = new FormGroup({
    name: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    number: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ]),
    cvccw: new FormControl({ value: null, disabled: true }, [
      Validators.required
    ])
  });

  constructor(
    private userService: UserService,
    private loginService: LoginService,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
    private bidService: BidsService,
    private router: Router,
    private _zone: NgZone
  ) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe(routeParams => {
      this.ratingValue = 1;
      this.userService
        .getUserInfo(this.loginService.getUserEmail())
        .subscribe(user => {
          this.userInfo = user;
        });
      this.userService.getCardInfo(this.loginService.getUserEmail()).subscribe(
        card => {
          this.cardInfo = card;
          if (this.cardInfo != null) {
            this.yearExp = this.cardInfo.exp_year;
            this.monthExp = this.cardInfo.exp_month;
          }
        },
        err => console.log(err.error)
      );
      this.productService
        .getSingleProduct(routeParams.idProduct)
        .subscribe(product => {
          this.product = product;
        });
      this.bidService
        .getBidsInfoOfProduct(routeParams.idProduct, 0, 5)
        .subscribe(
          bidInfo => {},
          err => {
            this.highestBid = err.error.items[0];
          }
        );
      this.userService.checkIfCustomerPaidItem(this.loginService.getUserEmail(), routeParams.idProduct)
        .subscribe(paid => {
          this.paid = paid;
        }, err => console.log(err.error));
    });
    this.addressForm.controls["street"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectStreetInput = true;
        this.messageStreetInput = "Enter street!";
        this.changeStreetInput = false;
      } else {
        this.incorrectStreetInput = false;
        this.messageStreetInput = "";
      }
      if (
        (this.userInfo.address != undefined &&
          value != null &&
          value != this.userInfo.address.street) ||
        (this.userInfo.address == null && value != null)
      ) {
        this.changeStreetInput = true;
      }
    });
    this.addressForm.controls["phonenumber"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectPhoneInput = true;
        this.messagePhoneInput = "Enter phone number!";
        this.changePhoneInput = false;
      } else {
        this.incorrectPhoneInput = false;
        this.messagePhoneInput = "";
      }
      if (
        (this.userInfo.address != undefined &&
          value != null &&
          value != this.userInfo.phoneNumber) ||
        (this.userInfo.phoneNumber == null && value != null)
      ) {
        this.changePhoneInput = true;
      }
    });
    this.addressForm.controls["zipcode"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectZipcodeInput = true;
        this.messageZipcodeInput = "Enter zipCode!";
        this.changeZipcodeInput = false;
      } else {
        this.incorrectZipcodeInput = false;
        this.messageZipcodeInput = "";
      }
      if (
        (this.userInfo.address != undefined &&
          value != null &&
          value != this.userInfo.address.zipCode) ||
        (this.userInfo.address == null && value != null)
      ) {
        this.changeZipcodeInput = true;
      }
    });
    this.addressForm.controls["country"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectCountryInput = true;
        this.messageCountryInput = "Enter country!";
        this.changeCountryInput = false;
      } else {
        this.incorrectCountryInput = false;
        this.messageCountryInput = "";
      }
      if (
        (this.userInfo.address != undefined &&
          value != null &&
          value != this.userInfo.address.country) ||
        (this.userInfo.address == null && value != null)
      ) {
        this.changeCountryInput = true;
      }
    });
    this.addressForm.controls["city"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectCityInput = true;
        this.messageCityInput = "Enter city!";
        this.changeCityInput = false;
      } else {
        this.incorrectCityInput = false;
        this.messageCityInput = "";
      }
      if (
        (this.userInfo.address != undefined &&
          value != null &&
          value != this.userInfo.address.city) ||
        (this.userInfo.address == null && value != null)
      ) {
        this.changeCityInput = true;
      }
    });
    this.cardForm.controls["name"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectNameInput = true;
        this.messageNameInput = "Enter your name!";
        this.changeNameInput = false;
      } else {
        this.incorrectNameInput = false;
        this.messageNameInput = "";
      }
      if (
        (this.cardInfo != undefined &&
          value != null &&
          value != this.cardInfo.name) ||
        (this.cardInfo == null && value != null)
      ) {
        this.changeNameInput = true;
      }
    });
    this.cardForm.controls["number"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectNumberInput = true;
        this.messageNumberInput = "Enter card number!";
        this.changeNumberInput = false;
      } else {
        this.incorrectNumberInput = false;
        this.messageNumberInput = "";
      }
      if (
        (this.cardInfo != undefined &&
          value != null &&
          value != "************" + this.cardInfo.number) ||
        (this.cardInfo == null && value != null)
      ) {
        this.changeNumberInput = true;
      }
    });
    this.cardForm.controls["cvccw"].valueChanges.subscribe(value => {
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

  checkboxAddress(event) {
    this.saveAddress = event.currentTarget.checked;
  }
  checkboxCard(event) {
    this.saveCard = event.currentTarget.checked;
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
  get street() {
    return this.addressForm.get("street");
  }
  get phonenumber() {
    return this.addressForm.get("phonenumber");
  }
  get city() {
    return this.addressForm.get("city");
  }
  get country() {
    return this.addressForm.get("country");
  }
  get zipcode() {
    return this.addressForm.get("zipcode");
  }

  edit() {
    this.enableSaveCardButton = true;
    this.addressForm.get("street").enable({ onlySelf: true });
    this.addressForm.get("phonenumber").enable({ onlySelf: true });
    this.addressForm.get("city").enable({ onlySelf: true });
    this.addressForm.get("country").enable({ onlySelf: true });
    this.addressForm.get("zipcode").enable({ onlySelf: true });
    this.cardForm.get("name").enable({ onlySelf: true });
    this.cardForm.get("number").enable({ onlySelf: true });
    this.cardForm.get("cvccw").enable({ onlySelf: true });

    if (this.userInfo.address != null) {
      this.addressForm.get("street").setValue(this.userInfo.address.street);
      this.addressForm.get("phonenumber").setValue(this.userInfo.phoneNumber);
      this.addressForm.get("city").setValue(this.userInfo.address.city);
      this.addressForm.get("country").setValue(this.userInfo.address.country);
      this.addressForm.get("zipcode").setValue(this.userInfo.address.zipCode);
    }
    if (this.cardInfo != null) {
      this.cardForm.get("name").setValue(this.cardInfo.name);
      this.cardForm
        .get("number")
        .setValue("************" + this.cardInfo.number);
      this.monthExp = this.cardInfo.exp_month;
      this.yearExp = this.cardInfo.exp_year;
    }
  }

  pay(cardHtmlElement, addressHtmlElement) {
    if ((this.cardInfo == null || this.userInfo.address == null) && !this.enableSaveCardButton) {
      this.showMessageError = true;
      window.scroll(0, 0);
    } else if (this.enableSaveCardButton) {
      if (this.name.errors != null) {
        this.incorrectNameInput = true;
        this.messageNameInput = "Enter your name!";
        cardHtmlElement.scrollIntoView();
      } else {
        this.incorrectNameInput = false;
        this.messageNameInput = "";
      }
      if (this.number.errors != null) {
        this.incorrectNumberInput = true;
        this.messageNumberInput = "Enter card number!";
        cardHtmlElement.scrollIntoView();
      } else {
        this.incorrectNumberInput = false;
        this.messageNumberInput = "";
      }
      if (this.cvccw.errors != null) {
        this.incorrectCvcInput = true;
        this.messageCvcInput = "Enter cvc/cw!";
        cardHtmlElement.scrollIntoView();
      } else {
        this.incorrectCvcInput = false;
        this.messageCvcInput = "";
      }
      if (this.yearExp == "Year") {
        this.incorrectYear = true;
        this.messageExpDate = "Choose expiration date!";
        cardHtmlElement.scrollIntoView();
      } else {
        this.incorrectYear = false;
        this.messageExpDate = "";
      }
      if (this.monthExp == "Month") {
        this.incorrectMonth = true;
        this.messageExpDate = "Choose expiration date!";
        cardHtmlElement.scrollIntoView();
      } else {
        this.incorrectMonth = false;
        if (this.yearExp != "Year") {
          this.messageExpDate = "";
        }
      }

      if (this.street.errors != null) {
        this.incorrectStreetInput = true;
        this.messageStreetInput = "Enter street!";
        addressHtmlElement.scrollIntoView();
      } else {
        this.incorrectStreetInput = false;
        this.messageStreetInput = "";
      }
      if (this.city.errors != null) {
        this.incorrectCityInput = true;
        this.messageCityInput = "Enter city!";
        addressHtmlElement.scrollIntoView();
      } else {
        this.incorrectCityInput = false;
        this.messageCityInput = "";
      }
      if (this.zipcode.errors != null) {
        this.incorrectZipcodeInput = true;
        this.messageZipcodeInput = "Enter zipCode!";
        addressHtmlElement.scrollIntoView();
      } else {
        this.incorrectZipcodeInput = false;
        this.messageZipcodeInput = "";
      }
      if (this.phonenumber.errors != null) {
        this.incorrectPhoneInput = true;
        this.messagePhoneInput = "Enter phone number!";
        addressHtmlElement.scrollIntoView();
      } else {
        this.incorrectPhoneInput = false;
        this.messagePhoneInput = "";
      }
      if (this.country.errors != null) {
        this.incorrectCountryInput = true;
        this.messageCountryInput = "Enter country!";
        addressHtmlElement.scrollIntoView();
      } else {
        this.incorrectCountryInput = false;
        this.messageCountryInput = "";
      }
    }
    if (
      (this.name.errors == null &&
        this.number.errors == null &&
        this.cvccw.errors == null &&
        this.yearExp != "Year" &&
        this.monthExp != "Month" &&
        this.street.errors == null &&
        this.city.errors == null &&
        this.zipcode.errors == null &&
        this.phonenumber.errors == null &&
        this.country.errors == null) ||
      (this.cardInfo != null && this.userInfo.address != null)
    ) {
      this.showMessageError = false;
      this.loading = true;
      window.scroll(0, 200);
      if (this.saveAddress) {
        let state;
        if (this.userInfo.address != null) {
        state = this.userInfo.address.state;
        } else {
          state = null;
        }
        this.userService.savePaymentInfo(
          this.loginService.getUserEmail(),
          this.addressForm.get("phonenumber").value,
          state,
          this.addressForm.get("street").value,
          this.addressForm.get("city").value,
          this.addressForm.get("zipcode").value,
          this.addressForm.get("country").value
        ).subscribe(userInfo => { 
          this.userInfo = userInfo;

          this.incorrectStreetInput = false;
          this.incorrectCityInput = false;
          this.incorrectZipcodeInput = false;
          this.incorrectCountryInput = false;
          this.incorrectPhoneInput = false;

          this.changeStreetInput = false;
          this.changeCityInput = false;
          this.changeZipcodeInput = false;
          this.changePhoneInput = false;
          this.changeCountryInput = false;

          this.messagePhoneInput = "";

          this.addressForm.get("street").disable({ onlySelf: true });
          this.addressForm.get("phonenumber").disable({ onlySelf: true });
          this.addressForm.get("city").disable({ onlySelf: true });
          this.addressForm.get("country").disable({ onlySelf: true });
          this.addressForm.get("zipcode").disable({ onlySelf: true });
        }, err => {
          this.loading = false;
          if (err.error == "Phone number is invalid!") {
            this.incorrectPhoneInput = true;
            this.messagePhoneInput = err.error;
            addressHtmlElement.scrollIntoView();
          }
        });
        
      }
        (<any>window).Stripe.card.createToken({
          number: this.cardForm.get("number").value,
          exp_month: this.monthExp,
          exp_year: this.yearExp,
          cvc: this.cardForm.get("cvccw").value,
          name: this.cardForm.get("name").value
        }, (status: number, response: any) => {
          this._zone.run(() => {
          if (status === 200) {
            if (this.saveCard) {
           this.userService.saveCardInformation(response.id, this.loginService.getUserEmail())
           .subscribe(cardInfo => {
            this.cardInfo = cardInfo;
           
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

            this.messageCvcInput = "";
            this.messageExpDate = "";
            this.messageNameInput = "";
            this.messageNumberInput = "";

            this.userService.chargeCustomer(
              this.userInfo.email,
              this.product.seller.email,
              this.product.id,
              null,
              this.highestBid.value * 100
            ).subscribe(
              response => {
                this.loading = false;
                this.hidePopUp = false;
                window.scroll(0, 0);
              },
              err => console.log("ERROR", err.error)
            );
           });
          }
          if (!this.saveCard) {
          this.userService.saveCardInformation(null, this.loginService.getUserEmail())
         .subscribe(customerId => {
          this.userService.chargeCustomer(
          this.userInfo.email,
          this.product.seller.email,
          this.product.id,
          response.id,
          this.highestBid.value * 100
        ).subscribe(
          response => {
            this.loading = false;
            this.hidePopUp = false;
            window.scroll(0, 0);
          },
          err => console.log("ERROR", err.error)
        );
      }, err => console.log("ERROR", err.error));
          }
          } else {
            this.loading = false;
            cardHtmlElement.scrollIntoView();
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

  onRate($event: {
    oldValue: number;
    newValue: number;
    starRating: StarRatingComponent;
  }) {
    this.ratingValue = `${$event.newValue}`;
  }
  done() {
    this.userService
      .saveRateFromUser(
        this.loginService.getUserEmail(),
        this.product.seller.email,
        this.ratingValue
      )
      .subscribe(
        rate => {
          this.router.navigate(["/my-account/bids"]);
        },
        err => console.log(err.error)
      );
  }
  skip() {
    this.router.navigate(["/my-account/bids"]);
  }
}
