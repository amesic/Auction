import { Component, OnInit, Input } from "@angular/core";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { FormGroup, Validators, FormControl } from "@angular/forms";
import { UserService } from "src/app/services/user.service";
import { LoginService } from "src/app/services/login.service";

@Component({
  selector: "app-address",
  templateUrl: "./address.component.html",
  styleUrls: ["./address.component.css"]
})
export class AddressComponent implements OnInit {
  @Input() addressInfo;

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  incorrectStreetInput = false;
  incorrectCityInput = false;
  incorrectZipcodeInput = false;
  incorrectStateInput = false;
  incorrectCountryInput = false;

  enableSaveCardButton = false;

  changeStreetInput = false;
  changeCityInput = false;
  changeZipcodeInput = false;
  changeStateInput = false;
  changeCountryInput = false;

  loading = false;

  messageStreetInput;
  messageCityInput;
  messageZipcodeInput;
  messageStateInput;
  messageCountryInput;

  addressForm = new FormGroup({
    state: new FormControl({ value: null, disabled: true }, [
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

  constructor(
    private userService: UserService,
    private loginService: LoginService
  ) {}

  ngOnInit() {
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
        (this.addressInfo != undefined &&
        value != null &&
        value != this.addressInfo.street) ||
        (this.addressInfo == null && value != null)
      ) {
        this.changeStreetInput = true;
      }
    });
    this.addressForm.controls["state"].valueChanges.subscribe(value => {
      if (value == "") {
        this.incorrectStateInput = true;
        this.messageStateInput = "Enter state!";
        this.changeStateInput = false;
      } else {
        this.incorrectStateInput = false;
        this.messageStateInput = "";
      }
      if (
        (this.addressInfo != undefined &&
        value != null &&
        value != this.addressInfo.state) ||
        (this.addressInfo == null && value != null)
      ) {
        this.changeStateInput = true;
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
        (this.addressInfo != undefined &&
        value != null &&
        value != this.addressInfo.zipCode) ||
        (this.addressInfo == null && value != null)
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
        (this.addressInfo != undefined &&
        value != null &&
        value != this.addressInfo.country) ||
        (this.addressInfo == null && value != null)
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
        (this.addressInfo != undefined &&
        value != null &&
        value != this.addressInfo.city) ||
        (this.addressInfo == null && value != null)
      ) {
        this.changeCityInput = true;
      }
    });
  }

  get street() {
    return this.addressForm.get("street");
  }
  get state() {
    return this.addressForm.get("state");
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

  editCard() {
    this.enableSaveCardButton = true;
    this.addressForm.get("street").enable({ onlySelf: true });
    this.addressForm.get("state").enable({ onlySelf: true });
    this.addressForm.get("city").enable({ onlySelf: true });
    this.addressForm.get("country").enable({ onlySelf: true });
    this.addressForm.get("zipcode").enable({ onlySelf: true });

    if (this.addressInfo != null) {
      this.addressForm.get("street").setValue(this.addressInfo.street);
      this.addressForm.get("state").setValue(this.addressInfo.state);
      this.addressForm.get("city").setValue(this.addressInfo.city);
      this.addressForm.get("country").setValue(this.addressInfo.country);
      this.addressForm.get("zipcode").setValue(this.addressInfo.zipCode);
    }
  }
  cancel() {
    this.enableSaveCardButton = false;
    this.addressForm.get("street").disable({ onlySelf: true });
    this.addressForm.get("state").disable({ onlySelf: true });
    this.addressForm.get("city").disable({ onlySelf: true });
    this.addressForm.get("country").disable({ onlySelf: true });
    this.addressForm.get("zipcode").disable({ onlySelf: true });

    if (this.addressInfo != null) {
      this.addressForm.get("street").setValue(this.addressInfo.street);
      this.addressForm.get("state").setValue(this.addressInfo.state);
      this.addressForm.get("city").setValue(this.addressInfo.city);
      this.addressForm.get("country").setValue(this.addressInfo.country);
      this.addressForm.get("zipcode").setValue(this.addressInfo.zipCode);
    } else {
      this.addressForm.get("street").setValue(null);
      this.addressForm.get("state").setValue(null);
      this.addressForm.get("city").setValue(null);
      this.addressForm.get("country").setValue(null);
      this.addressForm.get("zipcode").setValue(null);
    }

    this.incorrectStreetInput = false;
    this.incorrectCityInput = false;
    this.incorrectZipcodeInput = false;
    this.incorrectStateInput = false;
    this.incorrectCountryInput = false;

    this.changeStreetInput = false;
    this.changeCityInput = false;
    this.changeZipcodeInput = false;
    this.changeStateInput = false;
    this.changeCountryInput = false;

    this.messageStreetInput = "";
    this.messageCityInput = "";
    this.messageZipcodeInput = "";
    this.messageStateInput = "";
    this.messageCountryInput = "";

    this.loading = false;
  }

  saveInformations(addressHtmlElement) {
    // address info
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
    if (this.state.errors != null) {
      this.incorrectStateInput = true;
      this.messageStateInput = "Enter state!";
      addressHtmlElement.scrollIntoView();
    } else {
      this.incorrectStateInput = false;
      this.messageStateInput = "";
    }
    if (this.country.errors != null) {
      this.incorrectCountryInput = true;
      this.messageCountryInput = "Enter country!";
      addressHtmlElement.scrollIntoView();
    } else {
      this.incorrectCountryInput = false;
      this.messageCountryInput = "";
    }
    if (
      this.street.errors == null &&
      this.city.errors == null &&
      this.zipcode.errors == null &&
      this.state.errors == null &&
      this.country.errors == null
    ) {
      this.userService
        .saveAddressInfoFromUser(
          this.loginService.getUserEmail(),
          this.addressForm.get("state").value,
          this.addressForm.get("street").value,
          this.addressForm.get("city").value,
          this.addressForm.get("zipcode").value,
          this.addressForm.get("country").value
        )
        .subscribe(addressInfo => {
          this.addressInfo = addressInfo;
          this.loading = false;

          this.incorrectStreetInput = false;
          this.incorrectCityInput = false;
          this.incorrectZipcodeInput = false;
          this.incorrectStateInput = false;
          this.incorrectCountryInput = false;

          this.enableSaveCardButton = false;

          this.addressForm.get("street").disable({ onlySelf: true });
          this.addressForm.get("state").disable({ onlySelf: true });
          this.addressForm.get("city").disable({ onlySelf: true });
          this.addressForm.get("country").disable({ onlySelf: true });
          this.addressForm.get("zipcode").disable({ onlySelf: true });

          this.changeStreetInput = false;
          this.changeCityInput = false;
          this.changeZipcodeInput = false;
          this.changeStateInput = false;
          this.changeCountryInput = false;
        });
    }
  }
}
