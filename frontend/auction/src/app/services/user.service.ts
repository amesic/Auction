import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/User";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json"
  })
};

@Injectable({
  providedIn: "root"
})
export class UserService {
  urlUserInfo = "/auth/user/info";
  urlSaveCardInfo = "/auth/card/user";
  urlCardInfo = "/auth/card/info/user";
  urlChargeCustomer = "/auth/card/charge";
  urlRatingOfSeller = "/auth/rating";
  urlSaveRateFromUser = "/auth/rate";
  urlSaveRequiredInfo = "/auth/user/required/info";

  constructor(private http: HttpClient) {}

  getUserInfo(email): Observable<User> {
    return this.http.get<User>(this.urlUserInfo + "?email=" + email);
  }
  getCardInfo(email): Observable<any> {
    return this.http.get<any>(this.urlCardInfo + "?email=" + email);
  }
  saveCardInformation(token, emailUser): Observable<any> {
    return this.http.post<any>(
      this.urlSaveCardInfo,
      {
        token: token,
        emailUser: emailUser
      },
      httpOptions
    );
  }
  chargeCustomer(
    emailCustomer,
    emailSeller,
    productId,
    amount
  ): Observable<any> {
    return this.http.get<any>(
      this.urlChargeCustomer +
        "?emailCustomer=" +
        emailCustomer +
        "&emailSeller=" +
        emailSeller +
        "&productId=" +
        productId +
        "&amount=" +
        amount
    );
  }
  ratingOfSeller(emailSeller): Observable<any> {
    return this.http.get<any>(
      this.urlRatingOfSeller + "?emailSeller=" + emailSeller
    );
  }
  saveRateFromUser(emailCustomer, emailSeller, value): Observable<any> {
    return this.http.post<any>(
      this.urlSaveRateFromUser,
      {
        user: {
          email: emailCustomer
        },
        seller: {
          email: emailSeller
        },
        value: value
      },
      httpOptions
    );
  }
  saveRequiredInfoFromUser(
    username,
    gender,
    birthDate,
    phonenumber,
    email,
    emailUser
  ): Observable<User> {
    return this.http.post<User>(
      this.urlSaveRequiredInfo,
      {
        userName: username,
        gender: gender,
        birthDate: birthDate,
        phoneNumber: phonenumber,
        email: email,
        emailLoggedUser: emailUser
      },
      httpOptions
    );
  }
}
