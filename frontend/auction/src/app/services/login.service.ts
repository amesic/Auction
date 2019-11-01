import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class LoginService {
  constructor(private http: HttpClient) {}

  authenticate(url: string, email: string, password: string) {
    return this.http.post<any>(url, { email, password }).pipe(
      map(userData => {
        sessionStorage.setItem("username", userData.username);
        let tokenStr = "Bearer " + userData.token;
        sessionStorage.setItem("token", tokenStr);
        return userData;
      })
    );
  }
  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    return !(user == null);
  }
  logOut() {
    sessionStorage.removeItem("username");
  }
  getUserName() {
    if (this.isUserLoggedIn) {
      return sessionStorage.getItem("username");
    } else {
      return "";
    }
  }
}
