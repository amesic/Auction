import { Injectable } from '@angular/core';
import {User} from '../models/User';
import {Observable} from 'rxjs'; 
import {HttpClient, HttpHeaders} from '@angular/common/http'; 
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http:HttpClient) { }
  
  saveUserData(url: string, user: User) :Observable<any>{
    const headers= new HttpHeaders().set('Content-Type','application/json');
    return this.http.post(url, user, {headers, responseType: 'text'});
  }
}
