import { Injectable } from '@angular/core';
import {User} from '../models/User';
import {Observable} from 'rxjs'; 
import {HttpClient, HttpHeaders} from '@angular/common/http'; 


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  getUserId(url: string, dataFromUser: object ):Observable<User>{
  return this.http.post<any>(url, dataFromUser);
  }
}
