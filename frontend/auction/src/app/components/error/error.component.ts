import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, NavigationStart } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  private previousUrl: string;
  private currentUrl: string;

  constructor(private router: Router) {
    this.previousUrl = this.router.url;
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {        
        this.currentUrl = this.previousUrl;
        this.previousUrl = event.url;
      };
    });
   }

  ngOnInit() {
  }
  goBack() {
    this.router.navigate([this.previousUrl]);
  }

}
