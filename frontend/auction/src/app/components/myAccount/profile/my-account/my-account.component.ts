import { Component, OnInit} from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  userInfo;
  cardInfo;
  addressInfo;
  month = "Month";
  year = "Year";
  day = "Day";
  
  yearExp = "Year";
  monthExp = "Month";

  gender = "Your Gender";

  constructor(
    private userService: UserService, 
    private loginService: LoginService) { }

  ngOnInit() {
    this.userService.getUserInfo(this.loginService.getUserEmail()).subscribe(user => {
      this.userInfo = user;
      this.addressInfo = user.address;
      if (this.userInfo.birthDate != null) {
      let date = new Date(Date.parse(this.userInfo.birthDate));
      this.day = date.toDateString().split(" ")[2];
      this.year = date.toDateString().split(" ")[3];
      this.month = date.toDateString().split(" ")[1];
      }
      if (this.userInfo.gender != null) {
        this.gender = this.userInfo.gender;
      }
    });
    this.userService.getCardInfo(this.loginService.getUserEmail()).subscribe(card => {
      this.cardInfo = card;
     if (this.cardInfo != null) {
       this.yearExp = this.cardInfo.exp_year;
       this.monthExp = this.cardInfo.exp_month;
     }
    },
    err => console.log(err.error));
  }

}
