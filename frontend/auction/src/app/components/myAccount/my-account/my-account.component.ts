import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  userInfo;
  month = "";
  year = "";
  day = "";

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  pickedYear = "Year";
  pickedMonth = "Month";

  constructor(private userService: UserService, private loginService: LoginService) { }

  ngOnInit() {
    this.userService.getUserInfo(this.loginService.getUserEmail()).subscribe(user => {
      this.userInfo = user;
      if (this.userInfo.birthDate != null) {
      let date = new Date(Date.parse(this.userInfo.birthDate));
      this.day =  date.toDateString().split(" ")[2];
      this.year =  date.toDateString().split(" ")[3];
      this.month = date.toDateString().split(" ")[1];
      }
    });
  }

  pickAYear(year) {
    this.pickedYear = year;
  }
  pickAMonth(month) {
    this.pickedMonth = month;
  }

}
