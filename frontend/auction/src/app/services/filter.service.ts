import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from "@angular/common/http";
import { Filter } from '../models/Filter';

@Injectable({
  providedIn: 'root'
})
export class FilterService {
urlFilterItemsByName = "/characteristic/characteristic";

  constructor(private http: HttpClient) { }

  getFilterItemsByName(name: string, subcategoryId): Observable<Filter> {
    if (subcategoryId != null) {
    return this.http.get<Filter>(this.urlFilterItemsByName + "?name=" + name + "&subcategoryId=" + subcategoryId);
  } else {
    return this.http.get<Filter>(this.urlFilterItemsByName + "?name=" + name);
  }
}
}
