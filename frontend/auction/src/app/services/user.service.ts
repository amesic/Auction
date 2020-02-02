import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url = "/auth/user/info";

  constructor(private http: HttpClient) { }

  getUserInfo(email): Observable<User> {
    return this.http.get<User>(this.url + "?email=" + email);
  }
}
