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

  getFilterItemsByName(name: string, subcategoryId, filterClicked, searchValue): Observable<Filter> {
    let url = this.urlFilterItemsByName;
    url += "?name=" + name;
    if (subcategoryId != null) {
      url += "&subcategoryId=" + subcategoryId;
    } 
    if (filterClicked != null && filterClicked.length != 0) {
      url += "&listOfCharacteristicClicked=" + filterClicked;
    }
    if (searchValue != null) {
      url += "&searchUser=" + searchValue;
    }
    return this.http.get<Filter>(url);
}
 getFilterPriceInfo(subcategoryId, listOfCharacteristicClicked, searchValue): Observable<PriceProduct> {
   let url = this.urlFilterPriceInfo;
   if (subcategoryId != null) {
     url += "?subcategoryId=" + subcategoryId;
   }
   if (listOfCharacteristicClicked != null && listOfCharacteristicClicked.length != 0 && subcategoryId != null) {
     url += "&listOfCharacteristicsClicked=" + listOfCharacteristicClicked;
   } else if (listOfCharacteristicClicked != null && listOfCharacteristicClicked.length != 0) {
     url += "?listOfCharacteristicsClicked=" + listOfCharacteristicClicked;
   }
   if (searchValue != null && listOfCharacteristicClicked != null && listOfCharacteristicClicked.length != 0) {
     url += "&searchUser=" + searchValue;
   } else if (searchValue != null && subcategoryId != null) {
     url += "&searchUser=" + searchValue;
   } else if (searchValue != null && subcategoryId == null) {
    url += "?searchUser=" + searchValue;
   }
   return this.http.get<PriceProduct>(url);
 }
}
