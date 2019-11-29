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

  getFilterItemsByName(name: string, subcategoryId, listOfFiltersClicked): Observable<Filter> {
    let url = this.urlFilterItemsByName;
    url += "?name=" + name;
    if (subcategoryId != null) {
      url += "&subcategoryId=" + subcategoryId;
    } 
    if(listOfFiltersClicked != null) {
      url += "&listOfCharacteristicClicked=" + listOfFiltersClicked;
    }
    return this.http.get<Filter>(url);
}
}
