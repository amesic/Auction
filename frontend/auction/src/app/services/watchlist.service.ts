import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Watchlist } from '../models/Watchlist';
import { Observable } from 'rxjs';

const httpOptions={
  headers: new  HttpHeaders({
    'Content-Type':'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  urlSaveNewWatchlist = "/auth/save/watchlist"

  constructor(private http: HttpClient) { }

  saveItemFromUserToWatchlist(productId, emailUser): Observable<Watchlist> {
    return this.http.post<any>(this.urlSaveNewWatchlist, {
      "product": {"id": productId},
      "user": {"email": emailUser}
    }, httpOptions);
  }
}
