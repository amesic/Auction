import { NgModule, Injectable } from "@angular/core";
import { Routes, RouterModule, CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { LandingPageComponent } from "./components/landing-page/landing-page.component";
import { AllCategoriesComponent } from "./components/all-categories/all-categories.component";
import { SingleProductPageComponent } from "./components/single-product-page/single-product-page.component";
import { LoginService } from './services/login.service';

@Injectable()
export class LoginRegisterActivate implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.loginService.isUserLoggedIn()) {
      this.router.navigate(['/home']);
    }
    return true;
  }
}

const routes: Routes = [
  { path: "", redirectTo: "/home", pathMatch: "full" },
  {
    path: "home",
    children: [
      { path: "login", component: LoginComponent, canActivate:[LoginRegisterActivate] },
      { path: "register", component: RegisterComponent, canActivate:[LoginRegisterActivate] },
      { path: "", component: LandingPageComponent },
      { path: "allCategories", component: AllCategoriesComponent }
    ]
  },
  {
    path: "shop",
    children: [{ path: "product/:title/:idProduct", component: SingleProductPageComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
