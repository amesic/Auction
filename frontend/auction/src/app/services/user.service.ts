import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

const httpOptions={
  headers: new  HttpHeaders({
    'Content-Type':'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  urlUserInfo = "/auth/user/info";
  urlSaveCardInfo = "/auth/card/user";
  urlCardInfo = "/auth/card/info/user";

  constructor(private http: HttpClient) { }

  getUserInfo(email): Observable<User> {
    return this.http.get<User>(this.urlUserInfo + "?email=" + email);
  }
  getCardInfo(email): Observable<any> {
    return this.http.get<any>(this.urlCardInfo + "?email=" + email)
  }
  saveCardInformation(number, exp_month, exp_year, cvc, name, emailUser): Observable<any> {
    return this.http.post<any>(this.urlSaveCardInfo, {
      "number": number,
      "exp_month": exp_month,
      "exp_year": exp_year,
      "cvc": cvc,
      "name": name,
      "emailUser": emailUser
    }, httpOptions);
  }
}
