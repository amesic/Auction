import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core'; //koristimo komponentu Input

@Component({
  selector: 'app-below-header',
  templateUrl: './below-header.component.html',
  styleUrls: ['./below-header.component.css']
})
export class BelowHeaderComponent implements OnInit {
@Input() title1:string;
@Input() title2:string;
@Input() title3:string;
  constructor() { }

  ngOnInit() {
    if(this.title2!=undefined) this.title2=this.title2+'/';
  }

}
