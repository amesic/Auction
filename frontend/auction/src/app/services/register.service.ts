import { Injectable } from "@angular/core";
import { User } from "../models/User";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";


@Injectable({
  providedIn: "root"
})
export class RegisterService {
  constructor(private http: HttpClient) {}
  url = "/users/register";

  saveUserData(user: User): Observable<any> {
    const headers = new HttpHeaders().set("Content-Type", "application/json");
    return this.http.post(this.url, user, { headers, responseType: "text" });
  }
}
