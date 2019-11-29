import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from "@angular/common/http";
import { Filter } from '../models/Filter';
import { PriceProduct } from '../models/PriceProduct';

@Injectable({
  providedIn: 'root'
})
export class FilterService {
urlFilterItemsByName = "/characteristic/characteristic";
urlFilterPriceInfo = "/product/numberOfProductsByPrice";

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
 getFilterPriceInfo(): Observable<PriceProduct> {
   return this.http.get<PriceProduct>(this.urlFilterPriceInfo);
 }
}
