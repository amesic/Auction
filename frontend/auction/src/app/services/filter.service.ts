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

  getFilterItemsByName(name: string): Observable<Filter> {
    return this.http.get<Filter>(this.urlFilterItemsByName + "?name=" + name);
  }
}
