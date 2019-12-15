import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Watchlist } from '../models/Watchlist';
import { Observable } from 'rxjs';
import { PaginationInfo } from '../models/PaginationInfo';

const httpOptions={
  headers: new  HttpHeaders({
    'Content-Type':'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  urlSaveNewWatchlist = "/auth/save/watchlist";
  urlGetWatchlistFromUser = "/auth/watchlistFromUser";
  urlDeleteFromWatchlist = "/auth/deleteItemFromWatchlist";

  constructor(private http: HttpClient) { }

  saveItemFromUserToWatchlist(productId, emailUser): Observable<Watchlist> {
    return this.http.post<any>(this.urlSaveNewWatchlist, {
      "product": {"id": productId},
      "user": {"email": emailUser}
    }, httpOptions);
  }

  getWatchlistFromUser(email, pageNumber, size): Observable<PaginationInfo> {
    return this.http.get<PaginationInfo>(this.urlGetWatchlistFromUser + "?email=" + email +
    "&pageNumber=" + pageNumber + "&size=" + size);
  }

  deleteFromWatchlistOfUser(email, idProduct, pageNumber, size): Observable<PaginationInfo> {
    return this.http.delete<PaginationInfo>(this.urlDeleteFromWatchlist + "?email=" + email +
    "&idProduct=" + idProduct + "&pageNumber=" + pageNumber + "&size=" + size);
  }
}
